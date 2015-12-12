package instacollage;

public class Collage {
	// base64 encoded
	private String image;

	public Collage() {
	}

	public Collage(String imageString) {
		this.image = imageString;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
