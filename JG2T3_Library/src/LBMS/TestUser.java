package LBMS;
import java.sql.*;
import java.util.Scanner;
public class TestUser {
    
    public static String userID ="";
	static final String DRIVER = "com.mysql.jdbc.Driver";
	static final String DATABASE_URL = "jdbc:mysql://localhost/db_library?useSSL=false";
	static final String MYSQL_USERNAME ="root";
	static final String MYSQL_PASSWORD ="batman";
	private static boolean isLoggedIn = false;

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
    	// TODO Auto-generated method stub
   	 
    	System.out.println("Welcome to the Library Management System");
    	System.out.println("Choose an option: ");
    	System.out.println("1) Login");
    	System.out.println("2) Create an account");
    	Scanner sc = new Scanner(System.in);
    	int userChoice = sc.nextInt();
   	 
    	AccountCollection ac = new AccountCollection();
    	while(userChoice != 1 && userChoice !=2)
    	{
   		 System.out.println("Choose a correct option");
   		 userChoice = sc.nextInt();
    	}
    	switch(userChoice) {
    	case 1: login();
    			break;//login option
    	case 2: // Create user account
    			createUser();
    			isLoggedIn=(false);
    			break;
    	}  
	}
    
	private static void login() throws SQLException, ClassNotFoundException {
    	//database access check if username and password match
    	//if so returnID save in userID, set login = true
    	//else message user of error, loop back to login
   	 
   	  	Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
     	ResultSet rs = null;
     	PreparedStatement prepStatement = null;
    	 
     	String userName = new String();
     	String userPass = new String();
     	isLoggedIn = false;
    	 
   	 while (!isLoggedIn)
    	{
   		 //Prompt user for input
        	System.out.println("Enter username: ");
        	Scanner sc = new Scanner(System.in);
        	userName = sc.nextLine();
        	System.out.println("Enter Password: ");
        	userPass = sc.nextLine();
       	 
       	 
        	//Use for prepared statement
        	String sql = "select userID from useraccount where username = ? and password = ?";
       	 
        	//Fill in preparedstatement with value of username and user password
        	prepStatement = myConn.prepareStatement(sql);
        	prepStatement.setString(1, userName);
        	prepStatement.setString(2, userPass);
        	rs = prepStatement.executeQuery();
       	 
        	//If RS has been intialized, then we are logged in
        	if(rs.next())
        	{
	       		 System.out.println("You are logged in!");
	       		 isLoggedIn = true;
	       		 userID = rs.getString("userID");
        	}
        	//RS has no values in it so we didn't find a matching username/password
        	else
        	{
	       		System.out.println("\nInvalid username or password");
	       		System.out.println("Choose an option: ");
	        	System.out.println("1) Login");
	        	System.out.println("2) Create an Account");
	        	
	        	int userChoice = sc.nextInt();
	       	 
	        	AccountCollection ac = new AccountCollection();
	        	while(userChoice != 1 && userChoice !=2)
	        	{
		       		 System.out.println("Choose a correct option:\n");
		       		 userChoice = sc.nextInt();
	        	}
	        	switch(userChoice)
	        	{
	        	case 1: login();
	        			break;//login option
	        	case 2: // Create user account
	        			createUser();
	        			break;
	        	}  
        	}
	}
   	 
   	userName="";
   	userPass="";
   	mainMenu();
       	
}
	private static void createUser() throws SQLException, ClassNotFoundException{

		Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		Statement myState = myConn.createStatement();
     	ResultSet rstSet = null;
     	
     	String UserID = new String(),
     			FirstName = new String(), 
     			LastName = new String(),
     			Email = new String(),
     			Username = new String(),
     			Password = new String(), 
     			StreetNum = new String(), 
     			StreetName = new String(),
    			City = new String(),
    			State = new String(),
    			Zip = new String();

    	AccountCollection ac = new AccountCollection();
     	System.out.println("Enter ID (Must Start with U): ");
    	Scanner sc = new Scanner(System.in);
    	UserID = sc.nextLine();
    	while(UserID.charAt(0)!='U')
    	{
    		System.out.println("Enter ID (Must Start with U): ");
        	UserID = sc.nextLine();
    	}
    	String sqlID = "SELECT * FROM useraccount WHERE userid = '"+UserID+"'";
    	rstSet = myState.executeQuery(sqlID); 
    	while(rstSet.next()) 
    	{
    		System.out.println("UserID is already taken");
        	System.out.println("Enter UserID: ");
        	UserID = sc.nextLine();
        	sqlID = "SELECT * FROM useraccount WHERE userid = '"+UserID+"'";
        	rstSet = myState.executeQuery(sqlID); 
    	}
    	rstSet.close();
    	System.out.println("Enter First Name: ");
    	FirstName= sc.nextLine();
    	System.out.println("Enter LastName: ");
    	LastName = sc.nextLine();
    	System.out.println("Enter Username: ");
    	Username = sc.nextLine();
    	String sqlUsername = "SELECT * FROM useraccount WHERE username = '"+Username+"'";
    	rstSet = myState.executeQuery(sqlUsername); 
    	while(rstSet.next()) 
    	{
    		System.out.println("Username is already taken");
        	System.out.println("Enter Username: ");
        	Username = sc.nextLine();
        	sqlUsername = "SELECT * FROM useraccount WHERE username = '"+Username+"'";
        	rstSet = myState.executeQuery(sqlUsername); 
    	}
    	rstSet.close();
    	System.out.println("Enter Password: ");
    	Password = sc.nextLine();
    	System.out.println("Enter Email: ");
    	Email = sc.nextLine();
    	System.out.println("Enter Street Number: ");
    	StreetNum = sc.nextLine();
    	System.out.println("Enter Street Name: ");
    	StreetName = sc.nextLine();
    	System.out.println("Enter City: ");
    	City = sc.nextLine();
    	System.out.println("Enter State: ");
    	State = sc.nextLine();
    	System.out.println("Enter Zip: ");
    	Zip = sc.nextLine();
   	 
    	User temp = new User (UserID,FirstName,LastName,Email,Username,Password, new Address(StreetNum, StreetName,
    			City,State,Zip), 0.0);  
    	
    	System.out.println("Account Created: Returning to Login");
    	ac.add(temp);   	
    	login();
	}
	
	private static void mainMenu() throws SQLException, ClassNotFoundException {
		System.out.println("Main Menu ");
		System.out.println("Please choose an option: ");
		System.out.println("1. Account Management");
		System.out.println("2. Reservation Management");
		System.out.println("3. Rentables Management");
		System.out.println("4. Logout");
		
		Scanner sc = new Scanner(System.in);
		int userChoice = sc.nextInt();
		
		while(userChoice < 1 || userChoice >4) {
			System.out.println("Please choose a correct option: ");
			System.out.println("1. Account Management");
			System.out.println("2. Reservation Management");
			System.out.println("3. Rentables Management");
			System.out.println("4. Logout");
			userChoice = sc.nextInt();
			
		}
		
		
		switch(userChoice) {
    	case 1: accountManagement();
    			break;
    	case 2: reservationManagement(); 
    			mainMenu();
    			break;
    	case 3: rentableManagement();
    			mainMenu();
    			break;
    	case 4: logout();
    			break;
		}

	
	}


	private static void logout() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		userID="";
		isLoggedIn = false;
		System.out.println("You have successfully logged out!");

		System.out.println("Welcome to the Library Management System");
    	System.out.println("Choose an option: ");
    	System.out.println("1) Login");
    	System.out.println("2) Create an account");
    	Scanner sc = new Scanner(System.in);
    	int userChoice = sc.nextInt();
   	 
    	AccountCollection ac = new AccountCollection();
    	while(userChoice != 1 && userChoice !=2)
    	{
   		 System.out.println("Choose a correct option");
   		 userChoice = sc.nextInt();
    	}
    	switch(userChoice) {
    	case 1: login();
    			mainMenu();
    			break;//login option
    	case 2: // Create user account
    			isLoggedIn=(false);
    			
    			break;
    	}  
		
	}

	private static void changePassword() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		AccountCollection ac = new AccountCollection();
		Scanner sc = new Scanner(System.in);
		String newPassword;
		
		System.out.println("Enter New Password: ");
		newPassword = sc.next();
		Staff tempUser = ac.search(userID);
		
		if(ac.changePassword(newPassword, tempUser))
		{
			System.out.println("Your password has been changed");
			mainMenu();
		}
		
		else 
		{
			System.out.println("Your password has not been changed");
			mainMenu();
		}
	}
	private static void editAccount() throws ClassNotFoundException {
     	String UserID = new String(),
     			FirstName = new String(), 
     			LastName = new String(),
     			Email = new String(),
     			StreetNum = new String(), 
     			StreetName = new String(),
    			City = new String(),
    			State = new String(),
    			Zip = new String();
     	AccountCollection ac = new AccountCollection();
     	
		System.out.println("Enter ID (Must Start with U): ");
    	Scanner sc = new Scanner(System.in);
    	UserID = sc.nextLine();
    	while(!userID.equals(UserID))
    	{
    		System.out.println("Incorrect ID, Try Again: ");
    		System.out.println(userID);
        	UserID = sc.nextLine();
    	}
    	System.out.println("Enter First Name: ");
    	FirstName= sc.nextLine();
    	System.out.println("Enter LastName: ");
    	LastName = sc.nextLine();
    	System.out.println("Enter Email: ");
    	Email = sc.nextLine();
    	System.out.println("Enter Street Number: ");
    	StreetNum = sc.nextLine();
    	System.out.println("Enter Street Name: ");
    	StreetName = sc.nextLine();
    	System.out.println("Enter City: ");
    	City = sc.nextLine();
    	System.out.println("Enter State: ");
    	State = sc.nextLine();
    	System.out.println("Enter Zip: ");
    	Zip = sc.nextLine();
    	
    	User currentUser = (User) ac.search(userID);
    	Address newAddress = new Address(StreetNum,StreetName,City,State,Zip);
    	currentUser.setfName(FirstName);
    	currentUser.setlName(LastName);
    	currentUser.setEmail(Email);
    	currentUser.getAddress().setStreetNum(StreetNum);
    	currentUser.getAddress().setStreetName(StreetName);
    	currentUser.getAddress().setCity(City);
    	currentUser.getAddress().setState(State);
    	currentUser.getAddress().setZipCode(Zip);
    	
    	try {
			ac.update(currentUser);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	System.out.println("User has been updated");
    	try {
			mainMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			mainMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void viewAccount() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		AccountCollection ac = new AccountCollection();
		System.out.println(ac.search(userID));
		try {
			mainMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void accountManagement() throws ClassNotFoundException {
		System.out.println("Account Management\n");
		System.out.println("Choose an Option:");
		Scanner sc = new Scanner(System.in);
		System.out.println("1. View Account");
		System.out.println("2. Edit Account");
		System.out.println("3. Change password");
		int accountManagementChoice = sc.nextInt();
		
		while(accountManagementChoice < 1 || accountManagementChoice >3) {
			System.out.println("Please choose a correct option: ");
			System.out.println("1. View Account");
			System.out.println("2. Edit Account");
			System.out.println("3. Change password");
			accountManagementChoice = sc.nextInt();
		}
		
		switch(accountManagementChoice) {
		case 1: viewAccount();
				break;
		case 2: editAccount();
				break;
		case 3: try {
				changePassword();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
		}
		
	}

	private static void reservationManagement() throws ClassNotFoundException, SQLException {
	      new UserRMI(userID);
	      
	}	
	
	
	private static void rentableManagement() {
		System.out.println("You are in rentables");
		
	}

	
}




