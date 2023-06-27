package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.Shelf;

/**
 * Static class that allow to show the submenus of the CLI
 */
public class CLIMenus {
    public static final String ANSI_RESET = "\u001B[00m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[38;5;14m";
    public static final String ANSI_MAGENTA = "\u001B[38;5;13m";
    public static final String ANSI_BLUE = "\u001B[38;5;33m";
    public static final String ANSI_CREAM = "\u001B[38;5;229m";

    /**
     * It prints the shelves of other players
     * @param view is the view stored in the Client
     */
    public static void shelvesMenu(CLIView view){
        if(view.getPlayersShelf() != null) {
            System.out.println("\n" + "-+-".repeat(59));
            System.out.println(ANSI_CREAM + "These are the other player Shelves: " + ANSI_RESET);
            for (int i = 0; i < 14; i++)
                printShelvesPerRow(i, view);
            System.out.print("\n" + "-+-".repeat(59) + "\n");
        }
    }

    /**
     * It prints the final results of the game
     * @param results is the String preformatted by the Server
     */
    public static void endMenu(String results){
        System.out.print("\n" + ANSI_RED + "Game Ended!" + ANSI_RESET);
        System.out.print("\n" + results);
    }

    /**
     * It prints all the shelves rows by rows
     * @param row is the number of the row
     * @param view is the view stored in the Client
     */
    private static void printShelvesPerRow(int row, CLIView view){
        System.out.print("\n");
        switch (row){
            case 0:
                for(int i = 0; i < view.getPlayersNickname().size(); i++)
                    if(!view.getPlayersNickname().get(i).equals(view.getClientNickname()))
                        System.out.print(i + 1 + ") " + view.getPlayersNickname().get(i) + String.format("%-"+ calculateSpacing(38,  view.getPlayersNickname().get(i))+"s", " "));
                break;
            case 1, 3, 5, 7, 9, 11, 13:
                for(int i = 0; i < view.getPlayersNickname().size(); i++){
                    if(!view.getPlayersNickname().get(i).equals(view.getClientNickname())){
                        System.out.print(ANSI_RESET + "+---".repeat(5) + "+" + String.format("%-20s", " "));
                    }
                }
                break;
            case 2, 4, 6, 8, 10, 12:
                for(int i = 0; i < view.getPlayersNickname().size(); i++){
                    if(!view.getPlayersNickname().get(i).equals(view.getClientNickname())){
                        for(int j = 0; j < 5; j++)
                            System.out.print(ANSI_RESET + "|" + getShelfTilesAtPosition(view.getPlayersShelf().get(view.getPlayersNickname().get(i)), row/2 - 1, j));
                        System.out.print(ANSI_RESET + "|" + String.format("%-20s", " "));
                    }
                }
                break;
        }

    }

    /**
     * It prints the tile in the shelf at (x,y) position
     * @param shelf is the Shelf where to extract the info
     * @param x is the row
     * @param y is the columns
     * @return an ANSI String
     */
    private static String getShelfTilesAtPosition(Shelf shelf, int x, int y){
        return switch (shelf.getShelfTypes()[x][y]) {
            case TROPHY ->  ANSI_CYAN + " \u001B[1mT ";
            case PLANT ->  ANSI_MAGENTA + " \u001B[1mP ";
            case FRAME ->  ANSI_BLUE + " \u001B[1mF ";
            case GAME ->  ANSI_YELLOW + " \u001B[1mG ";
            case BOOK ->  ANSI_CREAM + " \u001B[1mB ";
            case CAT ->  ANSI_GREEN + " \u001B[1mC ";
            case EMPTY -> ANSI_WHITE + "   ";
        };
    }

    /**
     * It calculates the number of spacing to insert on the screen to align all the content
     * @param emptyLength is the number of chars without any string
     * @param dynamicTextToAdd is the text to add on the screen
     * @return an int that is the number of blank chars
     */
    private static int calculateSpacing(int emptyLength, String dynamicTextToAdd){
        return emptyLength - dynamicTextToAdd.length();
    }

}
