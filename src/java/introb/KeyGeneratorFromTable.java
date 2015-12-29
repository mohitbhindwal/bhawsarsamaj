package introb;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author hitesh
 *
 * This class will generate primary key from a table.
 * If the param exists, then the key is increment by 1 
 * and returned, else the param is added to the table and
 * 1 is returned. This returns an Integer key.
 */
public class KeyGeneratorFromTable implements KeyGenerator {
	/* (non-Javadoc)
	 * @see com.elbiz.introb.sql.KeyGenerator#generate(java.lang.String)
	 */
	public Object generate(Connection conn, String param) throws SQLException {
		Object result = null;
		String strSQL = "SELECT fkey FROM tkeygen WHERE fparam='" + param + "'";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(strSQL);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getObject(1);
				if (result instanceof Long) {
					result = Integer.valueOf(result.toString());
				}
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			rs.close();
			stmt.close();
		}
		return result;
	}
}
