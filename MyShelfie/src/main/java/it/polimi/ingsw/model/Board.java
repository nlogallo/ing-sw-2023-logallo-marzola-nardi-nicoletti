package it.polimi.ingsw.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * This class represents the game's board
 */
public class Board implements Serializable {
    /**
     * This attribute represents players number
     */
    private final int playersNumber;

    /**
     * This attribute represents the constraints Table
     */
    private static final int[][] constraintsTable = {   {0, 0, 0, 3, 4, 0, 0, 0, 0},
            {0, 0, 0, 2, 2, 4, 0, 0, 0},
            {0, 0, 3, 2, 2, 2, 3, 0, 0},
            {0, 4, 2, 2, 2, 2, 2, 2, 3},
            {4, 2, 2, 2, 2, 2, 2, 2, 4},
            {3, 2, 2, 2, 2, 2, 2, 4, 0},
            {0, 0, 3, 2, 2, 2, 3, 0, 0},
            {0, 0, 0, 4, 2, 2, 0, 0, 0},
            {0, 0, 0, 0, 4, 3, 0, 0, 0}};

    /**
     * This attribute represents the Tile board
     */
    private final Tile[][] tilesTable = new Tile[9][9];


    /**
     * This attribute stores the valid id for the tiles;
     */
    private final ArrayList<Integer> validIdList = new ArrayList<>();


    /**
     * Class Constructor
     */
    public Board (int playersNo) {
        playersNumber = playersNo;
        for(int i = 0; i<132; i++) {
            validIdList.add(i);
        }
        refillBoard();
    }


    /**
     * Getter
     */
    public ArrayList<Integer> getValidIdList () {
        return validIdList;
    }


    /**
     * Getter
     */
    public Tile[][] getTilesTable () {
        return tilesTable;
    }


    /**
     * Generates a new valid id
     *
     * @return the valid id
     */
    public int generateId () {
        Random rand = new Random();
        int idPos = rand.nextInt(validIdList.size());
        int id = validIdList.get(idPos);
        validIdList.remove(idPos);
        return id;
    }


    /**
     * Refill the board
     */
    public void refillBoard () {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tilesTable[i][j] == null) {
                    if ((constraintsTable[i][j] == 0) || (playersNumber < constraintsTable[i][j])) {
                        tilesTable[i][j] = null;
                    } else if (validIdList.size() > 0){
                        tilesTable[i][j] = new Tile(generateId());
                    }
                }
            }
        }
    }


    /**
     * Check if the board needs to be refilled
     *
     * @return true if the board needs to be refilled
     */
    public boolean checkRefill() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tilesTable[i][j] != null) {
                    if(i > 0 && tilesTable[i-1][j] != null) {
                        return false;
                    }
                    if(j > 0 && tilesTable[i][j-1] != null) {
                        return false;
                    }
                    if(i < 8 && tilesTable[i+1][j] != null) {
                        return false;
                    }
                    if(j < 8 && tilesTable[i][j+1] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * Pull the tiles from the board
     *
     * @param positions       has rows and columns of the tiles to pull
     *
     * @return the ArrayList of pulled tiles
     *
     * @exception IllegalArgumentException throws an IllegalArgumentException
     */
    public ArrayList<Tile> pullTiles(ArrayList<Position> positions) throws IllegalArgumentException, NullPointerException {
        ArrayList<Tile> pulledTiles = new ArrayList<>();

        for (Position position : positions) {
            int row = position.getRow();
            int column = position.getColumn();

            //check if the tiles are in the board
            if (tilesTable[row][column] == null) {
                throw new NullPointerException("There is not a tile in this position: " + row + "," + column);
            }

            //check if the tiles can be pulled
            if (!canPull(row, column)) {
                throw new IllegalArgumentException("One or more tile cannot be pulled");
            }
        }

        //pull tiles
        for (Position position : positions) {
            pulledTiles.add(tilesTable[position.getRow()][position.getColumn()]);
            tilesTable[position.getRow()][position.getColumn()] = null;
        }
        return pulledTiles;
    }


    /**
     * Check if a tile can be pulled form the board
     *
     * @param r         the number of the row to pull the tile from
     * @param c         the number of the column to pull the tile from
     *
     * @return true if the tile can be pulled
     */
    public boolean canPull (int r, int c)
    {
        boolean canBePulled = false;
        if (r == 0 || r == 8 || c == 0 || c == 8) {
            canBePulled = true;
        } else if (r > 0 && tilesTable[r - 1][c] == null) {
            canBePulled = true;
        } else if (r < 8 && tilesTable[r + 1][c] == null) {
            canBePulled = true;
        } else if (c > 0 && tilesTable[r][c - 1] == null) {
            canBePulled = true;
        } else if (c < 8 && tilesTable[r][c + 1] == null) {
            canBePulled = true;
        }
        return canBePulled;
    }


    /**
     * Getter method
     * @return the current Board
     */
    public Tile[][] getCurrentBoard () {
        return tilesTable;
    }

    /**
     * This method converts the Shelf in a matrix of TileType
     * @return the TileType matrix
     */
    public TileType[][] getTilesType(){
        TileType[][] matrixTypes = new TileType[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tilesTable[i][j] == null) {
                    matrixTypes[i][j] = TileType.EMPTY;
                } else {
                    matrixTypes[i][j] = tilesTable[i][j].getType();
                }
            }
        }
        return matrixTypes;
    }

    /**
     * This method checks if some tiles are aligned
     * @param positions ArrayList of positions to check if aligned
     * @return true if the tiles are aligned
     */
    public boolean areAligned (ArrayList<Position> positions) {
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> columns = new ArrayList<>();

        for (int i = 0; i < positions.size(); i++) {
            rows.add((positions.get(i)).getRow());
            columns.add((positions.get(i)).getColumn());
        }

        Collections.sort(rows);
        Collections.sort(columns);

        if (positions.size() == 1)
            return true;

        if (positions.size() == 2) {
            if (rows.get(0) == rows.get(1)) {
                if ((columns.get(1) - columns.get(0)) == 1)
                    return true;
            }
            else if (columns.get(0) == columns.get(1)) {
                if ((rows.get(1) - rows.get(0)) == 1)
                    return true;
            } else {return false;}

        }

        if (positions.size() == 3) {
            if ((rows.get(0) == rows.get(1)) && (rows.get(1) == rows.get(2))) {
                if (((columns.get(2) - columns.get(1)) == 1 ) && ((columns.get(1) - columns.get(0)) == 1)) {
                    return true;
                }
            }
            if ((columns.get(0) == columns.get(1)) && (columns.get(1) == columns.get(2))) {
                if (((rows.get(2) - rows.get(1)) == 1) && ((rows.get(1) - rows.get(0)) == 1)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

}
