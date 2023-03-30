package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

import java.util.ArrayList;

public class CG_DiffHeight extends CommonGoal{

    public CG_DiffHeight(int id, String description){
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
            for(int d = 0; d < countList.size() - 1; d++){
                if((countList.get(d) != countList.get(d + 1) + 1) && (countList.get(d) != countList.get(d + 1) - 1))
                    return false;
            }
        }
        return true;
    }
}
