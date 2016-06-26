package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.WebUtil;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.BlogCommentForm;
import hr.fer.zemris.java.hw15.models.BlogEntry;

/**
 * Servlet for users entry list page and for page showing blog entries.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(urlPatterns = { "/servleti/author/*" })
public class AuthorServlet extends HttpServlet {

	/** Default serial version */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] split = req.getRequestURI().split("/");

		if (split[split.length - 3].equals("author")) {
			String entryId = split[split.length - 1];
			String nick = split[split.length - 2];
			redirectToEntryContent(entryId, nick, req, resp);

		} else if (split[split.length - 2].equals("author")) {
			String nick = split[split.length - 1];
			redirectToEntryList(nick, req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] split = req.getRequestURI().split("/");

		String entryId = split[split.length - 1];
		String nick = split[split.length - 2];
		redirectToEntryContent(entryId, nick, req, resp);

		redirectToEntryContent(entryId, nick, req, resp);
	}

	/**
	 * Redirects servlet to page containing entries for chosen blog author.
	 * 
	 * @param nick Blog author nickname
	 * @param req HttpServletRequest object
	 * @param resp HttpServletResponse object
	 */
	private void redirectToEntryList(String nick, HttpServletRequest req,
			HttpServletResponse resp) {
		List<BlogEntry> entries = DAOProvider.getDao().getBlogEntries(nick);
		req.setAttribute("entries", entries);
		setEditable(nick, req);
		try {
			req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Redirects servlet to page showing blog entry content.
	 * 
	 * @param entryId Blog entry id.
	 * @param nick Blog author nickname
	 * @param req HttpServletRequest object
	 * @param resp HttpServletResponse object
	 */
	private void redirectToEntryContent(String entryId, String nick, HttpServletRequest req,
			HttpServletResponse resp) {
		try {
			Long id = Long.parseLong(entryId);
			BlogEntry entry = DAOProvider.getDao().getBlogEntry(id);

			if (entry == null || !entry.getAuthor().getNick().equals(nick)) {
				WebUtil.sendError("Requested entry does not exist!", req, resp);
				return;
			}

			req.setAttribute("entry", entry);
			req.setAttribute("comments", entry.getComments());

			if (req.getAttribute("commentForm") == null) {
				req.setAttribute("commentForm", BlogCommentForm.EMPTY);
			}

			setEditable(nick, req);
			req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);

		} catch (NumberFormatException e) {
			WebUtil.sendError("Invalid entry id format!", req, resp);
		} catch (DAOException e1) {
			WebUtil.sendError("Given entry id does not exist!", req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if current client has permission to edit blog content.
	 * 
	 * @param nick Client nickname.
	 * @param req HttpServletRequest object
	 */
	private void setEditable(String nick, HttpServletRequest req) {
		String user = (String) req.getSession().getAttribute("nick");
		if (user != null && user.equals(nick)) {
			req.setAttribute("editable", true);
		} else {
			req.setAttribute("editable", false);
		}
	}

}
