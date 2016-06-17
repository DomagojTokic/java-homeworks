package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Allows user to obtain a table of values of trigonometric functions sin(x)
 * and cos(x) for all integer angles (in degrees, not radians) in a range
 * determined by URL parameters a and b (if a is missing, a=0; if b is missing,
 * b=360; if a > b, they are swapped; if b > a+720, b is set to a+720).
 * 
 * @author Domagoj Tokić
 *
 */
public class TrigonometricServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer startDeg = getIntParameter("a", 0, req, resp);
		Integer endDeg = getIntParameter("b", 360, req, resp);

		if (startDeg > endDeg) {
			Integer temp = startDeg;
			startDeg = endDeg;
			endDeg = temp;
		}

		if (endDeg > startDeg + 720) {
			endDeg = startDeg + 720;
		}

		List<TrigResult> results = new ArrayList<>();

		while (startDeg <= endDeg) {
			results.add(new TrigResult(startDeg));
			startDeg++;
		}

		req.setAttribute("results", results);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometic.jsp").forward(req, resp);
	}

	/**
	 * Retrieves Integer parameter from request object.
	 * 
	 * @param param Parameter name
	 * @param def Default parameter value
	 * @param req Request object
	 * @param resp Response object
	 * @return Integer parameter
	 * @throws ServletException if the request could not be handled
	 * @throws IOException if an input or output error is detected when the
	 *             servlet handles request
	 */
	private Integer getIntParameter(String param, int def, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String paramString = (String) req.getParameter(param);

		if (param == null) {
			return def;
		} else {
			try {
				return Integer.parseInt(paramString);
			} catch (NumberFormatException e) {
				req.setAttribute("message",
						"Invalid parameter format - parameter should be whole number!");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			}
		}
		return null;
	}

}
