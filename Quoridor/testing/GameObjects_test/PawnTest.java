package GameObjects_test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GameObjects.Coordinates;
import GameObjects.Pawn;
import GameObjects.Pawn.DESTINATION;


public class PawnTest {
	private static final int DEFAULT_ROW = 0;
	private static final int DEFAULT_COLUMN = 0;
	private static final int DEFAULT_WALL_COUNT = 1;
	private DESTINATION dest;
	
	Pawn pawn;
	
	@Before
	public void setUp() {
		pawn = new Pawn(DEFAULT_ROW, DEFAULT_COLUMN, dest,  DEFAULT_WALL_COUNT);
	}
	
	@Test
	public void testDefaultCoordinates() {
		assertThat(pawn.getCoordinates().getRow(), is(DEFAULT_ROW));
		assertThat(pawn.getCoordinates().getColumn(), is(DEFAULT_COLUMN));
	}
	
	@Test
	public void testMove() {
		Coordinates coord = new Coordinates(1, 2);
		
		pawn.move(coord);
		
		assertThat(pawn.getCoordinates().getRow(), is(coord.getRow()));
		assertThat(pawn.getCoordinates().getColumn(), is(coord.getColumn()));
	}
	
	@Test
	public void testDefaultWallCount() {
		assertThat(pawn.getWallCount(), is(DEFAULT_WALL_COUNT));
	}
	
	
	@Test
	public void testGetWallCount() {
		assertThat(pawn.getWallCount(), is(DEFAULT_WALL_COUNT));
	}
	
	@Test
	public void testUseWallCount() {
		int i = 0;
		//pawn = new Pawn(DEFAULT_ROW, DEFAULT_COLUMN, dest,  1);
		pawn.useWall();
		System.out.println(pawn.getWallCount());
		assertThat(pawn.getWallCount(), is(i));
	}
	
	@Test
	public void testHasAvailableWalls() {
		assertTrue(pawn.hasAvailableWalls());
	}
	
	@Test
	public void testGetDestination() {
		pawn = new Pawn(DEFAULT_ROW, DEFAULT_COLUMN, DESTINATION.FIRST_COLUMN,  DEFAULT_WALL_COUNT);
		assertEquals(pawn.getDestination(), DESTINATION.FIRST_COLUMN);
	}
	
	@Test
	public void testGetCoordinates() {
		pawn = new Pawn(DEFAULT_ROW, DEFAULT_COLUMN, dest,  DEFAULT_WALL_COUNT);
		Pawn testPawn = new Pawn(DEFAULT_ROW, DEFAULT_COLUMN, dest,  DEFAULT_WALL_COUNT);
		assertEquals(pawn.getCoordinates(), testPawn.getCoordinates());
	}
	
	@Test
	public void testClone() {
		pawn = new Pawn(DEFAULT_ROW, DEFAULT_COLUMN, dest,  DEFAULT_WALL_COUNT);
		assertEquals(pawn.getCoordinates(), Pawn.clone(pawn).getCoordinates());
	}
	@After
	public void testUseWall() {
		pawn.useWall();
		assertTrue(!pawn.hasAvailableWalls());
	}
	
}
