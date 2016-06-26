package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.crypto.Crypto;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.models.BlogUser;

/**
 * Servlet for handling user login requests.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(urlPatterns = { "/servleti/login" })
public class LoginServlet extends HttpServlet {

	/** Default serial version */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String passwordHash = Crypto.encrypt(req.getParameter("password"));
		BlogUser user = DAOProvider.getDao().getBlogUser(username, passwordHash);
		if (user == null) {
			req.setAttribute("error", "Username or password are invalid!");
			req.getRequestDispatcher("main").forward(req, resp);
		} else {
			req.getSession().setAttribute("userId", user.getId());
			req.getSession().setAttribute("fn", user.getFirstName());
			req.getSession().setAttribute("ln", user.getLastName());
			req.getSession().setAttribute("nick", user.getNick());
			req.getSession().setAttribute("loggedIn", true);
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/author/" + user.getNick());
		}
	}

}
