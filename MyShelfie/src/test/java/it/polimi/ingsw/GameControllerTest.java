package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GameControllerTest {

    GameController gameController;

    Game game = new Game(400, 3);
    Player player1 = new Player(true, new Shelf(), "first", game);
    Player player2 = new Player(false, new Shelf(), "second", game);
    Player player3 = new Player(false, new Shelf(), "third", game);

    @Before
    public void setUp(){
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.startGame();
        gameController = new GameController(game, "first");
    }

    @Test
    public void getNicknameToPlayer_NicknameExists_AssertTrue(){
        Player player = gameController.nicknameToPlayer("first");
        assertTrue(gameController.getPlayers().contains(player));
    }

    @Test
    public void getNicknameToPlayer_NicknameDoesntExist_AssertNull(){
        Player player = gameController.nicknameToPlayer("fifth");
        assertNull(player);
    }

    @Test
    public void nextPhase_NoEndGame_AssertEquals(){
        gameController.nextPhase();
        assertEquals(gameController.getCurrentPlayer(), "second");
    }

    @Test
    public void nextPhase_EndGame_AssertEquals(){
        Game game1 = new Game(401, 3);
        Player player11 = new Player(true, new Shelf(), "first", game1);
        Player player22 = new Player(false, new Shelf(), "second", game1);
        Player player33 = new Player(false, new Shelf(), "third", game1);
        game1.addPlayer(player11);
        game1.addPlayer(player22);
        game1.addPlayer(player33);
        game1.startGame();
        GameController gameController1 = new GameController(game1, "first");
        gameController1.nextPhase();
        gameController1.nextPhase();
        game1.giveFirstToEndToken(player3);
        gameController1.nextPhase();
        assertEquals(gameController1.getCurrentPlayer(), "Game ended");
        assertEquals(gameController1.getWinner(), game1.winner());
    }

    @Test
    public void moveTiles_CorrectPositions_AssertEquals(){
        ArrayList<Position> arrayList = new ArrayList<>();
        arrayList.add(new Position(0, 3));
        arrayList.add(new Position(1, 3));
        assertEquals(gameController.moveTiles(arrayList, player1, 1), "Tiles moved");
    }

    @Test
    public void moveTiles_InvalidPositions_AssertEquals(){
        ArrayList<Position> arrayList = new ArrayList<>();
        arrayList.add(new Position(8, 3));
        arrayList.add(new Position(1, 3));
        assertEquals(gameController.moveTiles(arrayList, player1, 1), "Tiles cannot be pulled");
    }

    @Test
    public void updateToken_CommonGoalCompleted_AssertEquals(){
        TileType[][] playerShelf = new TileType[6][5];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                playerShelf[i][j] = TileType.EMPTY;
        switch (game.getCommonGoals().get(0).getId()){
            case 0:
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
                break;
            case 1:
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
                break;
            case 2:
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
                break;
            case 3:
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
                break;
            case 4:
                playerShelf[5][0] = TileType.GAME;
                playerShelf[0][0] = TileType.GAME;
                playerShelf[0][4] = TileType.GAME;
                playerShelf[5][4] = TileType.GAME;
                break;
            case 5:
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
                break;
            case 6:
                playerShelf[5][0] = TileType.GAME;
                playerShelf[4][0] = TileType.GAME;
                playerShelf[5][1] = TileType.GAME;
                playerShelf[4][1] = TileType.GAME;
                playerShelf[3][0] = TileType.GAME;
                playerShelf[3][1] = TileType.GAME;
                playerShelf[2][0] = TileType.GAME;
                playerShelf[2][1] = TileType.GAME;
                break;
            case 7:
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
                break;
            case 8:
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
                break;
            case 9:
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
                break;
            case 10:
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
                break;
            case 11:
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
                break;
        }
        assertEquals(gameController.updateToken(gameController.getCommonGoal().get(0), playerShelf, player1), "Congratulations, you've have earned a new Token: 8");
    }

    @Test
    public void updateToken_NullPlayerShelf_AssertEquals(){
        assertEquals(gameController.updateToken(game.getCommonGoals().get(0), null, player1), "An error has occurred");
    }

    @Test
    public void startDuoChat_CorrectInitialization_AssertTrue(){
        gameController.startDuoChat(player1, player2);
        assertTrue(game.getChats().get(1).getChatMembers().contains(player1) && game.getChats().get(1).getChatMembers().contains(player2));
    }

    @Test
    public void getCommonGoals_CorrectList_AssertEquals(){
        assertEquals(game.getCommonGoals(), gameController.getCommonGoal());
    }

    @Test
    public void getBoard_CorrectBoard_AssertEquals(){
        assertEquals(game.getBoard(), gameController.getBoard());
    }

    @Test
    public void getGame_CorrectGame_AssertEquals(){
        assertEquals(gameController.getGame(), game);
    }

    @Test
    public void getPlayersShelves_CorrectShelves_AssertEquals(){
        Map<String, Shelf> shelvesMap = new HashMap<>();
        for(Player p : game.getPlayers())
            shelvesMap.put(p.getNickname(), p.getShelf());
        assertEquals(gameController.getPlayersShelves(), shelvesMap);
    }

    @Test
    public void revertMutex_AllMutexAtFalse_AssertTrue(){
        gameController.revertMutex();
        for(int i = 0; i < game.getPlayers().size(); i++)
            assertTrue(game.getMutexAtIndex(i));
    }

}
