package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

import java.util.ArrayList;

/**
 * This is the class associated to the CommonGoal that checks whether there are columns or rows of different tile types
 */
public class CG_DiffTiles extends CommonGoal{

    /**
     * Constructor method, recalls the CommonGoal constructor method
     * @param id identifies the CommonGoal id
     * @param description is the description associated with the card
     */
    public CG_DiffTiles(int id, String description){
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
            case 5 -> checkColumns(tileTypes);
            case 7 -> checkLines(tileTypes);
            default -> throw new IllegalStateException("Unexpected id value");
        };
    }

    /**
     * This method checks if the CommonGoal is achieved in the rows
     * @param tileTypes is the player's shelf
     * @return true if the CommonGoal is achieved
     */
    private boolean checkLines(TileType[][] tileTypes){
        int countTimes = 0;
        ArrayList<TileType> tileTypeArrayList = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            tileTypeArrayList.clear();
            for(int j = 0; j < 5; j++){
                if(tileTypes[i][j] != TileType.EMPTY && !tileTypeArrayList.contains(tileTypes[i][j]))
                    tileTypeArrayList.add(tileTypes[i][j]);
            }
            if(tileTypeArrayList.size() > 4)
                countTimes++;
            if(countTimes == 2)
                return true;
        }
        return false;
    }

    /**
     * This method checks if the CommonGoal is achieved in the columns
     * @param tileTypes is the player's shelf
     * @return true if the CommonGoal is achieved
     */
    private boolean checkColumns(TileType[][] tileTypes){
        int countTimes = 0;
        ArrayList<TileType> tileTypeArrayList = new ArrayList<>();
        for(int j = 0; j < 5; j++){
            tileTypeArrayList.clear();
            for(int i = 0; i < 6; i++){
                if(tileTypes[i][j] != TileType.EMPTY && !tileTypeArrayList.contains(tileTypes[i][j]))
                    tileTypeArrayList.add(tileTypes[i][j]);
            }
            if(tileTypeArrayList.size() > 5)
                countTimes++;
            if(countTimes == 2)
                return true;
        }
        return false;
    }
}
