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
    public ServerController(Game game) {
        gameController = new GameController(game);
        isTokenChange = false;
    }


    /**
     * This method calls the Game Controller to start the game
     * @return a message with confirmation of the start of the game
     */
    public NetworkMessage startGame() {
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setTextMessage(gameController.startGame());
        return networkMessage;
    }


    /**
     * This method does some checks with the common goal, with player and with the player Shelf and after that,
     * if these checks went well, calls the Game Controller to update the common goal
     *
     * @param commonGoal is the Common Goal to update
     * @param player     is the player who manages to make the Common Goal
     * @return a message with confirmation of the completion of the Common Goal
     * Furthermore, the message contains the player Tokens
     */
    public NetworkMessage updateCommonGoal(CommonGoal commonGoal, Player player) {
        NetworkMessage networkMessage = new NetworkMessage();
        if (commonGoal == null) {
            networkMessage.setTextMessage("CommonGoal are not have not been chosen yet");
            return networkMessage;
        }
        if (player.getShelf().getShelfTypes() == null) {
            System.out.println("-Server- A player's shelf is null");
            networkMessage.setTextMessage("Something went wrong with your shelf, retry");
            return networkMessage;
        }
        if (player == null) {
            System.out.println("-Server- No player selected");
            networkMessage.setTextMessage("Something went wrong, retry");
            return networkMessage;
        }
        networkMessage.setTextMessage(gameController.updateToken(commonGoal, player.getShelf().getShelfTypes(), player));
        networkMessage.addContent(player.getTokenCards());
        return networkMessage;
    }


    /**
     * This method checks if the player isn't null and then checks if the player has done one or both Common Goal
     *
     * @param positions   is the specific position
     * @param player      is the specific player who make the tile move
     * @param column      t is the column of the Shelf where the player inserts the tiles he has taken from the Board
     * @param commonGoal1 is the first of two Common Goal of the Game
     * @param commonGoal2 is the second of two Common Goal of the Game
     * @return a message with the confirmation of the movement of the Tile,
     * and if the player has also managed to complete one or both of the Common Goals,
     * the message also includes the confirmation of the completion of the Common Goal.
     * Furthermore, the message contains in order: player Shelf, Game Board and player Tokens
     */
    public NetworkMessage makeMove(ArrayList<Position> positions, Player player, int column, CommonGoal commonGoal1,
                                   CommonGoal commonGoal2) {

        NetworkMessage networkMessage = new NetworkMessage();
        String stringToSend = null;
        if (player == null) {
            System.out.println("-Server- No player selected");
            networkMessage.setTextMessage("Something went wrong, retry");
            return networkMessage;
        }
        stringToSend = gameController.moveTiles(positions, player, column);
        if (!stringToSend.equals("Tiles cannot be pulled")) {
            String stringPart2 = null;

            if (commonGoal1.checkCommonGoal(player.getShelf().getShelfTypes())) {
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
            }

            stringToSend = stringToSend + "\n" + stringPart2;
            networkMessage.addContent(player.getShelf());
            networkMessage.addContent(gameController.getBoard());
            networkMessage.addContent(player.getTokenCards());

        }
        networkMessage.setTextMessage(stringToSend);
        return networkMessage;
    }


    /**
     * Getter method
     *
     * @return the Common Goal of the Game
     */
    public NetworkMessage getCommonGoal() {
        NetworkMessage networkMessage = new NetworkMessage();
        ArrayList<CommonGoal> commonGoalArrayList = gameController.getCommonGoal();
        if (commonGoalArrayList.size() == 0)
            networkMessage.setTextMessage("No Common Goal found");
        else {
            networkMessage.setTextMessage("These are the Common Goal for this game");
            networkMessage.addContent(commonGoalArrayList.get(0));
            networkMessage.addContent(commonGoalArrayList.get(1));
        }
        return networkMessage;
    }


    /**
     * This method starts a new phase of the Game
     * @return a message with the confirmation of the start of a new Game phase,
     * also if there is a winner this message says the name of the winner of the Game
     */
    public NetworkMessage nextPhase() {
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setTextMessage(gameController.nextPhase());
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
                networkMessage.setTextMessage("Something went wrong, retry");
            }
        } else {

            //add message to globalChat
            chats.get(0).addMessage(message);

        }
        return networkMessage;
    }


    /**
     * Getter method
     * @param senderNickName sender Nickname
     * @param chatId         the ID of the chat
     * @param beforeLastText is the last message that arrived in the chat
     * @return a list with inside, in order: senderNickname, chatId, the new last message (String).
     */
    public NetworkMessage getLastMessagesOfTheChat(String senderNickName, int chatId, String beforeLastText) {

        NetworkMessage networkMessage = new NetworkMessage();
        ArrayList<Chat> chats = gameController.getGame().getChats();
        Chat chat = null;

        for (Chat c : chats) {
            if (c.getId() == chatId) {
                chat = c;
            }
        }

        if (chat != null) {

            Player sender = gameController.nicknameToPlayer(senderNickName);
            ArrayList<Message> messageList = chat.getMessages();
            Message beforeLastMessage = null;

            for (Message message : messageList) {
                if ((message.getSender().equals(sender)) && (message.getMessage().equals(beforeLastText))) {
                    beforeLastMessage = message;
                }
            }

            int indexBeforeLastMessage = messageList.indexOf(beforeLastMessage);

            if ((beforeLastMessage != null) && (indexBeforeLastMessage != messageList.size() - 1)) {

                String textNewLastMessage = messageList.get(indexBeforeLastMessage + 1).getMessage();
                networkMessage.addContent(senderNickName);
                networkMessage.addContent(chatId);
                networkMessage.addContent(textNewLastMessage);
            }
        } else {
            System.out.println("-Server- The chat with this Id: " + chatId + " doesn't exist");
        }
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
        return networkMessage;

    }


    /**
     * Getter method
     * @return a Network message with inside a list of the remaining tokens
     */
    public NetworkMessage getGameTokens() {

        NetworkMessage networkMessage = new NetworkMessage();
        ArrayList<Token> listGameTokens = new ArrayList<>();

        networkMessage.setTextMessage("Game Token Update");


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

        networkMessage.setTextMessage(nickName + "Welcome to the Game, Enjoy");
        networkMessage.addContent(gameController.getBoard());
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
        return networkMessage;
    }
}
