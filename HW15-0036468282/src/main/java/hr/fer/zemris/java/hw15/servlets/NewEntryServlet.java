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
import hr.fer.zemris.java.hw15.forms.AbstractForm;
import hr.fer.zemris.java.hw15.forms.BlogEntryForm;
import hr.fer.zemris.java.hw15.models.BlogEntry;
import hr.fer.zemris.java.hw15.models.BlogUser;

/**
 * Servlet for adding new entry to blog.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(urlPatterns = { "/servleti/new_entry" })
public class NewEntryServlet extends HttpServlet {

	/** Default serial version */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nick = (String) req.getSession().getAttribute("nick");
		if (nick == null) {
			WebUtil.sendError("You must be logged in to create entry!", req, resp);
		}
		req.setAttribute("form", BlogEntryForm.EMPTY);
		req.getRequestDispatcher("/WEB-INF/pages/createEntry.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nick = (String) req.getSession().getAttribute("nick");

		if (nick == null) {
			WebUtil.sendError("You must be logged in to create entry!", req, resp);
		}

		String title = req.getParameter("title");
		String text = req.getParameter("text");

		BlogEntryForm form = new BlogEntryForm(title, text);
		form.checkValidity();

		if (!AbstractForm.isFormValid(form)) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/createEntry.jsp").forward(req, resp);
			return;
		}

		Long userId = (Long) req.getSession().getAttribute("userId");

		if (userId == null) {
			WebUtil.sendError("User id is not defined!", req, resp);
		}

		BlogUser user = DAOProvider.getDao().getBlogUser(userId);

		BlogEntry entry = new BlogEntry();
		entry.setTitle(title);
		entry.setText(text);
		entry.setCreatedAt(new Date());
		entry.setAuthor(user);

		DAOProvider.getDao().addBlogEntry(entry);

		resp.sendRedirect("author/" + nick);
	}

}
