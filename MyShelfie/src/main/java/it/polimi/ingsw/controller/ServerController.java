package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.utils.NetworkMessage;
import java.util.ArrayList;

public class ServerController {
    private final GameController gameController;
    private boolean isTokenChange;

    /**
     * Class constructor
     * @param game is the game associate with the gameController instance
     */
    public ServerController(Game game, String nickname ) {
        gameController = new GameController(game, nickname);
        isTokenChange = false;
    }

    /**
     * This method checks if the player isn't null and then checks if the player has done one or both Common Goal
     *
     * @param positions   is the specific position
     * @param player      is the specific player who make the tile move
     * @param column      t is the column of the Shelf where the player inserts the tiles he has taken from the Board
     * @return a message with the confirmation of the movement of the Tile,
     * and if the player has also managed to complete one or both of the Common Goals,
     * the message also includes the confirmation of the completion of the Common Goal.
     * Furthermore, the message contains in order: player Shelf, Game Board and player Tokens
     */
    public NetworkMessage makeMove(ArrayList<Position> positions, Player player, int column) {

        NetworkMessage networkMessage = new NetworkMessage();
        CommonGoal commonGoal1 = gameController.getCommonGoal().get(0);
        CommonGoal commonGoal2 = gameController.getCommonGoal().get(1);

        String stringToSend = null;
        if (player == null) {
            System.out.println("-Server- No player selected");
            networkMessage.setTextMessage("Something went wrong, retry");
            return networkMessage;
        }
        stringToSend = gameController.moveTiles(positions, player, column);
        gameController.nextPhase();
        if (!stringToSend.equals("Tiles cannot be pulled")) {
            String stringPart2 = null;

        if(!player.getCommonGoals(0)) {
            if (commonGoal1.checkCommonGoal(player.getShelf().getShelfTypes())) {
                gameController.updateToken(commonGoal1, player.getShelf().getShelfTypes(), player);
                this.isTokenChange = true;
            }
        }
        if(!player.getCommonGoals(1)) {
            if (commonGoal2.checkCommonGoal(player.getShelf().getShelfTypes())) {
                gameController.updateToken(commonGoal2, player.getShelf().getShelfTypes(), player);
                this.isTokenChange = false;
            }
        }

            /*if (commonGoal1.checkCommonGoal(player.getShelf().getShelfTypes())) {
                if (!commonGoal2.checkCommonGoal(player.getShelf().getShelfTypes())) {

                    stringPart2 = gameController.updateToken(commonGoal1, player.getShelf().getShelfTypes(), player);
                    this.isTokenChange = true;

                }
            } else if (commonGoal2.checkCommonGoal(player.getShelf().getShelfTypes())) {
                if (!commonGoal1.checkCommonGoal(player.getShelf().getShelfTypes())) {

                    stringPart2 = gameController.updateToken(commonGoal2, player.getShelf().getShelfTypes(), player);
                    this.isTokenChange = true;

                }
            } else if (commonGoal1.checkCommonGoal(player.getShelf().getShelfTypes())) {
                if (commonGoal2.checkCommonGoal(player.getShelf().getShelfTypes())) {
                    gameController.updateToken(commonGoal1, player.getShelf().getShelfTypes(), player);
                    gameController.updateToken(commonGoal2, player.getShelf().getShelfTypes(), player);

                    stringPart2 = "Congratulations, you've have earned two new Tokens: " +
                            commonGoal1.getId() + ", " + commonGoal2.getId();
                    this.isTokenChange = true;
                }
            }*/

            stringToSend = "Well done!";
            networkMessage.setTextMessage(stringToSend);
            networkMessage.addContent(player.getShelf());
            networkMessage.addContent(gameController.getBoard());
            networkMessage.addContent(player.getTokenCards());
        }

        networkMessage.setRequestId("UPS");
        networkMessage.setTextMessage(stringToSend);
        return networkMessage;
    }

    /**
     * This method finds the corresponding chat from the incoming message and then adds the message to the chat
     * In case the chat doesn't exist, this method creates it and the adds the message
     * @param senderNickName is the nickName of the sender
     * @param receiverNickNames is a list with the receiver's nickNames
     * @param text is the incoming message
     * @return an error message if a problem occurred
     */
    public NetworkMessage addMessage(String senderNickName, ArrayList<String> receiverNickNames, String text) {
        NetworkMessage networkMessage = new NetworkMessage();
        Player sender = gameController.nicknameToPlayer(senderNickName);
        ArrayList<Player> receivers = new ArrayList<>();
        ArrayList<Chat> chats = gameController.getGame().getChats();
        Chat chat = null;
        for(String s: receiverNickNames) {
            receivers.add(gameController.nicknameToPlayer(s));
        }

        Message message = new Message(text, sender, receivers);

        if (receivers.size() == 1) {
            for (Chat c : chats) {
                ArrayList<Player> playerList = c.getChatMembers();
                if ((playerList.get(0).equals(sender) && playerList.get(1).equals(receivers.get(0))) ||
                        (playerList.get(0).equals(receivers.get(0)) && (playerList.get(1).equals(sender)))) {
                    chat = c;
                }
            }
            if (chat != null) {
                //add message to duoChat
                chat.addMessage(message);
            } else if ((sender != null) && (receivers.get(0) != null)) {
                //start a new duoChat and add message to it
                gameController.startDuoChat(sender, receivers.get(0));
                chats.get(chats.size() - 1).addMessage(message);
            } else {
                networkMessage.setTextMessage("Something went wrong, retry!");
            }
        } else {

            //add message to globalChat
            chats.get(0).addMessage(message);
            networkMessage.setTextMessage("Message sent correctly!");

        }
        networkMessage.setRequestId("UC");
        return networkMessage;
    }


