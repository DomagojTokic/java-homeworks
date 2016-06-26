package hr.fer.zemris.java.hw15;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMFProvider;

/**
 * Initializator of entity factory which is used for cumunication with database.
 * 
 * @author Domagoj Tokić
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");
		sce.getServletContext().setAttribute("blog.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext()
				.getAttribute("blog.emf");
		if (emf != null) {
			emf.close();
		}
	}
}