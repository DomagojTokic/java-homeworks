package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.crypto.Crypto;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.AbstractForm;
import hr.fer.zemris.java.hw15.forms.BlogUserForm;
import hr.fer.zemris.java.hw15.models.BlogUser;

/**
 * Servlet for handling user registration.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(urlPatterns = { "/servleti/register" })
public class RegisterServlet extends HttpServlet {
	
	/** Default serial version */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("form", BlogUserForm.EMPTY);
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String nick = req.getParameter("nick");
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		BlogUserForm form = new BlogUserForm(firstName, lastName, nick, email, password);
		form.checkValidity();

		if (!AbstractForm.isFormValid(form)) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}

		boolean available = DAOProvider.getDao().checkNickAvailability(nick);
		if (!available) {
			form.getValidity().put("nick", "User with the same Nickname already exists");
			if (!AbstractForm.isFormValid(form)) {
				req.setAttribute("form", form);
				req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
				return;
			}
		}

		BlogUser newUser = new BlogUser();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setNick(nick);
		newUser.setEmail(email);
		newUser.setPasswordHash(Crypto.encrypt(password));

		DAOProvider.getDao().addBlogUser(newUser);
		resp.sendRedirect(req.getServletContext().getContextPath()
				+ "/servleti/main");
	}
}
