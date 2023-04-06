package it.polimi.ingsw.model;
import java.sql.Timestamp;

/**
 * This class represents a single message in a Chat
 */

public class Message {

    private final String message;
    private final Player sender;
    private final Player receiver;
    private final Timestamp timestamp;


    /**
     * Class constructor
     * @param message is the string of the message
     * @param sender is the sender of the message
     * @param receiver is the receiver of the message
     */
    public Message (String message, Player sender, Player receiver ) {

        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
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
     * @return the receiver of the message instance
     */
    public Player getReceiver() {
        return receiver;
    }


    /**
     * Getter method
     * @return the timeStamp of the message instance
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

}
