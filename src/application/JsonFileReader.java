package application;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

public interface JsonFileReader {
	public JSONObject parseFileToJson(File file) throws IOException;
}
