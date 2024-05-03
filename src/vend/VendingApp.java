package vend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class creates a console based vending machine.
 * 
 * To access the hidden maintenance menu, enter -1 from the main menu. 
 * Enter "snacks" as the password.
 * 
 * @author Fergal Bittles
 *
 */
public class VendingApp {
	
	static String title = "Vending Machine Menu";
	static String options[] = { "List All Items", "Insert Coins", "Make Purchase", "Quit" };
	static Menu myMenu = new Menu(title, options);
	static final int QUIT = options.length;
	static Scanner in = new Scanner(System.in);
	static VendingMachine vender = restoreMachineData(); // Restore machine data from CSV
	
	public static void main(String[] args) {
		int choice;
		
		do {
			choice = myMenu.getChoice();
			if (choice != QUIT) {
				processChoice(choice);
			}
		} while (choice != QUIT);
		
		saveMachineData(); // Save machine data to CSV
		
		System.out.println("\nFinished - Goodbye!");
		in.close();
	}
	
	/**
	 * Creates a new VendingMachine object and populates it with 6 different VendItems.
	 * @return - The VendingMachine object is returned
	 */
	private static VendingMachine createVendItems() {
		VendingMachine vend = new VendingMachine("The Vending Company", 10);

		VendItem item1 = new VendItem("Haribo", 1, 10);
		VendItem item2 = new VendItem("Skittles", 0.8, 4);
		VendItem item3 = new VendItem("Snickers", 0.8);
		VendItem item4 = new VendItem("Coke", 1.2, 1);
		VendItem item5 = new VendItem("Diet Coke", 1.2, 5);
		VendItem item6 = new VendItem("Toffee Crisp", 0.9, 2);

		vend.addNewItem(item1);
		vend.addNewItem(item2);
		vend.addNewItem(item3);
		vend.addNewItem(item4);
		vend.addNewItem(item5);
		vend.addNewItem(item6);

		return vend;
	}
	
	/**
	 * Processes the user choice from the main menu.
	 * @param choice - The users choice
	 */
	private static void processChoice(int choice) {
		switch(choice) {
			case(-1): serviceMenu(); break;
			case(1): listAllItems(); break;
			case(2): insertCoins(); break;
			case(3): makePurchase(); break;
			default: System.out.println("\nError: Invalid Choice\n"); break;
		}
	}
	
	/**
	 * Lists all of the items which are in the vending machine.
	 */
	private static void listAllItems() {
		System.out.println("\nOK - List All Items");
		System.out.println("+++++++++++++++++++\n");
		
		String[] items = vender.listItems();
		
		if (items == null) {
			System.out.println("Error: There are no items to list\n");
			return;
		} 
		
		
		for (int i = 0; i < items.length; i++) {
			System.out.println((i + 1) + ". " + items[i]);
		}	
	}
	
	/**
	 * Allows the user to insert coins into the vending machine.
	 * Also shows the current user money inserted into the machine.
	 */
	private static void insertCoins() {
		System.out.println("\nOK - Insert Coins");
		System.out.println("+++++++++++++++++\n");
		System.out.printf("Current money: £%.2f", vender.getUserMoney());
		System.out.println("\n");
		
		String options[] = {"5p", "10p", "20p", "50p", "£1", "£2"};
		for(int i = 0; i < options.length; i++) {
			System.out.println((i+1) + ". " + options[i]);
		}
		
		// Warn the user if coin amount is low
		checkCoinAmount();
		System.out.println();
		
		// Get input from the user
		int coin = getInput("Enter option number: ");
		
		if (coin <= 0 || coin > options.length) {
			System.out.println("\nError - Invalid choice. Returning to menu\n");
			return;
		}
		
		// Process the user input
		vender.insertCoin(coin);
		System.out.println("\nYou inserted " + options[coin - 1]);
		System.out.printf("New balance: £%.2f", vender.getUserMoney());
		System.out.println("\n");
	}
	
	/**
	 * Checks the amount of coins remaining within the vending machine and prints a warning message if coins are low.
	 */
	private static void checkCoinAmount() {
		if (vender.getCoinAmount(5) < 1
				|| vender.getCoinAmount(10) < 1
				|| vender.getCoinAmount(20) < 2
				|| vender.getCoinAmount(50) < 1 
				|| vender.getCoinAmount(1) < 1) {
			System.out.println("\nWARNING: Coins low, please enter exact amount");
		}
	}
	
