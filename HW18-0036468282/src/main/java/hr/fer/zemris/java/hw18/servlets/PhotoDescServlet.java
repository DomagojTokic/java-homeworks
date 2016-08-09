package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.PhotosDB;

/**
 * Servlet for retrieving photo description. Requires one parameter which is
 * file name of photo.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(urlPatterns = { "/servlets/photodesc" })
public class PhotoDescServlet extends HttpServlet {

	/** UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");

		String description = PhotosDB.getPhotoDesc(name, req, resp);

		resp.setContentType("text");
		resp.setCharacterEncoding("UTF-8");

		resp.getWriter().write(description);

		resp.getWriter().flush();
	}

}
