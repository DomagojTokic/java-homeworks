package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Servlet which register new vote by updating file containing voting results.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(name="glasanjeglasajservlet", urlPatterns={"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String optionID = req.getParameter("id");
		String pollID = req.getParameter("pollID");
		DAOProvider.getDao().addVote(pollID, optionID);
		req.getRequestDispatcher("glasanje-rezultati").forward(req, resp);
	}

}
