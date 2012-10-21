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
