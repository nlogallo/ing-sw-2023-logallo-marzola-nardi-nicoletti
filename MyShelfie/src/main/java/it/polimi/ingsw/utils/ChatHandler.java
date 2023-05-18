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
    private ArrayList<ClientChat> chats = new ArrayList<>();

    public ChatHandler (CLIView view) {
        this.view = view;
        clientNickname = view.getClientNickname();
    }


    /**
     * This method is the Menu for the player in which he could choose:     1: View Global Chat
     *                                                                      2: View dio chats
     *                                                                      3: Open duo chat
     */
    public void chatMenu() {

        while (true) {
            System.out.println("Buttons Allowed:        1: View Global Chat\n" +
                    "                                   2: View duo chats\n" +
                    "                                   3: Open duo chat"
                    );
            String userInput = scanner.nextLine();
            if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), 3)) {
                switch (Integer.parseInt(userInput)) {
                    case 1 ->  { viewGlobalChat(); break; }
                    case 2 -> { chooseDuoChat(); break; }
                    case 3 ->  { openDuoChat(); break; }
                }
                break;
            } else {
                System.out.println(ANSI_RED + "This button isn't allowed" + ANSI_RESET);
            }
        }
    }


    /**
     * This method send message
     * @param receivers are the receivers of the message
     * @param text is the text of the message
     */
    private void sendMessage (ArrayList<String> receivers, String text) {
        ClientController clientController = view.getClientController();
        clientController.sendMessage(receivers, text);
    }


    /**
     * This method adds the message in the specific messageList
     * @param sender is the sender of the message
     * @param receivers are the receivers of the message
     * @param text is the text of the message
     * @param timestamp is the timeStamp of the message
     */
    public void addMessage (String sender, ArrayList<String> receivers, String text, Timestamp timestamp) {

        if (receivers.size() > 1) {
            if (chats.size() == 0) {
                chats.add(new ClientChat(0, view.getPlayersNickname()));
            }
            chats.get(0).addMessage(text, sender, receivers, timestamp);
        } else {
            for (ClientChat chat : chats) {
                ArrayList<String> players = chat.getChatMembers();
                ArrayList<String> senderPlusReceiver = new ArrayList<>();
                senderPlusReceiver.add(sender);
                senderPlusReceiver.addAll(receivers);
                if (players.containsAll(senderPlusReceiver)) {
                    chat.addMessage(text, sender, receivers, timestamp);
                }
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
        for (ClientChat chat : chats) {
            if(chat.getChatMembers().size() == 2 && chat.getChatMembers().containsAll(players)) {
                return false;
            }
        }
        return true;
    }


    /**
     * This method opens a new duo Chat
     */
    private void openDuoChat () {

        ArrayList<String> players = view.getPlayersNickname();
        int contOption = 0;
        String player = null;

        while (true) {
            System.out.print("Type the correct number:");
            for (int i = 0; i < players.size(); i++) {
                player = players.get(i);
                if(!player.equals(clientNickname)) {
                    if (i == 0 && checkChat(clientNickname, player)) {
                        System.out.println("         1: " + player);
                    }
                    if (checkChat(clientNickname, player)) {
                        System.out.println(" ".repeat(30) + (i+1) + ": " + player);
                    }
                    contOption ++;
                }
            }
            String userInput = scanner.nextLine();
            if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), contOption)) {
                ArrayList<String> newChatPlayerList = new ArrayList<>();
                newChatPlayerList.add(clientNickname);
                newChatPlayerList.add(player);
                chats.add(new ClientChat(chats.size(), newChatPlayerList));
                viewDuoChat(chats.get(chats.size()-1));
                break;
            } else {
                System.out.println(ANSI_RED + "Value is incorrect. Retry!" + ANSI_RESET );
            }
        }
    }


    /**
     *
     */
    private void chooseDuoChat () {

        System.out.print("These are your open duo chats:");
        int cont;
        while (true) {
            cont = 0;
            for(int i = 0; i < chats.size(); i++) {
                if(chats.get(i).getChatMembers().size() == 2 && chats.get(i).getChatMembers().contains(clientNickname)) {
                    String otherPlayer = chats.get(i).getChatMembers().get(chats.get(i).getChatMembers().size()- chats.get(i).getChatMembers().indexOf(clientNickname)-1);
                    if(cont == 0) {
                        System.out.println("    1: " + otherPlayer);
                    } else {
                        System.out.println(" ".repeat(15) + (cont+1) + ": " + otherPlayer);
                    }
                    cont++;
                }
            }
            System.out.print("Your choose: ");
            String userInput = scanner.nextLine();
            if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), cont)) {
                viewDuoChat(chats.get(Integer.parseInt(userInput)-1));
                break;
            } else {
                System.out.println(ANSI_RED + "Value is incorrect. Retry!" + ANSI_RESET);
            }
        }
    }

    private void viewDuoChat (ClientChat chat) {

        String otherPlayer = chat.getChatMembers().get(chat.getChatMembers().size() - chat.getChatMembers().indexOf(clientNickname) -1);
        System.out.println("Chat with: " + ANSI_CREAM + otherPlayer + ANSI_RESET);
        System.out.println();
        ArrayList<ClientMessage> messageList = chat.getClientMessages();
        ArrayList<String> chatMembers = chat.getChatMembers();

        if(chat.getClientMessages().size() <= 20) {
            for (int i = 0; i < messageList.size(); i++) {
                printMessage(messageList.get(i), chatMembers);
            }
        }
        for (int i = chat.getClientMessages().size()-1; i > (chat.getClientMessages().size() - 21); i--) {
            printMessage(messageList.get(i), chatMembers);
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
            System.out.println("Buttons allowed:    1: Send a message" );
            System.out.println("                    2: Come back to Chat MENU");
            String userInput = scanner.nextLine();
            if (checkUserInput(userInput) && checkChoosePlayer(Integer.parseInt(userInput), 2)) {
                switch (Integer.parseInt(userInput)) {
                    case 1: { writeMessageAndSendIt(chat); break; }
                    case 2: { chatMenu(); break; }
                }
            } else {
                System.out.println(ANSI_RED + "Value is incorrect. Retry!" + ANSI_RESET);
            }
        }
    }

    private void writeMessageAndSendIt (ClientChat chat) {

        String message = scanner.nextLine();
        sendMessage(chat.getChatMembers(), message);
        while(true) {
            System.out.print("If you want sen an other message press Y otherwise press N: ");
            String s = scanner.nextLine().toUpperCase();
            String will = checkContinueString(s);
            if (will.equals("N")) {
                break;
            }
            message = scanner.nextLine();
            sendMessage(chat.getChatMembers(), message);
        }

    }

    private String checkContinueString(String val) {

        String outputString = val;

        if(!(val.equals("Y") || val.equals("N"))) {

            boolean isStringIncorrect = true;
            while (isStringIncorrect) {

                System.out.println(ANSI_RED + "The entry is incorrect!" + ANSI_RESET);
                System.out.print("If you want take an other tile press Y otherwise press N: ");
                String newString = scanner.nextLine().toUpperCase();
                if (newString.equals("Y") || newString.equals("N")) {
                    outputString = newString;
                    isStringIncorrect = false;
                }
            }
        }
        return outputString;
    }


    private void viewGlobalChat () {

        System.out.println("-+-".repeat(40));
        System.out.println("This is the global chat: ");
        ClientChat chat = chats.get(0);
        for (int i = chat.getClientMessages().size(); i > chat.getClientMessages().size() - 16; i--) {
            ClientMessage message = chat.getClientMessages().get(i);
            System.out.println(ANSI_CREAM + message.getTimestamp() + " " + message.getSender() + ": " + ANSI_RESET + message.getMessage());
        }
        chatMenu();
    }

}
