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
 * This class was created to implement the Observer Pattern. It represents the Observable object that the Views need to know if something has changed in the interface.
 */
public class ClientViewObservable {
    Observer view;

    /**
     * Class constructor
     * @param view is the Interface, for which this class could call the CLIView or the GUIView without distinction.
     */
    public ClientViewObservable(Observer view){
        this.view = view;
    }

    /**
     * This method notifies that something has changed in the board
     * @param board is the updated board
     */
    public void setBoard(Board board) {
        view.updateBoard(board);
    }

    /**
     * This method notifies that something has changed in the shelf
     * @param shelf is the updated shelf
     */
    public void setShelf(Shelf shelf) {
        view.updateShelf(shelf);
    }

    /**
     * This method notifies that something has changed in the Personal Goal. Should be noted that this method is called once only at
     * the beginning of the game, because the Personal Goal could not change through the game.
     * @param personalGoal is the personal goal
     */
    public void setPersonalGoal(PersonalGoal personalGoal) {
        view.updatePersonalGoal(personalGoal);
    }

    /**
     * This method notifies that something has changed in the Common Goal. Should be noted that this method is called twice only at
     * the beginning of the game (one per common goal), because the Common Goal could not change through the game.
     * @param commonGoal is the personal goal
     */
    public void setCommonGoal(CommonGoal commonGoal) {
        view.updateCommonGoal(commonGoal);
    }

    /**
     * This method notifies that someone has sent a new message in the chat.
     * @param sender is the nickname of the sender
     * @param receivers is the list of the receivers of the message
     * @param text is the text message
     */
    public void setChat(String sender, ArrayList<String> receivers, String text) {
        view.updateChat(sender, receivers, text);
    }

    /**
     * This method notifies if this client has the "seat" or not. Should be noted that this method is called once, only at the beginning of
     * the game
     * @param seat is true if it has the seat, false otherwise
     */
    public void setSeat(boolean seat) {
        view.updateSeat(seat);
    }

    /**
     * This method notifies that this client has achieved a new Token
     * @param token is the new Token
     */
    public void setPersonalTokens(Token token) {
        view.updatePersonalTokens(token);
    }

    /**
     * This method notifies that the game tokens are changed (someone has pulled them).
     * @param tokens are the remaining tokens
     */
    public void setGameTokens(ArrayList<Token> tokens){
        view.updateGameTokens(tokens);
    }

    /**
     * This method notifies that the screen message showed in the CLI should be changed
     * @param text is the text message
     */
    public void setScreenMessage(String text){
        view.updateScreenMessage(text);
    }

    /**
     * This method notifies the nickname of the other players
     * @param playersNickname is the list of nicknames
     */
    public void setPlayersNickname(ArrayList<String> playersNickname){
        view.updatePlayersNickname(playersNickname);
    }

    /**
     * This method sets the nickname of current player
     * @param nickname is the nickname
     */
    public void setCurrentPlayer(String nickname){
        view.updateCurrentPlayer(nickname);
    }

    /**
     * This method sets the shelves of the players, apart from who is playing
     * @param shelves are the shelves
     */
    public void setPlayersShelf(Map<String, Shelf> shelves){
        view.updatePlayersShelf(shelves);
    }

    /**
     * This method refresh the CLI interface
     */
    public void refreshCLI(){
        view.refreshCLI();
    }

    /**
     * This method set the Controller Client side in the view
     */
    public void setClientController(ClientController clientController){view.setClientController(clientController);}

    /**
     * This method enables the user input
     */
    public void enableInput(){
        view.enableInput();
    }

    public void isGameEnded(boolean isGameEnded) { view.isGameEnded(isGameEnded); }
}
