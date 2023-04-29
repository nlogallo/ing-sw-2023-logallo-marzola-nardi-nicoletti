package it.polimi.ingsw.utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the formatted message that this application uses to exchange information from a side to another.
 */
public class NetworkMessage implements Serializable {
    /**
     * requestId represents the type of message here there are all the possibilities
     * PS -> Player Setup (a message that contains all the info the setup the interface for the client side)
     * UB -> Update Board (a message that contains the updated board)
     * UC -> Update Chat (a message that contains a new message for a chat)
     * UPS -> Update Personal Setup (a message that contains the new Tokens achieved (if it has achieved), the shelf and the board updated)
     * UGT -> Update Game Token (a message that contains the list of the remaining tokens in the game)
     * UR -> Update Result (a message that contains the status of the game, such as who is playing, or the final results of the game)
     */
    private String requestId;
    /**
     * textMessage represents the text message that should appear int the client interface
     */
    private String textMessage;
    /**
     * content represent the list of the content of the message (such as the board or the shelf...) it depends by the requestId
     */
    private ArrayList<Object> content;

    public NetworkMessage(){
        this.requestId = null;
        this.textMessage = null;
        this.content = null;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public ArrayList<Object> getContent() {
        return content;
    }

    public void setTextMessage(String text){
        this.textMessage = text;
    }

    public void addContent(Object object){
        content.add(object);
    }

    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
