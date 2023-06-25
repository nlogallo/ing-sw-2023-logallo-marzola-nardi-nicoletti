package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.utils.ClientChat;
import it.polimi.ingsw.view.GUI.GUIControllers.MainSceneController;
import it.polimi.ingsw.view.Observer;
import javafx.application.Platform;

import java.sql.Timestamp;
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
    private ClientChat globalChat;
    private ArrayList<ClientChat> chats = new ArrayList<>();
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
    private boolean isGameEnded = false;

    private MainSceneController stageController;


    /**
     * Class Constructor
     * @param clientNickname is the client nickname
     */
    public GUIView(String clientNickname) {
        this.screenMessage = null;
        this.clientNickname = clientNickname;
    }


    /**
     * This method updates the current Board
     * @param board is the updated Board
     */
    @Override
    public void updateBoard(Board board) {
        this.board = board;
    }


    /**
     * Setter method
     * @param stageController is the new Stage Controller
     */
    public void setStageController(MainSceneController stageController) {
        this.stageController = stageController;
    }


    /**
     * This method updates the Shelf
     * @param shelf is the updated Shelf
     */
    @Override
    public void updateShelf(Shelf shelf) {
        this.shelf = shelf;
    }


    /**
     * This method updates the Personal Goal
     * @param personalGoal is the updated Personal Goal
     */
    @Override
    public void updatePersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }


    /**
     * This method updates the Common Goal
     * @param commonGoal is the updated Common Goal
     */
    @Override
    public void updateCommonGoal(CommonGoal commonGoal) {
        this.commonGoals.add(commonGoal);
    }


    /**
     * This method update the Personal Tokens
     * @param token is the new Personal Token to check if the user already has it or not
     */
    @Override
    public void updatePersonalTokens(Token token) {
        for(int i = 0; i < personalTokens.size(); i++){
            if(personalTokens.get(i).getId() == token.getId())
                return;
        }
        personalTokens.add(token);
    }


    /**
     * This method updates the Game Tokens
     * @param tokens is the arrayList of the updated Tokens
     */
    @Override
    public void updateGameTokens(ArrayList<Token> tokens) {
        gameTokens = new ArrayList<>(tokens);
    }


    /**
     * This method updates the Chat
     * @param sender is the sender of the message
     * @param receivers is the arrayList of the receivers
     * @param text is the text
     */
    @Override
    public void updateChat(String sender, ArrayList<String> receivers, String text) {
        if(receivers.size() > 1){
            if(globalChat == null) {
                if (playersNickname == null)
                    return;
                globalChat = new ClientChat(0, playersNickname);
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            globalChat.addMessage(text, sender, receivers, timestamp);
            Platform.runLater(() ->
            {
                stageController.addMessage(sender, receivers, text, timestamp);
            });;
        }
        else{
            for(ClientChat c : chats){
                if(c.getChatMembers().contains(receivers.get(0)) && c.getChatMembers().contains(sender)){
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    globalChat.addMessage(text, sender, receivers, timestamp);
                    Platform.runLater(() ->
                    {
                        stageController.addMessage(sender, receivers, text, timestamp);
                    });
                    return;
                }
            }
            ArrayList<String> partecipants = new ArrayList();
            partecipants.add(sender);
            partecipants.addAll(receivers);
            ClientChat duoChat = new ClientChat(chats.size() + 1, partecipants);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            duoChat.addMessage(text, sender, receivers, timestamp);
            if(stageController != null)
                Platform.runLater(() ->
                {
                    stageController.addMessage(sender, receivers, text, timestamp);
                });
        }
    }


    /**
     * This method updates the current seat
     * @param seat is the updated seat
     */
    @Override
    public void updateSeat(boolean seat) {
        this.seat = seat;
    }


    /**
     * This method updates the Screen Message
     * @param text is the updated Screen Message
     */
    @Override
    public void updateScreenMessage(String text) {
        this.screenMessage = text;
    }


    /**
     * This method updates the players nicknames
     * @param playersNickname is the ArrayList of the updated players nickname
     */
    @Override
    public void updatePlayersNickname(ArrayList<String> playersNickname) {
        this.playersNickname = new ArrayList<>(playersNickname);
    }


    /**
     * This method updates the current player
     * @param nickname is the nickname of the updated current player
     */
    @Override
    public void updateCurrentPlayer(String nickname) {
        this.currentPlayer = nickname;
        stageController.setCurrentlyPlayingLabel(nickname);
    }


    /**
     * This method updates the player's Shelf
     * @param playersShelf is the Map that contains the Shelf of the players
     */
    @Override
    public void updatePlayersShelf(Map<String, Shelf> playersShelf) {
        this.playersShelf = new HashMap<String, Shelf>(playersShelf);
    }


    /**
     * This method refresh the GUIView
     */
    @Override
    public void refreshView() {

        stageController.setOtherPlayersAssets();
        stageController.setGoalsPicture();
        stageController.setBoardImage();
        stageController.setShelfImage(this.shelf);
        stageController.setTokensPicture();
        stageController.setSeatPicture();

    }


    /**
     * This method checks if the game is ended
     * @param isGameEnded is a boolean parameter that means if the game is ended or not
     * @param protocol is the protocol with which the user is playing
     */
    @Override
    public void isGameEnded(boolean isGameEnded, int protocol) {
        this.isGameEnded = isGameEnded;
        if (isGameEnded) {
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(screenMessage);
            parameters.add(this);
            parameters.add(clientNickname);
            parameters.add(protocol);
            Platform.runLater(() -> SceneController.changeScene("EndGameScene.fxml", parameters));
        }
    }


    /**
     * Setter method
     * @param clientController is the specific client Controller
     */
    @Override
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }


    // To delete?
    @Override
    public void setIsOccurredAnError(boolean var) {
        this.isOccurredAnError = isOccurredAnError;
    }


    /**
     * This method enables input
     */
    @Override
    public void enableInput() {

    }


    /**
     * Getter method
     * @return the current Board
     */
    public Board getBoard() {
        return board;
    }


    /**
     * Getter method
     * @return the current Shelf
     */
    public Shelf getShelf() {
        return shelf;
    }


    /**
     * Getter method
     * @return is the Personal Goal of the client
     */
    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    /**
     * Getter method
     * @return the arrayList of the common goals
     */
    public ArrayList<CommonGoal> getCommonGoals() {
        return commonGoals;
    }


    /**
     * Getter method
     * @return true if the client is the first player in the Game
     */
    public boolean isSeat() {
        return seat;
    }


    /**
     * Getter method
     * @return the arrayList of the personal tokens of the client
     */
    public ArrayList<Token> getPersonalTokens() {
        return personalTokens;
    }


    /**
     * Getter method
     * @return the arrayList of the game tokens
     */
    public ArrayList<Token> getGameTokens() {
        return gameTokens;
    }


    // To delete?
    public String getScreenMessage() {
        return screenMessage;
    }


    /**
     * Getter method
     * @return the current player
     */
    public String getCurrentPlayer(){
        return this.currentPlayer;
    }


    /**
     * Getter method
     * @return the client nickname
     */
    public String getClientNickname(){
        return this.clientNickname;
    }


    /**
     * Getter method
     * @return the arrayList of the players' nickname, excluding the client nickname
     */
    public ArrayList<String> getPlayersNickname() {
        return this.playersNickname;
    }

    public boolean getOccurredAnError () {
        return this.isOccurredAnError;
    }


    /**
     * This method allows to move the Tiles
     * @param positions is the arrayList of the positions
     * @param column is the column of the shelf
     */
    public void moveTiles(ArrayList<String> positions, int column){
        clientController.moveTiles(positions, column);
    }


    /**
     * Getter method
     * @return the Map: playerNickname -> playerShelf
     */
    public Map<String, Shelf> getPlayersShelf() {
        return playersShelf;
    }

    /**
     * Getter method
     * @return the client controller
     */
    public ClientController getClientController() { return this.clientController; }
}
