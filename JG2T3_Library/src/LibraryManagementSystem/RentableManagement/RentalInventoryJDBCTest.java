package LibraryManagementSystem.RentableManagement;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class RentalInventoryJDBCTest {
	
	@Test
	void testCheckOutObj() {
		RentalInventoryJDBC ri = new RentalInventoryJDBC();
		Optional<Rental> r = ri.checkOutObj("", "");
		assertFalse(r.isPresent());
		// Test cases TODO more cases
		String rentableId = "4444449";
		String userId = "U000001";
		// Test logic
		Rental expected = new Rental(rentableId, LocalDate.now(), LocalDate.now().plus(Period.ofWeeks(2)), userId, 0);
		Optional<Rental> actualOpt = ri.checkOutObj(rentableId, userId);
		assertTrue(actualOpt.isPresent());
		Rental actual = actualOpt.get();
		assertEquals(expected, actual);
		
		// Rewind database after test
		try (
				Connection conn = JDBCConfig.getConnection();
				Statement s = conn.createStatement();
				) {
			conn.setAutoCommit(false);
			s.execute("DELETE FROM rental WHERE rentableId = " + rentableId);
			s.execute("UPDATE rentable SET isAvailable = 1 WHERE rentableId = " + rentableId);
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
		
		actual = ri.searchRentalsObj("time_renewed", "1");
		assertEquals(actual.size(), 3);
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
