package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

public class CG_8Tiles extends CommonGoal{

    public CG_8Tiles(int id, String description){
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
        int catsNumber = 0, booksNumber = 0, gamesNumber = 0, framesNumber = 0, trophiesNumber = 0, plantsNumber = 0;
        for (TileType[] tileType : tileTypes) {
            for (TileType type : tileType) {
                switch (type) {
                    case CAT:
                        catsNumber++;
                        break;
                    case BOOK:
                        booksNumber++;
                        break;
                    case GAME:
                        gamesNumber++;
                        break;
                    case FRAME:
                        framesNumber++;
                        break;
                    case TROPHY:
                        trophiesNumber++;
                        break;
                    case PLANT:
                        plantsNumber++;
                        break;
                    case EMPTY:
                        break;
                }
                if (catsNumber == 8 || booksNumber == 8 || gamesNumber == 8 || framesNumber == 8 || trophiesNumber == 8 || plantsNumber == 8)
                    return true;
            }
        }
        return false;
    }
}
