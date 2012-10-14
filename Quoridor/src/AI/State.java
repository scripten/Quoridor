package AI;

import GameObjects.Board;
import GameObjects.Coordinates;
import GameObjects.Pawn;

public class State {
	private Board gb;
	private Pawn pawn;
	private Coordinates previousCoord;
	
	public State(Board gameBoard, Pawn player) {
		gb = gameBoard;
		pawn = player;
		previousCoord = new Coordinates();
	}
	
	public Board getGameBoard() {
		return gb;
	}
	
	public Pawn getPawn() {
		return pawn;
	}
	
	public boolean canMoveNorth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		
		if (newCoord.getRow() > 0) {
			newCoord.setRow(newCoord.getRow() - 1);
			
			if (previousCoord.equals(newCoord))
				return false;
			
			if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()) && newCoord.getRow() > 1) 
				newCoord.setRow(pawn.getCoordinates().getRow() - 2);
			
			return gb.isValidMove(pawn.getCoordinates(), newCoord);
		} else
			return false;
	}
	
	public boolean canMoveSouth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		
		if (newCoord.getRow() < 8) {
			newCoord.setRow(newCoord.getRow() + 1);
			
			if (previousCoord.equals(newCoord))
				return false;
			
			if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()) && newCoord.getRow() < 7) 
				newCoord.setRow(pawn.getCoordinates().getRow() + 2);
			
			return gb.isValidMove(pawn.getCoordinates(), newCoord);
		} else
			return false;
		
		
	}
	
	public boolean canMoveEast() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		
		if (newCoord.getColumn() < 8) {
			newCoord.setColumn(newCoord.getColumn() + 1);
		

			if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()) && newCoord.getColumn() < 7) 
				newCoord.setColumn(pawn.getCoordinates().getColumn() + 2);
			
			
			if (previousCoord.equals(newCoord))
				return false;
			System.out.println(previousCoord.getRow() + " " + previousCoord.getColumn() + " " + newCoord.getRow() + " " + newCoord.getColumn());
			
			return gb.isValidMove(pawn.getCoordinates(), newCoord);
		} else
			return false;
	}
	
	public boolean canMoveWest() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(pawn.getCoordinates());
		
		if (newCoord.getColumn() > 0) {
			newCoord.setColumn(newCoord.getColumn() - 1);
		
			
			
			if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()) && newCoord.getColumn() > 1) 
				newCoord.setColumn(pawn.getCoordinates().getColumn() - 2);
			
			if (previousCoord.equals(newCoord))
				return false;
			
			System.out.println(previousCoord.getRow() + " " + previousCoord.getColumn() + " " + newCoord.getRow() + " " + newCoord.getColumn());
			
			return gb.isValidMove(pawn.getCoordinates(), newCoord);
		} else
			return false;
	}
	
	
	public boolean canMoveNorthEast() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(pawn.getCoordinates());
		
		if (newCoord.getRow() > 0 && newCoord.getColumn() < 8) {
			newCoord.setRow(newCoord.getRow() - 1);
			newCoord.setColumn(newCoord.getColumn() + 1);

			
			if (previousCoord.equals(newCoord))
				return false;
			
			return gb.isValidMove(pawn.getCoordinates(), newCoord);
		} else
			return false;
	}
	
	public boolean canMoveNorthWest() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(pawn.getCoordinates());
		
		if (newCoord.getRow() > 0 && newCoord.getColumn() > 0) {
			newCoord.setRow(newCoord.getRow() - 1);
			newCoord.setColumn(newCoord.getColumn() - 1);
			
			if (previousCoord.equals(newCoord))
				return false;
			
			return gb.isValidMove(pawn.getCoordinates(), newCoord);
		} else
			return false;
	}
	
	public boolean canMoveSouthWest() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(pawn.getCoordinates());
		
		if (newCoord.getRow() < 8 && newCoord.getColumn() > 0) {
			newCoord.setRow(newCoord.getRow() + 1);
			newCoord.setColumn(newCoord.getColumn() - 1);
 			
			if (previousCoord.equals(newCoord))
				return false;
			
			return gb.isValidMove(pawn.getCoordinates(), newCoord);
		} else
			return false;
	}
	
	
	public boolean canMoveSouthEast() {
		Coordinates newCoord;

		newCoord = Coordinates.clone(pawn.getCoordinates());
		
		if (newCoord.getRow() < 8 && newCoord.getColumn() < 8) {
			newCoord.setRow(newCoord.getRow() + 1);
			newCoord.setColumn(newCoord.getColumn() + 1);
			
			if (previousCoord.equals(newCoord))
				return false;
			
			return gb.isValidMove(pawn.getCoordinates(), newCoord);
		} else
			return false;
	}

	public void moveNorth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		previousCoord = Coordinates.clone(pawn.getCoordinates());
		
		newCoord.setRow(newCoord.getRow() - 1);
		
		if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()))
			newCoord.setRow(newCoord.getRow() - 1);
		
		pawn.move(newCoord);
		
		gb.setUnoccupied(pawn.getCoordinates());
		gb.setOccupied(newCoord);
	}
	
	public void moveSouth() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		previousCoord = Coordinates.clone(pawn.getCoordinates());
		
		newCoord.setRow(newCoord.getRow() + 1);
		
		if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()))
			newCoord.setRow(newCoord.getRow() + 1);
		
		gb.setUnoccupied(pawn.getCoordinates());
		gb.setOccupied(newCoord);
		
		pawn.move(newCoord);
	}
	
	public void moveEast() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		previousCoord = Coordinates.clone(pawn.getCoordinates());
		
		newCoord.setColumn(newCoord.getColumn() + 1);
		
		if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()))
			newCoord.setColumn(newCoord.getColumn() + 1);
		
		gb.setUnoccupied(pawn.getCoordinates());
		gb.setOccupied(newCoord);
		
		pawn.move(newCoord);
	}
	
	public void moveWest() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		previousCoord = Coordinates.clone(pawn.getCoordinates());
		
		newCoord.setColumn(newCoord.getColumn() - 1);
		
		if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()))
			newCoord.setColumn(newCoord.getColumn() - 1);
		
		gb.setUnoccupied(pawn.getCoordinates());
		gb.setOccupied(newCoord);
		
		pawn.move(newCoord);
		
		System.out.println(previousCoord.getRow() + " " + previousCoord.getColumn() + " " + newCoord.getRow() + " " + newCoord.getColumn());
	}
	
	public void moveNorthEast() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		previousCoord = Coordinates.clone(pawn.getCoordinates());
		
		newCoord.setRow(newCoord.getRow() - 1);
		newCoord.setColumn(newCoord.getColumn() + 1);

		gb.setUnoccupied(pawn.getCoordinates());
		gb.setOccupied(newCoord);
		
		pawn.move(newCoord);
	}
	
	public void moveNorthWest() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		previousCoord = Coordinates.clone(pawn.getCoordinates());
		
		newCoord.setRow(newCoord.getRow() - 1);
		newCoord.setColumn(newCoord.getColumn() - 1);

		gb.setUnoccupied(pawn.getCoordinates());
		gb.setOccupied(newCoord);
		
		pawn.move(newCoord);
	}
	
	public void moveSouthWest() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		previousCoord = Coordinates.clone(pawn.getCoordinates());
		
		newCoord.setRow(newCoord.getRow() + 1);
		newCoord.setColumn(newCoord.getColumn() - 1);

		gb.setUnoccupied(pawn.getCoordinates());
		gb.setOccupied(newCoord);
		
		pawn.move(newCoord);
	}
	
	public void moveSouthEast() {
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(pawn.getCoordinates());
		previousCoord = Coordinates.clone(pawn.getCoordinates());
		
		newCoord.setRow(newCoord.getRow() + 1);
		newCoord.setColumn(newCoord.getColumn() + 1);

		gb.setUnoccupied(pawn.getCoordinates());
		gb.setOccupied(newCoord);
		
		pawn.move(newCoord);
	}
	
	public static State clone(State curState) {
		State res;
		res = new State(Board.clone(curState.gb), Pawn.clone(curState.getPawn()));
		res.previousCoord = Coordinates.clone(curState.previousCoord);
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
        	
        	if (!pawn.getCoordinates().equals(state.getPawn().getCoordinates()))
        		return false;
        	else
        		return true;
        }
	}
}
