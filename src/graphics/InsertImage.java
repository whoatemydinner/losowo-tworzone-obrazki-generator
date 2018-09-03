package graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class InsertImage {
	private final File file;
	
	public InsertImage(File file) {
		this.file = file;
	}
	
	public BufferedImage getScaledBufferedImage(int destWidth, int destHeight) throws IOException {
		BufferedImage srcBufferedImage = this.getBufferedImage();
		BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = destBufferedImage.createGraphics();
		AffineTransform transform = AffineTransform.getScaleInstance(
				(double) (destWidth / srcBufferedImage.getWidth()), (double) (destHeight / srcBufferedImage.getHeight()));
		graphics.drawRenderedImage(srcBufferedImage, transform);
		return destBufferedImage;
	}
	
	public BufferedImage getBufferedImage() throws IOException {
		return ImageIO.read(file);
	}
}
