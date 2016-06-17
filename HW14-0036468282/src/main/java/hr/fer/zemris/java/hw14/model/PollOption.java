package hr.fer.zemris.java.hw14.model;

/**
 * Poll option class.
 * 
 * @author Domagoj Tokić
 *
 */
public class PollOption {

	/** Poll option id. */
	private long id;

	/** Poll option title. */
	private String optionTitle;

	/** Poll option link. */
	private String optionLink;

	/** Poll id. */
	private long pollID;

	/** Number of votes cast to poll option */
	private long votesCount;

	/**
	 * Creates new Poll Option object.
	 */
	public PollOption() {
	}

	/**
	 * Returns poll option id.
	 * 
	 * @return poll option id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets poll option id.
	 * 
	 * @param id Poll option id.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns poll option title.
	 * 
	 * @return poll option title.
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets poll option title.
	 * 
	 * @param optionTitle Poll option title.
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Returns poll option link.
	 * 
	 * @return poll option link.
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets poll option link.
	 * 
	 * @param optionLink Poll option link.
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Returns poll id.
	 * 
	 * @return poll id.
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets id of poll to which poll option belongs.
	 * 
	 * @param pollID Id of poll to which poll option belongs.
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Returns number of votes cast to poll option.
	 * 
	 * @return number of votes cast to poll option.
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets number of votes cast to poll option.
	 * 
	 * @param votesCount Number of votes cast to poll option.
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

}
