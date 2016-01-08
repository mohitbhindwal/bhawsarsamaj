package introb;


import static introb.IntrobQueryRunner.properties;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class IntrobSession {

    private Connection m_conn;
    private IntrobQueryRunner m_qrun;
    private boolean m_isDebugOn;
    private String m_dataSrouceName;
    private List m_tableNames; 	//List of table names being used for insert/update/delete
    private static int m_intOpenConnection = 0;
    private String m_strUserName;
    
    /**
     * Constructor
     */
    public IntrobSession() {
	m_conn = null;
	m_qrun = new IntrobQueryRunner();
	//ResourceBundle objResourceBundle = ResourceBundle.getBundle("introb");
//	m_dataSrouceName = objResourceBundle.getString("datasource");
	//m_isDebugOn = "true".startsWith(objResourceBundle.getString("debug").trim().toLowerCase());
	m_tableNames = null;
    }

    /**
     * Constructor with username argument 
     */
    public IntrobSession(String strUserName) {
	m_conn = null;
	m_qrun = new IntrobQueryRunner(strUserName);
	m_strUserName = strUserName;
         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
              Properties  properties = new Properties();
               InputStream resourceStream = null;
            try {
                
               resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("introb.properties"); 
               properties.load(resourceStream);
            } catch (IOException ex) {
              ex.printStackTrace();
            }finally{
            try {
                resourceStream.close();
            } catch (IOException ex) {
                Logger.getLogger(IntrobSession.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
 	m_dataSrouceName = properties.getProperty("datasource");
	m_isDebugOn = "true".startsWith(properties.getProperty("debug").trim().toLowerCase());
	m_tableNames = null;
    }

    
    /**
     * Open Introb Session.
     */
    public void open() throws SQLException {
	m_conn = getConnectionFromDataSource();
	System.out.println("IntrobSession OPEN. [ " + (++m_intOpenConnection) + " ]");
	if (m_isDebugOn) {
	    System.out.println("IntrobSession OPEN.");
	}
	m_tableNames = new Vector();
    }


    //This function will get a connection from the data source
    private Connection getConnectionFromDataSource() throws SQLException {
	try {
	//    Context context = new InitialContext();
	//    DataSource dataSource = (DataSource) context.lookup(ResourceBundle.getBundle("introb").getString("datasource"));
	    return ConnectionFactory.getConnection();
	} catch (Exception en) {
	    throw new IntrobException("Cannot get the DataSource from JNDI. " + en);
	}
    }

    //This function will return the connection.. to be used internally
    private Connection getConnection() throws SQLException {
	if (m_conn == null) {
	    throw new IntrobException("IntrobSession is CLOSED. Connection is Null.");
	} else if (m_conn.isClosed()){
	    throw new IntrobException("IntrobSession - Connection is closed.");
	} else {
	    m_conn.setAutoCommit(true);
	    return m_conn;
	}
    }

    /**
     * Created by jai on 4 sep 2010 
     * @return
     * @throws SQLException
     */
    //This function will get a connection metadata from the data source
    public DatabaseMetaData getDatabaseMetaData() {
	try {
	    return getConnection().getMetaData();    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
    
    //cleanup..
    protected void finalize() throws Throwable {
	try {
	    if (m_conn != null) {
		if (m_conn.isClosed()) {
		    System.out.println("IntrobSession Warning (finalize): Connection closed but not null.");
		} else {
		    System.out.println("IntrobSession Warning (finalize): OPEN connection.");
		    close();
		}
	    }
	} finally {
	    super.finalize();
	}
    }	

    /**
     * This will close the STO Session and the connection to the database.
     * @throws SQLException
     */
    public void close() throws SQLException{
	System.out.println("IntrobSession CLOSED. [ " + (--m_intOpenConnection) + " ]");
	if (m_conn != null) {
	    m_conn.close();
	    m_conn = null;
	}
	if (m_isDebugOn) {
	    System.out.println("IntrobSession CLOSED.");
	}

	//Now update the Cache used for LOV / TimeStampedRecordSet
	if (m_tableNames != null){
	  //  TimeStampedRecordSetCache.getInstance().removeByTableNames(m_tableNames);
	    m_tableNames = null;
	}
    }

    /**
     * This method is synonym to query(sql,list) but rather than list it takes 
     * array of objects as parameter if array is null or it doesn't contain the 
     * values then it behaves like query(sql) else it creates a vector and
     * calls query(sql,List)
     * 
     * Created On : - May 27, 2004 1:42:19 PM
     * Created by : - paras
     * 
     * @param sql - Sql String
     * @param obj - Array Of Objects used in sql as parameters
     * @return
     * @throws SQLException
     * 
     * 
     */
    public DataSet query(String sql, Object[] obj)
    throws SQLException {
	if (obj == null || obj.length == 0){
	    return query(sql);
	}
	return query(sql,Arrays.asList(obj));
    }

    /**
     * Execute the sql query and return the result DataSet.
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public DataSet query(String sql, List params) 
    throws SQLException {
	return m_qrun.query(this.getConnection(), sql, params);
    }


    /**
     * Execute the sql query and return the result DataSet.
     * @param sql
     * @return
     * @throws SQLException
     */
    public DataSet query(String sql) 
    throws SQLException {
	return m_qrun.query(this.getConnection(), sql);
    }


    /**
     * This method will fill a DataObject with the first record fetched by the sql query.
     * If no records are fethced, then it will mark the DataObject as deleted.
     * @param sql
     * @param params
     * @return DataObject
     * @throws SQLException
     */
    public DataObject getDataObject(String sql, List params) 
    throws SQLException {
	return m_qrun.getDataObject(this.getConnection(), sql, params);
    }


    /**
     * 
     * This method is synonym to getDataObject(sql,list) but rather than list it takes 
     * array of objects as parameter if array is null or it doesn't contain the 
     * values then it behaves like getDataObject(sql) else it creates a vector and
     * calls getDataObject(sql,List)
     * 
     * Created On : - May 27, 2004 1:47:27 PM
     * Created by : - paras
     * 
     * @param sql
     * @param obj
     * @return
     */
    public DataObject getDataObject(String sql, Object[] obj) throws SQLException{
	if (obj == null || obj.length == 0){
	    return getDataObject(sql);
	}
	return getDataObject(sql,Arrays.asList(obj));
    }
    /**
     * This method will fill a DataObject with the first record fetched by the sql query.
     * If no records are fethced, then it will mark the DataObject as deleted.
     * @param sql
     * @return DataObject
     * @throws SQLException
     */
    public DataObject getDataObject(String sql) 
    throws SQLException {
	return m_qrun.getDataObject(this.getConnection(), sql);
    }


    /**
     * This method will fill a DataSet with the records fetched by the sql query.
     * @param sql
     * @param params
     * @param ds
     * @throws SQLException
     */
    public void fillDataSet(String sql, List params, DataSet ds) 
    throws SQLException {
	m_qrun.fillDataSet(this.getConnection(), sql, params, ds);
    }


    /**
     * This method will fill a DataSet with the records fetched by the sql query.
     * @param sql
     * @param ds
     * @throws SQLException
     */
    public void fillDataSet(String sql, DataSet ds) 
    throws SQLException {
	m_qrun.fillDataSet(this.getConnection(), sql, ds);
    }


    /**
     * This method will be used to execute insert query on a DataObject.
     * It user has to provide the table name and the primary key of the table.
     * if the primary key - pk is not null then the primary key will be generated
     * using the generator class specified by the field pk.generator in the sto.properites
     * file on the classpath.
     * @param dobj
     * @param tblName
     * @param pk
     * @return
     * @throws SQLException
     */
    public int insert(DataObject dobj, String tblName, String pk) 
    throws SQLException {
	addTableName(tblName);
	return m_qrun.insert(this.getConnection(), dobj, tblName, pk);
    }

    /**
     * This method will be used to execute insert update delete queries without dataobject 
     * @param query
     * @return
     * @throws SQLException
     */
    public int insertupdatedelete(String query)throws SQLException {
	return m_qrun.insertupdatedelete(this.getConnection(), query);
    }

    /**
     * This method will be used to execute update query on a DataObject.
     * It user has to provide the table name and the primary key of the table.
     * @param dobj
     * @param tblName
     * @param pk
     * @return
     * @throws SQLException
     */
    public int update(DataObject dobj, String tblName, String pk) 
    throws SQLException {
	addTableName(tblName);
	return m_qrun.update(this.getConnection(), dobj, tblName, pk);
    }


    /**
     * This method will be used to execute delete query on a DataObject.
     * It user has to provide the table name and the primary key of the table.
     * @param dobj
     * @param tblName
     * @param pk
     * @return
     * @throws SQLException
     */
    public int delete(DataObject dobj, String tblName, String pk) 
    throws SQLException {
	addTableName(tblName);
	return m_qrun.delete(this.getConnection(), dobj, tblName, pk);
    }

    /**
     * This method will be used to execute delete query on a DataObject.
     * It user has to provide the table name and the Vector of fields .
     * @author piyush
     * @param dobj
     * @param tblName
     * @param Map
     * @return
     * @throws SQLException
     */
    public int delete(DataObject dobj, String tblName, Vector vec) 
    throws SQLException {
	addTableName(tblName);
	return m_qrun.delete(this.getConnection(), dobj, tblName, vec);
    }


    /**
     * This method will check for the status of the data object and execute
     * the insert, update or delete query accordingly..
     * @param dobj
     * @param tblName
     * @param pk
     * @return
     * @throws SQLException
     */
    public int execute(DataObject dobj, String tblName, String pk) 
    throws SQLException {
	addTableName(tblName);
	return m_qrun.execute(this.getConnection(), dobj, tblName, pk);
    }

    /**
     * created by jai on 13-11-2009 to use SQL DDL commandsc 
     * @param sql
     * @param blnAutoCommit
     * @return
     * @throws SQLException
     */
    public boolean execute(String sql, boolean blnAutoCommit) throws SQLException {
	this.getConnection().setAutoCommit(blnAutoCommit);
	return m_qrun.execute(this.getConnection(), sql);
    }

    /**
     * created by jai on 16-11-2009 to use SQL Batch Functionality 
     * @param sql
     * @throws SQLException
     */
    public void addBatch(String sql) throws SQLException{
	m_qrun.addBatch(this.getConnection(), sql);
    }

    /**
     * created by jai on 16-11-2009 to use SQL Batch Functionality 
     * @return
     * @throws SQLException
     */
    public int[] executeBatch() throws SQLException{
	return m_qrun.executeBatch();
    }

    /**
     * created by jai on 13-11-2009 to use SQL Function calling without any return value 
     * 
     * @param sql
     * @param blnAutoCommit
     * @param listParameters
     * @return
     * @throws SQLException
     */
    public boolean executeFunctionCall(String sql, boolean blnAutoCommit, List listParameters) throws SQLException {
	this.getConnection().setAutoCommit(blnAutoCommit);
	return m_qrun.executeFunctionCall(this.getConnection(), sql, listParameters);
    }

    /**
     * created by jai on 13-11-2009 to use SQL SELECT commands not based on DO DS
     * @param sql
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql) throws SQLException {
	return m_qrun.executeQuery(this.getConnection(), sql);
    }

    /**
     * created by jai on 13-11-2009 to use SQL SELECT commands formated result as array list
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public ArrayList getArrayListFromResultSet(String sql)throws SQLException {
	return m_qrun.getArrayListFromResultSet(this.getConnection(), sql);
    }

    /**
     * created by jai on 13-11-2009 to control autocommit featue 
     * @throws SQLException
     */
    public void commit() throws SQLException{
	System.out.println("IntrobSession COMMIT.");
	m_conn.commit();
	if (m_isDebugOn) {
	    System.out.println("IntrobSession COMMIT.");
	}
    }

    /**
     * created by jai on 16-11-2009 to control connection rollback featue
     * @throws SQLException
     */
    public void rollback() throws SQLException {
	System.out.println("IntrobSession ROLLBACK.");
	m_conn.rollback();
	if (m_isDebugOn) {
	    System.out.println("IntrobSession ROLLBACK.");
	}
    }

    /**
     * This method will check for the status of each DataObject in the
     * DataSet and execute the insert, update or delete query accordingly..
     * @param ds
     * @param tblName
     * @param pk
     * @return
     * @throws SQLException
     */
    public int executeAll(DataSet ds, String tblName, String pk) 
    throws SQLException {
	addTableName(tblName);
	return m_qrun.executeAll(this.getConnection(), ds, tblName, pk);
    }


    /**
     * This method will delete all the records specified in the table based
     * on the fieldname and value.
     * @param tblName
     * @param fieldName
     * @param value
     * @return
     * @throws SQLException
     */
    public int delete(String tblName, String fieldName, Object value) 
    throws SQLException {
	addTableName(tblName);
	return m_qrun.delete(this.getConnection(), tblName, fieldName, value);
    }


    /**
     * This will return a TimeStampedRecordSet
     * @param query
     * @param params
     * @param tableNames        - Name of the tables used to build this TimeStampedRecordSet
     * @param isFirstRecordNull
     * @return
     * @throws SQLException
     */
    public TimeStampedRecordSet getTimeStampedRecordSet(String query, List params, String tableNames, boolean isFirstRecordNull) 
    throws SQLException {
	return m_qrun.getTimeStampedRecordSet(this.getConnection(), query, params, tableNames, isFirstRecordNull);
    }

    /**
     * This will return a TimeStampedRecordSet
     * @param query
     * @param isFirstRecordNull
     * @param tableNames        - Name of the tables used to build this TimeStampedRecordSet
     * @return
     * @throws SQLException
     */
    public TimeStampedRecordSet getTimeStampedRecordSet(String query, String tableNames, boolean isFirstRecordNull) 
    throws SQLException {
	return m_qrun.getTimeStampedRecordSet(this.getConnection(), query, null, tableNames, isFirstRecordNull);
    }


    //	add this table to the list of tables for insert/update/delete. will be used to update LOV cache	
    private void addTableName(String tblName) {
	if (m_tableNames.contains(tblName) == false) {
	    m_tableNames.add(tblName);	
	}
    }

    /**
     * Returns The DataObject whose given value is equal to the value of field "fid"
     * If Your primary key is not named "fid" then please use 
     * getRecordsByField()
     * 
     * Created On : - Jun 24, 2004 12:28:41 PM
     * Created by : - paras
     * 
     * @param strTableName
     * @param objFieldValue
     * @return
     * @throws SQLException
     * 
     */
    public DataObject getByPrimaryKey(String strTableName, Object objFieldValue) throws SQLException{
	return getRecordsByField(strTableName,"fid",objFieldValue).get(0);
    }

    /**
     * Returns The DataObject whose given value is equal to the value of 
     * given fieldname
     * 
     * Created On : - Jun 24, 2004 12:30:27 PM
     * Created by : - paras
     * 
     * @param strTableName
     * @param strFieldName
     * @param objFieldValue
     * @return
     * @throws SQLException
     */
    public DataSet getRecordsByField(String strTableName, String strFieldName, Object objFieldValue) throws SQLException{
	return getRecordsByFields(strTableName, new String[]{strFieldName},new Object[]{objFieldValue},null);
    }

    /**
     * 
     * Created By - paras
     * Created On - Feb 23, 2005 3:36:34 PM
     * 
     * @param strTableName
     * @param strFieldName
     * @param objFieldValue
     * @param strOrderingField
     * @return
     * @throws SQLException
     */
    public DataSet getRecordsByField(String strTableName, String strFieldName, Object objFieldValue, String strOrderingField) throws SQLException{
	return getRecordsByFields(strTableName, new String[]{strFieldName},new Object[]{objFieldValue}, new String[]{strOrderingField});
    }

    /**
     * 
     * Created By - paras
     * Created On - Feb 23, 2005 3:36:38 PM
     * 
     * @param strTableName
     * @param strFieldName
     * @param objFieldValue
     * @param strOrderingFields
     * @return
     * @throws SQLException
     */
    public DataSet getRecordsByField(String strTableName, String strFieldName, Object objFieldValue, String[] strOrderingFields) throws SQLException{
	return getRecordsByFields(strTableName, new String[]{strFieldName},new Object[]{objFieldValue}, strOrderingFields);
    }

    /**
     * 
     * Created By - paras
     * Created On - Feb 23, 2005 3:37:54 PM
     * 
     * @param strTableName
     * @param strFieldNames
     * @param objFieldValues
     * @return
     * @throws SQLException
     */
    public DataSet getRecordsByFields(String strTableName, String[] strFieldNames, Object[] objFieldValues) throws SQLException{
	return getRecordsByFields(strTableName, strFieldNames,objFieldValues, null);
    }

    /**
     * Returns The DataObject whose given values is equal to the values of 
     * given fieldnames
     * 
     * Created On : - Jun 24, 2004 12:31:04 PM
     * Created by : - paras
     * 
     * @param strTableName
     * @param fields
     * @param fieldValues
     * @param orderingFields
     * @return
     * @throws SQLException
     */
    public DataSet getRecordsByFields(String strTableName, String[] strFieldNames, Object[] objFieldValues, String[] strOrderingFieldNames) throws SQLException{
	StringBuffer strQuery = new StringBuffer();
	if (strTableName.toLowerCase().indexOf("select ") >= 0 && strTableName.toLowerCase().indexOf(" from ") > 0){
	    strQuery.append(strTableName);
	}else{
	    strQuery.append("SELECT * FROM ");
	    strQuery.append(strTableName);
	}

	strQuery.append(' ');
	Vector vecFieldValues = new Vector();
	if (strFieldNames != null && strFieldNames.length > 0){
	    if (objFieldValues == null){
		objFieldValues = new Object[]{};
	    }

	    strQuery.append(" WHERE ");
	    for(int i=0; i < strFieldNames.length; i++){
		strQuery.append(strFieldNames[i]);
		if (objFieldValues == null || i >= objFieldValues.length || objFieldValues[i] == null){
		    strQuery.append(" is null ");
		}else if (objFieldValues[i] instanceof Number){
		    strQuery.append(" = ");
		    strQuery.append(objFieldValues[i]);
		    strQuery.append(' ');
		}else{
		    strQuery.append(" = ? ");
		    vecFieldValues.add(objFieldValues[i]);
		}
		if (i < strFieldNames.length - 1){
		    strQuery.append(" and ");
		}
	    }
	}

	if (strOrderingFieldNames != null && strOrderingFieldNames.length > 0){
	    strQuery.append(" ORDER BY ");
	    for(int i=0; i < strOrderingFieldNames.length; i++){
		strQuery.append(strOrderingFieldNames[i]);
		if (i < strOrderingFieldNames.length - 1){
		    strQuery.append(", ");
		}
	    }
	}

	return query(strQuery.toString(), vecFieldValues);
    }
    /**
    * By Chandrakant for procedure calling 
    */
    public CallableStatement prepareCall(String procName){
	
	CallableStatement callableStatement= null; 
	try {
	    callableStatement = getConnection().prepareCall(procName);
	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	return callableStatement;
    }
}
