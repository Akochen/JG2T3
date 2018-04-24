package LBMS;
import java.util.ArrayList;


public class RentableInventory {
	private ArrayList<Rentable> rentableList;
	
	/**
	 * Initiates an new RentableInventory
	 */
	public RentableInventory(){
		
	}
	
	/**
	 * Takes a Rentable as input and add it to the database
	 * @param r the Rentable to be added to the inventory
	 * @return returns true if Rentable was successfully added
	 */
	public boolean addRentable(Rentable r) {
		return false;
	}
	
	/**
	 * Checks if a book or DVD is available
	 * @param isbn The unique identifier of a book
	 * @return Returns true if there are more copies of the book than there are Rentals for the book
	 */
	public boolean isAvailable(String isbn) {
		Connection conn = null;
		Statement stmt = null;
		String sql = "COUNT(SELECT isbn " + "FROM " + Rentable + ")";
		
		
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
