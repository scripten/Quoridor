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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import GameObjects.Coordinates;
import GameObjects.Players;

public class BoardGUI extends JFrame implements MouseListener{
	private SquareButton [][]tiles = new SquareButton[9][9];  // [columns][rows]
	private GridBagConstraints[][] squareGridBags= new GridBagConstraints[9][9];
	private VerticalWallButton[][] verticalWalls = new VerticalWallButton[8][9];
	private GridBagConstraints[][] verticalWallGridBags= new GridBagConstraints[8][9];
	private HorizontalWallButton[][] horizontalWalls = new HorizontalWallButton[9][8];
	private GridBagConstraints[][] horizontalWallGridBags= new GridBagConstraints[9][8];

	private Board board = new Board();
	private Players players = new Players(false);
	private boolean playGame;
	private boolean playCPU;

	public BoardGUI() {
		setResizable(false);
		setSize(570, 540);
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
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

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
		
		playGame = true;
		playCPU = true;
	}

	public void handleSquareButtonPress(SquareButton btn){
		Coordinates currentCoordinates;
		Coordinates newCoordinates;

		if (playGame && btn.isValidated()) {
			currentCoordinates = players.getCurrentPlayer().getCoordinates();

			newCoordinates = new Coordinates();
			newCoordinates.setRow(btn.getRow());
			newCoordinates.setColumn(btn.getColumn());
			
			btn.setUsed(true);
			
			if(players.getCurrentPlayerID() == 0)
				btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blue space.png")));
			else if(players.getCurrentPlayerID() == 1)
				btn.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/red space.png")));
			

			tiles[currentCoordinates.getColumn()][currentCoordinates.getRow()].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/empty space.png")));
			tiles[currentCoordinates.getColumn()][currentCoordinates.getRow()].setUsed(false);
			tiles[currentCoordinates.getColumn()][currentCoordinates.getRow()].setInvalidated();
			
			board.setUnoccupied(currentCoordinates);
			board.setOccupied(newCoordinates);

			players.getCurrentPlayer().move(newCoordinates);
			
			if (players.isWinner()) {
				playGame = false;

				JOptionPane.showMessageDialog(this, String.format("Player %s has won the game.", players.getCurrentPlayerID() + 1));
				System.out.format("Player %s has won the game.", players.getCurrentPlayerID() + 1);
			} else {
				players.nextPlayer();
				
				if (playCPU) 
					CPUTurn();
			}
		}
		
	}

	public void handleVerticalWallPress(VerticalWallButton vertWall) {
		
		if (playGame && vertWall.isValidated()) {
			board.setVerticalWall(vertWall.getRow(), vertWall.getColumn());
			
			players.getCurrentPlayer().useWall();
			
			if(players.getCurrentPlayerID() == 0)
				vertWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blueVerticalWall.png")));
			else if (players.getCurrentPlayerID() == 1)
				vertWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/redVerticalWall.png")));
	
			vertWall.setUsed(true);
			
			// and also update the vertical wall in the row beneath it
			if(players.getCurrentPlayerID() == 0)
				verticalWalls[vertWall.getColumn()][vertWall.getRow() + 1].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blueVerticalWall.png")));
			else if (players.getCurrentPlayerID() == 1)
				verticalWalls[vertWall.getColumn()][vertWall.getRow() + 1].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/redVerticalWall.png")));
	
			verticalWalls[vertWall.getColumn()][vertWall.getRow() + 1].setUsed(true);
			
			players.nextPlayer();

			if (playCPU) 
				CPUTurn();
		} 
	}

