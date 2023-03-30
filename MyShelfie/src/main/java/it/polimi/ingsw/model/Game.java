package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the single game started by the server
 */
public class Game {
    private Player[] players;
    private Board board;
    private GameState state;
    private Player firstToEnd;
    private Player currentPlayer;

    /**
     * Class constructor
     * @param players as the players participating in the game
     */
    public Game(Player[] players){
        this.players = players;
        this.board = new Board(players.length);
        this.state = GameState.WAITING_FOR_PLAYERS;
        this.firstToEnd = null;
        this.currentPlayer = null;
    }

    /**
     * Makes the assignation of the personalGoal to each player
     */
    public void assignPersonalGoal(){
        TileType[][] PersonalGoal1 = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] PersonalGoal2 = {{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.PLANT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.CAT, TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.FRAME}};
        TileType[][] PersonalGoal3 = {{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.FRAME, TileType.EMPTY, TileType.EMPTY, TileType.GAME, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.PLANT, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.TROPHY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.BOOK, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] PersonalGoal4 = {{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.GAME},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.TROPHY, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.PLANT, TileType.EMPTY},{TileType.EMPTY, TileType.BOOK, TileType.CAT, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] PersonalGoal5 = {{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.FRAME, TileType.BOOK, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.PLANT},{TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.EMPTY}};
        TileType[][] PersonalGoal6 = {{TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.FRAME, TileType.EMPTY},{TileType.PLANT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] PersonalGoal7 = {{TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.FRAME, TileType.EMPTY},{TileType.EMPTY, TileType.PLANT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.TROPHY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.GAME},{TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] PersonalGoal8 = {{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.FRAME},{TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY},{TileType.PLANT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.GAME, TileType.EMPTY}};
        TileType[][] PersonalGoal9 = {{TileType.EMPTY, TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK},{TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY, TileType.PLANT},{TileType.FRAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] PersonalGoal10 = {{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.TROPHY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.BOOK, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.EMPTY},{TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.PLANT, TileType.EMPTY}};
        TileType[][] PersonalGoal11 = {{TileType.EMPTY, TileType.EMPTY, TileType.PLANT, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.BOOK, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY}};
        TileType[][] PersonalGoal12 = {{TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.PLANT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.GAME},{TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}};
        ArrayList<PersonalGoal> personalGoals = new ArrayList<>();
        personalGoals.add(new PersonalGoal(PersonalGoal1));
        personalGoals.add(new PersonalGoal(PersonalGoal2));
        personalGoals.add(new PersonalGoal(PersonalGoal3));
        personalGoals.add(new PersonalGoal(PersonalGoal4));
        personalGoals.add(new PersonalGoal(PersonalGoal5));
        personalGoals.add(new PersonalGoal(PersonalGoal6));
        personalGoals.add(new PersonalGoal(PersonalGoal7));
        personalGoals.add(new PersonalGoal(PersonalGoal8));
        personalGoals.add(new PersonalGoal(PersonalGoal9));
        personalGoals.add(new PersonalGoal(PersonalGoal10));
        personalGoals.add(new PersonalGoal(PersonalGoal11));
        personalGoals.add(new PersonalGoal(PersonalGoal12));
        Random random = new Random();
        int randomNum;
        int tempIndex = 11;
        for (Player player : players) {
            randomNum = random.nextInt(tempIndex);
            player.setPersonalGoal(personalGoals.get(randomNum));
            personalGoals.remove(randomNum);
            tempIndex--;
        }
    }
}