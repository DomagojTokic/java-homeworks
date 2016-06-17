package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sets session "pickedBgCol" attribute stored in last requests "pickedBgCol"
 * parameter. On every JSP page this attribute will be use as background color.
 * 
 * @author Domagoj Tokić
 *
 */
public class SetColorServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String color = (String) req.getParameter("pickedBgCol");
		req.getSession().setAttribute("pickedBgCol", color);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}
