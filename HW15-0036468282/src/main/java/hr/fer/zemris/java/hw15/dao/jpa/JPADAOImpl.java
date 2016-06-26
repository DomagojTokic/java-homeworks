package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.models.BlogComment;
import hr.fer.zemris.java.hw15.models.BlogEntry;
import hr.fer.zemris.java.hw15.models.BlogUser;

/**
 * Implementation of {@link DAO} which uses JPA technology to access database
 * and return data.
 * 
 * @author Domagoj Tokić
 *
 */
public class JPADAOImpl implements DAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getBlogEntries(String nick) throws DAOException {
		BlogUser blogUser;
		List<BlogUser> queryResult = JPAEMProvider.getEntityManager()
				.createQuery("select u from BlogUser as u where u.nick=:n").setParameter("n", nick)
				.getResultList();
		if (queryResult.isEmpty()) {
			throw new DAOException("Requested user does not exist!");
		} else {
			blogUser = queryResult.get(0);
		}
		return blogUser.getBlogEntries();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getBlogUsers() {
		return JPAEMProvider.getEntityManager().createQuery("select a from BlogUser as a")
				.getResultList();
	}

	@Override
	public boolean checkNickAvailability(String nick) {
		return JPAEMProvider.getEntityManager()
				.createQuery("select u from BlogUser as u where u.nick=:n").setParameter("n", nick)
				.getResultList().size() == 0;
	}

	@Override
	public List<BlogComment> getBlogComments(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogEntry blogEntry = em.find(BlogEntry.class, id);
		if (blogEntry == null)
			throw new DAOException("Blog entry with given id does not exist!");
		return new ArrayList<>(blogEntry.getComments());
	}

	@SuppressWarnings("unchecked")
	@Override
	public BlogUser getBlogUser(String username, String passwordHash) {
		List<BlogUser> queryResult = JPAEMProvider.getEntityManager()
				.createQuery("select u from BlogUser as u where u.nick=:un and u.passwordHash=:ph")
				.setParameter("un", username).setParameter("ph", passwordHash).getResultList();
		if (queryResult.isEmpty()) {
			return null;
		} else {
			return queryResult.get(0);
		}
	}

	@Override
	public BlogUser getBlogUser(Long id) {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser blogUser = em.find(BlogUser.class, id);
		if (blogUser == null)
			throw new DAOException("Blog entry with given id does not exist!");
		return blogUser;
	}

	@Override
	public BlogEntry getBlogEntry(Long id) {
		EntityManager em = JPAEMProvider.getEntityManager();
		return em.find(BlogEntry.class, id);
	}

	@Override
	public void addBlogUser(BlogUser user) {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public void addBlogEntry(BlogEntry entry) {
		JPAEMProvider.getEntityManager().persist(entry);
	}

	@Override
	public void addBlogComment(BlogComment comment) {
		JPAEMProvider.getEntityManager().persist(comment);
	}

}