package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Interface towards part of system dedicated to accessing data.
 * 
 * @author Domagoj Tokić
 *
 */
public interface DAO {

	/**
	 * Returns list of {@link Poll} objects.
	 * 
	 * @return list of {@link Poll} objects.
	 */
	List<Poll> getPolls();

	/**
	 * Returns {@link Poll} by it's id.
	 * 
	 * @param l Poll id.
	 * @return {@link Poll} by it's id.
	 */
	Poll getPollById(long l);

	/**
	 * Returns list of {@link PollOption} objects which correspond with given
	 * {@link Poll} id.
	 * 
	 * @param id {@link Poll} id.
	 * @param descending Descending ordering of {@link PollOption} objects in
	 *            list.
	 * @return list of {@link PollOption} objects which correspond with given
	 *         {@link Poll} id.
	 */
	List<PollOption> getPollOptions(String id, boolean descending);

	/**
	 * Returns {@link PollOption} objects with highest voting count in
	 * corresponding poll.
	 * 
	 * @param id {@link Poll} id.
	 * @return {@link PollOption} objects with highest voting count in
	 *         corresponding poll.
	 */
	List<PollOption> getTopPollOptions(String id);

	/**
	 * Adds vote to certain {@link PollOption}.
	 * @param pollId {@link Poll} id.
	 * @param optionId {@link PollOption} id.
	 */
	void addVote(String pollId, String optionId);

}