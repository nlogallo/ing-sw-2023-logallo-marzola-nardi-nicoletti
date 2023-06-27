package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    Game game = new Game(6, 3);
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
    public void checkMessage_correctBehaviour() {

        ArrayList<Player> receiversMessage1 = new ArrayList<>();
        ArrayList<Player> receiversMessage2 = new ArrayList<>();
        ArrayList<Player> receiversMessage3 = new ArrayList<>();

        receiversMessage1.add(player2);
        Message message1 = new Message("Hello World!", player1, receiversMessage1);

        receiversMessage2.add(player3);
        receiversMessage2.add(player4);
        Message message2 = new Message("*_*", player1, receiversMessage2);

        receiversMessage3.add(player2);
        receiversMessage3.add(player3);
        receiversMessage3.add(player4);
        Message message3 = new Message("^__^", player1, receiversMessage3);

        assertEquals(message1.getMessage(), "Hello World!");
        assertEquals(message2.getMessage(), "*_*");
        assertEquals(message3.getMessage(), "^__^");

        ArrayList<Player> checkList1 = new ArrayList<>();
        checkList1.add(player2);

        ArrayList<Player> checkList2 = new ArrayList<>();
        checkList2.add(player3);
        checkList2.add(player4);

        ArrayList<Player> checkList3 = new ArrayList<>();
        checkList3.add(player2);
        checkList3.add(player3);
        checkList3.add(player4);

        Timestamp timestamp = message1.getTimestamp();

        assertEquals(message1.getReceiver(), checkList1);
        assertEquals(message2.getReceiver(), checkList2);
        assertEquals(message3.getReceiver(), checkList3);
        assertEquals(message1.getSender(), player1);
        assertEquals(message2.getSender(), player1);
        assertEquals(message3.getSender(), player1);

    }






}
