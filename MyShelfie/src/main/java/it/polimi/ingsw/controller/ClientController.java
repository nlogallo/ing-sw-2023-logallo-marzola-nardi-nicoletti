package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.utils.NetworkMessage;
import it.polimi.ingsw.view.ClientViewObservable;

import java.util.ArrayList;

/**
 * This class represent the Client side controller
 */
public class ClientController {
    private final ClientViewObservable clientViewObservable;
    private final MyShelfieClient client;
    private final String playerNickname;

    /**
     * Method constructor
     * @param clientViewObservable is the observable view
     * @param client is the client instance
     * @param playerNickname is the nickname of the player
     */
    public ClientController(ClientViewObservable clientViewObservable, MyShelfieClient client, String playerNickname){
        this.clientViewObservable = clientViewObservable;
        this.client = client;
        this.playerNickname = playerNickname;
    }

    /**
     * This method allows the user to move the tiles from the board to the shelf. It creates a request to send at the sever for the approval.
     * @param position is the list of position of the tiles (taken as a string)
     * @param column is the column w
     * @param board is the current board. It is necessary to check if this movement is possible
     * @param shelf is the current shelf of the user. It is necessary to check if the movement is possible
     */
    public void moveTiles(ArrayList<String> position, int column, Board board, Shelf shelf){
        ArrayList<Position> listPosition = new ArrayList<Position>();
        Tile[][] tilesTable = board.getTilesTable();

        for(int i = 0; i < position.size(); i++){
            int x = position.get(i).charAt(0) - 49;
            int y = position.get(i).charAt(1) - 49;

            if (x < 0 || x > 8) {
                clientViewObservable.setScreenMessage("Tile position out of board bounds.");
                clientViewObservable.refreshCLI();
                return;
            }
            if (y < 0 || y > 8) {
                clientViewObservable.setScreenMessage("Tile position out of board bounds.");
                clientViewObservable.refreshCLI();
                return;
            }

            if (tilesTable[x][y] == null) {
                clientViewObservable.setScreenMessage("You can't select a spot that doesn't contain a tile.");
                clientViewObservable.refreshCLI();
                return;
            }

            if (!board.canPull(x, y)) {
                clientViewObservable.setScreenMessage("You can't pick tiles that don't have at least one free edge.");
                clientViewObservable.refreshCLI();
                return;
            }

            Position position1 = new Position(x, y);
            listPosition.add(position1);
        }

        if (listPosition.size() < 1 || listPosition.size() > 3) {
            clientViewObservable.setScreenMessage("You have to pick between 1 and 3 tiles");
            clientViewObservable.refreshCLI();
            return;
        }

        if (!board.areAligned(listPosition)) {
            clientViewObservable.setScreenMessage("You have to pick aligned tiles");
            clientViewObservable.refreshCLI();
            return;
        }

        //check for column
        if (column  - 1 < 0 || column - 1 > 4) {
            clientViewObservable.setScreenMessage("You have to pick a column number between 1 and 5.");
            clientViewObservable.refreshCLI();
            return;
        }
        if (shelf.freeRows(column - 1) < listPosition.size()) {
            clientViewObservable.setScreenMessage("You don't have enough free spots in this column.");
            clientViewObservable.refreshCLI();
            return;
        }
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.addContent(listPosition.size());
        networkMessage.addContent(listPosition);
        networkMessage.addContent(column);
        networkMessage.setRequestId("MT");
        NetworkMessage resp = client.sendMessage(networkMessage);
        System.out.println("after send");
        //unpack the message
        clientViewObservable.setShelf((Shelf) resp.getContent().get(0));
        clientViewObservable.refreshCLI();
    }

    /**
     * This method allows the user to send a message in a chat
     * @param receiver is the list of receiver (it depends on what chat the user selects)
     * @param text is the text of the message to send
     */
    public void sendMessage(ArrayList<String> receiver, String text){
        if ((text == null) || (text.trim().length() == 0)) {
            clientViewObservable.setScreenMessage("You can't send empty message");
            return;
        }
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.addContent(this.playerNickname);
        networkMessage.addContent(receiver);
        networkMessage.addContent(text);
        networkMessage.setRequestId("SM");
    }

