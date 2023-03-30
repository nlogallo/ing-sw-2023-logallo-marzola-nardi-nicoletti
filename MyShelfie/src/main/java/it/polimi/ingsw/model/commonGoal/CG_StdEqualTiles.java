package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

public class CG_StdEqualTiles extends CommonGoal{

    public CG_StdEqualTiles(int id, String description){
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
        return switch (this.getId()) {
            case 0 -> check2Tiles(tileTypes);
            case 2 -> check4Tiles(tileTypes);
            default -> throw new IllegalArgumentException("Card id is wrong!");
        };
    }

    private boolean check2Tiles(TileType[][] tileTypes){
        int countTimes = 0;
        for(int i = 0; i < tileTypes.length; i++){
            for(int j = 0; j < tileTypes[i].length; j++){
                if(j != tileTypes[i].length - 1 && tileTypes[i][j] != TileType.EMPTY && tileTypes[i][j] == tileTypes[i][j + 1])
                    countTimes++;
                if(i != tileTypes.length - 1 && tileTypes[i][j] != TileType.EMPTY && tileTypes[i][j] == tileTypes[i + 1][j])
                    countTimes++;
                if(countTimes >= 6)
                    return true;
            }
        }
        return false;
    }

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
                        if (countEquals > 3)
                            countTimes++;
                    }
                    if (i < 3) {
                        countEquals = 1;
                        for (int d = 1; i + d < 6; d++) {
                            if(tileType == tileTypes[i + d][j])
                                countEquals++;
                        }
                        if (countEquals > 3)
                            countTimes++;
                    }
                }
                if(countTimes > 3)
                    return true;
            }
        }
        return false;
    }
}
