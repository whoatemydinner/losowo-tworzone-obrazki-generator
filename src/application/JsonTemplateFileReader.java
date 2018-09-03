package application;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

public class JsonTemplateFileReader implements JsonFileReader {

	@Override
	public JSONObject parseFileToJson(File file) throws IOException {
		FileReader reader = new FileReader(file);
		int currentChar;
		StringBuilder sb = new StringBuilder();
		while ((currentChar = reader.read()) != -1) {
			sb.append((char) currentChar);
		}
		reader.close();
		return new JSONObject(sb.toString());
	}

}
