package LibraryManagementSystem.RentableManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConfig {
	
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/db_library?useSSL=false&autoReconnect=true&serverTimezone=UTC";
	private static final String USERNAME = "root";
	private static final String PASSWORD = null;
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

}
