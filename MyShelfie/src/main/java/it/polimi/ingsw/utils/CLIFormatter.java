package it.polimi.ingsw.utils;

import it.polimi.ingsw.view.CLIView;

public class CLIFormatter {
    CLIView view;
    public static final String ANSI_RESET = "\u001B[00m";
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
        System.out.print(ANSI_WHITE + "\nStatus: " + ANSI_GREEN + "\u001B[1mConnected" + ANSI_RESET + String.format("%-55s", " ") + "+---".repeat(2) + "+" + String.format("%-36s", " ") + "+---".repeat(5) + "+" + String.format("%-12s", " ")  + isSeatAvailable());

        //second line
        System.out.print(ANSI_WHITE + "\nCurrently playing: " + ANSI_RESET + view.getCurrentPlayer() + isYouPlaying() + String.format("%-" + calculateSpacing(72, 19, view.getCurrentPlayer() + isYouPlaying()) + "s", " ") + ANSI_RESET + "|" + getBoardTilesAtPosition(0,3)  + ANSI_RESET + "|" + getBoardTilesAtPosition(0, 4) + ANSI_RESET + "|" + String.format("%18s", " ")+ "1" + String.format("%17s", " ") + ANSI_RESET + "|" +  getShelfTilesAtPosition(0,0) + ANSI_RESET + "|" + getShelfTilesAtPosition(0,1)  + ANSI_RESET + "|" + getShelfTilesAtPosition(0,2) + ANSI_RESET + "|" + getShelfTilesAtPosition(0,3) + ANSI_RESET + "|"+  getShelfTilesAtPosition(0,4) + ANSI_RESET + "|");

        //third line
        System.out.print(ANSI_WHITE + "\nTime left to play: " + ANSI_YELLOW + "1:25" + ANSI_RESET + String.format("%-49s", " ") + ANSI_RESET + "+---".repeat(3) + "+" + String.format("%-32s", " ") + "+---".repeat(5) + "+" + String.format("%12s", " ") + ANSI_WHITE + "Your tokens:");

        //fourth line
        System.out.print(ANSI_WHITE + "\nServer Message: " + String.format("%-56s", " ") + ANSI_RESET + "|" + getBoardTilesAtPosition(1,3) + ANSI_RESET + "|" + getBoardTilesAtPosition(1,4) + ANSI_RESET + "|" +  getBoardTilesAtPosition(1,5) + ANSI_RESET + "|" + String.format("%14s", " ")+ "2" + String.format("%17s", " ")+ ANSI_RESET + "|" +  getShelfTilesAtPosition(1,0) + ANSI_RESET + "|" + getShelfTilesAtPosition(1,1)  + ANSI_RESET + "|" +  getShelfTilesAtPosition(1,2) + ANSI_RESET + "|" + getShelfTilesAtPosition(1,3) + ANSI_RESET + "|"+  getShelfTilesAtPosition(1,4) + ANSI_RESET + "|");

        //fifth line
        System.out.print(ANSI_RESET + "\n" + view.getScreenMessage() + String.format("%-" + calculateSpacing(68, 0, view.getScreenMessage()) + "s", " ") + ANSI_RESET + "+---".repeat(5) + "+" + String.format("%-28s", " ") + "+---".repeat(5) + "+" + String.format("%-12s", " ") + buildTokensPerLine(0));

        //sixth line
        System.out.print(ANSI_RESET + "\n" + String.format("%-68s", " ") + ANSI_RESET + "|" + getBoardTilesAtPosition(2,2) + ANSI_RESET + "|" + getBoardTilesAtPosition(2,3) + ANSI_RESET + "|" + getBoardTilesAtPosition(2,4) + ANSI_RESET + "|" + getBoardTilesAtPosition(2,5) + ANSI_RESET + "|" + getBoardTilesAtPosition(2,6) + ANSI_RESET + "|" + String.format("%-10s", " ")+ "3" + String.format("%-17s", " ")+ ANSI_RESET + "|" +  getShelfTilesAtPosition(2,0) + ANSI_RESET + "|" +  getShelfTilesAtPosition(2,1) + ANSI_RESET + "|" +  getShelfTilesAtPosition(2,2) + ANSI_RESET + "|" +  getShelfTilesAtPosition(2,3) + ANSI_RESET + "|" +  getShelfTilesAtPosition(2,4) + ANSI_RESET + "|"  + String.format("%-12s", " ") + buildTokensPerLine(1));

