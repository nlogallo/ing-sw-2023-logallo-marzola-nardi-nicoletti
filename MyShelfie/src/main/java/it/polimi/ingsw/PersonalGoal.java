package it.polimi.ingsw;

/**
 * @author Nunzio Logallo
 * This class represents the Personal Goal which are assigned to each player in the beginning of a Game
 */
public class PersonalGoal{
    private TileType[][] matrix;

    /**
     * Class constructor
     * @param matrix as the Personal Goal pattern matrix
     */
    public PersonalGoal(TileType[][] matrix){
        this.matrix = matrix;
    }

    /**
     * Returns true if the Personal Goal is verified in the Player Shelf
     * @param playerShelf as the Shelf of the Player
     * @return boolean
     */
    public boolean checkPersonalGoal(TileType[][] playerShelf){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                if(!matrix[i][j].equals(TileType.EMPTY)){
                    if(!matrix[i][j].equals(playerShelf[i][j]))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Return the Personal Goal Pattern to the Player
     * @return TileType[][]
     */

    public TileType[][] getMatrix(){
        return this.matrix;
    }
}
