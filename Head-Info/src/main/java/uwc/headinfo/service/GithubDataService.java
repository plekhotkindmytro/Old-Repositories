package uwc.headinfo.service;

import java.util.List;

import uwc.headinfo.model.GithubUser;

public interface GithubDataService {

	public List<GithubUser> getUserInfoList(String searchTerm,
			Object accessToken);

}
