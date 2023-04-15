package it.polimi.ingsw.controller;

import java.util.ArrayList;

public class NetworkMessage {

    private String textMessage;
    private ArrayList<Object> content;

    public NetworkMessage(){
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

}
