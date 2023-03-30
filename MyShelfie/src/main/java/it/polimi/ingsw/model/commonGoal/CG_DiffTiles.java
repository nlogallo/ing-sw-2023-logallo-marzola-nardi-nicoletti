package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

import java.util.ArrayList;

public class CG_DiffTiles extends CommonGoal{

    public CG_DiffTiles(int id, String description){
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
        return switch (this.getId()) {
            case 5 -> checkColumns(tileTypes);
            case 7 -> checkLines(tileTypes);
            default -> throw new IllegalArgumentException("Card id is wrong!");
        };
    }

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
