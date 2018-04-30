package LBMS;
import java.util.ArrayList;
import java.sql.*;


public interface RentableInventory {
	
	/**
	 * Takes a Rentable as input and add it to the database
	 * @param r the Rentable to be added to the inventory
	 * @return returns true if Rentable was successfully added
	 */
	@SuppressWarnings("finally")
	public boolean addRentable(Rentable r);
		
	
	/**
	 * Checks if a book or DVD is available
	 * @param isbn The unique identifier of a book
	 * @return Returns true if there are more copies of the book than there are Rentals for the book
	 */
	public boolean isAvailable(String identifier, String type);
	
	/**
	 * Deletes a Rentable with a specific SKU from the database
	 * @param isbn The SKU of the Rentable that needs to be removed
	 * @return returns the removed Rentable
	 */
	public Rentable removeRentable(int rentableSKU);
	
	/**
	 * Searches the database for a list of Rentables
	 * @param searchType The specific attribute being used to filter results
	 * @param searchParameter The parameter that is being compared to the Rentables in the database to determine what will be returned
	 * @return returns an ArrayList or Rentables that fit the search parameter
	 */
	public ArrayList<Rentable> searchRentables(String searchType, String searchParameter);

	/**
	 * Displays all Rentables in the database
	 * @return The arraylist of all Rentables in the database
	 */
	public ArrayList<Rentable> viewRentables();
}
