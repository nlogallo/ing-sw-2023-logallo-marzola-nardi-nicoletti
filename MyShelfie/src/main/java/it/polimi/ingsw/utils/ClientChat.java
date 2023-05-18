package it.polimi.ingsw.utils;

import java.sql.Timestamp;
import java.util.ArrayList;


/**
 * This class represents the Chat on Client Side, in which the players are recognized
 * via Strings to keep their personal information hidden on the client side
 */
public class ClientChat {

    private final int id;
    private final ArrayList<ClientMessage> chat = new ArrayList<>();
    private final ArrayList<String> playerList = new ArrayList<>();

    public ClientChat(int id, ArrayList<String> playerList) {
        this.id = id;
        this.playerList.addAll(playerList);
    }


    /**
     * Getter method
     * @return the id of the chat instance
     */
    public int getId() {
        return this.id;
    }


    /**
     * Getter method
     * @return an arrayList with the chat members
     */
    public ArrayList<String> getChatMembers() {
        return this.playerList;
    }


    /**
     * Getter method
     * @return an arrayList that represents the chat instance
     */
    public ArrayList<ClientMessage> getClientMessages() {
        return this.chat;
    }


    /**
     * This method adds the new player (String) in the chat instance
     * @param player is the new player (String)
     */
    public void addPlayerToChat(String player) {
        this.playerList.add(player);
    }


    /**
     * This method adds the new message (String) in the arrayList contained all messages (String) of the chat instance
     * @param message is the new message (String). The format of the new message (String) is: sender&&&receiver&&&text
     */
    public void addMessage(String message, String sender, ArrayList<String> receivers, Timestamp timestamp) {
        this.chat.add(new ClientMessage(message, sender, receivers, timestamp));

    }
}
