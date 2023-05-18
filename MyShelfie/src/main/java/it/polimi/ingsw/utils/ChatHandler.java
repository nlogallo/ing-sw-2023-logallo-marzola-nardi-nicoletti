package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.CLI.CLIView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;


public class ChatHandler {

    private final CLIView view;
    String clientNickname;
    private Scanner scanner = new Scanner(System.in);
    private static final String ANSI_RESET = "\u001B[00m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_CYAN = "\u001B[38;5;14m";
    private static final String ANSI_MAGENTA = "\u001B[38;5;13m";
    private static final String ANSI_BLUE = "\u001B[38;5;33m";
    private static final String ANSI_CREAM = "\u001B[38;5;229m";
    private ArrayList<ClientChat> duoChats = new ArrayList<>();
    private ClientChat globalChat = null;

    public ChatHandler (CLIView view) {
        this.view = view;
        clientNickname = view.getClientNickname();
        //chats.add(new ClientChat(0, view.getPlayersNickname()));
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
            System.out.println("-+-".repeat(59));
            System.out.println();
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
                        System.out.println("-+-".repeat(59));
                        System.out.println();
                    }
                }
                break;
            } else {
                System.out.println(ANSI_RED + "This button isn't allowed" + ANSI_RESET);
            }
        }
    }


    /**
     * This method send message
     * @param sender is the sender of the message
     * @param receivers are the receivers of the message
     * @param text is the text of the message
     */

    //to be fixed
    private void sendMessage (String sender, ArrayList<String> receivers, String text) {
        ClientController clientController = view.getClientController();
        //clientController.sendMessage(sender,receivers, text);
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
        if (receivers.size() > 1) {
            if (globalChat == null) {
                globalChat = new ClientChat(0, view.getPlayersNickname());
            }
            globalChat.addMessage(text, sender, receivers, timestamp);
        } else {

            ArrayList<String> senderPlusReceiver = new ArrayList<>();
            senderPlusReceiver.add(sender);
            senderPlusReceiver.addAll(receivers);

            for (ClientChat chat : duoChats) {
                ArrayList<String> players = chat.getChatMembers();
                if (players.containsAll(senderPlusReceiver)) {
                    isDuoChatAlreadyExist = true;
                    chat.addMessage(text, sender, receivers, timestamp);
                }
            }

            if(!isDuoChatAlreadyExist) {
                duoChats.add(new ClientChat(duoChats.size(), senderPlusReceiver ));
                duoChats.get(duoChats.size()-1).addMessage(text, sender, receivers, timestamp);
            }
        }
    }


    /**
     * This method checks the user input
     * @param userInput is the user input
     * @return true if the user input is correct, otherwise false
     */
    private boolean checkUserInput (String userInput) {

        boolean isCorrect = false;
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
        for (ClientChat chat : duoChats) {
            if(chat.getChatMembers().size() == 2 && chat.getChatMembers().containsAll(players)) {
                return false;
            }
        }
        return true;
    }



    /**
     * This method opens a new duo Chat
     */
    private void createDuoChat() {

        //players doesn't contain the clientNickname
        ArrayList<String> players = view.getPlayersNickname();
        int contOption = 0;
        System.out.println("\n" + "-+-".repeat(59) + "\n");

        while (true) {
            if (duoChats.size() < view.getPlayersNickname().size()) {
                System.out.print(ANSI_CREAM + "Other players with which you can create a new chat: " + ANSI_RESET);
                for (int i = 0; i < players.size(); i++) {
                    if (i == 0 && checkChat(clientNickname, players.get(i))) {
                            System.out.println("         1: " + players.get(i));
                    } else if (checkChat(clientNickname, players.get(i))) {
                            System.out.println(" ".repeat(61) + (i + 1) + ": " + players.get(i));
                    }
                    contOption++;
                }
                System.out.print("Your choose: ");
                String userInput = scanner.nextLine();
                if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), contOption)) {
                    ArrayList<String> newChatPlayerList = new ArrayList<>();
                    newChatPlayerList.add(clientNickname);
                    newChatPlayerList.add(players.get(Integer.parseInt(userInput)-1));
                    duoChats.add(new ClientChat(duoChats.size() + 1, newChatPlayerList));
                    viewDuoChat(duoChats.get(duoChats.size() - 1));
                    break;
                } else {
                    System.out.println(ANSI_RED + "Value is incorrect. Retry!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "You can't open other chat with the same people" + ANSI_RESET);
                chatMenu();
                break;
            }
        }
    }


    /**
     *
     */
    private void chooseDuoChat () {

        int cont;
        System.out.println("\n" + "-+-".repeat(59) + "\n");
        while (true) {
            cont = 0;
            if (duoChats.size() >= 1) {

                System.out.print(ANSI_CREAM + "These are your open duo chats: " + ANSI_RESET);
                int otherPlayerIndex;
                for(int i = 0; i < duoChats.size(); i++) {
                    if(duoChats.get(i).getChatMembers().size() == 2 && duoChats.get(i).getChatMembers().contains(clientNickname)) {
                        otherPlayerIndex = duoChats.get(i).getChatMembers().size() - duoChats.get(i).getChatMembers().indexOf(clientNickname) - 1;
                        String otherPlayer = duoChats.get(i).getChatMembers().get(otherPlayerIndex);
                        if(cont == 0) {
                            System.out.println("        1: " + otherPlayer);
                        } else {
                            System.out.println(" ".repeat(39) + (cont+1) + ": " + otherPlayer);
                        }
                        cont++;
                    }
                }

                System.out.print("Which chat do you want to view: ");
                String userInput = scanner.nextLine();
                if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), cont)) {
                    viewDuoChat(duoChats.get(Integer.parseInt(userInput)-1));
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

    private void viewDuoChat (ClientChat chat) {

        String otherPlayer = chat.getChatMembers().get(chat.getChatMembers().size() - chat.getChatMembers().indexOf(clientNickname) -1);
        System.out.println();
        System.out.println("-+-".repeat(59));
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
        sendMessageOrOpenMenu(chat);
    }

    private void printMessage (ClientMessage message, ArrayList<String> chatMembers) {

        String color;
        if (message.getSender().equals(chatMembers.get(0))) {
            color = ANSI_BLUE;
        } else {
            color = ANSI_MAGENTA;
        }
        System.out.println(ANSI_CREAM + message.getTimestamp() + " " + color + message.getSender() + ANSI_RESET + ": " + message.getMessage());

    }

    private void sendMessageOrOpenMenu(ClientChat chat) {

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

    private void writeMessageAndSendIt (ClientChat chat) {

        String message;
        while(true) {

            System.out.print(ANSI_CREAM + "If you want send a message press Y otherwise press N to come back to MENU: " + ANSI_RESET);
            String userWill = checkContinueString();
            if(userWill.equals("N")) {
                chatMenu();
                break;
            } else {
                System.out.print(ANSI_CREAM + "Type your message (Type Q to QUIT): " + ANSI_RESET);
                message = scanner.nextLine();
                if(message.equals("q") || message.equals("Q")) {
                    chatMenu();
                    break;
                } else {
                    sendMessage(view.getClientNickname(), chat.getChatMembers(), message);
                }
            }
        }
    }

    private String checkContinueString() {

        String outputString;
        while (true) {
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("Y") || input.equals("N")) {
                outputString = input;
                break;
            } else {
                System.out.println(ANSI_RED + "The entry is incorrect. Retry!" + ANSI_RESET);
                System.out.print("If you want send a message press Y otherwise press N: ");
            }
        }
        return outputString;
    }



    private void viewGlobalChat () {

        System.out.println("-+-".repeat(59));
        if (globalChat != null) {
            System.out.println(ANSI_CREAM + "This is the global chat: " + ANSI_RESET);
            for (int i = globalChat.getClientMessages().size(); i > globalChat.getClientMessages().size() - 16; i--) {
                ClientMessage message = globalChat.getClientMessages().get(i);
                System.out.println(ANSI_CREAM + message.getTimestamp() + " " + message.getSender() + ": " + ANSI_RESET + message.getMessage());
            }
            sendMessageOrOpenMenu(globalChat);
        } else {
            System.out.println(ANSI_RED + "There aren't messages in the global chat" + ANSI_RESET);
            chatMenu();
        }
    }

}
