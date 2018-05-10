package LibraryManagementSystem.RentableManagement;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class JDBCConfigTest {

	@Test
	void testGetConnection() {
		try (
			Connection conn = JDBCConfig.getConnection();
		) {
			assertFalse(conn.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Filed to get SQL connection to MySQL database:\n" + 
					e.toString() + "\n" +
					e.getCause().toString());
		}
	}
	
	@Test
	public void testVersion() throws Exception {
	  try (Connection connection = JDBCConfig.getConnection()) {
	    DatabaseMetaData metaData = connection.getMetaData();
	    assertEquals(4, metaData.getJDBCMajorVersion());
	    assertEquals(2, metaData.getJDBCMinorVersion());
	  }
	}

}
