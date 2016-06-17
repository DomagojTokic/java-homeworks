package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet for sending XML file with voting result to user.
 * 
 * @author Domagoj Tokić
 *
 */
@WebServlet(name="glasanje-xls", urlPatterns={"/servleti/glasanje-xls"})
public class GlasanjeXLSServlet extends HttpServlet {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	/** Output file name */
	private static final String FILE_NAME = "results.xls";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pollID = req.getParameter("pollID");
		List<PollOption> results = DAOProvider.getDao().getPollOptions(pollID, true);			
		
		int i = 0;
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("glasanje");
		for (PollOption option : results) {
			Row row = sheet.createRow(i);
			Cell cell = row.createCell(0);
			cell.setCellValue(option.getOptionTitle());
			cell = row.createCell(1);
			cell.setCellValue(option.getVotesCount());
			i++;
		}

		resp.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);
		resp.setContentType("application/vnd.ms-excel");
		workbook.write(resp.getOutputStream());
	}

}
