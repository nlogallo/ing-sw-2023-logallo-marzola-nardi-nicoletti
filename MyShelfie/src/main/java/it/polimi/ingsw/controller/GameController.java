package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;

import java.util.ArrayList;

/**
 * This class represents the controller of the game
 */
public class GameController {

    private Game game;

    private String nextPhaseMessage;
    private String currentPlayer;

    public GameController(Game game){
        this.game = game;
        this.nextPhaseMessage = null;
        for(Player p : game.getPlayers()){
            if(p.hasSeat())
                currentPlayer = p.getNickname();
        }
    }

    /**
     * Set the game for the next player's turn
     *
     * @return a String
     */
    public void nextPhase(){
        String nextPlayer = " ";
        try{
            nextPlayer = game.nextPhase().getNickname();
            game.saveGame();
        }catch (IllegalStateException ex){
            game.endGame();
            System.out.println("-Server- Game: " + this.game.getId() +" has ended");
            this.nextPhaseMessage = game.winner().getNickname() + " has won";
        }
        this.nextPhaseMessage = nextPlayer + " is your turn";
        this.currentPlayer = nextPlayer;
    }


    /**
     * Takes tiles from the board and put the in the player shelf.
     * After the move the board is refilled if needed.
     * It also checked if the player can get the first to end token.
     *
     * @param positions list of positions of the tiles to take from the board
     * @param player current player
     * @param column column of the player's shelf to put the tiles in
     *
     * @return a String
     */
    public String moveTiles(ArrayList<Position> positions, Player player, int column){
        ArrayList<Tile> tiles;
        try{
            tiles = game.requestForTiles(positions);
        }catch (IllegalArgumentException ex){
            return "Tiles cannot be pulled";
        }
        player.makeMove(column, tiles);
        player.checkFirstToEnd();
        game.boardRefill();
        game.revertMutex();
        return "Tiles moved";
    }


    /**
     * Give the correct token if the player has achieved the commonGoal
     *
     * @param commonGoal common goal to check
     * @param playerShelf player's shelf
     * @param player current player
     *
     * @return a String
     */
    public String updateToken(CommonGoal commonGoal, TileType[][] playerShelf, Player player){
        Token token;
        try {
            token = game.updateCommonGoal(commonGoal, playerShelf, player);
        } catch (IllegalStateException ex){
            return "";
        } catch (Exception e) {
            return "";
        }
        player.giveToken(token);
        return "Congratulations, you've have earned a new Token: " + commonGoal.getId();
    }


    /**
     * Return a player based on their nickname
     *
     * @param nickname player's nickname
     *
     * @return a Player
     */
    public Player nicknameToPlayer  (String nickname) {
        ArrayList<Player> players = game.getPlayers();
        for (Player p : players){
            if (nickname.equals(p.getNickname()))
                return p;
        }
        System.out.println("There is no player with this nickname");
        return null;
    }


    /**
     * Start a chat between two players
     *
     * @param player1 first player of the chat
     * @param player2 second player of the chat
     */
    public void startDuoChat(Player player1, Player player2) {
        game.startDuoChat(player1, player2);
    }


    /**
     * Getter method
     * @return the common goals
     */
    public ArrayList<CommonGoal> getCommonGoal(){
        return game.getCommonGoals();
    }


    /**
     * Getter method
     * @return the current Board of the game
     */
    public Board getBoard() { return game.getBoard(); }


    /**
     * Getter method
     * @return the current players list
     */
    public ArrayList<Player> getPlayers(){
        return this.game.getPlayers();
    }


    /**
     * Getter method
     * @return the current game
     */
    public Game getGame() { return this.game; }

    /**
     * Getter method
     * @return the current next phase message, that is who is playing in this phase or the winner of the game
     */
    public String getNextPhaseMessage(){
        return this.nextPhaseMessage;
    }

    public String getCurrentPlayer(){
        return this.currentPlayer;
    }

    /**
     * Setter method
     * @param message is the message to save
     */
    public void setNextPhaseMessage(String message){
        this.nextPhaseMessage = message;
    }

}
