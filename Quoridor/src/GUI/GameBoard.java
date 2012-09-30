package GUI;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
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

import GameObjects.Board;
import GameObjects.Coordinates;
import GameObjects.Players;

public class GameBoard extends JFrame implements MouseListener{
	private int currentRow = -1;
	private int currentColumn = -1;
	private SquareButton [][]squareSpaces = new SquareButton[9][9];  // [columns][rows]
	private GridBagConstraints[][] squareGridBags= new GridBagConstraints[9][9];
	private VerticalWallButton[][] verticalWalls = new VerticalWallButton[8][9];
	private GridBagConstraints[][] verticalWallGridBags= new GridBagConstraints[8][9];
	private HorizontalWallButton[][] horizontalWalls = new HorizontalWallButton[9][8];
	private GridBagConstraints[][] horizontalWallGridBags= new GridBagConstraints[9][8];

	
	private Board board = new Board(false);
	private Players players = new Players(false);


	public GameBoard() {
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
				squareSpaces[column][row] = new SquareButton("", column, row);
				squareSpaces[column][row].setMargin(new Insets(0, 0, 0, 0));
				squareSpaces[column][row].setAlignmentX(Component.CENTER_ALIGNMENT);
				squareSpaces[column][row].setSize(new Dimension(50, 50));
				squareSpaces[column][row].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/empty space.png")));
				squareSpaces[column][row].addMouseListener(this);
				squareSpaces[column][row].setBorderPainted(false);
				squareSpaces[column][row].setBorder(null);

				squareGridBags[column][row] =  new GridBagConstraints();
				squareGridBags[column][row].insets = new Insets(0, 0, 0, 5);
				squareGridBags[column][row].gridx = column*2;
				squareGridBags[column][row].gridy = row*2;
				getContentPane().add(squareSpaces[column][row], squareGridBags[column][row]);

				if(column!=8){
					//show an empty vertical wall
					verticalWalls[column][row] = new VerticalWallButton("", column, row);
					verticalWalls[column][row].setMargin(new Insets(0, 0, 0, 0));
					verticalWalls[column][row].setAlignmentX(Component.CENTER_ALIGNMENT);
					verticalWalls[column][row].setSize(new Dimension(50, 50));
					verticalWalls[column][row].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/emptyVerticalWall.png")));
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
					horizontalWalls[column][row].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/emptyHorizontalWall.png")));
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
		setVisible(true);
	}

	public void handleSquareButtonPress(SquareButton btn){
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// START TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		Coordinates currentCoordinates;
		Coordinates newCoordinates;
		
		currentCoordinates = players.getCurrentPlayer().getCoordinates();
		
		newCoordinates = new Coordinates();
		newCoordinates.setX(btn.getRow());
		newCoordinates.setY(btn.getColumn());
		
		System.out.println("Current pawn coordiates: " + currentCoordinates.getX() + " " + currentCoordinates.getY());
		System.out.println("Coordiates to move to: " + newCoordinates.getX() + " " + newCoordinates.getY());
		
		if (board.isValidMove(currentCoordinates, newCoordinates)) {
			System.out.println("VALID MOVE");
			
			players.getCurrentPlayer().move(newCoordinates);
			
			// players.nextPlayer();
		} else {
			System.out.println("INVALID MOVE");
		}
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// END TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		
		// TODO check if it's a valid move given the current position
		//System.out.println("Clicked square");
		if(currentColumn==-1 && currentRow==-1){
			if(btn.getRow()==8){
				//TODO first move stuff

				btn.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blue space.png")));
				currentColumn = btn.getColumn();
				currentRow = btn.getRow();
				return;
			}
		}
		
		//allow moves of North South East West from current position
		/*TODO checking for valid moves should be abstracted away from
		 * the GameBoard class.  Ideally we should be able to pass the 
		 * clicked buttons row and column into a isValidMove() method 
		 */

		//North
		if(btn.getRow()==(currentRow-1) && btn.getColumn()==currentColumn){
			btn.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blue space.png")));
			squareSpaces[currentColumn][currentRow].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/empty space.png")));
			currentColumn = btn.getColumn();
			currentRow = btn.getRow();
			return;
		}
		
		//South
		if(btn.getRow()==(currentRow+1) && btn.getColumn()==currentColumn){
			btn.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blue space.png")));
			squareSpaces[currentColumn][currentRow].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/empty space.png")));
			currentColumn = btn.getColumn();
			currentRow = btn.getRow();
			return;
		}
		
		//East
		if(btn.getColumn()==(currentColumn-1)&& btn.getRow()==currentRow){
			btn.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blue space.png")));
			squareSpaces[currentColumn][currentRow].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/empty space.png")));
			currentColumn = btn.getColumn();
			currentRow = btn.getRow();
			return;
		}
		
		//West
		if(btn.getColumn()==(currentColumn+1)&& btn.getRow()==currentRow){
			btn.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blue space.png")));
			squareSpaces[currentColumn][currentRow].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/empty space.png")));
			currentColumn = btn.getColumn();
			currentRow = btn.getRow();
			return;
		}
	}
	
	public void handleVerticalWallPress(VerticalWallButton vertWall){
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// START TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		board.setVerticalWall(vertWall.getRow(), vertWall.getColumn());
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// END TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		if(isLegalMove(vertWall)){
			vertWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueVerticalWall.png")));
			vertWall.setUsed(true);
		}
	}
	
