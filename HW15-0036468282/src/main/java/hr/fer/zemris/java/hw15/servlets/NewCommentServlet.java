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
import hr.fer.zemris.java.hw15.forms.BlogCommentForm;
import hr.fer.zemris.java.hw15.models.BlogComment;
import hr.fer.zemris.java.hw15.models.BlogEntry;

/**
 * Servlet for adding new comments to blog entry.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(urlPatterns = { "/servleti/new_comment" })
public class NewCommentServlet extends HttpServlet {

	/** Default serial version */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("email");
		String text = req.getParameter("commentText");
		String entryId = req.getParameter("id");

		if(entryId == null) {
			WebUtil.sendError("Entry id is not defined!", req, resp);
		}
		
		BlogEntry entry = DAOProvider.getDao().getBlogEntry(Long.parseLong(entryId));
		
		if(entry == null) {
			WebUtil.sendError("Requested entry does not exist!", req, resp);
			return;
		}

		BlogCommentForm commentForm = new BlogCommentForm(email, text);
		commentForm.checkValidity();
		if (!AbstractForm.isFormValid(commentForm)) {
			req.setAttribute("commentForm", commentForm);
			req.getRequestDispatcher(
					"/servleti/author/" + entry.getAuthor().getNick() + "/" + entryId)
					.forward(req, resp);
			return;
		}

		BlogComment comment = new BlogComment();
		comment.setPostedOn(new Date());
		comment.setUsersEMail(email);
		comment.setBlogEntry(entry);
		comment.setMessage(text);

		DAOProvider.getDao().addBlogComment(comment);

		resp.sendRedirect(req.getServletContext().getContextPath()
				+ "/servleti/author/" + entry.getAuthor().getNick() + "/" + entryId);
	}

}
