package instacollage;

import java.util.List;

public class Page<T> {
	private String nextUrl;

	private List<T> elements;

	public Page() {
	}

	public Page(List<T> elements) {
		this.elements = elements;
	}

	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}

	public String getNextUrl() {
		return nextUrl;
	}

	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}
}
