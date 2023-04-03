package it.polimi.ingsw.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * This class represents the game's board
 * @author Pier Matteo Marzola
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
    public Board getBoard() {
        return this;
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
    public ArrayList<Tile> pullTiles(ArrayList<Integer> positions) throws IllegalArgumentException {
        ArrayList<Tile> pulledTiles = new ArrayList<>();
        int tilesNo = positions.size();


        for (int i = 0; i < tilesNo; i += 2) {
            int r = positions.get(i);
            int c = positions.get(i + 1);

            //check if the tiles are in the board
            if (tilesTable[r][c] == null) {
                throw new NullPointerException("There is not a tile in this position");
            }

            //check if the tiles can be pulled
            if (!canPull(r, c)) {
                throw new IllegalArgumentException("One or more tile cannot be pulled");
            }
        }

        //pull tiles
        for (int i = 0; i < tilesNo; i += 2) {
            pulledTiles.add(tilesTable[positions.get(i)][positions.get(i + 1)]);
            tilesTable[positions.get(i)][positions.get(i + 1)] = null;
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
    private boolean canPull (int r, int c)
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
}
