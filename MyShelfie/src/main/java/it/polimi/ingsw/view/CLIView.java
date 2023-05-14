package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.utils.CLIFormatter;
import it.polimi.ingsw.utils.InputOutputHandler;

import java.util.ArrayList;

/**
 * This class is the CLI View that is used to exploit the Observer Pattern. In addiction, it contains all the info
 * of the Client to show at the user.
 */
public class CLIView implements Observer{
    private Board board;
    private Shelf shelf;
    private PersonalGoal personalGoal;
    private ArrayList<CommonGoal> commonGoals = new ArrayList<>();
    private ArrayList<Chat> chats = new ArrayList<>();
    private boolean seat;
    private ArrayList<Token> personalTokens = new ArrayList<>();
    private ArrayList<Token> gameTokens;
    private ArrayList<String> playersNickname;
    private String currentPlayer;
    private final String clientNickname;
    private String screenMessage;
    private ClientController clientController;
    private CLIFormatter cliFormatter = new CLIFormatter(this);
    private InputOutputHandler inputOutputHandler = new InputOutputHandler(this);
    private boolean isOccurredAnError = false;

    /**
     * Constructor method
     */
    public CLIView(String clientNickname){
        this.screenMessage = null;
        this.clientNickname = clientNickname;
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
     * @param chatId is the id of the chat
     * @param text is the text message
     */
    @Override
    public void updateChat(String sender, int chatId, String text){
        //if not exists create a new chat with that id
        //add in the arrayList<ClientChat>
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
     * This method update the current player
     * @param nickname is the nickname of current player
     */
    @Override
    public void updateCurrentPlayer(String nickname){
        this.currentPlayer = nickname;
    }

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

    public Board getBoard() {
        return board;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    public ArrayList<CommonGoal> getCommonGoals() {
        return commonGoals;
    }

    public boolean isSeat() {
        return seat;
    }

    public ArrayList<Token> getPersonalTokens() {
        return personalTokens;
    }

    public ArrayList<Token> getGameTokens() {
        return gameTokens;
    }

    public String getScreenMessage() {
        return screenMessage;
    }

    public String getCurrentPlayer(){
        return this.currentPlayer;
    }

    public String getClientNickname(){
        return this.clientNickname;
    }

    public ArrayList<String> getPlayersNickname() {
        return this.playersNickname;
    }

    public boolean getOccurredAnError () {
        return this.isOccurredAnError;
    }

    /**
     *
     * @param positions
     * @param column
     */
    public void moveTiles(ArrayList<String> positions, int column){
        clientController.moveTiles(positions, column, this.board, this.shelf);
    }

    /**
     *
     * @param text
     */
    public void sendMessage(ArrayList<String> receiver, String text){

    }


    /**
     * This method refresh the CLI with the updated objects
     */
    @Override
    public void refreshCLI(){
        cliFormatter.createCLIInterface();
    }

    @Override
    public void enableInput(){
        inputOutputHandler.userPressButton();
    }

}
