package GameClient;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.TileObserver;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

import AI.GoalEval;
import AI.Node;
import AI.State;
import AI.Search;
import AI.StateGen;
import AI.StateHeuristic;
import GUI.FirstWindow;
import GUI.HorizontalWallButton;
import GUI.SquareButton;
import GUI.VerticalWallButton;
import GameObjects.Board;
import GameObjects.Board.WALL_TYPE;
import GameObjects.Coordinates;
import GameObjects.Players;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.io.*;
import java.net.*;

public class GameClientGUI extends JFrame {
	private SquareButton [][]tiles = new SquareButton[9][9];  // [columns][rows]
	private GridBagConstraints[][] squareGridBags= new GridBagConstraints[9][9];
	private VerticalWallButton[][] verticalWalls = new VerticalWallButton[8][9];
	private GridBagConstraints[][] verticalWallGridBags= new GridBagConstraints[8][9];
	private HorizontalWallButton[][] horizontalWalls = new HorizontalWallButton[9][8];
	private GridBagConstraints[][] horizontalWallGridBags= new GridBagConstraints[9][8];

	private Board board = new Board();
	private Players players ;
	private boolean playGame;
	private JLabel lblPlayer1Walls;
	private JLabel lblPlayer2Walls;
	private JLabel lblPlayer3Walls;
	private JLabel lblPlayer4Walls;
	private JLabel lblCurrentPlayer;
	private SquareButton btnCurrentPlayer;

	public GameClientGUI() {
		players = new Players(true);
		playGame = true;
		//CPUTurn();
		
		setResizable(false);
		setSize(695, 540);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Quoridor");
		setName("Quoridor");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		mnHelp.setHorizontalTextPosition(SwingConstants.RIGHT);
		mnHelp.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mnHelp);

