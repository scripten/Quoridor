package GameObjects;

public class Coordinates {
	private int row;
	private int column;
	
	public Coordinates() {
		row = -1;
		column = -1;
	}
	
	public Coordinates(int rowCoordinate, int columnCoordinate) {
		row = rowCoordinate;
		column = columnCoordinate;
	}
	
	public void setRow(int coordinate) {
		row = coordinate;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setColumn(int coordinate) {
		column = coordinate;
	}
	
	public int getColumn() {
		return column;
	}
}