package LibraryManagementSystem.RentableManagement;

import java.sql.SQLException;
import java.util.Scanner;

public class StaffRentableManagementInterface {

	public StaffRentableManagementInterface() throws SQLException {
		openStaffInterface();
	}

	private void openStaffInterface() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		int method;

		// Print UI choices
		System.out.println("\nPlease select an operation: \n"
				+ "1) View all rentables\n"
				+ "2) Search Rentables\n"
				+ "3) Add a new rentable\n"
				+ "4) Remove a rentable\n"
				+ "5) Exit Rentable Management");

		method = Integer.parseInt(scanner.nextLine());
		// scanner.close();

		switch (method) {
		case 1:
			viewRentableUI();
			openStaffInterface();
			break;
		case 2:
			searchRentableUI();
			openStaffInterface();
			break;
		case 3:
			addRentableUI();
			openStaffInterface();
			break;
		case 4:
			removeRentableUI();
			openStaffInterface();
		case 5:
			break;
		}
	}

	private static void addRentableUI() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		RentableInventory inventory = new RentableInventory();
		String upc = "";
		String title = "";
		String isbn = "";
		String condition = "";
		String genre = "";
		String sku = "";

		System.out.println("Please select the type of rentable that you wish to add." + "\n1) Book" + "\n2) DVD"
				+ "\n3) E-Book\n" + "");

		int type = Integer.parseInt(scanner.nextLine());

		switch (type) {
		case 1:
			// Asks for input information about Rentable
			System.out.print("Please input the ID: ");
			sku = scanner.nextLine();
			System.out.print("Please input the UPC: ");
			upc = scanner.nextLine();
			System.out.print("Please input the title: ");
			title = scanner.nextLine();
			System.out.print("Please input the ISBN: ");
			isbn = scanner.nextLine();
			System.out.print("Please input the condition: ");
			condition = scanner.nextLine();
			System.out.print("Please input the genre: ");
			genre = scanner.nextLine();
			// scanner.close();

			if (sku.equals("") || upc.equals("") || title.equals("") || isbn.equals("") || condition.equals("")
					|| genre.equals("")) {
				System.out.println("Error: One or more inputs is blank! Unable to add book!");
			}

			// Creates Rentable and calls the method to add it to the db
			if(inventory.addRentable(new Rentable(sku, upc, title, isbn, condition, genre, "Book", "1"))) {
				System.out.println("Rentable Successfully added!");
			} else {
				System.out.println("Error: Unable to add rentable!");
			}
			break;
		case 2:
			// Asks for input information about Rentable
			System.out.print("Please input the ID: ");
			sku = scanner.nextLine();
			System.out.print("Please input the UPC: ");
			upc = scanner.nextLine();
			System.out.print("Please input the title: ");
			title = scanner.nextLine();
			System.out.print("Please input the condition: ");
			condition = scanner.nextLine();
			System.out.print("Please input the genre: ");
			genre = scanner.nextLine();
			// scanner.close();

			if (sku.equals("") || upc.equals("") || title.equals("") || condition.equals("") || genre.equals("")) {
				System.out.println("Error: One or more inputs is blank! Unable to add DVD!");
			}

			// Creates Rentable and calls the method to add it to the db
			if(inventory.addRentable(new Rentable(sku, upc, title, condition, genre, "1"))) {
				System.out.println("Rentable Successfully added!");
			} else {
				System.out.println("Error: Unable to add rentable!");
			}
			break;
		case 3:
			// Asks for input information about Rentable
			System.out.print("Please input the ID: ");
			sku = scanner.nextLine();
			System.out.print("Please input the UPC: ");
			upc = scanner.nextLine();
			System.out.print("Please input the title: ");
			title = scanner.nextLine();
			System.out.print("Please input the isbn: ");
			isbn = scanner.nextLine();
			System.out.print("Please input the condition: ");
			condition = scanner.nextLine();
			System.out.print("Please input the genre: ");
			genre = scanner.nextLine();
			// scanner.close();

			if (sku.equals("") || upc.equals("") || title.equals("") || isbn.equals("") || condition.equals("")
					|| genre.equals("")) {
				System.out.println("Error: One or more inputs is blank! Unable to add E-Book!");
			}

			// Creates Rentable and calls the method to add it to the db
			if(inventory.addRentable(new Rentable(sku, upc, title, isbn, condition, genre, "EBook", "1"))) {
				System.out.println("Rentable Successfully added!");
			} else {
				System.out.println("Error: Unable to add rentable!");
			}
			break;
		default:
			// scanner.close();
			System.out.print("Invalid rentable type. Rentable not added.");
			break;
		}
	}

	public static void viewRentableUI() throws SQLException {
		RentableInventory inventory = new RentableInventory();
		System.out.println("All rentables:\n" + inventory.viewRentables());
	}

	public static void searchRentableUI() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		RentableInventory inventory = new RentableInventory();

		System.out.println("Please select an attribute to search by:\n" + "1) Rentable ID\n" + "2) UPC\n" + "3) Title\n" + "4) ISBN\n"
				+ "5) Condition\n" + "6) Genre\n" + "7) Type\n" + "");
		int choice = Integer.parseInt(scanner.nextLine());
		String type = "";
		if (choice == 1) {
			type = "rentableId";
		} else if (choice == 2) {
			type = "upc";
		} else if (choice == 3) {
			type = "title";
		} else if (choice == 4) {
			type = "isbn";
		} else if (choice == 5) {
			type = "condition";
		} else if (choice == 6) {
			type = "genre";
		} else if (choice == 7){
			type = "type";
		}else{
			System.out.println("Invalid input. Please try again.");
			searchRentableUI();
			return;
		}
		String parameter = "";
		System.out.print("Please input what you would like to search for. \nSearch: ");
		parameter = scanner.nextLine();
		
		System.out.println("Results:" + inventory.searchRentables(type, parameter));
	}

	public static void removeRentableUI() {
		RentableInventory inventory = new RentableInventory();
		System.out.print("Please enter the ID of the rental you wish to remove: ");
		Scanner scanner = new Scanner(System.in);
		String sku = scanner.nextLine();
		System.out.println("Are you sure you would like to remove the rentable with an ID of " + sku
				+ "? This cannot be undone!" + "\n1) Yes" + "\n2) No");
		int choice;
		try {
			choice = Integer.parseInt(scanner.nextLine());
		} catch (Exception e) {
			System.out.println("Error: Not a valid option. Please restart the operation.");
			return;
		}

		if (choice == 1) {
			Rentable r;
			r = inventory.removeRentable(sku);
			if(r != null) {
				System.out.println("Rentable: " + (r).toString() + " has been removed successfully.");
			} else {
				System.out.println("Error: Unable to remove rentable");
			}
		} else if (choice == 2) {
			System.out.println("Cancelling rentable removal.");
			return;
		} else {
			System.out.println("Invalid Selection: Cancelling rentable removal.");
		}
	}

}
