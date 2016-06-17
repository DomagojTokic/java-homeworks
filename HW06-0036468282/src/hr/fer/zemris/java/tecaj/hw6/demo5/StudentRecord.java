package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * Storage of students personal data related to specific course.
 * 
 * @author Domagoj Tokić
 *
 */
public class StudentRecord {

	/** Student JMBAG */
	private String jmbag;

	/** Student first name */
	private String firstName;

	/** Student last name */
	private String lastName;

	/** Half term exam points */
	private Double halftermExamPoints;

	/** Final exam points */
	private Double finalExamPoints;

	/** Laboratory points */
	private Double labPoints;

	/** Final grade */
	private int finalGrade;

	/**
	 * Creates instance of {@link StudentRecord}
	 * 
	 * @param jmbag Student JMBAG
	 * @param firstName Student first name
	 * @param lastName Student last name
	 * @param halftermExamPoints Half term exam points
	 * @param finalExamPoints Final exam points
	 * @param labPoints Laboratory points
	 * @param finalGrade Final grade
	 */
	public StudentRecord(String jmbag, String firstName, String lastName,
			Double halftermExamPoints, Double finalExamPoints, Double labPoints,
			int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.halftermExamPoints = halftermExamPoints;
		this.finalExamPoints = finalExamPoints;
		this.labPoints = labPoints;
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns JMBAG
	 * 
	 * @return JMBAG
	 */
	public String getJmbag() {
		return jmbag;
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
	 * Returns last name
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns half term exam points
	 * 
	 * @return half term exam points
	 */
	public Double getHalftermExamPoints() {
		return halftermExamPoints;
	}

	/**
	 * Returns final exam points
	 * 
	 * @return final exam points
	 */
	public Double getFinalExamPoints() {
		return finalExamPoints;
	}

	/**
	 * Returns laboratory points
	 * 
	 * @return laboratory points
	 */
	public Double getLabPoints() {
		return labPoints;
	}

	/**
	 * Returns final grade
	 * 
	 * @return final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	public String toString() {
		return jmbag + " " + firstName + " " + lastName + " "
				+ halftermExamPoints + " " + finalExamPoints + " " + labPoints
				+ " " + finalGrade + "\n";
	}

}
