import java.awt.Image;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import java.awt.Graphics2D;
import java.util.UUID;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.RenderingHints;

/**
* @author dmytro.plekhotkin
*/
public class ImageGenerator {


	private static int HEX_COLOR_LENGTH = 6;
	private static int IMAGE_SIZE_X = 420;
	private static int IMAGE_SIZE_Y = 420;
	
	private UUID randomUUID;
	private Dimension imgDim;
	private Color[] colors;
	
	public ImageGenerator(int width, int height) {
		imgDim = new Dimension(width, height);
		
	}
	
	/**
	 * args[0] = number of random images;
	 * args[1] = width;
	 * args[2] = height;
	 */
	public static void main(final String [] args) throws IOException {
		int wigth = args[1] ? args[1]:IMAGE_SIZE_X;
		int height = args[2] ? args[2]:IMAGE_SIZE_Y;
		
		ImageGenerator imgGenerator = new ImageGenerator(wigth, height);
		
		for (int i = 0; i < args[0]; i++) {
			imgGenerator.randomImage();
		}
		
	}
	 
	private BufferedImage randomImage() throws IOException {
		BufferedImage image = new BufferedImage(IMAGE_SIZE_X, IMAGE_SIZE_Y, BufferedImage.TYPE_INT_RGB);

		randomUUID = UUID.randomUUID();

		String clearUUIDStr = randomUUID.toString().replace("-", "");
		int colorsNumber = clearUUIDStr.length() / HEX_COLOR_LENGTH;
		colors = new Color[colorsNumber];
		String hexColor;
		for (int i = 0; i < colorsNumber; i++) {
		
			hexColor = clearUUIDStr.substring(i*HEX_COLOR_LENGTH,(i+1)*HEX_COLOR_LENGTH).toUpperCase();
			colors[i] = Color.decode("#"+hexColor);
		}
		
		Graphics2D g2d = image.createGraphics();
		
		g2d.setBackground(Color.WHITE);
	    g2d.fillRect(0, 0, IMAGE_SIZE_X, IMAGE_SIZE_Y);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw 
		drawSnowflake(g2d, IMAGE_SIZE_X/2, IMAGE_SIZE_Y/2, 0, 6, 100.0);
		
		
		{
		/*	Color colorReplace = Color.decode("#FF0000");
			short[] red = new short[256];
			short[] green = new short[256];
			short[] blue = new short[256];
			for (short i = 0; i < 256; i++) {
			    red[i] = green[i] = blue[i] = (short)i;
			}

			red[0] = (short) colorReplace.getRed();
			green[0] = (short) colorReplace.getGreen();
			blue[0] = (short) colorReplace.getBlue();

			short[][] data = new short[][] {
		   		red, green, blue
			};

			ShortLookupTable blut = new ShortLookupTable(0, data);
			LookupOp lop = new LookupOp(blut, null);
			image = lop.filter(image, null);
		*/
		}

		File outputfile = new File(clearUUIDStr + ".png");
	    ImageIO.write(image, "png", outputfile);
			return image;
	}

	private void drawSnowflake(Graphics2D g2d, int x1, int y1, int angle, int branchNumber, double radius) {
        g2d.setColor(Color.BLACK);
        BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        
        g2d.setStroke(bs);

		String clearUUIDStr = randomUUID.toString().replace("-", "");

		int angleStep = (int) (360 / branchNumber);
		for (int i = 0; i < branchNumber; i++, angle+=angleStep) {
			
			// TODO: random polygon 3
	        // fill polygon
	        {
	        	// TODO: random depth 2
		        int [] xPoints = new int [3];
		        xPoints[0] = x1;
		        xPoints[1] = x1 + (int) (Math.cos(Math.toRadians(angle+angleStep)) * radius);
		        xPoints[2] = x1 + (int) (Math.cos(Math.toRadians(angle)) * radius);

		        int [] yPoints = new int [3];
		        yPoints[0] = y1;
		        yPoints[1] = y1 +(int) (Math.sin(Math.toRadians(angle+angleStep)) * radius);
		        yPoints[2] = y1 +(int) (Math.sin(Math.toRadians(angle)) * radius);
		 
		 		// TODO: random color 1
		 		g2d.setColor(colors[0]);
		        g2d.fill(new Polygon(xPoints, yPoints , 3));
			}

			// TODO: random polygon 4
	        // fill polygons
	        {
		        int [] xPoints = new int [3];
		        xPoints[0] = x1;
		        xPoints[1] = x1 + (int) (Math.cos(Math.toRadians(angle+angleStep)) * radius /2);
		        xPoints[2] = x1 + (int) (Math.cos(Math.toRadians(angle)) * radius /2);

		        int [] yPoints = new int [3];
		        yPoints[0] = y1;
		        yPoints[1] = y1 +(int) (Math.sin(Math.toRadians(angle+angleStep)) * radius/2);
		        yPoints[2] = y1 +(int) (Math.sin(Math.toRadians(angle)) * radius/2);
		 
		 		// TODO: random color 5
		 		g2d.setColor(colors[1]);
		        g2d.fill(new Polygon(xPoints, yPoints , 3));
			}
			
			// draw backbone
			{
				boolean hasOffset = true;
				double offset = 0;
				if (hasOffset) {
						// TODO: random ?
						offset = 2;
						
				}
				// draw base line
				{
					int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * radius);
					int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * radius);
					g2d.setColor(Color.decode("#000000"));
					g2d.drawLine(x1, y1, x2, y2);
				}
				
				// draw line level 2
				{
					int x3 = x1 + (int) (Math.cos(Math.toRadians(angle)) * (radius + offset));
					int y3 = y1 + (int) (Math.sin(Math.toRadians(angle)) * (radius + offset));
					int x4 = x1 + (int) (Math.cos(Math.toRadians(angle+angleStep)) * (radius + offset));
					int y4 = y1 + (int) (Math.sin(Math.toRadians(angle+angleStep)) * (radius + offset));
					g2d.drawLine(x3, y3, x4, y4);
				}
				
				// draw line level 1
	        	{
					int x5 = x1 + (int) (Math.cos(Math.toRadians(angle)) * (radius/2 + offset));
					int y5 = y1 + (int) (Math.sin(Math.toRadians(angle)) * (radius/2 + offset));
					int x6 = x1 + (int) (Math.cos(Math.toRadians(angle+angleStep)) * (radius/2 + offset));
					int y6 = y1 + (int) (Math.sin(Math.toRadians(angle+angleStep)) * (radius/2 + offset));
					g2d.drawLine(x5, y5, x6, y6);
				}
			}
    	}
	}
}
