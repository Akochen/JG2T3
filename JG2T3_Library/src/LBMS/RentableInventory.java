package LBMS;
import java.util.ArrayList;
import java.sql.*;


public class RentableInventory {
	private ArrayList<Rentable> rentableList;
	private Connection conn = null;
	private Statement stmt = null;
	private String URL;
	private String uName;
	private String uPass;
	private int nextSku;
	/**
	 * Initiates an new RentableInventory
	 */
	public RentableInventory(){
		try {
			URL = "jdbc:mysql://localhost:3306/db_library?&useSSL=true";
			uName = "root";
			uPass = "root";
			Class.forName("com.mysql.jdbc.Driver");
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false&autoReconnect=true", "root", "root");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private int getNextSku() {
		return 0;
		
	}
	
	/**
	 * Takes a Rentable as input and add it to the database
	 * @param r the Rentable to be added to the inventory
	 * @return returns true if Rentable was successfully added
	 */
	public boolean addRentable(Rentable r) {
		String query = "INSERT INTO Rentable VALUES( null, " + r.getTitle() + ", "
				+ r.getIsbn() + ", " + r.getCondition() + ", " + r.getGenre() + ", " 
				+ r.getType() + ", " + r.getRoomNumber() + ");";
		Statement statement= null;
		ResultSet test = null;
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			test = statement.executeQuery(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				test.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if a book or DVD is available
	 * @param isbn The unique identifier of a book
	 * @return Returns true if there are more copies of the book than there are Rentals for the book
	 */
	public boolean isAvailable(String isbn) {
		//String sql = "COUNT(SELECT isbn " + "FROM " + Rentable + ")";
		
		
		//String sql = "COUNT(SELECT sku " + "FROM " + Rentable + " WHERE sku IN(SELECT sku" + " FROM " + Rental + ")";
		//COUNT(SELECT isbn FROM Rentable);
		//COUNT(SELECT sku FROM Rentable WHERE sku IN(SELECT sku FROM Rental))
		
		return true;
	}
	
	/**
	 * Deletes a Rentable with a specific SKU from the database
	 * @param isbn The SKU of the Rentable that needs to be removed
	 * @return returns the removed Rentable
	 */
	public Rentable removeRentable(int rentableSKU) {
		return new Rentable("r");
	}
	
	/**
	 * Searches the database for a list of Rentables
	 * @param searchType The specific attribute being used to filter results
	 * @param searchParameter The parameter that is being compared to the Rentables in the database to determine what will be returned
	 * @return returns an ArrayList or Rentables that fit the search parameter
	 */
	public ArrayList<Rentable> searchRentables(String searchType, String searchParameter){
		return rentableList;
	}

	/**
	 * Displays all Rentables in the database
	 * @return The arraylist of all Rentables in the database
	 */
	public ArrayList<Rentable> viewRentables() {
		return rentableList;
	}
}
