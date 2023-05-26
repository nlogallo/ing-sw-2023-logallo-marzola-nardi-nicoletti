package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.commonGoal.CommonGoal;

import java.util.ArrayList;
import java.util.Map;

/**
 * This interface was created to implement the Observer Pattern. This interface will be used to implement the CLIView and GUIView
 */
public interface Observer {
    public void updateBoard(Board board);
    public void updateShelf(Shelf shelf);
    public void updatePersonalGoal(PersonalGoal personalGoal);
    public void updateCommonGoal(CommonGoal commonGoal);
    public void updatePersonalTokens(Token token);
    public void updateGameTokens(ArrayList<Token> tokens);
    public void updateChat(String sender, int idChat, String text);
    public void updateSeat(boolean seat);
    public void updateScreenMessage(String text);
    public void updatePlayersNickname(ArrayList<String> playersNickname);
    public void updateCurrentPlayer(String nickname);
    public void updatePlayersShelf(Map<String, Shelf> playersShelf);
    public void refreshCLI();
    public void setClientController(ClientController clientController);
    public void setIsOccurredAnError(boolean var);
    public void enableInput();
}