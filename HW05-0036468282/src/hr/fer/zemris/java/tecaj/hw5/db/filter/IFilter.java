package hr.fer.zemris.java.tecaj.hw5.db.filter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface for classes which tests StudentRecord objects with query
 * conditions.
 * 
 * @author Domagoj Tokić
 *
 */
public interface IFilter {

	/**
	 * Tests instance of StudentRecord if it accepts query conditions
	 * 
	 * @param record Student record for testing
	 * @return true if record accepts query conditions, else false.
	 */
	public boolean accepts(StudentRecord record);

}