        //seventh line
        System.out.print(ANSI_RESET + "\n" + canMakeHisMove() + ANSI_RESET + String.format("%-" + calculateSpacing(69, 0, canMakeHisMove()) + "s", " ") + "+---".repeat(8) + "+" + String.format("%-20s", " ") + "+---".repeat(5) + "+"  + String.format("%-12s", " ") + buildTokensPerLine(2));

        //eighth line
        System.out.print(ANSI_RESET + "\n2.Open chats" + String.format("%-52s", " ") + ANSI_RESET + "|" + getBoardTilesAtPosition(3,1) + ANSI_RESET + "|" + getBoardTilesAtPosition(3,2) + ANSI_RESET + "|" + getBoardTilesAtPosition(3,3) + ANSI_RESET + "|" + getBoardTilesAtPosition(3,4) + ANSI_RESET + "|" + getBoardTilesAtPosition(3,5) + ANSI_RESET + "|" + getBoardTilesAtPosition(3,6) + ANSI_RESET + "|" + getBoardTilesAtPosition(3,7) + ANSI_RESET + "|" + getBoardTilesAtPosition(3,8) + ANSI_RESET + "|" + String.format("%-2s", " ")+ "4"  + String.format("%-17s", " ") + ANSI_RESET + "|" +  getShelfTilesAtPosition(3,0) + ANSI_RESET + "|" +  getShelfTilesAtPosition(3,1) + ANSI_RESET + "|" +  getShelfTilesAtPosition(3,2) + ANSI_RESET + "|" +  getShelfTilesAtPosition(3,3) + ANSI_RESET + "|" +  getShelfTilesAtPosition(3,4)+ ANSI_RESET + "|");

        //ninth line
        System.out.print(ANSI_RESET + "\n" + String.format("%-60s", " ") + ANSI_RESET + "+---".repeat(9) + "+" + String.format("%-20s", " ") + "+---".repeat(5) + "+");

        //tenth line
        System.out.print(ANSI_RESET + "\n" + String.format("%-60s", " ") + ANSI_RESET + "|" + getBoardTilesAtPosition(4,0) + ANSI_RESET + "|" + getBoardTilesAtPosition(4,1) + ANSI_RESET + "|" + getBoardTilesAtPosition(4,2) + ANSI_RESET + "|" + getBoardTilesAtPosition(4,3) + ANSI_RESET + "|" + getBoardTilesAtPosition(4,4) + ANSI_RESET + "|" + getBoardTilesAtPosition(4,5) + ANSI_RESET + "|" + getBoardTilesAtPosition(4,6) + ANSI_RESET + "|" + getBoardTilesAtPosition(4,7) + ANSI_RESET + "|" + getBoardTilesAtPosition(4,8 ) + ANSI_RESET + "|" + String.format("%-2s", " ")+ "5" + String.format("%-17s", " ")+ ANSI_RESET + "|" +  getShelfTilesAtPosition(4,0) + ANSI_RESET + "|" +  getShelfTilesAtPosition(4,1) + ANSI_RESET + "|" +  getShelfTilesAtPosition(4,2) + ANSI_RESET + "|" +  getShelfTilesAtPosition(4,3) + ANSI_RESET + "|" +  getShelfTilesAtPosition(4,4) + ANSI_RESET + "|");

        //eleventh
        System.out.print(ANSI_RESET + "\n+--------------------------------------+" + String.format("%-20s", " " ) + "+---".repeat(9) + "+" + String.format("%-20s", " ") + "+---".repeat(5) + "+");

