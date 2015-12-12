package instacollage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class CollageService {

	public BufferedImage create(List<String> imageUrls, String type) {
		try {
			List<BufferedImage> imageList = new ArrayList<BufferedImage>();
			for (String urlString : imageUrls) {
				imageList.add(ImageIO.read(new URL(urlString)));
			}
			if (type.equals(CollageType.horisontal.toString())) {
				return horisontal(imageList);
			} else if (type.equals(CollageType.vertical.toString())) {
				return vertical(imageList);
			}

			return horisontal(imageList);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Cannot create a collage", e);
		}
	}

	private static BufferedImage horisontal(List<BufferedImage> imageList) {

		// do some calculate first
		int offset = 10;
		int wid = offset/2;
		int maxHeight = 0;
		for (BufferedImage image : imageList) {
			wid += image.getWidth() + offset/2;
			int imageHeight = image.getHeight();
			if (imageHeight > maxHeight) {
				maxHeight = imageHeight;
			}
		}
		maxHeight += offset;

		// create a new buffer and draw two image into the new image
		BufferedImage newImage = new BufferedImage(wid, maxHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		// fill background
		g2.setPaint(Color.DARK_GRAY);
		g2.fillRect(0, 0, wid, maxHeight);
		// draw image
		g2.setColor(oldColor);

		int horisontalPosition = offset / 2;
		for (BufferedImage image : imageList) {
			g2.drawImage(image, null, horisontalPosition, offset / 2);
			horisontalPosition += image.getWidth() + offset/2;
		}
		g2.dispose();
		return newImage;
	}

	private static BufferedImage vertical(List<BufferedImage> imageList) {

		// do some calculate first
		int offset = 10;
		int height = offset/2;
		int maxWidth = 0;
		for (BufferedImage image : imageList) {
			height += image.getHeight() + offset/2;
			int imageWidth = image.getWidth();
			if (imageWidth > maxWidth) {
				maxWidth = imageWidth;
			}
		}
		maxWidth += offset;

		// create a new buffer and draw two image into the new image
		BufferedImage newImage = new BufferedImage(maxWidth, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		// fill background
		g2.setPaint(Color.DARK_GRAY);
		g2.fillRect(0, 0, maxWidth, height);
		// draw image
		g2.setColor(oldColor);

		int verticalPosition = offset / 2;
		for (BufferedImage image : imageList) {
			g2.drawImage(image, null, offset / 2, verticalPosition);
			verticalPosition += image.getHeight() + offset/2;
		}
		g2.dispose();
		return newImage;
	}

}
