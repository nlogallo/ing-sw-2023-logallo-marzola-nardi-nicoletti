package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * @author Nunzio Logallo
 * This class represents the in game Player
 */
public class Player implements Serializable {
    private boolean seat;
    private Shelf shelf;
    private String nickname;
    private State state;
    private PersonalGoal personalGoal;
    private Game game;
    private Token[] tokenCards;
    private int points;

    /**
     * Class constructor
     * @param shelf as the personal Shelf of the Player
     * @param nickname as the in game name of the Player
     * @param state as the state in which the player is
     * @param personalGoal as the Personal Goal assigned to the player
     * @param game as the game in which the player is
     */
    public Player(boolean seat, Shelf shelf, String nickname, State state, PersonalGoal personalGoal, Game game){
        this.seat = seat;
        this.shelf = shelf;
        this.nickname = nickname;
        this.state = new IsWaiting();
        this.personalGoal = null;
        this.game = game;
        this.tokenCards = null;
        this.points = 0;
    }

    /**
     * Checks if the Player has the first player seat
     * @return boolean
     */
    public boolean hasSeat(){
        return seat;
    }

    /**
     * Calculates player points
     */
    public void calculatePoints(){
        for (Token tokenCard : tokenCards) {
            this.points += tokenCard.getPoints();
        }
    }

    public void makeMove(){

    }

    /**
     * Sets the player's personal goal
     * @param personalGoal as the personal goal assigned to the player
     */
    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }

    public PersonalGoal getPersonalGoal(){
        return this.personalGoal;
    }

    public int getPoints() {
        return this.points;
    }
}

