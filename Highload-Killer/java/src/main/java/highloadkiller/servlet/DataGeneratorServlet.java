package highloadkiller.servlet;

import highloadkiller.service.DataGeneratorService;
import highloadkiller.service.DataGeneratorServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MultipartConfig
public class DataGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataGeneratorServlet.class);

	private DataGeneratorService dataGeneratorService;

	public DataGeneratorServlet() {
		super();
		dataGeneratorService = new DataGeneratorServiceImpl();
	}

	/**
	 * @throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter responseWriter = null;
		try {
			responseWriter = response.getWriter();

			Part filePart = request.getPart("file");
			String scriptName = saveFile(filePart);
			final String dbName = UUID.randomUUID().toString().replace("-", "");
			
			dataGeneratorService.generateDBForTesting(dbName, scriptName);

			HttpSession session = request.getSession();
			if (session.getAttribute("dbName") == null) {
				session.setAttribute("dbName", dbName);
			}

			responseWriter.write("Database for testing was created");
		} catch (SQLException e) {
			LOGGER.error("Error while uploading a database dump.", e);
			if (responseWriter != null) {
				responseWriter.write("Error while uploading the file");
			}
		} catch (IOException e) {
			LOGGER.error("Error while uploading a database dump.", e);
			if (responseWriter != null) {
				responseWriter.write("Error while uploading the file");
			}
		} catch (URISyntaxException e) {
			LOGGER.error("Error while uploading a database dump.", e);
			if (responseWriter != null) {
				responseWriter.write("Error while uploading the file");
			}
		} finally {
			if (responseWriter != null) {
				responseWriter.close();
			}
		}
	}

	private String saveFile(final Part filePart) throws IOException,
			URISyntaxException {

		final String fileName = getFileName(filePart);

		OutputStream out = null;
		InputStream filecontent = null;

		try {
			File file = new File(fileName);
			LOGGER.debug(file.getAbsolutePath());
			out = new FileOutputStream(file);
			filecontent = filePart.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			LOGGER.info("File {} being uploaded.", fileName);
		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
		}

		return fileName;
	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		LOGGER.info("Part Header = {}", partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

}
