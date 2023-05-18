package it.polimi.ingsw.utils;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ClientMessage {

    private final String message;
    private final String sender;
    private final ArrayList<String> receivers = new ArrayList<>();
    private final Timestamp timestamp;


    /**
     * Class constructor
     * @param message is the string of the message
     * @param sender is the sender of the message
     * @param receivers is the receivers of the message
     */
    public ClientMessage (String message, String sender, ArrayList<String> receivers, Timestamp timestamp ) {

        this.message = message;
        this.sender = sender;
        this.receivers.addAll(receivers);
        this.timestamp = timestamp;
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
    public String getSender() {
        return sender;
    }


    /**
     * Getter method
     * @return the receivers of the message instance
     */
    public ArrayList<String> getReceiver() {
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

