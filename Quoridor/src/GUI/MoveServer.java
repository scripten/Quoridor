package GUI;

import java.net.*;
import java.io.*;
import java.util.*;

public class MoveServer {
	
	private ServerSocket server; // Server socket for output
	private Socket client;		 // Client socket for input
	private PrintWriter out;	 // Hooked to server socket to print over TCP
	private BufferedReader in;	 // Hooked to client socket to receive from TCP
	private int port;			 // Port number for connection
	private int numPlayers;		 // Number of players in game
	private int id;				 // Player id for this move server
	
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
			System.out.println("Game established with " + numPlayers + " players");
			System.out.println("This mover server is player " + id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
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
		MoveServer ms = new MoveServer(6666);
		ms.getClient();
		while(!ms.startGame())
			System.out.println("Start message incorrectly formatted");
		
		ms.close();
	}

}
