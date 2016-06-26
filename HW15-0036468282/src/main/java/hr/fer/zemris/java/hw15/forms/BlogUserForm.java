package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.WebUtil;
import hr.fer.zemris.java.hw15.models.BlogUser;

/**
 * Form class for {@link BlogUser}. Used when registering user.
 * 
 * @author Domagoj Tokić
 *
 */
public class BlogUserForm extends AbstractForm {

	/** First name */
	private String firstName;

	/** Last name */
	private String lastName;

	/** Nickname */
	private String nick;

	/** Email */
	private String email;

	/** Password */
	private String password;

	/** Empty form instance */
	public static final BlogUserForm EMPTY = new BlogUserForm("", "", "", "", "");

	/**
	 * Default constructor
	 */
	public BlogUserForm() {
	}

	/**
	 * Creates new BlogUserForm
	 * 
	 * @param firstName First name
	 * @param lastName Last name
	 * @param nick Nickname
	 * @param email Email
	 * @param password Password
	 */
	public BlogUserForm(String firstName, String lastName, String nick, String email,
			String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.password = password;
		validity.put("firstName", "");
		validity.put("lastName", "");
		validity.put("nick", "");
		validity.put("email", "");
		validity.put("password", "");
	}

	@Override
	public void checkValidity() {
		if (firstName == null || firstName.length() < 3) {
			validity.put("firstName", "First name must be at least 3 characters long");
		} else if (firstName.matches(".*\\d+.*")) {
			validity.put("firstName", "First name must not contain numbers");
		}
		if (lastName == null || lastName.length() < 3) {
			validity.put("lastName", "Last name must be at least 3 characters long");
		} else if (lastName.matches(".*\\d+.*")) {
			validity.put("lastName", "Last name must not contain numbers");
		}
		if (nick == null || nick.length() < 3)
			validity.put("nick", "Nickname must be at least 3 characters long");
		if (!WebUtil.isValidEmail(email))
			validity.put("email", "Email has invalid format");
		if (password == null || password.length() < 7)
			validity.put("password", "Password must be at least 7 characters long");
	}

	/**
	 * Returns first name.
	 * @return first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets first name.
	 * @param firstName First name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns last name.
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets last name
	 * @param lastName Last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns nickname.
	 * @return nickname.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets nickname.
	 * @param nick Nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Returns email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password
	 * @param password Password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
