package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.PhotosDB;

/**
 * Servlet for retrieving photo thumbnail. Requires parameter name which is
 * photo file name.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(urlPatterns = { "/servlets/thumbnail" })
public class ThumbnailServlet extends HttpServlet {

	/** UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");

		byte[] thumbnail = PhotosDB.getThumbnail(name, req, resp);

		resp.setContentType("image/jpeg");
		resp.setContentLength(thumbnail.length);
		resp.getOutputStream().write(thumbnail);

		resp.getOutputStream().flush();
	}

}
