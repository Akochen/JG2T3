package LibraryManagementSystem.RentableManagement;

import java.sql.SQLException;
import java.util.Scanner;

public class UserRentableManagementInterface {
	private String userID;
	
	public UserRentableManagementInterface(String userID) throws SQLException {
		this.userID = userID;
		openInterface();
	}
	
	private void openInterface() throws SQLException {
		System.out.println("\nWould you like to search rentables?\n"
                + "1) Yes\n"
                + "2) No");
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());
        
        if(choice == 1) {
            searchRentableUI();
        } else {
            return;
        }
	}
	
	public static void searchRentableUI() throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        RentableInventory inventory = new RentableInventory();
        
        System.out.println("\nPlease select an attribute to search by:\n"
                + "1) Title\n"
                + "2) ISBN\n"
                + "3) Condition\n"
                + "4) Genre\n"
                + "5) Type\n");
        int choice = Integer.parseInt(scanner.nextLine());
        String type = "";
        if(choice == 1) {
            type = "title";
        }else if(choice == 2) {
            type = "isbn";
        }else if(choice == 3) {
            type = "condition";
        }else if(choice ==4) {
            type = "genre";
        }else if(choice ==5) {
            type = "type";
        } else {
            System.out.println("Invalid input. Please try again.");
            searchRentableUI();
            return;
        }
        String parameter = "";
        System.out.print("Please input what you would like to search for. \nSearch: ");
        parameter = scanner.next();
        
        System.out.println("Results:\n" + inventory.searchRentables(type, parameter));
    }

}
