package it.polimi.ingsw;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.TileType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
    public void newTile_OverMaxID_ThrowsIllegalArgumentException () {
        assertThrows(IllegalArgumentException.class, () ->  new Tile (200));
    }

    @Test
    public void newTile_NegativeRow_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tile (5).setPosition(-2, 5));
    }

    @Test
    public void newTile_NegativeColumn_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Tile tile = new Tile(34);
            tile.setPosition(7, -5);
        });
    }

    @Test
    public void newTile_OverMaxRow_ThrowsIllegalArgumentException () {
        assertThrows(IllegalArgumentException.class, () ->
            new Tile(5).setPosition(10, 3));
    }

    @Test
    public void newTile_OverMaxColumn_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Tile tile = new Tile(131);
            tile.setPosition(0, 80);
        });
    }

    @Test
    public void newTile_OverMaxRowAndColumn_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tile(120).setPosition(48, 14));
    }

    @Test
    public void setPosition_correctBehaviour() {
        Tile tile = new Tile(9);
        tile.setPosition(4, 7);
    }

    @Test
    public void getPosition_correctBehaviour() {

        Tile tile = new Tile(4);
        tile.setPosition(3,7);
        int [] positions = tile.getPosition();
        int [] positionsArray = new int[] {3,7};
        boolean isCorrect = true;

        for (int i=0; i<positions.length; i++) {
            if (positions[i] != positionsArray[i]) {
                isCorrect = false;
            }
        }

        assertTrue(isCorrect);
        assertEquals(tile.getID(), 4);
    }

    @Test
    public void setContainer_CorrectBehaviour() {

        Tile tile1 = new Tile(20);
        Tile tile2 = new Tile(35);
        Tile tile3 = new Tile(47);
        Tile tile4 = new Tile(75);
        Tile tile5 = new Tile(97);
        Tile tile6 = new Tile(120);

        assertEquals(tile1.getType(), TileType.CAT);
        assertEquals(tile2.getType(), TileType.BOOK);
        assertEquals(tile3.getType(), TileType.GAME);
        assertEquals(tile4.getType(), TileType.FRAME);
        assertEquals(tile5.getType(), TileType.TROPHY);
        assertEquals(tile6.getType(), TileType.PLANT);

    }

    @Test
    public void getImageType_correctBehaviour() {

        Tile tile = new Tile(87);
        boolean isCorrect = 0 <= tile.getImageType() && tile.getImageType() <= 2;
        assert (isCorrect);
    }

}


