package GUI;

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
import java.util.ArrayList;
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
import GameObjects.Board;
import GameObjects.Board.WALL_TYPE;
import GameObjects.Pawn.DESTINATION;
import GameObjects.Coordinates;
import GameObjects.Players;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class BoardGUI extends JFrame implements MouseListener{
	private SquareButton [][]tiles = new SquareButton[9][9];  // [columns][rows]
	private GridBagConstraints[][] squareGridBags= new GridBagConstraints[9][9];
	private VerticalWallButton[][] verticalWalls = new VerticalWallButton[8][9];
	private GridBagConstraints[][] verticalWallGridBags= new GridBagConstraints[8][9];
	private HorizontalWallButton[][] horizontalWalls = new HorizontalWallButton[9][8];
	private GridBagConstraints[][] horizontalWallGridBags= new GridBagConstraints[9][8];

	private Board board = new Board();
	private Players players ;
	private boolean playGame;
	private boolean playCPU;
	private JLabel lblPlayer1Walls;
	private JLabel lblPlayer2Walls;
	private JLabel lblPlayer3Walls;
	private JLabel lblPlayer4Walls;
	private JLabel lblCurrentPlayer;
	private SquareButton btnCurrentPlayer;

	public BoardGUI(boolean playCPU, boolean fourPlayers) {
		players = new Players(fourPlayers);
		playGame = true;
		this.playCPU = playCPU;
	
		
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
				tiles[column][row].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/empty space.png")));
				tiles[column][row].addMouseListener(this);
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
					verticalWalls[column][row].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/emptyVerticalWall.png")));
					verticalWalls[column][row].addMouseListener(this);
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
					horizontalWalls[column][row].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/emptyHorizontalWall.png")));
					horizontalWalls[column][row].addMouseListener(this);
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
		tiles[4][8].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blue space.png")));
		tiles[4][8].setUsed(true);
		setVisible(true);
		
		// Top row player
		tiles[4][0].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/red space.png")));
		tiles[4][0].setUsed(true);
		setVisible(true);
		if(players.getNumberOfPlayers()==4){
		// Left column player
		tiles[0][4].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/green space.png")));
		tiles[0][4].setUsed(true);
		setVisible(true);
		
		
		// Right column player
		tiles[8][4].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/yellow space.png")));
		tiles[8][4].setUsed(true);
		setVisible(true);
		}
		
		//CPUTurn();
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
			if(playCPU && playGame)
				CPUTurn();
			
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
			if(playCPU)
				CPUTurn();
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
			if(playCPU)
				CPUTurn();
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

	@Override
	public void mouseEntered(MouseEvent e) {
		Coordinates currentCoordinates;
		Coordinates newCoordinates;

		if (!playGame)
			return;
		
		if(e.getSource() instanceof SquareButton){
			currentCoordinates = players.getCurrentPlayer().getCoordinates();

			SquareButton tile = (SquareButton) e.getSource();
	
			newCoordinates = new Coordinates();
			newCoordinates.setRow(tile.getRow());
			newCoordinates.setColumn(tile.getColumn());
			
			if (!tile.getUsed() && board.isValidMove(currentCoordinates, newCoordinates)) {
				tile.setValidated();
                setTileIcon(tile, players.getCurrentPlayerID());
			} 
			
		} else if(e.getSource() instanceof VerticalWallButton) {
			VerticalWallButton verticalWall = (VerticalWallButton)e.getSource();
			
			
			
			if(players.getCurrentPlayer().hasAvailableWalls() && !verticalWall.getUsed() && 
					board.isValidWallPlacement(verticalWall.getRow(), verticalWall.getColumn(), WALL_TYPE.VERTICAL, players.getPlayersCoordinates(), players.getPlayersDestinations())) {
				verticalWall.setValidated();
				
				// highlight the places where the wall would be placed
				if(!verticalWall.getUsed()){
                    setVerticalWallIcon(verticalWall , players.getCurrentPlayerID())  ;
				}

				if(verticalWall.getRow()<8){
					VerticalWallButton lowerWall = verticalWalls[verticalWall.getColumn()][verticalWall.getRow()+1];
					if(!lowerWall.getUsed()){
                        setVerticalWallIcon(lowerWall , players.getCurrentPlayerID())  ;
					}
				}
			}
			
		} else if(e.getSource() instanceof HorizontalWallButton){
			HorizontalWallButton horizontalWall = (HorizontalWallButton)e.getSource();

			if(players.getCurrentPlayer().hasAvailableWalls() && !horizontalWall.getUsed() && 
					board.isValidWallPlacement(horizontalWall.getRow(), horizontalWall.getColumn(), WALL_TYPE.HORIZONTAL, players.getPlayersCoordinates(), players.getPlayersDestinations())) {
				horizontalWall.setValidated();
				
				// highlight the places where the wall would be placed
				if(!horizontalWall.getUsed()){
                    setHorizontalWallIcon(horizontalWall , players.getCurrentPlayerID())  ;
				}
				if(horizontalWall.getColumn()<8){
					HorizontalWallButton rightWall = horizontalWalls[horizontalWall.getColumn()+1][horizontalWall.getRow()];
					if(!rightWall.getUsed()){
                        setHorizontalWallIcon(rightWall, players.getCurrentPlayerID())  ;
					}
				}
			}
		}
	}



	@Override
	public void mouseExited(MouseEvent e) {
	
		// TODO Auto-generated method stub

		if (!playGame)
			return;
		
		if (e.getSource() instanceof SquareButton){
			SquareButton tile = (SquareButton) e.getSource();
			if (tile.isValidated()){
		
				tile.setInvalidated();
				if (!tile.getUsed())
					setTileIcon(tile , -1);
			}
		} else if(e.getSource() instanceof VerticalWallButton){
			VerticalWallButton verticalWall = (VerticalWallButton)e.getSource();
			
			if (verticalWall.isValidated()) {
				// highlight the places where the wall would be placed

				verticalWall.setInvalidated();
			
				if(!verticalWall.getUsed())
					setVerticalWallIcon(verticalWall, -1);
				if(verticalWall.getRow()<8){
					VerticalWallButton lowerWall = verticalWalls[verticalWall.getColumn()][verticalWall.getRow()+1];
					if(!lowerWall.getUsed())
						setVerticalWallIcon(lowerWall, -1) ;
				}
			}
		} else if(e.getSource() instanceof HorizontalWallButton){
			HorizontalWallButton horizontalWall = (HorizontalWallButton)e.getSource();
	
			if (horizontalWall.isValidated()) {
				horizontalWall.invalidate();
			
				// highlight the places where the wall would be placed
				if(!horizontalWall.getUsed())
					horizontalWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/emptyHorizontalWall.png")));
				if(horizontalWall.getColumn()<8){
					HorizontalWallButton rightWall = horizontalWalls[horizontalWall.getColumn()+1][horizontalWall.getRow()];
					if(!rightWall.getUsed())
						rightWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/emptyHorizontalWall.png")));
				}
			}
		}
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public boolean CPUWall() {
		ArrayList<Node<State>> curStates = new ArrayList<Node<State>>(4);
		ArrayList<Coordinates> playersCoords = players.getPlayersCoordinates();
		ArrayList<DESTINATION> playersDests = players.getPlayersDestinations();
		ArrayList<Node<State>> nextPlayersMoves = new ArrayList<Node<State>>(4);
		
		int bestMoveIndex = -1;
		
		Node<State> nextMove;
		Coordinates wallCoord;
		
		boolean placedWall = false;
		
		boolean placeHorzWall = false;
		boolean placeVertWall = false;
		
		System.out.println("Determining if AI should place a wall.");
		
		if (!players.getCurrentPlayer().hasAvailableWalls())
			return false;
		
		// Get the shortest path for all players
		for (int i = 0; i < players.getNumberOfPlayers(); i++) {
			curStates.add(new Node<State>(new State(board, playersCoords.get(i), playersDests.get(i))));
			nextPlayersMoves.add(Search.aStar(curStates.get(i), new GoalEval(), new StateGen(), new StateHeuristic()));
			
			System.out.format("Player%d's shortest path cost is %d.\n", i + 1, nextPlayersMoves.get(i).getCost());
		}
		
		for (int i = 0; i < players.getNumberOfPlayers(); i++) {
			// Determine if another player will reach his or her destination before the current player
			if (players.getCurrentPlayerID() != i && nextPlayersMoves.get(players.getCurrentPlayerID()).getCost() > curStates.get(i).getCost()) {
				
				if (bestMoveIndex == -1)
					bestMoveIndex = i;
				else if (curStates.get(bestMoveIndex).getCost() > curStates.get(i).getCost())
					bestMoveIndex = i;
				
				System.out.format("Player%d with path cost %d will reach his or her destination before me, player%d with path cost %d.\n", 
						i + 1, nextPlayersMoves.get(i).getCost(), players.getCurrentPlayerID() + 1, nextPlayersMoves.get(players.getCurrentPlayerID()).getCost());
		
			}
		}
		
		if (bestMoveIndex > -1 && nextPlayersMoves.get(bestMoveIndex) != null) {
			nextMove = nextPlayersMoves.get(bestMoveIndex);
			
			while (nextMove.getParent() != curStates.get(bestMoveIndex)) {
				System.out.println("Next move: " + nextMove.getState().getCoordinates().toString());
			
				nextMove = nextMove.getParent();
			}
			
			if (nextMove.getState().getDestination() == DESTINATION.FIRST_ROW)
				wallCoord = nextMove.getState().getCoordinates();
			else if (nextMove.getState().getDestination() == DESTINATION.LAST_ROW) 
				wallCoord = curStates.get(bestMoveIndex).getState().getCoordinates();
			else if (nextMove.getState().getDestination() == DESTINATION.FIRST_COLUMN) 
				wallCoord = nextMove.getState().getCoordinates();
			else 
				wallCoord = curStates.get(bestMoveIndex).getState().getCoordinates();
			
			if (wallCoord.getRow() == 8)
				wallCoord.setRow(7);
			
			if (wallCoord.getColumn() == 8)
				wallCoord.setColumn(7);
			
			System.out.format("AI placing wall at %s\n", wallCoord.toString());
			
			System.out.println("Current position: " + curStates.get(bestMoveIndex).getState().getCoordinates().toString());
			System.out.println("Next move: " + nextMove.getState().getCoordinates().toString());
			
			
			
			
			if (nextMove.getState().getCoordinates().getRow() == curStates.get(bestMoveIndex).getState().getCoordinates().getRow() - 1) { // North
				placeHorzWall = true;
			} else if (nextMove.getState().getCoordinates().getRow() == curStates.get(bestMoveIndex).getState().getCoordinates().getRow() + 1) { // South
				placeHorzWall = true;
				
			} else if (nextMove.getState().getCoordinates().getColumn() == curStates.get(bestMoveIndex).getState().getCoordinates().getColumn() - 1) { // West
				placeVertWall = true;
			} else if (nextMove.getState().getCoordinates().getRow() == curStates.get(bestMoveIndex).getState().getCoordinates().getColumn() + 1) { // East
				placeVertWall = true;
			}
			
			
			if (placeHorzWall) {
				
				if (wallCoord.getColumn() < 7 && wallCoord.getColumn() > 0 && (board.hasBottomWall(wallCoord.getRow(), wallCoord.getColumn()+1) ||
						board.hasTopWall(wallCoord.getRow(), wallCoord.getColumn()+1) ||
						board.hasLeftWall(wallCoord.getRow(), wallCoord.getColumn()+1) ||
						board.hasRightWall(wallCoord.getRow(), wallCoord.getColumn()+1))) 
					wallCoord.setColumn(wallCoord.getColumn() - 1);
			} else {
				if (wallCoord.getRow() < 7 && wallCoord.getRow() > 0 && (board.hasBottomWall(wallCoord.getRow(), wallCoord.getColumn()+1) ||
						board.hasTopWall(wallCoord.getRow(), wallCoord.getColumn()+1) ||
						board.hasLeftWall(wallCoord.getRow(), wallCoord.getColumn()+1) ||
						board.hasRightWall(wallCoord.getRow(), wallCoord.getColumn()+1))) 
					wallCoord.setColumn(wallCoord.getRow() - 1);
			}
			
			System.out.format("AI placing wall at %s\n", wallCoord.toString());
			
			// Set horizontal wall
			if (placeHorzWall && board.isValidWallPlacement(wallCoord.getRow(), wallCoord.getColumn(), WALL_TYPE.HORIZONTAL, playersCoords, playersDests)) {
				placedWall = true;
				System.out.println("AI palced a horizontal wall.");
				board.setHorizontalWall(wallCoord.getRow(), wallCoord.getColumn());
				players.getCurrentPlayer().useWall();
				
				horizontalWalls[wallCoord.getColumn()][wallCoord.getRow()].setUsed(true);
				horizontalWalls[wallCoord.getColumn()+1][wallCoord.getRow()].setUsed(true);
				
                setHorizontalWallIcon(horizontalWalls[wallCoord.getColumn()][wallCoord.getRow()], players.getCurrentPlayerID())  ;
                setHorizontalWallIcon(horizontalWalls[wallCoord.getColumn()+1][wallCoord.getRow()], players.getCurrentPlayerID())  ;
			} else if (placeVertWall && board.isValidWallPlacement(wallCoord.getRow(), wallCoord.getColumn(), WALL_TYPE.VERTICAL, playersCoords, playersDests)) { // Set vertical wall
				placedWall = true;
				System.out.println("AI placed a vertical wall.");
				board.setVerticalWall(wallCoord.getRow(), wallCoord.getColumn());
				players.getCurrentPlayer().useWall();
				
				verticalWalls[wallCoord.getColumn()][wallCoord.getRow()].setUsed(true);
				verticalWalls[wallCoord.getColumn()][wallCoord.getRow() + 1].setUsed(true);
				
				setVerticalWallIcon(verticalWalls[wallCoord.getColumn()][wallCoord.getRow()], players.getCurrentPlayerID())  ;
                setVerticalWallIcon(verticalWalls[wallCoord.getColumn()][wallCoord.getRow() + 1], players.getCurrentPlayerID())  ;
			} else
				System.out.println("AI generated an invalid wall placement.");
			
		
		}
		
			
		
		return placedWall;
		
	}
	
	public void CPUTurn() {
		//if (players.getCurrentPlayerID() != 3)
		//	return;

		Node<State> curState = new Node<State>(null);
		if (!CPUWall()) {
			
		
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
			
			// Run AI on every pawn
			// If another pawn is closer to its destination, place wall to block path
			// If other pawn is same distance away from goal, but next move would be a win, place a wall
			
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
					return;
				} 
				
				
			} else 
				System.out.println("Invalid AI move");
		}
		players.nextPlayer();
		
		if (players.getCurrentPlayerID() !=0){
			CPUTurn();
			lblCurrentPlayer.setText("Current Player: "+(players.getCurrentPlayerID()+1));
			setTileIcon(btnCurrentPlayer , players.getCurrentPlayerID()) ;
		}
			
	}

    public void setHorizontalWallIcon(HorizontalWallButton btn, int player){
    	
    	
        if(player == 0)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blueHorizontalWall.png")));
        else if(player == 1)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/redHorizontalWall.png")));
        else if(player == 2)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/greenHorizontalWall.png")));
        else if(player == 3)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/yellowHorizontalWall.png")));
        else
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/emptyHorizontalWall.png")));
    }
    public void setVerticalWallIcon(VerticalWallButton btn, int player){
  
        if(player == 0)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blueVerticalWall.png")));
        else if(player == 1)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/redVerticalWall.png")));
        else if(player == 2)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/greenVerticalWall.png")));
        else if(player == 3)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/yellowVerticalWall.png")));
        else
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/emptyVerticalWall.png")));
    }
    public void setTileIcon(SquareButton btn, int player){

        if(player == 0)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blue space.png")));
        else if(player == 1)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/red space.png")));
        else if(player == 2)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/green space.png")));
        else if(player == 3)
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/yellow space.png")));
        else
            btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/empty space.png")));
    }
    
    public void nextTurn(){
    	lblCurrentPlayer.setText("Current Player: "+(players.getCurrentPlayerID()+1));
		setTileIcon(btnCurrentPlayer , players.getCurrentPlayerID()) ;
		players.nextPlayer();

		if (playCPU){ 
			CPUTurn();
			lblCurrentPlayer.setText("Current Player: "+(players.getCurrentPlayerID()+1));
			setTileIcon(btnCurrentPlayer , players.getCurrentPlayerID()) ;
		}
    }	
}
