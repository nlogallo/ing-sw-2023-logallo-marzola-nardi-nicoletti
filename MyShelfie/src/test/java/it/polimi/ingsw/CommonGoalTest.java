package it.polimi.ingsw;

import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.model.commonGoal.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommonGoalTest {
    @Test
    public void constructor_InvalidID_ThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new CG_SpecialEqualTiles(14, "description1"));
        assertThrows(IllegalArgumentException.class, () -> new CG_3DiffTypes(-1, "description1"));
        assertThrows(IllegalArgumentException.class, () -> new CG_8Tiles(15, "description1"));
        assertThrows(IllegalArgumentException.class, () -> new CG_DiffHeight(19, "description1"));
        assertThrows(IllegalArgumentException.class, () -> new CG_DiffTiles(22, "description1"));
        assertThrows(IllegalArgumentException.class, () -> new CG_SpecialEqualTiles(17, "description1"));
        assertThrows(IllegalArgumentException.class, () -> new CG_StdEqualTiles(20, "description1"));
    }

    @Test
    public void constructor_NullDescription_ThrowsNullPointerException(){
        assertThrows(IllegalArgumentException.class, () -> new CG_SpecialEqualTiles(14, null));
        assertThrows(IllegalArgumentException.class, () -> new CG_3DiffTypes(-1, null));
        assertThrows(IllegalArgumentException.class, () -> new CG_8Tiles(15, null));
        assertThrows(IllegalArgumentException.class, () -> new CG_DiffHeight(19, null));
        assertThrows(IllegalArgumentException.class, () -> new CG_DiffTiles(22, null));
        assertThrows(IllegalArgumentException.class, () -> new CG_SpecialEqualTiles(17, null));
        assertThrows(IllegalArgumentException.class, () -> new CG_StdEqualTiles(20, null));
    }

    @Test
    public void constructor_CorrectInput_CorrectOutput(){
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(0, """
                Six groups each containing at least
                2 tiles of the same type (not necessarily
                in the depicted shape).
                The tiles of one group can be different
                from those of another group.""");
        assertEquals("""
                Six groups each containing at least
                2 tiles of the same type (not necessarily
                in the depicted shape).
                The tiles of one group can be different
                from those of another group.""", commonGoal.getDescription());
        assertEquals(0, commonGoal.getId());
    }

    @Test
    public void checkCommonGoal0_SetId_AssertEquals(){
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(0, "description0");
        assertEquals(commonGoal.getId(), 0);
    }
    @Test
    public void checkCommonGoal0_SetDescription_AssertEquals(){
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(0, "description0");
        assertEquals(commonGoal.getDescription(), "description0");
    }
    @Test
    public void checkCommonGoal0_SetNullTileType_ThrowsNullPointerException(){
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(0, "description0");
        assertThrows(NullPointerException.class, ()->commonGoal.checkCommonGoal(null));
    }
    @Test
    public void checkCommonGoal0_SetInvalidId_ThrowsIllegalStateException(){
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(1, "description0");
        TileType[][] playerShelf = new TileType[6][5];
        assertThrows(IllegalStateException.class, ()->commonGoal.checkCommonGoal(playerShelf));
    }
    @Test
    public void checkCommonGoal0_0groups_AssertFalse(){
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(0, "description0");
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
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(0, "description0");
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
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(0, "description0");
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
        playerShelf[3][4] = TileType.FRAME;
        playerShelf[2][4] = TileType.FRAME;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }
    @Test
    public void checkCommonGoal1_SetId_AssertEquals(){
        CG_SpecialEqualTiles cgSpecialEqualTiles = new CG_SpecialEqualTiles(1, "description1");
        assertEquals(cgSpecialEqualTiles.getId(), 1);
    }
    @Test
    public void checkCommonGoal1_SetDescription_AssertEquals(){
        CG_SpecialEqualTiles cgSpecialEqualTiles = new CG_SpecialEqualTiles(1, "description1");
        assertEquals(cgSpecialEqualTiles.getDescription(), "description1");
    }
    @Test
    public void checkCommonGoal1_SetNullTileType_ThrowsNullPointerException(){
        CG_SpecialEqualTiles cgSpecialEqualTiles = new CG_SpecialEqualTiles(1, "description1");
        assertThrows(NullPointerException.class, ()-> cgSpecialEqualTiles.checkCommonGoal(null));
    }

    @Test
    public void checkCommonGoal1_SetInvalidId_ThrowsIllegalStateException(){
        CG_SpecialEqualTiles cgSpecialEqualTiles = new CG_SpecialEqualTiles(2, "description1");
        TileType[][] tileTypes = new TileType[6][5];
        assertThrows(IllegalStateException.class, ()-> cgSpecialEqualTiles.checkCommonGoal(tileTypes));
    }

    @Test
    public void checkCommonGoal1_NoDiagonal_AssertFalse(){
        CG_SpecialEqualTiles commonGoal = new CG_SpecialEqualTiles(1, "description1");
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
        CG_SpecialEqualTiles commonGoal = new CG_SpecialEqualTiles(1, "description1");
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
        CG_SpecialEqualTiles commonGoal = new CG_SpecialEqualTiles(1, "description1");
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
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(2, "description2");
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
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(2, "description2");
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.CAT;
        playerShelf[5][2] = TileType.PLANT;
        playerShelf[5][3] = TileType.PLANT;
        playerShelf[4][0] = TileType.PLANT;
        playerShelf[4][1] = TileType.CAT;
        playerShelf[4][2] = TileType.TROPHY;
        playerShelf[4][3] = TileType.PLANT;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][1] = TileType.CAT;
        playerShelf[3][2] = TileType.TROPHY;
        playerShelf[3][3] = TileType.PLANT;
        playerShelf[2][0] = TileType.PLANT;
        playerShelf[2][1] = TileType.CAT;
        playerShelf[2][2] = TileType.TROPHY;
        playerShelf[2][3] = TileType.PLANT;
        playerShelf[1][2] = TileType.TROPHY;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal2_4HorizontalGroup_AssertTrue(){
        CG_StdEqualTiles commonGoal = new CG_StdEqualTiles(2, "description2");
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[5][1] = TileType.PLANT;
        playerShelf[5][2] = TileType.PLANT;
        playerShelf[5][3] = TileType.PLANT;
        playerShelf[4][0] = TileType.CAT;
        playerShelf[4][1] = TileType.CAT;
        playerShelf[4][2] = TileType.CAT;
        playerShelf[4][3] = TileType.CAT;
        playerShelf[3][0] = TileType.TROPHY;
        playerShelf[3][1] = TileType.TROPHY;
        playerShelf[3][2] = TileType.TROPHY;
        playerShelf[3][3] = TileType.TROPHY;
        playerShelf[2][1] = TileType.BOOK;
        playerShelf[2][2] = TileType.BOOK;
        playerShelf[2][3] = TileType.BOOK;
        playerShelf[2][4] = TileType.BOOK;
        playerShelf[1][2] = TileType.TROPHY;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }
    @Test
    public void checkCommonGoal3_SetId_AssertEquals(){
        CG_3DiffTypes commonGoal = new CG_3DiffTypes(3, "description3");
        assertEquals(commonGoal.getId(), 3);
    }
    @Test
    public void checkCommonGoal3_SetDescription_AssertEquals(){
        CG_3DiffTypes commonGoal = new CG_3DiffTypes(3, "description3");
        assertEquals(commonGoal.getDescription(), "description3");
    }
    @Test
    public void checkCommonGoal3_SetInvalidId_ThrowsIllegalStateException(){
        CG_3DiffTypes commonGoal = new CG_3DiffTypes(9, "description3");
        TileType[][] playerShelf = new TileType[6][5];
        assertThrows(IllegalStateException.class, ()->commonGoal.checkCommonGoal(playerShelf));
    }
    @Test
    public void checkCommonGoal3_SetNullTileType_ThrowsNullPointerException(){
        CG_3DiffTypes commonGoal = new CG_3DiffTypes(3, "description3");
        assertThrows(NullPointerException.class, ()->commonGoal.checkCommonGoal(null));
    }

    @Test
    public void checkCommonGoal3_3groups_AssertFalse() {
        CG_3DiffTypes commonGoal = new CG_3DiffTypes(3, "description3");
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
        playerShelf[2][0] = TileType.PLANT;
        playerShelf[2][1] = TileType.BOOK;
        playerShelf[2][2] = TileType.GAME;
        playerShelf[2][3] = TileType.CAT;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal3_4groups_AssertTrue() {
        CG_3DiffTypes commonGoal = new CG_3DiffTypes(3, "description3");
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
    public void checkCommonGoal4_NullParameter_ThrowsNullPointerException(){
        CG_4Corners cg_4Corners = new CG_4Corners(4, "description4");
        assertThrows(NullPointerException.class, () -> cg_4Corners.checkCommonGoal(null));
    }

    @Test
    public void checkCommonGoal4_SetDescription_GetCorrectDescription(){
        CG_4Corners cg_4Corners = new CG_4Corners(4, "description4");
        assertEquals(cg_4Corners.getDescription(), "description4");
    }

    @Test
    public void checkCommonGoal4_SetId_GetCorrectId(){
        CG_4Corners cg_4Corners = new CG_4Corners(4, "description4");
        assertEquals(cg_4Corners.getId(), 4);
    }
    @Test
    public void checkCommonGoal4_NoMatches_AssertFalse() {
        CG_4Corners commonGoal = new CG_4Corners(4, "description4");
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
        CG_4Corners commonGoal = new CG_4Corners(4, "description4");
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
    public void checkCommonGoal5_SetId_AssertEquals(){
        CG_DiffTiles cgDiffTiles = new CG_DiffTiles(5, "description5");
        assertEquals(cgDiffTiles.getId(), 5);
    }
    @Test
    public void checkCommonGoal5_SetDescription_AssertEquals(){
        CG_DiffTiles cgDiffTiles = new CG_DiffTiles(5, "description5");
        assertEquals(cgDiffTiles.getDescription(), "description5");
    }

    @Test
    public void checkCommonGoal5_SetNullTileType_ThrowsNullPointerException(){
        CG_DiffTiles cgDiffTiles = new CG_DiffTiles(5 , "description5");
        assertThrows(NullPointerException.class, ()-> cgDiffTiles.checkCommonGoal(null));
    }

    @Test
    public void checkCommonGoal5_SetInvalidId_AssertThrowsIllegalStateException(){
        CG_DiffTiles cgDiffTiles = new CG_DiffTiles(2, "description");
        TileType[][] tileTypes = new TileType[6][5];
        assertThrows(IllegalStateException.class, ()-> cgDiffTiles.checkCommonGoal(tileTypes));
    }
    @Test
    public void checkCommonGoal5_0groups_AssertFalse() {
        CG_DiffTiles commonGoal = new CG_DiffTiles(5, "description5");
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
        CG_DiffTiles commonGoal = new CG_DiffTiles(5, "description5");
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
        CG_SpecialEqualTiles commonGoal = new CG_SpecialEqualTiles(6, "description6");
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
        CG_SpecialEqualTiles commonGoal = new CG_SpecialEqualTiles(6, "description6");
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
        playerShelf[2][0] = TileType.GAME;
        playerShelf[2][1] = TileType.GAME;
        assertTrue(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal7_1group_AssertFalse() {
        CG_DiffTiles commonGoal = new CG_DiffTiles(7, "description7");
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
        CG_DiffTiles commonGoal = new CG_DiffTiles(7, "description7");
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
        CG_3DiffTypes commonGoal = new CG_3DiffTypes(8, "description8");
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
        playerShelf[4][2] = TileType.TROPHY;
        playerShelf[3][2] = TileType.CAT;
        playerShelf[2][2] = TileType.GAME;
        playerShelf[1][2] = TileType.PLANT;
        assertFalse(commonGoal.checkCommonGoal(playerShelf));
    }

    @Test
    public void checkCommonGoal8_3groups_AssertTrue() {
        CG_3DiffTypes commonGoal = new CG_3DiffTypes(8, "description8");
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
        CG_SpecialEqualTiles commonGoal = new CG_SpecialEqualTiles(9, "description9");
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
        CG_SpecialEqualTiles commonGoal = new CG_SpecialEqualTiles(9, "description9");
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
    public void checkCommonGoal10_SetId_GetCorrectId(){
        CG_8Tiles cg8Tiles = new CG_8Tiles(10, "description11");
        assertEquals(cg8Tiles.getId(), 10);
    }

    @Test
    public void checkCommonGoal10_SetDescription_GetCorrectDescription(){
        CG_8Tiles cg8Tiles = new CG_8Tiles(10, "description11");
        assertEquals(cg8Tiles.getDescription(), "description11");
    }

    @Test
    public void checkCommonGoal10_SetNullTileType_ThrowsNullPointerException(){
        CG_8Tiles cg8Tiles = new CG_8Tiles(10, "description");
        assertThrows(NullPointerException.class, () -> cg8Tiles.checkCommonGoal(null));
    }



    @Test
    public void checkCommonGoal10_7tiles_AssertFalse() {
        CG_8Tiles commonGoal = new CG_8Tiles(10, "description8");
        TileType[][] playerShelf = new TileType[6][5];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        playerShelf[5][0] = TileType.PLANT;
        playerShelf[4][0] = TileType.TROPHY;
        playerShelf[3][0] = TileType.PLANT;
        playerShelf[3][1] = TileType.GAME;
        playerShelf[2][1] = TileType.CAT;
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
        CG_8Tiles commonGoal = new CG_8Tiles(10, "description8");
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
    public void checkCommonGoal11_SetId_GetCorrectId(){
        CG_DiffHeight cgDiffHeight = new CG_DiffHeight(11, "description11");
        assertEquals(cgDiffHeight.getId(), 11);
    }

    @Test
    public void checkCommonGoal11_SetDescription_GetCorrectDescription(){
        CG_DiffHeight cgDiffHeight = new CG_DiffHeight(11, "description11");
        assertEquals(cgDiffHeight.getDescription(), "description11");
    }

    @Test
    public void checkCommonGoal11_SetNullTileType_ThrowsNullPointerException(){
        CG_DiffHeight cgDiffHeight = new CG_DiffHeight(11, "description11");
        assertThrows(NullPointerException.class, () -> cgDiffHeight.checkCommonGoal(null));
    }

    @Test
    public void checkCommonGoal11_NoIncreasingOrDecreasing_AssertFalse() {
        CG_DiffHeight commonGoal = new CG_DiffHeight(11, "description11");
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
        CG_DiffHeight commonGoal = new CG_DiffHeight(11, "description11");
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
        CG_DiffHeight commonGoal = new CG_DiffHeight(11, "description11");
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
