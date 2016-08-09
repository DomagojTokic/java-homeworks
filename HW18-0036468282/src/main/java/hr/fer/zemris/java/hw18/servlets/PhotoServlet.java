package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.PhotosDB;

/**
 * Servlet for retrieving full sized photo. Requires parameter name which is
 * file name of photo.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(urlPatterns = { "/servlets/photo" })
public class PhotoServlet extends HttpServlet {

	/** UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");

		byte[] photo = PhotosDB.getPhoto(name, req, resp);

		resp.setContentType("image/jpeg");
		resp.setContentLength(photo.length);
		resp.getOutputStream().write(photo);

		resp.getOutputStream().flush();
	}

}
