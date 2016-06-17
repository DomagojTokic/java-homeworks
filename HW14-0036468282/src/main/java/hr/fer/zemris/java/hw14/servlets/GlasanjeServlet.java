package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet for preparing data - band id in link and band name displayed on
 * voting page.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(name="glasanje", urlPatterns={"/servleti/glasanje"})
public class GlasanjeServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pollID = req.getParameter("pollID");
		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID, false);
		Poll poll = DAOProvider.getDao().getPollById(Long.parseLong(pollID));

		req.setAttribute("options", options);
		req.setAttribute("poll", poll);
		req.setAttribute("pollID", pollID);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
