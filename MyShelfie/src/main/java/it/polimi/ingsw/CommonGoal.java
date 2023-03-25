package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Class that represents the Common Goal Card
 * @author Emanuele Nicoletti
 */
public class CommonGoal extends Card{
    private final int id;
    private final String description;

    /**
     * Class constructor
     * @param cardDescription is the text description of the card
     * @param id specifies the Common Goal
     */
    public CommonGoal(String cardDescription, int id) throws IllegalArgumentException, NullPointerException{
        if(id < 0 || id > 11) throw new IllegalArgumentException("Invalid id");
        if(cardDescription == null) throw new NullPointerException("Description cannot be null");
        this.id = id;
        description = cardDescription;
    }

    /**
     * attribute id getter method
     * @return this.id
     */
    public int getId(){
        return this.id;
    }

    /**
     * attribute description getter method
     * @return this.description
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * returns true if the common goal is achieved
     * @param playerShelf represents the player's shelf transformed in a TileType matrix
     * @return true if is achieved, false otherwise
     * @throws NullPointerException if the playerShelf is null
     * @throws IllegalArgumentException if the id does not exist
     */
    public boolean checkCommonGoal(TileType[][] playerShelf) throws NullPointerException, IllegalArgumentException {
        if (playerShelf == null)
            throw new NullPointerException("The shelf cannot be null");
        return switch (this.id) {
            case 0 -> checkCommonGoal0(playerShelf);
            case 1 -> checkCommonGoal1(playerShelf);
            case 2 -> checkCommonGoal2(playerShelf);
            case 3 -> checkCommonGoal3(playerShelf);
            case 4 -> checkCommonGoal4(playerShelf);
            case 5 -> checkCommonGoal5(playerShelf);
            case 6 -> checkCommonGoal6(playerShelf);
            case 7 -> checkCommonGoal7(playerShelf);
            case 8 -> checkCommonGoal8(playerShelf);
            case 9 -> checkCommonGoal9(playerShelf);
            case 10 -> checkCommonGoal10(playerShelf);
            case 11 -> checkCommonGoal11(playerShelf);
            default -> throw new IllegalArgumentException("This id does not exist");
        };
    }

