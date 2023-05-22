package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.view.GUI.GUIControllers.MainStageController;
import it.polimi.ingsw.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the GUI View that is used to exploit the Observer Pattern. In addiction, it contains all the info
 * of the Client to show at the user.
 */
public class GUIView  implements Observer {

    private Board board;
    private Shelf shelf;
    private PersonalGoal personalGoal;
    private ArrayList<CommonGoal> commonGoals = new ArrayList<>();
    private ArrayList<Chat> chats = new ArrayList<>();
    private boolean seat;
    private ArrayList<Token> personalTokens = new ArrayList<>();
    private ArrayList<Token> gameTokens;
    private ArrayList<String> playersNickname;
    private Map<String, Shelf> playersShelf;
    private String currentPlayer;
    private String clientNickname;
    private String screenMessage;
    private ClientController clientController;
    private boolean isOccurredAnError = false;

    private MainStageController stageController;

    public GUIView(String clientNickname) {
        this.screenMessage = null;
        this.clientNickname = clientNickname;
    }

    @Override
    public void updateBoard(Board board) {
        this.board = board;
        stageController.setBoardImage();
    }

    public void setStageController(MainStageController stageController) {
        this.stageController = stageController;
    }

    @Override
    public void updateShelf(Shelf shelf) {
        this.shelf = shelf;
        stageController.setShelfImage();
    }

    @Override
    public void updatePersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }

    @Override
    public void updateCommonGoal(CommonGoal commonGoal) {
        this.commonGoals.add(commonGoal);
        if (commonGoals.size() == 2)
            stageController.setGoalsPicture();

    }

    @Override
    public void updatePersonalTokens(Token token) {
        for(int i = 0; i < personalTokens.size(); i++){
            if(personalTokens.get(i).getId() == token.getId())
                return;
        }
        personalTokens.add(token);
    }

    @Override
    public void updateGameTokens(ArrayList<Token> tokens) {
        gameTokens = new ArrayList<>(tokens);
    }

    @Override
    public void updateChat(String sender, int idChat, String text) {
        //if not exists create a new chat with that id
        //add in the arrayList<ClientChat>
    }

    @Override
    public void updateSeat(boolean seat) {
        this.seat = seat;
    }

    @Override
    public void updateScreenMessage(String text) {
        this.screenMessage = text;
    }

    @Override
    public void updatePlayersNickname(ArrayList<String> playersNickname) {
        this.playersNickname = new ArrayList<>(playersNickname);
    }

    @Override
    public void updateCurrentPlayer(String nickname) {
        this.currentPlayer = nickname;
    }

    @Override
    public void updatePlayersShelf(Map<String, Shelf> playersShelf) {
        this.playersShelf = new HashMap<String, Shelf>(playersShelf);
    }

    @Override
    public void refreshCLI() {

    }

    @Override
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void setIsOccurredAnError(boolean var) {
        this.isOccurredAnError = isOccurredAnError;
    }

    @Override
    public void enableInput() {

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

    public void moveTiles(ArrayList<String> positions, int column){
        clientController.moveTiles(positions, column, this.board, this.shelf);
    }

    public Map<String, Shelf> getPlayersShelf() {
        return playersShelf;
    }

    /**
     * Getter method
     * @return the client controller
     */
    public ClientController getClientController() { return this.clientController; }
}
