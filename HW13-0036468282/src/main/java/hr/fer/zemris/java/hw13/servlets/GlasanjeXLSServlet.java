package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Servlet for sending XML file with voting result to user.
 * 
 * @author Domagoj Tokić
 *
 */
public class GlasanjeXLSServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, Integer> results = GlasanjeUtil.getResultMap(req, resp);
		int i = 0;

		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("glasanje");
		for (Entry<String, Integer> entry : results.entrySet()) {
			Row row = sheet.createRow(i);
			Cell cell = row.createCell(0);
			cell.setCellValue(entry.getKey());
			cell = row.createCell(1);
			cell.setCellValue(entry.getValue());
			i++;
		}

		resp.setContentType("application/vnd.ms-excel");
		workbook.write(resp.getOutputStream());
	}

}