	public void handleHorizontalWallPress(HorizontalWallButton horizWall){
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// START TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		
		board.setHorizontalWall(horizWall.getRow(), horizWall.getColumn());
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// END TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		
		if(isLegalMove(horizWall)){
			horizWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueHorizontalWall.png")));
			horizWall.setUsed(true);
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
		
		if(e.getSource() instanceof SquareButton){
			SquareButton tile = (SquareButton) e.getSource();
			if(isLegalMove(tile)){//FIXME
				tile.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blue space.png")));
			}
		}
		if(e.getSource() instanceof VerticalWallButton){
			VerticalWallButton verticalWall = (VerticalWallButton)e.getSource();
			if(isLegalMove(verticalWall)){
			verticalWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueVerticalWall.png")));
			}
		}
		if(e.getSource() instanceof HorizontalWallButton){
			HorizontalWallButton horizontalWall = (HorizontalWallButton)e.getSource();
			if(isLegalMove(horizontalWall)){
			horizontalWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueHorizontalWall.png")));
			}
		}
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() instanceof SquareButton){
			SquareButton tile = (SquareButton) e.getSource();
			if(isLegalMove(tile)){
				tile.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/empty space.png")));
			}
		}
		if(e.getSource() instanceof VerticalWallButton){
			VerticalWallButton verticalWall = (VerticalWallButton)e.getSource();
			if(isLegalMove(verticalWall)){
			verticalWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/emptyVerticalWall.png")));
			}
		}
		if(e.getSource() instanceof HorizontalWallButton){
			HorizontalWallButton horizontalWall = (HorizontalWallButton)e.getSource();
			if(isLegalMove(horizontalWall)){
			horizontalWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/emptyHorizontalWall.png")));
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
	
	/**
	 * TODO This is a placeholder, it will later be 
	 * replaced
	 * @param tile
	 * @return
	 */
	public boolean isLegalMove(JButton btnOnBoard){
		
		if(btnOnBoard instanceof SquareButton){
			SquareButton tile = (SquareButton)btnOnBoard;
		//North
		if(tile.getRow()==(currentRow-1) && tile.getColumn()==currentColumn){
			return true;
		}
		//South
		if(tile.getRow()==(currentRow+1) && tile.getColumn()==currentColumn){
			return true;
		}
		//East
		if(tile.getColumn()==(currentColumn-1)&& tile.getRow()==currentRow){
			return true;
		}
		//West
		if(tile.getColumn()==(currentColumn+1)&& tile.getRow()==currentRow){
			return true;
		}
		}else if(btnOnBoard instanceof VerticalWallButton){
			if(((VerticalWallButton)btnOnBoard).getUsed())
				return false;
			return true;
		}else if(btnOnBoard instanceof HorizontalWallButton){
			if(((HorizontalWallButton)btnOnBoard).getUsed())
				return false;
			return true;
		}
		
		
		
		
		return false;
	}


}
