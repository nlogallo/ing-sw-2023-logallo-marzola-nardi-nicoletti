package it.polimi.ingsw.controller;

import it.polimi.ingsw.MyShelfieClient;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.utils.NetworkMessage;
import it.polimi.ingsw.view.CLI.CLIMenus;
import it.polimi.ingsw.view.ClientViewObservable;

import java.util.ArrayList;
import java.util.Map;

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
     * @param positions is the list of position of the tiles (taken as a string)
     * @param column is the column w
     */
    public void moveTiles(ArrayList<String> positions, int column){
        ArrayList<Position> listPosition = convertStringListToPositionList(positions);
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.addContent(listPosition);
        networkMessage.addContent(column-1);
        networkMessage.setRequestId("MT");
        NetworkMessage resp = client.sendMessage(networkMessage);
        //unpack the message
        clientViewObservable.setScreenMessage(resp.getTextMessage());
        clientViewObservable.setShelf((Shelf) resp.getContent().get(0));
        clientViewObservable.setBoard((Board) resp.getContent().get(1));
        for(Token t : (ArrayList<Token>) resp.getContent().get(2))
            clientViewObservable.setPersonalTokens(t);
        clientViewObservable.refreshCLI();
    }


    /**
     * This method checks if the Tile is null or not
     * @param row is the specific row
     * @param column is the column
     * @param board is the Board
     * @return true if the Tile is not null
     */
    public boolean checkNullTiles(int row, int column, Board board) {

        Tile[][] tilesTable = board.getTilesTable();
        if (tilesTable[row - 1][column - 1] == null) {
            clientViewObservable.setScreenMessage("You can't select a spot that doesn't contain a tile.");
            return false;
        }
        return true;
    }


    /**
     * This method checks if the tile can be pulled or not
     * @param row is the row of the Tile
     * @param column is the column of the Tile
     * @param board is the Board
     * @return true if the Tile can be pulled, otherwise false
     */
    public boolean checkCanPullTile(int row, int column, Board board) {

        if (!board.canPull(row - 1, column - 1)) {
            clientViewObservable.setScreenMessage("You can't pick tiles that don't have at least one free edge.");
            return false;
        }
        return true;
    }


    /**
     * This method checks whether the chosen tiles are aligned or not
     * @param positions is the ArrayList of the Positions
     * @param board is the Board
     * @return true if the chosen tiles are aligned, otherwise false
     */
    public boolean checkIsAlignedTiles(ArrayList<String> positions, Board board) {

        ArrayList<Position> listPosition = convertStringListToPositionList(positions);
        if (!board.areAligned(listPosition)) {
            clientViewObservable.setScreenMessage("You have to pick aligned tiles");
            return false;
        }
        return true;
    }


    /**
     * This method is used to check that there are some free spots in a specific column
     * @param positions is the ArrayList of Positions
     * @param shelf is the Shelf
     * @param column is the specific column
     * @return true if there are some free spots in the specific column
     */
    public boolean checkFreeSpotsInColumnShelf (ArrayList<String> positions, Shelf shelf, int column) {

        ArrayList<Position> listPosition = convertStringListToPositionList(positions);
        if (shelf.freeRows(column - 1) < listPosition.size()) {
            clientViewObservable.setScreenMessage("You don't have enough free spots in this column.");
            return false;
        }
        return true;
    }


    /**
     * This method convert an ArrayList of String in a ArrayList of positions
     * @param positions is the ArrayList of String
     * @return an ArrayList of Position
     */
    private ArrayList<Position> convertStringListToPositionList(ArrayList<String> positions) {

        ArrayList<Position> listPosition = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) {
            int row = positions.get(i).charAt(0) - 49;
            int column = positions.get(i).charAt(1) - 49;
            Position newPosition = new Position(row, column);
            listPosition.add(newPosition);
        }
        return listPosition;
    }



    /**
     * This method allows the user to send a message in a chat
     * @param receiver is the list of receiver (it depends on what chat the user selects)
     * @param text is the text of the message to send
     */
    public void sendMessage(String sender, ArrayList<String> receiver, String text){
        if ((text == null) || (text.trim().length() == 0)) {
            clientViewObservable.setScreenMessage("You can't send empty message");
            return;
        }
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.addContent(this.playerNickname);
        networkMessage.addContent(receiver);
        networkMessage.addContent(text);
        networkMessage.setRequestId("SM");
        client.sendMessage(networkMessage);
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
        clientViewObservable.setChat((String) networkMessage.getContent().get(0), (ArrayList<String>) networkMessage.getContent().get(1), (String) networkMessage.getContent().get(2));
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
     * This method allows to update the result of the game (including the shelves of players)
     * @param networkMessage is the NetworkMessage received from the Server
     */
    public void updateResults(NetworkMessage networkMessage){
        if(networkMessage.getRequestId().equals("UR")) {
            clientViewObservable.setScreenMessage(networkMessage.getTextMessage());
            clientViewObservable.setCurrentPlayer((String) networkMessage.getContent().get(0));
            clientViewObservable.setPlayersShelf((Map<String, Shelf>) networkMessage.getContent().get(1));
            clientViewObservable.refreshCLI();
        }
        else{
            if(MyShelfieClient.getInterfaceChosen() == 1)
                CLIMenus.endMenu(networkMessage.getTextMessage());
            else{
                clientViewObservable.setScreenMessage(networkMessage.getTextMessage());
                clientViewObservable.isGameEnded(true, client.getProtocol());
            }
        }
    }

    /**
     * This method allows the user to type on the CLI
     */
    public void enableInput() {
        clientViewObservable.enableInput();
    }
}
