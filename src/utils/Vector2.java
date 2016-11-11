package utils;

public class Vector2 {
	public float x, y;
	
	public Vector2() {
		
	}
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public int toInt() {
		return 0x00 | ((int)(y * 255) << 8) | ((int)(x * 255) << 16) | (0xFF << 24);
	}
}
