package LBMS;
import java.util.ArrayList;
import java.sql.*;

import java.util.ArrayList;

public class RentableInventory implements IRentableInventory {
	
	private RentableInventoryJDBC inventory;
	
	public RentableInventory() {
		inventory = new RentableInventoryJDBC();
	}

	@Override
	public boolean addRentable(Rentable r) {
		return inventory.addRentable(r);
	}

	@Override
	public boolean isAvailable(String identifier, String type) {
		return inventory.isAvailable(identifier, type);
	}

	@Override
	public Rentable removeRentable(int rentableSKU) {
		return inventory.removeRentable(rentableSKU);
	}

	@Override
	public ArrayList<Rentable> searchRentables(String searchType, String searchParameter) {
		return inventory.searchRentables(searchType, searchParameter);
	}

	@Override
	public ArrayList<Rentable> viewRentables() {
		return inventory.viewRentables();
	}

}
