package GameObjects;

import GameObjects.Pawn;

public class Players {
	private final static int NUM_WALLS_FOUR_PLAYERS = 5;
	private final static int NUM_WALLS_TWO_PLAYERS = 10;
	
	private Pawn[] pawns;
	private int currentPlayer;
	private int numPlayers;
	
	public Players(boolean fourPlayers) {
		
		if (fourPlayers) {
			numPlayers = 4;
			pawns = new Pawn[numPlayers];
			
			pawns[0] = new Pawn(8, 4, NUM_WALLS_FOUR_PLAYERS);
			pawns[1] = new Pawn(0 ,4, NUM_WALLS_FOUR_PLAYERS);
			pawns[2] = new Pawn(4, 0, NUM_WALLS_FOUR_PLAYERS);
			pawns[3] = new Pawn(4, 8, NUM_WALLS_FOUR_PLAYERS);
		}
		else {
			numPlayers = 2;
			pawns = new Pawn[numPlayers];
			pawns[0] = new Pawn(8, 4, NUM_WALLS_TWO_PLAYERS);
			pawns[1] = new Pawn(0, 4, NUM_WALLS_TWO_PLAYERS);
		}
		
		currentPlayer = 0;
	}
	
	public Pawn getCurrentPlayer() {
		return pawns[currentPlayer];
	}
	
	public void nextPlayer() {
		currentPlayer++;
		
		if (currentPlayer > numPlayers - 1)
			currentPlayer = 0;
	}
}