package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.commonGoal.CommonGoal;

import java.util.ArrayList;

public class ServerController {
    private final GameController gameController;

    public ServerController(Game game){
        gameController = new GameController(game);
    }

    public NetworkMessage startGame(){
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setTextMessage(gameController.startGame());
        return networkMessage;
    }

    public NetworkMessage updateCommonGoal(CommonGoal commonGoal, Player player){
        NetworkMessage networkMessage = new NetworkMessage();
        if(commonGoal == null) {
            networkMessage.setTextMessage("CommonGoal are not have not been chosen yet");
            return networkMessage;
        }
        if(player.getShelf().getShelfTypes() == null) {
            System.out.println("-Server- A player's shelf is null");
            networkMessage.setTextMessage("Something went wrong with your shelf, retry");
            return networkMessage;
        }
        if(player == null) {
            System.out.println("-Server- No player selected");
            networkMessage.setTextMessage("Something went wrong, retry");
            return networkMessage;
        }
        networkMessage.setTextMessage(gameController.updateToken(commonGoal, player.getShelf().getShelfTypes(), player));
        networkMessage.addContent(PlayerController.UtilPlayerController.getPlayerTokens(player));
        return networkMessage;
    }

    public NetworkMessage makeMove(ArrayList<Position> positions, Player player, int column){
        NetworkMessage networkMessage = new NetworkMessage();
        if(player == null) {
            System.out.println("-Server- No player selected");
            networkMessage.setTextMessage("Something went wrong, retry");
            return networkMessage;
        }
        networkMessage.setTextMessage(gameController.moveTiles(positions, player, column));
        if(!networkMessage.getTextMessage().equals("Tiles cannot be pulled"))
        {
            networkMessage.addContent(PlayerController.UtilPlayerController.getPlayerShelf(player));
            networkMessage.addContent(gameController.getBoard());
        }
        return networkMessage;
    }

    public NetworkMessage getCommonGoal(){
        NetworkMessage networkMessage = new NetworkMessage();
        ArrayList<CommonGoal> commonGoalArrayList  = gameController.getCommonGoal();
        if(commonGoalArrayList.size() == 0)
            networkMessage.setTextMessage("No Common Goal found");
        else{
            networkMessage.setTextMessage("These are the Common Goal for this game");
            networkMessage.addContent(commonGoalArrayList.get(0));
            networkMessage.addContent(commonGoalArrayList.get(1));
        }
        return networkMessage;
    }

    public NetworkMessage nextPhase(){
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setTextMessage(gameController.nextPhase());
        return networkMessage;
    }








}
