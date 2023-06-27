package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.TileType;

import java.io.Serializable;

/**
 * This abstract class represents the generic Common Goal
 */
public abstract class CommonGoal implements Serializable {
    final private int id;
    final private String description;

    /**
     * Constructor method
     * @param cardId identifies the id of the CommonGoal
     * @param cardDescription is the description associated with the card
     * @throws IllegalArgumentException if the id is not between the legal range
     * @throws NullPointerException if the description is null
     */
    public CommonGoal(int cardId, String cardDescription) throws IllegalArgumentException, NullPointerException{
        if(cardId < 0 || cardId > 11) throw new IllegalArgumentException("Invalid id");
        if(cardDescription == null) throw new NullPointerException("Description cannot be null");
        this.id = cardId;
        this.description = cardDescription;
    }

    /**
     * It returns the common goal id
     * @return an int, that is the id
     */
    public int getId(){
        return this.id;
    }

    /**
     * It returns the common goal description
     * @return a String, that is the description
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * This is the method that checks if the CommonGoal is achieved
     * @param tileTypes is the player's shelf
     */
    public boolean checkCommonGoal(TileType[][] tileTypes){
        return false;
    }

}