        //twelfth line
        System.out.print(ANSI_RESET + "\n|" + ANSI_WHITE + " Last message:" + String.format("%-24s", " ") + ANSI_RESET + "|" + String.format("%-20s", " " ) + ANSI_RESET + "|" + getBoardTilesAtPosition(5,0) + ANSI_RESET + "|" + getBoardTilesAtPosition(5,1) + ANSI_RESET + "|" + getBoardTilesAtPosition(5,2) + ANSI_RESET + "|" + getBoardTilesAtPosition(5,3) + ANSI_RESET + "|" + getBoardTilesAtPosition(5,4) + ANSI_RESET + "|" + getBoardTilesAtPosition(5,5) + ANSI_RESET + "|" + getBoardTilesAtPosition(5,6) + ANSI_RESET + "|" + getBoardTilesAtPosition(5,7) + ANSI_RESET + "|" +  String.format("%-6s", " ")+ "6"  + String.format("%-17s", " ")  + ANSI_RESET + "|" +  getShelfTilesAtPosition(5,0) + ANSI_RESET + "|" +  getShelfTilesAtPosition(5,1) + ANSI_RESET + "|" +  getShelfTilesAtPosition(5,2) + ANSI_RESET + "|" +  getShelfTilesAtPosition(5,3) + ANSI_RESET + "|" +  getShelfTilesAtPosition(5,4) + ANSI_RESET + "|");

        //thirteenth line
        System.out.print(ANSI_RESET + "\n|" + "'HERE GOES LAST CHAT MESSAGE'" + String.format("%-9s", " ") + ANSI_RESET + "|" + String.format("%-20s", " " ) + "+---".repeat(8) + "+" + String.format("%-24s", " ") + "+---".repeat(5) + "+");

        //fourteenth line
        System.out.print(ANSI_RESET + "\n|" + ANSI_WHITE + "------by " + ANSI_RESET + "PLAYER'S NAME" + String.format("%-16s", " ") + ANSI_RESET + "|" + String.format("%-28s", " " ) + ANSI_RESET + "|" + getBoardTilesAtPosition(6,2) + ANSI_RESET + "|" + getBoardTilesAtPosition(6,3) + ANSI_RESET + "|" + getBoardTilesAtPosition(6,4) + ANSI_RESET + "|" + getBoardTilesAtPosition(6,5) + ANSI_RESET + "|" + getBoardTilesAtPosition(6,6) + ANSI_RESET + "|" + String.format("%-10s", " ")+ "7" + String.format("%-17s", " "));

        //fifteenth line
        System.out.print(ANSI_RESET + "\n|" + ANSI_WHITE + "------on " + ANSI_RESET + "WHICH CHAT" + String.format("%-19s", " ") + ANSI_RESET + "|" + String.format("%-28s", " " ) +  "+---".repeat(5) + "+" + String.format("%-28s", " ") + ANSI_WHITE + "Adjacent Item Tiles");

        //sixteenth line
        System.out.print(ANSI_RESET + "\n+--------------------------------------+" + String.format("%-32s", " " ) + ANSI_RESET + "|" + getBoardTilesAtPosition(7,3) + ANSI_RESET + "|" + getBoardTilesAtPosition(7,4) + ANSI_RESET + "|" + getBoardTilesAtPosition(7,5) + ANSI_RESET + "|" + String.format("%-14s", " ")+ "8" + String.format("%-17s", " ")+ "+------------------------------------+");

        //seventeenth line
        System.out.print(ANSI_RESET + "\n" + String.format("%-72s", " ") + "+---".repeat(3) + "+" + String.format("%-32s", " ") + "|    " + ANSI_WHITE + "  " + ANSI_RESET + "+---+             +---+       " + ANSI_RESET + "|");

        //eighteenth line
        System.out.print(ANSI_RESET + "\n+--------------------------------------+" + String.format("%-36s", " " ) + ANSI_RESET + "|" + getBoardTilesAtPosition(8,4) + ANSI_RESET + "|" + getBoardTilesAtPosition(8,5) + ANSI_RESET + "|" + String.format("%14s", " ")+ "9" + String.format("%17s", " ")+ ANSI_RESET +"| " + ANSI_WHITE +"  3x " + ANSI_RESET + "| = |" + ANSI_WHITE + " : 2      5x " + ANSI_RESET + "| = |" + ANSI_WHITE + " : 5 " + ANSI_RESET + "  |");

        //nineteenth line
        System.out.print(ANSI_RESET + "\n|" + ANSI_WHITE + " Players:" + String.format("%-29s", " " ) + ANSI_RESET + "|" + String.format("%-36s", " " ) + "+---".repeat(2) + "+" + String.format("%-32s", " ") + ANSI_RESET +"|      +---+             +---+       |");

