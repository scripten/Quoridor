package AI;

import GameObjects.Board;

public class StateHeuristic {
	State curState;				// Current state
	int heuristic;				// Heuristic cost
	
	public <T> void setCurrentState(T t) {
		curState = (State)t;
	}
	
	public void generateHeurstic() {
		heuristic = 0;

		switch (curState.getPawn().getDestination()) {
			case FIRST_ROW: {
				heuristic = curState.getPawn().getCoordinates().getRow();
				break;
			} case LAST_ROW: {
				heuristic = Board.NUM_ROWS - 1 - curState.getPawn().getCoordinates().getRow();
				break;
			} case FIRST_COLUMN: {
				heuristic = curState.getPawn().getCoordinates().getColumn() ;
				break;
			} case LAST_COLUMN: {
				heuristic = Board.NUM_COLS - 1 - curState.getPawn().getCoordinates().getColumn();
				break;
			} default: {
				
				break;
			}
		}
		
		
	}
	
	public int getHeuristic() {
		return heuristic;
	}
}
