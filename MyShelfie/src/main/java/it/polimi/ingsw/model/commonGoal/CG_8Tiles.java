package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

/**
 * This is the class associated to the CommonGoal that checks whether there eight tiles of the same type
 */
public class CG_8Tiles extends CommonGoal{

    /**
     * Constructor method, recalls the CommonGoal constructor method
     * @param id identifies the CommonGoal id
     * @param description is the description associated with the card
     */
    public CG_8Tiles(int id, String description){
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
