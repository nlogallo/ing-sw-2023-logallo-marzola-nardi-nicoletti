package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class ChatTest {

    Game game = new Game(0,4);
    Player player1 = new Player(false, new Shelf(), "P1", game);
    Player player2 = new Player(false, new Shelf(), "P2", game);
    Player player3 = new Player(false, new Shelf(), "P3", game);
    Player player4 = new Player(false, new Shelf(), "P4", game);


    @Before
    public void setUp() {

        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

    }

    @Test
    public void test1_checkCorrectStartOfGlobalChat_correctOutput() {

        assertEquals(game.getChats().get(0).getChatMembers(), game.getPlayers());

    }

    @Test
    public void test2_newDuoChat_correctOutput() {

        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);

        game.startDuoChat(player1, player2);
        assertEquals(game.getChats().get(1).getId(), 1);
        assertEquals(game.getChats().get(1).getChatMembers(), playersList);
        assertEquals(game.getChats().get(0).getChatMembers(), game.getPlayers());
        assertEquals(game.getChats().get(0).getId(), 0);

    }


    @Test
    public void test3_newMessagesInChat () {


        ArrayList<Player> chatMembers = new ArrayList<>();
        chatMembers.add(player1);
        chatMembers.add(player2);

        Chat chat1 = new Chat(400, chatMembers);
        Message message0 = new Message("Hello World", player1, player2);
        Message message1 = new Message("*_*", player3, player4);
        Message message2 = new Message("Good luck", player2, player1);
        Message message3= new Message("Hi", player4, player3);
        Message message4= new Message("-_-", player3, player1);

        chat1.addMessage(message0);
        chat1.addMessage(message1);
        chat1.addMessage(message2);
        chat1.addMessage(message3);
        chat1.addMessage(message4);

        assertEquals(chat1.getMessages().get(0).getMessage(), "Hello World");
        assertEquals(chat1.getMessages().get(3).getMessage(), "Hi");
        assertEquals(chat1.getMessages().get(2).getSender(), player2);
        assertEquals(chat1.getMessages().get(4).getSender(), player3);
        assertEquals(chat1.getMessages().get(3).getSender(), player4);
        assertEquals(chat1.getMessages().get(1).getReceiver(), player4);
        assertEquals(chat1.getMessages().get(0).getReceiver(), player2);
        assertEquals(chat1.getMessages().get(4).getReceiver(), player1);

    }

    @Test
    public void Test3_checkMessagesReceivedByPlayerX_correctOutput () {


        ArrayList<Player> chatMembers = new ArrayList<>();
        chatMembers.add(player1);
        chatMembers.add(player2);

        Chat chat1 = new Chat(150, chatMembers);
        Message message0 = new Message("Ciao", player1, player2);
        Message message1 = new Message("Bye", player3, player4);
        Message message2 = new Message("Enjoy", player2, player1);
        Message message3= new Message("Hello", player4, player3);
        Message message4= new Message("^_^", player3, player1);

        chat1.addMessage(message0);
        chat1.addMessage(message1);
        chat1.addMessage(message2);
        chat1.addMessage(message3);
        chat1.addMessage(message4);

        ArrayList<Message> messageReceiveByPlayer1 = new ArrayList<>();
        messageReceiveByPlayer1.add(message2);
        messageReceiveByPlayer1.add(message4);

        assertEquals(chat1.getMessagesReceivedByPlayerX(player1), messageReceiveByPlayer1);

    }


    @Test
    public void Test4_checkGetLastMessage_correctOutput() {

        ArrayList<Player> chatMembers = new ArrayList<>();
        chatMembers.add(player1);
        chatMembers.add(player2);

        Chat chat1 = new Chat(20, chatMembers);
        Message message0 = new Message("Hello", player1, player2);
        Message message1 = new Message("Bye", player2, player1);

        chat1.addMessage(message0);
        chat1.addMessage(message1);

        assertEquals(chat1.getLastMessage().getMessage(), "Bye");

    }
}
