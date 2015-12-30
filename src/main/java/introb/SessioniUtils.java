package  introb;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
 
 

public class SessioniUtils {
   
   /*
    * File should be inside folder com.kundli.util
    * */
   public static String readFileAsStream(String fileName){
	   String str="",result = "";
	   BufferedReader fis = null;
	   try {
		   InputStream is = SessioniUtils.class.getResourceAsStream(fileName);
	   if(is!=null)
		   fis =  new BufferedReader(new InputStreamReader(is));
	   if(fis!=null)
       while ((str  = fis.readLine()) != null) {
			result+=str;
		}
	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		try {
		if(fis!=null)fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	   return result;
   
   }
   
   public static String readFile(String path){
	   String str="",result = "";
	   FileReader fr = null;
	   BufferedReader fis =null;
	   try {
		fr = new FileReader(new File(path));
	    fis =  new BufferedReader(fr);
	    while ((str  = fis.readLine()) != null) {
			result+=str;
		}
	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		try {
		if(fr!=null)fr.close();
		if(fis!=null)fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	   return result;
   }
   
	
   
   
 
	public static String executeQuery(String sql){
		Connection con = null;
		Statement smt = null;
		String result = null;
		ResultSet rs = null;
		try{
		 con =	ConnectionFactory.getConnection();
		 smt =  con.createStatement();
	        rs = smt.executeQuery(sql);
		 if(rs.next())
			 result = rs.getString(1);
		}catch (Exception e) {
			 e.printStackTrace();
		}
		finally{
			try {
			if(con!=null)con.close();
			if(smt!=null)smt.close();
			if(rs!=null)rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}
	
	public static LinkedHashMap executeQueryForMap(String sql){
		Connection con = null;
		Statement smt = null;
		String result = null;
		ResultSet rs = null;
		LinkedHashMap map = null;
		try{
		 con =	ConnectionFactory.getConnection();
		 smt =  con.createStatement();
		  rs = smt.executeQuery(sql);
		 if(rs.next()){
			 map = new LinkedHashMap();
			 ResultSetMetaData rsmd= rs.getMetaData();
			 for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				map.put(rsmd.getColumnName(i), rs.getString(rsmd.getColumnName(i)));
			} 
		 }
			
			  
		}catch (Exception e) {
			 e.printStackTrace();
		}
		finally{
			try {
			if(con!=null)con.close();
			if(smt!=null)smt.close();
			if(rs!=null)rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return map;
	}
	
	public static int executeUpdate(String sql) throws SQLException{
		Connection con = null;
		Statement smt = null;
		int updateRow = 0;
		ResultSet rs = null;
		try{
		 con =	ConnectionFactory.getConnection();
		 smt =  con.createStatement();
	         updateRow = smt.executeUpdate(sql);
		}catch (SQLException e) {
			 e.printStackTrace();
			 throw e;
		}
		finally{
			try {
			if(con!=null)con.close();
			if(smt!=null)smt.close();
			if(rs!=null)rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return updateRow;
	}
	
	 public static boolean isValidDate(String date){
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		  dateFormat.setLenient(false);
		  try {
		      dateFormat.parse(date.trim());
		    } catch (ParseException pe) {
		      return false;
		    }
		    return true;
		  }
	 
	public static DataSet  query(String sql){
		 DataSet ds = null;
		 IntrobSession session = null;
		 try{
			 session = new IntrobSession();
			 session.open();
			 ds  = session.query(sql);
		 }catch (Exception e) {
			 e.printStackTrace();
		}finally{
			try {
			if(session!=null)
				session.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return ds;
	 }
	
	 

	
	public static void main(String[] args) {
	 
	}
	 
}
