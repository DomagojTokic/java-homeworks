package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;

/**
 * List of StudentRecord instances which remembers greatest lengths of
 * StudentRecord variables.
 * 
 * @author Domagoj Tokić
 *
 */
public class StudentResultList extends ArrayList<StudentRecord> {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Width of longest JMBAG
	 */
	private int jmbagWidth;

	/**
	 * Width of longest first name
	 */
	private int firstNameWidth;

	/**
	 * Width of longest last name
	 */
	private int lastNameWidth;

	/**
	 * Width of longest grade
	 */
	private int gradeWidth;

	/**
	 * Returns width of longest JMBAG
	 * 
	 * @return width of longest JMBAG
	 */
	public int getJmbagWidth() {
		return jmbagWidth;
	}

	/**
	 * Returns width of longest first name
	 * 
	 * @return width of longest first name
	 */
	public int getFirstNameWidth() {
		return firstNameWidth;
	}

	/**
	 * Returns width of longest last name
	 * 
	 * @return width of longest last name
	 */
	public int getLastNameWidth() {
		return lastNameWidth;
	}

	/**
	 * Returns width of longest grade
	 * 
	 * @return width of longest grade
	 */
	public int getGradeWidth() {
		return gradeWidth;
	}

	/**
	 * Adds StudentRecord instance into list and updates width attributes.
	 * 
	 * @return true if StudentRecord instance is added, false if instance is
	 *         null.
	 */
	@Override
	public boolean add(StudentRecord record) {
		if (record == null) {
			return false;
		}
		super.add(record);

		if (jmbagWidth < record.getJmbag().toString().length()) {
			jmbagWidth = record.getJmbag().toString().length();
		}
		if (firstNameWidth < record.getFirstName().toString().length()) {
			firstNameWidth = record.getFirstName().toString().length();
		}
		if (lastNameWidth < record.getLastName().toString().length()) {
			lastNameWidth = record.getLastName().toString().length();
		}
		if (gradeWidth < record.getJmbag().toString().length()) {
			gradeWidth = record.getFinalGrade().toString().length();
		}

		return true;
	}

}
