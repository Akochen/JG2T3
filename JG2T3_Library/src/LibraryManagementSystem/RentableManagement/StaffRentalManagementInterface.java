package LibraryManagementSystem.RentableManagement;

import java.sql.SQLException;
import java.util.Scanner;

public class StaffRentalManagementInterface {
	
	public StaffRentalManagementInterface() throws SQLException {
		new RentableInventory();
		openStaffInterface();
	}

	private void openStaffInterface() {
		Scanner scanner = new Scanner(System.in);
        int method = 0;
        
        //Print UI choices
        System.out.println("Please select an operation: \n"
                + "1) View all rentals\n"
                + "2) Search rentals\n"
                + "3) Exit Rental Management");
        method = scanner.nextInt();
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
            case 3: break;
        }
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
        parameter = scanner.nextLine();
        scanner.close();
        
        if(!type.equals("")) {
            System.out.println("Results: ");
            inventory.searchRentals(type, parameter);
        }
    }
	
	public static void viewRentalUI(){
        System.out.println("All rentables:");
        RentalInventory inventory = new RentalInventory();
        inventory.viewRentals();
    }

}
