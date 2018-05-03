package LBMS;
import java.util.ArrayList;
import java.sql.*;


public class RentableInventoryJDBC  implements IRentableInventory{
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
	
	public RentableInventoryJDBC(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
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
	 * @param identifier the unique attribute that determines the title or room number of the Rentable
	 * @param type The type of Rentable being checked (DVD or Book)
	 * @return Returns true if there are more copies of the book than there are Rentals for the book
	 */
	@Override
	public boolean isAvailable(String identifier, String type) {
		String getRentable ="";
		String sql = "";
		boolean result = false;
		
		//getRentable = "SELECT from Rentable WHERE rentable.sku = " + sku + ";";
		

		
		if(type.toLowerCase().equals("dvd")){
			sql = "SELECT COUNT(*) FROM Rentable WHERE rentable.sku NOT IN (SELECT sku FROM Rental) AND title = " + identifier + ";";
		}
		else if(type.toLowerCase().equals("book")){
			sql = "SELECT COUNT(*) FROM Rentable WHERE rentable.sku NOT IN (SELECT sku FROM Rental) AND isbn = " + identifier + ";";
		}
		
		Statement statement= null;
		ResultSet resultSet = null;
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			resultSet.first();
			if(resultSet.getInt(1) > 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				resultSet.close();
			} catch (SQLException e) {
				
			}
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
	 * Deletes a Rentable with a specific SKU from the database
	 * @param rentableSKU The SKU of the Rentable that needs to be removed
	 * @return returns the removed Rentable
	 */
	@Override
	public Rentable removeRentable(int rentableSKU) {
		// TODO
		return null;
	}
	
	/**
	 * Searches the database for a list of Rentables
	 * @param searchType The specific attribute being used to filter results
	 * @param searchParameter The parameter that is being compared to the Rentables in the database to determine what will be returned
	 * @return True if the search is successful and the desired Rentables are printed out
	 */
	@Override
	public boolean searchRentables(String searchType, String searchParameter){
		//redo, returns a string value. Do the same for viewRentables
		String sql = "";
		boolean result = false;
		//if(searchType.toLowerCase().equals("sku")){
			//int search;
			//search = parseInt(searchParameter);
					//sql = "SELECT * FROM Rentable WHERE rentable." + searchType + " = " + search + ";";

		//}
		//else
			sql = "SELECT * FROM Rentable WHERE rentable." + searchType + " = '" + searchParameter + "';";
			
			
			Statement statement= null;
			ResultSet resultSet;
			try {
				conn = DriverManager.getConnection(URL, uName, uPass);
				statement = conn.createStatement();
				resultSet = statement.executeQuery(sql);
				
				if(!resultSet.next()){
					System.out.println("No Results Found");}
				
				resultSet.beforeFirst();
				while(resultSet.next()){
					String type = resultSet.getString(6);
					
					if(type.toLowerCase().equals("book") || type.toLowerCase().equals("ebook")){
						System.out.println("SKU: " + resultSet.getString(1)
							+ ", Title: " + resultSet.getString(2)
							+ ", ISBN: " + resultSet.getString(3)
							+ ", Condition: " + resultSet.getString(4)
							+ ", Genre: " + resultSet.getString(5)
							+ ", Type: " + resultSet.getString(6));
					}else if(type.toLowerCase().equals("dvd")){
						System.out.println("SKU: " + resultSet.getString(1)
							+ ", Title: " + resultSet.getString(2)
							+ ", Condition: " + resultSet.getString(4)
							+ ", Genre: " + resultSet.getString(5)
							+ ", Type: " + resultSet.getString(6));
					}else if(type.toLowerCase().equals("room")){
						System.out.println("SKU: " + resultSet.getString(1)
							+ ", Room Number: " + resultSet.getString(7));
					}
					
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				try{
					resultSet.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
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
	 * @return The arraylist of all Rentables in the database
	 */
	@Override
	public boolean viewRentables() {
		String sql = "";
		boolean result = false;
			sql = "SELECT * FROM Rentable;";
			
			
			Statement statement= null;
			ResultSet resultSet;
			try {
				conn = DriverManager.getConnection(URL, uName, uPass);
				statement = conn.createStatement();
				resultSet = statement.executeQuery(sql);
				
				if(!resultSet.next())
					System.out.println("No Results Found");
				while(resultSet.next()){
					String type = resultSet.getString(6);
					
					if(type.toLowerCase().equals("book") || type.toLowerCase().equals("ebook")){
						System.out.println("SKU: " + resultSet.getString(1)
							+ ", Title: " + resultSet.getString(2)
							+ ", ISBN: " + resultSet.getString(3)
							+ ", Condition: " + resultSet.getString(4)
							+ ", Genre: " + resultSet.getString(5)
							+ ", Type: " + resultSet.getString(6));
					}else if(type.toLowerCase().equals("dvd")){
						System.out.println("SKU: " + resultSet.getString(1)
							+ ", Title: " + resultSet.getString(2)
							+ ", Condition: " + resultSet.getString(4)
							+ ", Genre: " + resultSet.getString(5)
							+ ", Type: " + resultSet.getString(6));
					}else if(type.toLowerCase().equals("room")){
						System.out.println("SKU: " + resultSet.getString(1)
							+ ", Room Number: " + resultSet.getString(7));
					}
					
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
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
