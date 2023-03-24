package it.polimi.ingsw;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TileTest {

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}


    @Test
    public void  Test1_correctInput_correctOutput () {
        Tile tile = new Tile(0);
        assertEquals(tile.getType(), TileType.CAT);
    }

    @Test
    public void Test2_correctInput_correctOutput () {
        Tile tile = new Tile(30);
        assertEquals(tile.getType(), TileType.BOOK);
    }

    @Test (expected = IllegalArgumentException.class)
    public void Test3_overMaxID_throwIllegalArgumentException () {
        Tile tile = new Tile (200);
    }

    @Test
    public void Test4_negativeRow_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tile (5).setPosition(-2, 5));
    }

    @Test (expected = IllegalArgumentException.class)
    public void Test5_negativeColumn_throwsIllegalArgumentException() {
        Tile tile = new Tile (34);
        tile.setPosition(7,-5);
    }

    @Test
    public void Test6_overMaxRow_throwsIllegalArgumentException () {
        assertThrows(IllegalArgumentException.class, () -> new Tile(5).setPosition(10,3));
    }

    @Test (expected = IllegalArgumentException.class)
    public void Test7_overMaxColumn_throwsIllegalArgumentException() {
        Tile tile = new Tile (131);
        tile.setPosition(0,80);
    }

    @Test
    public void Test8_overMaxRowAndColumn_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, (() -> new Tile(120).setPosition(48,14)));
    }

}


