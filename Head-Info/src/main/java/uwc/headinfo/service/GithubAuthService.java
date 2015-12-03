package uwc.headinfo.service;

import javax.servlet.http.HttpServletRequest;

public interface GithubAuthService {
	public String buildRedirectUrl(HttpServletRequest request);

	public String getAuthUrl(HttpServletRequest request);

	public String validateAuthCode(HttpServletRequest request);

	public String getAccessTokenInfo(String code);

}
