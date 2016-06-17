package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Storage object for student record.
 * 
 * @author Domagoj Tokić
 *
 */
public class StudentRecord {

	/**
	 * Students jmbag
	 */
	String jmbag;

	/**
	 * Students last name
	 */
	String lastName;

	/**
	 * Students first name
	 */
	String firstName;

	/**
	 * Students final grade
	 */
	String finalGrade;

	/**
	 * Creates instance of StudentRecord.
	 * 
	 * @param jmbag Students jmbag
	 * @param lastName Students last name
	 * @param firstName Students first name
	 * @param finalGrade Students final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,
			String finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns last name
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns first name
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns final grade
	 * 
	 * @return final grade
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
}
