package hr.fer.zemris.java.hw14.model;

/**
 * Poll class.
 * 
 * @author Domagoj Tokić
 *
 */
public class Poll {

	/** Poll id */
	private long id;

	/** Poll title */
	private String title;
	
	/** Poll message */
	private String message;
	
	/**
	 * Creates new poll object.
	 */
	public Poll() {
	}

	/**
	 * Returns poll id.
	 * @return poll id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets poll id.
	 * @param id Poll id.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns poll title.
	 * @return poll title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets poll title
	 * @param title Poll title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns poll message.
	 * @return poll message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets poll message.
	 * @param message Poll message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
		
	
}
