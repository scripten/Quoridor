package GUI;

import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Font;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.GroupLayout.Alignment;



/**
 * For now this is the sole class containing code for the 
 * Quoridor GUI.
 * <br><br>
 * Note*:  A large part of this code was generated by the 
 * WindowBuilder plugin for Eclipse
 * 
 * @author Nick
 *
 */
public class FirstWindow extends JFrame{
	public final static String MAIN_WINDOW_TITLE = "FirstWindow";
	public final static String MENU_BAR_NAME = "MenuBar";
	public final static String LABEL = "Hello";
	
	public final static String BUTTON_TEXT = "New Game";
	public final static String LABEL_NAME = "lblWelcome";

	// messages displayed by the label
	public static final String INITIAL_MESSAGE = "Welcome To Quoridor!";
	
	public final static String NEW_GAME_MESSAGE = "This will eventually start a new game!";

	private boolean visible;
	
	public FirstWindow() {
		
		super();
		visible = true;
		setName(MAIN_WINDOW_TITLE);
		setTitle(MAIN_WINDOW_TITLE);
		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Adding a menu bar that appears at the top of the window
		JMenuBar menuBar = new JMenuBar();
		menuBar.setName(MENU_BAR_NAME);
		setJMenuBar(menuBar);
		
		// Adding an menu called "File" to the menu bar
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		// Adding a 'New Game' menu item to the 'File' menu
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.addMouseListener(new MouseAdapter() {
			// Add actions to be preformed when the item is clicked here
			@Override
			public void mousePressed(MouseEvent e) {
				// Close the welcome menu and bring up the game board
				dispose();
				GameBoard gameBoard = new GameBoard();	
			}
		});
		mntmNewGame.setHorizontalAlignment(SwingConstants.LEFT);
		mnFile.add(mntmNewGame);
		
		
		// Adding a  'Save' menu item to the 'File' menu
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		// Adding a 'Help' menu to the menu bar
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		// Adding a 'QuoridorWiki' menu item to the 'Help' menu
		JMenuItem mntmQuoridorWiki = new JMenuItem("Quoridor Wiki");
		mntmQuoridorWiki.setName("Quoridor Wiki");
		mntmQuoridorWiki.addMouseListener(new MouseAdapter() {
			// the on mouse press event
			@Override
			public void mousePressed(MouseEvent e) {
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
		
		// Adding a button to the frame
		JButton btnNewGame = new JButton("New Game"); // sets the text
		btnNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Close the welcome menu and bring up the game board
				dispose();
				GameBoard gameBoard = new GameBoard();	
			}
		});
		btnNewGame.setName("btnNewGame");
		
		// Adding a welcome label to the frame
		JLabel lblWelcomeToQuoridor = new JLabel(INITIAL_MESSAGE);
		lblWelcomeToQuoridor.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWelcomeToQuoridor.setName(LABEL_NAME);
		
		// set the layout of the frame to be a GroupLayout and arrange the components
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(237)
							.addComponent(btnNewGame, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(214)
							.addComponent(lblWelcomeToQuoridor)))
					.addContainerGap(214, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWelcomeToQuoridor)
					.addGap(32)
					.addComponent(btnNewGame, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(317, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	
		// make everything visible
		setVisible(visible);
	}
}
