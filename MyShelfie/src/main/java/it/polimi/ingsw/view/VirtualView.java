package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.utils.NetworkMessage;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;

/**
 * This class represents the Virtual View element in the Server. We need it because we split the Controller in both Client and Server.
 */
public class VirtualView {
    private ServerController serverController;

    /**
     * Class constuctor
     * @param game is the game to which this virtual view refers
     */
   public VirtualView(Game game, String nickname){
       this.serverController = new ServerController(game, nickname);
   }

    /**
     * This method represent the request from the Client to move the tiles from the board to te shelg
     * @param networkMessage is the message request send by the client
     * @param player is the player who made the request
     * @return the response NetworkMessage at the request
     */
   public NetworkMessage moveTiles(NetworkMessage networkMessage, Player player){
       ArrayList<Position> tilesPositions = (ArrayList<Position>) networkMessage.getContent().get(0);
       Integer columnToConvert = (Integer) networkMessage.getContent().get(1);
       return serverController.makeMove(tilesPositions, player, columnToConvert);
   }

    /**
     * This method represents the request from the Client to send a message in a chat
     * @param networkMessage is the message request send by the client
     * @return the response NetworkMessage at the request
     */
   public NetworkMessage sendMessage(NetworkMessage networkMessage){
       String sender = (String) networkMessage.getContent().get(0);
       ArrayList<String> nicknameList = new ArrayList<String>();
       nicknameList = (ArrayList<String>) networkMessage.getContent().get(1);
       String text = (String) networkMessage.getContent().get(2);
       return serverController.addMessage(sender, nicknameList, text);
   }

    /**
     * This method represents the initialization needed by the Client to complete its interface
     * @param player is the player where the NetworkMessage will be sent
     * @return the NetworkMessage with all the contents
     */
   public NetworkMessage playerSetup(String player){
       return serverController.playerSetUp(player);
   }

    /**
     * This method is done to update the board of the clients
     * @return the NetworkMessage with the updated board
     */
   public NetworkMessage updateBoard(){
       return serverController.getBoard();
   }

    /**
     * This method is done to update the chats of the clients
     * @param networkMessage is the new message send by someone of the clients
     * @return the NetworkMessage with the new message
     */
   public NetworkMessage updateChat(NetworkMessage networkMessage){
       return serverController.getLastMessagesOfTheChat((String) networkMessage.getContent().get(0), (int) networkMessage.getContent().get(1), (String) networkMessage.getContent().get(2));
   }

    /**
     * This method is done to update the available tokens remained in the game
     * @return the NetworkMessage with the tokens
     */
   public NetworkMessage updateGameTokens(){
       return serverController.getGameTokens();
   }

    /**
     * This method is done to update the client with te info on what is currently done in the game, such as who is playing or the final result of the game.
     * @return the NetworkMessage with the info
     */
   public NetworkMessage updateResult(){
       return serverController.updateResult();
   }

    /**
     * Getter method
     * @return the updated game
     */
   public Game getGame(){
       return serverController.getGame();
   }

}
