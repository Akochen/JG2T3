package LibraryManagementSystem.RentableManagement;

import java.sql.*;
import java.util.Scanner;

public class TestMain {
/* Error: could not find or load main class eclipse
 * Build Path --> Configure Build Path --> Java Build Path --> Libraries
 * Remove missing library (Java connector with red X)
 * Add correct Java connector path (Your own)
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Please select an operation: \n"
				+ "5) Check In\n"
				+ "4) View all rentals\n"
				+ "3) View all rentables\n"
				+ "2) Search Rentables\n"
				+ "1) Add a new rentable");
		Scanner scanner = new Scanner(System.in);
		int method = scanner.nextInt();
		switch (method) {
			case 1:
				addRentableUI();
				break;
			case 2:
				searchRentableUI();
				break;
			case 3:
				viewRentableUI();
				break;
			case 4:
				viewRentalUI();
				break;
			case 5:
				checkInUI();
				break;
			case 6: 
				//TODO check-out code
				break;
			default:
				break;
		}
	}
	
	private static void addRentableUI() {
		Scanner scanner = new Scanner(System.in);
		RentableInventory inventory = new RentableInventory();
		String title = "";
		String isbn = "";
		String condition = "";
		String genre = "";
		String roomNum = "";
		String sku = "";
		
		System.out.println("Please select the type of rentable that you wish to add.\n1) Book\n2) DVD\n3) E-Book\n4) Room"); 
		
		int type = scanner.nextInt();
		
		switch (type) {	
			case 1:
				//Asks for input information about Rentable
				System.out.print("Please input the sku: ");
				sku = scanner.nextLine();
				System.out.print("Please input the title: ");
				title = scanner.nextLine();
				System.out.print("Please input the ISBN: ");
				isbn = scanner.nextLine();
				System.out.print("Please input the condition: ");
				condition = scanner.nextLine();
				System.out.print("Please input the genre: ");
				genre = scanner.nextLine();
				scanner.close();
				
				//Creates Rentable and calls the method to add it to the db
				inventory.addRentable(new Rentable(sku, title, "11", isbn, condition, genre, "Book"));
				break;
			case 2: 
				//Asks for input information about Rentable
				System.out.print("Please input the title: ");
				title = scanner.nextLine();
				System.out.print("Please input the condition: ");
				condition = scanner.nextLine();
				System.out.print("Please input the genre: ");
				genre = scanner.nextLine();
				scanner.close();
				
				//Creates Rentable and calls the method to add it to the db
				inventory.addRentable(new Rentable(sku, "11", title, condition, genre));
				break;
			case 3:
				//Asks for input information about Rentable
				System.out.print("Please input the title: ");
				title = scanner.nextLine();
				System.out.print("Please input the isbn: ");
				isbn = scanner.nextLine();
				System.out.print("Please input the condition: ");
				condition = scanner.nextLine();
				System.out.print("Please input the genre: ");
				genre = scanner.nextLine();
				scanner.close();
				
				//Creates Rentable and calls the method to add it to the db
				inventory.addRentable(new Rentable(sku, "11", title, isbn, condition, genre, "EBook"));
				break;
			default: 
				scanner.close();
				System.out.print("Invalid rentable type. Rentable not added.");
				break;
		}
	}
	
	public static void searchRentableUI(){
		Scanner scanner = new Scanner(System.in);
		RentableInventory inventory = new RentableInventory();
		
		System.out.println("Please select an attribute to search by:\n"
				+ "1) SKU\n"
				+ "2) Title\n"
				+ "3) ISBN\n"
				+ "4) Condition\n"
				+ "5) Genre\n"
				+ "6) Type\n"
				+ "7) Room Number");
		int choice = scanner.nextInt();
		String type = "";
		if(choice == 1) {
			type = "sku";
		} else if(choice == 2) {
			type = "title";
		}else if(choice == 3) {
			type = "isbn";
		}else if(choice == 4) {
			type = "condition";
		}else if(choice ==5) {
			type = "genre";
		}else if(choice ==6) {
			type = "type";
		}else if(choice ==7) {
			type = "room_number";
		} else {
			System.out.println("Invalid input. Please try again.");
			searchRentableUI();
			return;
		}
		String parameter = "";
		System.out.print("Please input what you would like to search for. \nSearch: ");
		parameter = scanner.next();
		
		if(!type.equals("")) {
			System.out.println("Results:");
			inventory.searchRentables(type, parameter);
		}
	}
	
	public static void viewRentableUI(){
		
		RentableInventory inventory = new RentableInventory();
		inventory.viewRentables();
	}
	
	public static void viewRentalUI(){
		
		RentalInventory inventory = new RentalInventory();
		System.out.println(inventory.viewRentals());
	}
	
	public static void searchRentalUI() {
        Scanner scanner = new Scanner(System.in);
        RentalInventory inventory = new RentalInventory();
        
        System.out.println("Please select an attribute to search by:\n"
                + "1) SKU\n"
                + "2) User ID\n"
                + "3) Times Renewed");
        int choice = scanner.nextInt();
        String type = "";
        if(choice == 1) {
            type = "sku";
        } else if(choice == 2) {
            type = "title";
        }else if(choice == 3) {
            type = "isbn";
        } else {
            System.out.println("Invalid input. Please try again.");
            searchRentalUI();
            return;
        }
        String parameter = "";
        System.out.print("Please input what you would like to search for. \nSearch: ");
        parameter = scanner.next();
        
        if(!type.equals("")) {
            System.out.println("Results: ");
            inventory.searchRentals(type, parameter);
        }
    }

	public static void checkInUI() {
		Scanner scanner = new Scanner(System.in);
		RentalInventory inventory = new RentalInventory();
		
		System.out.println("Please input the ID for the item you wish to check in:");
		int id = scanner.nextInt();
		System.out.println("Item is reserved by: " + inventory.checkIn(id));
		System.out.println("Item was Checked In.");

	}
}
