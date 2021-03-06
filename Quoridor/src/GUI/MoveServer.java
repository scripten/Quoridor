package GUI;

import java.net.*;
import java.io.*;
import java.util.*;

import GameObjects.*;

public class MoveServer {
	
	private ServerSocket server; // Server socket for output
	private Socket client;		 // Client socket for input
	private PrintWriter out;	 // Hooked to server socket to print over TCP
	private BufferedReader in;	 // Hooked to client socket to receive from TCP
	private static int port;			 // Port number for connection
	private int numPlayers;		 // Number of players in game
	private int id;				 // Player id for this move server
	private String playerName = "Player";   // Player name for this move server
	private Board board = new Board();
	private Players players;
	
	public MoveServer (int port) {
		this.port = port;
		try {	// Sets up a server socket and hooks a print stream to it 
			server = new ServerSocket(port);
			System.out.println("Server established on port " + port);
		} catch (IOException e) {
			System.out.println("Could not listen on port " + port);
			System.exit(1);
		}
	}
	
	// Set player name to send to game client to display as the player's name
	public void setName (String name) {
	    this.playerName = name; 
	}
	
	// Allows outside classes to start a move server
	public static void startServer() {
	    MoveServer ms = new MoveServer(6666);
	    ms.getClient();
        while(!ms.startGame())
            System.out.println("Start message incorrectly formatted");
        
        ms.close();
	}
	
	// Allows outside classes to start a move server - this overload allows port selection
    public static void startServer(int port) {
        MoveServer ms = new MoveServer(port);
        ms.getClient();
        while(!ms.startGame())
            System.out.println("Start message incorrectly formatted");
        
        ms.close();
    }
	
	// Waits for a client to connect to the socket and then sets up communications
	private void getClient () {
		try {	// Sets up a client server and begins listening
			client = server.accept();
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			System.out.println("Client connection established");
		} catch (IOException e) {
			System.out.println("No client socket established: Port " + port);
			System.exit(1);
		}
	}
	
	// Takes input from client and uses it to set up the initial game state
	private boolean startGame () {
		String input = "";
		try {
			// Grab input from the client and process it
			input = in.readLine();
			Scanner sc = new Scanner(input);
			if(!sc.next().equals("QUORIDOR"))
				return false;
			this.numPlayers = sc.nextInt();
			this.id = sc.nextInt();
			boolean fourPlayers;
			if (this.numPlayers == 4)
				fourPlayers = true;
			else
				fourPlayers = false;
			players = new Players(fourPlayers);
			
			System.out.println(input);
			System.out.println("Game established with " + numPlayers + " players");
			System.out.println("This mover server is player " + id);
			System.out.println("Sending response to game client, player name is " + this.playerName);
			out.write("READY " + this.playerName);
			gameLoop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	private void gameLoop () {
	    String input = "";
	    try {
	        while (true) {
	            input = in.readLine();
	            Scanner sc = new Scanner(input);
	            if (sc.hasNext()) {
	                String command = sc.next();
	                System.out.println(command);
	                if (command.equals("MOVE?")) {
	                    //make move
	                    System.out.println("This would be a prompt for a move");
	                } else if (command.equals("MOVED")) {
	                    //reflect a move
	                    String movingPlayer = sc.next();
                        String moveType = sc.next();
                        String firstMoveX = sc.next();
                        String firstMoveY = sc.next();
                        String secondMoveX = sc.next();
                        String secondMoveY = sc.next();
                        firstMoveX = firstMoveX.substring(1, 1);
                        firstMoveY = firstMoveY.substring(0, 0);
                        secondMoveX = secondMoveX.substring(1, 1);
                        secondMoveY = secondMoveY.substring(0, 0);

                        Coordinates firstMove = new Coordinates(Integer.parseInt(firstMoveX), Integer.parseInt(firstMoveY));
                        Coordinates secondMove = new Coordinates(Integer.parseInt(secondMoveX), Integer.parseInt(secondMoveY));
                        if (moveType == "M") {
                            // move pawn
                            (players.getSpecificPlayer(Integer.parseInt(movingPlayer))).move(secondMove);
                        } else {
                            // place wall
                            if (firstMove.getRow() != secondMove.getRow()){
                            	board.setVerticalWall(firstMove.getRow(), firstMove.getColumn());
                            } else {
                            	board.setHorizontalWall(firstMove.getRow(), firstMove.getColumn());
                            }
                        }
	                } else if (command.equals("REMOVED")) {
	                    //reflect player removal
	                    int removedPlayer = Integer.parseInt(sc.next());
	                    System.out.println("Player ID " + removedPlayer + " has been removed from the game.");
	                    if (removedPlayer == this.id) {
	                        System.out.println("Aww shucks, we got removed... Shutting down in shame...");
	                        break;
	                    } else {
	                    	players.removePlayer(removedPlayer);
	                    }
	                } else if (command.equals("WINNER")) {
	                    //reflect player win
	                    int winner = Integer.parseInt(sc.next());
	                    System.out.println("Winner: Player ID " + winner);
	                    if (winner == this.id)
	                        System.out.println("I won! WOOHOO!");
	                    break;
	                } 
	            }
	        }
	        this.close();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
            e.printStackTrace();
	    }
	}
	
	// When the server is closed, take care of the leftovers
	private void close () {
		try {
			server.close();
			client.close();
			in.close();
			out.close();
		} catch (IOException e) {
			System.out.println("Close operation failed");
			System.exit(1);
		}
	}
	
	public static void main(String[] args) {
	    if (args.length != 0) {
	        startServer(Integer.parseInt(args[0]));
	    } else {
	        startServer();
	    }
	}

}
