package com.dmytroplekhotkin.crawler.web.service;

public enum SortBy {

	RELEVANCE("relevance"), RATING("rating"), NAME("name"), DISTANCE("distance");

	private SortBy(final String sort) {
		this.sort = sort;
	}

	private final String sort;

	@Override
	public String toString() {
		return sort;
	}
}