    /**
     * returns true if the Common Goal with id = 0 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal0(TileType[][] tileTypes){
        int countTimes = 0;
        for(int i = 0; i < tileTypes.length; i++){
            for(int j = 0; j < tileTypes[i].length; j++){
                if(j != tileTypes[i].length - 1 && tileTypes[i][j] != TileType.EMPTY && tileTypes[i][j] == tileTypes[i][j + 1])
                    countTimes++;
                if(i != tileTypes.length - 1 && tileTypes[i][j] != TileType.EMPTY && tileTypes[i][j] == tileTypes[i + 1][j])
                    countTimes++;
                if(countTimes >= 6)
                    return true;
            }
        }
        return false;
    }

    /**
     * returns true if the Common Goal with id = 1 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal1(TileType[][] tileTypes){
        //still possible check if a diagonal could be in the shelf
        boolean stillPossible;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < tileTypes[i].length;){
                stillPossible = true;
                TileType tileType = tileTypes[i][j];
                if(tileType != TileType.EMPTY) {
                    if (j == 0) {
                        for (int d = 0; d < 5 && stillPossible; d++)
                            stillPossible = tileType == tileTypes[i + d][j + d];
                    } else {
                        for (int d = 0; d < 5 && stillPossible; d++) {
                            stillPossible = tileType == tileTypes[i + d][j - d];
                        }
                    }
                    if (stillPossible)
                        return true;
                }
               j += 4;
            }
        }
        return false;
    }

    /**
     * returns true if the Common Goal with id = 2 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal2(TileType[][] tileTypes){
        int countTimes = 0, countEquals;
        for(int i = 0; i < tileTypes.length; i++){
            for(int j = 0; j < tileTypes[i].length; j++){
                TileType tileType = tileTypes[i][j];
                if(tileType != TileType.EMPTY) {
                    if (j < 2) {
                        countEquals = 1;
                        for (int d = 1; j + d < 5; d++)
                            if(tileType == tileTypes[i][j + d])
                                countEquals++;
                        if (countEquals > 3)
                            countTimes++;
                    }
                    if (i < 3) {
                        countEquals = 1;
                        for (int d = 1; i + d < 6; d++) {
                            if(tileType == tileTypes[i + d][j])
                                countEquals++;
                        }
                        if (countEquals > 3)
                            countTimes++;
                    }
                }
                if(countTimes > 3)
                    return true;
            }
        }
        return false;
    }

    /**
     * returns true if the Common Goal with id = 3 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal3(TileType[][] tileTypes){
        int countTimes = 0, countTilesPerRow;
        ArrayList<TileType> tileTypeArrayList = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            tileTypeArrayList.clear();
            countTilesPerRow = 0;
            for(int j = 0; j < 5; j++) {
                if (tileTypes[i][j] != TileType.EMPTY) {
                    countTilesPerRow++;
                    if (!tileTypeArrayList.contains(tileTypes[i][j]))
                        tileTypeArrayList.add(tileTypes[i][j]);
                    if (tileTypeArrayList.size() > 3)
                        break;
                }
            }
            if(tileTypeArrayList.size() < 4 && countTilesPerRow == 5)
                countTimes++;
            if(countTimes > 3)
                return true;
        }
        return false;
    }

    /**
     * returns true if the Common Goal with id = 4 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal4(TileType[][] tileTypes){
        return ((tileTypes[0][0] != TileType.EMPTY) && (tileTypes[0][0] == tileTypes[0][4]) && (tileTypes[0][4] == tileTypes[5][0]) && (tileTypes[5][0] == tileTypes[5][4]));
    }

    /**
     * returns true if the Common Goal with id = 5 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal5(TileType[][] tileTypes){
        int countTimes = 0;
        ArrayList<TileType> tileTypeArrayList = new ArrayList<>();
        for(int j = 0; j < 5; j++){
            tileTypeArrayList.clear();
            for(int i = 0; i < 6; i++){
                if(tileTypes[i][j] != TileType.EMPTY && !tileTypeArrayList.contains(tileTypes[i][j]))
                    tileTypeArrayList.add(tileTypes[i][j]);
            }
            if(tileTypeArrayList.size() > 5)
                countTimes++;
            if(countTimes == 2)
                return true;
        }
        return false;
    }

    /**
     * returns true if the Common Goal with id = 6 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal6(TileType[][] tileTypes){
        int countTimes = 0;
        boolean stillPossible;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 4; j++){
                stillPossible = true;
                TileType tileType = tileTypes[i][j];
                if(tileType != TileType.EMPTY) {
                    for (int c = 0; c < 2 && stillPossible; c++) {
                        for (int r = 0; r < 2 && stillPossible; r++) {
                            if (tileType != tileTypes[i + r][j + c])
                                stillPossible = false;
                        }
                    }
                    if (stillPossible)
                        countTimes++;
                    if (countTimes > 1)
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * returns true if the Common Goal with id = 7 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal7(TileType[][] tileTypes){
        int countTimes = 0;
        ArrayList<TileType> tileTypeArrayList = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            tileTypeArrayList.clear();
            for(int j = 0; j < 5; j++){
                if(tileTypes[i][j] != TileType.EMPTY && !tileTypeArrayList.contains(tileTypes[i][j]))
                    tileTypeArrayList.add(tileTypes[i][j]);
            }
            if(tileTypeArrayList.size() > 4)
                countTimes++;
            if(countTimes == 2)
                return true;
        }
        return false;
    }

    /**
     * returns true if the Common Goal with id = 8 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal8(TileType[][] tileTypes){
        int countTimes = 0, countTilesPerColumn;
        ArrayList<TileType> tileTypeArrayList = new ArrayList<>();
        for(int j = 0; j < 5; j++){
            countTilesPerColumn = 0;
            tileTypeArrayList.clear();
            for(int i = 0; i < 6; i++) {
                if (tileTypes[i][j] != TileType.EMPTY) {
                    countTilesPerColumn++;
                    if (!tileTypeArrayList.contains(tileTypes[i][j]))
                        tileTypeArrayList.add(tileTypes[i][j]);
                    if (tileTypeArrayList.size() > 3)
                        break;
                }
            }
            if(tileTypeArrayList.size() < 4 && countTilesPerColumn == 6)
                countTimes++;
            if(countTimes > 2)
                return true;
        }
        return false;
    }

    /**
     * returns true if the Common Goal with id = 9 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal9(TileType[][] tileTypes){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                TileType tileType = tileTypes[i][j];
                if(tileType != TileType.EMPTY){
                    if((tileType == tileTypes[i][j + 2]) && (tileType == tileTypes[i + 1][j + 1]) && (tileType == tileTypes[i + 2][j] ) && (tileType == tileTypes[i + 2][j + 2]))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * returns true if the Common Goal with id = 10 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal10(TileType[][] tileTypes){
        int catsNumber = 0, booksNumber = 0, gamesNumber = 0, framesNumber = 0, trophiesNumber = 0, plantsNumber = 0;
        for (TileType[] tileType : tileTypes) {
            for (TileType type : tileType) {
                switch (type) {
                    case CAT:
                        catsNumber++;
                        break;
                    case BOOK:
                        booksNumber++;
                        break;
                    case GAME:
                        gamesNumber++;
                        break;
                    case FRAME:
                        framesNumber++;
                        break;
                    case TROPHY:
                        trophiesNumber++;
                        break;
                    case PLANT:
                        plantsNumber++;
                        break;
                    case EMPTY:
                        break;
                }
                if (catsNumber == 8 || booksNumber == 8 || gamesNumber == 8 || framesNumber == 8 || trophiesNumber == 8 || plantsNumber == 8)
                    return true;
            }
        }
        return false;
    }

    /**
     * returns true if the Common Goal with id = 11 is achieved
     * @param tileTypes is the player's shelf
     * @return true if is achieved, false otherwise
     */
    private boolean checkCommonGoal11(TileType[][] tileTypes){
        int countForColumn = 0;
        ArrayList<Integer> countList = new ArrayList<>();
        for(int j = 0; j < 5; j++){
            countForColumn = 0;
            for(int i = 0; i < 6; i++){
                if(tileTypes[i][j] != TileType.EMPTY)
                    countForColumn++;
            }
            countList.add(countForColumn);
            for(int d = 0; d < countList.size() - 1; d++){
                if((countList.get(d) != countList.get(d + 1) + 1) && (countList.get(d) != countList.get(d + 1) - 1))
                    return false;
            }
        }
        return true;
    }
}
