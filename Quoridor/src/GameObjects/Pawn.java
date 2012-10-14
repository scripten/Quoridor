package GameObjects;

import java.util.ArrayList;

import AI.State;
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
		private ArrayList<Coordinates> path;
		
		// Constructor
		public Pawn(int startRow, int startColumn, DESTINATION destintation, int numWalls) {
			wallCount = numWalls;
			coord = new Coordinates(startRow, startColumn);
			dest = destintation;
			path = new ArrayList<Coordinates>();
			
			path.add(Coordinates.clone(coord));
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
			path.add(Coordinates.clone(coord));
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
		
		public boolean hasTraveled(Coordinates newCoord) {
			for (int i = 0; i < path.size(); i++) {
				if (path.get(i).equals(coord)) {
					if (i + 1 < path.size() && path.get(i + 1).equals(newCoord)) 
						return true;
				}
			}
			
			return false;
		}
		
		@Override 
		public boolean equals(Object obj) { 
			
			// Determine if the nodes are equal
			if (obj == this) {
	            return true;
			}
	        else if (obj == null || obj.getClass() != this.getClass()) {
	            return false;
	        } else {
	        	Pawn pawn = (Pawn)obj;

	        	System.out.println(coord.getRow());
	        	System.out.println(coord.getColumn());
	        	System.out.println(pawn.getCoordinates().getRow());
	        	System.out.println(pawn.getCoordinates().getColumn());
	        	
	        	if (coord.equals(pawn.getCoordinates()))
	        		return true;
	        	else 
	        		return false;
	        }
		}
		
		public static Pawn clone(Pawn toClone) {
			Pawn res;
			res = new Pawn(toClone.getCoordinates().getRow(), toClone.getCoordinates().getColumn(), toClone.dest, toClone.wallCount);
			
			res.path.clear();

			for (int i = 0; i < toClone.path.size(); i++)
				res.path.add(Coordinates.clone(toClone.path.get(i)));

			return res;
		}
	}