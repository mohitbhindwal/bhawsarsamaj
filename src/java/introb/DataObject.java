package introb;

 


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

 
public class DataObject extends DataChangeListenerRegistry implements DataChangeListener, Serializable, Comparable<DataObject> {
	
	//Constants for specifying the status of the data objects..
	public final static int NOCHANGE = 0;
	public final static int CREATED = 1;
	public final static int MODIFIED = 2;
	public final static int DELETED = 3;
	public final static int JUNK = 4;
	private String m_strOrderFieldname = null;
	
	
	//Member variables
	private int m_status;
	private HashMap m_elements;
	private boolean m_isdirty;
	
	/**
	 * Constructor
	 * No listerner will be registered.
	 */
	public DataObject() {
		//Data object instantiated..
		super();
		m_status = CREATED;
		m_elements = new HashMap();
		m_isdirty = true;
	}
	
	/**
	 * 
	 * @param map
	 * 
	 * Constructed by paras - On May 28, 2005 At 7:31:32 PM
	 */
	public DataObject(Map map) {
		//Data object instantiated..
		super();
		m_status = CREATED;
		//Cloning necessary so that n one can modify the map from the outside
		m_elements = (map instanceof HashMap ? (HashMap) ((HashMap) map).clone() : new HashMap());
		m_isdirty = true;
	}
	
	/**
	 * Constructor for ordering of dataobject 
	 * No listerner will be registered.
	 */
	public DataObject(String order) {
		//Data object instantiated..
		super();
		m_status = CREATED;
		m_elements = new HashMap();
		m_strOrderFieldname = order ;
		m_isdirty = true;
	}

	
	/**
	 * Constructor
	 * This will register the listener with the DataObject.
	 * @deprecated
	 */
	public DataObject(DataChangeListener listener) {
		//Data object instantiated..
		super();
		m_status = CREATED;
		m_elements = new HashMap();
		this.addChangeListener(listener);
		m_isdirty = true;
	}
	
	/**
	 * Create a data object and add it the the fields.
	 * @param fieldName
	 * @return newly created data object.
	 * @deprecated
	 */
	public DataObject createDataObject(String fieldName) {
		DataObject dobj = new DataObject(this);
		this.set(fieldName, dobj);
		return dobj;
	}

	/**
	 * Create a data set and add it to the fields.
	 * @param fieldName
	 * @return newly created data set.
	 * @deprecated
	 */
	public DataSet createDataSet(String fieldName) {
		DataSet lst = new DataSet();
		lst.addChangeListener(this);
		this.set(fieldName, lst);
		return lst;
	}