		JMenuItem mntmQuoridorWiki = new JMenuItem("Quoridor Wiki");
		mntmQuoridorWiki.setName("Quoridor Wiki");
		mntmQuoridorWiki.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				URI url = null;
				try {
					url = new URI("http://en.wikipedia.org/wiki/Quoridor");
				} catch (URISyntaxException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				try {
					// try to open the default browser using the given url
					Desktop.getDesktop().browse(url);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		
		mnHelp.add(mntmQuoridorWiki);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		
		lblPlayer1Walls = new JLabel("Player 1 Walls: "+players.pawns[0].getWallCount());
		GridBagConstraints gbc_lblPlayer1Walls = new GridBagConstraints();
		gbc_lblPlayer1Walls.insets = new Insets(0, 0, 0, 5);
		gbc_lblPlayer1Walls.gridx = 18;
		gbc_lblPlayer1Walls.gridy = 2;
		getContentPane().add(lblPlayer1Walls, gbc_lblPlayer1Walls);
		
		lblPlayer2Walls = new JLabel("Player 2 Walls: "+players.pawns[1].getWallCount());
		GridBagConstraints gbc_lblPlayer2Walls = new GridBagConstraints();
		gbc_lblPlayer2Walls.insets = new Insets(0, 0, 0, 5);
		gbc_lblPlayer2Walls.gridx = 18;
		gbc_lblPlayer2Walls.gridy = 4;
		getContentPane().add(lblPlayer2Walls, gbc_lblPlayer2Walls);
		
		if(players.getNumberOfPlayers()==4){
		lblPlayer3Walls = new JLabel("Player 3 Walls: "+players.pawns[2].getWallCount());
		GridBagConstraints gbc_lblPlayer3Walls = new GridBagConstraints();
		gbc_lblPlayer3Walls.insets = new Insets(0, 0, 0, 5);
		gbc_lblPlayer3Walls.gridx = 18;
		gbc_lblPlayer3Walls.gridy = 6;
		getContentPane().add(lblPlayer3Walls, gbc_lblPlayer3Walls);
		
		lblPlayer4Walls = new JLabel("Player 4 Walls: "+players.pawns[3].getWallCount());
		GridBagConstraints gbc_lblPlayer4Walls = new GridBagConstraints();
		gbc_lblPlayer4Walls.insets = new Insets(0, 0, 0, 5);
		gbc_lblPlayer4Walls.gridx = 18;
		gbc_lblPlayer4Walls.gridy = 8;
		getContentPane().add(lblPlayer4Walls, gbc_lblPlayer4Walls);
		}
		
		lblCurrentPlayer = new JLabel("Current Player: "+(players.getCurrentPlayerID()+1));
		GridBagConstraints gbc_lblCurrentPlayer = new GridBagConstraints();
		gbc_lblCurrentPlayer.insets = new Insets(0, 0, 0, 5);
		gbc_lblCurrentPlayer.gridx = 18;
		gbc_lblCurrentPlayer.gridy = 10;
		getContentPane().add(lblCurrentPlayer, gbc_lblCurrentPlayer);
		
		btnCurrentPlayer = new SquareButton("", 0, 0);
		btnCurrentPlayer.setFocusPainted(false);
		btnCurrentPlayer.setFocusTraversalKeysEnabled(false);
		btnCurrentPlayer.setFocusable(false);
		btnCurrentPlayer.setDefaultCapable(false);
		btnCurrentPlayer.setIconTextGap(0);
		btnCurrentPlayer.setMargin(new Insets(0, 0, 0, 0));
		setTileIcon(btnCurrentPlayer , players.getCurrentPlayerID());
		GridBagConstraints gbc_btnCurrentPlayer = new GridBagConstraints();
		gbc_btnCurrentPlayer.anchor = GridBagConstraints.SOUTH;
		gbc_btnCurrentPlayer.insets = new Insets(0, 0, 0, 5);
		gbc_btnCurrentPlayer.gridx = 18;
		gbc_btnCurrentPlayer.gridy = 12;
		getContentPane().add(btnCurrentPlayer, gbc_btnCurrentPlayer);
		
		

		for(int row=0; row<9; row++){
			//show row of empty spaces and vertical walls
			for(int column=0; column<9; column++){
				//show an empty square space
				tiles[column][row] = new SquareButton("", column, row);
				tiles[column][row].setMargin(new Insets(0, 0, 0, 0));
				tiles[column][row].setAlignmentX(Component.CENTER_ALIGNMENT);
				tiles[column][row].setSize(new Dimension(50, 50));
				tiles[column][row].setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/empty space.png")));
				tiles[column][row].setBorderPainted(false);
				tiles[column][row].setBorder(null);

				squareGridBags[column][row] =  new GridBagConstraints();
				squareGridBags[column][row].insets = new Insets(0, 0, 0, 5);
				squareGridBags[column][row].gridx = column*2;
				squareGridBags[column][row].gridy = row*2;
				getContentPane().add(tiles[column][row], squareGridBags[column][row]);

				if(column!=8){
					//show an empty vertical wall
					verticalWalls[column][row] = new VerticalWallButton("", column, row);
					verticalWalls[column][row].setMargin(new Insets(0, 0, 0, 0));
					verticalWalls[column][row].setAlignmentX(Component.CENTER_ALIGNMENT);
					verticalWalls[column][row].setSize(new Dimension(50, 50));
					verticalWalls[column][row].setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/emptyVerticalWall.png")));
					verticalWalls[column][row].setBorderPainted(false);
					verticalWalls[column][row].setBorder(null);

					verticalWallGridBags[column][row] =  new GridBagConstraints();
					verticalWallGridBags[column][row].insets = new Insets(0, 0, 0, 5);
					verticalWallGridBags[column][row].gridx = column*2+1;
					verticalWallGridBags[column][row].gridy = row*2;
					getContentPane().add(verticalWalls[column][row], verticalWallGridBags[column][row]);	
				}

				if(row!=8){
					//show an empty horizontal wall
					horizontalWalls[column][row] = new HorizontalWallButton("", column, row);
					horizontalWalls[column][row].setMargin(new Insets(0, 0, 0, 0));
					horizontalWalls[column][row].setAlignmentX(Component.CENTER_ALIGNMENT);
					horizontalWalls[column][row].setSize(new Dimension(50, 50));
					horizontalWalls[column][row].setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/emptyHorizontalWall.png")));
					horizontalWalls[column][row].setBorderPainted(false);
					horizontalWalls[column][row].setBorder(null);

					horizontalWallGridBags[column][row] =  new GridBagConstraints();
					horizontalWallGridBags[column][row].insets = new Insets(0, 0, 0, 5);
					horizontalWallGridBags[column][row].gridx = column*2;
					horizontalWallGridBags[column][row].gridy = row*2+1;
					getContentPane().add(horizontalWalls[column][row], horizontalWallGridBags[column][row]);	
				}
			}
		}
		
		// before the user can see the board, set their starting piece on the board
		
		// Bottom row player
		tiles[4][8].setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/blue space.png")));
		tiles[4][8].setUsed(true);
		setVisible(true);
		
		// Top row player
		tiles[4][0].setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/red space.png")));
		tiles[4][0].setUsed(true);
		setVisible(true);
		if(players.getNumberOfPlayers()==4){
		// Left column player
		tiles[0][4].setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/green space.png")));
		tiles[0][4].setUsed(true);
		setVisible(true);
		
		
		// Right column player
		tiles[8][4].setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/yellow space.png")));
		tiles[8][4].setUsed(true);
		setVisible(true);
		}
		
	}

	public void handleSquareButtonPress(SquareButton btn){
		Coordinates currentCoordinates;
		Coordinates newCoordinates;

		if (playGame && btn.isValidated()) {
			btn.setInvalidated();
			
			currentCoordinates = players.getCurrentPlayer().getCoordinates();

			newCoordinates = new Coordinates();
			newCoordinates.setRow(btn.getRow());
			newCoordinates.setColumn(btn.getColumn());
			
			btn.setUsed(true);

            setTileIcon(btn , players.getCurrentPlayerID()) ;
			
            setTileIcon(tiles[currentCoordinates.getColumn()][currentCoordinates.getRow()], -1);
            
            tiles[currentCoordinates.getColumn()][currentCoordinates.getRow()].setUsed(false);
            
			board.setUnoccupied(currentCoordinates);
			board.setOccupied(newCoordinates);

			players.getCurrentPlayer().move(newCoordinates);
			
			if (players.isWinner()) {
				playGame = false;

				JOptionPane.showMessageDialog(this, String.format("Player %s has won the game.", players.getCurrentPlayerID() + 1));
				System.out.format("Player %s has won the game.", players.getCurrentPlayerID() + 1);
				dispose();
				FirstWindow mainMenu = new FirstWindow();
				
			} 
			players.nextPlayer();
			lblCurrentPlayer.setText("Current Player: "+(players.getCurrentPlayerID()+1));
			setTileIcon(btnCurrentPlayer , players.getCurrentPlayerID()) ;
		}
		
	}

	public void handleVerticalWallPress(VerticalWallButton vertWall) {
		
		if (playGame && vertWall.isValidated()) {
			vertWall.setInvalidated();
			
			board.setVerticalWall(vertWall.getRow(), vertWall.getColumn());
			
			players.getCurrentPlayer().useWall();

            setVerticalWallIcon(vertWall , players.getCurrentPlayerID());
	
			vertWall.setUsed(true);
			
			// and also update the vertical wall in the row beneath it

            setVerticalWallIcon(verticalWalls[vertWall.getColumn()][vertWall.getRow() + 1], players.getCurrentPlayerID() );

			verticalWalls[vertWall.getColumn()][vertWall.getRow() + 1].setUsed(true);

			players.nextPlayer();
			lblCurrentPlayer.setText("Current Player: "+(players.getCurrentPlayerID()+1));
			setTileIcon(btnCurrentPlayer , players.getCurrentPlayerID()) ;

		} 
	}

	public void handleHorizontalWallPress(HorizontalWallButton horizWall){
		
		if(playGame && horizWall.isValidated()) {
			horizWall.setInvalidated();
			
			board.setHorizontalWall(horizWall.getRow(), horizWall.getColumn());
			
			players.getCurrentPlayer().useWall();

            setHorizontalWallIcon(horizWall , players.getCurrentPlayerID());
			
			horizWall.setUsed(true);
			setHorizontalWallIcon(horizontalWalls[horizWall.getColumn() + 1][horizWall.getRow()], players.getCurrentPlayerID() )  ;
			horizontalWalls[horizWall.getColumn() + 1][horizWall.getRow()].setUsed(true);
			
			players.nextPlayer();
			lblCurrentPlayer.setText("Current Player: "+(players.getCurrentPlayerID()+1));
			setTileIcon(btnCurrentPlayer , players.getCurrentPlayerID()) ;

		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {

		//determine what type of button caused the event
		if(e.getSource() instanceof SquareButton){
			handleSquareButtonPress((SquareButton) e.getSource());
		}else if(e.getSource() instanceof VerticalWallButton){
			handleVerticalWallPress((VerticalWallButton)e.getSource());
		}else if(e.getSource() instanceof HorizontalWallButton){
			handleHorizontalWallPress((HorizontalWallButton)e.getSource());
		}
		
		
		
	}

	

	
	public void CPUTurn() {
		//if (players.getCurrentPlayerID() != 3)
		//	return;

		Node<State> curState = new Node<State>(null);
		
		curState.setState(new State(board, players.getCurrentPlayer().getCoordinates(), players.getCurrentPlayer().getDestination()));
		
		GoalEval goal = new GoalEval();
		StateGen stateGen = new StateGen();
		StateHeuristic heuristic = new StateHeuristic();
		Node<State> nextMove;
		
		nextMove = Search.aStar(curState, goal, stateGen, heuristic);
		
		if (nextMove == null) {
			System.out.println("AI did not find a path.");
			players.nextPlayer();
			return;
		}
		
		while (nextMove.getParent() != curState) 
			nextMove = nextMove.getParent();
		
		System.out.format("AI move (%d, %d)\n", 
				nextMove.getState().getCoordinates().getRow(), 
				nextMove.getState().getCoordinates().getColumn());
			
		if (board.isValidMove(players.getCurrentPlayer().getCoordinates(), nextMove.getState().getCoordinates())) {
			
			System.out.format("Path cost: %d\n", nextMove.getCost());
			System.out.println("Valid AI move");
			
			

			setTileIcon(tiles[nextMove.getState().getCoordinates().getColumn()][nextMove.getState().getCoordinates().getRow()],players.getCurrentPlayerID()) ;

			setTileIcon(tiles[players.getCurrentPlayer().getCoordinates().getColumn()][players.getCurrentPlayer().getCoordinates().getRow() ], -1);
			tiles[players.getCurrentPlayer().getCoordinates().getColumn()][players.getCurrentPlayer().getCoordinates().getRow()].setUsed(false);
			tiles[players.getCurrentPlayer().getCoordinates().getColumn()][players.getCurrentPlayer().getCoordinates().getRow()].setInvalidated();
			
			board.setUnoccupied(players.getCurrentPlayer().getCoordinates());
			board.setOccupied(nextMove.getState().getCoordinates());

			players.getCurrentPlayer().move(nextMove.getState().getCoordinates());
			
			if (players.isWinner()) {
				playGame = false;

				JOptionPane.showMessageDialog(this, String.format("Player %s has won the game.", players.getCurrentPlayerID() + 1));
				System.out.format("Player %s has won the game.\n", players.getCurrentPlayerID() + 1);
				dispose();
				FirstWindow mainMenu = new FirstWindow();
			} 
			
			
		} else 
			System.out.println("Invalid AI move");
		
		players.nextPlayer();
		
		if (players.getCurrentPlayerID() != 0){
			CPUTurn();
			lblCurrentPlayer.setText("Current Player: "+(players.getCurrentPlayerID()+1));
			setTileIcon(btnCurrentPlayer , players.getCurrentPlayerID()) ;
		}
			
	}

    public void setHorizontalWallIcon(HorizontalWallButton btn, int player){
        if(player == 0)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/blueHorizontalWall.png")));
        else if(player == 1)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/redHorizontalWall.png")));
        else if(player == 2)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/greenHorizontalWall.png")));
        else if(player == 3)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/yellowHorizontalWall.png")));
        else
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/emptyHorizontalWall.png")));
    }
    public void setVerticalWallIcon(VerticalWallButton btn, int player){
        if(player == 0)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/blueVerticalWall.png")));
        else if(player == 1)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/redVerticalWall.png")));
        else if(player == 2)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/greenVerticalWall.png")));
        else if(player == 3)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/yellowVerticalWall.png")));
        else
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/emptyVerticalWall.png")));
    }
    public void setTileIcon(SquareButton btn, int player){
        if(player == 0)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/blue space.png")));
        else if(player == 1)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/red space.png")));
        else if(player == 2)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/green space.png")));
        else if(player == 3)
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/yellow space.png")));
        else
            btn.setIcon(new ImageIcon(GameClientGUI.class.getResource("/quoridor images/empty space.png")));
    }
    
    public void nextTurn(){
    	lblCurrentPlayer.setText("Current Player: "+(players.getCurrentPlayerID()+1));
		setTileIcon(btnCurrentPlayer , players.getCurrentPlayerID()) ;
		players.nextPlayer();

    }
    
    public boolean handleMove(String move){
    	/*
    	 * MOVE <op> <location-1> <location-2>

    Message returned by move-server with its move. The <op> is a move
    operation code of M (for (m)ove pawn) or W (for place (w)all). The
    locations are each a (<row>, <column>) pair; the parentheses are
    part of a location.

    For an M move, <location-1> is where the move-server's pawn starts
    and <location-2> is where the move-server's pawn ends the move.
    Nothing special is required to indicate a jump move.

    For a W move, <location-1> is the top or left end of the wall
    placed and <location-2> is the bottom or right end.
    	 */
    	
    	String[] moveParts = move.split(" ");
    	String op = moveParts[1];
    	String location1 = moveParts[2];
    	String location2 = moveParts[3];
    	String loc1Row = location1.split(",")[0];
    	String loc2Row = location2.split(",")[0];
    	String loc1Col = location1.split(",")[1];
    	String loc2Col = location2.split(",")[1];
    	
    	if(op.equalsIgnoreCase("m"))
    		handleSquareButtonPress(tiles[Integer.parseInt(loc2Col)][Integer.parseInt(loc2Row)]);
    	
    	return true;
    }
    
    /**
     * Main method that will start the gui client
     * @param args
     */
    public static void main (String args[]){
    	
    	
    	String []greetings = {"QUORIDOR <4> <0>", "QUORIDOR <4> <3>", "QUORIDOR <4> <1>", "QUORIDOR <4> <2>"};
    	String fromServer = null; 
    	boolean gameOver = false;
    	  
    	  Socket []clientSockets = new Socket[4];
		try {
			clientSockets[0] = new Socket(args[0], 6666);

		
    	  DataOutputStream outToServer = new DataOutputStream(clientSockets[0].getOutputStream());
    	 // BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSockets[0].getInputStream()));
    	  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(System.in));
    	  
    	  
    	  outToServer.writeBytes(greetings[0]);
    	  do{
    	  fromServer = inFromServer.readLine();
    	  }while(!fromServer.startsWith("READY"));
    	  
    	  while(!gameOver){
    		  for(int i= 0; i<args.length; i++){
    			 outToServer.writeBytes("MOVE?\n");
    			 fromServer = inFromServer.readLine();
    			 if(!fromServer.startsWith("MOVE"))
    				 ;//reject player 
    			 else
    				 handleMove(fromServer);
    			 for(int j = 0; i<args.length; j++)
    				 outToServer.writeBytes("MOVED <"+i+"> "+fromServer.substring(fromServer.indexOf('<'), fromServer.length()));
    			 
    		  }  
    	  }
    	  
    	  
    	  
    	  clientSockets[0].close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}
