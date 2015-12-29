package introb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * @author hitesh
 *
 * This is a timestamped record set class.
 */
public class TimeStampedRecordSet implements Serializable {
	
	private Date m_timeStamp = null;
	private List m_listItems = null;
	private boolean m_isFirstRecordNull = false;
	private List m_tableNames = null;

	
	/**
	 * Constructor.
	 * @param isFirstRecordNull
	 */
	public TimeStampedRecordSet(boolean isFirstRecordNull) {
		m_isFirstRecordNull = isFirstRecordNull;
		m_timeStamp = new Date();
		m_listItems = new ArrayList();
		if (isFirstRecordNull == true) {
			m_listItems.add(null);
		}
		m_tableNames = new ArrayList();
	}
	
	/**
	 * @return The list containing the name of the tables used to build this TimeStampedRecordset
	 */
	public List getTableNames() {
		return m_tableNames;
	}
	
	/**
	 * This Table will set the tables specified to the list of the tables used to build this TimeStampedRecordset.
	 * @param tableName		Name of tables (comma separated) (generally we will get this by parsing the SQL query)
	 */
	public void setTableNames(String tableNames) {
		m_tableNames = new ArrayList();
		if (tableNames != null) {
	        StringTokenizer tokenizer = new StringTokenizer(tableNames, " ,");
	        while (tokenizer.hasMoreTokens()) {
	        	m_tableNames.add(tokenizer.nextToken().trim());
	        }
		}
	}
	
	/**
	 * Add object to the record set.
	 * @param record
	 */
	public void addRecord(Object record) {
		m_listItems.add(record);
	}
	
	/**
	 * add item to the record set.
	 * @param record
	 */
	public void deleteRecord(Object record) {
		m_listItems.remove(record);
	}
	

	/**
	 * returns the record at index i
	 * @param i
	 * @return record at index i.
	 */
	public Object getRecord(int index) {
		return m_listItems.get(index);
	}
	
	/**
	 * Return the time stamp.
	 * @return timestamp
	 */
	public Date getTimeStamp() {
		return m_timeStamp;
	}

	/**
	 * Returns the List
	 * @return List
	 */
	public List getList() {
		return m_listItems;
	}
	

	/**
	 * This method will work only if all the items in the RecordSet are of type java.util.Map
	 * This will filter the records based on the field name and the value specified.
	 * @param fieldName
	 * @param value
	 * @return Filtered list of Maps
	 */
	public List getFilteredList(String fieldName, Object value) {
		List result = new ArrayList();
		for (int i=0; i<m_listItems.size(); i++) {
			Object item = m_listItems.get(i);
			try {
				if (((Map) item).get(fieldName).equals(value)) {
					result.add(item);
				}
			} catch (Exception e) {
				//Do Nothing... We don't want this record in the filtered records..
			}
		}
		return result;
	}
	

	/**
	 * This method will work only if all the items in the RecordSet are of type java.util.Map
	 * This will filter the records based on the field name and the value specified. if exactMatch
	 * is false then it will check wheter the data in the recordset starts with the string specified
	 * in the value.
	 * @param fieldName
	 * @param value
	 * @param numberOfRecords
	 * @param exactMatch 
	 * @return Filtered List of records
	 */
	public List getFilteredList(String fieldName, String value, int numberOfRecords, boolean exactMatch) {
		List result = new ArrayList();
		for (int i=0; i<m_listItems.size(); i++) {
			//check whether we have already reached the number of records asked for..
			if (result.size() == numberOfRecords) {
				break;		//we don't need more records..
			}

			Object item = m_listItems.get(i);
			try {
				String strRecordFieldValue = (String) ((Map) item).get(fieldName);

				//check if we need exact match..
				if (exactMatch == true) {
					if (strRecordFieldValue.equals(value)) {
						result.add(item);
					}
				} else { //We don't need exact match..
					if (strRecordFieldValue.indexOf(value) == 0) {
						result.add(item);
					}
				}
				
			} catch (Exception e) {
				//Do Nothing... We don't want this record in the filtered records..
			}
		}
		return result;
	}
	
	/**
	 * This method will work only if all the items in the RecordSet are of type java.util.Map
	 * This will filter the records based on the field name and the value specified. if exactMatch
	 * is false then it will check wheter the data in the recordset starts with the string specified
	 * in the value
	 * @param fieldName
	 * @param value
	 * @return Filtered List of records
	 */
	public List getFilteredListMultipleValues(String fieldName, Object[] value) {
		List result = new ArrayList();
		for (int i=0; i<m_listItems.size(); i++) {
			//check whether we have already reached the number of records asked for..

			Object item = m_listItems.get(i);
			try {
				
				//check if we need exact match..
			   	for(int j=0;j<value.length;j++)
			   	{   	
				    //  System.out.println(value[j]);
			   	    if (((Map) item).get(fieldName).equals(value[j])) {
						result.add(item);
					}
			   	}
			} catch (Exception e) {
				//Do Nothing... We don't want this record in the filtered records..
			}
		}
		return result;
	}	
	
	/**
	 * This method will work only if all the items in the RecordSet are of type java.util.Map
	 * This will filter the records based on the field name and the value specified in the Map . if exactMatch
	 * is false then it will check wheter the data in the recordset starts with the string specified
	 * in the value
	 * @param hmp
	 * @return Filtered List of records
	 */
	public List getFilteredList(Map hmp) {
		List result = new ArrayList();
		for (int i=0; i<m_listItems.size(); i++) {
			Object item = m_listItems.get(i);
			try {
				
			 Iterator itr=	hmp.keySet().iterator();
			 boolean status=true;
			 while(itr.hasNext())
			 {     String fieldname= itr.next().toString();
			  	    if (((Map) item).get(fieldname).equals(hmp.get(fieldname))) {
					    continue;	
					}else
					{     status=false;
					    break;   
					}
			 }     
			 if(status)
			 result.add(item);
			} catch (Exception e) {
              e.printStackTrace();
			}
		}
		return result;
	}	
	
	/**
	 * Returns the size of the record set.
	 * @return number of records.
	 */
	public int size() {
		return m_listItems.size();
	}
}
