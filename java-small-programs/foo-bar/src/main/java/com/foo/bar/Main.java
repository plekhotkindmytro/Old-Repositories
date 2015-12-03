package com.foo.bar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Main
 */
public class Main extends HttpServlet {

	private Properties props = new Properties();
	private static final String SERVER_ERROR = "500. Internal server error";

	/**
	 * Default constructor.
	 */
	public Main() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		InputStream in = getServletContext().getResourceAsStream(
				"WEB-INF/servicepoints.properties");
		if (in != null) {
			try {
				props.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getPathInfo();
		uri = uri.substring(1);

		Object realUrl = props.getProperty(uri);

		String result = "";
		if (realUrl != null) {

			URL url = null;
			try {
				url = new URL(realUrl.toString());
			} catch (MalformedURLException e) {
				e.getStackTrace();
				result = SERVER_ERROR;
			}
			if (url != null) {
				BufferedReader in = null;
				try {
					URLConnection conn = url.openConnection();
					in = new BufferedReader(new InputStreamReader(
							conn.getInputStream()));
					String decodedString;
					while ((decodedString = in.readLine()) != null) {
						result += decodedString;
					}
				} catch (IOException e) {
					e.getStackTrace();
					result = SERVER_ERROR;
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.getStackTrace();
							result = SERVER_ERROR;
						}
					}
				}
			}
		} else {
			result = SERVER_ERROR;
		}
		try {
			PrintWriter out = response.getWriter();
			out.print(result);
			out.close();
		} catch (IOException e) {
			result = SERVER_ERROR;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
