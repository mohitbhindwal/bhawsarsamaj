package introb;


import java.sql.Connection;
import java.sql.SQLException;

public interface KeyGenerator {
	
	public Object generate(Connection conn, String param) throws SQLException;
	
}
