package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

public class CG_4Corners extends CommonGoal{

    public CG_4Corners(int id, String description){
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

    @Override
    public boolean checkCommonGoal(TileType[][] tileTypes) {
        if(tileTypes == null)
            throw new NullPointerException("The shelf cannot be null");
        return ((tileTypes[0][0] != TileType.EMPTY) && (tileTypes[0][0] == tileTypes[0][4]) && (tileTypes[0][4] == tileTypes[5][0]) && (tileTypes[5][0] == tileTypes[5][4]));
    }
}
