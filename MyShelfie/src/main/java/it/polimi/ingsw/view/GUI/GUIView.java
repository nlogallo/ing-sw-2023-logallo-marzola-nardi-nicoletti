package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.utils.ClientChat;
import it.polimi.ingsw.view.GUI.GUIControllers.MainStageController;
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

    private MainStageController stageController;

    public GUIView(String clientNickname) {
        this.screenMessage = null;
        this.clientNickname = clientNickname;
    }

    @Override
    public void updateBoard(Board board) {
        this.board = board;
    }

    public void setStageController(MainStageController stageController) {
        this.stageController = stageController;
    }

    @Override
    public void updateShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    @Override
    public void updatePersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }

    @Override
    public void updateCommonGoal(CommonGoal commonGoal) {
        this.commonGoals.add(commonGoal);
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
    public void updateChat(String sender, ArrayList<String> receivers, String text) {
        if(receivers.size() > 1){
            if(globalChat == null)
                globalChat = new ClientChat(0, playersNickname);
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
            Platform.runLater(() ->
            {
                stageController.addMessage(sender, receivers, text, timestamp);
            });
        }
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
        stageController.setOtherPlayersAssets();
        stageController.setGoalsPicture();
        stageController.setBoardImage();
        stageController.setShelfImage(this.shelf);
        stageController.setTokensPicture();
        stageController.setSeatPicture();
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
