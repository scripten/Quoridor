package AI;

import java.util.ArrayList;

import GameObjects.Coordinates;

public class StateGen {
	private final int MAX_STATES = 50;
	
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

		ArrayList<Coordinates> gen = getSates(curCoord);
		
		for (int i = 0; i < gen.size(); i++) 
			genState(gen.get(i));
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
	
	private void genState(Coordinates newCoordinates) {
		State newState;
		
		// Move if newCoordiantes is not null
		if (newCoordinates != null) {
			newState = State.clone(curState);
			newState.move(newCoordinates);
			
			states[count] = newState;
			count++;
		}
	}
	
	public void getState(ArrayList<Coordinates> res, ArrayList<Coordinates> explored, Coordinates currentCoordinates) {
		Coordinates newCoord;
		

		newCoord = Coordinates.clone(currentCoordinates);
		
		// North
		if (newCoord.getRow() > 0) {
			if (!curState.getGameBoard().hasTopWall(newCoord.getRow(), newCoord.getColumn())) {
				newCoord.setRow(newCoord.getRow() - 1); 
				
				if (curState.getGameBoard().isOccupied(newCoord.getRow(), newCoord.getColumn())) {
					if (!explored.contains(newCoord)) {
						explored.add(newCoord);
						getState(res, explored, newCoord);
					}
				} else 
					res.add(newCoord);
			}
		}
		
		newCoord = Coordinates.clone(currentCoordinates);
		
		// South
		if (newCoord.getRow() < 8) {
			
			if (!curState.getGameBoard().hasBottomWall(newCoord.getRow(), newCoord.getColumn())) {
				newCoord.setRow(newCoord.getRow() + 1);
				
				if (curState.getGameBoard().isOccupied(newCoord.getRow(), newCoord.getColumn())) {
					if (!explored.contains(newCoord)) {
						explored.add(newCoord);
						getState(res, explored, newCoord);
					}
				} else 
					res.add(newCoord);
			}
		} 
		
		
		newCoord = Coordinates.clone(currentCoordinates);
		

		
		// East
		if (newCoord.getColumn() < 8) {
			if (!curState.getGameBoard().hasRightWall(newCoord.getRow(), newCoord.getColumn())) {
				newCoord.setColumn(newCoord.getColumn() + 1);
			
	
				if (curState.getGameBoard().isOccupied(newCoord.getRow(), newCoord.getColumn())) {
					if (!explored.contains(newCoord)) {
						explored.add(newCoord);
						getState(res, explored, newCoord);
					}
				} else 
					res.add(newCoord);
			}
		} 
		
		newCoord = Coordinates.clone(currentCoordinates);
		
		// West
		if (newCoord.getColumn() > 0) {
			if (!curState.getGameBoard().hasLeftWall(newCoord.getRow(), newCoord.getColumn())) {
				newCoord.setColumn(newCoord.getColumn() - 1);
			
				if (curState.getGameBoard().isOccupied(newCoord.getRow(), newCoord.getColumn())) {
					if (!explored.contains(newCoord)) {
						explored.add(newCoord);
						getState(res, explored, newCoord);
					}
				} else 
					res.add(newCoord);
			}
		} 
	
	}
	
	public ArrayList<Coordinates> getSates(Coordinates coordinates) {
		ArrayList<Coordinates> res = new ArrayList<Coordinates>();
		ArrayList<Coordinates> explored = new ArrayList<Coordinates>();
		
		getState(res, explored, coordinates);
		
		return res;
	}
	

}
