package GameObjects;


import java.util.*;
import java.io.*;

/* This Board object runs the back-end elements of the Quoridor game.
// So far the initial construction of the board is completed, as
// is a skeletal system for initial placement and movement of pawns
*/

public class Board {

	// Fields

	protected ArrayList<ArrayList> rows;			 // ArrayList containing the ArrayLists that
													 // correspond to each row of the board
	private Pawn player1, player2, player3, player4; // Pawn objects that are to be used for each player
	private boolean fourPlayerGame;

	// Constructor

	// Board is constructed utilizing a single boolean parameter which designates how many players
	// are to be initialized and where they will start. Right now, no work has been done on
	// distinguishing between human and computer players, but the functionality for moving pawns
	// is entirely blind and thus allows for either a player-driven GUI or an AI to drive it.
	public Board (boolean fourPlayerGame) {
		this.fourPlayerGame = fourPlayerGame;
		// Initialize the x axis of the board grid using rows
		for(int x = 0; x < 9; x++) {
			ArrayList<Tile> row = new ArrayList<Tile>();
			// For each row there exists nine tiles, which can be accessed through
			// the getTile(int x, int y) method
			for(int y = 0; y < 9; y++) {
				// When each tile is generated, this algorithm checks if it is on the
				// edge of the board and if so, a wall is instantly placed to block off
				// movement outside of the grid
				Tile tile = new Tile(x, y);
				if(x == 0)
					tile.placeTop();
				else if(x == 9)  //FIXME 
					tile.placeBottom();
				if(y == 0)
					tile.placeLeft();
				else if(y == 9)  //FIXME 
					tile.placeRight();
				row.add(tile);
			}
			// Once each tile is finished, the final product is added to rows
			rows.add(row);
		}
		// Pawns are then generated based on the number of players currently in the game
		// Pawns have the ability to move their location and to keep track of that player's walls
		player1 = new Pawn(1,5);
		player2 = new Pawn(9,5);
		if(fourPlayerGame) {
			player3 = new Pawn(5,1);
			player4 = new Pawn(5,9);
			player1.setInitialWallCount(true);
			player2.setInitialWallCount(true);
			player3.setInitialWallCount(true);
			player4.setInitialWallCount(true);
		} else {
			player1.setInitialWallCount(false);
			player2.setInitialWallCount(false);
		}
	}

	// Methods

	// Move() takes a selected pawn and moves it in a legal direction if possible. If move()
	// is successful, it returns true and the move is made. Otherwise, move() returns false
	// and the attempted movement is skipped. Legal directions are "up", "down", "left", and "right"
	public boolean move (Pawn pawn, String direction) {
		return pawn.move(direction);
	}
	
	// Returns a tile at any given x and y value. If the given coordinates are outside of the grid
	// bounds, nothing happens beside a short print message and a null Tile being returned (This
	// may throw an exception in the future)
	public Tile getTile (int x, int y) {
		try {
			return (Tile)rows.get(x).get(y);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("That coordinate is outside the grid.");
		}
		return null;
	}
	
	// Returns one of the four Pawn objects. If an invalid parameter is passed, a null Pawn object
	// is returned (This may just become an exception, since an invalid player being called is a bug.
	public Pawn getPlayer (int player) {
		switch (player) {
			case 1:
				return player1;
			case 2:
				return player2;
			case 3:
				if(fourPlayerGame)
					return player3;
				else
					break;
			case 4:
				if(fourPlayerGame)
					return player4;
				else
					break;
		}
		System.out.println("That is not a legal player!");
		return null;
	}

	// Pawn is a private class used by the board object to keep track of the status of each player
	private class Pawn {
	
		// Fields
		
		private int x, y;		// Holds the coordinates of the pawn
		private int wallCount;  // Holds the number of walls this pawn has to use (Full wall placing
								// functionality is not yet implemented)
		
		// Constructor
		
		public Pawn (int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		// Methods
	
		// Move() returns a boolean value that depends on whether or not the attempted movement
		// was successful. If it was, true is returned and the move in completed, otherwise, false
		// is returned and the move is skipped. (This method should only be called by the board object,
		// anyway)
		protected boolean move (String direction) {
			if(direction.equals("up")) {
				if(x == 0)
					return false;
				Tile goal = (Tile)rows.get(x - 1).get(y);
				if(goal.hasOpenBottom()) {
					x--;
					return true;
				} else
					return false;
			} else if(direction.equals("left")) {
				if(y == 0)
					return false;
				Tile goal = (Tile)rows.get(x).get(y - 1);
				if(goal.hasOpenBottom()) {
					x--;
					return true;
				} else
					return false;
			} else if(direction.equals("right")) {
				if(y == 9)
					return false;
				Tile goal = (Tile)rows.get(x).get(y + 1);
				if(goal.hasOpenBottom()) {
					x--;
					return true;
				} else
					return false;
			} else if(direction.equals("down")) {
				if(x == 9)
					return false;
				Tile goal = (Tile)rows.get(x + 1).get(y);
				if(goal.hasOpenBottom()) {
					x--;
					return true;
				} else
					return false;
			} else
			return false;
		}
		
		// This merely sets the initial wall count for the player depending on how many other players
		// there are
		public void setInitialWallCount (boolean fourPlayerGame) {
			if(fourPlayerGame)
				wallCount = 5;
			else
				wallCount = 10;
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
}