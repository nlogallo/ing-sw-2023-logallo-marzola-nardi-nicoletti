package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ThreeMapTest {

    @Test
    public void constructor_InvalidNumberOfPlayers_ThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new ThreeMap(5));
    }

    @Test
    public void addKey_setPlayer_CorrectInput_CorrectOutput(){
        ThreeMap threeMap = new ThreeMap(4);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        Token token0 = new Token(8);
        ArrayList<Player> players = new ArrayList<>();
        Game game0 = new Game(0,2);
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", game0);
        Player player1 = new Player(false, new Shelf(), "player1", game0);
        Player player2 = new Player(false, new Shelf(), "player2", game0);
        Player player3 = new Player(false, new Shelf(), "player3", game0);
        threeMap.addKey(commonGoal0, token0);
        assertEquals(token0.getId(), threeMap.setPlayer(commonGoal0, player0).getId());
        Token token1 = new Token(6);
        assertEquals(token1.getId(), threeMap.setPlayer(commonGoal0, player1).getId());
        Token token2 = new Token(4);
        assertEquals(token2.getId(), threeMap.setPlayer(commonGoal0, player2).getId());
        Token token3= new Token(2);
        assertEquals(token3.getId(), threeMap.setPlayer(commonGoal0, player3).getId());
    }

    @Test
    public void addKey_set2Player_CorrectInput_CorrectOutput(){
        ThreeMap threeMap = new ThreeMap(2);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        Token token0 = new Token(8);
        ArrayList<Player> players = new ArrayList<>();
        Game game0 = new Game(0,2);
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", game0);
        Player player1 = new Player(false, new Shelf(), "player1", game0);
        threeMap.addKey(commonGoal0, token0);
        assertEquals(token0.getId(), threeMap.setPlayer(commonGoal0, player0).getId());
        Token token1 = new Token(4);
        assertEquals(token1.getId(), threeMap.setPlayer(commonGoal0, player1).getId());
    }

    @Test
    public void addKey_WrongInput_ThrowIllegalArgumentException(){
        ThreeMap threeMap = new ThreeMap(4);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        Token token0 = new Token(8);
        ArrayList<Player> players = new ArrayList<>();
        Game game0 = new Game(0,2);
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", game0);
        Player player1 = new Player(false, new Shelf(), "player1", game0);
        Player player2 = new Player(false, new Shelf(), "player2", game0);
        Player player3 = new Player(false, new Shelf(), "player3", game0);
        threeMap.addKey(commonGoal0, token0);
        assertThrows(IllegalArgumentException.class, () -> threeMap.setPlayer(new CG_DiffTiles(11, "description1"), player1));
    }

    @Test
    public void hasToken_Token_AssertTrue(){
        ThreeMap threeMap = new ThreeMap(4);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        Token token0 = new Token(7);
        ArrayList<Player> players = new ArrayList<>();
        Game game0 = new Game(0,2);
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", game0);
        threeMap.addKey(commonGoal0, token0);
        threeMap.setPlayer(commonGoal0, player0);
        assertTrue(threeMap.hasToken(commonGoal0, player0));
    }

    @Test
    public void hasToken_Token_AssertFalse(){
        ThreeMap threeMap = new ThreeMap(2);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        Token token0 = new Token(7);
        ArrayList<Player> players = new ArrayList<>();
        Game game0 = new Game(0,2);
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", game0);
        threeMap.addKey(commonGoal0, token0);
        assertFalse(threeMap.hasToken(commonGoal0, player0));
    }

    @Test
    public void allClass_2Token_AssertTrue(){
        ThreeMap threeMap = new ThreeMap(4);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        CG_DiffHeight commonGoal1 = new CG_DiffHeight(11, "description11");
        Token token0 = new Token(7);
        Token token1 = new Token(8);
        threeMap.addKey(commonGoal0, token0);
        threeMap.addKey(commonGoal1, token1);
        ArrayList<Player> players = new ArrayList<>();
        Game game0 = new Game(0,2);
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", game0);
        Player player1 = new Player(false, new Shelf(), "player1", game0);
        assertEquals(token0.getId(), threeMap.setPlayer(commonGoal0, player0).getId());
        assertEquals(token0.getId() - 2, threeMap.setPlayer(commonGoal0, player1).getId());
        assertTrue(threeMap.hasToken(commonGoal0,player0));
        assertTrue(threeMap.hasToken(commonGoal0,player0));
        assertFalse(threeMap.hasToken(commonGoal1, player0));
        assertFalse(threeMap.hasToken(commonGoal1, player1));
    }

    @Test
    public void getCommonGoals_id_ascending_order_AssertEquals(){
        ThreeMap threeMap = new ThreeMap(2);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        CG_DiffHeight commonGoal1 = new CG_DiffHeight(11, "description11");
        Token token = new Token(7);
        Token token1 = new Token(8);
        threeMap.addKey(commonGoal0, token);
        threeMap.addKey(commonGoal1, token1);
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add(commonGoal1);
        commonGoals.add(commonGoal0);
        ArrayList<CommonGoal> givenCommonGoals = threeMap.getCommonGoals();
        assertEquals(givenCommonGoals, commonGoals);
    }

    @Test
    public void getCommonGoals_id_descending_order_AssertEquals(){
        ThreeMap threeMap = new ThreeMap(2);
        CG_StdEqualTiles commonGoal1 = new CG_StdEqualTiles( 0, "description0");
        CG_DiffHeight commonGoal0 = new CG_DiffHeight(11, "description11");
        Token token = new Token(7);
        Token token1 = new Token(8);
        threeMap.addKey(commonGoal0, token);
        threeMap.addKey(commonGoal1, token1);
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add(commonGoal1);
        commonGoals.add(commonGoal0);
        ArrayList<CommonGoal> givenCommonGoals = threeMap.getCommonGoals();
        assertEquals(givenCommonGoals, commonGoals);
    }

    @Test
    public void getCommonGoals_AssertEquals(){
        ThreeMap threeMap = new ThreeMap(2);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        CG_DiffHeight commonGoal1 = new CG_DiffHeight(11, "description11");
        Token token = new Token(7);
        Token token1 = new Token(8);
        threeMap.addKey(commonGoal0, token);
        threeMap.addKey(commonGoal1, token1);
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add(commonGoal1);
        commonGoals.add(commonGoal0);
        ArrayList<CommonGoal> givenCommonGoals = threeMap.getCommonGoals();
        assertEquals(givenCommonGoals, commonGoals);
    }

    @Test
    public void getRemainingTokenList_2players_AssertTrue(){
        ThreeMap threeMap = new ThreeMap(2);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        CG_DiffHeight commonGoal1 = new CG_DiffHeight(11, "description11");
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add(commonGoal0);
        commonGoals.add(commonGoal1);
        Token token = new Token(7);
        Token token1 = new Token(8);
        threeMap.addKey(commonGoal0, token);
        threeMap.addKey(commonGoal1, token1);
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(8);
        integers.add(7);
        integers.add(4);
        integers.add(3);
        for(Token t : threeMap.getRemainingTokenList(commonGoals, 2)){
            assertTrue(integers.contains(t.getId()));
        }
    }

    @Test
    public void getRemainingTokenList_3players_AssertTrue(){
        ThreeMap threeMap = new ThreeMap(3);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        CG_DiffHeight commonGoal1 = new CG_DiffHeight(11, "description11");
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add(commonGoal0);
        commonGoals.add(commonGoal1);
        Token token = new Token(7);
        Token token1 = new Token(8);
        threeMap.addKey(commonGoal0, token);
        threeMap.addKey(commonGoal1, token1);
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(8);
        integers.add(7);
        integers.add(6);
        integers.add(5);
        integers.add(4);
        integers.add(3);
        for(Token t : threeMap.getRemainingTokenList(commonGoals, 3)){
            assertTrue(integers.contains(t.getId()));
        }
    }

    @Test
    public void getRemainingTokenList_4players_AssertTrue(){
        ThreeMap threeMap = new ThreeMap(4);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        CG_DiffHeight commonGoal1 = new CG_DiffHeight(11, "description11");
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add(commonGoal0);
        commonGoals.add(commonGoal1);
        Token token = new Token(7);
        Token token1 = new Token(8);
        threeMap.addKey(commonGoal0, token);
        threeMap.addKey(commonGoal1, token1);
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(8);
        integers.add(7);
        integers.add(6);
        integers.add(5);
        integers.add(4);
        integers.add(3);
        integers.add(2);
        integers.add(1);
        for(Token t : threeMap.getRemainingTokenList(commonGoals, 4)){
            assertTrue(integers.contains(t.getId()));
        }
    }
    @Test
    public void setInvalidPlayersNumber_ThrowsIllegalArgumentException(){
        ThreeMap threeMap = new ThreeMap(2);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        CG_DiffHeight commonGoal1 = new CG_DiffHeight(11, "description11");
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add(commonGoal0);
        commonGoals.add(commonGoal1);
        Token token = new Token(7);
        Token token1 = new Token(8);
        threeMap.addKey(commonGoal0, token);
        threeMap.addKey(commonGoal1, token1);
        assertThrows(IllegalArgumentException.class,()->threeMap.getRemainingTokenList(commonGoals, 1));
    }

    @Test
    public void getRemainingTokenListEmpty_AssertEquals(){
        ThreeMap threeMap = new ThreeMap(2);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        CG_DiffHeight commonGoal1 = new CG_DiffHeight(11, "description11");
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add(commonGoal0);
        commonGoals.add(commonGoal1);
        assertEquals(threeMap.getRemainingTokenList(commonGoals, 2).size(), 0);
    }

    @Test
    public void addExistingCommonGoal_ThrowsIllegalArgumentException(){
        ThreeMap threeMap = new ThreeMap(2);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        CG_DiffHeight commonGoal1 = new CG_DiffHeight(11, "description11");
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add(commonGoal0);
        commonGoals.add(commonGoal1);
        threeMap.addKey(commonGoal0, new Token(8));
        assertThrows(IllegalArgumentException.class,()->threeMap.addKey(commonGoal0, new Token(7)));
    }

    @Test
    public void hasToken_NoCommonGoalExisting_AssertTrue(){
        ThreeMap threeMap = new ThreeMap(4);
        CG_DiffHeight commonGoal1 = new CG_DiffHeight(11, "description11");
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        CG_4Corners cg_4Corners = new CG_4Corners(4, "description4");
        Token token0 = new Token(7);
        Token token1 = new Token(8);
        threeMap.addKey(commonGoal0, token0);
        threeMap.addKey(commonGoal1, token1);
        Game game0 = new Game(0, 2);
        Player player0 = new Player(false, new Shelf(), "player0", game0);
        assertTrue(threeMap.hasToken(cg_4Corners,player0));
    }


}

