package book;

import java.util.StringTokenizer;

public class Book {

	// Field separator used in the database.
	private static final String DELIMITER = "||";

	private Integer id;
	private String title;
	private String author;
	private String description;

	public Book() {
	}

	public Book(String line) {
		StringTokenizer tokens = new StringTokenizer(line, DELIMITER);

		this.id = Integer.valueOf(tokens.nextToken().trim());
		this.title = tokens.nextToken().trim();
		this.author = tokens.nextToken().trim();
		this.description = tokens.nextToken().trim();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ID: ").append(id).append("\nTitle: ").append(title).append("\nAuthor: ").append(author).append("\nDescription: ").append(description).append("\n");
		return builder.toString();
	}

}
