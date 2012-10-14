package AI;

public class GoalEval {
	
	public <T> boolean test(T t) {
		State curState = (State)t;
		
		switch (curState.getPawn().getDestination()) {
			case FIRST_ROW: {
				return curState.getPawn().getCoordinates().getRow() == 0;
			} case LAST_ROW: {
				return curState.getPawn().getCoordinates().getRow() == 8;
			} case FIRST_COLUMN: {
				return curState.getPawn().getCoordinates().getColumn() == 0;
			} case LAST_COLUMN: {
				return curState.getPawn().getCoordinates().getColumn() == 8;
			} default: {
				break;
			}
		}
		
		return false;
	}
}
