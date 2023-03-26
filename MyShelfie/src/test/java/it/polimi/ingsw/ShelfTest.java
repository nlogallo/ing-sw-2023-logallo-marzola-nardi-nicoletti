package it.polimi.ingsw;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

    Shelf shelf = null;

    @BeforeEach
    public void setUp() {
        shelf = new Shelf();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void freeRows() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(0));
        tiles.add(new Tile(50));
        shelf.insertTiles(0, tiles);
        assertSame( 4, shelf.freeRows(0));
    }

    @Test
    void freeSpots() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(0));
        tiles.add(new Tile(50));
        shelf.insertTiles(0, tiles);
        assertSame( 6, shelf.freeSpots());

        for (int i = 0; i < 5; i++) {
            shelf.insertTiles(i, tiles);
        }
        assertSame( 4, shelf.freeSpots());

    }

    @Test
    void insertTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(0));
        tiles.add(new Tile(50));
        shelf.insertTiles(0, tiles);
        Tile t = new Tile(0);
        assertSame((shelf.getTile(5, 0)).getID(), t.getID());
        Tile s = new Tile(50);
        assertSame((shelf.getTile(4, 0)).getID(), s.getID());
    }

    @Test
    void insertTilesThrowsException() {

        assertThrows(IllegalArgumentException.class, () -> {
            ArrayList<Tile> tiles = new ArrayList<>();
            tiles.add(new Tile(0));
            tiles.add(new Tile(50));
            shelf.insertTiles(0, tiles);
            shelf.insertTiles(0, tiles);
            tiles.add(new Tile(50));
            shelf.insertTiles(0, tiles);
        });
    }

    @Test
    void isFull1() {
            assertSame(false, shelf.isFull());
    }

    @Test
    void isFull2() {
        ArrayList<Tile> tilesOne = new ArrayList<>();
        ArrayList<Tile> tilesTwo = new ArrayList<>();
        tilesOne.add(new Tile(0));
        tilesOne.add(new Tile(50));
        tilesOne.add(new Tile(100));
        tilesTwo.add(new Tile(1));
        tilesTwo.add(new Tile(52));
        tilesTwo.add(new Tile(102));

        for (int i = 0; i < 5; i++) {
            shelf.insertTiles(i, tilesOne);
            assertSame(false, shelf.isFull());
            shelf.insertTiles(i, tilesTwo);
        }
        assertSame(true, shelf.isFull());
    }



    @Test
    void getShelfTypes1() {
        TileType[][] matrixTypes;
        matrixTypes = shelf.getShelfTypes();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++){
                assertSame(TileType.EMPTY, matrixTypes[i][j]);
            }
        }
    }

    @Test
    void getShelfTypes2() {
        TileType[][] matrixTypes;
        ArrayList<Tile> tilesOne = new ArrayList<>();
        ArrayList<Tile> tilesTwo = new ArrayList<>();

        tilesOne.add(new Tile(0));
        tilesOne.add(new Tile(50));
        tilesOne.add(new Tile(100));
        tilesTwo.add(new Tile(70));
        tilesTwo.add(new Tile(130));
        for (int i = 0; i < 5; i++) {
            shelf.insertTiles(i, tilesOne);
            shelf.insertTiles(i, tilesTwo);
        }

        matrixTypes = shelf.getShelfTypes();
        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 5; j++){
                assertSame((shelf.getTile(i, j)).getType(), matrixTypes[i][j]);
            }
        }
        for (int i = 0; i < 5; i++) {
            assertSame(TileType.EMPTY, matrixTypes[0][i]);
        }
    }
}