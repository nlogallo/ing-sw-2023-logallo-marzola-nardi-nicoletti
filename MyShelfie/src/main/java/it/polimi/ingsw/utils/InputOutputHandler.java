package it.polimi.ingsw.utils;

import it.polimi.ingsw.view.CLIView;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * This class represents the InputOutputHandler, it is used to check the values chosen by the Player in the CLI.
 */
public class InputOutputHandler {

    private final CLIView view;
    private final Scanner scanner = new Scanner(System.in);
    public static final String ANSI_RESET = "\u001B[00m";
    public static final String ANSI_RED = "\u001B[31m";

    public InputOutputHandler (CLIView view) {
        this.view = view;
    }


    /**
     * This method checks if the player clicks the right buttons in the right phase of the game.
     * When is its turn the player can type 1 to make the move of the Tiles or 2 to open the Chat, if opens the chat
     * the player can type Q to exit from the chat, and then he can press another time 2 to open the chat or 1 to make
     * the move.
     * If not the player's turn, it can only open the chat
     */
    public void userPressButton() {

        while (view.getCurrentPlayer() == null || !view.getCurrentPlayer().equals(view.getClientNickname())) {

            System.out.println("Button allowed: 2: Open Chat");
            String input = scanner.nextLine();
            if (input.equals("2")) {

                System.out.println("To exit from the chat press Q and the click enter");
                //chatHandler

                while (true) {
                    String button = scanner.nextLine().toUpperCase();
                    if (button.equals("Q")) {

                        //torna a visuale normale
                        break;
                    }
                }
            } else {
                System.out.println(ANSI_RED + "Value is incorrect. You can type only 2 to open the chat. Retry!" + ANSI_RESET);
            }
        }

        if (view.getCurrentPlayer().equals(view.getClientNickname())) {

            while(true) {

                System.out.println("Buttons allowed:     1: Make your move ");
                System.out.println("                     2: Open Chat");
                System.out.print("... : ");

                String input = scanner.nextLine();
                if(input.equals("1") || input.equals("2")) {

                    if (input.equals("1")) {

                        inputToMoveTile();
                        break;

                    } else {

                        System.out.println("To exit from the chat press Q and the click enter");
                        //apri visuale chat

                        while (true) {

                            // chatHandler

                            String button = scanner.nextLine().toUpperCase();
                            if (button.equals("Q")) {

                                //torna a visuale normale
                                break; // da contollare

                            } else {

                                //manda messaggio
                                //da finire
                            }
                        }
                    }
                } else {
                    System.out.println(ANSI_RED + "Incorrect value. You can type 1 if you want to take the Tiles " +
                            "or 2 if you want to open the chat. Retry!" + ANSI_RESET);
                }
            }
        }
    }


    /** This method does some checks of the values chosen by the Player on the Board.
     * It calls moveTiles of the CliView and if these values are okay this method terminate, otherwise this method
     * calls itself.
     * It continues to calls itself until the Player will insert correct values.
     */
    private void inputToMoveTile() {

        ArrayList<String> tilePositions = new ArrayList<>();
        ArrayList<String> rowPosition = new ArrayList<>();
        ArrayList<String> columnPosition = new ArrayList<>();
        String stringColumnShelf = null;

        System.out.print("You can take 3 more tiles.\n" +
                    "Type the row: ");
        String stringRow = scanner.nextLine();
        rowPosition.add(stringRow);

        System.out.print("Type the column: ");
        String stringColumn = scanner.nextLine();
        columnPosition.add(stringColumn);

        System.out.print("If you want take an other tile press Y otherwise press N: ");
        String s = scanner.nextLine().toUpperCase();
        String will = checkContinueString(s);

        if (will.equals("Y")) {
            int cont = 2;
            boolean inWhile = true;
            while (inWhile && cont != 0) {

                System.out.print("You can take " + cont + " more tiles.\n" +
                        "Type the row: ");
                stringRow = scanner.nextLine();
                rowPosition.add(stringRow);

                System.out.print("Type the column: ");
                stringColumn = scanner.nextLine();
                columnPosition.add(stringColumn);

                cont--;

                if (cont != 0) {

                    System.out.print("If you want take an other tile press Y otherwise press N: ");
                    s = scanner.nextLine().toUpperCase();
                    will = checkContinueString(s);

                    if (will.equals("N")) {
                        inWhile = false;
                    }
                }
            }

        }

        System.out.print("Type the Shelf's column where you want to insert the tile: ");
        stringColumnShelf = scanner.nextLine();

        if (!(checkInput(rowPosition, columnPosition, stringColumnShelf))) {
            System.out.println(ANSI_RED + "The numbers are incorrect. Retry!" + ANSI_RESET);
            inputToMoveTile();
        }

        String tilePosition = null;

        for (int i = 0; i<rowPosition.size(); i++) {
            tilePosition = rowPosition.get(i) + columnPosition.get(i);
            tilePositions.add(tilePosition);
        }

        tilePositions = adjustShelfColumnOrder(tilePositions, stringColumnShelf);

        view.moveTiles(tilePositions, Integer.parseInt(stringColumnShelf));
        boolean isOccurredAnError = view.getOccurredAnError();
        if(isOccurredAnError) {
            System.out.println(ANSI_RED + "The numbers are incorrect. Retry!" + ANSI_RESET);
            view.setIsOccurredAnError(false);
            inputToMoveTile();
        }
    }


