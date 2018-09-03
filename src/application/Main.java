package application;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		if (args.length == 1) {
			try {
				System.out.println(new JsonTemplateFileReader().parseFileToJson(new File(args[0])));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
