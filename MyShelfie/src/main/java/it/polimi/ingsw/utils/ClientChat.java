package it.polimi.ingsw.utils;

import java.util.ArrayList;


/**
 * This class represents the Chat on Client Side, in which the players are recognized
 * via Strings to keep their personal information hidden on the client side
 */
public class ClientChat {

    private final ArrayList<String> chat = new ArrayList<>();
    private final int id;
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
     * @return the last message (String) in the chat instance
     */
    public String getLastMessageString() {
        return chat.get(chat.size()-1);
    }


    /**
     * Getter method
     * @return an arrayList that represents the chat instance
     */
    public ArrayList<String> getMessagesString () {
        return this.chat;
    }


    /**
     * This method gets all messages received from a specific player
     * @param player the specified player
     * @return an arrayList with inside all messages received from a specific player
     */
    public ArrayList<String> getMessagesReceivedByPlayerX (String player) {

        ArrayList<String> messagesReceivedByPlayerX = new ArrayList<>();
        for (String s : chat) {
            String[] pieceOfS = s.split("&&&");
            String receiver = pieceOfS[1];
            String text = pieceOfS[2];
            if (receiver.equals(player)) {
                messagesReceivedByPlayerX.add(s);
            }
        }
        return messagesReceivedByPlayerX;
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
    public void addMessage(String message) {
        this.chat.add(message);
    }
}
