package it.polimi.ingsw.model.commonGoal;
import it.polimi.ingsw.model.TileType;

import java.util.ArrayList;

/**
 * This class is associated to all the CommonGoals that checks if rows or columns contain at maximum 3 different tile types
 */
public class CG_3DiffTypes extends CommonGoal {

    /**
     * Constructor method, recalls the CommonGoal constructor method
     * @param id identifies the CommonGoal id
     * @param description is the description associated with the card
     */
    public CG_3DiffTypes(int id, String description){
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
            case 3 -> checkLines(tileTypes);
            case 8 -> checkColumns(tileTypes);
            default -> throw new IllegalStateException("Unexpected id value");
        };
    }

    /**
     * This method checks if the shelf contains five tiles of maximum three different typer in four rows.
     * @param tileTypes is the player's shelf
     * @return true if this CommonGoal is achieved
     */
    private boolean checkLines(TileType[][] tileTypes){
        int countTimes = 0, countTilesPerRow;
        ArrayList<TileType> tileTypeArrayList = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            tileTypeArrayList.clear();
            countTilesPerRow = 0;
            for(int j = 0; j < 5; j++) {
                if (tileTypes[i][j] != TileType.EMPTY) {
                    countTilesPerRow++;
                    if (!tileTypeArrayList.contains(tileTypes[i][j]))
                        tileTypeArrayList.add(tileTypes[i][j]);
                    if (tileTypeArrayList.size() > 3)
                        break;
                }
            }
            if(tileTypeArrayList.size() < 4 && countTilesPerRow == 5)
                countTimes++;
            if(countTimes > 3)
                return true;
        }
        return false;
    }

    /**
     * This method checks if the shelf contains six tiles of maximum three different types in three columns.
     * @param tileTypes is the player's shelf
     * @return true if this CommonGoal is achieved
     */
    private boolean checkColumns(TileType[][] tileTypes){
        int countTimes = 0, countTilesPerColumn;
        ArrayList<TileType> tileTypeArrayList = new ArrayList<>();
        for(int j = 0; j < 5; j++){
            countTilesPerColumn = 0;
            tileTypeArrayList.clear();
            for(int i = 0; i < 6; i++) {
                if (tileTypes[i][j] != TileType.EMPTY) {
                    countTilesPerColumn++;
                    if (!tileTypeArrayList.contains(tileTypes[i][j]))
                        tileTypeArrayList.add(tileTypes[i][j]);
                    if (tileTypeArrayList.size() > 3)
                        break;
                }
            }
            if(tileTypeArrayList.size() < 4 && countTilesPerColumn == 6)
                countTimes++;
            if(countTimes > 2)
                return true;
        }
        return false;
    }
}
