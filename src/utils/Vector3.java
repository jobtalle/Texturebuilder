package utils;

public class Vector3 {
	public float x, y, z;
	
	public Vector3() {
		
	}
	
	public Vector3(int integer) {
		fromInt(integer);
	}
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static float dot(Vector3 a, Vector3 b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}
	
	public void fromInt(int integer) {
		x = (((integer >> 16) & 0xFF) - 128) / 128.0f;
		y = (((integer >> 8) & 0xFF) - 128) / 128.0f;
		z = ((integer & 0xFF) - 128) / 128.0f;
	}
	
	public int toInt() {
		return (int)((z + 1.0f) * 127) | ((int)((y + 1.0f) * 127) << 8) | ((int)((x + 1.0f) * 127) << 16) | (0xFF << 24);
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public void normalize() {
		float length = length();
		
		x /= length;
		y /= length;
		z /= length;
	}
	
	public void multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
	}
	
	public void add(Vector3 vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
	}
}
