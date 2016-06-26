package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.WebUtil;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.models.BlogEntry;

/**
 * Servlet which handles editing of blog entry.
 * 
 * @author Domagoj
 *
 */
@WebServlet(urlPatterns = { "/servleti/edit_entry" })
public class EditEntryServlet extends HttpServlet {

	/** Default serial version */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long id = Long.parseLong((String) req.getParameter("id"));
		BlogEntry entry = DAOProvider.getDao().getBlogEntry(id);
		
		if(entry == null) {
			WebUtil.sendError("Requested entry does not exist!", req, resp);
			return;
		}

		if (!entry.getAuthor().getNick().equals(req.getSession().getAttribute("nick"))) {
			WebUtil.sendError("Forbidden access error!", req, resp);
		}
		req.setAttribute("entry", entry);

		req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long id = Long.parseLong((String) req.getParameter("id"));
		BlogEntry entry = DAOProvider.getDao().getBlogEntry(id);
		String nick = (String) req.getSession().getAttribute("nick");

		if(entry == null) {
			WebUtil.sendError("Requested entry does not exist!", req, resp);
			return;
		}
		
		if (!entry.getAuthor().getNick().equals(nick)) {
			WebUtil.sendError("Forbidden access error!", req, resp);
		}

		String title = req.getParameter("title");
		String text = req.getParameter("text");

		entry.setTitle(title);
		entry.setText(text);
		entry.setLastModifiedAt(new Date());

		resp.sendRedirect("author/" + nick + "/" + entry.getId());
	}

}
