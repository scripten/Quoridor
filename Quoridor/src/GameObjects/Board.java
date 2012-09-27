package GameObjects;


import java.util.*;
import java.io.*;

import GUI.HorizontalWallButton;
import GUI.SquareButton;
import GUI.VerticalWallButton;

/* This Board object runs the back-end elements of the Quoridor game.
// So far the initial construction of the board is completed, as
// is a skeletal system for initial placement and movement of pawns
*/

public class Board {

	public enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}
	
	public final static int FOUR_PLAYER_MAX_WALLS = 5;
	public final static int TWO_PLAYER_MAX_WALLS = 10; 
	// Fields

	protected Tile[][] board = new Tile[9][9];			 // [row][column]
													 // correspond to each row of the board
	
	private boolean fourPlayerGame;
	

	// Constructor

	// Board is constructed utilizing a single boolean parameter which designates how many players
	// are to be initialized and where they will start. Right now, no work has been done on
	// distinguishing between human and computer players, but the functionality for moving pawns
	// is entirely blind and thus allows for either a player-driven GUI or an AI to drive it.
	public Board (boolean fourPlayerGame) {
		this.fourPlayerGame = fourPlayerGame;
		// Initialize the x axis of the board grid using rows
		
		for(int row = 0; row < 9; row++) 
			for(int column = 0; column < 9; column++) {
				board[row][column] = new Tile(row, column);	
			}
	}

	// Methods
	
	// Returns a tile at any given x and y value. If the given coordinates are outside of the grid
	// bounds, nothing happens beside a short print message and a null Tile being returned (This
	// may throw an exception in the future)
	public Tile getTile (int row, int column) {
		try {
			return board[row][column];
		} catch (ArrayIndexOutOfBoundsException e) {
			// throw exception
			System.out.println("That coordinate is outside the grid.");
		}
		return null;
	}
	
	

	// Pawn is a private class used by the board object to keep track of the status of each player
	public class Pawn {
	
		// Fields
		private int row, column;		// Holds the coordinates of the pawn
		private int wallCount;  // Holds the number of walls this pawn has to use (Full wall placing
								// functionality is not yet implemented)
		
		// Constructor
		
		public Pawn(int row, int column) {
			this.row = row;
			this.column = column;
		}
		
		public Pawn(SquareButton tile){
			this.row = tile.getRow();
			this.column = tile.getColumn();
		}
		
		// Methods
	
		// Move() returns a boolean value that depends on whether or not the attempted movement
		// was successful. If it was, true is returned and the move in completed, otherwise, false
		// is returned and the move is skipped. (This method should only be called by the board object,
		// anyway)
		public void move(DIRECTION direction) {
			switch (direction) {
				case UP:{
					row--;
					break;
				}
				case DOWN:{
					row++;
					break;
				}
				case LEFT:{
					column--;
					break;
				}
				case RIGHT:{
					column++;
					break;
				}
				default: {
					//throw invalid move exception
					break;
				}
				
				// set board tile to occupied
			}
		}
		
		// This merely sets the initial wall count for the player depending on how many other players
		// there are
		public void setInitialWallCount(boolean fourPlayerGame) {
			if(fourPlayerGame)
				wallCount = 5;
			else
				wallCount = 10;
		}
		
		public int getWallCount() {
			return wallCount;
		}
		
		public void setWall(int row, int column) {
			//set wall at row,column
			wallCount++;
		}
		
		public boolean hasAvailableWalls() {
			if (fourPlayerGame) {
				if (wallCount < FOUR_PLAYER_MAX_WALLS) 
					return true;
				else
					return false;
			} else {
				if (wallCount < TWO_PLAYER_MAX_WALLS) 
					return true;
				else
					return false;
			}
		}
	}

	private class Tile {
	
		// Fields

		private Edge top;
		private Edge right;
		private Edge left;
		private Edge bottom;
		private int x, y;

		// Constructor

		public Tile (int x, int y) {
			top = new Edge();
			right = new Edge();
			left = new Edge();
			bottom = new Edge();
			this.x = x;
			this.y = y;
		}

		// Methods
	
		public Edge getTop () {
			return top;
		}
		
		public Edge getRight () {
			return right;
		}

		public Edge getLeft () {
			return left;
		}
		
		public Edge getBottom () {
			return bottom;
		}
		
		public boolean hasOpenTop () {
			if(!top.isWall())
				return true;
			return false;
		}
		
		public boolean hasOpenRight () {
			if(!right.isWall())
				return true;
			return false;
		}
		
		public boolean hasOpenLeft () {
			if(!left.isWall())
				return true;
			return false;
		}
		
		public boolean hasOpenBottom () {
			if(!bottom.isWall())
				return true;
			return false;
		}
		
		public boolean placeTop () {
			if(this.hasOpenTop()) {
				top.placeWall();
				return true;
			}
			return false;
		}
		
		public boolean placeRight () {
			if(this.hasOpenRight()) {
				right.placeWall();
				return true;
			}
			return false;
		}
		
		public boolean placeLeft () {
			if(this.hasOpenLeft()) {
				left.placeWall();
				return true;
			}
			return false;
		}
		
		public boolean placeBottom () {
			if(this.hasOpenBottom()) {
				bottom.placeWall();
				return true;
			}
			return false;
		}
	
		private class Edge {
	
			// Fields

			private boolean isWall;

			// Constructor

			public Edge () {
			}

			// Methods

			public void placeWall () {
				isWall = true;
			}

			public boolean isWall () {
				return isWall;
			}
		}
	}
	
	public boolean isLegalMove(SquareButton tile){
		//TODO needs to be implemented
		return true;
	}
	public boolean isLegalMove(HorizontalWallButton horizWall){
		//TODO needs to be implemented
		return true;
			
	}
	public boolean isLegalMove(VerticalWallButton vertWall){
		//TODO needs to be implemented
		return true;
	}
}