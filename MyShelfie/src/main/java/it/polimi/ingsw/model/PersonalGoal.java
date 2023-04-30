package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represents the Personal Goal which are assigned to each player in the beginning of a Game
 */
public class PersonalGoal implements Serializable {
    private TileType[][] matrix;

    /**
     * Class constructor
     * @param matrix as the Personal Goal pattern matrix
     */
    public PersonalGoal(TileType[][] matrix){
        this.matrix = matrix;
    }

    /**
     * Check the personal goal and returns the points related
     * @param playerShelf as the Shelf of the Player
     * @return the points earned from the personal goal
     */
    public int checkPersonalGoal(TileType[][] playerShelf){
        int count = 0;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                if(!matrix[i][j].equals(TileType.EMPTY)){
                    if(matrix[i][j].equals(playerShelf[i][j]))
                        count++;
                }
            }
        }

        if (count == 1) {
            return 1;
        } else if (count == 2) {
            return 2;
        } else if (count == 3) {
            return 4;
        } else if (count == 4) {
            return 6;
        } else if (count == 5) {
            return 9;
        } else if (count == 6) {
            return 12;
        } else {
            return 0;
        }

    }

    /**
     * Return the Personal Goal Pattern to the Player
     * @return TileType[][]
     */
    public TileType[][] getMatrix(){
        return this.matrix;
    }
}
