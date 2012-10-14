package AI;

import GameObjects.Board;
import GameObjects.Coordinates;
import GameObjects.Pawn;

public class State {
	private Board gb;
	private Pawn pawn;
	private Coordinates curCoord;
	
	public State(Board gameBoard, Pawn player) {
		gb = gameBoard;
		pawn = player;
		curCoord = pawn.getCoordinates();
	}
	
	public Board getGameBoard() {
		return gb;
	}
	
	public Pawn getPawn() {
		return pawn;
	}
	
	public boolean canMoveNorth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getRow() > 0) {
			newCoord.setRow(newCoord.getRow() - 1);
		
			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}
	
	public boolean canMoveSouth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getRow() < 8) {
			newCoord.setRow(newCoord.getRow() + 1);
		
			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}
	
	public boolean canMoveEast() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getColumn() < 8) {
			newCoord.setColumn(newCoord.getColumn() + 1);
		
			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}
	
	public boolean canMoveWest() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getColumn() > 0) {
			newCoord.setColumn(newCoord.getColumn() - 1);
		
			return gb.isValidMove(curCoord, newCoord);
		} else
			return false;
	}

	public void moveNorth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		newCoord.setRow(newCoord.getRow() - 1);
		pawn.move(newCoord);
	}
	
	public void moveSouth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		newCoord.setRow(newCoord.getRow() + 1);
		pawn.move(newCoord);
	}
	
	public void moveEast() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		newCoord.setColumn(newCoord.getColumn() + 1);
		pawn.move(newCoord);
	}
	
	public void moveWest() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		newCoord.setColumn(newCoord.getColumn() - 1);
		pawn.move(newCoord);
	}
	
	public static State clone(State curState) {
		State res;
		res = new State(curState.gb, Pawn.clone(curState.getPawn()));
		return res;
	}
	
	@Override 
	public boolean equals(Object obj) { 
		System.out.println("asdf");
		// Determine if the nodes are equal
		if (obj == this) 
            return true;
        else if (obj == null || obj.getClass() != this.getClass()) 
            return false;
        else {
        	State state = (State)obj;
        	
        	if (!pawn.getCoordinates().equals(state.getPawn().getCoordinates()))
        		return false;
        	else
        		return true;
        }
	}
}
