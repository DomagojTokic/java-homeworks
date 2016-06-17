package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for preparing data - band id in link and band name displayed on
 * voting page.
 * 
 * @author Domagoj Tokić
 *
 */
public class GlasanjeServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> bandMap = new HashMap<>();
		for (String line : GlasanjeUtil.getGlasanjeDefLines(req, resp)) {
			String[] literals = line.split("\t");
			bandMap.put(literals[0], literals[1]);
		}

		req.setAttribute("bandMap", bandMap);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
