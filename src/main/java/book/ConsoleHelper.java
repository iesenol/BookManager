package book;

import java.util.Scanner;

public class ConsoleHelper {

	private static final Scanner in = new Scanner(System.in);

	/**
	 * @return
	 */
	public static String getString() {
		return in.nextLine();
	}
	

	/**
	 * Reads and returns an integer from standard in.
	 * 
	 * @param allowEmpty - if true, allows an empty string as valid input
	 * @return the input Integer, or null if received an empty string (just Enter)
	 */
	public static Integer getInt(boolean allowEmpty) {
		
		Integer i = null;
		
		while (true) {
			String line = in.nextLine().trim();
		
			if (line.isEmpty() && allowEmpty)
				break;
			else {
				try {
					i = Integer.parseInt(line);
					break;
				} catch (NumberFormatException e) {
					System.err.print("Please enter a valid integer: ");
				}
			}
		}
		
		return i;
	}
	
	public static void printTitle(String title) {
		System.out.println("\n==== " + title + " ====\n");
	}
	
	public static void printMessage(String msg) {
		printMessage(msg, 0);
	}
	
	public static void printMessage(String msg, int indentCount) {
		System.out.println(indent(indentCount) + msg);
	}	
	
	public static void printError(String msg) {
		System.err.println(msg + "\n");
	}	
	
	public static void printOption(int option, String description) {
		System.out.println("\t" + option + ") " + description);
	}
	
	public static void printPrompt(String text) {
		printPrompt(text, 0);
	}
	
	public static void printPrompt(String text, int indentCount) {
		System.out.print(indent(indentCount) + text + ": ");
	}
	
	public static String indent(int count) {
		StringBuilder s = new StringBuilder();
		
		for (int i = 0; i < count; i++)
			s.append("\t");
		
		return s.toString();
	}	
}
