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
    public void startOfGlobalChat_CorrectBehavior() {

        assertEquals(game.getChats().get(0).getChatMembers(), game.getPlayers());

    }

    @Test
    public void newDuoChat_CorrectBehavior() {

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
    public void newMessagesInChat_CorrectBehavior () {


        ArrayList<Player> chatMembers = new ArrayList<>();
        chatMembers.add(player1);
        chatMembers.add(player2);
        ArrayList<Player> receivers1 = new ArrayList<>();
        receivers1.add(player2);
        ArrayList<Player> receivers2 = new ArrayList<>();
        receivers2.add(player4);
        ArrayList<Player> receivers3 = new ArrayList<>();
        receivers3.add(player1);
        ArrayList<Player> receivers4 = new ArrayList<>();
        receivers3.add(player3);



        Chat chat1 = new Chat(400, chatMembers);
        Message message0 = new Message("Hello World", player1, receivers1);
        Message message1 = new Message("*_*", player3, receivers2);
        Message message2 = new Message("Good luck", player2, receivers3);
        Message message3= new Message("Hi", player4, receivers4);
        Message message4= new Message("-_-", player3, receivers3);

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
        assertEquals(chat1.getMessages().get(1).getReceiver(), receivers2);
        assertEquals(chat1.getMessages().get(0).getReceiver(), receivers1);
        assertEquals(chat1.getMessages().get(4).getReceiver(), receivers3);

    }

    @Test
    public void messagesReceivedByPlayerX_CorrectBehavior () {


        ArrayList<Player> chatMembers = new ArrayList<>();
        chatMembers.add(player1);
        chatMembers.add(player2);
        ArrayList<Player> receivers1 = new ArrayList<>();
        receivers1.add(player2);
        ArrayList<Player> receivers2 = new ArrayList<>();
        receivers2.add(player4);
        ArrayList<Player> receivers3 = new ArrayList<>();
        receivers3.add(player1);
        ArrayList<Player> receivers4 = new ArrayList<>();
        receivers3.add(player3);

        Chat chat1 = new Chat(150, chatMembers);
        Message message0 = new Message("Ciao", player1, receivers1);
        Message message1 = new Message("Bye", player3, receivers2);
        Message message2 = new Message("Enjoy", player2, receivers3);
        Message message3= new Message("Hello", player4, receivers4);
        Message message4= new Message("^_^", player3, receivers3);

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
    public void getLastMessage_CorrectBehavior() {

        ArrayList<Player> chatMembers = new ArrayList<>();
        chatMembers.add(player1);
        chatMembers.add(player2);
        ArrayList<Player> receivers1 = new ArrayList<>();
        receivers1.add(player2);
        ArrayList<Player> receivers2 = new ArrayList<>();
        receivers2.add(player1);

        Chat chat1 = new Chat(20, chatMembers);
        Message message0 = new Message("Hello", player1, receivers1);
        Message message1 = new Message("Bye", player2, receivers2);

        chat1.addMessage(message0);
        chat1.addMessage(message1);

        assertEquals(chat1.getLastMessage().getMessage(), "Bye");

    }
}
