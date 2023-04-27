package it.polimi.ingsw;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Tile;
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
    void generateId_TwoPLayers_CorrectBehavior() {

        int c = 0;
        ArrayList<Integer> validIdList;
        validIdList = boardTwo.getValidIdList();
        while (validIdList.size() > 0) {
            boardTwo.generateId();
            c++;
        }
        assertSame(132 - 29, c);
    }

    @Test
    void generateId_ThreePLayers_CorrectBehavior() {

        int c = 0;
        ArrayList<Integer> validIdList;
        validIdList = boardThree.getValidIdList();
        while (validIdList.size() > 0) {
            boardThree.generateId();
            c++;
        }
        assertSame(132-37,c );
    }

    @Test
    void generateId_FourPLayers_CorrectBehavior() {

        int c = 0;
        ArrayList<Integer> validIdList;
        validIdList = boardFour.getValidIdList();
        while (validIdList.size() > 0) {
            boardFour.generateId();
            c++;
        }
        assertSame(132-45,c );
    }



    @Test
    void refillBoard_TwoPLayers_CorrectBehavior() {
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
    void refillBoard_ThreePLayers_CorrectBehavior() {
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
    void refillBoard_FourPLayers_CorrectBehavior() {
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
    void checkRefill_ExpectedFalse() {
        assertSame(false, boardTwo.checkRefill());
        assertSame(false, boardThree.checkRefill());
        assertSame(false, boardFour.checkRefill());
    }

    @Test
    void checkRefill_ExpectedTrue() {
        ArrayList<Position> positions1 = new ArrayList<>();

        positions1.add(new Position(1,4));

        positions1.add(new Position(7,4));

        positions1.add(new Position(2,3));

        positions1.add(new Position(2,5));

        positions1.add(new Position(3,2));

        positions1.add(new Position(3,6));

        positions1.add(new Position(3,7));

        positions1.add(new Position(4,1));

        positions1.add(new Position(4,7));

        positions1.add(new Position(5,1));

        positions1.add(new Position(5,2));

        positions1.add(new Position(5,6));

        positions1.add(new Position(6,3));

        positions1.add(new Position(6,5));

        ArrayList<Tile> pulledTiles1 = boardTwo.pullTiles(positions1);

        //----------------------------------------------------------------------

        ArrayList<Position> positions2 = new ArrayList<>();

        positions2.add(new Position(3,5));

        positions2.add(new Position(3,5));

        positions2.add(new Position(4,2));

        positions2.add(new Position(4,6));

        positions2.add(new Position(5,3));

        positions2.add(new Position(5,5));
        ArrayList<Tile> pulledTiles2 = boardTwo.pullTiles(positions2);

        //----------------------------------------------------------------------


        ArrayList<Position> positions3 = new ArrayList<>();

        positions3.add(new Position(3,4));

        positions3.add(new Position(4,3));

        positions3.add(new Position(5,4));

        positions3.add(new Position(4,5));
        ArrayList<Tile> pulledTiles3 = boardTwo.pullTiles(positions3);

        assertSame(true, boardTwo.checkRefill());
    }



    @Test
    void pullTiles_FourPlayer_CorrectBehavior() {
        Tile[][] tilesTable;
        tilesTable = boardFour.getTilesTable();
        ArrayList<Position> positions = new ArrayList<>();

        int row = 3;
        int column = 1;
        Tile tileToPull = tilesTable[row][column];
        positions.add(new Position(row,column));

        ArrayList <Tile> pulledTiles = boardFour.pullTiles(positions);
        tilesTable = boardFour.getTilesTable();

        assertSame(null, tilesTable[row][column]);
        assertSame(tileToPull.getID(), (pulledTiles.get(0)).getID());
    }


    @Test
    void pullTiles_TwoPlayers_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            ArrayList<Position> positions = new ArrayList<>();

            positions.add(new Position(3,1));

            ArrayList<Tile> pulledTiles = boardTwo.pullTiles(positions);
        });
    }

    @Test
    void pullTiles_FourPlayers_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArrayList<Position> positions = new ArrayList<>();

            positions.add(new Position(1, 3));

            positions.add(new Position(1, 4));

            positions.add(new Position(1, 5));

            ArrayList<Tile> pulledTiles = boardFour.pullTiles(positions);
        });
    }
}