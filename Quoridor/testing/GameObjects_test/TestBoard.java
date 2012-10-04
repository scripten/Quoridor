package GameObject_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import GameObjects.Board;

public class TestBoard {
	Board board;
	
	@Before
	public void setUp() {
		board = new Board();
	}
	
	@Test
	public void testsetUnoccipued() {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board.setUnoccupied(i, j);
				assertTrue(!board.isOccupied(i, j));
			}
		}
	}
	
	@Test
	public void testSetOccupied() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board.setOccupied(i, j);
				assertTrue(board.isOccupied(i, j));
			}
		}
	}
	
	@Test
	public void testSetWalls() {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board.setHorizontalWall(i, j);
				board.setVerticalWall(i, j);
				//board.getTile(i, j).getBottomWall().
				//assertTrue(board.getTile(i, j).getBottomWall().isWall());
				
			}
		}
		
		
	}
	
	
}