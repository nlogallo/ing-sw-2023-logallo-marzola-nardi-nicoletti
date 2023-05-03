package it.polimi.ingsw.utils;

import it.polimi.ingsw.view.CLIView;

public class CLIFormatter {
    CLIView view;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[38;5;14m";
    public static final String ANSI_MAGENTA = "\u001B[38;5;13m";
    public static final String ANSI_BLUE = "\u001B[38;5;33m";
    public static final String ANSI_CREAM = "\u001B[38;5;229m";

    public CLIFormatter(CLIView view){
        this.view = view;
    }

    public void createCLIInterface(){
        //first line
        System.out.print("\n" + ANSI_WHITE + "Status: " + ANSI_GREEN + "\u001B[1mConnected" + ANSI_RESET + String.format("%-55s", " ") + "+---".repeat(2) + "+" + String.format("%-36s", " ") + "+---".repeat(5) + "+" + String.format("%-20s", " ")  + isSeatAvailable());
        //second line
        System.out.print(ANSI_WHITE + "\nCurrently playing: " + ANSI_RESET + view.getCurrentPlayer() + String.format("%-" + calculateSpacing(72, 19, view.getCurrentPlayer()) + "s", " ") + ANSI_RESET + "|" + getBoardTilesAtPosition(0,3)  + ANSI_RESET + "|" + getBoardTilesAtPosition(0, 4) + ANSI_RESET + "|" + String.format("%36s", " ") + ANSI_RESET + "|" +  getShelfTilesAtPosition(0,0) + ANSI_RESET + "|" + getShelfTilesAtPosition(0,1)  + ANSI_RESET + "|" + getShelfTilesAtPosition(0,2) + ANSI_RESET + "|" + getShelfTilesAtPosition(0,3) + ANSI_RESET + "|"+  getShelfTilesAtPosition(0,4) + ANSI_RESET + "|");
        //third line
        System.out.print(ANSI_WHITE + "\nTime left to play: " + ANSI_YELLOW + "1:25" + ANSI_RESET + String.format("%-49s", " ") + ANSI_RESET + "+---".repeat(3) + "+" + String.format("%-32s", " ") + "+---".repeat(5) + "+" + String.format("%20s", " ") + ANSI_WHITE + "Your tokens:");
        //fourth line
        System.out.print(ANSI_WHITE + "\nServer Message: " + String.format("%-56s", " ") + ANSI_RESET + "|" + getBoardTilesAtPosition(1,3) + ANSI_RESET + "|" + getBoardTilesAtPosition(1,4) + ANSI_RESET + "|" +  getBoardTilesAtPosition(1,5) + ANSI_RESET + "|" + String.format("%32s", " ") + ANSI_RESET + "|" +  getShelfTilesAtPosition(1,0) + ANSI_RESET + "|" + getShelfTilesAtPosition(1,1)  + ANSI_RESET + "|" +  getShelfTilesAtPosition(1,2) + ANSI_RESET + "|" + getShelfTilesAtPosition(1,3) + ANSI_RESET + "|"+  getShelfTilesAtPosition(1,4) + ANSI_RESET + "|");
        //fifth line
        System.out.print(ANSI_RESET + "\n" + view.getScreenMessage() + String.format("%-" + calculateSpacing(68, 0, view.getScreenMessage()) +"s", " ") + ANSI_RESET + "+---".repeat(5) + "+" + String.format("%-28s", " ") + "+---".repeat(5) + "+" + String.format("%-20s", " "));
        //sixth line
    }

    private int calculateSpacing(int emptyLength, int fixedTextLength, String dynamicTextToAdd){
        if(dynamicTextToAdd == null)
            return emptyLength - fixedTextLength - 4;
        else
            return emptyLength - fixedTextLength - dynamicTextToAdd.length();
    }
    private String isSeatAvailable(){
        if(view.isSeat())
            return ANSI_GREEN + "\u001B[1mSEAT";
        else
            return ANSI_RED + "\u001B[1mNO SEAT";
    }

    private String getBoardTilesAtPosition(int x, int y){
        return switch (view.getBoard().getTilesType()[x][y]) {
            case TROPHY ->  ANSI_CYAN + " \u001B[1mT ";
            case PLANT ->  ANSI_MAGENTA + " \u001B[1mP ";
            case FRAME ->  ANSI_BLUE + " \u001B[1mF ";
            case GAME ->  ANSI_YELLOW + " \u001B[1mG ";
            case BOOK ->  ANSI_CREAM + " \u001B[1mB ";
            case CAT ->  ANSI_GREEN + " \u001B[1mC ";
            case EMPTY -> ANSI_WHITE + " X ";
        };
    }

    private String getShelfTilesAtPosition(int x, int y){
        return switch (view.getShelf().getShelfTypes()[x][y]) {
            case TROPHY ->  ANSI_CYAN + " \u001B[1mT ";
            case PLANT ->  ANSI_MAGENTA + " \u001B[1mP ";
            case FRAME ->  ANSI_BLUE + " \u001B[1mF ";
            case GAME ->  ANSI_YELLOW + " \u001B[1mG ";
            case BOOK ->  ANSI_CREAM + " \u001B[1mB ";
            case CAT ->  ANSI_GREEN + " \u001B[1mC ";
            case EMPTY -> ANSI_WHITE + "   ";
        };
    }

    private String buildTokensPerLine(int line){
        String lineToPrint = "";
        if(view.getPersonalTokens().size() != 0){
            switch (line){
                case 0 -> {
                    return "+---+ ".repeat(view.getPersonalTokens().size());
                }
                case 1 -> {
                    for(int i = 0; i < view.getPersonalTokens().size(); i++){
                        lineToPrint = lineToPrint +  "| \u001B[1m" + view.getPersonalTokens().get(i).getPoints() + ANSI_RESET + " | ";
                    }
                }
            }
        }
        return lineToPrint;
    }

    /*private String canMakeHisMove(){
        //if(view.getCurrentPlayer().equals())
    }*/
}
