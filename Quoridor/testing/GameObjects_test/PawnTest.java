package GameObject_test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GameObjects.Coordinates;
import GameObjects.Pawn;


public class PawnTest {
	private static final int DEFAULT_X = 0;
	private static final int DEFAULT_Y = 0;
	private static final int DEFAULT_WALL_COUNT = 1;
	
	Pawn pawn;
	
	@Before
	public void setUp() {
		pawn = new Pawn(DEFAULT_X, DEFAULT_Y, DEFAULT_WALL_COUNT);
	}
	
	@Test
	public void testDefaultCoordinates() {
		assertThat(pawn.getCoordinates().getX(), is(DEFAULT_X));
		assertThat(pawn.getCoordinates().getY(), is(DEFAULT_Y));
	}
	
	@Test
	public void testMove() {
		Coordinates coord = new Coordinates(1, 2);
		
		pawn.move(coord);
		
		assertThat(pawn.getCoordinates().getX(), is(coord.getX()));
		assertThat(pawn.getCoordinates().getY(), is(coord.getY()));
	}
	
	@Test
	public void testDefaultWallCount() {
		assertThat(pawn.getWallCount(), is(DEFAULT_WALL_COUNT));
	}
	
	
	@Test
	public void testUseWallCount() {
		assertThat(pawn.getWallCount(), is(DEFAULT_WALL_COUNT));
	}
	
	@Test
	public void testHasAvailableWalls() {
		assertTrue(pawn.hasAvailableWalls());
	}
	
	@After
	public void testUseWall() {
		pawn.useWall();
		assertTrue(!pawn.hasAvailableWalls());
	}
	
}
