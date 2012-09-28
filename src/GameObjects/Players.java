package GameObjects;

import GameObjects.Pawn;

public class Players {
	private Pawn[] pawns;
	private int currentPlayer;
	private int numPlayers;
	
	public Players(boolean fourPlayers) {
		
		if (fourPlayers) {
			numPlayers = 4;
			pawns = new Pawn[numPlayers];
			
			pawns[0] = new Pawn(fourPlayers, 8, 4);
			pawns[1] = new Pawn(fourPlayers, 0 , 4);
			pawns[2] = new Pawn(fourPlayers, 4, 0);
			pawns[3] = new Pawn(fourPlayers, 4, 8);
		}
		else {
			numPlayers = 2;
			pawns = new Pawn[numPlayers];
			pawns[0] = new Pawn(fourPlayers, 8, 4);
			pawns[1] = new Pawn(fourPlayers, 0 , 4);
		}
		
		currentPlayer = 0;
	}
	
	public Pawn getCurrentPlayer() {
		return pawns[currentPlayer];
	}
	
	public void nextPlayer() {
		currentPlayer++;
		
		if (currentPlayer > numPlayers)
			currentPlayer = 0;
	}
}