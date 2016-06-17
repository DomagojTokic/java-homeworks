package hr.fer.zemris.java.hw14.dao.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import hr.fer.zemris.java.hw14.Initialization;

/**
 * Utility class for database initialization. Every method expects that it has
 * connection available from {@link SQLConnectionProvider} class. This
 * connection must be set up in some outer class before any of methods
 * implemented in this class are used. This class has it's connection set in
 * {@link Initialization} class. Closing connection must occur after all data
 * processing by this class has already occurred.
 * 
 * @author Domagoj Tokić
 *
 */
public class DBInitializationUtil {

	/** Path to definition files of pools */
	private static final String DEF_PATH = "/WEB-INF/polls";

	/**
	 * Creates Polls table.
	 * 
	 * @param connection Connection to database
	 * @return True if table is created.
	 */
	public static boolean createPollTable(Connection connection) {
		return createTable("CREATE TABLE Polls "
				+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "title VARCHAR(150) NOT NULL, "
				+ "message CLOB(2048) NOT NULL)", "Polls", connection);
	}

	/**
	 * Creates PollOptions table.
	 * 
	 * @param connection Connection to database.
	 * @return True if table is created.
	 */
	public static boolean createPollOptionTable(Connection connection) {
		return createTable("CREATE TABLE PollOptions "
				+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "optionTitle VARCHAR(100) NOT NULL, "
				+ "optionLink VARCHAR(150) NOT NULL, pollID BIGINT, "
				+ "votesCount BIGINT, "
				+ "FOREIGN KEY (pollID) REFERENCES Polls(id))",
				"PollOptions", connection);
	}

	/**
	 * Creates new table into database.
	 * 
	 * @param query Query text.
	 * @param name Table name.
	 * @param connection Connection to database.
	 * @return True if table is created.
	 */
	private static boolean createTable(String query, String name, Connection connection) {
		PreparedStatement preparedStatement = null;

		try {
			connection = SQLConnectionProvider.getConnection();
			preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
			preparedStatement.execute();
		} catch (SQLException e) {
			if (tableAlreadyExists(e)) {
				return false;
			} else {
				System.err.println(e.getMessage() + " : " + e.getStackTrace());
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if raised exception correlates table-exists error.
	 * 
	 * @param e raised SQLException
	 * @return True if raised exception correlates table-exists error.
	 */
	public static boolean tableAlreadyExists(SQLException e) {
		boolean exists;
		if (e.getSQLState().equals("X0Y32")) {
			exists = true;
		} else {
			exists = false;
		}
		return exists;
	}

	/**
	 * Initializes tables from poll definition files.
	 * 
	 * @param connection Connection to database.
	 * @param sce {@link ServletContextEvent} of initialization.
	 */
	public static void initTables(Connection connection, ServletContextEvent sce) {
		File[] pollFiles;
		String pollFilesPath = sce.getServletContext().getRealPath(DEF_PATH);
		pollFiles = new File(pollFilesPath).listFiles();
		for (File pollFile : pollFiles) {
			try (BufferedReader reader = new BufferedReader(new FileReader(pollFile))) {
				String pollDefLine = reader.readLine();
				long pollId = addPoll(pollDefLine, connection);
				String pollOptionLine;
				while ((pollOptionLine = reader.readLine()) != null) {
					addPollOption(pollOptionLine, pollId, connection);
				}
			} catch (FileNotFoundException ignorable) {
			} catch (IOException e) {
				System.err.println("Unable to read from poll file " + pollFile.getName());
			}
		}
	}

	/**
	 * Adds new poll to database.
	 * 
	 * @param pollDefLine Pool definition line in input file.
	 * @param connection Connection to database.
	 * @return Id of newly created pool.
	 */
	private static long addPoll(String pollDefLine, Connection connection) {
		String[] split = pollDefLine.split("\t");
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO Polls (title, message) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, split[0]);
			preparedStatement.setString(2, split[1]);
			preparedStatement.executeUpdate();

			ResultSet rset = preparedStatement.getGeneratedKeys();

			try {
				if (rset != null && rset.next()) {
					return rset.getLong(1);
				}
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Adds new poll option to defined pool.
	 * 
	 * @param pollOptionLine Line containing information of pool option.
	 * @param pollId Pool Id.
	 * @param connection Connection to database. 
	 */
	private static void addPollOption(String pollOptionLine, long pollId,
			Connection connection) {
		String[] split = pollOptionLine.split("\t");
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(
					"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,"
							+ pollId + ", 0)");
			preparedStatement.setString(1, split[0]);
			preparedStatement.setString(2, split[1]);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
