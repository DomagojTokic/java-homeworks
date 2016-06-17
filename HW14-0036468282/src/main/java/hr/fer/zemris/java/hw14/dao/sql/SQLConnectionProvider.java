package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * Storage of connections to database. Uses {@link ThreadLocal} class for
 * storage.
 * 
 * @author Domagoj Tokić
 *
 */
public class SQLConnectionProvider {

	/** Currently active database connections */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Sets connection to current thread or removes it if argument is
	 * <code>null</code>.
	 * 
	 * @param con Connection to database.
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Returns connection to database which user is able to use.
	 * 
	 * @return Connection to database.
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}