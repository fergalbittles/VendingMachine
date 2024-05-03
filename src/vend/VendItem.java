package vend;

/**
 * This class represents the VendItem object.
 * @author Fergal Bittles
 *
 */
public class VendItem implements Vendible {

	private int itemId; 
	private static int nextId = 1;
	private String name;
	private double unitPrice;
	private int qtyAvailable;
	
	/**
	 * Constructor for VendItem. Calls private setters which validate the data.
	 * @param name - The name of the item
	 * @param cost - The cost of the item
	 */
	public VendItem(String name, double cost) {
		this.itemId = useNextId();
		setName(name);
		setUnitPrice(cost);
		setQty(0); // 0 is the default quantity of a new item
	}
	
	/**
	 * Overloaded constructor for VendItem.
	 * This constructor calls the previous one, but also takes the quantity of the item as a parameter.
	 * @param name - The name of the item
	 * @param cost - The cost of the item
	 * @param quantity - The quantity of the item
	 */
	public VendItem(String name, double cost, int quantity) {
		this(name, cost);
		setQty(quantity);
	}
	
	/**
	 * Called by the constructor to assign the next available ID and then increment 'nextId'.
	 * @return - the next available ID is returned
	 */
	private static int useNextId() {
		int num = nextId;
		nextId++;
		return num;
	}
	
	/**
	 * Called by the constructor to validate the name of the item before instantiation.
	 * @param name - The name of the item
	 */
	private void setName(String name) {
		if (name != null && !name.equals("")) {
			this.name = name;
		} else {
			this.name = "Unassigned";
		}
	}
	
	/**
	 * Called by the constructor to validate the cost of the item.
	 * A VendItem must be less than or equal to £2 and be a multiple of 5p.
	 * @param cost - The cost of the item
	 */
	private void setUnitPrice(double cost) {
		double priceInPence = cost * 100;
		
		if (priceInPence % 5 == 0 && priceInPence <= 200 && priceInPence > 0) {
			this.unitPrice = cost;
		} else {
			this.unitPrice = 2; // £2 is the default cost of a new item
		}
	}
	
	/**
	 * Called by the constructor to validate the quantity of the item.
	 * Quantity must be greater than 0 and less than or equal to 10.
	 * @param qty - The quantity of the item
	 */
	private void setQty(int qty) {
		if (qty > 0 && qty <= 10) {
			this.qtyAvailable = qty;
		} else {
			this.qtyAvailable = 0; // 0 is the default quantity of a new item
		}
	}
	
	/**
	 * Returns the name of the item.
	 * @return - The name of the item
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the price of the item.
	 * @return - The price of the item
	 */
	public double getPrice() {
		return this.unitPrice;
	}
	
	/**
	 * Returns the quantity of the item.
	 * @return - The quantity of the item
	 */
	public int getQty() {
		return this.qtyAvailable;
	}
	
	/**
	 * Returns the ID of the item.
	 * @return - The ID of the item
	 */
	public int getItemId() {
		return this.itemId;
	}
	
	/**
	 * Updates the quantity of an item.
	 * @param quantity - This 'restock' quantity will be added to the current quantity
	 * @return - A boolean is returned to indicate failure or success
	 */
	public boolean restock(int quantity) {
		if ((quantity + qtyAvailable <= 10) && (quantity > 0)) {
			this.qtyAvailable += quantity;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Decreases the quantity of an item by 1.
	 * @return - A boolean is returned to indicate failure or success
	 */
	private boolean decrement() {
		if (this.qtyAvailable > 0) {
			this.qtyAvailable--;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method is inherited from the 'Vendible' interface.
	 * Called if a purchase is approved within a VendingMachine.
	 * @return - The string returned will thank the user for purchasing an item
	 */
	public String deliver() {
		boolean sold = this.decrement();
		
		if (sold) {
			String res = "Thanks for purchasing: " + this.getName();
			return res;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a string containing information about an item. Useful for testing the VendItem class.
	 */
	public String toString() {
		String res = "";
		
		res += "ID: " + this.getItemId() + "\n";
		res += "Name: " + this.getName() + "\n";
		res += "Price: " + String.format("Price: £%.2f", this.getPrice()) + "\n";
		res += "Quantity: " + this.getQty() + "\n";
		
		return res;
	}
	
}
