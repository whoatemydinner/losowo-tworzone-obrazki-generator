package graphics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class EmptySpace {
	public static final int WIDTH = 0;
	public static final int HEIGHT = 1;
	
	public static Pattern pattern = Pattern.compile("(?<width>[0-9]*)x(?<height>[0-9]*)");
	
	private final int x;
	private final int y;
	private final int xspan;
	private final int yspan;
	
	public EmptySpace(int x, int y, int xspan, int yspan) {
		this.x = x;
		this.y = y;
		this.xspan = xspan;
		this.yspan = yspan;
	}
	
	public EmptySpace(JSONObject object) throws IllegalArgumentException {
		try {
			String xyString = object.getString("topleftcorner");
			String bottomString = object.getString("bottomrightcorner");
			Matcher xyMatcher = pattern.matcher(xyString);
			int x = 0;
			int y = 0;
			if (xyMatcher.matches()) {
				this.x = Integer.parseInt(xyMatcher.group(1));
				this.y = Integer.parseInt(xyMatcher.group(2));
			} else {
				this.x = x;
				this.y = y;
			}
			
			Matcher bottomMatcher = pattern.matcher(bottomString);
			if (bottomMatcher.matches()) {
				int bottomx = Integer.parseInt(bottomMatcher.group(1));
				int bottomy = Integer.parseInt(bottomMatcher.group(2));
				this.xspan = bottomx - this.x;
				this.yspan = bottomy - this.y;
			} else {
				this.xspan = 1;
				this.yspan = 1;
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException("Invalid source JSON for template image.");
		}
	}
	
	public int[] getDimensions() {
		return new int[] {this.xspan, this.yspan};
	}
	
	public int[] getCoordinates() {
		return new int[] {this.x, this.y};
	}
	
	public boolean checkIfCoordinatesInside(int xcoor, int ycoor) {
		return xspan - xcoor >= x && yspan - ycoor >= y;
	}
}
