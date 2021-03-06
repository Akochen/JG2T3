package LibraryManagementSystem.RentableManagement;
import java.util.ArrayList;
import java.sql.*;

import java.util.ArrayList;

public class RentableInventory implements IRentableInventory {
	
	private RentableInventoryJDBC inventory;
	
	public RentableInventory() {
		inventory = new RentableInventoryJDBC();
	}
	
	/**
	 * Takes a Rentable as input and add it to the database
	 * @param r the Rentable to be added to the inventory
	 * @return returns true if Rentable was successfully added
	 */
	@Override
	public boolean addRentable(Rentable r) {
		return inventory.addRentable(r);
	}

	/**
	 * Deletes a Rentable with a specific SKU from the database
	 * @param sku The SKU of the Rentable that needs to be removed
	 * @return returns the removed Rentable
	 */
	@Override
	public Rentable removeRentable(String sku) {
		return inventory.removeRentable(sku);
	}
	
	/**
	 * Searches the database for a list of Rentables
	 * @param searchType The specific attribute being used to filter results
	 * @param searchParameter The parameter that is being compared to the Rentables in the database to determine what will be returned
	 * @return True if the search is successful and the desired Rentables are printed out
	 */
	@Override
	public ArrayList<String> searchRentables(String searchType, String searchParameter) {
		return inventory.searchRentables(searchType, searchParameter);
	}

	/**
	 * Displays all Rentables in the database
	 * @return True if all Rentables are successfully printed out
	 */
	@Override
	public ArrayList<String> viewRentables() {
		return inventory.viewRentables();
	}

}
