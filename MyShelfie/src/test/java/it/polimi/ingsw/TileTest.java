package it.polimi.ingsw;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.TileType;
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
    public void  newTile_CorrectBehavior () {
        Tile tile1 = new Tile(0);
        assertEquals(tile1.getType(), TileType.CAT);

        Tile tile2 = new Tile(30);
        assertEquals(tile2.getType(), TileType.BOOK);

    }

    @Test
    public void newTile_OverMaxID_ShouldThrowIllegalArgumentException () {
        Tile tile = new Tile (200);
    }

    @Test
    public void newTile_NegativeRow_ShouldThrowIllegalArgumentException() {
        new Tile (5).setPosition(-2, 5);
    }

    @Test
    public void newTile_NegativeColumn_ShouldThrowIllegalArgumentException() {
        Tile tile = new Tile (34);
        tile.setPosition(7,-5);
    }

    @Test
    public void newTile_OverMaxRow_ShouldThrowIllegalArgumentException () {
        new Tile(5).setPosition(10,3);
    }

    @Test
    public void newTile_OverMaxColumn_ShouldThrowIllegalArgumentException() {
        Tile tile = new Tile (131);
        tile.setPosition(0,80);
    }

    @Test
    public void newTile_OverMaxRowAndColumn_ShouldThrowIllegalArgumentException() {
        new Tile(120).setPosition(48,14);
    }

}


