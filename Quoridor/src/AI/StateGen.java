package AI;

public class StateGen {
	private final int MAX_STATES = 6;
	
	State[] states;		// Generated sates
	int index = 0;		// Current index
	int count = 0;		// Number of generated states
	
	public StateGen() {
		states = new State[MAX_STATES];
	}
	
	public <T> void setState(T t) {
		State curState;
		State newState;
		
		curState = (State)t;
		index = 0;
		count = 0;
		
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
		return states[index];
		//TODO: index out of bounds exception
	}
	

}