    /**
     * This private method checks if the parameter is a number or not.
     * @param rowPositions is the rows' ArrayList of the Tiles that the player picks up
     * @param columnPositions is the column's ArrayList of the Tiles that the player picks up
     * @param columnShelf is the column in which the player has decided to insert the collected tiles
     * @return true if all strings are String of number, otherwise false
     */
    private boolean checkInput(ArrayList<String> rowPositions, ArrayList<String> columnPositions, String columnShelf) {

        boolean isCorrect = true;

        for (String s : rowPositions) {
            isCorrect = checkFor(s);
            if (!isCorrect) {
                return false;
            }
        }
        for (String s : columnPositions) {
            isCorrect = checkFor(s);
            if (!isCorrect) {
                return false;
            }
        }
        isCorrect = checkFor(columnShelf);
        return isCorrect;
    }


    /**
     * This private methods checks if the String is composed only by number characters.
     * @param val is the String to check
     * @return tue if the String is composed only by number characters
     */
    private boolean checkFor(String val) {

        if (val.equals("")) {
            return false;
        } else {
            for (int i = 0; i < val.length(); i++) {
                if(!Character.isDigit(val.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * This method sorts the tiles collected by the player according to how he wants to insert them in the Shelf column.
     * @param oldTilePositions is the older order of Tiles
     * @param stringShelfColumn is the column in which the player wants to insert the Tiles
     * @return the new sorted ArrayList
     */
    private ArrayList<String> adjustShelfColumnOrder (ArrayList<String> oldTilePositions, String stringShelfColumn) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> idAlreadyTaken = new ArrayList<>();
        ArrayList<String> newTilePositions = new ArrayList<>();

        if (oldTilePositions.size() != 1 && oldTilePositions.size() != 0) {

            System.out.println("You have chosen these tiles: ");
            for(int i = 0; i < oldTilePositions.size(); i++) {
                System.out.println("ID: " + (i+1) + "; row: " + oldTilePositions.get(i).charAt(0) + "; column: "
                        + oldTilePositions.get(i).charAt(1));
            }

            System.out.println("Sort the Tile IDs according to the order you want " +
                    "the Tiles to be placed in the column(" + stringShelfColumn + "): ");

            for (int i = 0; i < oldTilePositions.size(); i++) {
                switch (i) {
                    case 0:
                        System.out.print("Type First Tile ID: ");
                        break;
                    case 1:
                        System.out.print("Type Second Tile ID: ");
                        break;
                    case 2:
                        System.out.print("Type Third Tile ID: ");
                        break;
                }
                String input = scanner.nextLine();
                input = checkIDTile(input, i+1, idAlreadyTaken, oldTilePositions.size());
                idAlreadyTaken.add(input);
                newTilePositions.add(oldTilePositions.get(Integer.parseInt(input)-1));
            }
        } else {
            newTilePositions.addAll(oldTilePositions);
        }
        System.out.println("-".repeat(300));
        return newTilePositions;
    }


    /**
     * This method check if the String val is equals Y or N.
     * If val is not equals Y or N this method continues to request a new String.
     * @param val is the String to check.
     * @return the correct String equals Y or N
     */
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


    /**
     * This method checks if the ID value, to reorder the Tiles collected according to how the
     * player wants to insert them in the Shelf column, is a correct value or not.
     * To be a correct value it must be equal to 1, 2 or 3 and must not have already been chosen previously.
     * If the ID is incorrect this method continues to request a new ID.
     * @param val is the value to check
     * @param cont is a simply counter to choose the correct case in the switch
     * @param idAlreadyTaken is the list of the IDs already chosen by the Player
     * @param size is the size of oldTilePositions
     * @return the correct ID value
     */
    private String checkIDTile (String val, int cont, ArrayList<String> idAlreadyTaken, int size) {

        Scanner scanner = new Scanner(System.in);
        String outputString = val;

        if (!(val.compareTo("0") > 0 && String.valueOf(size+1).compareTo(val) > 0) || idAlreadyTaken.contains(val)) {

            boolean isIncorrect = true;
            while (isIncorrect) {
                System.out.println(ANSI_RED + "Incorrect Value. Retry!" + ANSI_RESET);

                switch (cont) {
                    case 1:
                        System.out.print("Type First Tile ID: ");
                        break;
                    case 2:
                        System.out.print("Type Second Tile ID: ");
                        break;
                    case 3:
                        System.out.print("Type Third Tile ID: ");
                        break;
                }

                String input = scanner.nextLine();
                if((input.equals("1")) || input.equals("2") || input.equals("3")) {
                    if ((Integer.parseInt(input) <= size) && (!idAlreadyTaken.contains(input))) {
                        outputString = input;
                        isIncorrect = false;
                    }
                }
            }
        }
        return outputString;
    }
}
