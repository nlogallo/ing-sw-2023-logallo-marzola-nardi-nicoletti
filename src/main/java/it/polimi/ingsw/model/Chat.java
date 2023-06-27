package it.polimi.ingsw.model;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class represents a chat instance of the Game
 */

public class Chat implements Serializable {

    private final ArrayList <Message> chat = new ArrayList<>();
    private final int id;
    private final ArrayList<Player> playerList = new ArrayList<>();


    /**
     * Class constructor
     * @param id is the chat id
     * @param playerList contains the players inside the chat instance
     */
    public Chat(int id, ArrayList<Player> playerList ) {

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
    public ArrayList<Player> getChatMembers() {
        return this.playerList;
    }


    /**
     * Getter method
     * @return the last message in the chat instance
     */
    public Message getLastMessage() {
        return chat.get(chat.size()-1);
    }


    /**
     * Getter method
     * @return an arrayList that represents the chat instance
     */
    public ArrayList<Message> getMessages () {
        return this.chat;
    }


    /**
     * This method gets all messages received from a specific player
     * @param player the specified player
     * @return an arrayList with inside all messages received from a specific player
     */
    public ArrayList<Message> getMessagesReceivedByPlayerX (Player player) {

        ArrayList<Message> messageReceived = new ArrayList<>();
        for (Message message : this.chat) {
            if (message.getReceiver().contains(player)){
                messageReceived.add(message);
            }
        }
        return messageReceived;
    }


    /**
     * Getter method
     * @return an arrayList with inside all player's nicknames in the chat instance
     */
    public ArrayList<String> getNameChatMembers() {
        ArrayList<String> names = new ArrayList<>();
        for (Player player : playerList) {
            names.add(player.getNickname());
        }
        return names;
    }


    /**
     * This method adds the new player in the chat instance
     * @param player is the new player
     */
    public void addPlayerToChat(Player player) {
        this.playerList.add(player);
    }


    /**
     * This method adds the new Message in the arrayList contained all messages of the chat instance
     * @param message is the new message
     */
    public void addMessage(Message message) {
        this.chat.add(message);
    }

}
