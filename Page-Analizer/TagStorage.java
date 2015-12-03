import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

import java.net.*;

class TagStorage {
	private List<Tag> tagList;

	public List<Tag> getTagList() {
		if (tagList == null) {
			loadTagList();
		} 
		return tagList;
	}

	private void loadTagList() {
		
	}

	public void setTagList(List<Tag> tagList) {
		// validate at first
		// validate access
		this.tagList = tagList;
		
		saveTagList();
	}

	public void addTagList(List<Tag> tagList) {
		addTagList();
		saveTagList();
	}

	public int getOverallTagNumber() {
		int overallTagNumber = 0;
		for (Tag tag : tagList) {
			overallTagNumber += tag.getCount();
		}
		return overallTagNumber;
	}



}
