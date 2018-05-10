package LibraryManagementSystem.RentableManagement;

import java.sql.SQLException;
import java.util.Scanner;
import LibraryManagementSystem.ReservationManagement.*;


public class UserRentableManagementInterface {
	private static String userID;
	
	public UserRentableManagementInterface(String userID) throws SQLException, ClassNotFoundException {
		this.userID = userID;
		openInterface();
	}
	
	private void openInterface() throws SQLException, ClassNotFoundException {
		System.out.println("\nWhat would you like to do?\n"
                + "1) Search Rentables\n"
                + "2) View Rentables");
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());
        
        if(choice == 1) {
            searchRentableUI();
        } else if(choice == 2){
        	viewRentableUI();
        } else {
            return;
        }
	}
	
	public static void searchRentableUI() throws SQLException, ClassNotFoundException
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
        parameter = scanner.nextLine();
        
        System.out.println("Results:\n" + inventory.searchRentables(type, parameter));
        
		System.out.println("\nWould you like to reserve a rentable?\n"
                + "1) Yes\n"
                + "2) No");
        int choice2 = Integer.parseInt(scanner.nextLine());
        
        if(choice2 == 1) {
        	 System.out.println("Please input the UPC of the Rentable you would like to reserve.\n");
        	 String newUpc = scanner.nextLine();
        	 new ReservationCollectionJDBC();
        	 Reservation itemReservation = ReservationCollectionJDBC.createReservation(newUpc, userID, "ITEM");
        	 
        	 if (itemReservation == null)
             {
                 System.out.println("Unable to create room reservation.");
             } else
             {
                 System.out.println("Created a new item reservation...\n" + itemReservation);
             }
        } else {
            return;
        }
    }
	
	public static void viewRentableUI() throws SQLException {
		RentableInventory inventory = new RentableInventory();
		System.out.println("All rentables:\n" + inventory.viewRentables());
	}
}
