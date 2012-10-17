package AI;

import GameObjects.Pawn.DESTINATION;

public class GoalEval {
	
	public <T> boolean test(T t) {
		State curState = (State)t;
		
		switch (curState.getDestination()) {
			case FIRST_ROW: {
				return curState.getCoordinates().getRow() == 0;
			} case LAST_ROW: {
				return curState.getCoordinates().getRow() == 8;
			} case FIRST_COLUMN: {
				return curState.getCoordinates().getColumn() == 0;
			} case LAST_COLUMN: {
				return curState.getCoordinates().getColumn() == 8;
			} default: {
				break;
			}
		}
		
		return false;
	}
}
