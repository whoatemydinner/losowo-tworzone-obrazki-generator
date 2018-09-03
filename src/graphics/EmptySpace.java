package graphics;

import org.json.JSONException;
import org.json.JSONObject;

public class EmptySpace {
	public static final int WIDTH = 0;
	public static final int HEIGHT = 1;
	
	private final int x;
	private final int y;
	private final int xspan;
	private final int yspan;
	
	public EmptySpace(JSONObject object) throws IllegalArgumentException {
		try {
			this.x = object.getInt("x");
			this.y = object.getInt("y");
			this.xspan = object.getInt("xspan");
			this.yspan = object.getInt("yspan");
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
	
	/**
	 * 
	 * @return [0] większy wymiar, [1] rozmiar
	 */
	public int[] getLargerDimension() {
		if (xspan > yspan) {
			return new int[] {WIDTH, this.xspan};
		} else {
			return new int[] {HEIGHT, this.yspan};
		}
	}
}
