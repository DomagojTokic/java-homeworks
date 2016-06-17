package hr.fer.zemris.java.tecaj.hw5.db.field_getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class for returning last name from {@link StudentRecord}
 * 
 * @author Domagoj Tokić
 *
 */
public class LastNameGetter implements IFieldGetter {

	/**
	 * Returns last name from StudentRecord object
	 * 
	 * @return last name from StudentRecord object
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getLastName();
	}

}
