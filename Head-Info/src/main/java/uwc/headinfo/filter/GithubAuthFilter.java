package uwc.headinfo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uwc.headinfo.service.GithubAuthService;
import uwc.headinfo.service.impl.GithubAuthServiceImpl;

/**
 * Servlet Filter implementation class AuthFilter
 */
public class GithubAuthFilter implements Filter {

	private GithubAuthService authService;

	/**
	 * Default constructor.
	 */
	public GithubAuthFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession();
		Object accessToken = session.getAttribute("github_access_token");

		if (accessToken == null) {
			String code = httpRequest.getParameter("code");
			if (code == null) {

				String authUrl = authService.getAuthUrl(httpRequest);
				httpResponse.sendRedirect(authUrl);
			} else {
				String accessTokenInfo = authService.getAccessTokenInfo(code);
				String[] params = accessTokenInfo.split("&");
				for (int i = 0; i < params.length; i++) {
					if (params[i].startsWith("access_token")) {
						String value = params[i].replace("access_token=", "");
						session.setAttribute("github_access_token", value);

					}
				}

				httpResponse.sendRedirect(httpRequest.getContextPath()
						+ "/index.jsp");
				// String redirectUrl =
				// authService.buildRedirectUrl(httpRequest);
				// httpResponse.sendRedirect(redirectUrl);
			}
		} else {
			chain.doFilter(httpRequest, httpResponse);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		authService = new GithubAuthServiceImpl();
	}

}
