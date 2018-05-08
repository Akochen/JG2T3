package LibraryManagementSystem.RentableManagement;

import java.util.ArrayList;
import java.sql.*;

public class RentableInventoryJDBC implements IRentableInventory {
	private ArrayList<Rentable> rentableList;
	private Connection conn = null;
	private Statement stmt = null;
	private final String URL = "jdbc:mysql://127.0.0.1:3306/db_library?useSSL=false&autoReconnect=true";
	private final String uName = "root";
	private final String uPass = "root";
	private int nextSku;

	/**
	 * Initiates an new RentableInventory
	 */

	public RentableInventoryJDBC() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Takes a Rentable as input and add it to the database
	 * 
	 * @param r
	 *            the Rentable to be added to the inventory
	 * @return returns true if Rentable was successfully added
	 */
	@Override
	public String addRentable(Rentable r) {
		String query = r.getType();
		String endMsg = "Rentable successfully added!";
		
		if (r.getType().toLowerCase().equals("dvd")) {
			query = "INSERT INTO Rentable VALUES('" + r.getSku() + "', '" + r.getUpc() + "', '" + r.getTitle() + "', "
					+ "''" + ", '" + r.getCondition() + "', '" + r.getGenre() + "', '" + r.getType() + "', " + r.getAvailability()+ ");";
		} else if (r.getType().toLowerCase().equals("book") || r.getType().toLowerCase().equals("ebook")) {
			query = "INSERT INTO Rentable VALUES('" + r.getSku() + "', '" + r.getUpc() + "', '" + r.getTitle() + "', '"
					+ r.getIsbn() + "', '" + r.getCondition() + "', '" + r.getGenre() + "', '" + r.getType() + "', " + r.getAvailability() + ");";
		}
		Statement statement = null;
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			statement.execute(query);
		} catch (SQLException e1) {
			endMsg = "Error: ID already exists.";
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				endMsg = "Error: Unable to close statement.";
			}
			try {
				conn.close();
			} catch (SQLException e) {
				endMsg = "Error: Unable to close connection.";
			}
		}
		return endMsg;
	}

	/**
	 * Deletes a Rentable with a specific SKU from the database
	 * 
	 * @param rentableSKU
	 *            The SKU of the Rentable that needs to be removed
	 * @return returns the removed Rentable
	 */
	@Override
	public Rentable removeRentable(String rentableSKU) {
		String selQuery = "SELECT * FROM rentable WHERE rentableid = '" + rentableSKU + "'";
		String delQuery = "DELETE FROM rentable WHERE rentableid = '" + rentableSKU + "'";
		Rentable rentable = null;

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(selQuery);
			if (resultSet.first()) {
				if (resultSet.getString(7).toLowerCase().equals("book")
						|| resultSet.getString(7).toLowerCase().equals("ebook")) {
					rentable = new Rentable(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
							resultSet.getString(7), resultSet.getInt(8)+"");
				} else if (resultSet.getString(7).toLowerCase().equals("dvd")) {
					rentable = new Rentable(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(6), resultSet.getInt(8)+"");
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				return null;
			}
		}

		try {
			statement.executeUpdate(delQuery);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rentable;
	}

	/**
	 * Searches the database for a list of Rentables
	 * 
	 * @param searchType
	 *            The specific attribute being used to filter results
	 * @param searchParameter
	 *            The parameter that is being compared to the Rentables in the
	 *            database to determine what will be returned
	 * @return True if the search is successful and the desired Rentables are
	 *         printed out
	 */
	@Override
	public ArrayList<String> searchRentables(String searchType, String searchParameter) {
		String sql = "";
		ArrayList<String> result = new ArrayList<String>();
		sql = "SELECT * FROM Rentable WHERE rentable." + searchType + " = '" + searchParameter + "';";
		//sql = "SELECT * FROM Rentable WHERE rentable." + searchType + " = 'Best Book NA';";
		Statement statement = null;
		ResultSet resultSet;
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);

			if (!resultSet.next()) {
				result.add("No Results Found");
				return result;
			}
			do
				result.add("Rentable ID: " + resultSet.getString(1)
				+", UPC: " + resultSet.getString(2)
				+", Title: " + resultSet.getString(3)
				+", ISBN: " + resultSet.getString(4)
				+", Condition: " + resultSet.getString(5)
				+", Genre: " + resultSet.getString(6)
				+", Type: " + resultSet.getString(7)
				+", Availability: " + resultSet.getInt(8) + "\n");	
			while (resultSet.next());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Displays all Rentables in the database
	 * 
	 * @return The arraylist of all Rentables in the database
	 */
	@Override
	public ArrayList<String> viewRentables() {
		String sql = "";
		ArrayList<String> result = new ArrayList<String>();
		sql = "SELECT * FROM Rentable;";

		Statement statement = null;
		ResultSet resultSet;
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);

			if (!resultSet.first()) {
				result.add("No Results Found");
				return result;
			}
			do
				result.add("Rentable Id: " + resultSet.getString(1) 
				+ ", Upc: " + resultSet.getString(2) 
				+ ", Title: " + resultSet.getString(3) 
				+ ", ISBN: " + resultSet.getString(4) 
				+ ", Condition: " + resultSet.getString(5) 
				+ ", Genre: " + resultSet.getString(6) 
				+ ", Type: " + resultSet.getString(7) 
				+ ", Availability: " + resultSet.getString(8) + "\n");
			while (resultSet.next());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
