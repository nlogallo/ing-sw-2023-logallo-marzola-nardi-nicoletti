package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.view.CLI.CLIMenus;
import it.polimi.ingsw.view.CLI.CLIView;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * This class represents the InputOutputHandler, it is used to acquire and check the values chosen by the Player in the CLI.
 */
public class InputOutputHandler {

    private final CLIView view;
    private final ChatHandler chatHandler;
    private final Scanner scanner = new Scanner(System.in);
    private static final String ANSI_RESET = "\u001B[00m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_CYAN = "\u001B[38;5;14m";
    private static final String ANSI_MAGENTA = "\u001B[38;5;13m";
    private static final String ANSI_BLUE = "\u001B[38;5;33m";
    private static final String ANSI_CREAM = "\u001B[38;5;229m";


    /**
     * Class Constructor
     * @param view is a specific view
     */
    public InputOutputHandler (CLIView view) {
        this.view = view;
        chatHandler = new ChatHandler(view);

    }

    /**
     * This method checks if the player clicks the right buttons in the right phase of the game.
     * When is its turn the player can type :       1: Make move Tiles
     *                                              2: Open Chat Menu
     *                                              3: Show other player Shelves
     *                                              4: Show first commonGoal description
     *                                              5: Show second commonGoal description
     *
     * If not the player's turn, it can only type:  2: Open Chat Menu
     *                                              3: Show other player Shelves
     *                                              4: Show first commonGoal description
     *                                              5: Show second commonGoal description
     */
    public void userPressButton() {

        while (view.getCurrentPlayer() == null || !view.getCurrentPlayer().equals(view.getClientNickname())) {

            System.out.println(ANSI_CREAM + "Button allowed:" + ANSI_RESET + "     2: Open Chat MENU");
            System.out.println(" ".repeat(20) + "3: Show other player Shelves ");
            System.out.println(" ".repeat(20) + "4: Show first commonGoal description");
            System.out.println(" ".repeat(20) + "5: Show second commonGoal description");
            System.out.println("... : ");

            String input = scanner.nextLine();
            if (input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5") ) {

                switch (Integer.parseInt(input)) {
                    case 2 -> chatHandler.chatMenu();
                    case 3 -> CLIMenus.shelvesMenu(view);
                    case 4 -> showCommonGoalDescription(view.getCommonGoals().get(0), 0);
                    case 5 -> showCommonGoalDescription(view.getCommonGoals().get(1), 1);
                }
            } else {
                System.out.println(ANSI_RED + "Incorrect value. Retry!" + ANSI_RESET);
            }
        }

        if (view.getCurrentPlayer().equals(view.getClientNickname())) {

            boolean inWhile = true;
            while(inWhile) {

                System.out.println(ANSI_CREAM + "Buttons allowed:" + ANSI_RESET + "     1: Make your move ");
                System.out.println(" ".repeat(21) + "2: Open Chat MENU");
                System.out.println(" ".repeat(21) + "3: Show other player Shelves");
                System.out.println(" ".repeat(21) + "4: Show first commonGoal description");
                System.out.println(" ".repeat(21) + "5: Show second commonGoal description");
                System.out.print("... : ");

                String input = scanner.nextLine();
                if(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5") ) {

                    switch (Integer.parseInt(input)) {
                        case 1 -> {
                            inputToMoveTile();
                            inWhile = false;
                        }
                        case 2 -> chatHandler.chatMenu();
                        case 3 -> CLIMenus.shelvesMenu(view);
                        case 4 -> showCommonGoalDescription(view.getCommonGoals().get(0), 0);
                        case 5 -> showCommonGoalDescription(view.getCommonGoals().get(1), 1);
                    }
                } else {
                    System.out.println(ANSI_RED + "Incorrect value. Retry!" + ANSI_RESET);
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

        ArrayList<String> positions = new ArrayList<>();
        String stringColumnShelf = null;

        int cont = 3;
        boolean inWhile = true;
        boolean repeatInputToMoveTile = false;
        while (inWhile && cont != 0) {

            System.out.println("\n" + ANSI_BLUE + "You can take " + cont + " more tiles." + ANSI_RESET);
            String stringRow = generateNumberString("Type the row (Q to quit): ", 9);

            if (checkQuitFromWhile(stringRow)) {
                repeatInputToMoveTile = true;
                break;
            }

            String stringColumn = generateNumberString("Type the column (Q to quit): ", 9);
            if( checkQuitFromWhile(stringColumn)) {
                repeatInputToMoveTile = true;
                break;
            }

            if (view.callCheckNullTiles(Integer.parseInt(stringRow), Integer.parseInt(stringColumn), view.getBoard())) {
                if(view.callCheckCanPullTile(Integer.parseInt(stringRow), Integer.parseInt(stringColumn), view.getBoard())) {
                    positions.add(stringRow + stringColumn);
                    if(cont == 3) {

                        cont--;
                        System.out.print("If you want take an other tile press Y otherwise press N: ");
                        String will = checkContinueString();

                        if (will.equals("N")) {
                            inWhile = false;
                        }

                    } else {
                        if(view.callCheckIsAlignedTiles(positions, view.getBoard())) {

                            cont--;
                            if (cont != 0) {

                                System.out.print("If you want take an other tile press Y otherwise press N: ");
                                String will = checkContinueString();

                                if (will.equals("N")) {
                                    inWhile = false;
                                }
                            }

                        } else {
                            System.out.println(ANSI_RED + "You have to pick aligned tiles. Retry!" + ANSI_RESET);
                            positions.remove(positions.size()-1);
                        }
                    }
                } else {
                    System.out.println(ANSI_RED + "You can't pull this tile. Retry!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "There isn't a tile in:   row: " + stringRow + "; column: " + stringColumn + ANSI_RESET);
            }
        }

        if (repeatInputToMoveTile) {
            System.out.println("\n" + "-+-".repeat(58) + "\n");
            userPressButton();
        } else {
            while (true) {

                stringColumnShelf = generateNumberString("Type the Shelf's column where you want to insert the tile (Q to quit): ", 5);
                if (stringColumnShelf.equalsIgnoreCase("Q")) {
                    repeatInputToMoveTile = true;
                    break;
                } else if (view.callCheckFreeSpotsInColumnShelf(positions, view.getShelf(), Integer.parseInt(stringColumnShelf))) {
                    break;
                } else {
                    System.out.println(ANSI_RED + "You don't have enough free spots in this column. Retry!" + ANSI_RESET);
                }
            }

            if (!repeatInputToMoveTile) {
                positions = adjustShelfColumnOrder(positions, stringColumnShelf);
                view.moveTiles(positions, Integer.parseInt(stringColumnShelf));
            } else {
                System.out.println("\n" + "-+-".repeat(58) + "\n");
                userPressButton();
            }
        }
    }


    /**
     * This method checks if the user will is to exit from the while
     * @param userWill is the user will
     * @return false if the user will is to exit from the while, otherwise true
     */
    public boolean checkQuitFromWhile (String userWill) {
        return userWill.equalsIgnoreCase("Q");
    }


    /**
     * This method checks if user's enter is a number or Q to quit
     * @param name can be:
     *             - Type the row:
     *             - Type the column:
     *             - Type the Shelf's column where you want to insert the tile:
     * @param rightExtreme is the right extreme of the range in which user input must be contained
     * @return the correct string
     */
    private String generateNumberString(String name, int rightExtreme) {

        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.print(name);
            String val = scanner.nextLine();
            if (checkIfIsANumberStringOrQ(val)) {
                if (val.equalsIgnoreCase("Q") || (1 <= Integer.parseInt(val) && Integer.parseInt(val) <= rightExtreme)) {
                    return val;
                } else {
                    System.out.println(ANSI_RED + "The number must be between 1 and " + rightExtreme + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "The enter isn't a number or Q!" + ANSI_RESET);
            }
        }
    }


    /**
     * This private methods checks if the String is composed only by number characters.
     * @param val is the String to check
     * @return tue if the String is composed only by number characters
     */
    private boolean checkIfIsANumberStringOrQ(String val) {

        if (val.equals("")) {
            return false;
        } else if (val.equalsIgnoreCase("Q")){
            return true;
        } else {
            for (int i = 0; i < val.length(); i++) {
                if(!Character.isDigit(val.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
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
     * This method checks if the player wants to take another tile or not
     * @return the correct string that it could equal to Y or N
     */
    private String checkContinueString() {

        String outputString;
        while (true) {
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("Y") || input.equals("N")) {
                outputString = input;
                break;
            } else {
                System.out.println(ANSI_RED + "The entry is incorrect. Retry!" + ANSI_RESET);
                System.out.print("If you want take an other tile press Y otherwise press N: ");
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

        if (!checkIfIsANumberStringOrQ(val) || idAlreadyTaken.contains(val) || (Integer.parseInt(val) < 0 || Integer.parseInt(val) > size)) {

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


    /**
     * This method prints the specific common Goal description
     * @param commonGoal is the specific common Goal
     * @param id is the position of the specific common Goal in the commonGoals' array
     */
    private void showCommonGoalDescription (CommonGoal commonGoal, int id) {
        System.out.println();
        System.out.println("-+-".repeat(58));
        System.out.println(ANSI_CREAM + "Common Goal number:  " + ANSI_RESET + (id+1));
        System.out.println(ANSI_CREAM + "Description: " + ANSI_RESET + commonGoal.getDescription());
        System.out.println("-+-".repeat(58));
        System.out.println();
    }
}
