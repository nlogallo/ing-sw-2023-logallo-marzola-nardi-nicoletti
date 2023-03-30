package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

public class CG_SpecialEqualTiles extends CommonGoal{

    public CG_SpecialEqualTiles(int id, String description){
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
            case 1 -> checkDiagonal(tileTypes);
            case 6 -> checkSquare(tileTypes);
            case 9 -> checkCross(tileTypes);
            default -> throw new IllegalArgumentException("Card id is wrong!");
        };
    }

    private boolean checkDiagonal(TileType[][] tileTypes){
        boolean stillPossible;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < tileTypes[i].length;){
                stillPossible = true;
                TileType tileType = tileTypes[i][j];
                if(tileType != TileType.EMPTY) {
                    if (j == 0) {
                        for (int d = 0; d < 5 && stillPossible; d++)
                            stillPossible = tileType == tileTypes[i + d][j + d];
                    } else {
                        for (int d = 0; d < 5 && stillPossible; d++) {
                            stillPossible = tileType == tileTypes[i + d][j - d];
                        }
                    }
                    if (stillPossible)
                        return true;
                }
                j += 4;
            }
        }
        return false;
    }

    private boolean checkSquare(TileType[][] tileTypes){
        int countTimes = 0;
        boolean stillPossible;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 4; j++){
                stillPossible = true;
                TileType tileType = tileTypes[i][j];
                if(tileType != TileType.EMPTY) {
                    for (int c = 0; c < 2 && stillPossible; c++) {
                        for (int r = 0; r < 2 && stillPossible; r++) {
                            if (tileType != tileTypes[i + r][j + c])
                                stillPossible = false;
                        }
                    }
                    if (stillPossible)
                        countTimes++;
                    if (countTimes > 1)
                        return true;
                }
            }
        }
        return false;
    }

    public boolean checkCross(TileType[][] tileTypes){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                TileType tileType = tileTypes[i][j];
                if(tileType != TileType.EMPTY){
                    if((tileType == tileTypes[i][j + 2]) && (tileType == tileTypes[i + 1][j + 1]) && (tileType == tileTypes[i + 2][j] ) && (tileType == tileTypes[i + 2][j + 2]))
                        return true;
                }
            }
        }
        return false;
    }
}