	/**
	 * Allows the user to purchase items from the vending machine.
	 */
	private static void makePurchase() {
		System.out.println("\nOK - Make a Purchase");
		System.out.println("++++++++++++++++++++\n");
		System.out.printf("Current money: £%.2f", vender.getUserMoney());
		System.out.println("\n");
		
		String[] items = vender.listItems();
		
		if (items == null) {
			System.out.println("Error: There are no items to purchase\n");
			return;
		} 
		
		for (int i = 0; i < items.length; i++) {
			System.out.println((i + 1) + ". " + items[i]);
		}

		// Get input from the user
		int item = getInput("Enter option number: ");

		// Attempt to purchase the selected item
		item--;
		String result = vender.purchaseItem(item);
		System.out.println("\n" + result);
		System.out.println();
	}
	
	/**
	 * Allows the user to access the hidden maintenance menu.
	 * The user must enter "snacks" as the password.
	 */
	private static void serviceMenu() {
		System.out.print("\nEnter maintenance password: ");
		String password = in.nextLine();
		
		if (!password.equals("snacks")) {
			System.out.println("\nError: Invalid password. Returning to menu\n");
			return;
		}
		
		System.out.println();
		String title = "Maintenance Menu";
		String options[] = { "Get System Info", "Reset System", "Set Machine Status", "Restock an Item", "Add a New Item", "Return to Customer Menu" };
		Menu serviceMenu = new Menu(title, options);
		final int RETURN = options.length;

		int choice;
		do {
			choice = serviceMenu.getChoice();
			if (choice != RETURN) {
				processServiceChoice(choice);
			}
		} while (choice != RETURN);

		System.out.println("\nConfirmed - Returning to customer menu\n");
	}
	
	/**
	 * Processes the choice made from the maintenance menu.
	 * @param choice - The user choice
	 */
	private static void processServiceChoice(int choice) {
		switch(choice) {
			case(1): getSystemInfo(); break;
			case(2): resetSystem(); break;
			case(3): setMachineStatus(); break;
			case(4): restockAnItem(); break;
			case(5): addNewItem(); break;
			default: System.out.println("\nError: Invalid Choice\n"); break;
		}
	}

	/**
	 * Prints the system information.
	 */
	private static void getSystemInfo() {
		System.out.println("\nOK - Getting System Info");
		System.out.println("++++++++++++++++++++++++\n");
		System.out.println(vender.getSystemInfo());
	}
	
	/**
	 * Allows the user to reset the system.
	 * This will empty the machine of all items/money and set the status back to vending mode.
	 */
	private static void resetSystem() {
		System.out.println("\nOK - Reset System");
		System.out.println("+++++++++++++++++\n");
		
		if (!vender.getStatus().equals("Service Mode")) {
			System.out.println("Failure: Enable \"SERVICE MODE\" to reset the system\n");
			return;
		}
		
		System.out.println("WARNING:\n");
		System.out.println("Resetting the system will empty all money/items and set the machine to vending mode\n");
		System.out.print("Are you sure you wish to continue? Enter Y or N: ");
		String str = in.nextLine();
		str = str.trim();
		
		if (str.equals("")) {
			System.out.println("\nINVALID INPUT - The system has not been reset\n");
			return;
		}
		
		char confirm = str.charAt(0);
		
		if (confirm == 'Y' || confirm == 'y') {
			vender.reset();
			System.out.println("\nYES - The system has been reset\n");
		} else if (confirm == 'N' || confirm == 'n') {
			System.out.println("\nNO - The system has not been reset\n");
		} else {
			System.out.println("\nINVALID INPUT - The system has not been reset\n");
		}
	}
	
	/**
	 * Sets the status of the vending machine to "Vending Mode" or "Service Mode".
	 */
	private static void setMachineStatus() {
		System.out.println("\nOK - Set Machine Status");
		System.out.println("+++++++++++++++++++++++\n");
		System.out.println("The machine is currently in " + vender.getStatus());
		
		Status newMode;
		if (vender.getStatus().equals("Vending Mode")) {
			newMode = Status.SERVICE_MODE;
		} else {
			newMode = Status.VENDING_MODE;
		}
		
		System.out.println("\nWould you like to switch to " + newMode.getStatus() + "?\n");
		System.out.print("Enter Y or N: ");
		String str = in.nextLine();
		str = str.trim();
		
		if (str.equals("")) {
			System.out.println("\nINVALID INPUT - The status has not been changed\n");
			return;
		}
		
		char confirm = str.charAt(0);
		
		if (confirm == 'Y' || confirm == 'y') {
			vender.setStatus(newMode);
			System.out.println("\nYES - The machine is now in " + newMode.getStatus() + "\n");
		} else if (confirm == 'N' || confirm == 'n') {
			System.out.println("\nNO - The status has not been changed\n");
		} else {
			System.out.println("\nINVALID INPUT - The status has not been changed\n");
		}
	}
	
