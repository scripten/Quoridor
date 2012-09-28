package GameObjects;

import GameObjects.Coordinates;

//Pawn is a private class used by the board object to keep track of the status of each player
	public class Pawn {
		// Constants
		public final static int FOUR_PLAYER_MAX_WALLS = 5;
		public final static int TWO_PLAYER_MAX_WALLS = 10; 
		
		// Fields
		private int wallCount;  // Holds the number of walls this pawn has to use (Full wall placing

		
		private Coordinates coord;
		
		// Constructor
		public Pawn(boolean fourPlayers, int row, int column) {
			setInitialWallCount(fourPlayers);
			coord = new Coordinates(row, column);
		}
		
		// Methods
	
		public Coordinates getCoordinates() {
			return coord;
		}

		public void move(Coordinates newCoordinates) {
			coord.setX(newCoordinates.getX());
			coord.setY(newCoordinates.getY());
		}
		
		// This merely sets the initial wall count for the player depending on how many other players
		// there are
		public void setInitialWallCount(boolean fourPlayerGame) {
			if (fourPlayerGame)
				wallCount = 5;
			else
				wallCount = 10;
		}
		
		public int getWallCount() {
			return wallCount;
		}
		
		public void useWall() {
			wallCount--;
		}
		
		public boolean hasAvailableWalls() {
			return wallCount == 0;
		}
	}