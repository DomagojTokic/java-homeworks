package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Generates random color used on funny.jsp page as font color.
 * 
 * @author Domagoj Tokić
 *
 */
public class FunnyStoryServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Random rand = new Random();
		String r = Integer.toHexString(rand.nextInt(255));
		String g = Integer.toHexString(rand.nextInt(255));
		String b = Integer.toHexString(rand.nextInt(255));
		req.setAttribute("fontColor", "#" + r + g + b);
		req.getRequestDispatcher("WEB-INF/stories/funny.jsp").forward(req, resp);
	}

}
