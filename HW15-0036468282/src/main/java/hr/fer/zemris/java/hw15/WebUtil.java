package hr.fer.zemris.java.hw15;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web utility class for storing method used in multiple classes.
 * 
 * @author Domagoj Tokić
 *
 */
public class WebUtil {

	/**
	 * Redirects user to error page.
	 * 
	 * @param message Error message
	 * @param req HttpServletRequest object
	 * @param resp HttpServletResponse object
	 */
	public static void sendError(String message, HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("message", message);
		try {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if provided string is valid email.
	 * 
	 * @param email Sting which is checked.
	 * @return True if sting is valid email.
	 */
	public static boolean isValidEmail(String email) {
		if (email == null) {
			return false;
		}
		return email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
	}

}
