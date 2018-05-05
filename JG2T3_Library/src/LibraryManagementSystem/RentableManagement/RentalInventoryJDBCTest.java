package LibraryManagementSystem.RentableManagement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RentalInventoryJDBCTest {

	@Test
	void testSearchRental() {
		RentalInventoryJDBC ri = new RentalInventoryJDBC();
		assertTrue(ri.searchRentals("book", "1"));
	}

}
