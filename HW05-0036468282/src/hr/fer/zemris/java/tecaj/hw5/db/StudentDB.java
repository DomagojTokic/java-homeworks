package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.filter.QueryFilter;
import hr.fer.zemris.java.tecaj.hw5.db.query.IndexedQueryParser;
import hr.fer.zemris.java.tecaj.hw5.db.query.QueryParserException;

/**
 * Simulation of database containing student records. Reads input from file
 * database.txt located in project folder. Provides user with two types of
 * database queries: 'query' and 'indexquery'. Standard query can filter
 * database by attributes 'jmbag', 'lastName' and 'firstName'. Supported
 * comparison operations are: '=', '!=', '<', '<=', '>', '>=' and 'LIKE'. 'LIKE'
 * operator can use one special character '*' which signifies any random series
 * of characters. In current implementation all values for comparisons are
 * strings so they need to be surrounded by double quotes. Indexed query
 * supports only indexed attribute 'jmbag' and operator '='.
 * 
 * @author Domagoj Tokić
 *
 */
public class StudentDB {

	/**
	 * Local instance of {@link StudentDatabase}
	 */
	static StudentDatabase database;

	/**
	 * Entrance into student database query.
	 * 
	 * @param args No arguments
	 */
	public static void main(String[] args) {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"),
					StandardCharsets.UTF_8);
			database = new StudentDatabase(lines);
			String query;
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(System.in));

			System.out.println("Please enter query command:");

			while (true) {
				query = reader.readLine().trim();

				if (query.equals("exit")) {
					break;
				}

				try {
					StudentResultList result = processQuery(query);
					printQueryResult(result);
					System.out.println("Records selected: " + result.size());
				} catch (QueryParserException e) {
					System.err.println(e.getMessage());
				} catch (UnsupportedOperationException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					System.err.println("This shouldn't happen");
				}

			}
			System.out.println("Finished working with database...");
		} catch (IOException e) {
			System.err.println("Unable to read from database.");
		}

	}

	/**
	 * Decides which type of query should occur (regular or indexed) and
	 * delegates query conditions to matching classes.
	 * 
	 * @param query Query string
	 * @return list of student records which satisfy query
	 */
	private static StudentResultList processQuery(String query) {
		if (query.length() >= 5 && query.substring(0, 5).equals("query")) {
			query = query.substring(5, query.length());

			return (StudentResultList) database.filter(new QueryFilter(query));

		} else if (query.length() >= 10
				&& query.substring(0, 10).equals("indexquery")) {
			List<StudentRecord> result = new StudentResultList();

			query = query.substring(10, query.length());
			String indexedAttribute = new IndexedQueryParser(query)
					.getIndexedAttributeValue();

			StudentRecord record = database.forJMBAG(indexedAttribute);

			if (record != null) {
				result.add(record);
			}
			return (StudentResultList) result;
		}

		throw new UnsupportedOperationException(
				"Please write valid query command: 'query' or 'indexquery' or 'exit' for end of session.");
	}
	
	/**
	 * Prints formatted result of query.
	 * 
	 * @param result List of student records which are result of query
	 */
	private static void printQueryResult(StudentResultList result) {

		if (result.isEmpty()) {
			return;
		}

		int jmbagWidth = result.getJmbagWidth();
		int firstNameWidth = result.getFirstNameWidth();
		int lastNameWidth = result.getLastNameWidth();
		int gradeWidth = result.getGradeWidth();

		String horisontalBorder = "+"
				+ new String(new char[jmbagWidth + 2]).replace('\0', '=') + "+"
				+ new String(new char[lastNameWidth + 2]).replace('\0', '=')
				+ "+"
				+ new String(new char[firstNameWidth + 2]).replace('\0', '=')
				+ "+" + new String(new char[gradeWidth + 2]).replace('\0', '=')
				+ "+";

		System.out.println(horisontalBorder);

		for (StudentRecord record : result) {
			System.out.printf(
					"| %-" + jmbagWidth + "s | %-" + lastNameWidth + "s | %-"
							+ firstNameWidth + "s | %-" + gradeWidth + "s |\n",
					record.getJmbag(), record.getLastName(),
					record.getFirstName(), record.getFinalGrade());
		}

		System.out.println(horisontalBorder);
	}

}
