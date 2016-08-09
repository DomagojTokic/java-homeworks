package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.PhotosDB;

/**
 * Servlet for retrieving a list of tags for all photos.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(urlPatterns = { "/servlets/tags" })
public class TagsServlet extends HttpServlet {

	/** UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Set<String> tags = PhotosDB.getTags(req, resp);

		String[] array = new String[tags.size()];
		tags.toArray(array);

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		resp.getWriter().write(jsonText);

		resp.getWriter().flush();
	}

}
