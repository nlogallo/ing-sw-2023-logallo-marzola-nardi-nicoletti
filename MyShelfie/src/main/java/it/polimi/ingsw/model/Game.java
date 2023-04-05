package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.model.commonGoal.*;
import it.polimi.ingsw.model.wrapperCustom.DescriptionWrapper;
import it.polimi.ingsw.model.wrapperCustom.PersonalGoalWrapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * This class represents the single game started by the server
 */
public class Game implements Serializable{
    int id;
    private ArrayList<Player> players;
    private int playersNumber;
    private Board board;
    private GameState state;
    private Player firstToEnd;
    private Player currentPlayer;
    private ThreeMap commonGoals;
    private final File gameFile;


    /**
     * Class constructor
     * @param id is the ID of the game instance
     * @throws NullPointerException if participants is null
     */
    public Game(int id, int playersNumber) throws NullPointerException{
        this.id = id;
        this.playersNumber = playersNumber;
        this.players = new ArrayList<>();
        this.board = null;
        this.state = GameState.WAITING_FOR_PLAYERS;
        this.firstToEnd = null;
        this.currentPlayer = null;
        this.commonGoals = null;
        this.gameFile = new File("data/savedGame/game" + this.id + ".bin");
    }

    /**
     * This method allows to start a new game
     * @throws IllegalArgumentException if the number of players is incorrect
     */
    public void startGame() throws  IllegalArgumentException{
        if(players.size() < 2 || players.size() > 4)
            throw new IllegalArgumentException("Incorrect number of players");
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
        //saveGame();
    }

    /**
     * This method ends the game
     */
    public void endGame(){
        this.state = GameState.ENDED;
        this.currentPlayer = null;
        try {
            Files.delete(Path.of("data/savedGame/game" + this.id + ".bin"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method saves the game infos in a binary file
     */
    public void saveGame(){
        try {
            byte[] idData = serializeObject(this);
            FileOutputStream fileOutputStream = new FileOutputStream(this.gameFile);
            fileOutputStream.write(idData, 0, idData.length);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method restore the infos of the game
     * @return a Game object
     */
    public Game restoreGame(){
        Object object;
        byte[] data = new byte[(int) this.gameFile.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(this.gameFile);
            fileInputStream.read(data, 0, data.length);
            fileInputStream.close();
            object = deserializeObject(data);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return (Game) object;
    }

    /**
     * This method serialize an Object
     * @param object is the object to serialize
     * @return a byte array
     * @throws IOException
     */
    private byte[] serializeObject(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * This method deserialize in an Object
     * @param data is an array of byte
     * @return an Object
     * @throws IOException
     */
    private Object deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
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
    public int getId() {
        return id;
    }


    /**
     * Getter method
     * @return the current players list
     */
    public ArrayList<Player> getPlayers () {
        return players;
    }


    /**
     * This method adds a new player in the game, and if the list is empty it associates the
     * first player that has been added to the current player
     * @param player is the new player
     * @throws IllegalArgumentException if the game has already the maximum number of players
     */
    public void addPlayer (Player player) throws IllegalArgumentException{
        if (players.size() == 0) {
            this.currentPlayer = player;
        }
        if (players.size() <= 3) {
            this.players.add(player);
        } else throw new IllegalArgumentException("The game has already the max number of players");
    }


    /**
     * This method changes the order of the players in the players' ArrayList and set the new current player
     * The player's position in the list represents the remaining turns he will have to wait before his turn
     * @return the next player who will play
     */
    public Player nextPhase() {

        int currentPlayerIndex = players.indexOf(this.currentPlayer);
        players.set(currentPlayerIndex, players.get(currentPlayerIndex + 1));
        for(int i = 1; i <=2 ; i++) {
            Player tempPlayer = players.get(i);
            players.set(i, players.get(i+1));
            players.set(i+1, tempPlayer);
        }
        players.set(players.size()-1, this.currentPlayer);
        this.currentPlayer = players.get(0);
        return this.currentPlayer;
    }


    /**
     * This method decrees the winner of the game
     * @return the winner of the game
     */
    public Player winner () {

        Player winner;
        ArrayList <Integer> playerPoints = new ArrayList<>();
        for (Player player : players) {
            playerPoints.add(player.getPoints());
        }
        int maxPointIndex = playerPoints.indexOf(Collections.max(playerPoints));
        winner = players.get(maxPointIndex);
        return winner;
    }


    /**
     * Getter method
     * @return the current Board of the game
     */
    public it.polimi.ingsw.model.Board getBoard() {
        return this.board;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state){
        this.state = state;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }
}


