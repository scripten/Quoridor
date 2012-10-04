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
	private int currentRow = 8;
	private int currentColumn = 4;
	private SquareButton [][]tiles = new SquareButton[9][9];  // [columns][rows]
	private GridBagConstraints[][] squareGridBags= new GridBagConstraints[9][9];
	private VerticalWallButton[][] verticalWalls = new VerticalWallButton[8][9];
	private GridBagConstraints[][] verticalWallGridBags= new GridBagConstraints[8][9];
	private HorizontalWallButton[][] horizontalWalls = new HorizontalWallButton[9][8];
	private GridBagConstraints[][] horizontalWallGridBags= new GridBagConstraints[9][8];


	private Board board = new Board();
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
				tiles[column][row] = new SquareButton("", column, row);
				tiles[column][row].setMargin(new Insets(0, 0, 0, 0));
				tiles[column][row].setAlignmentX(Component.CENTER_ALIGNMENT);
				tiles[column][row].setSize(new Dimension(50, 50));
				tiles[column][row].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/empty space.png")));
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
		// before the user can see the board, set their starting piece on the board
		tiles[4][8].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blue space.png")));
		tiles[4][8].setUsed(true);
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
		newCoordinates.setRow(btn.getRow());
		newCoordinates.setColumn(btn.getColumn());


		System.out.println("Current pawn coordiates: " + currentCoordinates.getRow() + " " + currentCoordinates.getColumn());
		System.out.println("Coordiates to move to: " + newCoordinates.getRow() + " " + newCoordinates.getColumn());

		if (board.isValidMove(currentCoordinates, newCoordinates)) {
			System.out.println("VALID MOVE");

			players.getCurrentPlayer().move(newCoordinates);
			btn.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blue space.png")));
			tiles[currentColumn][currentRow].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/empty space.png")));
			currentColumn = btn.getColumn();
			currentRow = btn.getRow();
			return;

			// players.nextPlayer();
		} else {
			System.out.println("INVALID MOVE");
			return;
		}

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// END TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		
	}

	public void handleVerticalWallPress(VerticalWallButton vertWall){

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// START TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		/*if(!board.setVerticalWall(vertWall.getRow(), vertWall.getColumn()))
				System.out.println(vertWall.getRow() + " " + vertWall.getColumn() + " : INVALID WALL");*/


		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// END TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		if(vertWall.getRow()==8)	//FIXME don't allow user to click vertical walls in bottom most column.
			return;					// is there a better way to handle this?
		
		//if(isLegalMove(vertWall)){
			// update the wall the user clicked
		if(board.setVerticalWall(vertWall.getRow(), vertWall.getColumn())) {
			vertWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueVerticalWall.png")));
			vertWall.setUsed(true);
			// and also update the vertical wall in the row beneath it
			verticalWalls[vertWall.getColumn()][vertWall.getRow()+1].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueVerticalWall.png")));
			verticalWalls[vertWall.getColumn()][vertWall.getRow()+1].setUsed(true);
		} else
			System.out.println(vertWall.getRow() + " " + vertWall.getColumn() + " : INVALID WALL");
	}

	public void handleHorizontalWallPress(HorizontalWallButton horizWall){
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// START TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		/*if(!board.setHorizontalWall(horizWall.getRow(), horizWall.getColumn()))
			System.out.println(horizWall.getRow() + " " + horizWall.getColumn() + " : INVALID WALL");*/

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// END TEST
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		if(horizWall.getColumn()==8)  	//FIXME don't allow user to click horizontal walls in right most column.
			return;						// is there a better way to handle this?

		//if(isLegalMove(horizWall)){
		if(board.setHorizontalWall(horizWall.getRow(), horizWall.getColumn())) {
			horizWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueHorizontalWall.png")));
			horizWall.setUsed(true);
			horizontalWalls[horizWall.getColumn()+1][horizWall.getRow()].setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueHorizontalWall.png")));
			horizontalWalls[horizWall.getColumn()+1][horizWall.getRow()].setUsed(true);
		} else
			System.out.println(horizWall.getRow() + " " + horizWall.getColumn() + " : INVALID WALL");
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
			
			if(verticalWall.getRow()==8)
				return;
			
			if(isLegalMove(verticalWall)){
				// highlight the places where the wall would be placed
				if(!verticalWall.getUsed())
					verticalWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueVerticalWall.png")));
				if(verticalWall.getRow()<8){
					VerticalWallButton lowerWall = verticalWalls[verticalWall.getColumn()][verticalWall.getRow()+1];
					if(!lowerWall.getUsed())
						lowerWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueVerticalWall.png")));
				}
			}
		}
		if(e.getSource() instanceof HorizontalWallButton){
			HorizontalWallButton horizontalWall = (HorizontalWallButton)e.getSource();
			if(horizontalWall.getColumn()==8)
				return;
			if(isLegalMove(horizontalWall)){
				// highlight the places where the wall would be placed
				if(!horizontalWall.getUsed())
					horizontalWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueHorizontalWall.png")));
				if(horizontalWall.getColumn()<8){
					HorizontalWallButton rightWall = horizontalWalls[horizontalWall.getColumn()+1][horizontalWall.getRow()];
					if(!rightWall.getUsed())
						rightWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/blueHorizontalWall.png")));
				}
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
			
			if(verticalWall.getRow()==8)
				return;
			if(isLegalMove(verticalWall)){
				// highlight the places where the wall would be placed

				if(!verticalWall.getUsed())
					verticalWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/emptyVerticalWall.png")));
				if(verticalWall.getRow()<8){
					VerticalWallButton lowerWall = verticalWalls[verticalWall.getColumn()][verticalWall.getRow()+1];
					if(!lowerWall.getUsed())
						lowerWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/emptyVerticalWall.png")));
				}
			}
		}
		if(e.getSource() instanceof HorizontalWallButton){
			HorizontalWallButton horizontalWall = (HorizontalWallButton)e.getSource();
			if(horizontalWall.getRow()==8)
				return;
			if(isLegalMove(horizontalWall)){
				// highlight the places where the wall would be placed
				if(!horizontalWall.getUsed())
					horizontalWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/emptyHorizontalWall.png")));
				if(horizontalWall.getColumn()<8){
					HorizontalWallButton rightWall = horizontalWalls[horizontalWall.getColumn()+1][horizontalWall.getRow()];
					if(!rightWall.getUsed())
						rightWall.setIcon(new ImageIcon(GameBoard.class.getResource("/quoridor images/emptyHorizontalWall.png")));
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
