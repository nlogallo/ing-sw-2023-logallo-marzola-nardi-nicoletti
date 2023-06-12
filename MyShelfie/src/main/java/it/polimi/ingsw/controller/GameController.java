package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the controller of the game
 */
public class GameController {

    private Game game;
    private String currentPlayer;

    public GameController(Game game, String nickname){
        this.game = game;
        this.currentPlayer = nickname;
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
        }catch (IllegalStateException ex){
            game.endGame();
        }
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
        } catch (Exception ex){
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
    public Player nicknameToPlayer(String nickname) {
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
     * @return the current player
     */
    public String getCurrentPlayer(){
        if (game.getCurrentPlayer() != null)
            return game.getCurrentPlayer().getNickname();
        else
            return "Game ended";
    }

    /**
     * This method returns the final ranking
     * @return the ranking as a String
     */
    public String getWinner(){
        return game.winner();
    }

    /**
     * This method returns the shelves of the players, apart from who is playing
     * @return the ArrayList of shelves
     */
    public Map<String, Shelf> getPlayerShelf(){
        Map<String, Shelf> shelvesMap = new HashMap<>();
        for(Player p : game.getPlayers())
            shelvesMap.put(p.getNickname(), p.getShelf());
        return shelvesMap;
    }

    /**
     * This method allows to revert the mutex of the specific game
     */
    public void revertMutex(){
        game.revertMutex();
    }

}
