package LBMS;

import java.util.ArrayList;

public class RentalInventoryJDBC implements IRentalInventory{

	/**
	 * Checks a Rentable back into the library and deletes its Rental and checks if there are any reservations for the returned Rentable
	 * @param sku the SKU of the Rentable being checked in
	 * @return returns true if the Rentable is successfully checked in
	 */
	@Override
	public boolean checkIn(int sku) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Checks a Rentable out of the library and creates a Rental for it
	 * @param sku The SKU of the Rentable being checked out
	 * @return returns true if the Rentable is successfully checked out
	 */
	@Override
	public boolean checkOut(int sku) {
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
	 * @return An ArrayList containing all Rentals that pass the filter
	 */
	@Override
	public ArrayList<Rental> searchRentals(String searchType, String searchParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Used for displaying all existing rentals
	 * @return Returns an ArrayList of all existing Rentals
	 */
	@Override
	public ArrayList<Rental> viewRentals() {
		// TODO Auto-generated method stub
		return null;
	}

}