	public void handleHorizontalWallPress(HorizontalWallButton horizWall){
		
		if(playGame && horizWall.isValidated()) {
			board.setHorizontalWall(horizWall.getRow(), horizWall.getColumn());
			
			players.getCurrentPlayer().useWall();
			
			if (players.getCurrentPlayerID() == 0)
				horizWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blueHorizontalWall.png")));
			else if(players.getCurrentPlayerID() == 1)
				horizWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/redHorizontalWall.png")));
			
			horizWall.setUsed(true);
			
			if (players.getCurrentPlayerID() == 0)
				horizontalWalls[horizWall.getColumn() + 1][horizWall.getRow()].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blueHorizontalWall.png")));
			else if (players.getCurrentPlayerID() == 1)
				horizontalWalls[horizWall.getColumn() + 1][horizWall.getRow()].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/redHorizontalWall.png")));

			horizontalWalls[horizWall.getColumn() + 1][horizWall.getRow()].setUsed(true);
			
			players.nextPlayer();

			if (playCPU) 
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
				
				if(players.getCurrentPlayerID()==0)
				tile.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blue space.png")));
				else if(players.getCurrentPlayerID()==1)
					tile.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/red space.png")));

			} 
			
		} else if(e.getSource() instanceof VerticalWallButton) {
			VerticalWallButton verticalWall = (VerticalWallButton)e.getSource();
			
			if(players.getCurrentPlayer().hasAvailableWalls() && !verticalWall.getUsed() && board.isValidWallPlacement(verticalWall.getRow(), verticalWall.getColumn(), WALL_TYPE.VERTICAL)) {
				verticalWall.setValidated();
				
				// highlight the places where the wall would be placed
				if(!verticalWall.getUsed()){
					if(players.getCurrentPlayerID()==0)
					verticalWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blueVerticalWall.png")));
					else if(players.getCurrentPlayerID()==1)
						verticalWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/redVerticalWall.png")));
				}

				if(verticalWall.getRow()<8){
					VerticalWallButton lowerWall = verticalWalls[verticalWall.getColumn()][verticalWall.getRow()+1];
					if(!lowerWall.getUsed()){
						if(players.getCurrentPlayerID()==0)
						lowerWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blueVerticalWall.png")));
						else if(players.getCurrentPlayerID()==1)
							lowerWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/redVerticalWall.png")));

					}
				}
			}
			
		} else if(e.getSource() instanceof HorizontalWallButton){
			HorizontalWallButton horizontalWall = (HorizontalWallButton)e.getSource();

			if(players.getCurrentPlayer().hasAvailableWalls() && !horizontalWall.getUsed() && board.isValidWallPlacement(horizontalWall.getRow(), horizontalWall.getColumn(), WALL_TYPE.HORIZONTAL)) {
				horizontalWall.setValidated();
				
				// highlight the places where the wall would be placed
				if(!horizontalWall.getUsed()){
					if(players.getCurrentPlayerID()==0)
					horizontalWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blueHorizontalWall.png")));
					else if (players.getCurrentPlayerID()==1)
						horizontalWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/redHorizontalWall.png")));

				}
				if(horizontalWall.getColumn()<8){
					HorizontalWallButton rightWall = horizontalWalls[horizontalWall.getColumn()+1][horizontalWall.getRow()];
					if(!rightWall.getUsed()){
						if(players.getCurrentPlayerID()==0)
						rightWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blueHorizontalWall.png")));
						else if(players.getCurrentPlayerID()==1)
							rightWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/redHorizontalWall.png")));

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
					tile.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/empty space.png")));
			}
		} else if(e.getSource() instanceof VerticalWallButton){
			VerticalWallButton verticalWall = (VerticalWallButton)e.getSource();
			
			if (verticalWall.isValidated()) {
				// highlight the places where the wall would be placed

				verticalWall.setInvalidated();
			
				if(!verticalWall.getUsed())
					verticalWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/emptyVerticalWall.png")));
				if(verticalWall.getRow()<8){
					VerticalWallButton lowerWall = verticalWalls[verticalWall.getColumn()][verticalWall.getRow()+1];
					if(!lowerWall.getUsed())
						lowerWall.setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/emptyVerticalWall.png")));
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

	
	public void CPUTurn() {
		Node<State> curState = new Node<State>(null);
		
		curState.setState(new State(board, players.getCurrentPlayer()));
		
		GoalEval goal = new GoalEval();
		StateGen stateGen = new StateGen();
		StateHeuristic heuristic = new StateHeuristic();
		Node<State> nextMove;
		
		nextMove = Search.aStar(curState, goal, stateGen, heuristic);
		
	
		while (nextMove.getParent() != curState) 
			nextMove = nextMove.getParent();
		

		System.out.format("AI move (%d, %d)\n", 
				nextMove.getState().getPawn().getCoordinates().getRow(), 
				nextMove.getState().getPawn().getCoordinates().getColumn());
			
		if (board.isValidMove(players.getCurrentPlayer().getCoordinates(), nextMove.getState().getPawn().getCoordinates())) {
			

			System.out.format("Path cost: %d\n", nextMove.getCost());
			System.out.println("Valid AI move");
			
			if(players.getCurrentPlayerID() == 0)
	
				tiles[nextMove.getState().getPawn().getCoordinates().getColumn()][nextMove.getState().getPawn().getCoordinates().getRow()].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/blue space.png")));
			else if(players.getCurrentPlayerID() == 1)
				tiles[nextMove.getState().getPawn().getCoordinates().getColumn()][nextMove.getState().getPawn().getCoordinates().getRow()].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/red space.png")));
			
			tiles[players.getCurrentPlayer().getCoordinates().getColumn()][players.getCurrentPlayer().getCoordinates().getRow()].setIcon(new ImageIcon(BoardGUI.class.getResource("/quoridor images/empty space.png")));
			tiles[players.getCurrentPlayer().getCoordinates().getColumn()][players.getCurrentPlayer().getCoordinates().getRow()].setUsed(false);
			tiles[players.getCurrentPlayer().getCoordinates().getColumn()][players.getCurrentPlayer().getCoordinates().getRow()].setInvalidated();
			
			board.setUnoccupied(players.getCurrentPlayer().getCoordinates());
			board.setOccupied(nextMove.getState().getPawn().getCoordinates());

			players.getCurrentPlayer().move(nextMove.getState().getPawn().getCoordinates());
			
			
			if (players.isWinner()) {
				playGame = false;

				JOptionPane.showMessageDialog(this, String.format("Player %s has won the game.", players.getCurrentPlayerID() + 1));

				System.out.format("Player %s has won the game.\n", players.getCurrentPlayerID() + 1);

			} else 
				players.nextPlayer();
		} else 
			System.out.println("Invalid AI move");
		
	}
	
}
