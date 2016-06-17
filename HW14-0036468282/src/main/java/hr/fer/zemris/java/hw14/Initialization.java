package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.dao.sql.DBInitializationUtil;
import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;

/**
 * Servlet context listener which initializes connection pool to database and
 * initializes database tables if they currently don't exist.
 * 
 * @author Domagoj Tokić
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	/** Path to database configuration file */
	private static final String DB_CONFIG_PATH = "/WEB-INF/dbsettings.properties";

	/** Minimal database connection pool size */
	private static final int MIN_POOL_SIZE = 2;

	/** Maximal database connection pool size */
	private static final int MAX_POOL_SIZE = 20;

	/** Database connection pool step size */
	private static final int POOL_SIZE_STEP = 2;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties conProperties = new Properties();
		try {
			conProperties.load(sce.getServletContext().getResourceAsStream(DB_CONFIG_PATH));
		} catch (IOException e) {
			System.err.println("Unable to read database properties");
			System.exit(1);
		}

		String host = conProperties.getProperty("host");
		String port = conProperties.getProperty("port");
		String dbName = conProperties.getProperty("name");
		String user = conProperties.getProperty("user");
		String password = conProperties.getProperty("password");

		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName
				+ ";user=" + user + ";password=" + password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Error during initialization of connection pool.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		cpds.setMinPoolSize(MIN_POOL_SIZE);
		cpds.setAcquireIncrement(POOL_SIZE_STEP);
		cpds.setMaxPoolSize(MAX_POOL_SIZE);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

		Connection connection = null;
		try {
			connection = cpds.getConnection();
			SQLConnectionProvider.setConnection(connection);
			boolean pollTableInit = DBInitializationUtil.createPollTable(connection);
			boolean pollOptionTableInit = DBInitializationUtil.createPollOptionTable(connection);
			if (pollTableInit && pollOptionTableInit) {
				DBInitializationUtil.initTables(connection, sce);
			}
			connection.close();
		} catch (SQLException e) {
			try {
				throw new IOException("Database isn't available.", e);
			} catch (IOException ignorable) {
			}
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}