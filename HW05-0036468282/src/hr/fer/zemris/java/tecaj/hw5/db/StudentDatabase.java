package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw5.db.filter.IFilter;

/**
 * Storage of {@link StudentRecord} instances. Contains database (list) and
 * attribute 'jmbag' index (map).
 * 
 * @author Domagoj Tokić
 *
 */
public class StudentDatabase {

	/**
	 * Database of student records
	 */
	List<StudentRecord> database;

	/**
	 * Attribute 'jmbag' index
	 */
	SimpleHashtable<String, StudentRecord> jmbagIndex;

	/**
	 * Creates instance of Student database by parsing list of strings. Every
	 * string represents one student record.
	 * Example of valid String:
	 * "0000000001\tAkšamović\tMarin\t2"
	 * 
	 * @param records List of student records
	 */
	public StudentDatabase(List<String> records) {
		database = new ArrayList<>();
		jmbagIndex = new SimpleHashtable<>();
		for (String record : records) {
			String[] literals = record.split("\\t");
			if (literals.length == 4) {
				database.add(new StudentRecord(literals[0], literals[1],
						literals[2], literals[3]));
				jmbagIndex.put(literals[0], new StudentRecord(literals[0],
						literals[1], literals[2], literals[3]));
			} else {
				System.err.println("Invalid database format");
				System.exit(1);
			}
		}
	}

	/**
	 * Returns instance of StudentRecord with matching attribute 'jmbag'.
	 * 
	 * @param jmbag Attribute 'jmbag' of requested student record
	 * @return instance of StudentRecord with matching attribute 'jmbag'.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return jmbagIndex.get(jmbag);
	}

	/**
	 * Returns list of student records which satisfy conditions defined in
	 * filter.
	 * 
	 * @param filter Filter of student records
	 * @return list of student records which satisfy conditions defined in
	 *         filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> result = new StudentResultList();
		for (StudentRecord record : database) {
			if (filter.accepts(record)) {
				result.add(record);
			}
		}
		return result;
	}

}
