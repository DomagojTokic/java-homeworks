package hr.fer.zemris.java.hw14.model;

import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.omg.PortableServer.IMPLICIT_ACTIVATION_POLICY_ID;

/**
 * Singleton razred koji zna koga treba vratiti kao pružatelja
 * usluge pristupa podsustavu za perzistenciju podataka.
 * Uočite da, iako je odluka ovdje hardkodirana, naziv
 * razreda koji se stvara mogli smo dinamički pročitati iz
 * konfiguracijske datoteke i dinamički učitati -- time bismo
 * implementacije mogli mijenjati bez ikakvog ponovnog kompajliranja
 * koda.
 * 
 * @author marcupic
 *
 */
public class DAOProvider {

	private static DAO dao;
	
	private static final String CONFIG_FILE = "/classes/impl.properties";
	
	/**
	 * Dohvat primjerka.
	 * 
	 * @return objekt koji enkapsulira pristup sloju za perzistenciju podataka.
	 */
	public static DAO getDao() {
		if(dao == null) {
			ResourceBundle config = ResourceBundle.getBundle(CONFIG_FILE);
			try {
				dao = (DAO) Class.forName(config.getString("model")).newInstance();
			} catch (InstantiationException e) {
				System.err.println("Unable to instantiate model");
				System.exit(1);
			} catch (IllegalAccessException e) {
				System.err.println("Unable to acess to model");
				System.exit(1);
			} catch (ClassNotFoundException e) {
				System.err.println("Model class not found");
				System.exit(1);
			}
		}
		return dao;
	}
	
}