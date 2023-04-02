package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.model.commonGoal.*;
import it.polimi.ingsw.model.wrapperCustom.DescriptionWrapper;
import it.polimi.ingsw.model.wrapperCustom.PersonalGoalWrapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 * This class represents the single game started by the server
 */
public class Game {
    private ArrayList<Player> players;
    private Board board;
    private GameState state;
    private Player firstToEnd;
    private Player currentPlayer;
    private ThreeMap commonGoals;

    /**
     * Class constructor
     * @param participants as the players participating in the game
     * @throws NullPointerException if participants is null
     */
    public Game(ArrayList<Player> participants) throws NullPointerException{
        this.players = new ArrayList<>();
        if(participants == null)
            throw new NullPointerException("Players list cannot be null");
        players.addAll(participants);
        this.board = null;
        this.state = GameState.WAITING_FOR_PLAYERS;
        this.firstToEnd = null;
        this.currentPlayer = null;
        this.commonGoals = null;
    }

    /**
     * This method permits to start a new game
     * @throws IllegalArgumentException if the number of players is incorrect
     */
    public void startGame() throws  IllegalArgumentException{
        /*if(players.size() < 2 || players.size() > 4)
            throw new IllegalArgumentException("Number of players incorrect"); reuse when the method add player is done*/
        this.state = GameState.STARTED;
        try {
            this.commonGoals = new ThreeMap(players.size());
        }catch (IllegalArgumentException ex){
            System.out.println("Now you have to wait a player");
            /*here we have to do something*/
        }
        chooseCommonGoals();
        assignPersonalGoal();
        board = new Board(players.size());
    }

    /**
     * Makes the assignation of the personalGoal to each player
     */
    public void assignPersonalGoal(){
        ArrayList<Integer> personalGoalsIndex = new ArrayList<>();
        for(int i = 0; i < 12; i++)
            personalGoalsIndex.add(i);
        Random random = new Random();
        int randomNum;
        int tempIndex = 11;
        for (Player player : players) {
            randomNum = random.nextInt(tempIndex);
            player.setPersonalGoal(new PersonalGoal(readPGFromJSON(randomNum)));
            personalGoalsIndex.remove(randomNum);
            tempIndex--;
        }
    }

    /**
     * This method picks two random commonGoals and saved them in the ThreeMap
     */
    public void chooseCommonGoals(){
        ArrayList<Integer> commonGoalIndex = new ArrayList<>();
        for(int i = 0; i < 12; i++)
            commonGoalIndex.add(i);
        Random random = new Random();
        int randomNum;
        int tempIndex = 11;
        boolean firstCommonGoal = true;
        for (int i = 0; i < 2; i++) {
            randomNum = random.nextInt(tempIndex);
            setProperCommonGoal(randomNum, readDescriptionFromJSON(randomNum), firstCommonGoal);
            firstCommonGoal = false;
            commonGoalIndex.remove(randomNum);
            tempIndex--;
        }
    }

    /* Needs to be modified after we create the new class for positions
    public requestForTiles(){
        if(board.checkRefill())
            board.refillBoard();
        board.pullTiles();
    }
    */

    /**
     * This method give the correct token if the player has achieved the commonGoal
     * @param commonGoal is the commonGoal
     * @param playerShelf is the player's shelf
     * @param player is the player who require for a check
     * @return a Token
     * @throws Exception when the Token cannot be given
     * @throws NullPointerException if at least one of parameters is null
     * @throws IllegalStateException if the player already has a token for that CommonGoal
     */
    public Token updateCommonGoal(CommonGoal commonGoal, TileType[][] playerShelf, Player player) throws Exception, NullPointerException, IllegalStateException {
        if(commonGoal == null)
            throw new NullPointerException("The commongoal cannot be null");
        if(playerShelf == null)
            throw new NullPointerException("The shelf cannot be null");
        if(player == null)
            throw new NullPointerException("The player cannot be null");
        if(commonGoals.hasToken(commonGoal, player))
            throw new IllegalStateException("Player already has a token");
        else{
            if(commonGoal.checkCommonGoal(playerShelf))
                return commonGoals.setPlayer(commonGoal, player);
            else
                throw new Exception();
        }
    }


    public ArrayList<CommonGoal> getCommonGoals(){
        return commonGoals.getCommonGoals();
    }


    /**
     * This private method reads the PersonalGoals matrix from the personalGoals.json file
     * @param id is the id of the PersonalGoal card
     * @return the matrix associated at that PersonalGoal
     */
    private TileType[][] readPGFromJSON(int id){
        Gson gson = new Gson();
        PersonalGoalWrapper wrapper;
        try {
            FileReader reader = new FileReader("data/personalGoals.json");
            wrapper = gson.fromJson(reader, PersonalGoalWrapper.class);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wrapper.getMatrix(id);
    }

    /**
     * This private method reads the CommonGoal descriptions from the commonGoalDescriptions.json file
     * @param id is the id of the CommonGoals card
     * @return a String, that is the description
     */
    private String readDescriptionFromJSON(int id){
        Gson gson = new Gson();
        DescriptionWrapper wrapper;
        try {
            FileReader reader = new FileReader("data/commonGoalDescriptions.json");
            wrapper = gson.fromJson(reader, DescriptionWrapper.class);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wrapper.getDescription(id);
    }

    /**
     * This private method calls the constructor of proper CommonGoal card
     * @param id is the CommonGoal id
     * @param description is the description associated
     * @param firstCommonGoal is a boolean needed to set the correct id of Token Cards. If true it creates a Token with id 8, otherwise with id 7
     * @throws IllegalStateException if the id is out of range
     */
    private void setProperCommonGoal(int id, String description, boolean firstCommonGoal) throws IllegalStateException{
        int id_token;
        if(firstCommonGoal)
            id_token = 8;
        else
            id_token = 7;
        switch (id){
            case 0, 2 -> commonGoals.addKey(new CG_StdEqualTiles(id, description), new Token(id_token));
            case 1, 6, 9 -> commonGoals.addKey(new CG_SpecialEqualTiles(id, description), new Token(id_token));
            case 3, 8 -> commonGoals.addKey(new CG_3DiffTypes(id, description), new Token(id_token));
            case 4 -> commonGoals.addKey(new CG_4Corners(id, description), new Token(id_token));
            case 5, 7 -> commonGoals.addKey(new CG_DiffTiles(id, description), new Token(id_token));
            case 10 -> commonGoals.addKey(new CG_8Tiles(id, description), new Token(id_token));
            case 11 -> commonGoals.addKey(new CG_DiffHeight(id, description), new Token(id_token));
            default -> throw new IllegalStateException("Unexpected id value: " + id);
        }
    }



}