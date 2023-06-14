package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

import java.util.ArrayList;

/**
 * This is the class associated to the CommonGoal that checks whether there are five columns of increasing or decreasing height
 */
public class CG_DiffHeight extends CommonGoal{

    /**
     * Constructor method, recalls the CommonGoal constructor method
     * @param id identifies the CommonGoal id
     * @param description is the description associated with the card
     */
    public CG_DiffHeight(int id, String description){
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
        int countForColumn;
        ArrayList<Integer> countList = new ArrayList<>();
        for(int j = 0; j < 5; j++){
            countForColumn = 0;
            for(int i = 0; i < 6; i++){
                if(tileTypes[i][j] != TileType.EMPTY)
                    countForColumn++;
            }
            countList.add(countForColumn);
        }
        boolean increasing = true;
        for(int d = 0; d < countList.size() - 1; d++) {
            if ((countList.get(d) != countList.get(d + 1) + 1)) {
                increasing = false;
                break;
            }
        }
        if(increasing)
            return true;
        for(int d = 0; d < countList.size() - 1; d++){
            if((countList.get(d) != countList.get(d + 1) - 1))
                return false;
        }
        return true;
    }
}
