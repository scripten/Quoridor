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

	// Fields

	private static int NUM_ROWS = 9;
	private static int NUM_COLS = 9;
	
	private Tile[][] board = new Tile[NUM_ROWS][NUM_COLS];											 
	private boolean fourPlayerGame;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Constructor

	// Board is constructed utilizing a single boolean parameter which designates how many players
	// are to be initialized and where they will start. Right now, no work has been done on
	// distinguishing between human and computer players, but the functionality for moving pawns
	// is entirely blind and thus allows for either a player-driven GUI or an AI to drive it.
	public Board (boolean fourPlayerGame) {
		this.fourPlayerGame = fourPlayerGame;
		
		// Initialize the x axis of the board grid using rows
		
		for(int row = 0; row < NUM_ROWS; row++) 
			for(int column = 0; column < NUM_COLS; column++) {
				board[row][column] = new Tile();	
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
	
	public void setOccupied(int row, int column) {
		board[row][column].setOccupied();
	}
	
	public void setUnccupied(int row, int column) {
		board[row][column].setUnoccupied();
	}
	
	public boolean isOccupied(int row, int column) {
		return board[row][column].isOccupied();
	}
	
	public boolean isValidMove(Coordinates currentCoordinates, Coordinates newCoordinates) {
		int curX;
		int curY;
		int newX;
		int newY;
		
		curX = currentCoordinates.getX();
		curY = currentCoordinates.getY();
		
		newX = newCoordinates.getX();
		newY = newCoordinates.getY();
		if (curX != newX && curY != newY) {
			// diagonal
			return false;
		} else if (curX < newX) {
			// north
			if (newX - curX > 1 || board[curX][curY].getBottomWall().isWall()) 
				return false;
		} else if (curX > newX ) {
			// south
			if (curX - newX  > 1 || board[curX][curY].getTopWall().isWall()) 
				return false;
		} else if (curY < newY) {
			// east
			if (newY - curY > 1 || board[curX][curY].getRightWall().isWall()) 
				return false;
		} else if (curY > newY) {
			// west
			if (curY - newY > 1 || board[curX][curY].getLeftWall().isWall()) 
				return false;
		} 
			
		return true;
	}

	public void setHorizontalWall(int x, int y) {
		board[x][y].getBottomWall().placeWall();
		
		if (x < NUM_ROWS - 1)
			board[++x][y].getTopWall().placeWall();
	}
	
	public void setVerticalWall(int x, int y) {
		board[x][y].getRightWall().placeWall();

		if (y < NUM_COLS - 1)
			board[x][++y].getLeftWall().placeWall();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private class Tile {
	
		// Fields

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
			occupied = true;
		}
		
		public void setUnoccupied() {
			occupied = false;
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

			private boolean isWall;

			// Constructor

			public Wall() {
				isWall = false;
			}

			// Methods

			public void placeWall() {
				isWall = true;
			}

			public boolean isWall() {
				return isWall;
			}
		}
	}
	
	
}