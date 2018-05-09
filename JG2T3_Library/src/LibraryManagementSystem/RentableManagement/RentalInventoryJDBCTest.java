package LibraryManagementSystem.RentableManagement;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class RentalInventoryJDBCTest {

	@Test
	void testSearchRentalObj() {
		ArrayList<Rental> expected = new ArrayList<>();
		expected.add(new Rental(14, Date.valueOf("2018-05-03"), Date.valueOf("2018-06-03"), "21", 0));
		RentalInventoryJDBC ri = new RentalInventoryJDBC();
		ArrayList<Rental> actual = ri.searchRentalsObj("book", "1");
		assertIterableEquals(expected, actual);
	}
	
	@Test
	void testBuildRentalTable() {
		Rental t = new Rental(14, Date.valueOf("2018-05-03"), Date.valueOf("2018-06-03"), "21", 0);
		ArrayList<Rental> tl = new ArrayList<>();
		tl.add(t);
		String expected = "SKU   | Start Date | End Date   | Times Renewed | UserId\n"
				        + "14    | 2018-05-03 | 2018-06-03 | 0             | 21    \n";
		String actual = RentalInventoryJDBC.buildRentalsTable(tl);
		assertEquals(expected, actual);
	}

}
