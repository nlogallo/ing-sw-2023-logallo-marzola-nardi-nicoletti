package it.polimi.ingsw;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommonGoalTest {
    @Test
    public void constructor_InvalidID_ThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new CommonGoal ("description1", 14));
    }

    @Test
    public void constructor_NullDescription_ThrowsNullPointerException(){
        assertThrows(NullPointerException.class, () -> new CommonGoal(null, 5));
    }

    @Test
    public void constructor_CorrectInput_CorrectOutput(){
        CommonGoal commonGoal = new CommonGoal("""
                Six groups each containing at least
                2 tiles of the same type (not necessarily
                in the depicted shape).
                The tiles of one group can be different
                from those of another group.""", 0);
        assertEquals("""
                Six groups each containing at least
                2 tiles of the same type (not necessarily
                in the depicted shape).
                The tiles of one group can be different
                from those of another group.""", commonGoal.getDescription());
        assertEquals(0, commonGoal.getId());
    }

    @Test
    public void checkCommonGoal0_0groups_AssertFalse(){
        CommonGoal commonGoal = new CommonGoal("description0", 0);
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.CAT;
        playerShelf[5][1] = TileType.BOOK;
        playerShelf[5][2] = TileType.GAME;
        playerShelf[5][3] = TileType.FRAME;
        playerShelf[5][4] = TileType.PLANT;
        playerShelf[4][0] = TileType.BOOK;
        playerShelf[4][1] = TileType.PLANT;
        playerShelf[4][2] = TileType.CAT;
        playerShelf[4][3] = TileType.PLANT;
        playerShelf[4][4] = TileType.FRAME;
        playerShelf[3][2] = TileType.BOOK;
        playerShelf[2][2] = TileType.PLANT;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal0_5groups_AssertFalse(){
        CommonGoal commonGoal = new CommonGoal("description0", 0);
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.PLANT;
        playerShelf[5][2] = TileType.PLANT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.PLANT;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal0_8groups_AssertTrue(){
        CommonGoal commonGoal = new CommonGoal("description0", 0);
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.TROPHY;
        playerShelf[5][2] = TileType.TROPHY;
        playerShelf[5][3] = TileType.GAME;
        playerShelf[5][4] = TileType.CAT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.GAME;
        playerShelf[4][2] = TileType.TROPHY;
        playerShelf[4][3] = TileType.PLANT;
        playerShelf[4][4] = TileType.CAT;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][1] = TileType.GAME;
        playerShelf[3][2] = TileType.GAME;
        playerShelf[3][3] = TileType.GAME;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal1_NoDiagonal_AssertFalse(){
        CommonGoal commonGoal = new CommonGoal("description1", 1);
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.TROPHY;
        playerShelf[5][2] = TileType.TROPHY;
        playerShelf[5][3] = TileType.GAME;
        playerShelf[5][4] = TileType.CAT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.GAME;
        playerShelf[4][2] = TileType.TROPHY;
        playerShelf[4][3] = TileType.PLANT;
        playerShelf[4][4] = TileType.CAT;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][1] = TileType.GAME;
        playerShelf[3][2] = TileType.GAME;
        playerShelf[3][3] = TileType.GAME;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal1_Diagonal_AssertTrue(){
        CommonGoal commonGoal = new CommonGoal("description1", 1);
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.TROPHY;
        playerShelf[5][2] = TileType.TROPHY;
        playerShelf[5][3] = TileType.GAME;
        playerShelf[5][4] = TileType.GAME;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.GAME;
        playerShelf[4][2] = TileType.TROPHY;
        playerShelf[4][3] = TileType.GAME;
        playerShelf[4][4] = TileType.CAT;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][1] = TileType.GAME;
        playerShelf[3][2] = TileType.GAME;
        playerShelf[3][3] = TileType.GAME;
        playerShelf[2][0] = TileType.FRAME;
        playerShelf[2][1] = TileType.GAME;
        playerShelf[1][0] = TileType.GAME;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal1_ReverseDiagonal_AssertTrue(){
        CommonGoal commonGoal = new CommonGoal("description1", 1);
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.GAME;
        playerShelf[5][1] = TileType.BOOK;
        playerShelf[5][2] = TileType.TROPHY;
        playerShelf[5][3] = TileType.BOOK;
        playerShelf[5][4] = TileType.PLANT;
        playerShelf[4][0] = TileType.CAT;
        playerShelf[4][1] = TileType.PLANT;
        playerShelf[4][2] = TileType.BOOK;
        playerShelf[4][3] = TileType.FRAME;
        playerShelf[4][4] = TileType.FRAME;
        playerShelf[3][1] = TileType.CAT;
        playerShelf[3][2] = TileType.TROPHY;
        playerShelf[3][3] = TileType.BOOK;
        playerShelf[3][4] = TileType.TROPHY;
        playerShelf[2][2] = TileType.CAT;
        playerShelf[2][3] = TileType.TROPHY;
        playerShelf[2][4] = TileType.FRAME;
        playerShelf[1][3] = TileType.CAT;
        playerShelf[1][4] = TileType.FRAME;
        playerShelf[0][4] = TileType.CAT;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal2_0groups_AssertFalse(){
        CommonGoal commonGoal = new CommonGoal("description2", 2);
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.TROPHY;
        playerShelf[5][2] = TileType.TROPHY;
        playerShelf[5][3] = TileType.GAME;
        playerShelf[5][4] = TileType.CAT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.GAME;
        playerShelf[4][2] = TileType.TROPHY;
        playerShelf[4][3] = TileType.PLANT;
        playerShelf[4][4] = TileType.CAT;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][1] = TileType.GAME;
        playerShelf[3][2] = TileType.GAME;
        playerShelf[3][3] = TileType.GAME;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal2_4groups_AssertTrue(){
        CommonGoal commonGoal = new CommonGoal("description2", 2);
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.TROPHY;
        playerShelf[5][2] = TileType.TROPHY;
        playerShelf[5][3] = TileType.PLANT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.TROPHY;
        playerShelf[4][2] = TileType.CAT;
        playerShelf[4][3] = TileType.PLANT;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][1] = TileType.CAT;
        playerShelf[3][2] = TileType.TROPHY;
        playerShelf[3][3] = TileType.PLANT;
        playerShelf[2][0] = TileType.PLANT;
        playerShelf[2][1] = TileType.PLANT;
        playerShelf[2][2] = TileType.PLANT;
        playerShelf[2][3] = TileType.PLANT;
        playerShelf[1][3] = TileType.PLANT;
        playerShelf[0][3] = TileType.PLANT;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal3_3groups_AssertFalse() {
        CommonGoal commonGoal = new CommonGoal("description3", 3);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.GAME;
        playerShelf[5][1] = TileType.CAT;
        playerShelf[5][2] = TileType.CAT;
        playerShelf[5][3] = TileType.BOOK;
        playerShelf[5][4] = TileType.CAT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.CAT;
        playerShelf[4][2] = TileType.CAT;
        playerShelf[4][3] = TileType.CAT;
        playerShelf[4][4] = TileType.FRAME;
        playerShelf[3][0] = TileType.FRAME;
        playerShelf[3][1] = TileType.FRAME;
        playerShelf[3][2] = TileType.FRAME;
        playerShelf[3][3] = TileType.FRAME;
        playerShelf[3][4] = TileType.FRAME;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal3_4groups_AssertTrue() {
        CommonGoal commonGoal = new CommonGoal("description3", 3);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.GAME;
        playerShelf[5][1] = TileType.CAT;
        playerShelf[5][2] = TileType.CAT;
        playerShelf[5][3] = TileType.BOOK;
        playerShelf[5][4] = TileType.CAT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.CAT;
        playerShelf[4][2] = TileType.CAT;
        playerShelf[4][3] = TileType.CAT;
        playerShelf[4][4] = TileType.FRAME;
        playerShelf[3][0] = TileType.FRAME;
        playerShelf[3][1] = TileType.FRAME;
        playerShelf[3][2] = TileType.FRAME;
        playerShelf[3][3] = TileType.FRAME;
        playerShelf[3][4] = TileType.FRAME;
        playerShelf[2][0] = TileType.FRAME;
        playerShelf[2][1] = TileType.TROPHY;
        playerShelf[2][2] = TileType.FRAME;
        playerShelf[2][3] = TileType.CAT;
        playerShelf[2][4] = TileType.TROPHY;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }
    @Test
    public void checkCommonGoal4_NoMatches_AssertFalse() {
        CommonGoal commonGoal = new CommonGoal("description4", 4);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.CAT;
        playerShelf[5][0] = TileType.GAME;
        playerShelf[0][0] = TileType.EMPTY;
        playerShelf[0][4] = TileType.GAME;
        playerShelf[5][4] = TileType.GAME;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal4_Matches_AssertTrue() {
        CommonGoal commonGoal = new CommonGoal("description4", 4);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.CAT;
        playerShelf[5][0] = TileType.GAME;
        playerShelf[0][0] = TileType.GAME;
        playerShelf[0][4] = TileType.GAME;
        playerShelf[5][4] = TileType.GAME;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }
    @Test
    public void checkCommonGoal5_0groups_AssertFalse() {
        CommonGoal commonGoal = new CommonGoal("description5", 5);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.GAME;
        playerShelf[4][0] = TileType.GAME;
        playerShelf[3][0] = TileType.CAT;
        playerShelf[2][0] = TileType.TROPHY;
        playerShelf[1][0] = TileType.FRAME;
        playerShelf[0][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.GAME;
        playerShelf[4][1] = TileType.BOOK;
        playerShelf[3][1] = TileType.CAT;
        playerShelf[2][1] = TileType.TROPHY;
        playerShelf[1][1] = TileType.FRAME;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal5_0groups_AssertTrue() {
        CommonGoal commonGoal = new CommonGoal("description5", 5);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.GAME;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[3][0] = TileType.CAT;
        playerShelf[2][0] = TileType.TROPHY;
        playerShelf[1][0] = TileType.FRAME;
        playerShelf[0][0] = TileType.BOOK;
        playerShelf[5][1] = TileType.GAME;
        playerShelf[4][1] = TileType.BOOK;
        playerShelf[3][1] = TileType.CAT;
        playerShelf[2][1] = TileType.TROPHY;
        playerShelf[1][1] = TileType.FRAME;
        playerShelf[0][1] = TileType.PLANT;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal6_1group_AssertFalse() {
        CommonGoal commonGoal = new CommonGoal("description6", 6);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.GAME;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.GAME;
        playerShelf[4][1] = TileType.GAME;
        playerShelf[3][0] = TileType.GAME;
        playerShelf[3][1] = TileType.GAME;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal6_2groups_AssertTrue() {
        CommonGoal commonGoal = new CommonGoal("description6", 6);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.GAME;
        playerShelf[4][0] = TileType.GAME;
        playerShelf[5][1] = TileType.GAME;
        playerShelf[4][1] = TileType.GAME;
        playerShelf[3][0] = TileType.GAME;
        playerShelf[3][1] = TileType.GAME;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal7_1group_AssertFalse() {
        CommonGoal commonGoal = new CommonGoal("description7", 7);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.TROPHY;
        playerShelf[5][2] = TileType.BOOK;
        playerShelf[5][3] = TileType.FRAME;
        playerShelf[5][4] = TileType.CAT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.TROPHY;
        playerShelf[4][2] = TileType.BOOK;
        playerShelf[4][3] = TileType.CAT;
        playerShelf[4][4] = TileType.CAT;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }
    @Test
    public void checkCommonGoal7_2groups_AssertTrue() {
        CommonGoal commonGoal = new CommonGoal("description7", 7);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.TROPHY;
        playerShelf[5][2] = TileType.BOOK;
        playerShelf[5][3] = TileType.FRAME;
        playerShelf[5][4] = TileType.CAT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.TROPHY;
        playerShelf[4][2] = TileType.BOOK;
        playerShelf[4][3] = TileType.FRAME;
        playerShelf[4][4] = TileType.CAT;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal8_2groups_AssertFalse() {
        CommonGoal commonGoal = new CommonGoal("description8", 8);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[3][0] = TileType.CAT;
        playerShelf[2][0] = TileType.PLANT;
        playerShelf[1][0] = TileType.PLANT;
        playerShelf[0][0] = TileType.BOOK;
        playerShelf[5][1] = TileType.PLANT;
        playerShelf[4][1] = TileType.PLANT;
        playerShelf[3][1] = TileType.CAT;
        playerShelf[2][1] = TileType.PLANT;
        playerShelf[1][1] = TileType.PLANT;
        playerShelf[0][1] = TileType.BOOK;
        playerShelf[5][2] = TileType.PLANT;
        playerShelf[4][2] = TileType.PLANT;
        playerShelf[3][2] = TileType.CAT;
        playerShelf[2][2] = TileType.PLANT;
        playerShelf[1][2] = TileType.PLANT;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal8_3groups_AssertTrue() {
        CommonGoal commonGoal = new CommonGoal("description8", 8);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[3][0] = TileType.CAT;
        playerShelf[2][0] = TileType.PLANT;
        playerShelf[1][0] = TileType.PLANT;
        playerShelf[0][0] = TileType.BOOK;
        playerShelf[5][1] = TileType.PLANT;
        playerShelf[4][1] = TileType.PLANT;
        playerShelf[3][1] = TileType.CAT;
        playerShelf[2][1] = TileType.PLANT;
        playerShelf[1][1] = TileType.PLANT;
        playerShelf[0][1] = TileType.BOOK;
        playerShelf[5][2] = TileType.PLANT;
        playerShelf[4][2] = TileType.PLANT;
        playerShelf[3][2] = TileType.CAT;
        playerShelf[2][2] = TileType.PLANT;
        playerShelf[1][2] = TileType.PLANT;
        playerShelf[0][2] = TileType.PLANT;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal9_NoX_AssertFalse() {
        CommonGoal commonGoal = new CommonGoal("description9", 9);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[3][0] = TileType.CAT;
        playerShelf[2][0] = TileType.PLANT;
        playerShelf[1][0] = TileType.PLANT;
        playerShelf[0][0] = TileType.BOOK;
        playerShelf[5][1] = TileType.PLANT;
        playerShelf[4][1] = TileType.PLANT;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal9_X_AssertTrue() {
        CommonGoal commonGoal = new CommonGoal("description9", 9);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[4][0] = TileType.TROPHY;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][2] = TileType.PLANT;
        playerShelf[2][0] = TileType.FRAME;
        playerShelf[1][0] = TileType.PLANT;
        playerShelf[0][0] = TileType.BOOK;
        playerShelf[5][1] = TileType.FRAME;
        playerShelf[4][1] = TileType.PLANT;
        playerShelf[4][2] = TileType.TROPHY;
        playerShelf[5][2] = TileType.PLANT;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal10_7tiles_AssertFalse() {
        CommonGoal commonGoal = new CommonGoal("description10", 10);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[4][0] = TileType.TROPHY;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][2] = TileType.PLANT;
        playerShelf[2][0] = TileType.FRAME;
        playerShelf[1][0] = TileType.PLANT;
        playerShelf[0][0] = TileType.BOOK;
        playerShelf[5][1] = TileType.FRAME;
        playerShelf[4][1] = TileType.PLANT;
        playerShelf[4][2] = TileType.TROPHY;
        playerShelf[5][2] = TileType.PLANT;
        playerShelf[5][3] = TileType.PLANT;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal10_8tiles_AssertTrue() {
        CommonGoal commonGoal = new CommonGoal("description10", 10);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[4][0] = TileType.TROPHY;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][2] = TileType.PLANT;
        playerShelf[2][0] = TileType.FRAME;
        playerShelf[1][0] = TileType.PLANT;
        playerShelf[0][0] = TileType.BOOK;
        playerShelf[5][1] = TileType.FRAME;
        playerShelf[4][1] = TileType.PLANT;
        playerShelf[4][2] = TileType.TROPHY;
        playerShelf[5][2] = TileType.PLANT;
        playerShelf[5][3] = TileType.PLANT;
        playerShelf[5][4] = TileType.PLANT;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal11_NoIncreasingOrDecreasing_AssertFalse() {
        CommonGoal commonGoal = new CommonGoal("description11", 11);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[4][0] = TileType.TROPHY;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][2] = TileType.PLANT;
        playerShelf[2][0] = TileType.FRAME;
        playerShelf[1][0] = TileType.PLANT;
        playerShelf[0][0] = TileType.BOOK;
        playerShelf[5][1] = TileType.FRAME;
        playerShelf[4][1] = TileType.PLANT;
        playerShelf[4][2] = TileType.TROPHY;
        playerShelf[5][2] = TileType.PLANT;
        playerShelf[5][3] = TileType.PLANT;
        playerShelf[5][4] = TileType.PLANT;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal11_Increasing_AssertTrue() {
        CommonGoal commonGoal = new CommonGoal("description11", 11);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[4][0] = TileType.GAME;
        playerShelf[3][0] = TileType.TROPHY;
        playerShelf[2][0] = TileType.GAME;
        playerShelf[1][0] = TileType.FRAME;
        playerShelf[0][0] = TileType.GAME;
        playerShelf[5][1] = TileType.PLANT;
        playerShelf[4][1] = TileType.GAME;
        playerShelf[3][1] = TileType.TROPHY;
        playerShelf[2][1] = TileType.GAME;
        playerShelf[1][1] = TileType.FRAME;
        playerShelf[5][2] = TileType.PLANT;
        playerShelf[4][2] = TileType.GAME;
        playerShelf[3][2] = TileType.TROPHY;
        playerShelf[2][2] = TileType.GAME;
        playerShelf[5][3] = TileType.PLANT;
        playerShelf[4][3] = TileType.GAME;
        playerShelf[3][3] = TileType.TROPHY;
        playerShelf[5][4] = TileType.PLANT;
        playerShelf[4][4] = TileType.GAME;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal11_Decreasing_AssertTrue() {
        CommonGoal commonGoal = new CommonGoal("description11", 11);
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][4] = TileType.PLANT;
        playerShelf[4][4] = TileType.GAME;
        playerShelf[3][4] = TileType.TROPHY;
        playerShelf[2][4] = TileType.GAME;
        playerShelf[1][4] = TileType.FRAME;
        playerShelf[5][3] = TileType.PLANT;
        playerShelf[4][3] = TileType.GAME;
        playerShelf[3][3] = TileType.TROPHY;
        playerShelf[2][3] = TileType.GAME;
        playerShelf[5][2] = TileType.PLANT;
        playerShelf[4][2] = TileType.GAME;
        playerShelf[3][2] = TileType.PLANT;
        playerShelf[5][1] = TileType.TROPHY;
        playerShelf[4][1] = TileType.PLANT;
        playerShelf[5][0] = TileType.GAME;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }
}
