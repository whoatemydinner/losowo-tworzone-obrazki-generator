package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.Main;

public class TemplateImage {
	private final File file;
	private final List<EmptySpace> emptySpaces;
	
	public TemplateImage(JSONObject object) throws IllegalArgumentException {
		try {
			this.file = new File(Main.templatesFolder + object.getString("name"));
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
	
	public String getName() {
		return this.file.getAbsolutePath();
	}
	
	public BufferedImage insertInsertImages(List<InsertImage> insertImages) throws IOException, IllegalArgumentException {
		if (insertImages.size() != this.emptySpaces.size()) {
			throw new IllegalArgumentException("Wrong number of images to insert.");
		}
		
		BufferedImage baseImage = getBufferedImage();
		Graphics graphics = baseImage.getGraphics();
		
		for (int index = 0; index < this.emptySpaces.size(); index++) {
			EmptySpace space = this.emptySpaces.get(index);
			System.out.println("space: " + space.getDimensions()[0] + "x" + space.getDimensions()[1]);
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
	
	public static void save(BufferedImage image, String filename) {
		if (filename == null) {
			Random randGen = new Random();
			long randLong = randGen.nextLong();
			filename = String.valueOf(Math.abs(randLong));
		}
	    try {
	            if (ImageIO.write(image, "png", new File(Main.outputFolder + filename + ".png")))
	            {
	                System.out.println("Succeeded in saving " + filename + ".png!");
	            }
	    } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	    }
	}
}
