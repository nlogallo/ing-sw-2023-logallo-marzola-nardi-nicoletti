package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Nunzio Logallo
 * This class represents the in game Player
 */
public class Player implements Serializable {
    private boolean seat;
    private Shelf shelf;
    private String nickname;
    private State state;
    private PersonalGoal personalGoal;
    private Game game;
    private ArrayList<Token> tokenCards = new ArrayList<>();
    private int points;

    /**
     * Class constructor
     * @param shelf as the personal Shelf of the Player
     * @param nickname as the in game name of the Player
     * @param state as the state in which the player is
     * @param game as the game in which the player is
     */
    public Player(boolean seat, Shelf shelf, String nickname, State state, Game game){
        this.seat = seat;
        this.shelf = shelf;
        this.nickname = nickname;
        this.state = new IsWaiting();
        this.personalGoal = null;
        this.game = game;
        this.points = 0;
    }

    /**
     * Checks if the Player has the first player seat
     * @return boolean
     */
    public boolean hasSeat(){
        return seat;
    }

    /**
     * Calculates player points
     */
    public void calculatePoints(){
        int sumPoints = 0;
        int tokenNo = tokenCards.size();
        for (int i = 0; i < tokenNo; i++) {
            sumPoints += (tokenCards.get(i)).getPoints();
        }
        if (this.personalGoal != null) {
            sumPoints += personalGoal.checkPersonalGoal(shelf.getShelfTypes());
        }
        sumPoints += adjacentTilesPoints(shelf.getShelfTypes());
        this.points = sumPoints;
    }

    /**
     * Calculates points of the adjacent tiles in the shelf
     * @param playerShelf the shelf of the player
     * @return the points earned from the adjacent tiles in the shelf
     */
    public int adjacentTilesPoints (TileType[][] playerShelf) {
        int sumPoints = 0;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                if(!playerShelf[i][j].equals(TileType.EMPTY)){
                    sumPoints += convertToPoints(calculateAdjacentTilesPoints(playerShelf, playerShelf[i][j], i, j));
                }
            }
        }
        return sumPoints;
    }

    /**
     * Recursive method that calculates the numbers of adjacent tiles in the shelf of a specific type and near to a specific tile
     * @param playerShelf the shelf of the player
     * @param type the type of tiles currently checking
     * @param row row of the tile that is being checked
     * @param column column of the tile that is being checked
     * @return the number of tiles adjacent of the same type
     */
    private int calculateAdjacentTilesPoints(TileType[][] playerShelf, TileType type, int row, int column)
    {
        if (type != playerShelf[row][column]) {
            return 0;
        } else {
            playerShelf[row][column] = TileType.EMPTY;
            if (row == 0  && column == 0) {
                return 1 + calculateAdjacentTilesPoints(playerShelf, type, row+1, column)
                         + calculateAdjacentTilesPoints(playerShelf, type, row, column+1);
            }
            else if (row == 0 && column == 4) {
                return 1 + calculateAdjacentTilesPoints(playerShelf, type, row+1, column)
                         + calculateAdjacentTilesPoints(playerShelf, type, row, column-1);
            }
            else if (row == 5 && column == 0) {
                return 1 + calculateAdjacentTilesPoints(playerShelf, type, row-1, column)
                         + calculateAdjacentTilesPoints(playerShelf, type, row, column+1);
            }
            else if (row == 5 && column == 4) {
                return 1 + calculateAdjacentTilesPoints(playerShelf, type, row-1, column)
                         + calculateAdjacentTilesPoints(playerShelf, type, row, column-1);
            }
            else if (row == 0) {
                return 1 + calculateAdjacentTilesPoints(playerShelf, type, row+1, column)
                         + calculateAdjacentTilesPoints(playerShelf, type, row, column-1)
                         + calculateAdjacentTilesPoints(playerShelf, type, row, column+1);
            }
            else if (row == 5) {
                return 1 + calculateAdjacentTilesPoints(playerShelf, type, row, column-1)
                        + calculateAdjacentTilesPoints(playerShelf, type, row-1, column)
                        + calculateAdjacentTilesPoints(playerShelf, type, row, column+1);
            }
            else if (column == 0) {
                return 1 + calculateAdjacentTilesPoints(playerShelf, type, row +1, column)
                        + calculateAdjacentTilesPoints(playerShelf, type, row-1, column)
                        + calculateAdjacentTilesPoints(playerShelf, type, row, column+1);
            }
            else if (column == 4) {
                return 1 + calculateAdjacentTilesPoints(playerShelf, type, row +1, column)
                        + calculateAdjacentTilesPoints(playerShelf, type, row-1, column)
                        + calculateAdjacentTilesPoints(playerShelf, type, row, column-1);
            } else {
                return 1 + calculateAdjacentTilesPoints(playerShelf, type, row +1, column)
                        + calculateAdjacentTilesPoints(playerShelf, type, row-1, column)
                        + calculateAdjacentTilesPoints(playerShelf, type, row, column-1)
                        + calculateAdjacentTilesPoints(playerShelf, type, row, column+1);
            }
        }
    }

    /**
     * Convert the number of adjacent tiles to points
     * @param n number of adjacent tiles
     * @return the points earned from the adjacent tiles
     */
    private int convertToPoints (int n){
        if (n == 3) {
            return 2;
        } else if (n == 4) {
            return 3;
        } else if (n == 5) {
            return 5;
        } else if (n > 5 ) {
            return 8;
        } else {
            return 0;
        }
    }

    /**
     * Check if the player shelf is full and calls the game to ask for the firstToEnd token
     */
    public void checkFirstToEnd () {
        if (shelf.isFull()) {
            game.giveFirstToEndToken(this);
        }
    }

    /**
     * Add a token to the tokens of the player
     * @param token the token to add to the player
     */
    public void giveToken(Token token) {
        tokenCards.add(token);
    }

    /**
     * Takes the tiles from the board and put them in the player shelf
     * @param column the column to put the tiles in
     * @param positions list of the positions of tiles to take from the board
     */
    public void makeMove(int column, ArrayList<Position> positions){
        shelf.insertTiles(column, game.requestForTiles(positions));
    }

    /**
     * Sets the player's personal goal
     * @param personalGoal as the personal goal assigned to the player
     */
    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }

    /**
     * Getter
     */
    public PersonalGoal getPersonalGoal(){
        return this.personalGoal;
    }

    /**
     * Getter
     */
    public int getPoints() {
        return this.points;
    }
}

