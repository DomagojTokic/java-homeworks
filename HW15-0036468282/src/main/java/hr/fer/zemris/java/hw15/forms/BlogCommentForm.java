package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.WebUtil;
import hr.fer.zemris.java.hw15.models.BlogComment;

/**
 * Form class for {@link BlogComment}. Used when adding new comment.
 * 
 * @author Domagoj Tokić
 *
 */
public class BlogCommentForm extends AbstractForm {

	/** Email */
	private String email;

	/** Comment text */
	private String text;

	/** Instance of empty form */
	public static final BlogCommentForm EMPTY = new BlogCommentForm("", "");

	/**
	 * Default constructor.
	 */
	public BlogCommentForm() {
	}

	/**
	 * Creates new BlogCommentForm
	 * 
	 * @param email Email
	 * @param text Comment text
	 */
	public BlogCommentForm(String email, String text) {
		this.email = email;
		this.text = text;
		validity.put("email", "");
		validity.put("text", "");
	}

	@Override
	public void checkValidity() {
		if (!WebUtil.isValidEmail(email)) {
			validity.put("email", "Email has invalid format!");
		}
		if (text == null || text.replaceAll("\\s+", "").length() == 0) {
			validity.put("text", "Text cannot be empty!");
		}
	}

	/**
	 * Returns email
	 * 
	 * @return email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email
	 * 
	 * @param email Email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns comment text.
	 * 
	 * @return comment text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets comment text.
	 * 
	 * @param text Comment text
	 */
	public void setText(String text) {
		this.text = text;
	}
}
