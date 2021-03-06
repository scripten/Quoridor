package GameObjects;

import static java.lang.Math.abs;

import java.util.ArrayList;

import AI.GoalEval;
import AI.Node;
import AI.Search;
import AI.State;
import AI.StateGen;
import AI.StateHeuristic;
import GameObjects.Pawn.DESTINATION;

/* This Board object runs the back-end elements of the Quoridor game.
// So far the initial construction of the board is completed, as
// is a skeletal system for initial placement and movement of pawns
*/

public class Board {
	
	public enum WALL_TYPE {
		VERTICAL, HORIZONTAL
	}

	// Fields
	public final static int NUM_ROWS = 9;
	public final static int NUM_COLS = 9;
	
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
		//try {
			return board[row][column];
		//} catch (ArrayIndexOutOfBoundsException e) {
		//	System.out.println("That coordinate is outside the grid.");
		//	throw e;
		//}
	}
	

	public boolean hasTopWall(int row, int column) {
		return board[row][column].getTopWall().isWall();
	}
	
	public boolean hasBottomWall(int row, int column) {
		return board[row][column].getBottomWall().isWall();
	}
	
	public boolean hasLeftWall(int row, int column) {
		return board[row][column].getLeftWall().isWall();
	}
	
	public boolean hasRightWall(int row, int column) {
		return board[row][column].getRightWall().isWall();
	}
	
	public boolean hasVerticalFirstWall(int row, int column) {
		return board[row][column].hasVerticalFirstWall();
	}
	
	public boolean hasHorizontalFirstWall(int row, int column) {
		return board[row][column].hasHorizontalFirstWall();
	}
	
	public void setOccupied(Coordinates currentCoordinates) {
		board[currentCoordinates.getRow()][currentCoordinates.getColumn()].setOccupied();
	}
	
	public void setUnoccupied(Coordinates currentCoordinates) {
		board[currentCoordinates.getRow()][currentCoordinates.getColumn()].setUnoccupied();
	}
	
	public boolean isOccupied(int row, int column) {
		return board[row][column].isOccupied();
	}
	
	public boolean isValidMove(Coordinates currentCoordinates, Coordinates newCoordinates) {
		int curRow; 			// Current row
		int curColumn; 			// Current column
		int newRow;				// New row
		int newColumn;			// New column

		Coordinates recCoord = Coordinates.clone(currentCoordinates);
		
		curRow = currentCoordinates.getRow();
		curColumn = currentCoordinates.getColumn();
		
		newRow = newCoordinates.getRow();
		newColumn = newCoordinates.getColumn();
	
		if (curRow == newRow && curColumn == newColumn)
			return false;
		else if (board[newRow][newColumn].isOccupied())
			return false;
		if (curRow != newRow && curColumn != newColumn) {			// Diagonal

			// Determine which direction the move is in
			if (curRow < newRow && curColumn < newColumn) {			// South east
				
				if (board[curRow + 1][curColumn].isOccupied()) {
					if (board[curRow + 1][curColumn].isOccupied()) {
						recCoord.setRow(curRow + 1);
						
						// Determine if a wall is blocking move
						if (board[curRow + 1][curColumn].getTopWall().isWall())
							return false;
						else if (board[curRow + 1][curColumn].getRightWall().isWall())
							return false;
						else 
							return isValidMove(recCoord, newCoordinates);
					}
				} else if (board[curRow][curColumn + 1].isOccupied()) {
					if (board[curRow][curColumn + 1].isOccupied()) {
						recCoord.setColumn(curColumn + 1);
						
						if (board[curRow][curColumn + 1].getLeftWall().isWall())
							return false;
						else if (board[curRow][curColumn + 1].getBottomWall().isWall())
							return false;
						else 
							return isValidMove(recCoord, newCoordinates);
					}
				} else
					return false;
			} else if (curRow > newRow && curColumn < newColumn) {	// North east
				
				if (board[curRow - 1][curColumn].isOccupied()) {
					if (board[curRow - 1][curColumn].isOccupied()) {
						recCoord.setRow(curRow - 1);
						
						if (board[curRow - 1][curColumn].getBottomWall().isWall())
							return false;
						else if (board[curRow - 1][curColumn].getRightWall().isWall())
							return false;
						else 
							return isValidMove(recCoord, newCoordinates);
					}
				} else if (board[curRow][curColumn + 1].isOccupied()) {
					if (board[curRow][curColumn + 1].isOccupied()) {
						recCoord.setColumn(curColumn + 1);
						
						if (board[curRow][curColumn + 1].getTopWall().isWall())
							return false;
						else if (board[curRow][curColumn + 1].getTopWall().isWall())
							return false;
						else 
							return isValidMove(recCoord, newCoordinates);
					}
				} else
					return false;
			} else if (curRow < newRow && curColumn > newColumn) {	// South west
				
				if (board[curRow + 1][curColumn].isOccupied()) {
					if (board[curRow + 1][curColumn].isOccupied()) {
						recCoord.setRow(curRow + 1);
						
						if (board[curRow + 1][curColumn].getTopWall().isWall())
							return false;
						else if (board[curRow + 1][curColumn].getLeftWall().isWall())
							return false;
						else 
							return isValidMove(recCoord, newCoordinates);
					}
				} else if (board[curRow][curColumn - 1].isOccupied()) {
					if (board[curRow][curColumn - 1].isOccupied()) {
						recCoord.setColumn(curColumn - 1);
						
						if (board[curRow][curColumn - 1].getRightWall().isWall())
							return false;
						else if (board[curRow][curColumn - 1].getTopWall().isWall())
							return false;
						else 
							return isValidMove(recCoord, newCoordinates);
					}
				} else
					return false;
			} else if (curRow > newRow && curColumn > newColumn) {	// North west
				
				if (board[curRow - 1][curColumn].isOccupied()) {
					if (board[curRow - 1][curColumn].isOccupied()) {
						recCoord.setRow(curRow - 1);
						
						if (board[curRow - 1][curColumn].getBottomWall().isWall())
							return false;
						else if (board[curRow - 1][curColumn].getLeftWall().isWall())
							return false;
						else 
							return isValidMove(recCoord, newCoordinates);
					}
				} else if (board[curRow][curColumn - 1].isOccupied()) {
					if (board[curRow][curColumn - 1].isOccupied()) {
						recCoord.setColumn(curColumn - 1);
						
						if (board[curRow][curColumn - 1].getRightWall().isWall())
							return false;
						else if (board[curRow][curColumn - 1].getTopWall().isWall())
							return false;
						else 
							return isValidMove(recCoord, newCoordinates);
					}
				} else
					return false;
			} else
				return false;
		} else if (curRow < newRow) {								// South
			// Reject the move if the move distance is too big or if a wall is in place
			if (board[curRow][curColumn].getBottomWall().isWall()) 
				return false;
			else if (board[curRow + 1][curColumn].isOccupied()) {
				recCoord.setRow(curRow + 1);
				return isValidMove(recCoord, newCoordinates);
			} else if (newRow - curRow > 1 || newColumn != curColumn) 
				return false;
		} else if (curRow > newRow ) {								// North
			// Reject the move if the move distance is too big or if a wall is in place
			if (board[curRow][curColumn].getTopWall().isWall()) 
				return false;
			else if (board[curRow - 1][curColumn].isOccupied()) {
				recCoord.setRow(curRow - 1);
				return isValidMove(recCoord, newCoordinates);
			} else if (curRow - newRow > 1 || newColumn != curColumn)
				return false;
		} else if (curColumn < newColumn) {							// East
			// Reject the move if the move distance is too big or if a wall is in place
			if (board[curRow][curColumn].getRightWall().isWall()) 
				return false;
			else if (board[curRow][curColumn + 1].isOccupied()) {
				recCoord.setColumn(curColumn + 1);
				return isValidMove(recCoord, newCoordinates);
			} else if (newColumn - curColumn > 1 || newRow != curRow)
				return false;
		} else if (curColumn > newColumn) {							// West
			// Reject the move if the move distance is too big or if a wall is in place
			if (board[curRow][curColumn].getLeftWall().isWall()) 
				return false;
			else if (board[curRow][curColumn - 1].isOccupied()) {
				recCoord.setColumn(curColumn - 1);
				return isValidMove(recCoord, newCoordinates);
			} else if (curColumn - newColumn > 1 || newRow != curRow)
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
	
	public boolean isValidWallPlacement(int row, int column, WALL_TYPE wallType, ArrayList<Coordinates> allPlayerCoordinates, ArrayList<DESTINATION> allPlayerDestinations) {
		boolean canMove = false;
		
		if (row >= NUM_ROWS - 1 || column >= NUM_COLS - 1 || row < 0 || column < 0)
			canMove = false;
		else if (wallType == WALL_TYPE.VERTICAL)
			if (board[row][column].hasHorizontalFirstWall())
				canMove =  false;
			else if (board[row][column].getRightWall().isWall() || board[row + 1][column].getRightWall().isWall())
				canMove =  false;
			else {
				
				for (int i = 0; i < allPlayerCoordinates.size(); i++) {
					Node<State> curState = new Node<State>(null);
					Board boardCopy = clone(this);
					
					boardCopy.setVerticalWall(row, column);
					curState.setState(new State(boardCopy, allPlayerCoordinates.get(i), allPlayerDestinations.get(i)));
					
					GoalEval goal = new GoalEval();
					StateGen stateGen = new StateGen();
					StateHeuristic heuristic = new StateHeuristic();
					Node<State> nextMove;
					
					nextMove = Search.aStar(curState, goal, stateGen, heuristic);

					if (nextMove == null) {
						System.out.println("Wall would block a path.");
						canMove = false;
						break;
					} else
						canMove =  true;
				}
			}
				
		else if (wallType == WALL_TYPE.HORIZONTAL)
			if (board[row][column].hasVerticalFirstWall())
				canMove =  false;
			else if (board[row][column].getBottomWall().isWall() || board[row][column + 1].getBottomWall().isWall())
				canMove =  false;
			else {
				
				for (int i = 0; i < allPlayerCoordinates.size(); i++) {
					Node<State> curState = new Node<State>(null);
					Board boardCopy = clone(this);
					
					boardCopy.setHorizontalWall(row, column);
					curState.setState(new State(boardCopy, allPlayerCoordinates.get(i), allPlayerDestinations.get(i)));
					
					GoalEval goal = new GoalEval();
					StateGen stateGen = new StateGen();
					StateHeuristic heuristic = new StateHeuristic();
					Node<State> nextMove;
					
					nextMove = Search.aStar(curState, goal, stateGen, heuristic);
					if (nextMove == null) {
						System.out.println("Wall would block a path.");
						canMove = false;
						break;
					} else
						canMove =  true;
				}
			}
		else 
			canMove = true;
		
		return canMove;
		// TODO: determine if placement blocks all available paths
	}
	
	public static Board clone(Board toClone) {
		Board res;
		
		res = new Board();
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				res.board[i][j].occupied = toClone.board[i][j].occupied;
				res.board[i][j].topWall.wall = toClone.board[i][j].topWall.wall;
				res.board[i][j].bottomWall.wall = toClone.board[i][j].bottomWall.wall;
				res.board[i][j].leftWall.wall = toClone.board[i][j].leftWall.wall;
				res.board[i][j].rightWall.wall = toClone.board[i][j].rightWall.wall;
			}
		}
		return res;
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
				occupied = true;
		}
		
		public void setUnoccupied() {
				occupied = false;
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