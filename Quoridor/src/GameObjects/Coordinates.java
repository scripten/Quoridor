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
	
	public static Coordinates clone(Coordinates toClone) {
		Coordinates res;
		res = new Coordinates(toClone.getRow(), toClone.getColumn());
		return res;
	}
	
	@Override
	public String toString() {
		return String.format("Row: %s Column: %s", row, column);
	}
	@Override
	public boolean equals(Object obj) {
		// Determine if the nodes are equal
		if (obj == this) 
	        return true;
	    else if (obj == null || obj.getClass() != this.getClass()) 
	        return false;
	    else {
	    	Coordinates coord = (Coordinates)obj;
	 
	    	if (coord.getRow() == row && coord.getColumn() == column)
	    		return true;
	    	else
	    		return false;
	    }
	}
}