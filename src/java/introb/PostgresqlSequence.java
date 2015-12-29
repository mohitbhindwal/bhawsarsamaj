package introb;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author hitesh
 *
 * Primary key generator for PostgreSQL database.
 */
public class PostgresqlSequence implements KeyGenerator {
	/* (non-Javadoc)
	 * @see com.elbiz.introb.sql.KeyGenerator#generate(java.lang.String)
	 */
	public Object generate(Connection conn, String param) throws SQLException {
		Object result = null;
		String strSQL = "SELECT nextval('" + param + "_id_seq')";
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
