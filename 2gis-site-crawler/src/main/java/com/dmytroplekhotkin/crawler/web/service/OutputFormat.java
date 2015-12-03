package com.dmytroplekhotkin.crawler.web.service;

public enum OutputFormat {

	JSON("json"), XML("xml"), JSONP("jsonp");

	private OutputFormat(final String format) {
		this.format = format;
	}

	private final String format;

	@Override
	public String toString() {
		return format;
	}
}
