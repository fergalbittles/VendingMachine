package vend;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a console based menu.
 * @author - Fergal Bittles
 *
 */
public class Menu {
	
	private String options[];
	private String title; 
	private Scanner input; 

	/**
	 * Constructor for Menu.
	 * @param title   - The menu title
	 * @param options - The menu options
	 */
	public Menu(String title, String options[]) {
		this.title = title;
		copyOptions(options);
		input = new Scanner(System.in);
	}

	/**
	 * Requests and then reads a user choice.
	 * Exception handling is used to prevent input mismatch errors.
	 * @return - The user choice
	 */
	public int getChoice() {
		display();
		
		int choice = 0;
		boolean validInput = false;
		
		do {
			System.out.print("Enter option number: ");
			try {
				choice = input.nextInt();
				validInput = true;
				input.nextLine();
			} catch (InputMismatchException nfe) {
				System.out.println("\nError: Invalid input\n");
				input.nextLine();
			}
		} while (!validInput);
		
		return choice;
	}

	/**
	 * Displays the menu title followed by the menu options.
	 */
	private void display() {
		if (title == null || options == null) {
			System.out.println("Error: Menu not defined");
			return;
		}
		
		System.out.println(title);
		for (int i = 0; i < title.length(); i++) {
			System.out.print("+");
		}
		System.out.println("\n");
		
		int count = 1;
		for (String str : options) {
			System.out.println(count + ". " + str);
			count++;
		}
		
		System.out.println();
	}

	/**
	 * Initialises the options array by copying the contents to a new array.
	 * @param data - Options for the user to select from
	 */
	private void copyOptions(String data[]) {
		if (data == null) {
			options = null;
			return;
		}
		
		options = new String[data.length];
		for (int index = 0; index < data.length; index++) {
			options[index] = data[index];
		}
	}

}
