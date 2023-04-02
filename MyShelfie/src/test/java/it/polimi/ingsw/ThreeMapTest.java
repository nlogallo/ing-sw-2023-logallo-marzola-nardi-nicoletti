package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CG_DiffHeight;
import it.polimi.ingsw.model.commonGoal.CG_StdEqualTiles;
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
        State state = new State() {};
        ArrayList<Player> players = new ArrayList<>();
        Game game0 = new Game(0, players);
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", state, personalGoal0, game0);
        Player player1 = new Player(false, new Shelf(), "player1", state, personalGoal0, game0);
        Player player2 = new Player(false, new Shelf(), "player2", state, personalGoal0, game0);
        Player player3 = new Player(false, new Shelf(), "player3", state, personalGoal0, game0);
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
    public void addKey_WrongInput_ThrowIllegalArgumentException(){
        ThreeMap threeMap = new ThreeMap(4);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        Token token0 = new Token(8);
        State state = new State() {};
        ArrayList<Player> players = new ArrayList<>();
        Game game0 = new Game(0, players);
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", state, personalGoal0, game0);
        Player player1 = new Player(false, new Shelf(), "player1", state, personalGoal0, game0);
        Player player2 = new Player(false, new Shelf(), "player2", state, personalGoal0, game0);
        Player player3 = new Player(false, new Shelf(), "player3", state, personalGoal0, game0);
        threeMap.addKey(commonGoal0, token0);
        threeMap.setPlayer(commonGoal0, player0);
        Token token1 = new Token(6);
        threeMap.setPlayer(commonGoal0, player1);
        Token token2 = new Token(4);
        threeMap.setPlayer(commonGoal0, player2);
        Token token3 = new Token(2);
        threeMap.setPlayer(commonGoal0, player3);
        assertThrows(IllegalArgumentException.class, () -> threeMap.setPlayer(commonGoal0, player1));
    }

    @Test
    public void hasToken_Token_AssertTrue(){
        ThreeMap threeMap = new ThreeMap(4);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        Token token0 = new Token(7);
        ArrayList<Player> players = new ArrayList<>();
        Game game0 = new Game(0, players);
        State state = new State() {};
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", state, personalGoal0, game0);
        threeMap.addKey(commonGoal0, token0);
        threeMap.setPlayer(commonGoal0, player0);
        assertTrue(threeMap.hasToken(commonGoal0, player0));
    }

    @Test
    public void hasToken_Token_AssertFalse(){
        ThreeMap threeMap = new ThreeMap(4);
        CG_StdEqualTiles commonGoal0 = new CG_StdEqualTiles( 0, "description0");
        Token token0 = new Token(7);
        ArrayList<Player> players = new ArrayList<>();
        Game game0 = new Game(0, players);
        State state = new State() {};
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", state, personalGoal0, game0);
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
        Game game0 = new Game(0, players);
        State state = new State() {};
        TileType[][] matrix = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal0 = new PersonalGoal(matrix);
        Player player0 = new Player(false, new Shelf(), "player0", state, personalGoal0, game0);
        Player player1 = new Player(false, new Shelf(), "player1", state, personalGoal0, game0);
        assertEquals(token0.getId(), threeMap.setPlayer(commonGoal0, player0).getId());
        assertEquals(token0.getId() - 2, threeMap.setPlayer(commonGoal0, player1).getId());
        assertTrue(threeMap.hasToken(commonGoal0,player0));
        assertTrue(threeMap.hasToken(commonGoal0,player0));
        assertFalse(threeMap.hasToken(commonGoal1, player0));
        assertFalse(threeMap.hasToken(commonGoal1, player1));
    }
}
