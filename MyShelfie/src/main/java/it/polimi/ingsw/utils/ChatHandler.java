package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.CLI.CLIView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This class represents the handler of the chat, it is used to check and acquire the will of the player
 * It is used also for create new duo chats, send new messages and view all the chats
 */
public class ChatHandler {

    private final CLIView view;
    private final Scanner scanner = new Scanner(System.in);
    private static final String ANSI_RESET = "\u001B[00m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_CYAN = "\u001B[38;5;14m";
    private static final String ANSI_MAGENTA = "\u001B[38;5;13m";
    private static final String ANSI_BLUE = "\u001B[38;5;33m";
    private static final String ANSI_CREAM = "\u001B[38;5;229m";
    private static final String ANSI_GREEN = "\u001B[32m";


    /**
     * Class Constructor
     * @param view is a specific CLIView
     */
    public ChatHandler (CLIView view) {
        this.view = view;
    }


    /**
     * This method is the Menu for the player in which he could choose:     1: View Global Chat
     *                                                                      2: View the list of already open duo chats
     *                                                                      3: Create a new duo chat
     *                                                                      4: Come back to Game MENU
     */
    public void chatMenu() {

        while (true) {

            System.out.println();
            System.out.println("-+-".repeat(58));
            System.out.println();

            //PlayersNickname doesn't contain ClientNickname, so for a game with two players, playersNickname has size = 1
            if (view.getPlayersNickname().size() == 1) {
                System.out.println(ANSI_CREAM + "Buttons Allowed:" + ANSI_RESET + "     1: View Global Chat");
                System.out.println(" ".repeat(21) + "2: Come back to Game MENU");
                System.out.print("... : ");
                String userInput = scanner.nextLine();
                if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), 4)) {
                    switch (Integer.parseInt(userInput)) {
                        case 1 -> viewGlobalChat();
                        case 2 -> {
                            System.out.println();
                            System.out.println("-+-".repeat(59));
                            System.out.println();
                        }
                    }
                    break;
                } else {
                    System.out.println(ANSI_RED + "This button isn't allowed" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_CREAM + "Buttons Allowed:" + ANSI_RESET + "     1: View Global Chat");
                System.out.println(" ".repeat(21) + "2: View the list of already open duo chats");
                System.out.println(" ".repeat(21) + "3: Create a new duo chat");
                System.out.println(" ".repeat(21) + "4: Come back to Game MENU");
                System.out.print("... : ");
                String userInput = scanner.nextLine();
                if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), 4)) {
                    switch (Integer.parseInt(userInput)) {
                        case 1 ->  viewGlobalChat();
                        case 2 ->  chooseDuoChat();
                        case 3 ->  createDuoChat();
                        case 4 -> {
                            System.out.println();
                            System.out.println("-+-".repeat(58));
                            System.out.println();
                        }
                    }
                    break;
                } else {
                    System.out.println(ANSI_RED + "This button isn't allowed" + ANSI_RESET);
                }
            }
        }
    }


    /**
     * This method send message
     * @param sender is the sender of the message
     * @param receivers are the receivers of the message
     * @param text is the text of the message
     */

    private void sendMessage (String sender, ArrayList<String> receivers, String text) {
        ClientController clientController = view.getClientController();
        clientController.sendMessage(sender,receivers, text);
    }


    /**
     * This method adds the message in the specific messageList
     * @param sender is the sender of the message
     * @param receivers are the receivers of the message
     * @param text is the text of the message
     * @param timestamp is the timeStamp of the message
     */
    public void addMessage (String sender, ArrayList<String> receivers, String text, Timestamp timestamp) {

        boolean isDuoChatAlreadyExist = false;
        if ((receivers.size() > 1) || (view.getPlayersNickname().size() == 1)) {
            if (view.getGlobalChat() == null) {
                ArrayList<String> players = new ArrayList<>(view.getPlayersNickname());
                players.add(view.getClientNickname());
                view.setGlobalChat(new ClientChat(0, players));
            }
            view.addMessageInGlobalChat(text, sender, receivers, timestamp);
        } else {

            ArrayList<String> senderPlusReceiver = new ArrayList<>();
            senderPlusReceiver.add(sender);
            senderPlusReceiver.addAll(receivers);

            for (ClientChat chat : view.getDuoChats()) {
                ArrayList<String> players = chat.getChatMembers();
                if (players.containsAll(senderPlusReceiver)) {
                    isDuoChatAlreadyExist = true;
                    chat.addMessage(text, sender, receivers, timestamp);
                }
            }

            if(!isDuoChatAlreadyExist) {
                view.getDuoChats().add(new ClientChat(view.getDuoChats().size()+1, senderPlusReceiver ));
                view.getDuoChats().get(view.getDuoChats().size()-1).addMessage(text, sender, receivers, timestamp);
            }
        }
    }


    /**
     * This method checks the user input
     * @param userInput is the user input
     * @return true if the user input is correct, otherwise false
     */
    private boolean checkUserInput (String userInput) {

        if (userInput.equals("")) {
            return false;
        } else {
                for (int i = 0; i < userInput.length(); i++) {
                    if(!Character.isDigit(userInput.charAt(i))) {
                        return false;
                    }
                }
                return true;
        }
    }


    /**
     * This method checks the user choose in the different Menu
     * @param userInput is the user input
     * @param contOption is the number of the different option in the different Menu
     * @return true if the user input is correct, otherwise false
     */
    private boolean checkChoosePlayer (int userInput, int contOption) {
        return userInput >= 1 && userInput <= contOption;
    }


    /**
     * This method checks if the duo chat already exists or not
     * @param clientNickname is the client nickname
     * @param player is the other player
     * @return true if the chat doesn't exist, otherwise false
     */
    private boolean checkChat (String clientNickname, String player) {

        ArrayList<String> players = new ArrayList<>();
        players.add(clientNickname);
        players.add(player);
        for (ClientChat chat : view.getDuoChats()) {
            if(chat.getChatMembers().size() == 2 && chat.getChatMembers().containsAll(players)) {
                return false;
            }
        }
        return true;
    }


    /**
     * This method creates a new duo Chat
     */
    private void createDuoChat() {

        //players doesn't contain the clientNickname
        ArrayList<String> players = view.getPlayersNickname();
        int contOption = 0;
        //isHappen is a boolean variable which is used to make a correct print
        boolean isHappen = false;
        System.out.println("\n" + "-+-".repeat(58) + "\n");

        while (true) {
            if (view.getDuoChats().size() < view.getPlayersNickname().size()) {
                System.out.print(ANSI_CREAM + "Other players with which you can create a new chat: " + ANSI_RESET);
                for (int i = 0; i < players.size(); i++) {
                    if (i == 0 && checkChat(view.getClientNickname(), players.get(i))) {
                            System.out.println("         1: " + players.get(i));
                            isHappen = true;
                    } else if (checkChat(view.getClientNickname(), players.get(i))) {
                        if (isHappen) {
                            System.out.println(" ".repeat(61) + (i + 1) + ": " + players.get(i));
                        } else {
                            System.out.println("         " + (i+1) + ": " + players.get(i));
                            isHappen = true;
                        }
                    }
                    contOption++;
                }
                System.out.print("Your choose: ");
                String userInput = scanner.nextLine();
                if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), contOption) &&
                        checkChat(view.getClientNickname(), players.get(Integer.parseInt(userInput)-1))) {
                    ArrayList<String> newChatPlayerList = new ArrayList<>();
                    newChatPlayerList.add(view.getClientNickname());
                    newChatPlayerList.add(players.get(Integer.parseInt(userInput)-1));
                    view.addDuoChat(new ClientChat(view.getDuoChats().size() + 1, newChatPlayerList));
                    viewDuoChat(view.getDuoChats().get(view.getDuoChats().size() - 1));
                    break;
                } else {
                    System.out.println(ANSI_RED + "Value is incorrect. Retry!" + ANSI_RESET);
                    isHappen = false;
                }
            } else {
                System.out.println(ANSI_RED + "You can't open other chat with the same people" + ANSI_RESET);
                chatMenu();
                break;
            }
        }
    }


    /**
     * This method choose the specific duo chat to see from the list of the already open duo chat
     */
    private void chooseDuoChat () {

        int cont;
        System.out.println("\n" + "-+-".repeat(58) + "\n");
        while (true) {
            cont = 0;
            if (view.getDuoChats().size() > 0) {

                System.out.print(ANSI_CREAM + "These are your open duo chats: " + ANSI_RESET);
                int otherPlayerIndex;
                for(int i = 0; i < view.getDuoChats().size(); i++) {
                    if(view.getDuoChats().get(i).getChatMembers().size() == 2 && view.getDuoChats().get(i).getChatMembers().contains(view.getClientNickname())) {
                        otherPlayerIndex = view.getDuoChats().get(i).getChatMembers().size() - view.getDuoChats().get(i).getChatMembers().indexOf(view.getClientNickname()) - 1;
                        String otherPlayer = view.getDuoChats().get(i).getChatMembers().get(otherPlayerIndex);
                        if(cont == 0) {
                            System.out.println("        1: " + otherPlayer);
                        } else {
                            System.out.println(" ".repeat(39) + (cont+1) + ": " + otherPlayer);
                        }
                        cont++;
                    }
                }

                System.out.print("Which chat do you want to view (Type Q to QUIT): ");
                String userInput = scanner.nextLine();
                if(userInput.equals("q") || userInput.equals("Q")) {
                    chatMenu();
                    break;
                } else if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), cont)) {
                    viewDuoChat(view.getDuoChats().get(Integer.parseInt(userInput)-1));
                    break;
                } else {
                    System.out.println(ANSI_RED + "Value is incorrect. Retry!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED +  "There aren't duo chats open" + ANSI_RESET);
                chatMenu();
                break;
            }
        }
    }


    /**
     * This method prints the specific chat
     * @param chat is the specific chat
     */
    private void viewDuoChat (ClientChat chat) {

        String otherPlayer = chat.getChatMembers().get(chat.getChatMembers().size() - chat.getChatMembers().indexOf(view.getClientNickname()) -1);
        System.out.println();
        System.out.println("-+-".repeat(58));
        System.out.println();
        System.out.println("Chat with: " + ANSI_CYAN + otherPlayer + ANSI_RESET);
        System.out.println();
        ArrayList<ClientMessage> messageList = chat.getClientMessages();
        ArrayList<String> chatMembers = chat.getChatMembers();

        if(chat.getClientMessages().size() <= 20) {
            for (int i = 0; i < messageList.size(); i++) {
                printMessage(messageList.get(i), chatMembers);
            }
        } else {
            for (int i = chat.getClientMessages().size()-1; i > (chat.getClientMessages().size() - 21); i--) {
                printMessage(messageList.get(i), chatMembers);
            }
        }
        sendMessageOrOpenChatMenu(chat);
    }


    /**
     * This method prints the specific message in the correct form factor
     * @param message is the message
     * @param chatMembers are the chat members
     */
    private void printMessage (ClientMessage message, ArrayList<String> chatMembers) {

        String color = null;
        switch (chatMembers.indexOf(message.getSender())) {
            case 0 -> color = ANSI_BLUE;
            case 1 -> color = ANSI_MAGENTA;
            case 2 -> color = ANSI_CYAN;
            case 3 -> color = ANSI_GREEN;
        }
        System.out.println(message.getTimestamp() + " " + ANSI_RESET +  color + message.getSender() + ANSI_RESET + ": " + message.getMessage());
    }


    /**
     * This method is a subMenu in which the player could choose:       1: Send a message
     *                                                                  2: Come back to Chat MENU
     * @param chat is a specific chat
     */
    private void sendMessageOrOpenChatMenu(ClientChat chat) {

        while (true) {
            System.out.println();
            System.out.println(ANSI_CREAM + "Buttons allowed:" + ANSI_RESET + "    1: Send a message" );
            System.out.println(" ".repeat(20) + "2: Come back to Chat MENU");
            System.out.print("... : ");
            String userInput = scanner.nextLine();
            if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), 2)) {
                switch (Integer.parseInt(userInput)) {
                    case 1 -> writeMessageAndSendIt(chat);
                    case 2 -> chatMenu();
                }
                break;
            } else {
                System.out.println(ANSI_RED + "Value is incorrect. Retry!" + ANSI_RESET);
            }
        }
    }


    /**
     * This method checks if the player wants to send a new message or not
     * @param chat is a specific chat
     */
    private void writeMessageAndSendIt (ClientChat chat) {

        String message;
        while(true) {

            System.out.print(ANSI_CREAM + "Type your message (Type Q to QUIT): " + ANSI_RESET);
            message = scanner.nextLine();
            if(message.equals("q") || message.equals("Q")) {
                chatMenu();
                break;
            } else {
                ArrayList<String> receivers = new ArrayList<>();
                if (chat.getChatMembers().size() > 2) {
                    receivers.addAll(view.getPlayersNickname());
                } else {
                    if (chat.getChatMembers().get(0).equals(view.getClientNickname())) {
                        receivers.add(chat.getChatMembers().get(1));
                    } else {
                        receivers.add(chat.getChatMembers().get(0));
                    }
                }
                System.out.println(receivers);
                sendMessage(view.getClientNickname(), receivers, message);
            }
        }
    }


    /**
     * This method prints the global chat
     */
    private void viewGlobalChat () {

        System.out.println("-+-".repeat(58) + "\n");
        if (view.getGlobalChat() == null) {
            ArrayList<String> players = new ArrayList<>();
            players.add(view.getClientNickname());
            players.addAll(view.getPlayersNickname());
            view.setGlobalChat(new ClientChat(0, players));
        }

        if (view.getGlobalChat().getClientMessages().size() >= 1) {
            System.out.println(ANSI_CREAM + "This is the global chat: " + ANSI_RESET + "\n");
            int minSize;
            if(view.getGlobalChat().getClientMessages().size() - 16 < 0)
                minSize = 0;
            else
                minSize = view.getGlobalChat().getClientMessages().size() - 16;
            for (int i = minSize; i < view.getGlobalChat().getClientMessages().size(); i++) {
                ClientMessage message = view.getGlobalChat().getClientMessages().get(i);
                printMessage(message, view.getGlobalChat().getChatMembers());
            }
        } else {
            System.out.println(ANSI_RED + "There aren't messages in the global chat" + ANSI_RESET);
        }
        sendMessageOrOpenChatMenu(view.getGlobalChat());
    }
}
