package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;

import java.util.ArrayList;

public class GameController {

    private Game game;
    public GameController(Game game){
        this.game = game;
    }

    public String startGame(){
        try{
            game.startGame();
        }catch (IllegalArgumentException ex){
            System.out.println("-Server- Game: " + this.game.getId() + "has not reach the correct number of players");
        }
        return "Game is starting...";
    }

    public String nextPhase(){
        String nextPlayer;
        try{
            nextPlayer = game.nextPhase().getNickname();
            game.saveGame();
        }catch (IllegalStateException ex){
            game.endGame();
            System.out.println("-Server- Game: " + this.game.getId() +" has ended");
            return game.winner().getNickname() + " has won";
        }
        return nextPlayer + "is your turn";
    }

    public String moveTiles(ArrayList<Position> positions, Player player, int column){
        ArrayList<Tile> tiles;
        try{
            tiles = game.requestForTiles(positions);
        }catch (IllegalArgumentException ex){
            return "Tiles cannot be pulled";
        }
        player.makeMove(column, tiles);
        return "Tiles moved";
    }

    public String updateToken(CommonGoal commonGoal, TileType[][] playerShelf, Player player){
        Token token;
        try {
            token = game.updateCommonGoal(commonGoal, playerShelf, player);
        } catch (IllegalStateException ex){
            return "You've already achieved this CommonGoal";
        } catch (Exception e) {
            return "You've not achieved this CommonGoal";
        }
        player.giveToken(token);
        return "Congratulations, you've have earned a new Token";
    }

    public ArrayList<CommonGoal> getCommonGoal(){
        return game.getCommonGoals();
    }

    public Board getBoard(){
        return game.getBoard();
    }

    public ArrayList<Player> getPlayers(){
        return this.game.getPlayers();
    }

}
