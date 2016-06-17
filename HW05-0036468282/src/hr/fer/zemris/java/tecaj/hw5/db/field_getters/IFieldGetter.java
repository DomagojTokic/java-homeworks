package hr.fer.zemris.java.tecaj.hw5.db.field_getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Getter class for {@link StudentRecord}
 * 
 * @author Domagoj Tokić
 *
 */
public interface IFieldGetter {
	
	/**
	 * Getter for StudentRecord. Returns one string attribute.
	 * 
	 * @param record Instance of StudentRecord for attribute extraction.
	 * @return String attribute
	 */
	String get(StudentRecord record);

}
