package LibraryManagementSystem.RentableManagement;

import java.sql.*;
import java.util.ArrayList;

import LibraryManagementSystem.ReservationManagement.ReservationCollection;
import LibraryManagementSystem.ReservationManagement.ReservationCollectionJDBC;

public class RentalInventoryJDBC implements IRentalInventory {
	private final String URL = "jdbc:mysql://127.0.0.1:3306/db_library?useSSL=false&autoReconnect=true";
	private final String uName = "root";
	//private final String uPass = "root";

	public RentalInventoryJDBC() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Checks a Rentable back into the library and deletes its Rental and checks if there are any reservations for the returned Rentable
	 * @param sku the SKU of the Rentable being checked in
	 * @return returns true if the Rentable is successfully checked in
	 */
	@Override
	public ArrayList<String> checkIn(int rentableId) {
		String sql1 = "";
		String sql2 = "";
		String test = "";
		ArrayList<String> reservedBy = new ArrayList<String>();
		sql1 = "SELECT * FROM Rentable WHERE Rentable.rentableId = '" + rentableId + "';";
		//test = "SELECT userId FROM Reservation WHERE Reservation.upc = '" + 
		sql2 = "DELETE FROM Rental WHERE Rental.rentableId = '" + rentableId + "';";
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSet testresultSet = null;

		try { //include reservation for this to work!!!!!
			conn = DriverManager.getConnection(URL, uName, "");
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql1);
			
			
			if (!resultSet.first()){
				reservedBy.add("No Reservtions Found");
				return reservedBy; //This is what needed to be done
			}
			String upcToEnter = resultSet.getString("upc");
			System.out.println(upcToEnter);
			test = "SELECT userId FROM Reservation WHERE Reservation.upc = '" + upcToEnter + "';";
			testresultSet = statement.executeQuery(test);
			/*if ( ReservationCollectionJDBC.searchByUpc( upcToEnter ) ){
				reservedBy.add( resultSet.getString("userId") );
			}
			*/
			if (!testresultSet.first()){
				reservedBy.add("No Reservtions Found");
				return reservedBy; //This is what needed to be done
			}
				reservedBy.add( testresultSet.getString("userId") );
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		
		try {
			statement.executeUpdate(sql2);
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
		return reservedBy;

	}

	/**
	 * Checks a Rentable out of the library and creates a Rental for it
	 * @param sku The SKU of the Rentable being checked out
	 * @param userId The userId of the user checking out the Rentable
	 * @return returns true if the Rentable is successfully checked out
	 */
	@Override
	public boolean checkOut(int sku, String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Extends the end date of a Rental
	 * @param r The rental to renew
	 * @return returns true if the rental is successfully renewed
	 */
	@Override
	public boolean renewRental(Rental r) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Used for filtering Rentals based on a specific input parameter
	 * @param searchType The attribute being used to filter results
	 * @param searchParameters The parameter being compared to the rentals in the database to determine what will be returned
	 * @return True if the search is successful and the desired Rentals are printed out
	 */
	@Override
	public boolean searchRentals(String searchType, String searchParameters) {
		boolean result = false;
		String sql = "SELECT * FROM rental, rentable WHERE rental.rentableID = rentable.rentableID AND rentable.type = ? AND rentable.title LIKE ?;";
		ResultSet resultSet;
		try (
			Connection conn = JDBCConfig.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
		) {
			statement.setString(1, searchType);
			statement.setString(2, searchParameters);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String toPrint = "ID: " + resultSet.getString(1) + ", Start Date: " + resultSet.getString(2)
				+ ", End Date: " + resultSet.getString(3) + ", User Id: " + resultSet.getString(4)
				+ ", Times Renewed: " + resultSet.getString(5);
				System.out.println(toPrint);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * Used for displaying all existing rentals
	 * 
	 * @return Returns an ArrayList of all existing Rentals
	 */
	@Override
	public ArrayList<String> viewRentals() {
		String sql = "";
		ArrayList<String> result = new ArrayList<String>();
		sql = "SELECT * FROM Rental;";
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet;
		try {
			conn = DriverManager.getConnection(URL, uName, "");
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (!resultSet.first()){
				result.add("No Rentals Found");
			}
			do
				result.add("Rentable ID: " + resultSet.getString(1) 
				+ ", Start Date: " + resultSet.getString(2)
				+ ", End Date: " + resultSet.getString(3) 
				+ ", User Id: " + resultSet.getString(4)
				+ ", Times Renewed: " + resultSet.getString(5) + "\n");
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
