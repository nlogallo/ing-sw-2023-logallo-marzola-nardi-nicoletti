package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board boardTwo, boardThree, boardFour = null;

    @BeforeEach
    void setUp() {
        boardTwo = new Board(2);
        boardThree = new Board(3);
        boardFour = new Board(4);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void testGenerateID1() {

        int c = 0;
        ArrayList<Integer> validIdList;
        validIdList = boardTwo.getValidIdListList();
        while (validIdList.size() > 0) {
            boardTwo.generateId();
            c++;
        }
            assertSame(132 - 29, c);
    }

    @Test
    void testGenerateID2() {

        int c = 0;
        ArrayList<Integer> validIdList;
        validIdList = boardThree.getValidIdListList();
        while (validIdList.size() > 0) {
            boardThree.generateId();
            c++;
        }
        assertSame(132-37,c );
    }

    @Test
    void testGenerateID3() {

        int c = 0;
        ArrayList<Integer> validIdList;
        validIdList = boardFour.getValidIdListList();
        while (validIdList.size() > 0) {
            boardFour.generateId();
            c++;
        }
        assertSame(132-45,c );
    }



    @Test
    void refillBoard1() {
        Tile[][] tilesTable;
        tilesTable = boardTwo.getTilesTable();

        int count = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j<9; j++) {
                if (tilesTable[i][j] != null) {
                    count++;
                }
            }
        }
        assertSame(29, count);

    }

    @Test
    void refillBoard2() {
        Tile[][] tilesTable;
        tilesTable = boardThree.getTilesTable();

        int count = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j<9; j++) {
                if (tilesTable[i][j] != null) {
                    count++;
                }
            }
        }
        assertSame(37, count);
    }

    @Test
    void refillBoard3() {
        Tile[][] tilesTable;
        tilesTable = boardFour.getTilesTable();

        int count = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j<9; j++) {
                if (tilesTable[i][j] != null) {
                    count++;
                }
            }
        }
        assertSame(45, count);
    }

    @Test
    void checkRefill1() {
        assertSame(false, boardTwo.checkRefill());
        assertSame(false, boardThree.checkRefill());
        assertSame(false, boardFour.checkRefill());
    }

    @Test
    void checkRefill2() {
        ArrayList<Integer> positions1 = new ArrayList<>();
        positions1.add(1);
        positions1.add(4);

        positions1.add(7);
        positions1.add(4);

        positions1.add(2);
        positions1.add(3);

        positions1.add(2);
        positions1.add(5);

        positions1.add(3);
        positions1.add(2);

        positions1.add(3);
        positions1.add(6);

        positions1.add(3);
        positions1.add(7);

        positions1.add(4);
        positions1.add(1);

        positions1.add(4);
        positions1.add(7);

        positions1.add(5);
        positions1.add(1);

        positions1.add(5);
        positions1.add(2);

        positions1.add(5);
        positions1.add(6);

        positions1.add(6);
        positions1.add(3);

        positions1.add(6);
        positions1.add(5);
        ArrayList<Tile> pulledTiles1 = boardTwo.pullTiles(positions1);

        ArrayList<Integer> positions2 = new ArrayList<>();
        positions2.add(3);
        positions2.add(3);

        positions2.add(3);
        positions2.add(5);

        positions2.add(4);
        positions2.add(2);

        positions2.add(4);
        positions2.add(6);

        positions2.add(5);
        positions2.add(3);

        positions2.add(5);
        positions2.add(5);
        ArrayList<Tile> pulledTiles2 = boardTwo.pullTiles(positions2);


        ArrayList<Integer> positions3 = new ArrayList<>();
        positions3.add(3);
        positions3.add(4);

        positions3.add(4);
        positions3.add(3);

        positions3.add(5);
        positions3.add(4);

        positions3.add(4);
        positions3.add(5);
        ArrayList<Tile> pulledTiles3 = boardTwo.pullTiles(positions3);

        assertSame(true, boardTwo.checkRefill());
    }



    @Test
    void pullTiles1() {
        Tile[][] tilesTable;
        tilesTable = boardFour.getTilesTable();
        ArrayList<Integer> positions = new ArrayList<>();
        ArrayList<Tile> pulledTiles;

        int r = 3;
        int c = 1;
        Tile tileToPull;
        tileToPull = tilesTable[r][c];
        positions.add(r);
        positions.add(c);

        pulledTiles = boardFour.pullTiles(positions);
        tilesTable = boardFour.getTilesTable();

        assertSame(null, tilesTable[r][c]);
        assertSame(tileToPull.getID(), (pulledTiles.get(0)).getID());
    }


    @Test
    void pullTilesException1() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArrayList<Integer> positions = new ArrayList<>();

            positions.add(3);
            positions.add(1);

            ArrayList<Tile> pulledTiles = boardTwo.pullTiles(positions);
        });
    }

    @Test
    void pullTilesException2() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArrayList<Integer> positions = new ArrayList<>();

            positions.add(1);
            positions.add(3);

            positions.add(1);
            positions.add(4);

            positions.add(1);
            positions.add(5);

            ArrayList<Tile> pulledTiles = boardFour.pullTiles(positions);
        });
    }
}