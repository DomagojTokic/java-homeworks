package hr.fer.zemris.java.hw14.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton class which returns object which is implementation of data access
 * class. Implementation DAO object can simply be changed by changing class path
 * in config.properties file and restarting application.
 * 
 * @author Domagoj Tokić
 *
 */
public class DAOProvider {

	/** Data access object */
	private static DAO dao;

	/**
	 * Path to file which holds class path to implementation of data access
	 * object.
	 */
	private static final String DAO_CONFIG_FILE_PATH = "config.properties";

	/**
	 * Returns data access object.
	 * 
	 * @return data access object.
	 */
	public static DAO getDao() {
		if (dao == null) {
			dao = initDAO();
		}
		return dao;
	}

	/**
	 * Initializes data access object.
	 * 
	 * @return Data access object.
	 */
	private static DAO initDAO() {
		Properties config = new Properties();
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(DAO_CONFIG_FILE_PATH);
			config.load(input);
			return (DAO) Class.forName(config.getProperty("dao")).newInstance();
		} catch (IOException e) {
			System.err.println("Configuration file not found.");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.err.println("Dao class not found.");
			System.exit(1);
		} catch (InstantiationException e) {
			System.err.println("Unable to cast class set in config.properties file to DAO.");
			System.exit(1);
		} catch (IllegalAccessException e) {
			System.err.println("Unable to access class set in config.properties.");
			System.exit(1);
		}
		return null;
	}

}