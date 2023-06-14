package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

/**
 * This class is associated with the CommonGoal that checks whether there are four equal tile types in the four corners of the Shelf
 */
public class CG_4Corners extends CommonGoal{

    /**
     * Constructor method, recalls the CommonGoal constructor method
     * @param id identifies the CommonGoal id
     * @param description is the description associated with the card
     */
    public CG_4Corners(int id, String description){
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
     * @param tileTypes is the player's shelf
     * @return true if the CommonGoal is achieved
     * @throws NullPointerException if the player's shelf is null
     */
    @Override
    public boolean checkCommonGoal(TileType[][] tileTypes) throws NullPointerException{
        if(tileTypes == null)
            throw new NullPointerException("The shelf cannot be null");
        return ((tileTypes[0][0] != TileType.EMPTY) && (tileTypes[0][0] == tileTypes[0][4]) && (tileTypes[0][4] == tileTypes[5][0]) && (tileTypes[5][0] == tileTypes[5][4]));
    }
}
