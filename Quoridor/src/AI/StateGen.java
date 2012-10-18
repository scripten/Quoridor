package AI;

import java.util.ArrayList;

import GameObjects.Coordinates;

public class StateGen {
	private final int MAX_STATES = 8;
	
	State[] states;		// Generated sates
	int index = 0;		// Current index
	int count = 0;		// Number of generated states
	State curState;
	Coordinates curCoord;
	
	public StateGen() {
		states = new State[MAX_STATES];
	}
	
	public <T> void setState(T t) {
		
		State newState;
		
		curState = (State)t;
		
		curCoord = Coordinates.clone(curState.getCoordinates());
		index = 0;
		count = 0;

		// Move pawn north east
		if (curState.canMoveNorthEast()) {
			newState = State.clone(curState);
			newState.moveNorthEast();
			states[count] = newState;
			count++;
			
		}
		
		// Move pawn north west
		if (curState.canMoveNorthWest()) {
			newState = State.clone(curState);
			newState.moveNorthWest();
			states[count] = newState;
			count++;
		}
		
		// Move pawn south west
		if (curState.canMoveSouthWest()) {
			newState = State.clone(curState);
			newState.moveSouthWest();
			states[count] = newState;
			count++;
		}
		
		// Move pawn south east
		if (curState.canMoveSouthEast()) {
			newState = State.clone(curState);
			newState.moveSouthEast();
			states[count] = newState;
			count++;
		}
		
		// Move pawn north
		if (curState.canMoveNorth()) {
			newState = State.clone(curState);
			newState.moveNorth();
			states[count] = newState;
			count++;
		}
		
		// Move pawn south
		if (curState.canMoveSouth()) {
			newState = State.clone(curState);
			newState.moveSouth();
			states[count] = newState;
			count++;
		}
		
	  // Move pawn west
		if (curState.canMoveWest()) {
			newState = State.clone(curState);
			newState.moveWest();
			states[count] = newState;
			count++;
		}
		
		// Move pawn east
		if (curState.canMoveEast()) {
			newState = State.clone(curState);
			newState.moveEast();
			states[count] = newState;
			count++;
		}
		
		// TODO: move diagonal for jumps
	}
	
	public void firstState() {
		index = 0;
	}
	
	public boolean endOfStates() {
		return index == count;
	}
	
	public void nextState() {
		if (index < count)
			index++;
		//TODO: index out of bounds exception
	}
	
	public State getCurrentState() {
		if (index < count)
			return states[index];
		else
			return null;
		//TODO: index out of bounds exception
	}
	
	
	public ArrayList<Coordinates> getNorthMoves() {
		ArrayList<Coordinates> res = null;
		Coordinates newCoord;
		
		newCoord = Coordinates.clone(curCoord);
		
		if (newCoord.getRow() > 0) {
			newCoord.setRow(newCoord.getRow() - 1);
			
			if (gb.isOccupied(newCoord.getRow(), newCoord.getColumn()) && newCoord.getRow() > 1) 
				newCoord.setRow(curCoord.getRow() - 2);
			
			return gb.isValidMove(curCoord, newCoord);
		} else
			return res;
	}

}
