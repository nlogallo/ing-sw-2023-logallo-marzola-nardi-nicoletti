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
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[38;5;14m";
    public static final String ANSI_MAGENTA = "\u001B[38;5;13m";
    public static final String ANSI_BLUE = "\u001B[38;5;33m";
    public static final String ANSI_CREAM = "\u001B[38;5;229m";

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

                        //return to normal view
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
                        //open chat view

                        while (true) {

                            // chatHandler

                            String button = scanner.nextLine().toUpperCase();
                            if (button.equals("Q")) {

                                //return to normal view
                                break;

                            } else {

                                //chatHandler
                                //send messages

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

        int cont = 3;
        boolean inWhile = true;
        while (inWhile && cont != 0) {

            System.out.println("You can take " + cont + " more tiles.");
            String stringRow = generateCorrectNumberString("Type the row: ");
            rowPosition.add(stringRow);

            String stringColumn = generateCorrectNumberString("Type the column: ");
            columnPosition.add(stringColumn);

            cont--;

            if (cont != 0) {

                System.out.print("If you want take an other tile press Y otherwise press N: ");
                String s = scanner.nextLine().toUpperCase();
                String will = checkContinueString(s);

                if (will.equals("N")) {
                    inWhile = false;
                }
            }
        }

        stringColumnShelf = generateCorrectNumberString("Type the Shelf's column where you want to insert the tile: ");

        String tilePosition = null;

        for (int i = 0; i < rowPosition.size(); i++) {
            tilePosition = rowPosition.get(i) + columnPosition.get(i);
            tilePositions.add(tilePosition);
        }

        tilePositions = adjustShelfColumnOrder(tilePositions, stringColumnShelf);

        //to be fixed as soon as change the controls on the Controller
        //to also have numerical control immediately
        view.moveTiles(tilePositions, Integer.parseInt(stringColumnShelf));
        boolean isOccurredAnError = view.getOccurredAnError();
        if (isOccurredAnError) {
            System.out.println(ANSI_RED + "The numbers are incorrect. Retry!" + ANSI_RESET);
            view.setIsOccurredAnError(false);
            inputToMoveTile();
        }
    }


    /**
     * This method checks if user's enter is a number
     * @param name can be:
     *             - Type the row:
     *             - Type the column:
     *             - Type the Shelf's column where you want to insert the tile:
     * @return the correct string
     */
    private String generateCorrectNumberString(String name) {

        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.print(name);
            String val = scanner.nextLine();
            if (checkIfIsANumberString(val)) {
                return val;
            }
            
            System.out.println(ANSI_RED + "The enter isn't a number!" + ANSI_RESET);
        }
    }


    /**
     * This private methods checks if the String is composed only by number characters.
     * @param val is the String to check
     * @return tue if the String is composed only by number characters
     */
    private boolean checkIfIsANumberString(String val) {

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

        ArrayList<String> idAlreadyTaken = new ArrayList<>();
        ArrayList<String> newTilePositions = new ArrayList<>();

        if (oldTilePositions.size() != 1 && oldTilePositions.size() != 0) {

            int intTileRow;
            int intTileColumn;

            System.out.println("You have chosen these tiles: ");
            for(int i = 0; i < oldTilePositions.size(); i++) {

                intTileRow = Character.getNumericValue(oldTilePositions.get(i).charAt(0));
                intTileColumn = Character.getNumericValue(oldTilePositions.get(i).charAt(1));

                System.out.println(getBoardTilesAtPosition(intTileRow - 1, intTileColumn - 1) + "ID: " + (i+1)
                        + "; row: " + intTileRow + "; column: " + intTileColumn + ANSI_RESET);

            }

            System.out.println("Sort the Tile IDs according to the order you want " +
                    "the Tiles to be placed in the column(" + stringShelfColumn + "): ");

            for (int i = 0; i < oldTilePositions.size(); i++) {

                switch (i) {
                    case 0 -> System.out.print("Type First Tile ID: ");
                    case 1 -> System.out.print("Type Second Tile ID: ");
                    case 2 -> System.out.print("Type Third Tile ID: ");
                }

                String input = checkIDTile(i, idAlreadyTaken, oldTilePositions.size());
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
     * @param cont is a simply counter to choose the correct case in the switch
     * @param idAlreadyTaken is the list of the IDs already chosen by the Player
     * @param size is the size of oldTilePositions
     * @return the correct ID value
     */
    private String checkIDTile (int cont, ArrayList<String> idAlreadyTaken, int size) {

        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();
        String outputString = val;

        if (!checkIfIsANumberString(val) || idAlreadyTaken.contains(val) || (Integer.parseInt(val) < 0 || Integer.parseInt(val) > size)) {

            boolean isIncorrect = true;
            while (isIncorrect) {
                System.out.println(ANSI_RED + "Incorrect Value. Retry!" + ANSI_RESET);

                switch (cont) {
                    case 0 -> System.out.print("Type First Tile ID: ");
                    case 1 -> System.out.print("Type Second Tile ID: ");
                    case 2 -> System.out.print("Type Third Tile ID: ");
                }

                String input = scanner.nextLine();
                if ((input.equals("1")) || input.equals("2") || input.equals("3")) {
                    if ((Integer.parseInt(input) <= size) && (!idAlreadyTaken.contains(input))) {
                        outputString = input;
                        isIncorrect = false;
                    }
                }
            }
        }
        return outputString;
    }


    /**
     * This method finds the correct Tile colour
     * @param x is the Tile's row
     * @param y is the Tile's column
     * @return the correct Tile colour
     */
    private String getBoardTilesAtPosition(int x, int y){
        return switch (view.getBoard().getTilesType()[x][y]) {
            case TROPHY ->  ANSI_CYAN;
            case PLANT ->  ANSI_MAGENTA;
            case FRAME ->  ANSI_BLUE ;
            case GAME ->  ANSI_YELLOW ;
            case BOOK ->  ANSI_CREAM ;
            case CAT ->  ANSI_GREEN ;
            case EMPTY -> ANSI_WHITE ;
        };
    }
}
