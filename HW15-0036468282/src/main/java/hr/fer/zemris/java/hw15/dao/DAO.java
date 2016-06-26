package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.models.BlogComment;
import hr.fer.zemris.java.hw15.models.BlogEntry;
import hr.fer.zemris.java.hw15.models.BlogUser;

/**
 * Interface for getting {@link BlogEntry}, {@link BlogUser} and
 * {@link BlogComment} objects for application storage.
 * 
 * @author Domagoj Tokić
 *
 */
public interface DAO {

	/**
	 * Returns all blog entries made by specified user.
	 * 
	 * @param nick User nickname
	 * @return blog entries made by specified user.
	 * @throws DAOException
	 */
	public List<BlogEntry> getBlogEntries(String nick) throws DAOException;

	/**
	 * Returns all blog users.
	 * 
	 * @return all blog users.
	 */
	public List<BlogUser> getBlogUsers();

	/**
	 * Checks if given nick is already in use.
	 * 
	 * @param nick Nickname
	 * @return True id nickanme is available.
	 */
	public boolean checkNickAvailability(String nick);

	/**
	 * Returns comments for requested blog entry
	 * 
	 * @param id Blog entry id
	 * @return comments for requested blog entry
	 */
	public List<BlogComment> getBlogComments(Long id);

	/**
	 * Returns blog user.
	 * 
	 * @param username Username
	 * @param passwordHash Password hash
	 * @return blog user.
	 */
	public BlogUser getBlogUser(String username, String passwordHash);

	/**
	 * Returns blog user.
	 * 
	 * @param id Blog user id
	 * @return blog user.
	 */
	public BlogUser getBlogUser(Long id);

	/**
	 * Returns blog entry.
	 * 
	 * @param id Blog entry id
	 * @return blog entry.
	 */
	public BlogEntry getBlogEntry(Long id);

	/**
	 * Adds new blog user.
	 * 
	 * @param user Blog user
	 */
	public void addBlogUser(BlogUser user);

	/**
	 * Adds new blog entry
	 * 
	 * @param entry Blog entry
	 */
	public void addBlogEntry(BlogEntry entry);

	/**
	 * Adds new blog entry comment
	 * 
	 * @param comment Blog entry comment
	 */
	public void addBlogComment(BlogComment comment);

}