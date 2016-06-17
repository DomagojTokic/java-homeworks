package hr.fer.zemris.java.tecaj.hw5.db.field_getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class for returning name from {@link StudentRecord}
 * 
 * @author Domagoj Tokić
 *
 */
public class FirstNameGetter implements IFieldGetter {

	/**
	 * Returns name from StudentRecord object
	 * 
	 * @return first name from StudentRecord object
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getFirstName();
	}

}
