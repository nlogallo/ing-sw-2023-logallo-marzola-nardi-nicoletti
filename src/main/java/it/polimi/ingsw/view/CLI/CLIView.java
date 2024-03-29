package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.utils.ChatHandler;
import it.polimi.ingsw.utils.ClientChat;
import it.polimi.ingsw.utils.InputOutputHandler;
import it.polimi.ingsw.view.Observer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the CLI View that is used to exploit the Observer Pattern. In addiction, it contains all the info
 * of the Client to show at the user.
 */
public class CLIView implements Observer {
    private Board board;
    private Shelf shelf;
    private PersonalGoal personalGoal;
    private ArrayList<CommonGoal> commonGoals = new ArrayList<>();
    private ArrayList<ClientChat> duoChats = new ArrayList<>();
    private ClientChat globalChat = null;
    private boolean seat;
    private ArrayList<Token> personalTokens = new ArrayList<>();
    private ArrayList<Token> gameTokens;
    private ArrayList<String> playersNickname;
    private Map<String, Shelf> playersShelf;
    private String currentPlayer;
    private final String clientNickname;
    private String screenMessage;
    private ClientController clientController;
    private CLIFormatter cliFormatter = new CLIFormatter(this);
    private InputOutputHandler inputOutputHandler = new InputOutputHandler(this);
    private boolean isOccurredAnError = false;
    private ChatHandler chatHandler;

    /**
     * Constructor method
     */
    public CLIView(String clientNickname){
        this.screenMessage = null;
        this.clientNickname = clientNickname;
        this.chatHandler = new ChatHandler(this);
    }

    /**
     * This method updates the board
     * @param board is the updated board
     */
    @Override
    public void updateBoard(Board board){
        this.board = board;
    }

    /**
     * This method updates the shelf
     * @param shelf is the updated shelf
     */
    @Override
    public void updateShelf(Shelf shelf){
        this.shelf = shelf;
    }

    /**
     * This method updates one Common Goal
     * @param commonGoal is the updated commonGoal
     */
    @Override
    public void updateCommonGoal(CommonGoal commonGoal){
        this.commonGoals.add(commonGoal);
    }

    /**
     * This method add the new message send by someone in a chat
     * @param sender is the nickname of the sender
     * @param receivers are the receivers of the message
     * @param text is the text message
     */
    @Override
    public void updateChat(String sender, ArrayList<String> receivers, String text){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        chatHandler.addMessage(sender, receivers, text, timestamp);
    }

    /**
     * This method updates the "seat", it simply set true or false (if the client has it or not)
     * @param seat is the boolean
     */
    @Override
    public void updateSeat(boolean seat){
        this.seat = seat;
    }

    /**
     * This method updates the personal tokens
     * @param token is the new token to be added
     */
    @Override
    public void updatePersonalTokens(Token token){
        for(int i = 0; i < personalTokens.size(); i++){
            if(personalTokens.get(i).getId() == token.getId())
                return;
        }
        personalTokens.add(token);
    }

    /**
     * This method updates all the tokens left in the game
     * @param tokens is the list of remaining tokens in the game
     */
    @Override
    public void updateGameTokens(ArrayList<Token> tokens){
        gameTokens = new ArrayList<>(tokens);
    }

    /**
     * This method updates the Personal Goal
     * @param personalGoal is the Personal Goal
     */
    @Override
    public void updatePersonalGoal(PersonalGoal personalGoal){
        this.personalGoal = personalGoal;
    }

    /**
     * This method updates the message to be printed on the CLI
     * @param text is the text message
     */
    @Override
    public void updateScreenMessage(String text){
        this.screenMessage = text;
    }

    /**
     * This method updates the nickname of all the players (apart for this client nickname)
     * @param playersNickname is the list of nicknames
     */
    @Override
    public void updatePlayersNickname(ArrayList<String> playersNickname){
        this.playersNickname = new ArrayList<>(playersNickname);
    }

    /**
     * This method updates the current player
     * @param nickname is the nickname of current player
     */
    @Override
    public void updateCurrentPlayer(String nickname){
        this.currentPlayer = nickname;
    }

    /**
     * This method updates the shelf of other players
     * @param playersShelf is the ArrayList of shelves
     */
    @Override
    public void updatePlayersShelf(Map<String, Shelf> playersShelf) {
        this.playersShelf = new HashMap<String, Shelf>(playersShelf);
    }

    /**
     * This method sets the client controller
     * @param clientController is the client controller to set
     */
    @Override
    public void setClientController(ClientController clientController){
        this.clientController = clientController;
    }


    /**
     * This method updates the boolean variable isOccurredAnError.
     * The variable isOccurredAnError becomes true if there was an error.
     */
    @Override
    public void setIsOccurredAnError (boolean isOccurredAnError) { this.isOccurredAnError = isOccurredAnError; }


    /**
     * This method set the global Chat
     * @param globalChat is the global Chat
     */
    public void setGlobalChat (ClientChat globalChat) { this.globalChat = globalChat; }


