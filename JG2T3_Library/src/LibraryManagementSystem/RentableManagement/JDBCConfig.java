package LibraryManagementSystem.RentableManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConfig {
	
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/db_library?useSSL=false&autoReconnect=true";
	private static final String USERNAME = "root";
	// private static final String PASSWORD = "password";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, "");
	}

}