    /**
     * This method allows to fill the interface at the start of the game
     * @param networkMessage is the NetworkMessage received from the Server
     */
    public void playerSetup(NetworkMessage networkMessage){
        clientViewObservable.setScreenMessage(networkMessage.getTextMessage());
        ArrayList<Object> content = networkMessage.getContent();
        clientViewObservable.setBoard((Board) content.get(0));
        clientViewObservable.setShelf((Shelf) content.get(1));
        clientViewObservable.setPersonalGoal((PersonalGoal) content.get(2));
        clientViewObservable.setCommonGoal((CommonGoal) content.get(3));
        clientViewObservable.setCommonGoal((CommonGoal) content.get(4));
        clientViewObservable.setSeat((boolean) content.get(5));
        Integer numberOfOtherPlayer = (Integer) content.get(6);
        ArrayList<String> listOfNickname = new ArrayList<String>();
        for(int i = 0; i < numberOfOtherPlayer; i++)
            listOfNickname.add((String) content.get(7 + i));
        clientViewObservable.setPlayersNickname(listOfNickname);
        Integer numberOfGameTokens = (Integer) content.get(7 + numberOfOtherPlayer);
        ArrayList<Token> listOfGameTokens = new ArrayList<Token>();
        for(int i = 0; i < numberOfGameTokens; i++)
            listOfGameTokens.add((Token) content.get(8 + numberOfOtherPlayer + i));
        clientViewObservable.setGameTokens(listOfGameTokens);
        clientViewObservable.refreshCLI();
    }

    /**
     * This method allows to update the current board
     * @param networkMessage is the NetworkMessage received from the Server
     */
    public void updateBoard(NetworkMessage networkMessage){
        clientViewObservable.setScreenMessage(networkMessage.getTextMessage());
        clientViewObservable.setBoard((Board) networkMessage.getContent().get(0));
    }

    /**
     * This method allows to update the chats
     * @param networkMessage is the NetworkMessage received from the Server
     */
    public void updateChat(NetworkMessage networkMessage){
        clientViewObservable.setChat((String) networkMessage.getContent().get(0), (int) networkMessage.getContent().get(1), (String) networkMessage.getContent().get(2));
    }

    /**
     * This method allows to update the interface while the player is playing
     * @param networkMessage is the NetworkMessage received from the Server
     */
    public void updatePersonalSetup(NetworkMessage networkMessage){
        clientViewObservable.setScreenMessage(networkMessage.getTextMessage());
        clientViewObservable.setBoard((Board) networkMessage.getContent().get(0));
        clientViewObservable.setShelf((Shelf) networkMessage.getContent().get(1));
        Integer numberOfPersonalTokens = (Integer) networkMessage.getContent().get(2);
        for(int i = 0; i < numberOfPersonalTokens; i++)
            clientViewObservable.setPersonalTokens((Token) networkMessage.getContent().get(3 + i));
    }

    /**
     * This method allows to update the available game tokens
     * @param networkMessage is the NetworkMessage received from the Server
     */
    public void updateGameTokens(NetworkMessage networkMessage){
        clientViewObservable.setScreenMessage(networkMessage.getTextMessage());
        ArrayList<Object> content = networkMessage.getContent();
        ArrayList<Token> listOfGameTokens = new ArrayList<Token>();
        Integer numberOfGameTokens = (Integer) content.get(0);
        for(int i = 0; i < numberOfGameTokens; i++)
            listOfGameTokens.add((Token) content.get(1 + i));
        clientViewObservable.setGameTokens(listOfGameTokens);
    }

    /**
     * This method allows to update the result of the game
     * @param networkMessage is the NetworkMessage received from the Server
     */
    public void updateResults(NetworkMessage networkMessage){
        clientViewObservable.setScreenMessage(networkMessage.getTextMessage());
        clientViewObservable.setCurrentPlayer((String) networkMessage.getContent().get(0));
        clientViewObservable.refreshCLI();
    }

}
