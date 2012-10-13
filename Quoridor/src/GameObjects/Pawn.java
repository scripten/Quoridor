package GameObjects;

import GameObjects.Coordinates;

//Pawn is a private class used by the board object to keep track of the status of each player
	public class Pawn {
		
		public enum DESTINATION {
			FIRST_ROW, LAST_ROW, FIRST_COLUMN, LAST_COLUMN
		}
		
		// Fields
		private int wallCount;  // Holds the number of walls this pawn has to use (Full wall placing
		private Coordinates coord;
		private DESTINATION dest;
		
		// Constructor
		public Pawn(int startRow, int startColumn, DESTINATION destintation, int numWalls) {
			wallCount = numWalls;
			coord = new Coordinates(startRow, startColumn);
			dest = destintation;
		}
		
		// Methods
		public Coordinates getCoordinates() {
			return coord;
		}
		
		public DESTINATION getDestination() {
			return dest;
		}

		public void move(Coordinates newCoordinates) {
			coord.setRow(newCoordinates.getRow());
			coord.setColumn(newCoordinates.getColumn());
		}
		
		public int getWallCount() {
			return wallCount;
		}
		
		public void useWall() {
			wallCount--;
		}
		
		public boolean hasAvailableWalls() {
			return wallCount != 0;
		}
	}