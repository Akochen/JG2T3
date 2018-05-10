package LibraryManagementSystem.RentableManagement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class RentalInventoryJDBCTest {
	
	// TODO testCheckOut()

	@Test
	void testSearchRentalObj() {
		ArrayList<Rental> expected = new ArrayList<>();
		expected.add(new Rental(
				"5555557", 
				LocalDate.parse("2018-05-10"), 
				LocalDate.parse("2018-05-14"), 
				"U000001", 
				0));
		RentalInventoryJDBC ri = new RentalInventoryJDBC();
		ArrayList<Rental> actual = ri.searchRentalsObj("rentableid", "5555557");
		assertIterableEquals(expected, actual);
	}
	
	@Test
	void testBuildRentalTable() {
		Rental t = new Rental(
				"14", 
				LocalDate.parse("2018-05-03"), 
				LocalDate.parse("2018-06-03"), 
				"21", 
				0);
		ArrayList<Rental> tl = new ArrayList<>();
		tl.add(t);
		String expected = "RentableId | Start Date | End Date   | Times Renewed | UserId\n"
				        + "14         | 2018-05-03 | 2018-06-03 | 0             | 21    \n";
		String actual = RentalInventoryJDBC.buildRentalsTable(tl);
		assertEquals(expected, actual);
	}

}
