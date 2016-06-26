package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.models.BlogEntry;

/**
 * Form class for {@link BlogEntry}. Used when adding new entry.
 * 
 * @author Domagoj Tokić
 *
 */
public class BlogEntryForm extends AbstractForm {

	/** Blog entry title */
	private String title;

	/** Blog entry text */
	private String text;

	/** Instance of empty form */
	public static final BlogEntryForm EMPTY = new BlogEntryForm("", "");

	/**
	 * Creates new BlogEntryForm
	 * 
	 * @param title Blog entry title
	 * @param text Blog entry text
	 */
	public BlogEntryForm(String title, String text) {
		this.title = title;
		this.text = text;
		validity.put("title", "");
		validity.put("text", "");
	}

	/**
	 * Default constructor
	 */
	public BlogEntryForm() {
	}

	@Override
	public void checkValidity() {
		if (title == null || title.replaceAll("\\s+", "").length() < 3) {
			validity.put("title", "Title must have at least 3 characters!");
		}
		if (text == null || text.replaceAll("\\s+", "").length() == 0) {
			validity.put("text", "Text field cannot be empty!");
		}
	}

	/**
	 * Returns blog entry title.
	 * 
	 * @return blog entry title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets blog entry title.
	 * 
	 * @param title blog entry title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns blog entry text.
	 * 
	 * @return blog entry text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets blog entry text.
	 * 
	 * @param text blog entry text.
	 */
	public void setText(String text) {
		this.text = text;
	}
}
