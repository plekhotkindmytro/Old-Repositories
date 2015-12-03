package highloadkiller.servlet;

import highloadkiller.service.MeasureService;
import highloadkiller.service.MeasureServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeasureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MeasureServlet.class);
	
	private MeasureService measureService;

	public MeasureServlet() {
		super();
		measureService = new MeasureServiceImpl();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object dbName = session.getAttribute("dbName");

		String sqlQuery = request.getParameter("sql");
		String iterationString = request.getParameter("iterations");
		
		if (dbName != null && sqlQuery != null && !sqlQuery.isEmpty()
				&& iterationString != null && !iterationString.isEmpty()) {
			int iterations = Integer.parseInt(iterationString);
			PrintWriter responseWriter = null;

			try {
				responseWriter = response.getWriter();
				long time = measureService.measureTime(sqlQuery,
						dbName.toString(), iterations);

				String timeString = String.format(
						"%d min, %d sec",
						TimeUnit.MILLISECONDS.toMinutes(time),
						TimeUnit.MILLISECONDS.toSeconds(time)
								- TimeUnit.MINUTES
										.toSeconds(TimeUnit.MILLISECONDS
												.toMinutes(time)));
				responseWriter.write(timeString);
			} catch (SQLException e) {
				LOGGER.error("SQL query is wrong", e);
				if (responseWriter != null) {
					responseWriter.write("Error while measuring the query");
				}
			} finally {
				if (responseWriter != null) {
					responseWriter.close();
				}
			}
		}
	}
}