    /**
     * This method add a new duoChat in the list of open duo Chats
     * @param duoChat is the new duo Chat
     */
    public void addDuoChat (ClientChat duoChat) { this.duoChats.add(duoChat);}

    /**
     * Getter
     * @return the Board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Getter
     * @return the Shelf
     */
    public Shelf getShelf() {
        return shelf;
    }

    /**
     * Getter
     * @return the Personal Goal
     */
    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    /**
     * Getter
     * @return the Common Goals
     */
    public ArrayList<CommonGoal> getCommonGoals() {
        return commonGoals;
    }

    /**
     * This method check if the seat is present
     * @return a boolean
     */
    public boolean isSeat() {
        return seat;
    }

    /**
     * Getter
     * @return the personal achieved Tokens
     */
    public ArrayList<Token> getPersonalTokens() {
        return personalTokens;
    }

    /**
     * Getter
     * @return the Tokens still available in the game
     */
    public ArrayList<Token> getGameTokens() {
        return gameTokens;
    }

    /**
     * Getter
     * @return the screen Message
     */
    public String getScreenMessage() {
        return screenMessage;
    }

    /**
     * Getter
     * @return the current Player
     */
    public String getCurrentPlayer(){
        return this.currentPlayer;
    }

    /**
     * Getter
     * @return the Client Nickname
     */
    public String getClientNickname(){
        return this.clientNickname;
    }

    /**
     * Getter
     * @return all players nickname except for the client nickname
     */
    public ArrayList<String> getPlayersNickname() {
        return this.playersNickname;
    }

    /**
     * This method allows to move the Tiles from the CLI
     * @param positions is the arrayList of the position of the Tiles
     * @param column is a specific column of the client Shelf
     */
    public void moveTiles(ArrayList<String> positions, int column){
        clientController.moveTiles(positions, column);
    }

    /**
     * This method refreshes the CLI with the updated objects
     */
    @Override
    public void refreshView(){
        cliFormatter.createCLIInterface();
    }


    /**
     * This method enables the users input
     */
    @Override
    public void enableInput(){
        inputOutputHandler.userPressButton();
    }

    /**
     * Getter method
     * @return the shelves of other players
     */
    public Map<String, Shelf> getPlayersShelf() {
        return playersShelf;
    }

    /**
     * Getter method
     * @return the client controller
     */
    public ClientController getClientController() { return this.clientController; }


    /**
     * Getter method
     * @return the global Chat
     */
    public ClientChat getGlobalChat () { return globalChat; }

    /**
     * It adds a message in the global chat
     * @param text is text of the message
     * @param sender is the sender nickname
     * @param receivers is the nickname list of receivers
     * @param timestamp is timestamp of the message
     */
    public void addMessageInGlobalChat(String text, String sender, ArrayList<String> receivers, Timestamp timestamp){
        globalChat.addMessage(text, sender, receivers, timestamp);
    }


    /**
     * Getter method
     * @return the arrayList of open duo chats
     */
    public ArrayList<ClientChat> getDuoChats () { return this.duoChats; }


    /**
     * This method call the clientController method that checks if a Tile is null
     * @param row is the row of the Tile
     * @param column is the column of the Tile
     * @param board is the Board of the Game
     * @return true if the Tile isn't null, otherwise false
     */
    public boolean callCheckNullTiles (int row, int column, Board board) { return clientController.checkNullTiles(row, column, board); }


    /**
     * This method call the clientController method that checks if a Tile can be pulled
     * @param row is the row of the Tile
     * @param column is the column of the Tile
     * @param board is the Board of the Game
     * @return true if the Tile isn't null, otherwise false
     */
    public boolean callCheckCanPullTile(int row, int column, Board board) { return clientController.checkCanPullTile(row, column, board); }


    /**
     * This method call the clientController method that checks if the chosen Tiles are aligned
     * @param positions is the arrayList of the positions of the Tiles
     * @param board is the Board of the Game
     * @return true if the Tiles are aligned, otherwise false
     */
    public boolean callCheckIsAlignedTiles(ArrayList<String> positions, Board board) { return clientController.checkIsAlignedTiles(positions, board); }


    /**
     * This method call the clientController method that checks if there are free spots in a specific column of the client Shelf
     * @param positions is the arrayList of the position of the Tiles
     * @param shelf is the client Shelf
     * @param column is the specific column
     * @return true if there are, otherwise false
     */
    public boolean callCheckFreeSpotsInColumnShelf (ArrayList<String> positions, Shelf shelf, int column) { return clientController.checkFreeSpotsInColumnShelf(positions, shelf, column); }


    /**
     * This method checks if the game is ended
     * @param isGameEnded is a boolean parameter that means if the game is ended or not
     * @param protocol is the protocol with which the user is playing
     */
    @Override
    public void isGameEnded(boolean isGameEnded, int protocol) {

    }
}
