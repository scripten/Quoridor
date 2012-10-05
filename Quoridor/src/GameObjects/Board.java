package GameObjects;

/* This Board object runs the back-end elements of the Quoridor game.
// So far the initial construction of the board is completed, as
// is a skeletal system for initial placement and movement of pawns
*/

public class Board {

	public enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}
	
	public enum WALL_TYPE {
		VERTICAL, HORIZONTAL
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
		int curRow; 
		int curColumn; 
		int newRow;
		int newColumn;
		
		curRow = currentCoordinates.getRow();
		curColumn = currentCoordinates.getColumn();
		
		newRow = newCoordinates.getRow();
		newColumn = newCoordinates.getColumn();
		
		if (curRow != newRow && curColumn != newColumn) {
			// diagonal
			return false;
		} else if (curRow==newRow && curColumn==newColumn){
			return false;
		}else if (curRow < newRow) {
			// north
			if (newRow - curRow > 1 || board[curRow][curColumn].getBottomWall().isWall()) 
				return false;
		} else if (curRow > newRow ) {
			// south
			if (curRow - newRow  > 1 || board[curRow][curColumn].getTopWall().isWall()) 
				return false;
		} else if (curColumn < newColumn) {
			// east
			if (newColumn - curColumn > 1 || board[curRow][curColumn].getRightWall().isWall()) 
				return false;
		} else if (curColumn > newColumn) {
			// west
			if (curColumn - newColumn > 1 || board[curRow][curColumn].getLeftWall().isWall()) 
				return false;
		} 
			
		return true;
	}

	public boolean setHorizontalWall(int row, int column) {
		if (column < NUM_COLS - 1) {
			board[row][column].getBottomWall().placeWall();
			board[row][column + 1].getBottomWall().placeWall();
			
			
			board[row][column].setHorizontalFirstWall();
			
			if (row < NUM_ROWS - 1) {
				board[++row][column].getTopWall().placeWall();
				board[row][++column].getTopWall().placeWall();
			}
		}
		return true;
	}
	
	public boolean setVerticalWall(int row, int column) {
		if (row  < NUM_ROWS - 1) {
			board[row][column].getRightWall().placeWall();
			board[row+1][column].getRightWall().placeWall();
			
			board[row][column].setVerticalFirstWall();
			
			if (column < NUM_COLS - 1) {
				board[row][++column].getLeftWall().placeWall();
				board[++row][column].getLeftWall().placeWall();
			}
		}
		return true;
	}
	
	public boolean isValidWallPlacement(int row, int column, WALL_TYPE wallType) {
		boolean canMove = false;
		
		if (row == NUM_ROWS - 1 || column == NUM_COLS - 1)
			canMove = false;
		else if (wallType == WALL_TYPE.VERTICAL)
			if (board[row][column].hasHorizontalFirstWall())
				canMove =  false;
			else if (board[row][column].getRightWall().isWall() || board[row + 1][column].getRightWall().isWall())
				canMove =  false;
			else 
				canMove =  true;
		else if (wallType == WALL_TYPE.HORIZONTAL)
			if (board[row][column].hasVerticalFirstWall())
				canMove =  false;
			else if (board[row][column].getBottomWall().isWall() || board[row][column + 1].getBottomWall().isWall())
				canMove =  false;
			else 
				canMove =  true;
		else 
			canMove = true;
		
		return canMove;
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
		private boolean hasHorizFirstWall;
		private boolean hasVertFirstWall;

		// Constructor
		public Tile () {
			occupied = false;
			hasHorizFirstWall = false;
			hasVertFirstWall = false;
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
		
		public boolean hasHorizontalFirstWall() {
			return hasHorizFirstWall;
		}
		
		public boolean hasVerticalFirstWall() {
			return hasVertFirstWall;
		}
		

		public void setHorizontalFirstWall() {
			hasHorizFirstWall = true;
		}
		
		public void setVerticalFirstWall() {
			hasVertFirstWall = true;
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