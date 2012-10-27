package AI;

import java.util.ArrayList;

import GameObjects.Board;
import GameObjects.Coordinates;

public class WallPlacement {
	
	public enum DIRECTION {
		NORTH, SOUTH, EAST, WEST
	}
	
	Board gb;
	Coordinates curCoord;
	Coordinates newCoord;
	Coordinates wallCoord;
	boolean placeHorzWall = false;
	boolean placeVertWall = false;
	DIRECTION dir;
	
	public void setBoard(Board gameBoard) {
		gb = gameBoard;
	}
	
	public void setCurrentCoordinates(Coordinates currentCoordinates) {
		curCoord = Coordinates.clone(currentCoordinates);
		wallCoord = Coordinates.clone(currentCoordinates);
	}
	
	public void setNewCoordinates(Coordinates newCoordinates) {
		newCoord = Coordinates.clone(newCoordinates);
	}
	
	public boolean isHorizontalWall() {
		return placeHorzWall;
	}
	
	public boolean isVerticalWall() {
		return placeVertWall;
	}
	
	private void setDirection() {
		// Determine which direct to place a wall
		if (newCoord.getRow() == curCoord.getRow() - 1) { 				// North
			placeHorzWall = true;
			dir = DIRECTION.NORTH;
			wallCoord.setRow(wallCoord.getRow() - 1);
		} else if (newCoord.getRow() == curCoord.getRow() + 1) { 		// South
			placeHorzWall = true;
			dir = DIRECTION.SOUTH;
		} else if (newCoord.getColumn() == curCoord.getColumn() - 1) { 	// West
			placeVertWall = true;
			dir = DIRECTION.WEST;
			wallCoord.setColumn(wallCoord.getColumn() - 1);
		} else if (newCoord.getColumn() == curCoord.getColumn() + 1) { 	// East
			placeVertWall = true;
			dir = DIRECTION.EAST;
		}
	}
	
	public ArrayList<Coordinates> getWallPlacements() {
		ArrayList<Coordinates> res = new ArrayList<Coordinates>();
		int row;
		int col;
		
		setDirection();
	
		row = wallCoord.getRow();
		col = wallCoord.getColumn();
		
		if (placeHorzWall) {
			if (dir == DIRECTION.NORTH) {
				if (col < Board.NUM_COLS - 1 && !gb.hasTopWall(row, col + 1) && !gb.hasVerticalFirstWall(row, col)) 
					res.add(new Coordinates(row, col));
				if (col > 0 && !gb.hasTopWall(row, col - 1) && !gb.hasVerticalFirstWall(row, col - 1)) 
					res.add(new Coordinates(row, col - 1));
			} else if (dir == DIRECTION.SOUTH) {
				if (col < Board.NUM_COLS - 1 && !gb.hasBottomWall(row, col + 1) && !gb.hasVerticalFirstWall(row, col)) 
					res.add(new Coordinates(row, col));
				if (col > 0 && !gb.hasBottomWall(row, col - 1) && !gb.hasVerticalFirstWall(row, col - 1)) 
					res.add(new Coordinates(row, col - 1));
			}
		} else {
			if (dir == DIRECTION.WEST) {
				if (row < Board.NUM_ROWS - 1 && !gb.hasLeftWall(row + 1, col) && !gb.hasHorizontalFirstWall(row, col)) 
					res.add(new Coordinates(row, col));
				if (row > 0 && !gb.hasLeftWall(row - 1, col) && !gb.hasHorizontalFirstWall(row - 1, col)) 
					res.add(new Coordinates(row - 1, col));
			} else if (dir == DIRECTION.EAST) {
				if (row < Board.NUM_ROWS - 1 && !gb.hasRightWall(row + 1, col) && !gb.hasHorizontalFirstWall(row, col)) 
					res.add(new Coordinates(row, col));
				if (row > 0 && !gb.hasRightWall(row - 1, col) && !gb.hasHorizontalFirstWall(row - 1, col)) 
					res.add(new Coordinates(row - 1, col));
			}
		}
		
		
		return res;
	} 
}
