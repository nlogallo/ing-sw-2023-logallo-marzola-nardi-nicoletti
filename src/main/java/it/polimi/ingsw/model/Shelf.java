package it.polimi.ingsw.model;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class represents the player's shelf
 */
public class Shelf implements Serializable {

    /**
     * This attribute represents the shelf's rows
     */
    private final int rows = 6;

    /**
     * This attribute represents the shelf's columns
     */
    private final int columns = 5;

    /**
     * This matrix stores the player's tiles
     */
    private final Tile[][] matrix;


    /**
     * Class Constructor
     */
    public Shelf() {
        matrix = new Tile[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = null;
            }
        }
    }

    /**
     * Check how many free slots are in a column of the shelf
     * @param col the number of the column to check the free spaces
     * @return the number of free spaces in the specified column
     */
    public int freeRows(int col) {
        int freeR = 0;
        for (int i = rows - 1; i >= 0 ; i--) {
            if (matrix[i][col] == null) {
                freeR++;
            }
        }
        return freeR;
    }

    /**
     * Check the maximum number of free tiles between all the columns of the shelf
     * @return the maximum number of free tiles between all the columns of the shelf
     */
    public int freeSpots() {
        int freeS = 0;
        for (int i = 0; i < columns; i++) {
            if (freeRows(i) > freeS) {
                freeS = freeRows(i);
            }
        }
        return freeS;
    }

    /**
     * Inserts tiles in the shelf
     * @param col       the number of the column to insert the tiles in
     * @param t         the list of tiles to insert in the shelf
     * @exception IllegalArgumentException throws an IllegalArgumentException
     */
    public void insertTiles(int col, ArrayList<Tile> t) throws IllegalArgumentException {
        int tilesNo = t.size();
        if (freeRows(col) < tilesNo ) {
            throw new IllegalArgumentException("Not enough space available in this column");
        }

        int n = 0;
        for (int i = rows - 1; n < tilesNo; i--) {
            if (matrix[i][col] == null) {
                matrix[i][col] = t.get(n);
                n++;
            }
        }
    }

    /**
     * Check if the shelf is full
     * @return true if the shelf is full
     */
    public boolean isFull () {
        for (int i = 0; i < columns; i++) {
            if (matrix [0][i] == null)
                return false;
        }
        return true;
    }

    /**
     * Getter
     * @return the shelf
     */
    public Shelf getShelf() {
            return this;
    }

    /**
     * Getter
     * @param r     row of the tile of interest
     * @param c     column of the tile of interest
     * @return      the tile in the specified position
     */
    public Tile getTile(int r, int c) {
        return matrix[r][c];
    }

    /**
     * Getter
     * @return      the matrix of tileType from the tiles in the shelf
     */
    public TileType[][] getShelfTypes() {
        TileType[][] matrixTypes = new TileType[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == null) {
                    matrixTypes[i][j] = TileType.EMPTY;
                } else {
                    matrixTypes[i][j] = matrix[i][j].getType();
                }
            }
        }
        return matrixTypes;
    }

}
