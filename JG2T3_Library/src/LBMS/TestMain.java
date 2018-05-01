package LBMS;

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
		Scanner scanner = new Scanner(System.in);
		int method = scanner.nextInt();
		switch (method) {
			case 1:
				addRentableUI();
			case 2:
				//TODO check-in code
			case 3: 
				//TODO check-out code
			case 4:
				isAvailableUI();
			case 5:
				searchRentableUI();
			case 6:
				viewRentableUI();
				
		}
	}
	
	private static void addRentableUI() {
		Scanner scanner = new Scanner(System.in);
		RentableInventory inventory = new RentableInventoryJDBC();
		String title = "";
		String isbn = "";
		String condition = "";
		String genre = "";
		String roomNum = "";
		int sku = 100;
		
		System.out.println("Please select the type of rentable that you wish to add.\n1) Book\n2) DVD\n3) E-Book\n4) Room"); 
		
		int type = scanner.nextInt();
		
		switch (type) {	
			case 1:
				//Asks for input information about Rentable
				/*System.out.print("Please input the sku: ");
				sku = scanner.nextInt();*/
				System.out.print("Please input the title: ");
				title = scanner.next();
				System.out.print("Please input the ISBN: ");
				isbn = scanner.next();
				System.out.print("Please input the condition: ");
				condition = scanner.next();
				System.out.print("Please input the genre: ");
				genre = scanner.next();
				
				//Creates Rentable and calls the method to add it to the db
				inventory.addRentable(new Rentable(sku, title, isbn, condition, genre, "Book"));
				break;
			case 2: 
				//Asks for input information about Rentable
				System.out.print("Please input the title: ");
				title = scanner.next();
				System.out.print("Please input the condition: ");
				condition = scanner.next();
				System.out.print("Please input the genre: ");
				genre = scanner.next();
				
				//Creates Rentable and calls the method to add it to the db
				inventory.addRentable(new Rentable(100, title, condition, genre));
				break;
			case 3:
				//Asks for input information about Rentable
				System.out.print("Please input the title: ");
				title = scanner.next();
				System.out.print("Please input the isbn: ");
				isbn = scanner.next();
				System.out.print("Please input the condition: ");
				condition = scanner.next();
				System.out.print("Please input the genre: ");
				genre = scanner.next();
				
				//Creates Rentable and calls the method to add it to the db
				inventory.addRentable(new Rentable(100, title, isbn, condition, genre, "EBook"));
				break;
			case 4:
				//Asks for input information about Rentable
				System.out.print("Please input the room number: ");
				roomNum = scanner.next();
				
				//Creates Rentable and calls the method to add it to the db
				inventory.addRentable(new Rentable(100, roomNum));
				break;
			default: 
				System.out.print("Invalid rentable type. Rentable not added.");
				break;
		}
	}
	
	public static void isAvailableUI(){
		Scanner scanner = new Scanner(System.in);
		RentableInventory inventory = new RentableInventoryJDBC();
		
		if(inventory.isAvailable("22", "book"))
			System.out.println("The book is available.");
		else
			System.out.println("The book is not available.");
	}
	
	public static void searchRentableUI(){
		Scanner scanner = new Scanner(System.in);
		RentableInventory inventory = new RentableInventoryJDBC();
		inventory.searchRentables("title", "the book");
	}
	
	public static void viewRentableUI(){
		
		RentableInventory inventory = new RentableInventoryJDBC();
		inventory.viewRentables();
	}

}
