package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Creates XML (MS Excel) file with n pages and a-b rows. On page i there is a
 * table with two columns. The first column contains integer numbers from a to
 * b. The second column contains i-th powers of these numbers. Format of request
 * is /powers?a=1&b=1&n=1. Scope of a and b is [-100, 100]. Scope of n is [1,
 * 5].
 * 
 * @author Domagoj Tokić
 *
 */
public class PowersServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer start = null;
		Integer end = null;
		Integer pages = null;
		try {
			start = Integer.parseInt((String) req.getParameter("a"));
			end = Integer.parseInt((String) req.getParameter("b"));
			pages = Integer.parseInt((String) req.getParameter("n"));
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Invalid parameters format - parameters should be whole numbers!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}

		if (start < -100 || start > 100 || end < -100 || end > 100 || pages < 1 || pages > 5
				|| start > end) {
			req.setAttribute("message", "Invalid parameters for XML file!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);			
		}

		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		for (int i = 1; i <= pages; i++) {
			HSSFSheet sheet = workbook.createSheet(Integer.toString(i));
			for (int j = start; j <= end; j++) {
				Row row = sheet.createRow(j - start);
				Cell cell = row.createCell(0);
				cell.setCellValue(j);
				cell = row.createCell(1);
				cell.setCellValue(Math.pow(j, i));
			}
		}

		resp.setContentType("application/vnd.ms-excel");
		workbook.write(resp.getOutputStream());

	}
}
