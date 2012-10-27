package GameObjects;

import java.util.ArrayList;

import GameObjects.Pawn;
import GameObjects.Pawn.DESTINATION;

public class Players {
	
	
	private final static int NUM_WALLS_FOUR_PLAYERS = 5;
	private final static int NUM_WALLS_TWO_PLAYERS = 10;
	
	public Pawn[] pawns;
	private int currentPlayer;
	private int numPlayers;
	
	public Players(boolean fourPlayers) {
		
		if (fourPlayers) {
			numPlayers = 4;
			pawns = new Pawn[numPlayers];
		
			pawns[0] = new Pawn(8, 4, DESTINATION.FIRST_ROW, NUM_WALLS_FOUR_PLAYERS);
			pawns[1] = new Pawn(0 ,4, DESTINATION.LAST_ROW, NUM_WALLS_FOUR_PLAYERS);
			pawns[2] = new Pawn(4, 0, DESTINATION.LAST_COLUMN, NUM_WALLS_FOUR_PLAYERS);
			pawns[3] = new Pawn(4, 8, DESTINATION.FIRST_COLUMN, NUM_WALLS_FOUR_PLAYERS);
		}
		else {
			numPlayers = 2;
			pawns = new Pawn[numPlayers];
			pawns[0] = new Pawn(8, 4, DESTINATION.FIRST_ROW, NUM_WALLS_TWO_PLAYERS);
			pawns[1] = new Pawn(0, 4, DESTINATION.LAST_ROW, NUM_WALLS_TWO_PLAYERS);
			
			/*pawns[0] = new Pawn(4, 0, DESTINATION.LAST_COLUMN,NUM_WALLS_TWO_PLAYERS);
			pawns[1] = new Pawn(4, 8, DESTINATION.FIRST_COLUMN, NUM_WALLS_TWO_PLAYERS);*/
		}
		
		currentPlayer = 0;
	}
	
	public Pawn getCurrentPlayer() {
		return pawns[currentPlayer];
	}
	
	public int getCurrentPlayerID() {
		return currentPlayer;
	}
	
	public void nextPlayer() {
		currentPlayer++;
		
		if (currentPlayer > numPlayers - 1)
			currentPlayer = 0;
	}
	
	public int getNumberOfPlayers() {
		return numPlayers;
	}
	
	public boolean isWinner() {
		switch (pawns[currentPlayer].getDestination()) {
			case FIRST_ROW: {
				return pawns[currentPlayer].getCoordinates().getRow() == 0;
			} case LAST_ROW: {
				return pawns[currentPlayer].getCoordinates().getRow() == 8;
			} case FIRST_COLUMN: {
				return pawns[currentPlayer].getCoordinates().getColumn() == 0;
			} case LAST_COLUMN: {
				return pawns[currentPlayer].getCoordinates().getColumn() == 8;
			} default: {
				break;
			}
		}
		
		return false;
	}
	
	public ArrayList<Coordinates> getPlayersCoordinates() {
		ArrayList<Coordinates> res;
		
		res = new ArrayList<Coordinates>(numPlayers);
		for (int i = 0; i < numPlayers; i++)
			res.add(pawns[i].getCoordinates());
		
		return res;
	}
	
	public ArrayList<DESTINATION> getPlayersDestinations() {
		ArrayList<DESTINATION> res;
		
		res = new ArrayList<DESTINATION>(numPlayers);
		for (int i = 0; i < numPlayers; i++)
			res.add(pawns[i].getDestination());
		
		return res;
	}
}