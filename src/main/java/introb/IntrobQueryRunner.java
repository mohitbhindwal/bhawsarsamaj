package introb;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
public class IntrobQueryRunner {
	private boolean m_isDebugOn;
	private boolean m_blnConvertTimeStampToUtilDate = true;
	private Statement m_stmt = null;
	private String m_strUserName;
        public static Properties properties =null;
	
	public IntrobQueryRunner() {
	//	ResourceBundle objResourceBundle = ResourceBundle.getBundle("introb");
	//	m_isDebugOn = "true".startsWith(objResourceBundle.getString("debug").trim().toLowerCase());
	//	m_blnConvertTimeStampToUtilDate = "true".startsWith(objResourceBundle.getString("converttimestamptoutildate").trim().toLowerCase());
	}

	public IntrobQueryRunner(String strUserName) {
		m_strUserName = strUserName;
              
                properties = new Properties();
               InputStream resourceStream = null;
             
            try {
           resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("introb.properties"); 
               properties.load(resourceStream);
           //  System.out.println("introb.IntrobQueryRunner.<init>11111()"+IntrobQueryRunner.class.getResourceAsStream(".").toString());
            } catch (Exception ex) {
                Logger.getLogger(IntrobQueryRunner.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                    try {
                        resourceStream.close();
                    } catch (IOException ex) {
                        Logger.getLogger(IntrobQueryRunner.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
                
		//ResourceBundle objResourceBundle = ResourceBundle.getBundle("E:\\mohit\\bhawsarsamaj\\src\\java\\introb\\introb");
		m_isDebugOn = "true".startsWith(properties.get("debug").toString().trim().toLowerCase());
		//m_blnConvertTimeStampToUtilDate = "true".startsWith(properties.get("converttimestamptoutildate").toString().trim().toLowerCase());
	}

	
    /**
     * Fill the <code>PreparedStatement</code> replacement parameters with 
     * the given objects.
     * @param stmt
     * @param params Query replacement parameters; <code>null</code> is a valid
     * value to pass in.
     * @throws SQLException
     */	protected void fillStatement(PreparedStatement stmt, List params)
			throws SQLException {
		if (params == null) {
			return;
		}
		for (int i = 0; i < params.size(); i++) {
		    Object obj = params.get(i);
		    if (m_blnConvertTimeStampToUtilDate && obj instanceof Date){
			obj = new Timestamp(((Date)obj).getTime());
                        stmt.setTimestamp(i+1,(Timestamp)obj);
                        System.out.println("introb.IntrobQueryRunner.fillStatement()@@@@@@@@"+obj);
		    }else if (obj instanceof Date){
			obj = (Date) obj;
			stmt.setObject(i + 1, obj, Types.DATE);	
                        System.out.println("introb.IntrobQueryRunner.fillStatement()#####"+obj);
		    }
		    else if (obj instanceof String ) {
			if(!obj.toString().contains("$$"))
			    obj = obj.toString().replace("'", "''");
			stmt.setObject(i + 1, obj, Types.VARCHAR );
		    }
		    else if (obj != null) {
			stmt.setObject(i + 1, obj);
		    }
		    else {
			stmt.setNull(i + 1, Types.OTHER);
		    }
		}
	}


     /**
      * Execute the sql query and return the results.
      * @param conn
      * @param sql
      * @param params
      * @param rsh
      * @return newly created DataSet
      * @throws SQLException
      */
	public DataSet query(Connection conn, String sql, List params) 
			throws SQLException {
		
		//debug messages
		if (m_isDebugOn) {
			logMessage(getSQLQueryWithParams(sql, params));
		}
		
		DataSet result = new DataSet();
		fillDataSet(conn, sql, params, result);
		return result;
	}


	/**
	 * Execute query with no parameters
	 * @param conn
	 * @param sql
      * @return newly created DataSet
	 * @throws SQLException
	 */
	public DataSet query(Connection conn, String sql) throws SQLException {
		return this.query(conn, sql, null);
	}


	/**
	 * This method will fill a DataObject with the first record fetched by the sql query.
	 * If no records are fethced, then it will mark the DataObject as JUNK.
	 * @param conn
	 * @param sql
	 * @param params
	 * @return DataObject
	 * @throws SQLException
	 */
	public DataObject getDataObject(Connection conn, String sql, List params)
			throws SQLException {
		DataObject result = new DataObject(); //status is CREATED
		
		//debug messages
		if (m_isDebugOn) {
			logMessage(getSQLQueryWithParams(sql, params));
		}
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			if (params != null && params.size() > 0){
				stmt = conn.prepareStatement(sql);
				this.fillStatement((PreparedStatement)stmt, params);
				rs = ((PreparedStatement)stmt).executeQuery();
			}else{
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			}
			
			if (rs.next()) {
	            ResultSetMetaData rsmd = rs.getMetaData();
	            int cols = rsmd.getColumnCount();

	            for (int i = 1; i <= cols; i++) {
					Object obj = rs.getObject(i);
					if (m_blnConvertTimeStampToUtilDate && obj instanceof Timestamp){
						obj = new Date(((Timestamp)obj).getTime());
					}
	                result.set(rsmd.getColumnName(i), obj);
	            }
	            result.reset();	//status set to NOCHANGE
	        } else {
	        	//No records found. Mark this DataObject as JUNK.
	        	result.delete();	//status set to JUNK
	        }
		} catch (SQLException e) {
			this.rethrow(e, sql, params, "QUERY: ");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return result;
	}

	/**
	 * This method will fill a DataObject with the first record fetched by the sql query.
	 * If no records are fethced, then it will set the DataObject as deleted.
	 * @param conn
	 * @param sql
	 * @return DataObject
	 * @throws SQLException
	 */
	public DataObject getDataObject(Connection conn, String sql) 
			throws SQLException {
		return getDataObject(conn, sql, null);
	}

	
	/**
	 * This method will fill a DataSet with the records fetched by the sql query.
	 * @param conn
	 * @param sql
	 * @param params
	 * @param ds
	 * @throws SQLException
	 */
	public void fillDataSet(Connection conn, String sql, List params, DataSet ds) 
			throws SQLException {
		
		//debug messages
		if (m_isDebugOn) {
			logMessage(getSQLQueryWithParams(sql, params));
		}
		
		ResultSet rs = null;
		Statement stmt = null;
		try {
			if (params != null && params.size() > 0){
				stmt = conn.prepareStatement(sql);
				this.fillStatement((PreparedStatement)stmt, params);
				rs = ((PreparedStatement)stmt).executeQuery();
			}else{
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			}
			
	        while (rs.next()) {
	        	DataObject dobj = new DataObject();
	            ResultSetMetaData rsmd = rs.getMetaData();
	            int cols = rsmd.getColumnCount();

	            for (int i = 1; i <= cols; i++) {
	            	Object obj = rs.getObject(i);
	            	if (m_blnConvertTimeStampToUtilDate && obj instanceof Timestamp){
	            		obj = new Date(((Timestamp)obj).getTime());
	            	}
	                dobj.set(rsmd.getColumnName(i), obj);
	            }
	            ds.addDataObject(dobj);
	        }
	        ds.reset();
		} catch (SQLException e) {
			this.rethrow(e, sql, params, "QUERY: ");
		} finally {
			close(rs);
			close(stmt);
		}
	}
	
	/**
	 * This method will fill a DataSet with the records fetched by the sql query.
	 * @param conn
	 * @param sql
	 * @param ds
	 * @throws SQLException
	 */
	public void fillDataSet(Connection conn, String sql, DataSet ds) 
			throws SQLException {
		fillDataSet(conn, sql, null, ds);
	}
	

	/**
	 * This method will return a TimeStampedRecordSet filled with the records fetched by the sql query.
	 * @param conn           	- Connection to database
	 * @param sql               - SQL query
	 * @param params            - parameter for sql query
	 * @param tableNames        - Name of the tables used to build this TimeStampedRecordSet
	 * @param isFirstRecordNull - flag to determine whether the first record should be null
	 * @return                  - timestamped recordset
	 * @throws SQLException
	 */
	public TimeStampedRecordSet getTimeStampedRecordSet(Connection conn, String sql, List params, String tableNames, boolean isFirstRecordNull) 
		throws SQLException {
		TimeStampedRecordSet rors = new TimeStampedRecordSet(isFirstRecordNull);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			if (params != null && params.size() > 0){
				stmt = conn.prepareStatement(sql);
				this.fillStatement((PreparedStatement)stmt, params);
				rs = ((PreparedStatement)stmt).executeQuery();
			}else{
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			}
			
		    while (rs.next()) {
		    	Map map = new HashMap();
		        ResultSetMetaData rsmd = rs.getMetaData();
		        int cols = rsmd.getColumnCount();
		
		        for (int i = 1; i <= cols; i++) {
					Object obj = rs.getObject(i);
					if (m_blnConvertTimeStampToUtilDate && obj instanceof Timestamp){
						obj = new Date(((Timestamp)obj).getTime());
					}
		            map.put(rsmd.getColumnName(i), obj);
		        }
		        //Now add this map to the TimeStampedRecordSet
		        rors.addRecord(map);
		    }
		} catch (SQLException e) {
			this.rethrow(e, sql, params, "QUERY: ");
		} finally {
			close(rs);
			close(stmt);
		}
		
		rors.setTableNames(tableNames);		//set the table names
		return rors;
	}
	

	/**
	 * This method will return a TimeStampedRecordSet filled with the records fetched by the sql query.
	 * @param conn           	- Connection to database
	 * @param sql               - SQL query
	 * @param tableNames        - Name of the tables used to build this TimeStampedRecordSet
	 * @param isFirstRecordNull - flag to determine whether the first record should be null
	 * @return                  - timestamped recordset
	 * @throws SQLException
	 */
	public TimeStampedRecordSet getTimeStampedRecordSet(Connection conn, String sql, String tableNames, boolean isFirstRecordNull) 
		throws SQLException {
		
		return this.getTimeStampedRecordSet(conn, sql, null, tableNames, isFirstRecordNull);
	}
	

	/**
	 * Close a <code>ResultSet</code>, avoid closing if null.
	 * @param rs
	 * @throws SQLException
	 */
	public void close(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}


	/**
	 * Close a <code>Statement</code>, avoid closing if null.
	 */
	public void close(Statement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}

	
	/**
	 * This method will be used to execute insert query on a DataObject.
	 * It user has to provide the table name and the primary key of the table.
	 * if the primary key - pk is not null then the primary key will be generated
	 * using the generator class specified by the field pk.generator in the sto.properites
	 * file on the classpath.
	 * @param conn
	 * @param dobj
	 * @param tblName
	 * @param pk
	 * @return number of recrods inserted
	 */
	public int insert(Connection conn, DataObject dobj, String tblName, String pk) 
			throws SQLException {
		String strSQL = null;
		Vector params = new Vector();
		int rows = 0;
           
		//When pk is not null we will generate the primary key..
		if (pk != null) {
			KeyGenerator generator = null;
			try {
				//we will get the generator class from sto.properties on the classpath
				generator = (KeyGenerator) Class.forName("introb.PostgresqlSequence").newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				throw new IntrobQueryException("Cannot instantiate primary key generator. " + ResourceBundle.getBundle("introb").getString("pk.generator") + " " + e.getMessage());
			}
			 
			//Set the value of primary key generated from the KeyGenerator
			try {
                            Object obj = generator.generate(conn, tblName);
                          
				dobj.set(pk,obj );
                               
			} catch (SQLException e) {
				SQLException se = new SQLException("PRIMARY KEY: " + e.getMessage());
				se.setNextException(e);
				throw se;
			}
		}
		 
		//Build the query here..
		String strFields = "";
		String strValues = "";
		if (dobj != null) {
			Iterator fields = dobj.getFieldNames().iterator();
			while (fields.hasNext()) {
				String fieldName = (String) fields.next();
				//DataObject or a List.. Check here.. 
				if ((dobj.isContainment(fieldName)==false) && (dobj.isMany(fieldName)==false)) {
					if (!strFields.equals("")) {
						strFields = strFields + ", ";
						strValues = strValues + ", ";
					}
					strFields = strFields + fieldName;
					strValues =  strValues + "?";
					params.add(dobj.get(fieldName));
				}
			}
			
		}
 
		strFields = "(" + strFields + ")";
		strValues = " VALUES (" + strValues + ")";
		
		//Build insert query
		strSQL = "INSERT INTO " + tblName + strFields + strValues; 

		//debug messages
		if (m_isDebugOn) {
			logMessage(getSQLQueryWithParams(strSQL, params));
		}

		//Query is built.. Execute the query now..
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(strSQL);
			fillStatement(stmt, params);
                      
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			this.rethrow(e, strSQL, params, "INSERT: ");
		} finally {
			close(stmt);
		}
		 
		//Query Executed..
		return rows;
	}
	
	/**
	 * This method will be used to execute insert update delete queries without dataobject 
	 * @param conn
	 * @param query
	 * @return number of recrods inserted
	 */
	public int insertupdatedelete(Connection conn, String query) throws SQLException {
		String strSQL = query;
		int rows = 0;

		//debug messages
		if (m_isDebugOn) {
			logMessage(strSQL);
		}

		//Query is built.. Execute the query now..
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rows = stmt.executeUpdate(strSQL);
		} catch (SQLException e) {
			this.rethrow(e, strSQL, new Vector(), "INSERT: ");
		} finally {
			close(stmt);
		}
		
		//Query Executed..
		return rows;
	}
	
	
	/**
	 * This method will be used to execute update query on a DataObject.
	 * It user has to provide the table name and the primary key of the table.
	 * @param conn
	 * @param dobj
	 * @param tblName
	 * @param pk
	 * @return number of records updated
	 */
	public int update(Connection conn, DataObject dobj, String tblName, String pk) 
			throws SQLException {
		String strSQL = null;
		Vector params = new Vector();
		int rows = 0;
		String strsetter = "";
		
		//Build the query here..
		if (dobj != null) {
			Iterator fields = dobj.getFieldNames().iterator();
			while (fields.hasNext()) {
				String fieldName = (String) fields.next();
				//The field should not be primary key, or DataObject or a List.. Check here.. 
				if ((!fieldName.equals(pk)) && (dobj.isContainment(fieldName)==false) && (dobj.isMany(fieldName)==false)) {
					if (!strsetter.equals("")) {
						strsetter = strsetter + ", ";
					}
					strsetter = strsetter + fieldName + "=?";
					params.add(dobj.get(fieldName));
				}
			}
			
		}

		//Check whether we have any fields that needs update..
		if (strsetter.equals("")) {
			throw new IntrobQueryException("Cannot build update query.");
		} else {
			strSQL = "UPDATE " + tblName + " SET " + strsetter + " WHERE " 
					 + pk + "=?";
			params.add(dobj.get(pk));
		}

		//debug messages
		if (m_isDebugOn) {
			logMessage(getSQLQueryWithParams(strSQL, params));
		}

		
		//Query is built.. Execute the query now..
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(strSQL);
			fillStatement(stmt, params);
			rows = stmt.executeUpdate();
			logQueryInfo(conn, getSQLQueryWithParams(strSQL, params));
		} catch (SQLException e) {
			this.rethrow(e, strSQL, params, "UPDATE: ");
		} finally {
			close(stmt);
		}
		
		//Query Executed..
		return rows;
	}
	
	
	
