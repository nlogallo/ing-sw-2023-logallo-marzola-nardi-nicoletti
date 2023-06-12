package it.polimi.ingsw;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.NetworkMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ServerControllerTest {

    ServerController serverController;
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
      serverController = new ServerController(game,"testNickname");
    }

    @Test
    public void getGame_AssertEquals(){
        assertEquals(serverController.getGame(), game);
    }

    @Test
    public void makeMove_AssertEquals_NetworkMessage(){
        ArrayList<Position> positions = new ArrayList<Position>();
        positions.add(new Position(0,3));
        positions.add(new Position(1, 3));
        NetworkMessage networkMessage = serverController.makeMove(positions, player1, 1);
        assertEquals(networkMessage.getTextMessage(), "Well done!");
        assertEquals(networkMessage.getRequestId(), "UPS");
        assertEquals(networkMessage.getContent().get(0), player1.getShelf());
        assertEquals(networkMessage.getContent().get(1), game.getBoard());
        assertEquals(networkMessage.getContent().get(2), player1.getTokenCards());
    }

    @Test
    public void makeMove_PlayerNull_AssertEquals_NetworkMessage(){
        ArrayList<Position> positions = new ArrayList<Position>();
        positions.add(new Position(0,3));
        positions.add(new Position(1, 3));
        NetworkMessage networkMessage = serverController.makeMove(positions, null, 1);
        assertEquals(networkMessage.getTextMessage(), "Something went wrong, retry");
    }

    @Test
    public void addMessage_AssertEquals_NetworkMessage(){
        ArrayList<String> nicknameList = new ArrayList<>();
        nicknameList.add("first");
        nicknameList.add("second");
        nicknameList.add("third");
        NetworkMessage networkMessage = serverController.addMessage("first", nicknameList, "textMessage");
        assertEquals(networkMessage.getRequestId(), "UC");
        assertEquals(networkMessage.getTextMessage(), "Message sent correctly!");
        nicknameList.remove("first");
        nicknameList.remove("third");
        NetworkMessage networkMessage2 = serverController.addMessage("first", nicknameList, "textMessage2");
        assertEquals(networkMessage2.getRequestId(), "UC");
        nicknameList.remove("second");
        nicknameList.add("third");
        NetworkMessage networkMessage3 = serverController.addMessage("first", nicknameList, "textMessage3");
        assertEquals(networkMessage3.getRequestId(), "UC");
        NetworkMessage networkMessage4 = serverController.addMessage("first", nicknameList, "textMessage3.1");
        assertEquals(networkMessage4.getRequestId(), "UC");
    }

    @Test
    public void getBoard_AssertEquals_NetworkMessage(){
        NetworkMessage networkMessage = serverController.getBoard();
        assertEquals(networkMessage.getRequestId(), "UB");
        assertEquals(networkMessage.getTextMessage(), "This is your Board");
        assertEquals(networkMessage.getContent().get(0), game.getBoard());
    }

    @Test
    public void getGameTokens_AssertTrue_NetworkMessage(){
        NetworkMessage networkMessage = serverController.getGameTokens();
        assertEquals(networkMessage.getRequestId(), "UGT");
        assertEquals(networkMessage.getTextMessage(), "Game Token Update");
        ArrayList<Token> remainingTokenList = game.getThreeMap().getRemainingTokenList(game.getCommonGoals(), 3);
        for(int i = 0; i < remainingTokenList.size(); i++) {
            Token token = (Token) networkMessage.getContent().get(i + 1);
            assertEquals(token.getId(), remainingTokenList.get(i).getId());
        }
    }

    @Test
    public void playerSetup_AssertEquals_NetworkMessage(){
        NetworkMessage networkMessage = serverController.playerSetUp("first");
        ArrayList<Token> remainingTokenList = game.getThreeMap().getRemainingTokenList(game.getCommonGoals(), 3);
        remainingTokenList.add(new Token(0));
        assertEquals(networkMessage.getRequestId(), "PS");
        assertEquals(networkMessage.getTextMessage(), "first, welcome to the Game. Enjoy it!");
        assertEquals(networkMessage.getContent().get(0), game.getBoard());
        assertEquals(networkMessage.getContent().get(1), player1.getShelf());
        assertEquals(networkMessage.getContent().get(2), player1.getPersonalGoal());
        assertEquals(networkMessage.getContent().get(3), game.getCommonGoals().get(0));
        assertEquals(networkMessage.getContent().get(4), game.getCommonGoals().get(1));
        assertEquals(networkMessage.getContent().get(5), true);
        assertEquals(networkMessage.getContent().get(6), 2);
        assertEquals(networkMessage.getContent().get(7), "second");
        assertEquals(networkMessage.getContent().get(8), "third");
        assertEquals(networkMessage.getContent().get(9), remainingTokenList.size());
        for(int i = 0; i < remainingTokenList.size(); i++) {
            Token token = (Token) networkMessage.getContent().get(10 + i);
            assertEquals(token.getId(), remainingTokenList.get(i).getId());
        }
    }

    @Test
    public void updateResult_NoGameEnded_AssertEquals_NetworkMessage(){
        NetworkMessage networkMessage = serverController.updateResult();
        Map<String, Shelf> shelvesMap = new HashMap<>();
        for(Player p : game.getPlayers())
            shelvesMap.put(p.getNickname(), p.getShelf());
        assertEquals(networkMessage.getRequestId(), "UR");
        assertEquals(networkMessage.getTextMessage(), "first is your turn!");
        assertEquals(networkMessage.getContent().get(0), game.getCurrentPlayer().getNickname());
        assertEquals(networkMessage.getContent().get(1), shelvesMap);
    }

    @Test
    public void updateResult_GameEnded_AsssertEquals_NetworkMessage(){
        game.endGame();
        NetworkMessage networkMessage = serverController.updateResult();
        assertEquals(networkMessage.getRequestId(), "END");
        assertEquals(networkMessage.getTextMessage(), game.winner());
    }

}
