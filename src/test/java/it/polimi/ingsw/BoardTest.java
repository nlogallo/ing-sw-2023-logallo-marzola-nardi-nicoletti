package it.polimi.ingsw;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.TileType;
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
    void getValidIdList_CorrectBehavior() {
        assertEquals(103, boardTwo.getValidIdList().size());
        assertEquals(95, boardThree.getValidIdList().size());
        assertEquals(87, boardFour.getValidIdList().size());
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
    void checkRefill_CornerCases_ExpectedFalse() {
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(new Position(0,3));
        positions.add(new Position(0,4));
        positions.add(new Position(1,3));
        positions.add(new Position(1,5));
        positions.add(new Position(2,2));
        positions.add(new Position(2,6));
        positions.add(new Position(3,1));
        positions.add(new Position(3,8));
        positions.add(new Position(3,7));
        ArrayList<Tile> pulledTiles = boardFour.pullTiles(positions);
        assertSame(false, boardFour.checkRefill());

        positions = new ArrayList<>();
        positions.add(new Position(1,4));
        positions.add(new Position(2,3));
        positions.add(new Position(2,5));
        positions.add(new Position(3,2));
        positions.add(new Position(3,6));
        ArrayList<Tile> pulledTiles1 = boardFour.pullTiles(positions);
        assertSame(false, boardFour.checkRefill());

        positions = new ArrayList<>();
        positions.add(new Position(2,4));
        positions.add(new Position(3,3));
        positions.add(new Position(3,5));
        ArrayList<Tile> pulledTiles2 = boardFour.pullTiles(positions);
        assertSame(false, boardFour.checkRefill());

        positions = new ArrayList<>();
        positions.add(new Position(3,4));
        ArrayList<Tile> pulledTiles3 = boardFour.pullTiles(positions);
        assertSame(false, boardFour.checkRefill());

        positions = new ArrayList<>();
        positions.add(new Position(4,0));
        positions.add(new Position(4,1));
        positions.add(new Position(4,3));
        positions.add(new Position(4,5));
        positions.add(new Position(4,7));
        ArrayList<Tile> pulledTiles4 = boardFour.pullTiles(positions);
        assertSame(false, boardFour.checkRefill());

        positions = new ArrayList<>();
        positions.add(new Position(4,2));
        positions.add(new Position(4,4));
        positions.add(new Position(4,6));

        ArrayList<Tile> pulledTiles5 = boardFour.pullTiles(positions);
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
        assertEquals(tileToPull.getID(), (pulledTiles.get(0)).getID());
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

    @Test
    void areAligned_ThreeTilesCheck() {
        ArrayList<Position> positions = new ArrayList<>();

        Position p1 = new Position(1,3);
        positions.add(p1);
        Position p2 = new Position(1,2);
        positions.add(p2);
        Position p3 = new Position(1,1);
        positions.add(p3);
        assertSame(true, boardTwo.areAligned(positions));

        //---------------------------------------------------------

        positions = new ArrayList<>();

        p1 = new Position(1,3);
        positions.add(p1);
        p2 = new Position(1,0);
        positions.add(p2);
        p3 = new Position(1,1);
        positions.add(p3);
        assertSame(false, boardTwo.areAligned(positions));

        //---------------------------------------------------------

        positions = new ArrayList<>();

        p1 = new Position(1,1);
        positions.add(p1);
        p2 = new Position(2,1);
        positions.add(p2);
        p3 = new Position(3,1);
        positions.add(p3);
        assertSame(true, boardTwo.areAligned(positions));

        //---------------------------------------------------------

        positions = new ArrayList<>();

        p1 = new Position(2,1);
        positions.add(p1);
        p2 = new Position(5,1);
        positions.add(p2);
        p3 = new Position(3,1);
        positions.add(p3);
        assertSame(false, boardTwo.areAligned(positions));
    }

    @Test
    void areAligned_TwoTilesCheck() {
        ArrayList<Position> positions = new ArrayList<>();

        Position p1 = new Position(1,3);
        positions.add(p1);
        Position p2 = new Position(1,2);
        positions.add(p2);
        assertSame(true, boardTwo.areAligned(positions));

        //---------------------------------------------------------

        positions = new ArrayList<>();

        p1 = new Position(1,3);
        positions.add(p1);
        p2 = new Position(1,1);
        positions.add(p2);
        assertSame(false, boardTwo.areAligned(positions));

        //---------------------------------------------------------

        positions = new ArrayList<>();
        p1 = new Position(2,3);
        positions.add(p1);
        p2 = new Position(1,3);
        positions.add(p2);
        assertSame(true, boardTwo.areAligned(positions));

        //---------------------------------------------------------

        positions = new ArrayList<>();
        p1 = new Position(3,3);
        positions.add(p1);
        p2 = new Position(1,3);
        positions.add(p2);
        assertSame(false, boardTwo.areAligned(positions));

        //---------------------------------------------------------

        positions = new ArrayList<>();
        p1 = new Position(3,3);
        positions.add(p1);
        p2 = new Position(1,5);
        positions.add(p2);
        assertSame(false, boardTwo.areAligned(positions));
    }

    @Test
    void areAligned_OneTilesCheck() {
        ArrayList<Position> positions1 = new ArrayList<>();

        Position p1 = new Position(1,3);
        positions1.add(p1);
        assertSame(true, boardTwo.areAligned(positions1));

        //---------------------------------------------------------

        ArrayList<Position> positions2 = new ArrayList<>();

        Position p13 = new Position(1,1);
        positions2.add(p13);
        assertSame(true, boardTwo.areAligned(positions2));
    }

    @Test
    void canPull_ExpectedTrue() {
        assertSame(true, boardFour.canPull(0, 3));
        assertSame(true, boardThree.canPull(0, 3));
    }

    @Test
    void getTilesType_FourBoardPlayer_CorrectBehavior() {
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j<9; j++)
                if (boardFour.getTilesTable()[i][j] != null)
                    assertSame(boardFour.getTilesTable()[i][j].getType(), boardFour.getTilesType()[i][j]);
                else {
                    assertSame(TileType.EMPTY, boardFour.getTilesType()[i][j]);
                }
        }
    }

    @Test
    void getTilesTable_CorrectBehavior (){
        assertEquals( boardTwo.getCurrentBoard(), boardTwo.getTilesTable());
    }

}