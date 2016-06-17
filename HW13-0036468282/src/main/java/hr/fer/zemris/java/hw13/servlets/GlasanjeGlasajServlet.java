package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which register new vote by updating file containing voting results.
 * 
 * @author Domagoj Tokić
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> votingRez = new TreeMap<>();
		if (!GlasanjeUtil.resultFileExists(req)) {
			for (String line : GlasanjeUtil.getGlasanjeDefLines(req, resp)) {
				String[] literals = line.split("\t");
				votingRez.put(literals[0], "0");
			}
		} else {
			for (String line : GlasanjeUtil.getGlasanjeRezLines(req, resp)) {
				String[] literals = line.split("\t");
				votingRez.put(literals[0], literals[1]);
			}
		}
		Integer newCount = Integer.valueOf(votingRez.get(req.getParameter("id"))) + 1;
		votingRez.put((String) req.getParameter("id"), newCount.toString());

		GlasanjeUtil.writeResultFile(votingRez, req);
		req.getRequestDispatcher("glasanje-rezultati").forward(req, resp);
	}

}
