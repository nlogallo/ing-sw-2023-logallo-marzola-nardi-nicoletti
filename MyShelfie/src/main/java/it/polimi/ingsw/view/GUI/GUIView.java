package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.view.Observer;

import java.util.ArrayList;


public class GUIView  implements Observer {

    public GUIView() {}
    @Override
    public void updateBoard(Board board) {

    }

    @Override
    public void updateShelf(Shelf shelf) {

    }

    @Override
    public void updatePersonalGoal(PersonalGoal personalGoal) {

    }

    @Override
    public void updateCommonGoal(CommonGoal commonGoal) {

    }

    @Override
    public void updatePersonalTokens(Token token) {

    }

    @Override
    public void updateGameTokens(ArrayList<Token> tokens) {

    }

    @Override
    public void updateChat(String sender, int idChat, String text) {

    }

    @Override
    public void updateSeat(boolean seat) {

    }

    @Override
    public void updateScreenMessage(String text) {

    }

    @Override
    public void updatePlayersNickname(ArrayList<String> playersNickname) {

    }

    @Override
    public void updateCurrentPlayer(String nickname) {

    }

    @Override
    public void refreshCLI() {

    }

    @Override
    public void setClientController(ClientController clientController) {

    }

    @Override
    public void setIsOccurredAnError(boolean var) {

    }

    @Override
    public void enableInput() {

    }
}
