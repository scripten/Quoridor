package GameObjects;

public class Coordinates {
	private int x;
	private int y;
	
	public Coordinates() {
		x = -1;
		y = -1;
	}
	
	public Coordinates(int xCoordinate, int yCoordinate) {
		x = xCoordinate;
		y = yCoordinate;
	}
	
	public void setX(int coordinate) {
		x = coordinate;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int coordinate) {
		y = coordinate;
	}
	
	public int getY() {
		return y;
	}
}