package book;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;

public class BookManager {

	// Database related
	private static final String FILE_NAME = "database.txt";
	private static final String DELIMITER = "||";

	// Available menu choices, and user's selection.
	private static final Range<Integer> menuOptionRange = Range.between(1, 5);
	private static Integer choice = 0;

	// Database records.
	static ArrayList<Book> books = new ArrayList<Book>();

	public static void main(String[] args) throws IOException {

		readDatabase();

		do {
			getUserChoice();
			switch (choice) {
			case 1:
				viewAllBooks();
				break;
			case 2:
				addBook();
				break;
			case 3:
				editBook();
				break;
			case 4:
				searchBook();
				break;
			case 5:
				writeDatabase();
				break;
			}
		} while (choice != 5);

	}

	/**
	 * Shows the main menu and gets the user response.
	 */
	private static void getUserChoice() {
		ConsoleHelper.printTitle("Book Manager");
		ConsoleHelper.printOption(1, "View all books");
		ConsoleHelper.printOption(2, "Add a book");
		ConsoleHelper.printOption(3, "Edit a book");
		ConsoleHelper.printOption(4, "Search for a book");
		ConsoleHelper.printOption(5, "Save and exit\n");
		ConsoleHelper.printPrompt("Choose [1-5]");
		do {
			choice = ConsoleHelper.getInt(false);
			if (!menuOptionRange.contains(choice))
				ConsoleHelper.printError("Please enter a valid choice [1-5]: ");
		} while (!menuOptionRange.contains(choice));
	}

	/**
	 * Displays all books in the database.
	 */
	private static void viewAllBooks() {
		ConsoleHelper.printTitle("View Books");

		if (books.size() == 0)
			ConsoleHelper.printMessage("No book exists.");
		else {
			books.forEach(b -> ConsoleHelper.printMessage("[" + b.getId() + "] " + b.getTitle(), 1));

			do {
				ConsoleHelper.printMessage("\nTo view details enter the book ID, to return press <Enter>.");
				ConsoleHelper.printPrompt("\nBook ID");
				Integer bookId = ConsoleHelper.getInt(true);

				if (bookId != null) {
					try {
						Book book = (Book) books.stream().filter(b -> b.getId().equals(bookId))
								                .collect(Collectors.toList()).get(0);
						ConsoleHelper.printMessage("");
						ConsoleHelper.printMessage("Title: " + book.getTitle(), 1);
						ConsoleHelper.printMessage("Author: " + book.getAuthor(), 1);
						ConsoleHelper.printMessage("Description: " + book.getDescription(), 1);
					} catch (Exception e) {
						ConsoleHelper.printError("Please enter a valid book ID");
					}
				} else
					break;
			} while (true);
		}
	}

	/**
	 * Adds a book into the database.
	 */
	private static void addBook() {
		ConsoleHelper.printTitle("Add a Book");
		Book book = new Book();

		ConsoleHelper.printMessage("Please enter the following information:\n");

		ConsoleHelper.printPrompt("Title: ", 1);
		String txt = ConsoleHelper.getString();
		book.setTitle(txt);

		ConsoleHelper.printPrompt("Author: ", 1);
		txt = ConsoleHelper.getString();
		book.setAuthor(txt);

		ConsoleHelper.printPrompt("Description: ", 1);
		txt = ConsoleHelper.getString();
		book.setDescription(txt);

		if (books.size() == 0)
			book.setId(1);
		else
			book.setId(1 + books.stream().mapToInt(b -> b.getId().intValue()).max().getAsInt());

		books.add(book);

		ConsoleHelper.printMessage("\nBook [" + book.getId() + "] saved\n");
	}

