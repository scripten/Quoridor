package GameObjects_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import GameObjects.Board;
import GameObjects.Coordinates;
import GameObjects.Board.WALL_TYPE;

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
				Coordinates curCoord = new Coordinates(i, j);
				board.setUnoccupied(curCoord);
				assertTrue(!board.isOccupied(i, j));
			}
		}
	}
	
	@Test
	public void testSetOccupied() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Coordinates curCoord = new Coordinates(i, j);
				board.setUnoccupied(curCoord);
				assertTrue(!board.isOccupied(i, j));
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
	
	

	
	@Test
	public void testhasBottomWall() {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board.setHorizontalWall(i, j);
				board.hasBottomWall(i, j);

				assertTrue(board.hasBottomWall(i, j));
				
			}
		}
		
		
	}
	
	@Test
	public void testhasRightWall() {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board.setVerticalWall(i, j);
				
				assertTrue(board.hasRightWall(i, j));
				
			}
		}
		
		
	}
	
	@Test
	public void testhasLeftWall() {
		
		for (int i = 0; i < 9; i++) {
		
				int p = 1;
				board.setVerticalWall(i, p++);
				
				assertTrue(board.hasLeftWall(i, p));
				
			
		}
		
		
	}
	
	@Test
	public void testhasTopWall() {
		
		for (int j = 0; j < 9; j++) {
			int t = 1;
			board.setHorizontalWall(t++, j);
				
			assertTrue(board.hasTopWall(t, j));
				
			
		}
		
		
	}
	
	@Test
	public void testisOccupied() {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Coordinates coord = new Coordinates(i, j);
				board.setOccupied(coord);
				
				assertTrue(board.isOccupied(i, j));
				
			}
		}
		
		
	}
	
	@Test
	public void testisValidMoveWithPawn() {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int p = 1;
				int n = 1;
				Coordinates coord = new Coordinates(i, j);
				Coordinates newCoord = new Coordinates (p++, n++);
				board.setOccupied(newCoord);
				board.setOccupied(coord);

				assertFalse(board.isValidMove(coord, newCoord));
				
			}
		}
		
		
	}
	
	@Test
	public void testisValidMoveWithHorizontalWall() {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int p = 1;
				Coordinates coord = new Coordinates(i, j);
				Coordinates newCoord = new Coordinates (p, j);
				board.setHorizontalWall(p, j);

				
				assertFalse(board.isValidMove(coord, newCoord));
				p++;
			}
		}
		
		
	}
	
	@Test
	public void testisValidMoveWithVerticalWall() {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int n = 1;
				Coordinates coord = new Coordinates(i, j);
				Coordinates newCoord = new Coordinates (i, n);
				board.setVerticalWall(i, j);
				
				assertFalse(board.isValidMove(coord, newCoord));
				n++;
			}
		}
		
		
	}
	
	/*@Test
	public void testisValidWallPlacement() {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int p = 1;
				Coordinates coord = new Coordinates(i, j);
				Coordinates newCoord = new Coordinates (p, j);
				board.setHorizontalWall(p, j);
				board.setOccupied(coord);
				
				assertFalse(board.isValidWallPlacement(i, j, WALL_TYPE.VERTICAL, allPlayerCoordinates, allPlayerDestinations));
				p++;
			}
		}
		
		
	}*/
	
	@Test
	public void testBoardClone() {
		Board board = new Board();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int p = 1;
				int n = 1;
				Coordinates coord = new Coordinates(i, j);
				Coordinates newCoord = new Coordinates (p++, n++);
				board.setOccupied(newCoord);
				board.setOccupied(coord);
				board.setHorizontalWall(p, j);
				board.setVerticalWall(n, j);
				Board clone = Board.clone(board);
				assertEquals(board.isOccupied(i, j), clone.isOccupied(i, j));
				assertEquals(board.hasTopWall(i, j), clone.hasTopWall(i, j));
				assertEquals(board.hasBottomWall(i, j), clone.hasBottomWall(i, j));
				assertEquals(board.hasLeftWall(i, j), clone.hasLeftWall(i, j));
				assertEquals(board.hasRightWall(i, j), clone.hasRightWall(i, j));
				
	}
		}
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// TILE TESTS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	
	
}
