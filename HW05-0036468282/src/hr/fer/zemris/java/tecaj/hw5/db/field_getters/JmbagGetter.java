package hr.fer.zemris.java.tecaj.hw5.db.field_getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class for returning jmbag from {@link StudentRecord}
 * 
 * @author Domagoj Tokić
 *
 */
public class JmbagGetter implements IFieldGetter {

	/**
	 * Returns jmbag from StudentRecord object
	 * 
	 * @return jmbag from StudentRecord object
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getJmbag();
	}

}