        //twenty-first line
        System.out.print(ANSI_RESET + "\n|" + putPlayerName(1) + String.format("%-" + calculateSpacing(44, 1, putPlayerName(1)) + "s", " ") + "|" + String.format("%-77s", " " ) + "|                                    |");

        //twenty-second line
        System.out.print(ANSI_RESET + "\n|" + putPlayerName(2) + String.format("%-" + calculateSpacing(39, 1, putPlayerName(2)) + "s", " ") + "|" + String.format("%-20s", " " ) + "  1   2   3   4   5   6   7   8   9" + String.format("%-22s", " " ) + "|      +---+             +---+       |");

        //twenty-third line
        System.out.print(ANSI_RESET + "\n|" + putPlayerName(3) + String.format("%-" + calculateSpacing(39, 1, putPlayerName(3)) + "s", " ") + "|" + String.format("%-77s", " " ) +  "|   " + ANSI_WHITE + "4x " + ANSI_RESET + "| = | " + ANSI_WHITE + ": 3      6+ " + ANSI_RESET + "| = |" + ANSI_WHITE + " : 8 " + ANSI_RESET +"  |");

        //twenty-fourth line
        System.out.print(ANSI_RESET + "\n|" + putPlayerName(4) + String.format("%-" + calculateSpacing(39, 1, putPlayerName(4)) + "s", " ") + "|" + String.format("%-77s", " " ) + "|      +---+             +---+       |");

        //Twenty-fifth line
        System.out.print(ANSI_RESET + "\n+--------------------------------------+" + String.format("%-77s", " " ) + "+------------------------------------+");

        //Twenty-sixth
        System.out.print("\n\n");

        //Twenty-seventh
        System.out.print(ANSI_RESET + "\nYour personal Goal:  " + String.format("%-15s", " " ) + "1st Common Goal:     " + isFirstCommonGoalAchieved() + ANSI_RESET + String.format("%-15s", " " ) + "2nd Common Goal:     " + isSecondCommonGoalAchieved() + ANSI_RESET + String.format("%-15s", " " ) + "End Game Token:     " + isEndGameTokenAchieved());

        //Twenty-eighth line
        System.out.print("\n");

        //Twenty-ninth line
        System.out.print(ANSI_RESET + "\n" + "+---".repeat(5) + "+" + String.format("%-15s", " " ) + buildCommonGoalPerLine(0, view.getCommonGoals().get(0).getId()) + ANSI_RESET + buildTokenAvailable(1, 0) + String.format("%-15s", " " ) + buildCommonGoalPerLine(0, view.getCommonGoals().get(1).getId()) + ANSI_RESET  + buildTokenAvailable(2, 0) + String.format("%-15s", " " ) + ANSI_RESET  + buildTokenAvailable(0, 0));

        //thirtieth line
        System.out.print(ANSI_RESET + "\n" + "|" +  getPersonalGoalAtPosition(0,0) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(0,1) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(0,2) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(0,3) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(0,4) + ANSI_RESET + "|" + String.format("%-15s", " " ) + buildCommonGoalPerLine(1, view.getCommonGoals().get(0).getId()) + ANSI_RESET + buildTokenAvailable(1, 1) + String.format("%-15s", " " ) + buildCommonGoalPerLine(1, view.getCommonGoals().get(1).getId()) + ANSI_RESET + buildTokenAvailable(2, 1) + String.format("%-15s", " " ) + ANSI_RESET  + buildTokenAvailable(0, 1));

        //thirty-first line
        System.out.print(ANSI_RESET + "\n" + "+---".repeat(5) + "+" + String.format("%-15s", " " ) + buildCommonGoalPerLine(2, view.getCommonGoals().get(0).getId()) + ANSI_RESET + buildTokenAvailable(1, 2) + String.format("%-15s", " " ) + buildCommonGoalPerLine(2, view.getCommonGoals().get(1).getId()) + ANSI_RESET + buildTokenAvailable(2, 2) + String.format("%-15s", " " ) + ANSI_RESET  + buildTokenAvailable(0, 2));

        //thirty-second line
        System.out.print(ANSI_RESET + "\n" + "|" +  getPersonalGoalAtPosition(1,0) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(1,1) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(1,2) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(1,3) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(1,4) + ANSI_RESET + "|" + String.format("%-15s", " " ) + buildCommonGoalPerLine(3, view.getCommonGoals().get(0).getId()) + ANSI_RESET + String.format("%-29s", " " ) + buildCommonGoalPerLine(3, view.getCommonGoals().get(1).getId()));

