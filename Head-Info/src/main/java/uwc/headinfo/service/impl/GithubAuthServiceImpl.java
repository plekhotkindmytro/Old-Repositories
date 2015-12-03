package uwc.headinfo.service.impl;

import javax.servlet.http.HttpServletRequest;

import uwc.headinfo.service.GithubAuthService;
import uwc.headinfo.util.ApiConnector;
import uwc.headinfo.util.GithubProperties;

public class GithubAuthServiceImpl implements GithubAuthService {
	public String getAuthUrl(HttpServletRequest request) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(GithubProperties.getAuthUrl());
		urlBuilder.append("?client_id=");
		urlBuilder.append(GithubProperties.getClientId());
		urlBuilder.append("&redirect_uri=");

		String redirectUrl = buildRedirectUrl(request);
		urlBuilder.append(redirectUrl);
		return urlBuilder.toString();
	}

	public String validateAuthCode(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAccessTokenInfo(String code) {
		StringBuilder paramsBuilder = new StringBuilder();

		paramsBuilder.append("client_id=").append(
				GithubProperties.getClientId());
		paramsBuilder.append("&client_secret=").append(
				GithubProperties.getClientSecret());
		paramsBuilder.append("&code=").append(code);

		String accessTokenInfo = ApiConnector.post(
				GithubProperties.getAccessTokenUrl(), paramsBuilder.toString());

		return accessTokenInfo;
	}

	public String buildRedirectUrl(final HttpServletRequest httpRequest) {
		final StringBuffer redirectUri = httpRequest.getRequestURL();
		final String queryString = buildQueryString(httpRequest);
		if (!queryString.isEmpty()) {
			redirectUri.append("?");
			redirectUri.append(queryString);
		}
		return redirectUri.toString();
	}

	private static String buildQueryString(HttpServletRequest httpRequest) {
		final String queryString = httpRequest.getQueryString();
		final StringBuilder queryBuilder = new StringBuilder();
		if (queryString != null) {
			final String[] params = queryString.split("&");
			for (String param : params) {
				if (!isAuthParam(param)) {
					if (queryBuilder.length() > 0) {
						queryBuilder.append("&");
					}
					queryBuilder.append(param);
				}
			}
		}
		return queryBuilder.toString();
	}

	private static boolean isAuthParam(String param) {
		return param.startsWith("code");
	}

}
