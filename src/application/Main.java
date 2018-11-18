package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import graphics.InsertImage;
import graphics.TemplateImage;

public class Main {
	
	private static String templatesFile = "templates.json";
	private static int numberOfFilesToGenerate = 10;
	
	public static String templatesFolder;
	public static String imagesFolder;
	public static String outputFolder;

	public static void main(String[] args) {
		String configFile = "config.json";
		List<TemplateImage> templateImages = new ArrayList<>();
		List<File> images = new ArrayList<>(Arrays.asList(new File(imagesFolder).listFiles()));
		List<InsertImage> insertImages = new ArrayList<>();
		try {
			FileToJsonReader reader = new FileToJsonReader();
			configFile = (args.length > 0) ? args[0] : configFile;
			JSONObject config = reader.parseFileToJson(new File(configFile));
			templatesFolder = config.getString("templatesFolder");
			imagesFolder = config.getString("imagesFolder");
			outputFolder = config.getString("outputFolder");
			numberOfFilesToGenerate = config.getInt("numberOfFilesToGenerate");
			templatesFile = templatesFolder + config.getString("nameOfTemplatesFile");
			JSONObject templates = reader.parseFileToJson(new File(templatesFile));
			JSONArray templateArray = templates.getJSONArray("templates");
			for (int i = 0; i < templateArray.length(); i++) {
				JSONObject template = templateArray.getJSONObject(i);
				System.out.println(template);
				TemplateImage templateImage = new TemplateImage(template);
				templateImages.add(templateImage);
			}
			for (File file : images) {
				insertImages.add(new InsertImage(file));
			}
			for (TemplateImage ti : templateImages) {
				System.out.println(ti.getName());
				System.out.println(ti.getNumberOfEmptySpaces());
			}
			for (InsertImage ii : insertImages) {
				System.out.println(ii.getName());
			}
			Random randomGen = new Random();
			for (int i = 0; i < numberOfFilesToGenerate; i++) {
				TemplateImage ti = templateImages.get(randomGen.nextInt(templateImages.size()));
				List<InsertImage> hmm = new ArrayList<>();
				for (int j = 0; j < ti.getNumberOfEmptySpaces(); j++) {
					InsertImage image = insertImages.get(randomGen.nextInt(insertImages.size()));
					hmm.add(image);
				}
				BufferedImage export = ti.insertInsertImages(hmm);
				TemplateImage.save(export, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
