package LibraryManagementSystem.RentableManagement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class RentalInventoryJDBCTest {
	
	// TODO testCheckOut()

	@Test
	void testSearchRentalObj() {
		ArrayList<Rental> expected = new ArrayList<>();
		expected.add(new Rental(
				14, 
				Instant.parse("2018-05-03T12:00:00.00Z"), 
				Instant.parse("2018-06-03T12:00:00.00Z"), 
				"21", 
				0));
		RentalInventoryJDBC ri = new RentalInventoryJDBC();
		ArrayList<Rental> actual = ri.searchRentalsObj("title", "1");
		assertIterableEquals(expected, actual);
	}
	
	@Test
	void testBuildRentalTable() {
		Rental t = new Rental(
				14, 
				Instant.parse("2018-05-03T12:00:00.00Z"), 
				Instant.parse("2018-06-03T12:00:00.00Z"), 
				"21", 
				0);
		ArrayList<Rental> tl = new ArrayList<>();
		tl.add(t);
		String expected = "SKU   | Start Date | End Date   | Times Renewed | UserId\n"
				        + "14    | 2018-05-03 | 2018-06-03 | 0             | 21    \n";
		String actual = RentalInventoryJDBC.buildRentalsTable(tl);
		assertEquals(expected, actual);
	}

}
