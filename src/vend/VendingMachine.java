package vend;

/**
 * This class represents the VendingMachine object.
 * @author Fergal Bittles
 *
 */
public class VendingMachine {

	private String owner;
	private int maxItems;
	private int itemCount;
	private VendItem[] stock;
	private double totalMoney;
	private double userMoney;
	private Status vmStatus;
	
	// The amount of coins in a vending machine
	private int fivePence;
	private int tenPence;
	private int twentyPence;
	private int fiftyPence;
	private int onePound;
	private int twoPound;
	
	/**
	 * Constructor for VendingMachine.
	 * @param owner - The owner of a vending machine
	 * @param maxItems - The maximum amount of items allowed in a vending machine
	 */
	public VendingMachine(String owner, int maxItems) {
		setOwner(owner);
		setMaxItems(maxItems);
		this.stock = new VendItem[this.maxItems];
		this.itemCount = 0;
		this.userMoney = 0.0;
		setStatus(Status.VENDING_MODE); // Vending Mode is the default status of a new machine
		
		// Every machine will be initialised with a float, so that users can receive change
		this.totalMoney = 13.50;
		this.fivePence = 10;
		this.tenPence = 10;
		this.twentyPence = 10;
		this.fiftyPence = 10;
		this.onePound = 5;
		this.twoPound = 0;
	}
	
	/**
	 * Called by the constructor to validate the owner name.
	 * @param owner - The name of the owner
	 */
	private void setOwner(String owner) {
		if (owner != null && !owner.equals("")) {
			this.owner = owner;
		} else {
			this.owner = "Unassigned";
		}
	}
	
	/**
	 * Called by the constructor to validate the maximum amount of items.
	 * @param maxItems - The maximum amount of VendItems allowed within a VendingMachine
	 */
	private void setMaxItems(int maxItems) {
		if (maxItems > 0) {
			this.maxItems = maxItems;
		} else {
			this.maxItems = 10; // 10 is the default maximum amount of items
		}
	}
	
	/**
	 * Sets the status of a vending machine.
	 * @param stat - The status of a vending machine
	 */
	public void setStatus(Status stat) {
		this.vmStatus = stat;
	}
	
	/**
	 * Sets the total amount of money that is inside a vending machine.
	 * Called when restoring a vending machine using the data inside a CSV file.
	 * @param totalMoney - The total money within a vending machine
	 */
	public void setTotalMoney(double totalMoney) {
		if (totalMoney > 0) {
			this.totalMoney = totalMoney;
		} else {
			this.totalMoney = 0;
		}
	}
	
	/**
	 * Sets the amount of each coin contained inside a vending machine.
	 * Called when restoring a vending machine using the data inside a CSV file.
	 * @param coin - The type of coin
	 * @param amount - The amount of the coin inside a machine
	 */
	public void setCoinAmount(int coin, int amount) {
		if (amount < 0) {
			amount = 0;
		}
		
		switch (coin) {
		case 1: this.onePound = amount; break;
		case 2: this.twoPound = amount; break;
		case 5: this.fivePence = amount; break;
		case 10: this.tenPence = amount; break;
		case 20: this.twentyPence = amount; break;
		case 50: this.fiftyPence = amount; break;
		}
	}
	
	/**
	 * Returns the amount of a particular coin type that is contained inside a machine.
	 * @param coin - The type of coin
	 * @return - The amount of the coin inside a vending machine
	 */
	public int getCoinAmount(int coin) {
		switch (coin) {
		case 1: return this.onePound;
		case 2: return this.twoPound;
		case 5: return this.fivePence;
		case 10: return this.tenPence;
		case 20: return this.twentyPence;
		case 50: return this.fiftyPence;
		default: return -1;
		}
	}
	
	/**
	 * Returns the owner of a vending machine.
	 * @return - The owner of the vending machine
	 */
	public String getOwner() {
		return this.owner;
	}
	
