package it.polimi.ingsw.model.commonGoal;
import it.polimi.ingsw.model.TileType;

import java.util.ArrayList;

public class CG_3DiffTypes extends CommonGoal {

    public CG_3DiffTypes(int id, String description){
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
    public boolean checkCommonGoal(TileType[][] tileTypes) throws NullPointerException, IllegalArgumentException{
        if(tileTypes == null)
            throw new NullPointerException("The shelf cannot be null");
        return switch (this.getId()) {
            case 3 -> checkLines(tileTypes);
            case 8 -> checkColumns(tileTypes);
            default -> throw new IllegalArgumentException("Card id is wrong!");
        };
    }

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
