package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirects index page to blog main page.
 * 
 * @author Domagoj
 *
 */
@WebServlet(urlPatterns = { "/index" })
public class IndexServlet extends HttpServlet {

	/** Default serial version */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nick = (String) req.getSession().getAttribute("nick");
		if (nick == null) {
			req.getSession().setAttribute("loggedIn", false);
		}
		resp.sendRedirect("servleti/main");
	}

}
