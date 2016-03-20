package introb;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class ConnectionFactory {

	private static ConnectionFactory INSTANCE = new ConnectionFactory();
    private static DataSource ds ;
	private ConnectionFactory(){
/*		try{
		System.out.println("-->Getting connection from source");
		  ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/PostgreSQLDS");
		System.out.println("-->Getting connection from source DONE");
		}catch (Exception e) {
             e.printStackTrace();
		}*/  // calculation_pre
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	static public  Connection getConnection(){
		Connection c = null;
		try {
	  //     c = DriverManager.getConnection("jdbc:postgresql://127.8.110.2:5432/bigup","adminaaee558", "8SX_4pj6aFBa");
		   //c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bigup","adminaaee558", "8SX_4pj6aFBa");
	     // c = DriverManager.getConnection("jdbc:postgresql://localhost:5444/postgres","postgres", "postgres");
		  c = DriverManager.getConnection("jdbc:postgresql://localhost:5444/bhawsarsamaj","postgres", "postgres");
	 //   DataSource datasource = (DataSource)initialContext.lookup("java:jboss/datasources/PostgreSQLDS");
	  //  c = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return c;
	}

	public static void main(String[] args) throws SQLException, UnsupportedEncodingException {
		System.out.println();
                Connection con = ConnectionFactory.getConnection();
                ResultSet rs =  con.createStatement().executeQuery("select post from post where id = 4 ");
                rs.next();
                System.out.println(new String(rs.getString(1).getBytes("UTF-8"),"UTF-8"));
                con.close();
	}


}
