package AI;

import GameObjects.Board;

public class StateGen {
	Board curState;
	int index = 0;
	
	public <T> void setState(T t) {
		curState = (Board)t;
	}
	
	public void firstState() {
		index = 0;
	}
	
	public boolean endOfStates() {
		return true;
	}
	
	public void nextState() {
		index++;
	}
	
	public Board getCurrentState() {
		return curState;
	}
	
	
	
	
}
