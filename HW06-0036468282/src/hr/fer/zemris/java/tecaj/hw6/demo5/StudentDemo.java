package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Examples of filtering, mapping, sorting and executing basic arithmetic
 * operations on large collections of data using data streams.
 * 
 * @author Domagoj Tokić
 *
 */
public class StudentDemo {

	/**
	 * Entrance into demo class
	 * 
	 * @param args No arguments
	 */
	public static void main(String[] args) {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"),
					StandardCharsets.UTF_8);
			List<StudentRecord> records = convert(lines);

			long broj = getAbove25Points(records);

			long broj5 = countGradeA(records);

			List<StudentRecord> odlikasi = gradeAStudents(records);

			List<StudentRecord> odlikasiSortirano = gradeAStudentsSorted(
					records);

			List<String> nepolozeniJMBAGovi = gradeFStudentsSorted(records);

			Map<Integer, List<StudentRecord>> mapaPoOcjenama = generateGradeMap(
					records);

			Map<Integer, Integer> mapaPoOcjenama2 = generateCountForGrades(
					records);

			Map<Boolean, List<StudentRecord>> prolazNeprolaz = mapPassFail(
					records);

			System.out.println(broj);
			System.out.println(broj5);
			System.out.println(odlikasi);
			System.out.println(odlikasiSortirano);
			System.out.println(nepolozeniJMBAGovi);
			System.out.println(mapaPoOcjenama);
			System.out.println(mapaPoOcjenama2);
			System.out.println(prolazNeprolaz);

		} catch (IOException e) {
			System.err.println("Unable to open file students.txt");
			System.exit(1);
		}

	}

	/**
	 * Generates list of students who scored more than total 25 points
	 * 
	 * @param records Student records
	 * @return filtered student records
	 */
	private static long getAbove25Points(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getHalftermExamPoints()
				+ s.getFinalGrade() + s.getLabPoints() > 25).count();
	}

	/**
	 * Returns number of students with grade A
	 * 
	 * @param records Student records
	 * @return number of students with grade A
	 */
	private static long countGradeA(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.count();
	}

	/**
	 * Returns list of students with grade A
	 * 
	 * @param records Student records
	 * @return list of students with grade A
	 */
	private static List<StudentRecord> gradeAStudents(
			List<StudentRecord> records) {
		return records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.collect(Collectors.toList());
	}

	/**
	 * Returns sorted list of students with grade A
	 * 
	 * @param records Student records
	 * @return sorted list of students with grade A
	 */
	private static List<StudentRecord> gradeAStudentsSorted(
			List<StudentRecord> records) {
		Comparator<StudentRecord> pointsComparator = (s1, s2) -> {
			Double points1 = s1.getHalftermExamPoints()
					+ s1.getFinalExamPoints() + s1.getLabPoints();
			Double points2 = s2.getHalftermExamPoints()
					+ s2.getFinalExamPoints() + s2.getLabPoints();
			return points1.compareTo(points2);
		};
		return records.stream().filter(s -> s.getFinalGrade() == 5)
				.sorted(pointsComparator.reversed())
				.collect(Collectors.toList());
	}

	/**
	 * Returns sorted list of students with grade F
	 * 
	 * @param records Student records
	 * @return sorted list of students with grade F
	 */
	private static List<String> gradeFStudentsSorted(
			List<StudentRecord> records) {
		Comparator<StudentRecord> jmbagComparator = (s1, s2) -> s1.getJmbag()
				.compareTo(s2.getJmbag());
		return records.stream()
				.filter(s -> s.getFinalGrade() == 1)
				.sorted(jmbagComparator).map(s -> s.getJmbag())
				.collect(Collectors.toList());
	}

	/**
	 * Groups students with same grades into separate lists
	 * 
	 * @param records Student records
	 * @return map with grade as key and list of students as value
	 */
	private static Map<Integer, List<StudentRecord>> generateGradeMap(
			List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}

	/**
	 * Counts number of students with same grades
	 * 
	 * @param records Student records
	 * @return map with grade as key and number of students with that grade as
	 *         value
	 */
	private static Map<Integer, Integer> generateCountForGrades(
			List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors
				.toMap(StudentRecord::getFinalGrade, s -> 1, Integer::sum));
		/*
		 * collect(Collectors.groupingBy(
		 * StudentRecord::getFinalGrade, Collectors.counting()))
		 */
	}

	/**
	 * Groups students by passing and failing grades
	 * 
	 * @param records Student records
	 * @return map with boolean as key (true if student passed, false if failed)
	 *         and value as list of students
	 */
	private static Map<Boolean, List<StudentRecord>> mapPassFail(
			List<StudentRecord> records) {
		return records.stream()
				.collect(
				Collectors.partitioningBy(s -> s.getFinalGrade() != 1));
	}

	/**
	 * Generates list of {@link StudentRecord} from strings
	 * 
	 * @param lines List of strings with student information
	 * @return List of {@link StudentRecord}
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> students = new ArrayList<>();
		
		for (String line : lines) {
			if (line.isEmpty())
				break;
			
			String[] literals = line.split("\t");
			
			if (literals.length != 7) {
				throw new IllegalArgumentException(
						"Unable to create student for\n" + line);
			}
			
			try {
				students.add(new StudentRecord(literals[0], literals[1],
						literals[2], Double.parseDouble(literals[3]),
						Double.parseDouble(literals[4]),
						Double.parseDouble(literals[5]),
						Integer.parseInt(literals[6])));
			} catch (NumberFormatException e) {
				System.err.println(
						"Points or final grade in wrong format for line\n"
								+ line);
				System.exit(1);
			}
		}
		
		return students;
	}

}
