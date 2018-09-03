package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TemplateImage {
	private final File file;
	private final List<EmptySpace> emptySpaces;
	
	public TemplateImage(JSONObject object) throws IllegalArgumentException {
		try {
			this.file = new File(object.getString("file"));
			JSONArray emptySpacesArray = object.getJSONArray("spaces");
			emptySpaces = new ArrayList<EmptySpace>();
			for (Object entry : emptySpacesArray) {
				emptySpaces.add(new EmptySpace((JSONObject) entry));
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException("Invalid source JSON for template image.");
		}
		
		if (this.emptySpaces.size() == 0) {
			throw new IllegalArgumentException("Invalid source JSON for template image.");
		}
	}
	
	public int getNumberOfEmptySpaces() {
		return this.emptySpaces.size();
	}
	
	public BufferedImage insertInsertImages(List<InsertImage> insertImages) throws IOException, IllegalArgumentException {
		if (insertImages.size() != this.emptySpaces.size()) {
			throw new IllegalArgumentException("Wrong number of images to insert.");
		}
		
		BufferedImage baseImage = getBufferedImage();
		Graphics graphics = baseImage.getGraphics();
		
		for (int index = 0; index < this.emptySpaces.size(); index++) {
			EmptySpace space = this.emptySpaces.get(index);
			BufferedImage imageToInsert = insertImages.get(index).getScaledBufferedImage(
					space.getDimensions()[0], space.getDimensions()[1]);
			graphics.drawImage(imageToInsert, space.getCoordinates()[0], space.getCoordinates()[1], null);
		}
		
		graphics.drawImage(getBufferedImage(), 0, 0, null);
		
		return baseImage;
	}
	
	public BufferedImage getBufferedImage() throws IOException {
		return ImageIO.read(file);
	}
}
