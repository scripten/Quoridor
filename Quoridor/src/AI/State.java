package AI;

import GameObjects.Board;
import GameObjects.Coordinates;
import GameObjects.Pawn.DESTINATION;

public class State {
	private Board gb;
	private Coordinates curCoord;
	private DESTINATION dest;
	
	public State(Board gameBoard, Coordinates coordinates, DESTINATION destination) {
		gb = Board.clone(gameBoard);
		curCoord = Coordinates.clone(coordinates);
		dest = destination;
	}
	
	public Board getGameBoard() {
		return gb;
	}
	
	public Coordinates getCoordinates() {
		return curCoord;
	}
	
	public DESTINATION getDestination() {
		return dest;
	}
	
	public void move(Coordinates newCoordinates) {
		gb.setUnoccupied(curCoord);
		gb.setOccupied(newCoordinates);
		
		curCoord = Coordinates.clone(newCoordinates);
	}
	
	/*public boolean canMoveNorth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getRow() > 0) {
			newCoord.setRow(newCoord.getRow() - 1);
			
			if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()) && newCoord.getRow() > 1) 
				newCoord.setRow(curCoord.getRow() - 2);
			
			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}*/
	
	/*public boolean canMoveSouth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getRow() < 8) {
			newCoord.setRow(newCoord.getRow() + 1);
			
			if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()) && newCoord.getRow() < 7) 
				newCoord.setRow(curCoord.getRow() + 2);
			
			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
		
		
	}*/
	
	/*public boolean canMoveEast() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getColumn() < 8) {
			newCoord.setColumn(newCoord.getColumn() + 1);
		

			if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()) && newCoord.getColumn() < 7) 
				newCoord.setColumn(curCoord.getColumn() + 2);
			
			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}*/
	
	/*public boolean canMoveWest() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getColumn() > 0) {
			newCoord.setColumn(newCoord.getColumn() - 1);
		
			if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()) && newCoord.getColumn() > 1) 
				newCoord.setColumn(curCoord.getColumn() - 2);

			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}*/
	
	
	public boolean canMoveNorthEast() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getRow() > 0 && newCoord.getColumn() < 8) {
			newCoord.setRow(newCoord.getRow() - 1);
			newCoord.setColumn(newCoord.getColumn() + 1);

			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}
	
	public boolean canMoveNorthWest() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getRow() > 0 && newCoord.getColumn() > 0) {
			newCoord.setRow(newCoord.getRow() - 1);
			newCoord.setColumn(newCoord.getColumn() - 1);
			
			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}
	
	public boolean canMoveSouthWest() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getRow() < 8 && newCoord.getColumn() > 0) {
			newCoord.setRow(newCoord.getRow() + 1);
			newCoord.setColumn(newCoord.getColumn() - 1);
 			
			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}
	
	
	public boolean canMoveSouthEast() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getRow() < 8 && newCoord.getColumn() < 8) {
			newCoord.setRow(newCoord.getRow() + 1);
			newCoord.setColumn(newCoord.getColumn() + 1);
			
			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}

	public void moveNorth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);

		newCoord.setRow(newCoord.getRow() - 1);
		
		if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()))
			newCoord.setRow(newCoord.getRow() - 1);
		
		gb.setUnoccupied(curCoord);
		gb.setOccupied(newCoord);
		
		curCoord = Coordinates.clone(newCoord);
	}
	
	public void moveSouth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);

		newCoord.setRow(newCoord.getRow() + 1);
		
		if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()))
			newCoord.setRow(newCoord.getRow() + 1);
		
		gb.setUnoccupied(curCoord);
		gb.setOccupied(newCoord);
		
		curCoord = Coordinates.clone(newCoord);
	}
	
	public void moveEast() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);

		newCoord.setColumn(newCoord.getColumn() + 1);
		
		if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()))
			newCoord.setColumn(newCoord.getColumn() + 1);
		
		gb.setUnoccupied(curCoord);
		gb.setOccupied(newCoord);
		
		curCoord = Coordinates.clone(newCoord);
	}
	
	public void moveWest() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);

		newCoord.setColumn(newCoord.getColumn() - 1);
		
		if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()))
			newCoord.setColumn(newCoord.getColumn() - 1);
		
		gb.setUnoccupied(curCoord);
		gb.setOccupied(newCoord);
		
		curCoord = Coordinates.clone(newCoord);
		
	}
	
	public void moveNorthEast() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);

		newCoord.setRow(newCoord.getRow() - 1);
		newCoord.setColumn(newCoord.getColumn() + 1);

		gb.setUnoccupied(curCoord);
		gb.setOccupied(newCoord);
		
		curCoord = Coordinates.clone(newCoord);
	}
	
	public void moveNorthWest() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);

		newCoord.setRow(newCoord.getRow() - 1);
		newCoord.setColumn(newCoord.getColumn() - 1);

		gb.setUnoccupied(curCoord);
		gb.setOccupied(newCoord);
		
		curCoord = Coordinates.clone(newCoord);
	}
	
	public void moveSouthWest() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);

		newCoord.setRow(newCoord.getRow() + 1);
		newCoord.setColumn(newCoord.getColumn() - 1);

		gb.setUnoccupied(curCoord);
		gb.setOccupied(newCoord);
		
		curCoord = Coordinates.clone(newCoord);
	}
	
	public void moveSouthEast() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);

		newCoord.setRow(newCoord.getRow() + 1);
		newCoord.setColumn(newCoord.getColumn() + 1);

		gb.setUnoccupied(curCoord);
		gb.setOccupied(newCoord);
		
		curCoord = Coordinates.clone(newCoord);
	}
	
	
	
	public static State clone(State curState) {
		State res;
		res = new State(curState.gb, curState.curCoord, curState.dest);
		return res;
	}
	
	@Override 
	public boolean equals(Object obj) { 
		// Determine if the nodes are equal
		if (obj == this) 
            return true;
        else if (obj == null || obj.getClass() != this.getClass()) 
            return false;
        else {
        	State state = (State)obj;
        	
        	if (!curCoord.equals(state.curCoord))
        		return false;
        	else
        		return true;
        }
	}
}
