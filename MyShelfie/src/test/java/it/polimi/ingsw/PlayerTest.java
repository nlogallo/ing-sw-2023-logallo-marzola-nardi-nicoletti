package it.polimi.ingsw;
import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class PlayerTest {

    Player player = null;

    @BeforeEach
    public void setUp(){
        player = new Player(false, new Shelf(), "username", null);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void adjacentTilesPoints_CorrectBehavior() {
        TileType[][] playerShelf1 = {{TileType.PLANT, TileType.PLANT, TileType.EMPTY, TileType.GAME, TileType.PLANT},
                                    {TileType.PLANT, TileType.PLANT, TileType.PLANT, TileType.PLANT, TileType.CAT},
                                    {TileType.TROPHY, TileType.TROPHY, TileType.PLANT, TileType.PLANT, TileType.PLANT},
                                    {TileType.TROPHY, TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.TROPHY, TileType.GAME, TileType.CAT, TileType.CAT, TileType.CAT},
                                    {TileType.GAME, TileType.GAME, TileType.GAME, TileType.CAT, TileType.CAT}};

        assertSame(19, player.adjacentTilesPoints(playerShelf1));

        TileType[][] playerShelf2 = {{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.PLANT, TileType.EMPTY, TileType.PLANT, TileType.PLANT, TileType.EMPTY},
                                    {TileType.PLANT, TileType.EMPTY, TileType.EMPTY, TileType.PLANT, TileType.EMPTY},
                                    {TileType.PLANT, TileType.EMPTY, TileType.PLANT, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.PLANT, TileType.PLANT, TileType.PLANT, TileType.EMPTY, TileType.EMPTY}};

        assertSame(10, player.adjacentTilesPoints(playerShelf2));

    }

    @Test
    void calculatePoints_WithTokens_CorrectBehavior() {
        player.calculatePoints();
        assertSame(0, player.getPoints());

        player.giveToken(new Token(8));
        player.giveToken(new Token(3));
        player.calculatePoints();

        assertSame(12, player.getPoints());

    }

    @Test
    void calculatePoints_WithPersonalGoal_CorrectBehavior() {
        TileType[][] personalGoal = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},
                                    {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},
                                    {TileType.FRAME, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.GAME, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        player.setPersonalGoal(new PersonalGoal(personalGoal));
        ArrayList <Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(50));
        tiles.add(new Tile(20));
        tiles.add(new Tile(70));

        player.makeMove(0, tiles);
        player.calculatePoints();
        assertSame(4, player.getPoints());

        player.giveToken(new Token(8));
        player.giveToken(new Token(3));
        player.calculatePoints();

        assertSame(16, player.getPoints());
    }

    @Test
    void hasSeat_CorrectBehavior () {
        Player p = new Player(true, new Shelf(), "p", null);
        assertEquals(true, p.hasSeat());
        assertEquals(false, player.hasSeat());

    }

    @Test
    void checkFirstToEnd_NotFullShelf_CorrectBehavior(){
        Player p = new Player(true, new Shelf(), "p", new Game(0, 2));
        boolean hasEndGameToken = false;
        p.checkFirstToEnd();
        for (Token t: player.getTokenCards()
             ) {
            if (t.getPoints() == 1)
                hasEndGameToken = true;
        }
        assertEquals(false, hasEndGameToken);
    }
    @Test
    void checkFirstToEnd_FullShelf_CorrectBehavior(){
        Player p = new Player(true, new Shelf(), "p", new Game(0, 2));

        ArrayList <Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(50));
        tiles.add(new Tile(20));
        tiles.add(new Tile(70));
        for (int i = 0; i<5; i++) {
            p.makeMove(i, tiles);
            p.makeMove(i, tiles);
        }

        assertEquals(true, p.getShelf().isFull());

        boolean hasEndGameToken = false;
        p.checkFirstToEnd();
        for (int i = 0; i< p.getTokenCards().size(); i++) {
            if (p.getTokenCards().get(i).getId() == 0)
                hasEndGameToken = true;
        }
        assertEquals(true, hasEndGameToken);
    }

    @Test
    void makeMove_CorrectBehavior() {
        ArrayList <Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(50));
        tiles.add(new Tile(20));
        tiles.add(new Tile(70));

        player.makeMove(0, tiles);

        assertEquals(50, player.getShelf().getTile(5, 0).getID());
        assertEquals(20, player.getShelf().getTile(4, 0).getID());
        assertEquals(70, player.getShelf().getTile(3, 0).getID());

    }

    @Test
    void getPersonalGoal_CorrectBehavior() {
        TileType[][] personalGoal = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},
                {TileType.FRAME, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.GAME, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        player.setPersonalGoal(new PersonalGoal(personalGoal));

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(personalGoal[i][j], player.getPersonalGoal().getMatrix()[i][j]);
            }
        }
    }

    @Test
    void getState_CorrectBehavior() {
        player.setState(State.IS_PLAYING);
        assertEquals(State.IS_PLAYING, player.getState());
    }

    @Test
    void setSeat_CorrectBehavior() {
        player.setSeat(false);
        assertEquals(false, player.hasSeat());

        player.setSeat(true);
        assertEquals(true, player.hasSeat());
    }

    @Test
    void getNickname_CorrectBehavior() {
        Player p = new Player(true, new Shelf(), "Nick", new Game(0, 2));
        assertEquals("Nick", p.getNickname());
    }

    @Test
    void setTrueCommonGoals_CorrectBehavior() {
        assertEquals(false, player.getCommonGoals(0));
        assertEquals(false, player.getCommonGoals(1));

        player.setTrueCommonGoals(0);
        player.setTrueCommonGoals(1);

        assertEquals(true, player.getCommonGoals(0));
        assertEquals(true, player.getCommonGoals(1));

    }



}