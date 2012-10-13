package AI;

import GameObjects.Board;

public class StateHeuristic {
	Board curState;
	Board goalState;
	
	public <T> void setCurrentState(T t) {
		curState = (Board)t;
	}
	
	public <T> void setGoalState(T t) {
		goalState = (Board)t;
	}
	
	public int getHeuristic() {
		return 0;
	}
}
