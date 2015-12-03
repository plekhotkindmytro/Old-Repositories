class Page {
	private List<Tag> tagList;
	private URL url;
	public Page(URL pageUrl) {
		this.url = pageUrl;
	}
	
	public void analize() {
		
	}
	
	public List<Tag> getTagList() {
		return tagList;
	}
	public int getAllTagsNumber() {
		int allTagsNumber = 0;
		for (Tag tag : tagList) {
			allTagsNumber += tag.getCount();
		}
		return allTagsNumber;	
	}
}