	/**
	 * Check whether a key is present or not.
	 * @param fieldName
	 * @return true if fieldName specified is present.
	 */
	public boolean containsKey(String fieldName) {
		return m_elements.containsKey(fieldName);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Sep 28, 2004 12:26:19 PM
	 * 
	 * @param fieldName - Check Whether the field is present or not. If present then the field is removed. 
	 * @return true if field is there and removed successfilly else it returns false.
	 */
	public boolean removeIfThere(String fieldName){
		boolean blnReturn = containsKey(fieldName);
		if (blnReturn){
			remove(fieldName);
		}
		return blnReturn;
	}
	
	
	/**
	 * Return the item with the specifed path. For e. g. you can get quantity of 
	 * first ordered item by using the path "orderitems.0/qty" 
	 * @param fieldPath
	 * @return object
	 */
	public Object getFromPath(String fieldPath) {
		Object retValue = null;
		try {
			int index = fieldPath.indexOf("/");
			if (index == -1) {
				//FIELD-NAME specified is an element.. return that element..
				retValue = this.get(fieldPath);
			} else {
				//FIELD-NAME specified is a DataSet or a DataObject.. 
				String field = fieldPath.substring(0, index);
				String path = fieldPath.substring(index+1);
				
				int indexDOT = field.indexOf(".");
				if (indexDOT == -1) {
					//FIELD is a DataObject..
					retValue = ((DataObject) this.get(field)).getFromPath(path);
				} else {
					//FIELD is a DataSet..
					String dobjField = field.substring(0, indexDOT);
					int dsIndex = Integer.parseInt(field.substring(indexDOT+1));
					DataSet ds = (DataSet) this.get(dobjField);
					DataObject dobj = ds.get(dsIndex);
					retValue = dobj.getFromPath(path);
				}
			}
		} catch (FieldNotFoundException f) {
			throw new FieldNotFoundException("Item not found in getFromPath - " + fieldPath + " -- " + f);
		} catch (Exception e) {
			throw new FieldNotFoundException("Exception from getFromPath - " + fieldPath + " -- " + e);
		}
		
		return retValue;
	}
	
	/**
	 * Return the item with the specifed fieldName.
	 * @param fieldName
	 * @return object
	 */
	public Object get(String fieldName) {
		Object retValue = null;
		if (m_elements.containsKey(fieldName)) {	//Check whether the item exists
			retValue = m_elements.get(fieldName);
		}
		else {
			FieldNotFoundException e = new FieldNotFoundException("Cannot get requested item. Item not found - " + fieldName);
			throw e;
		}
		return retValue;
	}
	
	/**
	 * Set an item in the Data object. If the item is already present, the value
	 * is set to the specified new value, else the item is added to the DataObject.
	 * @param fieldName
	 * @param value
	 */
	public void set(String fieldName, Object value) {	//Check whether the item exists
		boolean blnValueChanged = false;
		if (!m_elements.containsKey(fieldName) || (m_elements.get(fieldName) == null && value == null) || (value != m_elements.get(fieldName)) || (value != null && !value.equals(m_elements.get(fieldName)))){
			blnValueChanged = true;
		}
		m_elements.put(fieldName, value);

		//Update the status only if the status is NOCHANGE
		//We will update the status only when the field is not a DataSet and not a DataObject
		//if (isContainment(fieldName)==false && isMany(fieldName)==false && m_status== NOCHANGE) {
		//	this.setStatus(MODIFIED);
		//}
		
		//Modified by paras - 17 Oct 2004, 21:03:31PM
		//It Causes problem for nested data set. As If the child data set is updated then we dont get the 
		//listener that dataset is updated. Problem in Healthcare Insurance Company Creation - Division - 
		//Branch
		if (m_status== NOCHANGE && blnValueChanged) {
			this.setStatus(MODIFIED);
		}

		//If the field is a DataSet or a DataObject, Then add
		//this object to the DataChangeListerner list..
		if (value instanceof DataChangeListenerRegistry) {
			((DataChangeListenerRegistry) value).addChangeListener(this);
		}

		this.setDirty();
	}
	
	/**
	 * Remove an item from the DataObject
	 * @param fieldName
	 */
	public void remove(String fieldName) {
		if (m_elements.containsKey(fieldName)) {	//Check whether the item exists
			m_elements.remove(fieldName);
		} 
		else {
			FieldNotFoundException e = new FieldNotFoundException("Cannot remove requested item. Item not found - " + fieldName);
			throw e;
		}
		
	}
	

	/**
	 * @return status of the Data Object.
	 */
	public int getStatus(){
		return m_status;
	}
	

	/**
	 * Set the status of the Data Object
	 * @param status
	 */
	public void setStatus(int status){
		m_status = status;
		if (status == CREATED || status == MODIFIED || status == DELETED) {
			this.setDirty();
		}
	}
	
	
	/**
	 * This method is used to mark the DataObject as deleted.
	 */
	public void delete() {
		if (m_status == CREATED) {
			m_status = JUNK;
		} else if (m_status != JUNK) {
			m_status = DELETED;
		}
		setDirty();
	}
	
	
	/**
	 * This method specifies whether the spcified Item is a DataObject.
	 * This can be used to identify many-to-one relationshsips.
	 * @param fieldName
	 * @return true if the object is DataObject.
	 */
	public boolean isContainment(String fieldName) {
		boolean result = false;
		if (m_elements.containsKey(fieldName)) {
			if (m_elements.get(fieldName) instanceof DataObject) {
				result = true;
			}
		}
		return result;
	}
	
	
	/**
	 * This method specifies whether the spcified Item is a Collection.
	 * This can be used to identify one-to-many relationshsips.
	 * @param fieldName
	 * @return true if object is DataSet.
	 */
	public boolean isMany(String fieldName) {
		boolean result = false;
		if (m_elements.containsKey(fieldName)) {
			if (m_elements.get(fieldName) instanceof DataSet) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * Returns a set view of the field names contained in this DataObject
	 * @return
	 */
	public Set getFieldNames() {
		return m_elements.keySet();
	}
	
	/**
	 * This method returns true if the fieldName and the value matches with an
	 * entry in the elements of the DataObject. Match is not allowed for DataSets
	 * or DataObjects fields.
	 * @param fieldName
	 * @param value
	 * @return true if fieldname and value matches with the object in the DataObject.
	 */
	public boolean match(String fieldName, Object value) {
		boolean result = false;
		
		//Match is not allowed if the fieldName specified is a DataSet or a DataObject..
		if (isMany(fieldName)) {
			throw new IntrobException("Field - " + fieldName + " is a DataSet. Match not allowed.");
		} else if(isContainment(fieldName)) {
			throw new IntrobException("Field - " + fieldName + " is a DataObject. Match not allowed.");
		} else if(get(fieldName).equals(value)) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * This method returns true if all the keys and their values match with the
	 * entries in the elements of the DataObject. Match is not allowed for DataSets
	 * or DataObjects fields.
	 * @param params
	 * @return true/false
	 */
	public boolean match(Map params) {
		boolean result = false;
		if (params == null) {
			throw new IntrobException("Params Map is null. Match not allowed.");
		}
		
		Set keys = params.keySet();
		Iterator iter = keys.iterator();

		//Match is not allowed if the fieldName specified is a DataSet or a DataObject..
		//We will match all the keys in the params Map
		while(iter.hasNext()) {
			String fieldName = (String) iter.next();
			if (isMany(fieldName)) {
				throw new IntrobException("Field - " + fieldName + " in the params map is a DataSet. Match not allowed.");
			} else if(isContainment(fieldName)) {
				throw new IntrobException("Field - " + fieldName + " in the params map is a DataObject. Match not allowed.");
			} else if(this.get(fieldName).equals(params.get(fieldName))) {
				result = true;	//This key match found.. now check for the next key..
			} else {
				result = false;
				//Match not found.. Now no need to loop through all the keys..
				break;
			}
		}
		return result;
	}


	/* (non-Javadoc)
	 * @see com.elbiz.introb.ObjectParent#isDirty()
	 */
	public boolean isDirty() {
		return m_isdirty;
	}


	/* (non-Javadoc)
	 * @see com.elbiz.introb.ObjectParent#setDirty(boolean)
	 */
	public void setDirty() {
		m_isdirty = true;
		this.notifyListeners();
	}
	
	
	/**
	 * This method will set the status to NOCHANGE and dirty status to false.
	 * Only works on DataObjects with status not as JUNK.
	 */
	public void reset() {
		if (m_status != JUNK) {
			m_status = NOCHANGE;
		}
		m_isdirty = false;
	}

	
	/**
	 * This will reset the DataObject and all the DataObjects and DataObjectLists
	 * held by this DataObject.
	 * Only work on DataObjects with status not as JUNK.
	 */
	public void resetGraph() {
		if (m_status != JUNK) {
			//reset this data object
			this.reset();
			
			//now reset all the dataobjects and dataobjectlists held by this
			Set keys = m_elements.keySet();
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				if (this.isContainment(key)) {
					//reset dataobject
					((DataObject) this.get(key)).resetGraph();
				} else if (this.isMany(key)) {
					//reset dataobjectlist
					((DataSet) this.get(key)).resetGraph();
				}
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see com.elbiz.introb.DataChangeListener#dataChanged()
	 */
	public void dataChanged() {
		this.notifyListeners();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String result = "DataObject";
		int status = getStatus();
		if (status == JUNK){
			result = result + "[Status - JUNK]";
		}else if (status == DELETED){
			result = result + "[Status - DELETED]";
		}else if (status == MODIFIED){
			result = result + "[Status - MODIFIED]";
		}else if (status == CREATED){
			result = result + "[Status - CREATED]";
		}else if (status == NOCHANGE){
			result = result + "[Status - NOCHANGE]";
		}
		result=result+"{";
		Set keys = m_elements.keySet();
		Iterator iter = keys.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object value = this.get(key);
			result = result + "[" + key + "=";
			if (value instanceof DataObject) {
				result = result + value + "] ";
			} else if (value instanceof DataSet) {
				result = result + "DataSet-Size-" + ((DataSet) value).size() + "] ";
			} else {
				result = result + this.get(key) + "] ";
			}
		}
		result = result + "}";
		return result;
	}
	
	/**
	 * This method will be used to create a true clone (deep copy) of DataSet.
	 */
	public Object clone(){
		return ObjectUtilities.clone(this);
	}
	
	
	/** 
	 * Methods that return a specified type object if it contains or 
	 * returns a default value or null if no default value is given. 
	 * Means it suppresses the Exception occured if data objet doesn't
	 * contain the field.
	 * 
	 * Defined By - paras
	 * Pattern applied - Normal
	 *
	 */
	public Object getObject(String strFieldName){
		return getObject(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:32:37 PM
	 * 
	 * @param strFieldName
	 * @param objDefault
	 * @return
	 */
	public Object getObject(String strFieldName, Object objDefault){
		return containsKey(strFieldName) ? get(strFieldName) : objDefault;
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:32:43 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public Boolean getBoolean(String strFieldName){
		return getBoolean(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:32:46 PM
	 * 
	 * @param strFieldName
	 * @param blnDefault
	 * @return
	 */
	public Boolean getBoolean(String strFieldName, Boolean blnDefault){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof Boolean) ? (Boolean) get(strFieldName) : blnDefault; 
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:32:50 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public String getString(String strFieldName){
		return getString(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:32:55 PM
	 * 
	 * @param strFieldName
	 * @param strDefault
	 * @return
	 */
	public String getString(String strFieldName, String strDefault){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof String) ? (String) get(strFieldName) : strDefault; 
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:32:58 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public Integer getInteger(String strFieldName){
		return getInteger(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:02 PM
	 * 
	 * @param strFieldName
	 * @param intDefault
	 * @return
	 */
	public Integer getInteger(String strFieldName, Integer intDefault){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof Integer) ? (Integer) get(strFieldName) : intDefault; 
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:05 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public Short getShort(String strFieldName){
		return getShort(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:08 PM
	 * 
	 * @param strFieldName
	 * @param srtDefault
	 * @return
	 */
	public Short getShort(String strFieldName, Short srtDefault){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof Short) ? (Short) get(strFieldName) : srtDefault; 
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:12 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public BigDecimal getBigDecimal(String strFieldName){
		return getBigDecimal(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:15 PM
	 * 
	 * @param strFieldName
	 * @param bdcDefault
	 * @return
	 */
	public BigDecimal getBigDecimal(String strFieldName, BigDecimal bdcDefault){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof BigDecimal) ? (BigDecimal) get(strFieldName) : bdcDefault; 
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:18 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public Double getDouble(String strFieldName){
		return getDouble(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:21 PM
	 * 
	 * @param strFieldName
	 * @param dblDefault
	 * @return
	 */
	public Double getDouble(String strFieldName, Double dblDefault){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof Double) ? (Double) get(strFieldName) : dblDefault; 
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:24 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public Float getFloat(String strFieldName){
		return getFloat(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:28 PM
	 * 
	 * @param strFieldName
	 * @param fltDefault
	 * @return
	 */
	public Float getFloat(String strFieldName, Float fltDefault){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof Float) ? (Float) get(strFieldName) : fltDefault; 
	}

	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:32 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public Long getLong(String strFieldName){
		return getLong(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:36 PM
	 * 
	 * @param strFieldName
	 * @param lngDefault
	 * @return
	 */
	public Long getLong(String strFieldName, Long lngDefault){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof Long) ? (Long) get(strFieldName) : lngDefault; 
	}

	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:39 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public BigInteger getBigInteger(String strFieldName){
		return getBigInteger(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:42 PM
	 * 
	 * @param strFieldName
	 * @param bntDefault
	 * @return
	 */
	public BigInteger getBigInteger(String strFieldName, BigInteger bntDefault){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof BigInteger)? (BigInteger) get(strFieldName) : bntDefault; 
	}

	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:46 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public Date getDate(String strFieldName){
		return getDate(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:49 PM
	 * 
	 * @param strFieldName
	 * @param date
	 * @return
	 */
	public Date getDate(String strFieldName, Date date){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof Date) ? (Date) get(strFieldName) : date; 
	}

	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:46 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public DataObject getDataObject(String strFieldName){
		return getDataObject(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:49 PM
	 * 
	 * @param strFieldName
	 * @param doObj
	 * @return
	 */
	public DataObject getDataObject(String strFieldName, DataObject doObj){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof DataObject) ? (DataObject) get(strFieldName) : doObj; 
	}

	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:46 PM
	 * 
	 * @param strFieldName
	 * @return
	 */
	public DataSet getDataSet(String strFieldName){
		return getDataSet(strFieldName, null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Dec 17, 2004 5:33:49 PM
	 * 
	 * @param strFieldName
	 * @param doObj
	 * @return
	 */
	public DataSet getDataSet(String strFieldName, DataSet ds){
		return containsKey(strFieldName) && (get(strFieldName) == null || get(strFieldName) instanceof DataSet) ? (DataSet) get(strFieldName) : ds; 
	}
	
	/**
	 * 
	 * @return
	 * 
	 * Created by paras - On May 28, 2005 At 7:28:17 PM
	 */
	public Map toMap(){
	   return (Map) m_elements.clone(); 
	}

	public int compareTo(DataObject o) {
	    return this.get(m_strOrderFieldname).toString().compareTo(o.get(m_strOrderFieldname).toString());
	}
	
	public void setOrderFieldName(String name){
	    m_strOrderFieldname = name;
	}
	
	public String getOrderFieldName(){
	    return m_strOrderFieldname ;
	}

}
