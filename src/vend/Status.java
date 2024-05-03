package vend;

/**
 * Enumeration for the VendingMachine status.
 * @author Fergal Bittles
 *
 */
public enum Status {

	VENDING_MODE(0), SERVICE_MODE(1);
	
	private int sNum;
	private String names[] = {"Vending Mode", "Service Mode"};
	
	private Status(int num) {
		sNum = num;
	}
	
	public String getStatus() {
		return names[sNum];
	}
	
}
