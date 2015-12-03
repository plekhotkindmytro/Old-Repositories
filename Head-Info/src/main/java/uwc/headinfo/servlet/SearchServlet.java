package uwc.headinfo.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uwc.headinfo.model.GithubUser;
import uwc.headinfo.service.GithubDataService;
import uwc.headinfo.service.impl.GithubDataServiceImpl;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GithubDataService githubDataService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
		githubDataService = new GithubDataServiceImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchTerm = request.getParameter("searchTerm");

		Object accessToken = request.getSession().getAttribute(
				"github_access_token");
		List<GithubUser> user = githubDataService.getUserInfoList(searchTerm,
				accessToken);
		request.setAttribute("githubUserInfo", user);

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/WEB-INF/headInfo.jsp");
		dispatcher.forward(request, response);
	}

}
