package introb;

import java.sql.Connection;
import java.sql.DriverManager;

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

	public static void main(String[] args) {
		System.out.println(ConnectionFactory.getConnection());
	}


}
