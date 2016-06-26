package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class which provides JPA Entity Manager factory object.
 * @author Domagoj
 *
 */
public class JPAEMFProvider {

	/** Entity manager factory */
	public static EntityManagerFactory emf;
	
	/**
	 * Returns entity manager factory.
	 * @return entity manager factory.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets entity manager factory.
	 * @param emf Entity manager factory.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}