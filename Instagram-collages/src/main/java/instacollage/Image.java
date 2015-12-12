package instacollage;

public class Image {

	private String id;
	private String thumbnailUrl;
	private String lowUrl;
	private String standardUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getLowUrl() {
		return lowUrl;
	}

	public void setLowUrl(String lowUrl) {
		this.lowUrl = lowUrl;
	}

	public String getStandardUrl() {
		return standardUrl;
	}

	public void setStandardUrl(String standardUrl) {
		this.standardUrl = standardUrl;
	}

}
