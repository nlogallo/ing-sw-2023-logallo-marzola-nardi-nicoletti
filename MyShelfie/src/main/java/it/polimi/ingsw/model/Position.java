package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represents the position of the tile
 */
public class Position implements Serializable {

    private int row;
    private int column;

    /**
     * Class Constructor
     * @param row of the tile
     * @param column of the tile
     */
    public Position (int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Getter
     *
     * @return      the row of the tile
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter
     *
     * @return      the column of the tile
     */
    public int getColumn() {
        return column;
    }

    /**
     * Setter
     * @param row identifies the row of the tile
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Setter
     * @param column identifies the column of the tile
     */
    public void setColumn(int column) {
        this.column = column;
    }

}