        //thirty-third line
        System.out.print(ANSI_RESET + "\n" + "+---".repeat(5) + "+" + String.format("%-15s", " " ) + buildCommonGoalPerLine(4, view.getCommonGoals().get(0).getId()) + String.format("%-29s", " " ) + ANSI_RESET + buildCommonGoalPerLine(4, view.getCommonGoals().get(1).getId()));

        //thirty-fourth
        System.out.print(ANSI_RESET + "\n" + "|" +  getPersonalGoalAtPosition(2,0) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(2,1) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(2,2) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(2,3) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(2,4) + ANSI_RESET + "|" + String.format("%-15s", " " ) + buildCommonGoalPerLine(5, view.getCommonGoals().get(0).getId()) + ANSI_RESET + String.format("%-29s", " " ) + buildCommonGoalPerLine(5, view.getCommonGoals().get(1).getId()));

        //thirty-fifth line
        System.out.print(ANSI_RESET + "\n" + "+---".repeat(5) + "+" + String.format("%-15s", " " ) + buildCommonGoalPerLine(6, view.getCommonGoals().get(0).getId()) + String.format("%-29s", " " ) + ANSI_RESET + buildCommonGoalPerLine(6, view.getCommonGoals().get(1).getId()));

        //thirty-sixth line
        System.out.print(ANSI_RESET + "\n" + "|" +  getPersonalGoalAtPosition(3,0) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(3,1) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(3,2) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(3,3) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(3,4) + ANSI_RESET+ "|" + String.format("%-15s", " " ) + buildCommonGoalPerLine(7, view.getCommonGoals().get(0).getId()) +  ANSI_RESET + String.format("%-29s", " " ) + buildCommonGoalPerLine(7, view.getCommonGoals().get(1).getId()));

        //thirty-seventh line
        System.out.print(ANSI_RESET + "\n" + "+---".repeat(5) + "+" + String.format("%-15s", " " ) + buildCommonGoalPerLine(8, view.getCommonGoals().get(0).getId()) + String.format("%-29s", " " ) + ANSI_RESET + buildCommonGoalPerLine(8, view.getCommonGoals().get(1).getId()));

        //thirty-eighth line
        System.out.print(ANSI_RESET + "\n" + "|" +  getPersonalGoalAtPosition(4,0) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(4,1) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(4,2) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(4,3) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(4,4) + ANSI_RESET+ "|" + String.format("%-15s", " " ) + buildCommonGoalPerLine(9, view.getCommonGoals().get(0).getId()) +  ANSI_RESET + String.format("%-29s", " " ) + buildCommonGoalPerLine(9, view.getCommonGoals().get(1).getId()));

        //thirty-ninth line
        System.out.print(ANSI_RESET + "\n" + "+---".repeat(5) + "+" + String.format("%-15s", " " ) + buildCommonGoalPerLine(10, view.getCommonGoals().get(0).getId()) + String.format("%-29s", " " ) + ANSI_RESET + buildCommonGoalPerLine(10, view.getCommonGoals().get(1).getId()));

        //fortieth  line
        System.out.print(ANSI_RESET + "\n" + "|" +  getPersonalGoalAtPosition(5,0) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(5,1) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(5,2) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(5,3) + ANSI_RESET + "|" +  getPersonalGoalAtPosition(5,4) + ANSI_RESET+ "|" + String.format("%-15s", " " ) + buildCommonGoalPerLine(11, view.getCommonGoals().get(0).getId()) + ANSI_RESET + String.format("%-29s", " " ) + buildCommonGoalPerLine(11, view.getCommonGoals().get(1).getId()));

        //forty-first line
        System.out.print(ANSI_RESET + "\n" + "+---".repeat(5) + "+" + String.format("%-15s", " " ) + buildCommonGoalPerLine(12, view.getCommonGoals().get(0).getId()) + String.format("%-29s", " " ) + ANSI_RESET + buildCommonGoalPerLine(12, view.getCommonGoals().get(1).getId()));

        //forty-second line
        System.out.print("\n");

