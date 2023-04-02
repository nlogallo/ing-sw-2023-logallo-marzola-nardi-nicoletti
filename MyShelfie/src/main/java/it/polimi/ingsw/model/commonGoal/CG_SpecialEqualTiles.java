package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

/**
 * This is the class associated to the CommonGoal that checks whether there are equal tiles on special positions, like diagonal, square or cross
 */
public class CG_SpecialEqualTiles extends CommonGoal{

    /**
     * Constructor method, recalls the CommonGoal constructor method
     * @param id identifies the CommonGoal id
     * @param description is the description associated with the card
     */
    public CG_SpecialEqualTiles(int id, String description){
        super(id, description);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    /**
     * This method checks if these types of CommonGoal are achieved
     * @param tileTypes is the player shelf
     * @return true if the CommonGoal is achieved
     * @throws NullPointerException if the player's shelf is null
     * @throws IllegalArgumentException if the id is not correct
     */
    @Override
    public boolean checkCommonGoal(TileType[][] tileTypes) throws NullPointerException, IllegalStateException{
        if(tileTypes == null)
            throw new NullPointerException("The shelf cannot be null");
        return switch (this.getId()) {
            case 1 -> checkDiagonal(tileTypes);
            case 6 -> checkSquare(tileTypes);
            case 9 -> checkCross(tileTypes);
            default -> throw new IllegalStateException("Unexpected id value");
        };
    }

    /**
     * This method checks if the CommonGoal is achieved for one diagonal
     * @param tileTypes is the player's shelf
     * @return true if the CommonGoal is achieved
     */
    private boolean checkDiagonal(TileType[][] tileTypes){
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
     * This method checks if the CommonGoal is achieved for two squares
     * @param tileTypes is the player's shelf
     * @return true if the CommonGoal is achieved
     */
    private boolean checkSquare(TileType[][] tileTypes){
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
     * This method checks if the CommonGoal is achieved for one cross (X)
     * @param tileTypes is the player's shelf
     * @return true if the CommonGoal is achieved
     */
    public boolean checkCross(TileType[][] tileTypes){
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
}
