package LBMS;

import java.util.ArrayList;

public class RentalInventory implements IRentalInventory {
	
	private RentalInventoryJDBC inventory;
	
	public RentalInventory() {
		inventory = new RentalInventoryJDBC();
	}

	@Override
	public boolean checkIn(int sku) {
		return inventory.checkIn(sku);
	}

	@Override
	public boolean checkOut(int sku) {
		return inventory.checkOut(sku);
	}

	@Override
	public boolean renewRental(Rental r) {
		return inventory.renewRental(r);
	}

	@Override
	public ArrayList<Rental> searchRentals(String searchType, String searchParameters) {
		return inventory.searchRentals(searchType, searchParameters);
	}

	@Override
	public ArrayList<Rental> viewRentals() {
		return inventory.viewRentals();
	}

}
