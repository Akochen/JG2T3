package LBMS;

import java.util.ArrayList;

public class RentalInventory {
	private ArrayList<Rental> rentalList;
	
	/**
	 * Initiates a new RentalInventory
	 */
	public RentalInventory() {
		
	}
	
	/**
	 * Checks a Rentable back into the library and deletes its Rental and checks if there are any reservations for the returned Rentable
	 * @param sku the SKU of the Rentable being checked in
	 * @return returns true if the Rentable is successfully checked in
	 */
	public boolean checkIn(int sku) {
		return true;
	}
	
	/**
	 * Checks a Rentable out of the library and creates a Rental for it
	 * @param sku The SKU of the Rentable being checked out
	 * @return returns true if the Rentable is successfully checked out
	 */
	public boolean checkOut(int sku) {
		return true;
	}
	
	/**
	 * Extends the end date of a Rental
	 * @param r The rental to renew
	 * @return returns true if the rental is successfully renewed
	 */
	public boolean renewRental(Rental r) {
		return false;
	}
	
	/**
	 * Used for filtering Rentals based on a specific input parameter
	 * @param searchType The attribute being used to filter results
	 * @param searchParameters The parameter being compared to the rentals in the database to determine what will be returned
	 * @return An ArrayList containing all Rentals that pass the filter
	 */
	public ArrayList<Rental> searchRentals(String searchType, String searchParameters){
		return new ArrayList<Rental>();
	}
	
	/**
	 * Used for displaying all existing rentals
	 * @return Returns an ArrayList of all existing Rentals
	 */
	public ArrayList<Rental> viewRentals(){
		return new ArrayList<Rental>();
	}
	
	/**
	 * Cycles through all Rentables and adds a fee to the balance of any users that are late 
	 * @return Returns true if all Rentals have been checked and fees were added when appropriate
	 */
	private boolean addFee() {
		return false;
		
	}
}
