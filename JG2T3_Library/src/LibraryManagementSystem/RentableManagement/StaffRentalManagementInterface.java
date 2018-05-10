package LibraryManagementSystem.RentableManagement;

import java.sql.SQLException;
import java.util.Scanner;

public class StaffRentalManagementInterface {
	
	public StaffRentalManagementInterface() throws SQLException {
		new RentableInventory();
		openStaffInterface();
	}

	private void openStaffInterface() {
		//Scanner sc = new Scanner(System.in);
		//sc.close();
		Scanner scanner = new Scanner(System.in);
        int method = 0;
        
        //Print UI choices
        System.out.println("Please select an operation: \n"
                + "1) View all rentals\n"
                + "2) Search rentals\n"
                + "3) Check out\n"
                + "4) Check In\n"
                + "5) Add fees to overdue rentals\n"
                + "6) Exit Rental Management");
        method = Integer.parseInt(scanner.nextLine());
        
        //scanner.close();
        switch (method) {
            case 1:
                viewRentalUI();
                openStaffInterface();
                break;
            case 2:
                searchRentalUI();
                openStaffInterface();
                break;
			case 3:
				checkOutUI();
				break;               
            case 4:
            	checkInUI();
            	break;
            case 5: 
            	addFeeUI();
            	break;
            case 6: break;
        }
	}
	
	public static void searchRentalUI() {
        Scanner scanner = new Scanner(System.in);
        RentalInventory inventory = new RentalInventory();
        
        System.out.println("Please select an attribute to search by:\n"
                + "1) Rentable ID\n"
                + "2) User ID\n"
                + "3) Times Renewed");
        int choice = Integer.parseInt(scanner.nextLine());
        String type = "";
        if(choice == 1) {
            type = "rentableid";
        } else if(choice == 2) {
            type = "userid";
        }else if(choice == 3) {
            type = "times_renewed";
        } else {
            System.out.println("Invalid input. Please try again.");
            searchRentalUI();
            return;
        }
        String parameter = "";
        System.out.print("Please input what you would like to search for. \nSearch: ");
        parameter = scanner.nextLine();
        //scanner.close();
        
        if(!type.equals("")) {
            System.out.println("Results:\n");
            System.out.println(inventory.searchRentals(type, parameter));
        }
    }
	
	public static void viewRentalUI(){
        System.out.println("All rentals:\n");
        RentalInventory inventory = new RentalInventory();
        System.out.println(inventory.viewRentals());
    }

	public static void checkInUI() {
		Scanner scanner = new Scanner(System.in);
		RentalInventory inventory = new RentalInventory();
		
		System.out.println("Please input the ID for the item you wish to check in:");
		int id = scanner.nextInt();
		System.out.println(inventory.checkIn(id));

	}
	
	private void addFeeUI() {
		RentalInventory inventory = new RentalInventory();
		if(inventory.addFee()) {
			System.out.println("Overdue rentals charged.");
		} else {
			System.out.println("Unable to charge rentals. Are there any overdue?");
		}
	}
	
	private void checkOutUI() {
		RentalInventory inventory = new RentalInventory();
		Scanner scanner = new Scanner(System.in);
		System.out.print("Please input the Rentable ID: ");
		String rentable = scanner.nextLine();
		System.out.print("Please input the user's ID: ");
		String user  = scanner.nextLine();
		
		System.out.println(inventory.checkOut(rentable, user));
		
	}
	
}