	/**
	 * Allows the user to restock a VendItem inside the vending machine.
	 */
	private static void restockAnItem() {
		System.out.println("\nOK - Restock an Item");
		System.out.println("++++++++++++++++++++\n");
		
		// Machine must be set to service mode
		if (!vender.getStatus().equals("Service Mode")) {
			System.out.println("Failure: Enable \"SERVICE MODE\" to restock items\n");
			return;
		}
		
		String[] items = vender.listItems();
		
		if (items == null) {
			System.out.println("Error: There are no items to restock\n");
			return;
		} 
		
		System.out.println("Items and their quantity listed below:\n");
		for (int i = 0; i < items.length; i++) {
			System.out.println((i + 1) + ". " + items[i]);
		}

		// Get input from the user
		int item = getInput("Enter option number to select an item: ");
		System.out.println();
		
		int quantity = getInput("Enter the quantity that you would like to add: ");
		System.out.println();

		// Decrement due to zero indexing
		item--;

		String res = "";
		
		if (item >= vender.getItemCount() || item < 0) {
			res += "Failure: Invalid item selected";
		} else if (vender.getItemCount() == 0) {
			res += "Failure: The vending machine does not contain any items to restock";
		} else if (quantity < 1) {
			res += "Failure: \"" + quantity + "\" is not a valid quantity";
		} else {
			boolean restock = vender.getVendItem(item).restock(quantity);
			if (!restock) {
				res += "Failure: The maximum quantity for \"" + vender.getVendItem(item).getName() + "\" is 10" + "\n";
				res += "         There are currently " + vender.getVendItem(item).getQty() + " in stock" + "\n";
				res += "         The maximum that can be added is: " + (10 - vender.getVendItem(item).getQty());
			} else {
				res += "Success: ";
				res += "\"" + vender.getVendItem(item).getName() + "\"" + " was successfully restocked" + "\n";
				res += "         There are now " + vender.getVendItem(item).getQty() + " in stock";
			}
		}
		
		System.out.println(res);
		System.out.println();	
	}
	
	/**
	 * Allows the user to create a new VendItem to add to the vending machine.
	 */
	private static void addNewItem() {	
		System.out.println("\nOK - Add a New Item");
		System.out.println("+++++++++++++++++++\n");
		
		// Machine must be set to service mode
		if (!vender.getStatus().equals("Service Mode")) {
			System.out.println("Failure: Enable \"SERVICE MODE\" to restock items\n");
			return;
		}
			
		// Get input from the user
		System.out.print("Enter the name of the item: ");
		String name = in.nextLine();
		name = name.trim();
		System.out.println();

		int costInPence = getInput("Enter the cost of the item (in pence): ");
		System.out.println();

		int quantity = getInput("Enter quantity of the item (0 if none): ");
		
		// Validate the user input
		if (name.equals("")) {
			System.out.println("\nFailure: The new item must be given a valid name\n");
			return;
		}
		
		if (costInPence > 200 || costInPence <= 0 || costInPence % 5 != 0) {
			System.out.println("\nFailure: The item cost must be greater than 0p and a maximum of £2");
			System.out.println("         The cost must also be a multiple of 5p\n");
			return;
		}
		
		if (quantity < 0 || quantity > 10) {
			System.out.println("\nFailure: The new item must have a quantity between 0 and 10 (inclusive)\n");
			return;
		}
		
		// Create and add the item
		double costInPounds = (double)costInPence / 100;
		VendItem item = new VendItem(name, costInPounds, quantity);
		boolean itemAdded = vender.addNewItem(item);
		
		if (itemAdded) {
			String res = "\n";
			res += "Success: Item \"" + name + "\" was added to the vending machine" + "\n";
			res += "         The cost is set to " + String.format("£%.2f", costInPounds) + "\n";
			res += "         The quantity is set to " + quantity + "\n";
			System.out.println(res);
		} else {
			System.out.println("\nFailure: There is no room in the vending machine for more items\n");
		}
	}
	
