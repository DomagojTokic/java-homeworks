package hr.fer.zemris.java.hw13.servlets;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for preparing data displayed on voting results page.
 * 
 * @author Domagoj Tokić
 *
 */
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> defMap = new HashMap<>();
		Map<String, Integer> rezMap = GlasanjeUtil.getResultMap(req, resp);
		Map<String, String> songMap = new HashMap<>();

		List<String> defList = GlasanjeUtil.getGlasanjeDefLines(req, resp);

		initDefAndSongMap(defMap, songMap, defList);

		if (defMap.isEmpty() || songMap.isEmpty() || rezMap.isEmpty()) {
			req.setAttribute("message", "Voting file is empty");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}

		Map<String, String> outputSongs = getOutputSongsMap(songMap, rezMap);

		req.getSession().setAttribute("results", rezMap);
		req.setAttribute("outputSongs", outputSongs);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Initializes definitions map and song map. Definitions map has "id" as key
	 * and band name as value. Song map has band name as key and link to song.
	 * 
	 * @param defMap Definitions map.
	 * @param songMap Song map.
	 * @param defList List of rows from definitions file.
	 */
	private void initDefAndSongMap(Map<String, String> defMap, Map<String, String> songMap,
			List<String> defList) {
		for (String line : defList) {
			String[] literals = line.split("\t");
			if (literals.length == 3) {
				defMap.put(literals[0], literals[1]);
				songMap.put(literals[1], literals[2]);
			}
		}
	}

	/**
	 * Returns map of songs which have the highest number of votes.
	 * 
	 * @param songMap Song map.
	 * @param results Map with voting results.
	 * @return map of songs which have the highest number of votes.
	 */
	private Map<String, String> getOutputSongsMap(Map<String, String> songMap,
			Map<String, Integer> results) {
		Map<String, String> outputSongs = new LinkedHashMap<>();
		int counter = 0;
		int top = 0;
		for (Entry<String, Integer> entry : results.entrySet()) {
			if (counter == 0 || results.get(entry.getKey()) == top) {
				top = results.get(entry.getKey());
				outputSongs.put(entry.getKey(), songMap.get(entry.getKey()));
				counter++;
			}
		}
		return outputSongs;
	}

}
