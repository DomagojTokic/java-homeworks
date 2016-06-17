package hr.fer.zemris.java.hw13.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility map for voting classes. Contains generic methods used across voting
 * servlets.
 * 
 * @author Domagoj Tokić
 *
 */
public class GlasanjeUtil {

	/** Path to voting definition file. */
	private static final String DEFINITION_PATH = "/WEB-INF/glasanje-definicija.txt";

	/** Path to voting result file. */
	private static final String RESULT_PATH = "/WEB-INF/glasanje-rezultati.txt";

	/**
	 * Returns lines of voting definition file.
	 * 
	 * @param req Servlets request object.
	 * @param resp Servlets response object.
	 * @return lines of voting definition file.
	 */
	public static List<String> getGlasanjeDefLines(HttpServletRequest req,
			HttpServletResponse resp) {
		return getFileLines(DEFINITION_PATH, req, resp);
	}

	/**
	 * Returns lines of voting results file.
	 * 
	 * @param req Servlets request object.
	 * @param resp Servlets response object.
	 * @return lines of voting result file.
	 */
	public static List<String> getGlasanjeRezLines(HttpServletRequest req,
			HttpServletResponse resp) {
		return getFileLines(RESULT_PATH, req, resp);
	}

	/**
	 * Returns list of file lines of given path. If file does not exist, message
	 * is sent to client.
	 * 
	 * @param path Relative path to file.
	 * @param req Servlets request object.
	 * @param resp Servlets response object.
	 * @return list of file lines of given path.
	 */
	private static List<String> getFileLines(String path, HttpServletRequest req,
			HttpServletResponse resp) {
		String fileName = req.getServletContext().getRealPath(path);
		List<String> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));) {
			String line;

			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					list.add(line);
				}
			}
			reader.close();
		} catch (IOException e) {
			try {
				req.setAttribute("message", "Requested file for voting does not exist!");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			} catch (ServletException | IOException ignorable) {
			}
		}

		return list;
	}

	/**
	 * Returns map containing results. Key is name of band, value is number of
	 * votes.
	 * 
	 * @param req Servlets request object.
	 * @param resp Servlets response object.
	 * @return map containing results.
	 */
	public static Map<String, Integer> getResultMap(HttpServletRequest req,
			HttpServletResponse resp) {
		Map<String, Integer> results = new HashMap<>();

		List<String> rezList = getGlasanjeRezLines(req, resp);
		List<String> defList = getGlasanjeDefLines(req, resp);

		Map<String, Integer> rezMap = new HashMap<>();

		for (String line : rezList) {
			String[] literals = line.split("\t");
			if (literals.length == 2) {
				rezMap.put(literals[0], Integer.valueOf(literals[1]));
			}
		}

		for (String line : defList) {
			String[] literals = line.split("\t");
			if (literals.length == 3) {
				results.put(literals[1], rezMap.get(literals[0]));
			}
		}

		return results.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1,
						LinkedHashMap::new));
	}

	/**
	 * Checks if file containing results exists.
	 * 
	 * @param req Servlets request object.
	 * @return True if results file exists, else false.
	 */
	public static boolean resultFileExists(HttpServletRequest req) {
		String fileName = req.getServletContext().getRealPath(RESULT_PATH);
		return new File(fileName).exists();
	}

	/**
	 * Writes results file from given map.
	 * 
	 * @param votingRez Map containing results.
	 * @param req Servlets request object.
	 */
	public static void writeResultFile(Map<String, String> votingRez, HttpServletRequest req) {
		String fileName = req.getServletContext().getRealPath(RESULT_PATH);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for (Entry<String, String> entry : votingRez.entrySet()) {
				writer.write(entry.getKey() + "\t" + entry.getValue());
				writer.newLine();
			}
			writer.close();

		} catch (IOException ignorable) {
		}
	}

}