	/**
	 * Allows the user to select a menu option.
	 * @param message - This will act as a prompt for the user
	 * @return - The user selection is returned
	 */
	public static int getInput(String message) {
		int selection = 0;
		boolean validInput = false;
		
		do {
			System.out.print(message);
			try {
				selection = in.nextInt();
				validInput = true;
				in.nextLine();
			} catch (InputMismatchException nfe) {
				System.out.println("\nError: Invalid input\n");
				in.nextLine();
			}
		} while (!validInput);
		
		return selection;
	}
	
	/**
	 * Saves the state/data of the vending machine to a CSV file.
	 * When the program starts again, data will be read from this file and the machine will be restored.
	 */
	private static void saveMachineData() {
		String csvOutPath = "machineData.csv";
		
		try {
			PrintWriter myPw = new PrintWriter(csvOutPath);
			
			// The system information (user money is not restored)
			myPw.println(vender.getOwner() + ", " + vender.getMaxItems() + ", " + vender.getStatus() + ", " + vender.getTotalMoney());
			
			// The coins inside the machine
			myPw.println(vender.getCoinAmount(5) + ", " + vender.getCoinAmount(10) + ", " + vender.getCoinAmount(20) + ", " + vender.getCoinAmount(50) + ", " + vender.getCoinAmount(1) + ", " + vender.getCoinAmount(2));
			
			// The items inside the machine
			for (int i = 0; i < vender.getItemCount(); i++) {
				myPw.print(vender.getVendItem(i).getName() + ", ");
				myPw.print(vender.getVendItem(i).getPrice() + ", ");
				myPw.print(vender.getVendItem(i).getQty() + "\n");
			}
			
			myPw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Restores the vending machine using data from a CSV file.
	 * If the CSV file is corrupt or does not exist, a new vending machine will be returned.
	 * @return - A VendingMachine object is returned
	 */
	private static VendingMachine restoreMachineData() {
		VendingMachine machine;
		String csvInPath = "machineData.csv";
		
		try {
			File myFile = new File(csvInPath);
			Scanner scan = new Scanner(myFile);
			
			// Check for first line
			if (!scan.hasNextLine()) {
				System.out.println("Corrupt CSV: No first line");
				System.out.println("Reverting to default settings\n");
				scan.close();
				return createVendItems();
			}
			
			// The first line is system information
			String info = scan.nextLine().trim();
			if (!info.contains(",") || info.equals("")) {
				System.out.println("Corrupt CSV: Invalid first line format");
				System.out.println("Reverting to default settings\n");
				scan.close();
				return createVendItems();
			}
			
			// Split and validate the first line
			String[] infoParts = info.split(",");
			
			if (infoParts.length != 4) {
				System.out.println("Corrupt CSV: Invalid first line length");
				System.out.println("Reverting to default settings\n");
				scan.close();
				return createVendItems();
			}
			
			if (infoParts[0].trim().equals("")) {
				System.out.println("Corrupt CSV: Invalid owner name on first line");
				System.out.println("Reverting to default settings\n");
				scan.close();
				return createVendItems();
			}
			
			if (!isInteger(infoParts[1].trim()) || !isDouble(infoParts[3].trim())) {
				System.out.println("Corrupt CSV: Invalid number value on first line");
				System.out.println("Reverting to default settings\n");
				scan.close();
				return createVendItems();
			}
			
			if (!infoParts[2].trim().equals("Vending Mode") && !infoParts[2].trim().equals("Service Mode")) {
				System.out.println("Corrupt CSV: Invalid status on first line");
				System.out.println("Reverting to default settings\n");
				scan.close();
				return createVendItems();
			}
				
			// Initialise the VendingMachine
			String owner = infoParts[0].trim();
			int maxItems = Integer.parseInt(infoParts[1].trim());
			machine = new VendingMachine(owner, maxItems);
			
			String status = infoParts[2].trim();
			if (status.equals("Vending Mode")) {
				machine.setStatus(Status.VENDING_MODE);
			} else {
				machine.setStatus(Status.SERVICE_MODE);
			}
			
			double totalMoney = Double.parseDouble(infoParts[3].trim());
			machine.setTotalMoney(totalMoney);
			
			// Check for second line
			if (!scan.hasNextLine()) {
				System.out.println("Corrupt CSV: No second line");
				System.out.println("Reverting to default settings\n");
				scan.close();
				return createVendItems();
			}
			
			// The second line is coin amounts
			String coins = scan.nextLine().trim();
			if (!coins.contains(",") || coins.equals("")) {
				System.out.println("Corrupt CSV: Invalid second line format");
				System.out.println("Reverting to default settings\n");
				scan.close();
				return createVendItems();
			}
			
			// Split and validate the second line	
			String[] coinParts = coins.split(",");

			if (coinParts.length != 6) {
				System.out.println("Corrupt CSV: Invalid second line length");
				System.out.println("Reverting to default settings\n");
				scan.close();
				return createVendItems();
			}
			
			for (int i = 0; i < coinParts.length; i++) {
				if (!isInteger(coinParts[i].trim())) {
					System.out.println("Corrupt CSV: Invalid number value on second line");
					System.out.println("Reverting to default settings\n");
					scan.close();
					return createVendItems();
				}
			}
			
			// Validate total money
			int fivePence = Integer.parseInt(coinParts[0].trim());
			int tenPence = Integer.parseInt(coinParts[1].trim());
			int twentyPence = Integer.parseInt(coinParts[2].trim());
			int fiftyPence = Integer.parseInt(coinParts[3].trim());
			int onePound = Integer.parseInt(coinParts[4].trim());
			int twoPound = Integer.parseInt(coinParts[5].trim());
			
			int totalInPence = 0;
			totalInPence += fivePence * 5;
			totalInPence += tenPence * 10;
			totalInPence += twentyPence * 20;
			totalInPence += fiftyPence * 50;
			totalInPence += onePound * 100;
			totalInPence += twoPound * 200;
			
			double totalInPounds = (double)totalInPence / 100;
			if (totalInPounds != totalMoney) {
				System.out.println("Corrupt CSV: Total money is not correct");
				System.out.println("Reverting to default settings\n");
				scan.close();
				return createVendItems();
			}
			
			// Add coins to the VendingMachine
			machine.setCoinAmount(5, fivePence);
			machine.setCoinAmount(10, tenPence);
			machine.setCoinAmount(20, twentyPence);
			machine.setCoinAmount(50, fiftyPence);
			machine.setCoinAmount(1, onePound);
			machine.setCoinAmount(2, twoPound);
			
			// The remaining lines of the CSV file contain VendItems
			int lineNum = 3;
			while(scan.hasNextLine()) {
				// Scan the item
				String item = scan.nextLine().trim();
				if (!item.contains(",") || item.equals("")) {
					System.out.println("Corrupt CSV: Invalid vend item format on line " + lineNum);
					System.out.println("Reverting to default settings\n");
					scan.close();
					return createVendItems();
				}
				
				// Split and validate the line
				String[] itemParts = item.split(",");
				
				if (itemParts.length != 3) {
					System.out.println("Corrupt CSV: Invalid vend item length on line " + lineNum);
					System.out.println("Reverting to default settings\n");
					scan.close();
					return createVendItems();
				}
				
				if (itemParts[0].trim().equals("")) {
					System.out.println("Corrupt CSV: Invalid vend item name on line " + lineNum);
					System.out.println("Reverting to default settings\n");
					scan.close();
					return createVendItems();
				}
				
				if (!isDouble(itemParts[1].trim()) || !isInteger(itemParts[2].trim())) {
					System.out.println("Corrupt CSV: Invalid number value for vend item on line " + lineNum);
					System.out.println("Reverting to default settings\n");
					scan.close();
					return createVendItems();
				}
				
				// Add the item to the VendingMachine
				String itemName = itemParts[0].trim();
				double itemPrice = Double.parseDouble(itemParts[1].trim());
				int itemQty = Integer.parseInt(itemParts[2].trim());
				VendItem myItem = new VendItem(itemName, itemPrice, itemQty);
				machine.addNewItem(myItem);
				
				lineNum++;
			}
			
			scan.close();
			return machine;	
		} catch (FileNotFoundException e) {
			// Create a new VendingMachine if the CSV file does not exist
			return createVendItems();
		}
	}
	
	/**
	 * Checks if a string can be converted to an integer.
	 * @param s - The string to be checked
	 * @return - A boolean is returned to indicate if the string can be parsed to a number
	 */
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    
	    return true;
	}
	
	/**
	 * Checks if a string can be converted to a double.
	 * @param s - The string to be checked
	 * @return - A boolean is returned to indicate if the string can be parsed to a number
	 */
	public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    
	    return true;
	}

}

