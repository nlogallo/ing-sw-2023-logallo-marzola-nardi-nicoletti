package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

/**
 * This is the class associated to the CommonGoal that checks whether there are n equal tiles on standard positions, like rows or columns
 */
public class CG_StdEqualTiles extends CommonGoal{

    /**
     * Constructor method, recalls the CommonGoal constructor method
     * @param id identifies the CommonGoal id
     * @param description is the description associated with the card
     */
    public CG_StdEqualTiles(int id, String description){
        super(id, description);
    }

    /**
     * It returns the common goal id
     * @return an int, that is the id
     */
    @Override
    public int getId() {
        return super.getId();
    }

    /**
     * It returns the common goal description
     * @return a String, that is the description
     */
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
            case 0 -> check2Tiles(tileTypes);
            case 2 -> check4Tiles(tileTypes);
            default -> throw new IllegalStateException("Unexpected id value" );
        };
    }

    /**
     * This method checks if there are 2 equal tiles types in the rows and in the columns. This has to be repeated for six times.
     * @param tileTypes is the player's shelf
     * @return true if the CommonGoal is achieved
     */
    private boolean check2Tiles(TileType[][] tileTypes){
        int countTimes = 0;
        for(int i = tileTypes.length - 1; i >= 0 ; i--){
            for(int j = 0; j < tileTypes[i].length; j++){
                if(j != tileTypes[i].length - 1 && tileTypes[i][j] != TileType.EMPTY && tileTypes[i][j] == tileTypes[i][j + 1]) {
                    countTimes++;
                    tileTypes[i][j] = TileType.EMPTY;
                    tileTypes[i][j + 1] = TileType.EMPTY;
                }
                if(i != 0 && tileTypes[i][j] != TileType.EMPTY && tileTypes[i][j] == tileTypes[i - 1][j]) {
                    countTimes++;
                    tileTypes[i][j] = TileType.EMPTY;
                    tileTypes[i - 1][j] = TileType.EMPTY;
                }
                if(countTimes >= 6)
                    return true;
            }
        }
        System.out.println(countTimes);
        return false;
    }

    /**
     * This method checks if there are 4 equal tiles types in the rows and in the columns. This has to be repeated for four times
     * @param tileTypes is the player's shelf
     * @return true if the CommonGoal is achieved
     */
    private boolean check4Tiles(TileType[][] tileTypes){
        int countTimes = 0, countEquals;
        for(int i = 0; i < tileTypes.length; i++){
            for(int j = 0; j < tileTypes[i].length; j++){
                TileType tileType = tileTypes[i][j];
                if(tileType != TileType.EMPTY) {
                    if (j < 2) {
                        countEquals = 1;
                        for (int d = 1; j + d < 5; d++)
                            if(tileType == tileTypes[i][j + d])
                                countEquals++;
                        if (countEquals > 3) {
                            countTimes++;
                            for(int k = 0; k < 4; k++)
                                tileTypes[i][j + k] = TileType.EMPTY;
                        }
                    }
                    if (i < 3) {
                        countEquals = 1;
                        for (int d = 1; i + d < 6; d++) {
                            if(tileType == tileTypes[i + d][j])
                                countEquals++;
                        }
                        if (countEquals > 3) {
                            countTimes++;
                            for(int k = 0; k < 4; k++)
                                tileTypes[i + k][j] = TileType.EMPTY;
                        }
                    }
                }
                if(countTimes > 3)
                    return true;
            }
        }
        return false;
    }
}
