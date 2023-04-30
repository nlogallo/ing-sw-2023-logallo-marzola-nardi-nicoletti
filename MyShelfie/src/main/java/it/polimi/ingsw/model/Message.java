package it.polimi.ingsw.model;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * This class represents a single message in a Chat
 */

public class Message {

    private final String message;
    private final Player sender;
    private final ArrayList<Player> receivers = new ArrayList<>();
    private final Timestamp timestamp;


    /**
     * Class constructor
     * @param message is the string of the message
     * @param sender is the sender of the message
     * @param receivers is the receivers of the message
     */
    public Message (String message, Player sender, ArrayList<Player> receivers ) {

        this.message = message;
        this.sender = sender;
        this.receivers.addAll(receivers);
        this.timestamp = new Timestamp(System.currentTimeMillis());

    }


    /**
     * Getter method
     * @return the message string
     */
    public String getMessage() {
        return message;
    }


    /**
     * Getter method
     * @return the sender of the message instance
     */
    public Player getSender() {
        return sender;
    }


    /**
     * Getter method
     * @return the receivers of the message instance
     */
    public ArrayList<Player> getReceiver() {
        return this.receivers;
    }


    /**
     * Getter method
     * @return the timeStamp of the message instance
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

}
