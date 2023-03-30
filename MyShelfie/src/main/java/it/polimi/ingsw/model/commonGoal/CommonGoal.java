package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

public abstract class CommonGoal {

    final private int id;
    final private String description;

    public CommonGoal(int cardId, String cardDescription){
        if(cardId < 0 || cardId > 11) throw new IllegalArgumentException("Invalid id");
        if(cardDescription == null) throw new NullPointerException("Description cannot be null");
        this.id = cardId;
        this.description = cardDescription;
    }
    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean checkCommonGoal(TileType[][] tileTypes){
        return false;
    }

}
