package GameObjects;

/* This Board object runs the back-end elements of the Quoridor game.
// So far the initial construction of the board is completed, as
// is a skeletal system for initial placement and movement of pawns
*/

public class Board {

	public enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}

	// Fields

	private final static int NUM_ROWS = 9;
	private final static int NUM_COLS = 9;
	
	private Tile[][] board = new Tile[NUM_ROWS][NUM_COLS];											 
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Constructor

	// Board is constructed utilizing a single boolean parameter which designates how many columns
	// are to be initialized and where the column will start. Right now, no work has been done on
	// distinguishing between human and computer columns, but the functionality for moving pawns
	// is entirely blind and thus allows for either a player-driven GUI or an AI to drive it.
	public Board () {
		// Initialize the rows of the board grid using rows
		for(int row = 0; row < NUM_ROWS; row++) { 
			for(int column = 0; column < NUM_COLS; column++) {
				board[row][column] = new Tile();	
			}
		}
	}

	// Methods
	
	// Returns a tile at a given row and column value. If the given coordinates are outside of the grid
	// bounds, nothing happens beside a short print message and a null Tile being returned (This
	// may throw an exception in the future)
	public Tile getTile(int row, int column) {
		try {
			return board[row][column];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("That coordinate is outside the grid.");
			throw e;
		}
	}
	
	public void switchOccupied(int row, int column) {
		board[row][column].setOccupied();
	}
	
	public boolean isOccupied(int row, int column) {
		return board[row][column].isOccupied();
	}
	
	public boolean isValidMove(Coordinates currentCoordinates, Coordinates newCoordinates) {
		int currow; 
		int curcolumn; 
		int newrow;
		int newcolumn;
		
		currow = currentCoordinates.getRow();
		curcolumn = currentCoordinates.getColumn();
		
		newrow = newCoordinates.getRow();
		newcolumn = newCoordinates.getColumn();
		if (currow != newrow && curcolumn != newcolumn) {
			// diagonal
			return false;
		} else if (currow==newrow && curcolumn==newcolumn){
			return false;
		}else if (currow < newrow) {
			// north
			if (newrow - currow > 1 || board[currow][curcolumn].getBottomWall().isWall()) 
				return false;
		} else if (currow > newrow ) {
			// south
			if (currow - newrow  > 1 || board[currow][curcolumn].getTopWall().isWall()) 
				return false;
		} else if (curcolumn < newcolumn) {
			// east
			if (newcolumn - curcolumn > 1 || board[currow][curcolumn].getRightWall().isWall()) 
				return false;
		} else if (curcolumn > newcolumn) {
			// west
			if (curcolumn - newcolumn > 1 || board[currow][curcolumn].getLeftWall().isWall()) 
				return false;
		} 
			
		return true;
	}

	public void setHorizontalWall(int row, int column) {
		if (column < NUM_COLS - 1) {
			board[row][column].getBottomWall().placeWall();
			board[row][column + 1].getBottomWall().placeWall();
			
			
			if (row < NUM_ROWS - 1) {
				board[++row][column].getTopWall().placeWall();
				board[row][++column].getTopWall().placeWall();
			}
		} else {
			// invalid wall placement
		}
	}
	
	public void setVerticalWall(int row, int column) {
		if (row  < NUM_ROWS - 1) {
			board[row][column].getRightWall().placeWall();
			board[row+1][column].getRightWall().placeWall();
	
			if (column < NUM_COLS - 1) {
				board[row][++column].getLeftWall().placeWall();
				board[++row][column].getLeftWall().placeWall();
			}
		} else {
			// invalid wall placement
		}
	}
	
	public boolean isValidWallPlacement(int row, int column) {
		
		if (row == NUM_ROWS - 1 || column == NUM_COLS - 1)
			return false;
		if (board[row][column].getBottomWall().isWall() || board[row][column + 1].getBottomWall().isWall())
			return false;
		if (board[row][column].getTopWall().isWall() || board[row][column + 1].getTopWall().isWall() )
			return false;
		if (board[row][column].getLeftWall().isWall() || board[row + 1][column].getLeftWall().isWall())
			return false;
		if (board[row][column].getRightWall().isWall() || board[row + 1][column].getRightWall().isWall())
			return false;
		else 
			return true;
		
		// TODO: determine if placement blocks all available paths
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private class Tile {
	
		// Field
		private Wall topWall;
		private Wall rightWall;
		private Wall leftWall;
		private Wall bottomWall;
		private boolean occupied;

		// Constructor
		public Tile () {
			occupied = false;
			
			topWall = new Wall();
			rightWall = new Wall();
			leftWall = new Wall();
			bottomWall = new Wall();
		}

		// Methods
		
		public boolean isOccupied() {
				return occupied;
		}
		
		public void setOccupied() {
			if(occupied)
				occupied = false;
			else
				occupied = true;
		}
	
		public Wall getTopWall () {
			return topWall;
		}
		
		public Wall getRightWall () {
			return rightWall;
		}

		public Wall getLeftWall () {
			return leftWall;
		}
		
		public Wall getBottomWall () {
			return bottomWall;
		}

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		private class Wall {
	
			// Fields

			private boolean wall;

			// Constructor

			public Wall() {
				wall = false;
			}

			// Methods

			public void placeWall() {
				wall = true;
			}

			public boolean isWall() {
				return wall;
			}
		}
	}
	
	
}