    /**
     * Getter method
     * @param senderNickName sender Nickname
     * @param chatId         the ID of the chat
     * @param lastMessage is the last message that arrived in the chat
     * @return a list with inside, in order: senderNickname, chatId, the new last message (String).
     */
    public NetworkMessage getLastMessagesOfTheChat(String senderNickName, int chatId, String lastMessage) {

        NetworkMessage networkMessage = new NetworkMessage();
        ArrayList<Chat> chats = gameController.getGame().getChats();
        Chat chat = null;

        for (Chat c : chats) {
            if (c.getId() == chatId) {
                chat = c;
            }
        }

        Player sender = gameController.nicknameToPlayer(senderNickName);

        if (chat != null && sender != null) {
            networkMessage.addContent(senderNickName);
            networkMessage.addContent(chatId);
            networkMessage.addContent(lastMessage);
            } else {
            if(chat == null) {
                System.out.println("-Server- The chat with this Id: " + chatId + " doesn't exist");
            }
            if (sender == null) {
                System.out.println("-Server- Sender nickName doesn't exist");
            }
        }
        networkMessage.setRequestId("UC");
        return networkMessage;
    }


    /**
     * Getter method
     * @return a Network message with inside the current Board.
     */
    public NetworkMessage getBoard() {

        NetworkMessage networkMessage = new NetworkMessage();
        Board board = gameController.getBoard();
        networkMessage.setTextMessage("This is your Board");
        networkMessage.addContent(board);
        networkMessage.setRequestId("UB");
        return networkMessage;

    }


    /**
     * Getter method
     * @return a Network message with inside a list of the remaining tokens
     */
    public NetworkMessage getGameTokens() {

        NetworkMessage networkMessage = new NetworkMessage();
        int numOfPlayer = gameController.getPlayers().size();
        ArrayList<CommonGoal> commonGoals = gameController.getCommonGoal();
        ArrayList<Token> remainingTokenList = gameController.getGame().getThreeMap().getRemainingTokenList(commonGoals, numOfPlayer);
        if(gameController.getGame().getFirstToEnd() == null)
            remainingTokenList.add(new Token(0));
        networkMessage.addContent(remainingTokenList.size());

        for (Token token : remainingTokenList) {
            networkMessage.addContent(token);
        }

        networkMessage.setTextMessage("Game Token Update");
        networkMessage.setRequestId("UGT");
        return networkMessage;
    }


    /**
     * Getter method
     * @return true if the Tokens of the game are changed
     */
    public boolean getIsTokenChange() {
        return this.isTokenChange;
    }


    /**
     * Setter method
     * It makes the reset of the private parameter isTokenChange
     */
    public void resetIsTokenChange() {
        this.isTokenChange = false;
    }


    /**
     * This method does the initial setup of everything the player needs to start playing properly
     * @param nickName is the nickName of the specific player
     * @return a Network message with inside, in order: initial Board, Personal goal, commonGoal 1, commonGoal 2,
     * private parameter hasSeat of the player, number of the other players (Integer), other player nickNames,
     * number of the remaining Game Tokens (Integer) and the remaining Game Tokens
     */
    public NetworkMessage playerSetUp(String nickName) {

        NetworkMessage networkMessage = new NetworkMessage();
        int numOfPlayer = gameController.getPlayers().size();
        Integer numberOfOtherPlayer = numOfPlayer - 1;
        ArrayList<String> otherNickNamesList = new ArrayList<>();

        for (Player s : gameController.getPlayers()) {
            if (!s.equals(gameController.nicknameToPlayer(nickName))) {
                otherNickNamesList.add(s.getNickname());
            }
        }
        Integer numOfGameToken = (gameController.getPlayers().size() * 2) + 1;
        ArrayList<CommonGoal> commonGoals = gameController.getCommonGoal();
        ArrayList<Token> remainingTokenList = gameController.getGame().getThreeMap().getRemainingTokenList(commonGoals, numOfPlayer);
        remainingTokenList.add(new Token(0));

        networkMessage.setTextMessage(nickName + ", welcome to the Game. Enjoy it!");
        networkMessage.addContent(gameController.getBoard());
        networkMessage.addContent(gameController.nicknameToPlayer(nickName).getShelf());
        networkMessage.addContent(gameController.nicknameToPlayer(nickName).getPersonalGoal());
        networkMessage.addContent(gameController.getCommonGoal().get(0));
        networkMessage.addContent(gameController.getCommonGoal().get(1));
        networkMessage.addContent(gameController.nicknameToPlayer(nickName).hasSeat());
        networkMessage.addContent(numberOfOtherPlayer);
        for (String s : otherNickNamesList) {
            networkMessage.addContent(s);
        }
        networkMessage.addContent(numOfGameToken);
        for (Token token : remainingTokenList) {
            networkMessage.addContent(token);
        }
        networkMessage.setRequestId("PS");
        return networkMessage;
    }

    /**
     * This method returns a NetworkMessage with the current player (as a String) or the result of the match
     * @return the NetworkMessage
     */
    public NetworkMessage updateResult(){
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.addContent(gameController.getCurrentPlayer());
        networkMessage.setRequestId("UR");
        if(!gameController.getCurrentPlayer().equals("Game ended"))
            networkMessage.setTextMessage(gameController.getCurrentPlayer() + " is your turn!");
        else
            networkMessage.setTextMessage(gameController.getWinner() + " has won");
        return networkMessage;
    }

    public Game getGame(){
        return gameController.getGame();
    }
}