	/**
	 * This method will be used to execute delete query on a DataObject.
	 * It user has to provide the table name and the primary key of the table.
	 * @param conn
	 * @param dobj
	 * @param tblName
	 * @param pk
	 * @return number of records deleted
	 */
	public int delete(Connection conn, DataObject dobj, String tblName, String pk) 
			throws SQLException {
		String strSQL = null;
		Vector params = new Vector();
		int rows = 0;
		//String strsetter = "";
		
		//Build the query here..
		if (dobj != null) {
			strSQL = "DELETE FROM " + tblName + " WHERE " +  pk + "=?";			
			params.add(dobj.get(pk));
		} else {
			throw new IntrobQueryException("Cannot build delete query. DataObject is null.");
		}

		//debug messages
		if (m_isDebugOn) {
			logMessage(getSQLQueryWithParams(strSQL, params));
		}
		
		//Query is built.. Execute the query now..
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(strSQL);
			fillStatement(stmt, params);
			rows = stmt.executeUpdate();
			logQueryInfo(conn, getSQLQueryWithParams(strSQL, params));
		} catch (SQLException e) {
			e.printStackTrace();
			this.rethrow(e, strSQL, params, "DELETE: ");
			
		} finally {
			close(stmt);
		}
		
		//Query Executed..
		return rows;
	}

	/**
	 * This method will be used to execute delete query on a DataObject.
	 * It user has to provide the composite keys  of the table.
	 * @author piyush
	 * @param conn
	 * @param dobj
	 * @param tblName
	 * @param pk
	 * @return number of records deleted
	 */
	public int delete(Connection conn, DataObject dobj, String tblName,Vector vecFields) 
			throws SQLException {
		String strSQL = null;
		Vector params = new Vector();
		int rows = 0;
		//String strsetter = "";
		Iterator itrFields=vecFields.iterator();
		
		String strWhereClause="";	
	
		//Build the query here..
		if (dobj != null) {
			
			while(itrFields.hasNext()){
			    String field=itrFields.next().toString();
			    Object Value =dobj.get(field);
			    params.add(Value);
			    if(strWhereClause.trim().length()>0)
					strWhereClause+=" AND "+field+"=? ";
			    else
			        strWhereClause+=" "+field+"=? ";
			}
			
			if(strWhereClause.trim().length()>0)
			    strWhereClause=" Where "+strWhereClause;
		
		    
		    strSQL = "DELETE FROM " + tblName + "  " + strWhereClause;			
			
		} else {
			throw new IntrobQueryException("Cannot build delete query. DataObject is null.");
		}

		//System.out.println("589 Query "+strSQL);
		//debug messages
		if (m_isDebugOn) {
			logMessage(getSQLQueryWithParams(strSQL, params));
		}
		
		//Query is built.. Execute the query now..
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(strSQL);
			fillStatement(stmt, params);
			rows = stmt.executeUpdate();
			logQueryInfo(conn, getSQLQueryWithParams(strSQL, params));
		} catch (SQLException e) {
			e.printStackTrace();
			this.rethrow(e, strSQL, params, "DELETE: ");
			
		} finally {
			close(stmt);
		}
		
		//Query Executed..
		return rows;
	}
	
	/**
	 * This method will check for the status of the data object and execute
	 * the insert, update or delete query accordingly..
	 * @param conn
	 * @param dobj
	 * @param tblName
	 * @param pk
	 * @return number of records inserted/updated/deleted
	 * @throws SQLException
	 */
	public int execute(Connection conn, DataObject dobj, String tblName, String pk) 
			throws SQLException {
		int rows = 0;
		if (dobj.getStatus() ==  DataObject.CREATED) {
			rows = this.insert(conn, dobj, tblName, pk);
		} else if (dobj.getStatus() ==  DataObject.MODIFIED) {
			rows = this.update(conn, dobj, tblName, pk);
		} else if (dobj.getStatus() ==  DataObject.DELETED) {
			rows = this.delete(conn, dobj, tblName, pk);
		}  
		return rows;
	}

	/**
	 * created by jai on 13-11-2009 to use SQL DDL commands  
	 * @param conn
	 * @param strQuery
	 * @return
	 * @throws SQLException
	 */
	public boolean execute(Connection conn, String sql)throws SQLException {
	    Statement stmt = conn.createStatement();
	    return stmt.execute(sql);
	}
	
	/**
	 * created by jai on 13-11-2009 to use SQL Function calling without any return value 
	 * @param conn
	 * @param sql
	 * @param listParameters
	 * @return
	 * @throws SQLException
	 */
	public boolean executeFunctionCall(Connection conn, String sql, List listParameters) throws SQLException {
	    if(sql.indexOf("call")<=0){
		sql = "{ call " + sql + " }";
	    }
	    CallableStatement stmt = conn.prepareCall(sql);
	    if(listParameters != null){
		Iterator itr = listParameters.iterator();
		int i = 1;
		while (itr.hasNext()){
		    stmt.setString(i, itr.next().toString());
		    i = i + 1;
		}
	    }	    
	    return stmt.execute();
	}
	
	/**
	 * created by jai on 16-11-2009 to use SQL Batch Functionality 
	 * @param conn
	 * @param sql
	 * @throws SQLException
	 */
	public void addBatch(Connection conn, String sql) throws SQLException {
	    if(m_stmt== null){
		m_stmt = conn.createStatement();
	    }
	    m_stmt.addBatch(sql);
	}
	
	/**
	 * created by jai on 16-11-2009 to use SQL Batch Functionality 
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatch() throws SQLException{
	    int[] returnValue = null;
	    if(m_stmt != null){
		returnValue = m_stmt.executeBatch();
	    }
	    m_stmt = null;
	    return returnValue;
	}

	/**
	 * created by jai on 13-11-2009 to use SQL SELECT commands not based on DO DS
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(Connection conn, String sql)throws SQLException {
	    Statement stmt = conn.createStatement();
	    return stmt.executeQuery(sql);
	}
	
	/**
	 * created by jai on 13-11-2009 to use SQL SELECT commands formated result as array list
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getArrayListFromResultSet(Connection conn, String sql)throws SQLException {
	    ResultSet rs = executeQuery(conn,sql);
	    ResultSetMetaData rsMD = rs.getMetaData();
	    ArrayList arrayListRS = new ArrayList();
	    HashMap hashMap=null;
	    while (rs.next()){
		hashMap=new HashMap();
		for (int i = 1; i <= rsMD.getColumnCount(); i++){
		    hashMap.put(rsMD.getColumnName(i),rs.getObject(i));
		}
		arrayListRS.add(hashMap);
	    }
	    rs.close();
	    rs=null;
	    return arrayListRS;
	}

	
	/**
	 * This method will check for the status of each DataObject in the
	 * DataSet and execute the insert, update or delete query accordingly..
	 * @param conn
	 * @param ds
	 * @param tblName
	 * @param pk
	 * @return number of records inserted/updated/deleted
	 * @throws SQLException
	 */
	public int executeAll(Connection conn, DataSet ds, String tblName, String pk) 
	throws SQLException {
		int rows = 0;
		List lst = ds.getDirtyDataObjects();
		for (int i=0; i<lst.size(); i++) {
			DataObject dobj = (DataObject) lst.get(i);
			if (dobj.getStatus() ==  DataObject.CREATED) {
				rows = rows + this.insert(conn, dobj, tblName, pk);
			} else if (dobj.getStatus() ==  DataObject.MODIFIED) {
				rows = rows + this.update(conn, dobj, tblName, pk);
			} else if (dobj.getStatus() ==  DataObject.DELETED) {
				rows = rows + this.delete(conn, dobj, tblName, pk);
			}  
		}
		return rows;
	}


	//this method is used to log messages
	private void logMessage(String message) {
		//TODO UPDATE THIS CLASS TO USE JAVA LOGGING API FOR DEBUGGING MESSAGES.
		System.out.println(message);
	}


	//this method is used internally to show SQL query and the parameters
	//in a viewable format.
	private String getSQLQueryWithParams(String sql, List params) {
        StringBuffer msg = new StringBuffer();
		msg.append(" Query: ");
		msg.append(sql);
		msg.append(" Parameters: ");
		if (params == null) {
			msg.append("[]");
		} else {
			msg.append(Arrays.asList(params.toArray()));
		}
		return msg.toString();
	}


	/**
	 * This method is used internally to buid the error message ..
	 * This will wrap Exception sql query and the parameters.
	 * @param cause
	 * @param sql
	 * @param params
	 * @return error message
	 */
	private String getErrorMessage(SQLException cause, String sql, List params, String title) {
        StringBuffer msg = new StringBuffer(title);
        msg.append(cause.getMessage());
        msg.append(getSQLQueryWithParams(sql, params));
		return msg.toString();
	}


	/**
     * Throws a new exception with a more informative error message.
	 * @param cause
	 * @param sql
	 * @param params
	 * @param title
	 * @throws SQLException
	 */
    protected void rethrow(SQLException cause, String sql, List params, String title)
        throws SQLException {
        SQLException e = new SQLException(getErrorMessage(cause, sql, params, title));
        e.setNextException(cause);
        throw e;
    }
    
    
    /**
     * This method will delete all the records specified in the table based
     * on the fieldname and value.
     * @param conn
     * @param tblName
     * @param fieldName
     * @param value
     * @return number of records deleted
     * @throws SQLException
     */
	public int delete(Connection conn, String tblName, String fieldName, Object value) 
			throws SQLException {
		int rows = 0;
		Vector params = new Vector();
		String strSQL = "DELETE FROM " + tblName + " WHERE " +  fieldName + "=?";
		params.add(value);
		PreparedStatement stmt = null;
		
		//debug messages
		if (m_isDebugOn) {
			logMessage(getSQLQueryWithParams(strSQL, params));
		}
		
		try {
			stmt = conn.prepareStatement(strSQL);
			fillStatement(stmt, params);
			rows = stmt.executeUpdate();
			logQueryInfo(conn, getSQLQueryWithParams(strSQL, params));
		} catch (SQLException e) {
			e.printStackTrace();
			this.rethrow(e, strSQL, params, "DELETE: ");
			
		} finally {
			close(stmt);
		}

		//Query Executed..
		return rows;
	}
	
	private void logQueryInfo(Connection conn, String query){/*
		try {
//			System.out.println("calling query logging method");
			Statement Qstmt = conn.createStatement();
		        query  = query.replace("$$", "'");
			String queryInfo = "INSERT INTO realm_queryinfo(fquery,fdate,fdatetime,fusername) VALUES ($$"+query+"$$,CURRENT_DATE,now(),'"+m_strUserName+"')";
			Qstmt.executeUpdate(queryInfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	*/}
		
	/**
     * This method will delete all the records specified in the table based
     * on the fieldname and value of map
     * @author Piyush
     * @param conn
     * @param tblName
     * @param fieldName
     * @param value
     * @return number of records deleted
     * @throws SQLException
     */
	public int delete(Connection conn, String tblName, Map fieldValue) 
			throws SQLException {
		char c = 34 ;
		byte b = 23 ;
		int rows = 0;
		Vector params = new Vector();
		String strWhereClause="";
		Set fields=fieldValue.keySet();
	
		Iterator itrFields= fields.iterator();
		while(itrFields.hasNext()){
		    String field=itrFields.next().toString();
		    Object Value =fieldValue.get(field);
		    params.add(Value);
		    if(strWhereClause.trim().length()>0)
				strWhereClause+=" AND "+field+"=? ";
		    else
		        strWhereClause+=" "+field+"=? ";
		}
		
		if(strWhereClause.trim().length()>0)
		    strWhereClause=" Where "+strWhereClause;
		
		String strSQL = "DELETE FROM " + tblName + " " +strWhereClause;
		//System.out.println("715 Query "+strSQL);
		PreparedStatement stmt = null;
		
		//debug messages
		if (m_isDebugOn) {
			logMessage(getSQLQueryWithParams(strSQL, params));
		}
		
		try {
			stmt = conn.prepareStatement(strSQL);
			fillStatement(stmt, params);
			rows = stmt.executeUpdate();
			logQueryInfo(conn, getSQLQueryWithParams(strSQL, params));
		} catch (SQLException e) {
			e.printStackTrace();
			this.rethrow(e, strSQL, params, "DELETE: ");
			
		} finally {
			close(stmt);
		}

		//Query Executed..
		return rows;
	}

	
	
}
