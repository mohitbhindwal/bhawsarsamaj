package introb;

/*
 * Created on Apr 9, 2004 - Hitesh Bagadiya
 *
 * Copyright (c) 2003-04 Elbiz Systems Pvt. Ltd.
 * Indore, MP, India
 *
 * This software is the confidential and proprietary information of
 * Elbiz Systems Pvt. Ltd. You shall use this software only in
 * accordance with the terms of agreement with Elbiz Systems Pvt. Ltd.
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 * @author hitesh
 *
 * DataSet class. This corresponds to a List of Hierarchical records.
 */
public class DataSet extends DataChangeListenerRegistry implements DataChangeListener, Serializable {
	
	protected Vector m_dataobjects;
	protected boolean m_isdirty;
	
	
	/**
	 * Default Constructor.
	 */
	public DataSet() {
		m_isdirty = false;
		m_dataobjects = new Vector();
	}
	

	/**
	 * Constructor used for one-to-many relationships.
	 * @param dobj
	 * @deprecated
	 */
	public DataSet(DataObject dobj) {
		m_isdirty = false;
		m_dataobjects = new Vector();
		addChangeListener(dobj);
	}

	
	/**
	 * This method will create a DataObject and add it to its List.
	 * @return newly created DataObject
	 * @deprecated
	 */
	public DataObject createDataObject() {
		DataObject d = new DataObject(this);
		m_dataobjects.add(d);
		return d;
		//We will not notify the listeners about the adding the newly 
		//created DataObject. They will be automatically notified when 
		//any field of the created DataObject will be set.
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Sep 29, 2004 11:47:00 AM
	 * 
	 * @return - The Newly Created DataObject
	 */
	public DataObject addDataObject() {
		return addDataObject(null);
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Sep 29, 2004 11:47:33 AM
	 * 
	 * This method will add a DataObject to its List.
	 * @param dobj - DataObject to be added. If it is null then a new data object created implicitly.
	 * @return - First Check that it is in dataset or not if not then added else The dobj if it is not null else the new instance of DataObject that is created.
	 * 
	 */
	public DataObject addDataObject(DataObject dobj) {
		if (indexOf(dobj) != -1){
			System.out.println("Data Object is allready added at position - " + indexOf(dobj));
			return dobj;
		}
		if (dobj == null){
			dobj = new DataObject();
		}
		dobj.addChangeListener(this);
		m_dataobjects.add(dobj);
		return dobj;
	}
	
	/**
	 * 
	 * Created By - paras
	 * Created On - Feb 25, 2005 3:04:14 PM
	 * 
	 * @param dobj
	 * @return position of data object in dataset return -1 if not found
	 */
	public int indexOf(DataObject dobj) {
		if (dobj == null){
			return -1;
		}
		return m_dataobjects.indexOf(dobj);
	}
	
	
	/**
	 * This method is used to remove DataObject from the data set.
	 * @param dobj - DataObject to be removed
	 */
	public void removeDataObject(DataObject dobj) {
		m_dataobjects.remove(dobj);
	}

	/**
	 * This method returns the DataObject at a particular index.
	 * @param i
	 * @return DataObject at a index i.
	 */
	public DataObject get(int i){
		return (DataObject) m_dataobjects.get(i);
	}
		
	/**
	 * This method returns the list of all the DataObjects that have been
	 * created, modified or deleted. 
	 * @return List of dirty DataObjects.
	 */
	public List getDirtyDataObjects(){
		Vector vec = new Vector();
		for (int i=0; i< m_dataobjects.size(); i++) {
			DataObject d = (DataObject) m_dataobjects.get(i);
			if (d.isDirty()) {
				vec.add(d);
			}
		}
		return vec;
	}

	
	/**
	 * This method returns the list of all the DataObjects 
	 * with the specified status.
	 * @return List of DataObjects filtered by status.
	 */
	public List getDataObjects(int status){
		Vector vec = new Vector();
		for (int i=0; i< m_dataobjects.size(); i++) {
			DataObject d = (DataObject) m_dataobjects.get(i);
			if (d.getStatus() == status) {
				vec.add(d);
			}
		}
		return vec;
	}
	
	
	/**
	 * This method returns the list of all the DataObjects 
	 * with the that are not deleted.
	 * @return List of DataObjects with status NOCHANGE, CREATED and MODIFIED
	 */
	public List getVisibleDataObjects(){
		Vector vec = new Vector();
		for (int i=0; i< m_dataobjects.size(); i++) {
			DataObject d = (DataObject) m_dataobjects.get(i);
			if (d.getStatus() == DataObject.NOCHANGE || d.getStatus() == DataObject.CREATED || d.getStatus() == DataObject.MODIFIED) {
				vec.add(d);
			}
		}
		return vec;
	}
	
	
	/**
	 * Returns the number of DataObjects in the DataSet
	 * @return number of DataObjects in the DataSet.
	 */
	public int size() {
		return m_dataobjects.size();
	}
	
	
	/**
	 * This method will be used to create a true clone (deep copy) of DataSet.
	 */
	public Object clone(){
		return ObjectUtilities.clone(this);
	}
	
	
	/**
	 * This method will return a List of Data Objects that match the
	 * filter criteria provided based on the fieldName and value.
	 * @param fieldName
	 * @param value
	 * @return filtered List of DataObjects
	 */
	public List filterDataObjects(String fieldName, Object value) {
		Vector result = new Vector();
		for (int i=0; i < m_dataobjects.size(); i++) {
			if(((DataObject)m_dataobjects.get(i)).match(fieldName, value)) {
				result.add(m_dataobjects.get(i));
			}
		}
		return result;
	}
	
	/**
	 * This method will return a List of Data Objects that match the
	 * filter criteria provided by the Map of fieldNames and 
	 * corresponding values.
	 * @param params
	 * @return filtered List of DataObjects
	 */
	public List filterDataObjects(Map params) {
		Vector result = new Vector();
		for (int i=0; i < m_dataobjects.size(); i++) {
			if(((DataObject)m_dataobjects.get(i)).match(params)) {
				result.add(m_dataobjects.get(i));
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
	
	public int getUndeletedDataObjectCount(){
		int undeletedcount = 0;
		DataSet ds = this;
		for (int i = 0; i < ds.size(); i++) {
			if (ds.get(i).getStatus() == DataObject.DELETED || ds.get(i).getStatus() == DataObject.JUNK) {
				continue;
			}
			undeletedcount++;
		}
		return undeletedcount;
	}
	
	/**
	 * This will call reset() for all the DataObjects in the list.
	 */
	public void reset() {
		m_isdirty = false;
		for (int i=0; i<m_dataobjects.size(); i++) {
			((DataObject) m_dataobjects.get(i)).reset();
		}
	}

	/**
	 * This will call resetGraph() for all the DataObjects in the list.
	 */
	public void resetGraph() {
		m_isdirty = false;
		for (int i=0; i<m_dataobjects.size(); i++) {
			((DataObject) m_dataobjects.get(i)).resetGraph();
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
		String result = "DataSet - Size " + this.size() + "\n";
		for (int i=0; i < this.size(); i++) {
			result = result + this.get(i) + "\n";
		}
		  Iterator i =       this.iterator();
	
		  
		
		return result;
	}
	
	/**
	 * Methods Added to make compatible the DataSet with Table And ComboBox.
	 * Created By - paras
	 * Created On - Sep 30, 2004 11:56:15 AM
	 * 
	 * @param objFields
	 * @return
	 */
	public List getList(List objFields){
		return getList(objFields.toArray());
	}
		
	public List getList(Object[] objFields){
		return getList(objFields, null, false, false);
	}
	
	public List getList(List objFields, boolean blnWantMap){
		return getList(objFields.toArray(), blnWantMap);
	}
	
	public List getList(Object[] objFields, boolean blnWantMap){
		return getList(objFields, null, blnWantMap, false);
	}

	public List getListOfMaps(List objFields){
		return getListOfMaps(objFields.toArray());
	}
	
	public List getListOfMaps(Object[] objFields){
		return getList(objFields, null, true, false);
	}

	public List getListOffMaps(List objFields, List objFieldNames){
		return getList(objFields.toArray(), objFieldNames.toArray(), true, false);
	}

	public List getList(List objFields, List objFieldNames, boolean blnWantMap){
		return getList(objFields.toArray(), objFieldNames.toArray(), blnWantMap, false);
	}
	
	public List getList(List objFields, List objFieldNames, boolean blnWantMap, boolean blnIncludeJunkAndDeleted){
		return getList(objFields.toArray(), objFieldNames.toArray(), blnWantMap, blnIncludeJunkAndDeleted);
	}
	
	public List getListOffMaps(Object[] objFields, Object objFieldNames[]){
		return getList(objFields, objFieldNames, true, false);
	}

	public List getList(Object[] objFields, Object objFieldNames[], boolean blnWantMap){
		return getList(objFields, objFieldNames, blnWantMap, false);
	}
	
	public List getList(Object[] objFields, Object objFieldNames[], boolean blnWantMap, boolean blnIncludeJunkAndDeleted){
		if (objFields == null){
			return null;
		}
		Vector vecData = new Vector();
		for (int i = 0; i < size(); i++) {
			DataObject dataObject = get(i);
			if ((dataObject.getStatus() == DataObject.DELETED || dataObject.getStatus() == DataObject.JUNK) && !blnIncludeJunkAndDeleted) {
				continue;
			}
			Object objRow = null;
			objRow = blnWantMap ? (Object) new HashMap() : (Object) new Vector();
			for(int j = 0;j < objFields.length; j++){
				String strFieldName = (String) objFields[j];
				Object objValue = null;
				if (dataObject.containsKey(strFieldName)){
					objValue = dataObject.get(strFieldName);
				}
				if (objFieldNames != null && objFieldNames.length > j){
					strFieldName = (String) objFieldNames[j];
				}
				if (blnWantMap){
					((HashMap)objRow).put(strFieldName, objValue);
				}else{
					((Vector)objRow).add(objValue);
				}
			}
			vecData.add(objRow);
		}
		return vecData;
	}

	public static DataSet convertListOfMapsToDataSet(List lstMaps) {
		DataSet dataset = new DataSet();
		for(int i=0; i < lstMaps.size(); i++){
			DataObject dataobject = dataset.addDataObject();
			Iterator itrEntries = ((Map) lstMaps.get(i)).entrySet().iterator();
			while (itrEntries.hasNext()){
				Map.Entry entry = (Map.Entry) itrEntries.next();
				dataobject.set(entry.getKey().toString(), entry.getValue());
			}
			dataobject.reset();
		}
		return dataset; 
	}



    public Iterator iterator() {
        return m_dataobjects.iterator();
    }
    
}
