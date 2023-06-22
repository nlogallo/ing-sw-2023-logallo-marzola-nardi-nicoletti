package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Random;

/**
 * This class represents the Tile class of the game
 */

public class Tile implements Serializable {

    private int ID;
    private TileType type;
    private TileContainer container;
    private int row;
    private int column;
    private int imageType;


    /**
     * Class constructor
     * @param ID is the Tile ID of the same TileType
     */
    public Tile (int ID) {

        if (checkID(ID)) {
            this.ID = ID;
            setTileType(ID);
        }

        this.container = TileContainer.IN_BOARD;
        this.row = -1;
        this.column = -1;

        Random r = new Random();
        imageType = r.nextInt(3);
    }



    /**
     * This method sets the new value of row and column
     * @param row new value of Row
     * @param column new value of Column
     */
    public void setPosition(int row, int column) {

        if (checkParametersPosition (row, column)) {
            this.row = row;
            this.column = column;
        }
    }



    /**
     * @return a vector containing respectively the current row and column of the Tile
     */
    public int [] getPosition () {
        return new int[] {this.row, this.column};
    }


    /**
     *
     * @return current Tile ID
     */
    public int getID() { return this.ID; }



    /**
     *
     * @return current Tile type
     */
    public TileType getType () { return this.type; }



    /**
     * This method checks if the ID parameter is in the range between the rightExtreme and the leftExtreme
     * @param ID Tile ID
     * @param rightExtreme right extreme of the range
     * @param leftExtreme left extreme of the range
     * @return true if ID is between leftExtreme and the rightExtreme
     */
    private boolean isBetween (int ID, int leftExtreme, int rightExtreme) {
        return ID >= leftExtreme && ID <= rightExtreme;
    }



    /**
     * This method checks if the ID parameter is in the possible global range, otherwise throws the exception
     * @param ID Tile ID
     * @return true if ID is in the range
     * @throws IllegalArgumentException throws an IllegalArgumentException
     */
    private boolean checkID(int ID) throws IllegalArgumentException {

        if (isBetween(ID, 0, 131)) {
            return true;
        } else { throw new IllegalArgumentException("This ID isn't correct"); }
    }



    /**
     * This method sets the value of TileType based on the value of the ID
     * @param ID Tile ID
     */
    private void setTileType (int ID) {

        if (isBetween(ID, 0, 21)) {
            this.type = TileType.CAT;
        } else if (isBetween(ID, 22, 43)) {
            this.type = TileType.BOOK;
        } else if (isBetween(ID, 44,65)) {
            this.type = TileType.GAME;
        } else if (isBetween(ID, 66,87)) {
            this.type = TileType.FRAME;
        } else if (isBetween(ID, 88, 109)) {
            this.type = TileType.TROPHY;
        } else if (isBetween(ID, 110, 131)) {
            this.type = TileType.PLANT;
        }
    }



    /**
     * This method checks the new position parameters
     * @param row value of row
     * @param column value of column
     * @return true if the position parameters are correct otherwise false
     * @throws IllegalArgumentException if row and column are negative, or they are greater than 8 (exclusive)
     */
    private boolean checkParametersPosition(int row, int column) throws IllegalArgumentException {

        boolean areContained = false;
        if (row >= 0 && column >= 0) {
            if (row <= 8) {
                if (column <= 8) {
                    areContained = true;
                } else throw new IllegalArgumentException("Column must be lower than 9");
            } else throw new IllegalArgumentException("Row must be lower than 9");
        } else throw new IllegalArgumentException("Row and Column must be positive or zero");
        return areContained;
    }

    /**
     * Getter method
     */
    public int getImageType() {
        return imageType;
    }
}

