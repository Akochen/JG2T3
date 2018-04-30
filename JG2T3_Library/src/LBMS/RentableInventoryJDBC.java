package LBMS;
import java.util.ArrayList;
import java.sql.*;


public class RentableInventoryJDBC  implements RentableInventory{
	private ArrayList<Rentable> rentableList;
	private Connection conn = null;
	private Statement stmt = null;
	private final String URL = "jdbc:mysql://127.0.0.1:3306/db_library?useSSL=false&autoReconnect=true";
	private final String uName = "username";
	private final String uPass = "password";
	private int nextSku;
	/**
	 * Initiates an new RentableInventory
	 */
	
	public RentableInventoryJDBC(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * Takes a Rentable as input and add it to the database
	 * @param r the Rentable to be added to the inventory
	 * @return returns true if Rentable was successfully added
	 */
	@Override
	public boolean addRentable(Rentable r) {
		String query = r.getType();
		
		if(r.getType().toLowerCase().equals("room")) {
			query = "INSERT INTO Rentable VALUES( null, " + "null" + ", "
					+ "null" + ", " +"null" + ", " +"null" + ", " 
					+ "null" + ", '" + r.getRoomNumber() + "');";
		}else if(r.getType().toLowerCase().equals("dvd")) {
			query = "INSERT INTO Rentable VALUES( null, '" + r.getTitle() + "', "
					+ "null" + ", '" + r.getCondition() + "', '" + r.getGenre() + "', '" 
					+ r.getType() + "', null);";
		} else if(r.getType().toLowerCase().equals("book") || r.getType().toLowerCase().equals("ebook")) {
			query = "INSERT INTO Rentable VALUES( null, '" + r.getTitle() + "', '"+ r.getIsbn() +"', '" 
					+ r.getCondition() + "', '" + r.getGenre() + "', '" + r.getType() + "', null);";
		}
		Statement statement= null;
		int test = 0;
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			test = statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			try {
				conn.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * Checks if a book or DVD is available
	 * @param isbn The unique identifier of a book
	 * @return Returns true if there are more copies of the book than there are Rentals for the book
	 */
	@Override
	public boolean isAvailable(String identifier, String type) {
		String sql = "";
		
		if(type.toLowerCase().equals("dvd")){
			sql = "SELECT COUNT(*) FROM Rentable WHERE rentable.sku NOT IN (SELECT sku FROM Rental) AND title = " + identifier + ";";
		}
		else if(type.toLowerCase().equals("book")){
			sql = "SELECT COUNT(*) FROM Rentable WHERE rentable.sku NOT IN (SELECT sku FROM Rental) AND isbn = " + identifier + ";";
		}
		
		Statement statement= null;
		int test = 0;
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			test = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			try {
				conn.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * Deletes a Rentable with a specific SKU from the database
	 * @param isbn The SKU of the Rentable that needs to be removed
	 * @return returns the removed Rentable
	 */
	@Override
	public Rentable removeRentable(int rentableSKU) {
		return new Rentable("r");
	}
	
	/**
	 * Searches the database for a list of Rentables
	 * @param searchType The specific attribute being used to filter results
	 * @param searchParameter The parameter that is being compared to the Rentables in the database to determine what will be returned
	 * @return returns an ArrayList or Rentables that fit the search parameter
	 */
	@Override
	public ArrayList<Rentable> searchRentables(String searchType, String searchParameter){
		return rentableList;
	}

	/**
	 * Displays all Rentables in the database
	 * @return The arraylist of all Rentables in the database
	 */
	@Override
	public ArrayList<Rentable> viewRentables() {
		return rentableList;
	}
}
