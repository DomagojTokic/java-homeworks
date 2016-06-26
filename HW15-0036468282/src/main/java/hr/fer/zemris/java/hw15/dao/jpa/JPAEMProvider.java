package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * JPA entity manager provider. Provides connection to database used in data
 * access.
 * 
 * @author Domagoj Tokić
 *
 */
public class JPAEMProvider {

	/** Collection of {@link LocalData} which are thread-local */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Returns entity manager.
	 * 
	 * @return entity manager.
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Closes entity manager.
	 * 
	 * @throws DAOException if entity manager is unable to commit transaction or
	 *             close.
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	/**
	 * Wrapper for {@link EntityManager}
	 * 
	 * @author Domagoj Tokić
	 *
	 */
	private static class LocalData {

		/** Entity manager */
		EntityManager em;
	}

}