	/**
	 * Edits a book in the database.
	 */
	private static void editBook() {
		ConsoleHelper.printTitle("Edit a Book");

		if (books.size() == 0)
			ConsoleHelper.printMessage("No book exists.");
		else {
			books.forEach(b -> ConsoleHelper.printMessage("[" + b.getId() + "] " + b.getTitle(), 1));

			do {
				ConsoleHelper.printMessage("\nEnter the book ID of the book you want to edit; to return press <Enter>.");
				ConsoleHelper.printPrompt("\nBook ID");
				Integer bookId = ConsoleHelper.getInt(true);

				if (bookId != null) {
					try {
						Book book = (Book) books.stream()
								                .filter(b -> b.getId().equals(bookId))
								                .collect(Collectors.toList())
								                .get(0);

						ConsoleHelper.printMessage("Input the following information. To leave a field unchanged, hit <Enter>");
						ConsoleHelper.printMessage("");

						ConsoleHelper.printPrompt("Title [" + book.getTitle() + "]: ", 1);
						String txt = ConsoleHelper.getString();
						if (!StringUtils.isEmpty(txt))
							book.setTitle(txt);

						ConsoleHelper.printPrompt("Author [" + book.getAuthor() + "]: ", 1);
						txt = ConsoleHelper.getString();
						if (!StringUtils.isEmpty(txt))
							book.setAuthor(txt);

						ConsoleHelper.printPrompt("Description [" + book.getDescription() + "]: ", 1);
						txt = ConsoleHelper.getString();
						if (!StringUtils.isEmpty(txt))
							book.setDescription(txt);

						ConsoleHelper.printMessage("\nBook saved.");

					} catch (Exception e) {
						ConsoleHelper.printError("Please enter a valid book ID");
					}
				} else
					break;
			} while (true);
		}

	}

	/**
	 * Searches the database using keywords against the titles of the books. 
	 */
	private static void searchBook() {
		ConsoleHelper.printTitle("Search");

		if (books.size() == 0)
			ConsoleHelper.printMessage("No book exists.");
		else {
			ConsoleHelper.printMessage("Type in one or more keywords to search for\n");
			ConsoleHelper.printPrompt("Search", 1);
			String searchTerm = ConsoleHelper.getString().trim();

			ArrayList<Book> searchResult = 
					(ArrayList<Book>) books.stream()
					                       .filter(u -> u.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
					                       .collect(Collectors.toList());

			if (searchResult.size() == 0)
				ConsoleHelper.printMessage("\nNo books matched your query.");
			else {
				ConsoleHelper.printMessage("\nThe following books matched your query. Enter the book ID to see more details, or <Enter> to return.\n");

				searchResult.forEach(r -> ConsoleHelper.printMessage("[" + r.getId() + "] " + r.getTitle(), 1));

				do {
					ConsoleHelper.printPrompt("\nBook ID");
					Integer bookId = ConsoleHelper.getInt(true);
					if (bookId != null) {
						try {
							Book book = (Book) searchResult.stream()
									                       .filter(b -> b.getId().equals(bookId))
									                       .collect(Collectors.toList())
									                       .get(0);
							ConsoleHelper.printMessage("");
							ConsoleHelper.printMessage("ID: " + book.getId(), 1);
							ConsoleHelper.printMessage("Title: " + book.getTitle(), 1);
							ConsoleHelper.printMessage("Author: " + book.getAuthor(), 1);
							ConsoleHelper.printMessage("Description: " + book.getDescription(), 1);
						} catch (Exception e) {
							ConsoleHelper.printError("Please enter a valid book ID");
						}
					} else
						break;
				} while (true);
			}
		}
	}

	/**
	 * Reads the database into memory.
	 */
	private static void readDatabase() {
		Path filePath = Paths.get(FILE_NAME);

		if (Files.exists(filePath)) {
			try (Stream<String> fileStream = Files.lines(filePath)) {
				fileStream.forEach(s -> books.add(new Book(s)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ConsoleHelper.printMessage("Loaded " + books.size() + " books into the library");
	}

	/**
	 * Writes the records in the memory into the database.
	 */
	private static void writeDatabase() {
		try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
			books.forEach(s -> pw.println(
					new StringBuilder().append(s.getId()).append(DELIMITER)
					                   .append(s.getTitle()).append(DELIMITER)
					                   .append(s.getAuthor()).append(DELIMITER)
					                   .append(s.getDescription()).toString() ));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ConsoleHelper.printMessage("\nLibrary saved.\n");
	}

}
