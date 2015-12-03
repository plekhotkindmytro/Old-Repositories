package com.trashcssview.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;

public class Stylesheet {
	private String urlString;
	private List<String> selectors = new ArrayList<String>();
	private Set<Document> usedDocuments = new HashSet<Document>();;

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public List<String> getSelectors() {
		return selectors;
	}

	public void setSelectors(List<String> selectors) {
		this.selectors = selectors;
	}

	public Set<Document> getUsedDocuments() {
		return usedDocuments;
	}

	public void setUsedDocuments(Set<Document> usedDocuments) {
		this.usedDocuments = usedDocuments;
	}

	public void addUsedDocument(Document document) {
		usedDocuments.add(document);
	}
}
