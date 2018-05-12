package LibraryManagementSystem.RentableManagement;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import LibraryManagementSystem.AccountManagement.AccountCollection;
import LibraryManagementSystem.AccountManagement.User;


public class RentalInventoryJDBC implements IRentalInventory {
	private final String URL = "jdbc:mysql://127.0.0.1:3306/db_library?useSSL=false&autoReconnect=true";
	private final String uName = "root";
	private final String uPass = "root";

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
		String sql3 = "";
		String sqlUpdate = "";
		ArrayList<String> reservedBy = new ArrayList<String>();
		sql1 = "SELECT * FROM Rentable WHERE Rentable.rentableId = '" + rentableId + "';";
		sql2 = "DELETE FROM Rental WHERE Rental.rentableId = '" + rentableId + "';";
		sql3 = "SELECT * FROM Rental WHERE Rental.rentableId = '" + rentableId + "';";
		sqlUpdate = "UPDATE Rentable SET isAvailable = 1 WHERE Rentable.rentableId = '" + rentableId + "';";
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSet testresultSet = null;
		ResultSet resultSet3 = null;

		try { 
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql1);
			
			
			if (!resultSet.first()){
				reservedBy.add("No Reservtions Found");
				return reservedBy; 
			}
			String upcToEnter = resultSet.getString("upc");
			test = "SELECT userId FROM Reservation WHERE Reservation.upc = '" + upcToEnter + "';";
			testresultSet = statement.executeQuery(test);
			if (!testresultSet.first()){
				reservedBy.add("No Reservtions Found");
			} else {
				do
					reservedBy.add("Item is reserved by: " + testresultSet.getString("userId") + "\n" );
				while ( testresultSet.next());
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		
		try {
			resultSet3 = statement.executeQuery(sql3);
			if (!resultSet3.first()){
				reservedBy.add("No Rentals Found");
				return reservedBy; 
			}else 
				reservedBy.add("Rentable was Checked In.");
			
			statement.executeUpdate(sqlUpdate);
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
	public String checkOut(String rentableId, String userId) {
		Optional<Rental> added = checkOutObj(rentableId, userId);
		if (added.isPresent()) {
			ArrayList<Rental> r = new ArrayList<>();
			r.add(added.get());
			return buildRentalsTable(r);
		} else {
			return "Sorry, the Rental failed to be created.";
		}
	}
	
	public Optional<Rental> checkOutObj(String rentableId, String userId) {
		
		// Create Rental (sku, today, 2 weeks later, userId, 0)
		Rental toBe = new Rental(rentableId, LocalDate.now(), LocalDate.now().plus(Period.ofWeeks(2)), userId, 0);
		String sql = "INSERT INTO rental (sku, start_date, end_date, user_id, times_renewed) VALUES (?,?,?,?,?)";
		try (
				Connection conn = JDBCConfig.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				PreparedStatement clearIsAvailableSt = conn.prepareStatement("UPDATE rentable SET isAvailable = 0 WHERE rentableId = ?");
				PreparedStatement checkAvailableSt = conn.prepareStatement("SELECT rentableId FROM rentable WHERE rentableId = ? AND rentableId NOT IN (SELECT rentableId FROM rental);");
				) {
			// Check if the Rentable has a Rental
			checkAvailableSt.setString(1, rentableId);
			ResultSet av = checkAvailableSt.executeQuery();
			if (!av.next()) { // if the resultset is empty, the rentable is not available
				return Optional.empty();
			}
			st.setString(1, toBe.getRentableId());
			st.setObject(2, toBe.getStartDate());
			st.setObject(3, toBe.getEndDate());
			st.setString(4, toBe.getUserId());
			st.setInt(5, toBe.getTimesRenewed());
			st.executeQuery();
			// set isAvailable to 0
			clearIsAvailableSt.setString(1, rentableId);
			clearIsAvailableSt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(toBe);
	}

	/**
	 * Extends the end date of a Rental
	 * @param r The rental to renew
	 * @return returns true if the rental is successfully renewed
	 */
	@Override
	public boolean renewRental(String rentableIdToSearch) {
		boolean toReturn = false;
		String sqlSelect = "SELECT * FROM rental WHERE rental.rentableId = '" + rentableIdToSearch + "' AND rental.end_date < (NOW() + INTERVAL 3 DAY) AND end_date > NOW();";
		String sqlUpdate = "UPDATE Rental SET end_date = DATE_ADD(end_date, INTERVAL 3 DAY) WHERE Rental.rentableId = '" + rentableIdToSearch + "';";

		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sqlSelect);

			if (!resultSet.first())
				return toReturn;
			
			statement.executeUpdate(sqlUpdate);
			toReturn = true;
			
		}catch (SQLException e) {
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
			return toReturn;
			
	}

	/**
	 * Used for filtering Rentals based on a specific input parameter
	 * @param searchType The attribute being used to filter results
	 * @param searchParameters The parameter being compared to the rentals in the database to determine what will be returned
	 * @return an ArrayList of Rentals meeting the search criteria. 
	 */
	@Override
	public String searchRentals(String searchType, String searchParameters) {
		return buildRentalsTable(searchRentalsObj(searchType, searchParameters));
	}
	
	public ArrayList<Rental> searchRentalsObj(String searchType, String searchParameters) {
		ArrayList<Rental> resList = new ArrayList<>();
		String matchOn;
		switch (searchType) {
		case "rentableid":
			matchOn = "rental.rentableId";
			break;
		case "userid":
			matchOn = "rental.userId";
			break;
		case "times_renewed":
			matchOn = "rental.times_renewed";
			break;
		default:
			// TODO handle invalid searchType strings
			matchOn = "rental.rentableId";
		}
		
		String sql;
		if (matchOn.equals("rentable.title")) {
			sql = "SELECT * FROM rental, rentable WHERE rental.rentableId = rentable.rentableId AND rentable.title LIKE ?;";
		} else {
			sql = "SELECT * FROM rental, rentable WHERE rental.rentableId = rentable.rentableId AND " + matchOn + " = ?;";
		}

		ResultSet resultSet;
		try (
			Connection conn = JDBCConfig.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
		) {
			statement.setString(1, searchParameters);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Rental rowObj = new Rental(
						resultSet.getString(1), 
						resultSet.getObject(2, LocalDate.class),
						resultSet.getObject(3, LocalDate.class),
						resultSet.getString(4),
						resultSet.getInt(5));
				resList.add(rowObj);
			}
		} catch (SQLException e) {
			// TODO handle sql exceptions
			e.printStackTrace();
		}
		return resList;
	}
	
	public static String buildRentalsTable(ArrayList<Rental> result) {
		StringBuilder rentalTable = new StringBuilder();
        String rentalTableHeader = "RentableId | Start Date | End Date   | Times Renewed | UserId\n";
        String rentalRowTemplate = "%-10s | %-10s | %-10s | %-13s | %-6s\n";
        rentalTable.append(rentalTableHeader);
        for (Rental rental: result) {
        	rentalTable.append(String.format(rentalRowTemplate, 
        			rental.getRentableId(), 
        			rental.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE), 
        			rental.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE), 
        			rental.getTimesRenewed(),
        			rental.getUserId()));
        }
        return rentalTable.toString();
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
			conn = DriverManager.getConnection(URL, uName, uPass);
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

	@Override
	public boolean addFee() {
		String sql = "SELECT userId FROM db_library.rental WHERE end_date < NOW();";
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		boolean result = true;
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			AccountCollection ac = new AccountCollection();
			if(!resultSet.first()) {
				result = false;
			} else {
				resultSet.beforeFirst();
				while(resultSet.next()) {
					User user = (User)ac.search(resultSet.getString(1));
					user.increaseBalance(1.5);
					ac.updateBalance(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				return false;
			}
			try {
				statement.close();
			} catch (SQLException e) {
				return false;
			}
			try {
				conn.close();
			} catch (SQLException e) {
				return false;
			}
		}
		return result;
	}
}
