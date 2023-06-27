package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class extends Card Class and represents the Token of the game.
 */
public class Token implements Serializable {
    private final int id;
    private int points;
    private TokenType type;


    /**
     * Class constructor
     * @param ID is the ID of the Token
     * @throws IllegalArgumentException if points is different from 2,4,6,8, or if type isn't between
     * the types in the enum
     */
    public Token (int ID) throws IllegalArgumentException {
        if (checkID(ID)) {
            associateIDtoTokenPoints(ID);
            this.id = ID;
        } else throw new IllegalArgumentException("ID isn't correct");

    }



    /**
     * Getter method
     * @return current points of the Token instance
     */
    public int getPoints() {
        return points;
    }



    /**
     * Getter method
     * @return current type of the Token instance
     */
    public TokenType getType() {
        return type;
    }



    /**
     * This method sets the new value of Token points
     * @param ID is the new ID of Token
     * @throws IllegalArgumentException if the TokenID is over the maximum range
     */
    public void setPoints (int ID) throws IllegalArgumentException {
        if (checkID(ID)) {
            associateIDtoTokenPoints(ID);
        } else throw new IllegalArgumentException("ID isn't correct (over the maximum range)");
    }



    /**
     * This method checks the TokenID
     * @param ID is the ID of the Token
     * @return true if TokenID is between 0 and 9 (inclusive)
     */
    private boolean checkID (int ID) {
        return ID >= 0 && ID <= 9;
    }



    /**
     * This method associates the TokenID to the respective points and TokenType
     * ID = 0 -> Points = 1 and TokenType = END_GAME
     * ID = 1 -> Points = 2 and TokenType = SCORING
     * ID = 2 -> Points = 2 and TokenType = SCORING
     * ID = 3 -> Points = 4 and TokenType = SCORING
     * ID = 4 -> Points = 4 and TokenType = SCORING
     * ID = 5 -> Points = 6 and TokenType = SCORING
     * ID = 6 -> Points = 6 and TokenType = SCORING
     * ID = 7 -> Points = 8 and TokenType = SCORING
     * ID = 8 -> Points = 8 and TokenType = SCORING
     * @param ID is the ID of the Token
     */
    private void associateIDtoTokenPoints (int ID) {
        if (isBetween(ID, 0, 0)) {
            this.points = 1;
            this.type = TokenType.END_GAME;
        } else {

            this.type = TokenType.SCORING;

            if (isBetween(ID, 1,2)) {
                this.points = 2;
            } else if (isBetween(ID, 3,4)) {
                this.points = 4;
            } else if (isBetween(ID, 5, 6)) {
                this.points = 6;
            } else if (isBetween(ID, 7, 8)) {
                this.points = 8;
            }
        }
    }



    /**
     * This method checks the ID
     * @param ID is the ID of the Token
     * @param leftExtreme is the leftExtreme of the range
     * @param rightExtreme is the rightExtreme of the range
     * @return true if the ID value is between the leftExtreme and the rightExtreme
     */
    private boolean isBetween(int ID, int leftExtreme, int rightExtreme) {
        return ID >= leftExtreme && ID <= rightExtreme;
    }


    /**
     * getter of attribute id
     * @return int (the token id)
     */
    public int getId(){
        return this.id;
    }

}
