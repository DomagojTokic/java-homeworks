package hr.fer.zemris.java.hw14.servlets;

import java.util.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet for preparing data displayed on voting results page.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(name="glasanjerezultatiservlet", urlPatterns={"/servleti/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pollID = req.getParameter("pollID");
		List<PollOption> results = DAOProvider.getDao().getPollOptions(pollID, true);
		List<PollOption> top = DAOProvider.getDao().getTopPollOptions(pollID);
		Poll poll = DAOProvider.getDao().getPollById(Long.valueOf(pollID));

		req.setAttribute("results", results);
		req.setAttribute("outputSongs", top);
		req.setAttribute("poll", poll);
		req.setAttribute("pollID", pollID);		

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