	/**
	 * Returns the maximum number of items that can be stored in a vending machine.
	 * @return - The maximum number of items
	 */
	public int getMaxItems() {
		return this.maxItems;
	}
	
	/**
	 * Returns the amount of money that a user has entered into a vending machine.
	 * @return - The user money
	 */
	public double getUserMoney() {
		return this.userMoney;
	}
	
	/**
	 * Returns the total amount of money that is currently inside a vending machine.
	 * @return - The total money
	 */
	public double getTotalMoney() {
		return this.totalMoney;
	}
	
	/**
	 * Returns the status of a vending machine.
	 * @return - The status of a vending machine
	 */
	public String getStatus() {
		return this.vmStatus.getStatus();
	}
	
	/**
	 * Returns the count of all items inside a vending machine.
	 * @return - The item count
	 */
	public int getItemCount() {
		return this.itemCount;
	}
	
	/**
	 * Returns a VendItem from a particular position in the stock array.
	 * @param position - The position of the item within the stock array
	 * @return - The selected VendItem is returned.
	 */
	public VendItem getVendItem(int position) {
		if (position >= 0 && position < itemCount) {
			return this.stock[position];
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a lot of valuable information about a VendingMachine.
	 * @return - A String containing information about a vending machine
	 */
	public String getSystemInfo() {
		String res = "";
		
		res += "System Info" + "\n";
		res += "===========" + "\n";
		res += "Owner: " + this.owner + "\n";
		res += "Status: " + this.getStatus() + "\n";
		res += "Max Items: " + this.maxItems + "\n";
		res += "Item Count: " + this.itemCount + "\n";
		res += "User Money: " + String.format("£%.2f", this.getUserMoney()) + "\n";
		res += "Total Money: " + String.format("£%.2f", this.totalMoney) + "\n";
		res += "> 5p Coins: " + this.fivePence + "\n";
		res += "> 10p Coins: " + this.tenPence + "\n";
		res += "> 20p Coins: " + this.twentyPence + "\n";
		res += "> 50p Coins: " + this.fiftyPence + "\n";
		res += "> £1 Coins: " + this.onePound + "\n";
		res += "> £2 Coins: " + this.twoPound + "\n";
		
		return res;
	}
	
	/**
	 * Returns a string containing information about a VendingMachine. Useful for testing the VendingMachine class.
	 */
	public String toString() {
		return this.getSystemInfo();
	}
	
	/**
	 * Adds a new item to a vending machine.
	 * @param item - The item to be added
	 * @return - A boolean is returned to indicate failure or success
	 */
	public boolean addNewItem(VendItem item) {
		// Check that the item doesn't exist in the machine already
		for (int i = 0; i < this.itemCount; i++) {
			if (this.stock[i].getItemId() == item.getItemId()) {
				return false;
			}
		}
		
		// Check that there is enough space and that the item isn't null
		if ((this.itemCount < this.maxItems) && (item != null)) {
			this.stock[this.itemCount] = item;
			this.itemCount++;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns an array of strings with the name, quantity and price of each item.
	 * @return - An array of strings containing information about each item
	 */
	public String[] listItems() {
		if (this.itemCount <= 0) {
			return null;
		}
		
		String[] items = new String[this.itemCount];

		for (int i = 0; i < this.itemCount; i++) {
			items[i] = "";
			items[i] += this.stock[i].getName();
			items[i] += String.format("\n   Price: £%.2f", this.stock[i].getPrice());
			items[i] += "\n   Quantity: " + this.stock[i].getQty() + "\n";
		}

		return items;
	}
	
	/**
	 * Resets a VendingMachine by getting rid of all items and cash.
	 */
	public void reset() {
		this.stock = new VendItem[this.maxItems];
		this.itemCount = 0;
		this.totalMoney = 0.0; 
		this.userMoney = 0.0;
		setStatus(Status.VENDING_MODE);
		
		this.fivePence = 0;
		this.tenPence = 0;
		this.twentyPence = 0;
		this.fiftyPence = 0;
		this.onePound = 0;
		this.twoPound = 0;
	}
	
	/**
	 * Called when a user inserts a coin into a vending machine.
	 * The value of the coin is added to the total money, and also to the user money.
	 * @param option - This corresponds with the type of coin that the user has inserted
	 * @return - A boolean is returned to indicate failure or success
	 */
	public boolean insertCoin(int option) {
		if (option == 1) {
			this.userMoney += 0.05;
			this.totalMoney += 0.05;
			this.fivePence++;
			return true;
		} else if (option == 2) {
			this.userMoney += 0.1;
			this.totalMoney += 0.1;
			this.tenPence++;
			return true;
		} else if (option == 3) {
			this.userMoney += 0.2;
			this.totalMoney += 0.2;
			this.twentyPence++;
			return true;
		} else if (option == 4) {
			this.userMoney += 0.5;
			this.totalMoney += 0.5;
			this.fiftyPence++;
			return true;
		} else if (option == 5) {
			this.userMoney += 1.0;
			this.totalMoney += 1.0;
			this.onePound++;
			return true;
		} else if (option == 6) {
			this.userMoney += 2.0;
			this.totalMoney += 2.0;
			this.twoPound++;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Allows the user to purchase an item from a vending machine.
	 * @param item - The position of the item within the stock array
	 * @return - A String thanking the user for their purchase, or giving them information on why their purchase failed
	 */
	public String purchaseItem(int item) {
		if (item >= this.itemCount || item < 0) {
			
			// The user has made an invalid selection
			return "Failure: Invalid selection";
			
		} else if (this.vmStatus == Status.SERVICE_MODE) {
			
			// The vending machine is in service mode
			return "Failure: Machine is in service mode";
			
		} else if (this.itemCount == 0) {
			
			// There are no items in the vending machine
			return "Failure: The vending machine does not contain any items";
			
		} else if (this.stock[item].getPrice() > this.userMoney) {
			
			// The user has not entered enough money
			String insFunds = "";
			insFunds += "Failure: Insufficient funds" + "\n";
			insFunds += "This item costs: " + String.format("£%.2f", this.stock[item].getPrice()) + "\n";
			insFunds += "You entered: " + String.format("£%.2f", this.userMoney);
			return insFunds;
			
		} else {
			
			String res = this.stock[item].deliver();
			
			if (res == null) {
				
				// The item is out of stock
				String noStock = "";
				noStock += "Failure: Item \"" + this.stock[item].getName() + "\" is out of stock" + "\n";
				noStock += "Please select a different item";
				return noStock;
				
			} else {
				
				// The user successfully purchased the item
				
				// Look at the total quantity of all items in the vending machine and set to service mode if there is nothing left
				int totalQuantity = 0;
				for(int i = 0; i < this.itemCount; i++) {
					totalQuantity += this.stock[i].getQty();
				}
				if (totalQuantity == 0) {
					this.setStatus(Status.SERVICE_MODE);
				}
				
				// Calculate change
				double changeBeforeRounding = this.userMoney - this.stock[item].getPrice();
				double change = Math.round(changeBeforeRounding * 100.0) / 100.0;
				
				// Calculate the various coins needed to give the user their change
				res += calculateChange(change, item);
				return res;
				
			}
		}	
	}
	
	/**
	 * Calculates the coins that will be needed for the users change.
	 * If there are sufficient coins to give change, this method will call the changeGiver method.
	 * If there are not enough coins to give change, a string will be returned indicating this.
	 * @return - A string displaying the change, or declaring that the change can not be given
	 */
	private String calculateChange(double change, int item) {
		String res = "";
		
		// Work out the coin combination used to give change
		int numOf5p = 0;
		int numOf10p = 0;
		int numOf20p = 0;
		int numOf50p = 0;
		int numOf1Pound = 0;
		int numOf2Pound = 0;
		int totalCoinUsed = 0;
		double changeInPenceDouble = change*100;
		double mathRound = Math.round(changeInPenceDouble);
		int changeInPence = (int)mathRound;
		
		while(changeInPence != 0) {
			if (((changeInPence % 200) < changeInPence) && (this.twoPound > 0)) {
				this.twoPound--;
				changeInPence -= 200;
				numOf2Pound++; 
				totalCoinUsed++;
			} else if (((changeInPence % 100) < changeInPence) && (this.onePound > 0)) {
				this.onePound--;
				changeInPence -= 100;
				numOf1Pound++; 
				totalCoinUsed++; 
			} else if (((changeInPence % 50) < changeInPence) && (this.fiftyPence > 0)) {
				this.fiftyPence--;
				changeInPence -= 50;
				numOf50p++; 
				totalCoinUsed++;
			} else if (((changeInPence % 20) < changeInPence) && (this.twentyPence > 0)) {
				this.twentyPence--;
				changeInPence -= 20;
				numOf20p++; 
				totalCoinUsed++; 
			} else if (((changeInPence % 10) < changeInPence) && (this.tenPence > 0)) {
				this.tenPence--;
				changeInPence -= 10;
				numOf10p++; 
				totalCoinUsed++;
			} else if (((changeInPence % 5) < changeInPence) && (this.fivePence > 0)) {
				this.fivePence--;
				changeInPence -= 5;
				numOf5p++; 
				totalCoinUsed++;
			} else {

				// There are not enough coins to give the user the correct change
				this.userMoney = 0.0;
				
				// All coins will stay in the machine
				this.fivePence += numOf5p;
				this.tenPence += numOf10p;
				this.twentyPence += numOf20p;
				this.fiftyPence += numOf50p;
				this.onePound += numOf1Pound;
				this.twoPound += numOf2Pound; 
				
				res += "\nCost: " + String.format("£%.2f", this.stock[item].getPrice());
				res += "\nChange: Insufficient coins, no change given";
				return res;
				
			}
		}
		
		// Calculation complete, give the user their change
		res += giveChange(change, item, numOf5p, numOf10p, numOf20p, numOf50p, numOf1Pound, numOf2Pound, totalCoinUsed);
		return res;
	}
	
	/**
	 * Gives the user the various coin denominations needed for their change.
	 * @return - A string displaying the change
	 */
	private String giveChange(double change, int item, int numOf5p, int numOf10p, int numOf20p, int numOf50p, int numOf1Pound, int numOf2Pound, int totalCoinUsed) {		
		String res = "";
		
		res += "\nCost: " + String.format("£%.2f", this.stock[item].getPrice());
		res += "\nChange: " + String.format("£%.2f", change);
		
		while (totalCoinUsed!=0) {
			if (numOf2Pound != 0) {
				res += "\n";
				res += "> £2 Coins: " + numOf2Pound;
				totalCoinUsed -= numOf2Pound;
				numOf2Pound = 0;
			} else if (numOf1Pound != 0) {
				res += "\n";
				res += "> £1 Coins: " + numOf1Pound;
				totalCoinUsed -= numOf1Pound;
				numOf1Pound = 0;
			} else if (numOf50p != 0) {
				res += "\n";
				res += "> 50p Coins: " + numOf50p;
				totalCoinUsed -= numOf50p;
				numOf50p = 0;
			} else if (numOf20p != 0) {
				res += "\n";
				res += "> 20p Coins: " + numOf20p;
				totalCoinUsed -= numOf20p;
				numOf20p = 0;
			} else if (numOf10p != 0) {
				res += "\n";
				res += "> 10p Coins: " + numOf10p;
				totalCoinUsed -= numOf10p;
				numOf10p = 0;
			} else if (numOf5p != 0) {
				res += "\n";
				res += "> 5p Coins: " + numOf5p;
				totalCoinUsed -= numOf5p;
				numOf5p = 0;
			}
		}
		
		// Reduce user money and total money
		this.userMoney = 0.0;
		this.totalMoney -= change;
		
		return res;
	}
	
}
