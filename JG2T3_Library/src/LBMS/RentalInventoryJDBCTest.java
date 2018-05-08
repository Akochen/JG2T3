package LBMS;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class RentalInventoryJDBCTest {

	@Test
	void testSearchRental() {
		ArrayList<Rental> expected = new ArrayList<>();
		expected.add(new Rental(14, Date.valueOf("2018-05-03"), Date.valueOf("2018-06-03"), 21, 0));
		RentalInventoryJDBC ri = new RentalInventoryJDBC();
		ArrayList<Rental> actual = ri.searchRentals("book", "1");
		assertIterableEquals(expected, actual);
	}

}
