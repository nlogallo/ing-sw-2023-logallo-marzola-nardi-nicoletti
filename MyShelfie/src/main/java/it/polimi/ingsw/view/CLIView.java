package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is the CLI View that is used to exploit the Observer Pattern. In addition it contains all the info
 * of the Client to show at the user.
 */
public class CLIView implements Observer{
    private Board board;
    private Shelf shelf;
    private PersonalGoal personalGoal;
    private ArrayList<CommonGoal> commonGoals;
    private ArrayList<Chat> chats;
    private boolean seat;
    private ArrayList<Token> personalTokens;
    private ArrayList<Token> gameTokens;
    private ArrayList<String> playersNickname;
    private String screenMessage;
    private ClientController clientController;

    /**
     * Constructor method
     */
    public void CLIView(){
        this.screenMessage = null;
        this.commonGoals = new ArrayList<CommonGoal>();
        this.personalTokens = new ArrayList<Token>();
        this.chats = new ArrayList<Chat>();
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
        commonGoals.add(commonGoal);
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

    /**
     *
     * @param clientController
     */
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
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
    private void refreshCLI(){
        clearCLI();
        //here to create the CLI interface
    }

    /**
     * This method clear the CLI for all the type of OS
     */
    private void clearCLI() {
        final String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            try {
                Runtime.getRuntime().exec("cls");
            } catch (IOException e) {
                System.out.println("\nWe're sorry, but we cannot clear your console");
            }
        } else{
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException e) {
                System.out.println("\nWe're sorry, but we cannot clear your console");
            }
        }
    }
}
