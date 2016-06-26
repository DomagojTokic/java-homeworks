package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.models.BlogUser;

/**
 * Servlet for preparing list of blog users on main page.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(urlPatterns = { "/servleti/main" })
public class MainServlet extends HttpServlet {

	/** Default serial version */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<BlogUser> authors = DAOProvider.getDao().getBlogUsers();
		req.setAttribute("authors", authors);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<BlogUser> authors = DAOProvider.getDao().getBlogUsers();
		req.setAttribute("authors", authors);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}

}