        //forty-third line
        System.out.print("\n" + String.format("%-36s", " " ) + "3.Read the description" + String.format("%-28s", " " ) + "4.Read the description" + "\n");
        
        System.out.print("\n\n" + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("\n");

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

    private String isYouPlaying () {
        if (!(view.getCurrentPlayer() == null || !view.getCurrentPlayer().equals(view.getClientNickname())))
            return " (you)";
        else return " ";
    }

    private String isFirstCommonGoalAchieved(){
        if (view.getPersonalTokens().size() == 0)
            return ANSI_RED + "\u001B[1m  NOT ACHIEVED";

        for (int i = 0; i< view.getPersonalTokens().size(); i++) {
            if ((view.getPersonalTokens().get(i).getId() != 0) && ((view.getPersonalTokens().get(i).getId() % 2) == 0))
                return ANSI_GREEN + "\u001B[1m  ACHIEVED    ";
        }
        return ANSI_RED + "\u001B[1m  NOT ACHIEVED";
    }

    private String isSecondCommonGoalAchieved(){
        if (view.getPersonalTokens().size() == 0)
            return ANSI_RED + "\u001B[1m  NOT ACHIEVED";

        for (int i = 0; i< view.getPersonalTokens().size(); i++) {
            if ((view.getPersonalTokens().get(i).getId() % 2) == 1)
                return ANSI_GREEN + "\u001B[1m  ACHIEVED    ";
        }
        return ANSI_RED + "\u001B[1m  NOT ACHIEVED";
    }

    private String isEndGameTokenAchieved(){
        if (view.getPersonalTokens().size() == 0)
        return ANSI_RED + "\u001B[1m  NOT ACHIEVED";

        for (int i = 0; i< view.getPersonalTokens().size(); i++) {
            if (view.getPersonalTokens().get(i).getId() == 0)
                return ANSI_GREEN + "\u001B[1m  ACHIEVED    ";
        }
        return ANSI_RED + "\u001B[1m  NOT ACHIEVED";
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

    private String getPersonalGoalAtPosition(int x, int y) {
        return switch (view.getPersonalGoal().getMatrix()[x][y]) {
            case TROPHY ->  ANSI_CYAN + " \u001B[1mT ";
            case PLANT ->  ANSI_MAGENTA + " \u001B[1mP ";
            case FRAME ->  ANSI_BLUE + " \u001B[1mF ";
            case GAME ->  ANSI_YELLOW + " \u001B[1mG ";
            case BOOK ->  ANSI_CREAM + " \u001B[1mB ";
            case CAT ->  ANSI_GREEN + " \u001B[1mC ";
            case EMPTY -> ANSI_WHITE + "   ";
        };
    }

    /* id = 0 -> end token check
       id = 1 -> second common goal token check
       id = 2 -> first common goal token check
     */
    private String buildTokenAvailable (int id, int line) {
        switch (id) {
            case 0 -> {
                boolean present = false;

                for (int i = 0; i < view.getGameTokens().size(); i++) {
                    if (view.getGameTokens().get(i).getId() == 0)
                        present = true;
                }
                switch (line) {
                    case 0, 2 -> {
                        if (present)
                            return "    +---+      ";
                        else return String.format("%-14s", " ");
                    }
                    case 1 -> {
                        if (present)
                            return "    | 1 |      ";
                        else return String.format("%-14s", " ");
                    }
                }
            }

            case 2 -> {
                int maxToken = 0;

                for (int i = 0; i < view.getGameTokens().size(); i++) {
                    if ((view.getGameTokens().get(i).getId() % 2) == 1)
                    {
                        if (view.getGameTokens().get(i).getPoints() > maxToken)
                            maxToken = view.getGameTokens().get(i).getPoints();
                    }
                }

                switch (line) {
                    case 0, 2 -> {
                        if (maxToken != 0)
                            return "     +---+    ";
                        else return String.format("%-14s", " ");
                    }
                    case 1 -> {
                        if (maxToken != 0)
                            return "     | " + maxToken + " |    ";
                        else return String.format("%-14s", " ");
                    }
                }

            }

            case 1 -> {
                int maxToken = 0;

                for (int i = 0; i < view.getGameTokens().size(); i++) {
                    if ((view.getGameTokens().get(i).getId() != 0) && ((view.getGameTokens().get(i).getId() % 2) == 0))
                    {
                        if (view.getGameTokens().get(i).getPoints() > maxToken)
                            maxToken = view.getGameTokens().get(i).getPoints();
                    }
                }

                switch (line) {
                    case 0, 2 -> {
                        if (maxToken != 0)
                            return "     +---+    ";
                        else return String.format("%-14s", " ");
                    }
                    case 1 -> {
                        if (maxToken != 0)
                            return "     | " + maxToken + " |    ";
                        else return String.format("%-14s", " ");
                    }
                }

            }
        }
        return "ERRORE" + String.format("%-8s", " " );
    }

    private String buildTokensPerLine(int line){
        String lineToPrint = "";
        if(view.getPersonalTokens().size() != 0){
            switch (line){
                case 0, 2 -> {
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

    private String canMakeHisMove(){
        if(view.getCurrentPlayer() == null || !view.getCurrentPlayer().equals(view.getClientNickname())){
            return ANSI_WHITE + "1.Make your move (disabled)";
        }
        else
            return ANSI_RESET + "1.Make your move";
    }

    private String putPlayerName(int line) {
        if (line == 1) {
            return " - " + ANSI_RESET + view.getClientNickname() + " (you)";
        } else if (view.getPlayersNickname().size() >= line - 1) {
            return " - " + view.getPlayersNickname().get(line - 2);
        } else return " - ";
    }

    private String buildCommonGoalPerLine (int line, int id) {
        switch (id ){
            case 0 -> {
                switch (line) {
                    case 0, 4 -> {
                        return "+---+" + String.format("%-16s", " " );
                    }
                    case 1, 3 -> {
                        return "| = |" + String.format("%-16s", " " );
                    }

                    case 2 -> {
                        return "+---+  x6" + String.format("%-12s", " " );
                    }
                    case 5, 6, 7, 8, 9, 10, 11, 12 -> {
                        return String.format("%-21s", " " );
                    }
                }

            }

            case 1  -> {
                switch (line) {
                    case 0 -> {
                        return "+---+" + String.format("%-16s", " " );
                    }
                    case 1 -> {
                        return "| = |" + String.format("%-16s", " " );
                    }
                    case 2 -> {
                        return "+---+---+" + String.format("%-12s", " " );
                    }
                    case 3 -> {
                        return "    | = |" + String.format("%-12s", " " );
                    }
                    case 4 -> {
                        return "    +---+---+" + String.format("%-8s", " " );
                    }
                    case 5 -> {
                        return "        | = |" + String.format("%-8s", " " );
                    }
                    case 6 -> {
                        return "        +---+---+" + String.format("%-4s", " " );
                    }
                    case 7 -> {
                        return "            | = |" + String.format("%-4s", " " );
                    }
                    case 8 -> {
                        return "            +---+---+";
                    }
                    case 9 -> {
                        return "                | = |";
                    }
                    case 10 -> {
                        return "                +---+";
                    }
                    case 11, 12 -> {
                        return String.format("%-21s", " " );
                    }
                }
            }

            case 2 -> {
                switch (line) {
                    case 0, 2, 6, 8 -> {
                        return "+---+" + String.format("%-16s", " " );
                    }
                    case 1, 3, 5, 7 -> {
                        return "| = |" + String.format("%-16s", " " );
                    }
                    case 4 -> {
                        return "+---+  x4" + String.format("%-12s", " " );
                    }
                    case 9, 10, 11, 12 -> {
                        return String.format("%-21s", " " );
                    }
                }
            }

            case 3 -> {
                switch (line) {
                    case 0, 2 -> {
                        return "+---+---+---+---+---+";
                    }
                    case 1 -> {
                        return "|   |   |   |   |   |";
                    }
                    case 3 -> {
                        return ANSI_RED + "      max 3 !=       ";
                    }
                    case 5 -> {
                        return "          x4         ";
                    }
                    case 4, 6, 7, 8, 9, 10, 11, 12 -> {
                        return String.format("%-21s", " " );
                    }
                }
            }

            case 4 -> {
                switch (line) {
                    case 0, 12 -> {
                        return "+---+  --   --  +---+";
                    }
                    case 1, 11 -> {
                        return "| = |           | = |";
                    }
                    case 2, 10 -> {
                        return "+---+           +---+";
                    }
                    case 3, 5, 7, 9 -> {
                        return String.format("%-21s", " " );
                    }
                    case 4, 6, 8 -> {
                        return "|                   |";
                    }
                }
            }

            case 5 -> {
                switch (line) {
                    case 0, 2, 4, 8, 10, 12 -> {
                        return "+---+" + String.format("%-16s", " " );
                    }
                    case 1, 3, 5, 7, 9, 11 -> {
                        return "|!= |" + String.format("%-16s", " " );
                    }
                    case 6 -> {
                        return "+---+  x2" + String.format("%-12s", " " );
                    }
                }
            }

            case 6 -> {
                switch (line) {
                    case 0, 4 -> {
                        return "+---+---+" + String.format("%-12s", " " );
                    }
                    case 1, 3 -> {
                        return "| = | = |" + String.format("%-12s", " " );
                    }
                    case 2 -> {
                        return "+---+---+  x2" + String.format("%-8s", " " );
                    }
                    case 5, 6, 7, 8, 9, 10, 11, 12 -> {
                        return String.format("%-21s", " " );
                    }
                }
            }

            case 7 -> {
                switch (line) {
                    case 0, 2 -> {
                        return "+---+---+---+---+---+";
                    }
                    case 1 -> {
                        return "|!= |!= |!= |!= |!= |";
                    }
                    case 3, 5, 6, 7, 8, 9, 10, 11, 12 -> {
                        return String.format("%-21s", " " );
                    }
                    case 4 -> {
                        return "          x2         ";
                    }
                }
            }

            case 8 -> {
                switch (line) {
                    case 0, 2, 4, 6, 8, 10, 12 -> {
                        return "+---+" + String.format("%-16s", " ");
                    }
                    case 1, 3, 9, 11 -> {
                        return "|   |" + String.format("%-16s", " ");
                    }
                    case 7 -> {
                        return "|   |" + ANSI_RED + " max 3 !=" + String.format("%-7s", " ");
                    }
                    case 5 -> {
                        return "|   |  x3" + String.format("%-12s", " " );
                    }
                }
            }

            case 9 -> {
                switch (line) {
                    case 0, 6 -> {
                        return "+---+   +---+" + String.format("%-8s", " " );
                    }
                    case 1, 5 -> {
                        return "| = |   | = |" + String.format("%-8s", " " );
                    }
                    case 2, 4 -> {
                        return "+---+---+---+" + String.format("%-8s", " " );
                    }
                    case 3 -> {
                        return "    | = |    " + String.format("%-8s", " " );
                    }
                    case 7, 8, 9, 10, 11, 12 -> {
                        return String.format("%-21s", " " );
                    }

                }
            }

            case 10 -> {
                switch (line) {
                    case 0, 2 -> {
                        return "+---+" + String.format("%-16s", " " );
                    }
                    case 1 -> {
                        return "| = |  x8" + String.format("%-12s", " " );
                    }
                    case 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 -> {
                        return String.format("%-21s", " " );
                    }
                }
            }

            case 11 -> {
                switch (line) {
                    case 0 -> {
                        return "+---+" + String.format("%-16s", " " );
                    }
                    case 1 -> {
                        return "|   |" + String.format("%-16s", " " );
                    }
                    case 2 -> {
                        return "+---+---+" + String.format("%-12s", " " );
                    }
                    case 3 -> {
                        return "|   |   |" + String.format("%-12s", " " );
                    }
                    case 4 -> {
                        return "+---+---+---+" + String.format("%-8s", " " );
                    }
                    case 5 -> {
                        return "|   |   |   |" + String.format("%-8s", " " );
                    }
                    case 6 -> {
                        return "+---+---+---+---+" + String.format("%-4s", " " );
                    }
                    case 7 -> {
                        return "|   |   |   |   |" + String.format("%-4s", " " );
                    }
                    case 8, 10 -> {
                        return "+---+---+---+---+---+";
                    }
                    case 9 -> {
                        return "|   |   |   |   |   |";
                    }
                    case 11, 12 -> {
                        return String.format("%-21s", " " );
                    }
                }
            }

        }
        return "ERRORE" + String.format("%-15s", " " );
    }
}
