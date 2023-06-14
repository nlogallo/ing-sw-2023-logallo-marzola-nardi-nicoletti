package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commonGoal.CommonGoal;

import java.io.Serializable;
import java.util.*;

/**
 * Custom Map created for saving the tokens (for each CommonGoal) taken by players
 */

public class ThreeMap implements Serializable {
    private Map<CommonGoal, Token> firstMap;
    private Map<Token, Player> secondMap;

    private final int playersNumber;

    /**
     * Class Constructor
     *
     * @param playersNumber is the number of players in the game
     * @throws IllegalArgumentException when the number of players is incorrect
     */
    public ThreeMap(int playersNumber) throws IllegalArgumentException {
        if (playersNumber < 2 || playersNumber > 4)
            throw new IllegalArgumentException("Illegal number of players ");
        this.playersNumber = playersNumber;
        firstMap = new HashMap<CommonGoal, Token>(0);
        secondMap = new HashMap<Token, Player>(0);
    }

    /**
     * It allows you to insert a new key(CommonGoal, Token) in the map
     *
     * @param commonGoalKey is the Common Goal Card
     * @param tokenKey      is the Token create for that specific Common Goal card
     * @throws IllegalArgumentException if the Common Goal or the Token already exist
     */
    public void addKey(CommonGoal commonGoalKey, Token tokenKey) throws IllegalArgumentException {
        if (firstMap.containsKey(commonGoalKey) || secondMap.containsKey(tokenKey))
            throw new IllegalArgumentException("This key already exists");
        firstMap.put(commonGoalKey, tokenKey);
        secondMap.put(tokenKey, null); //the player doesn't exist yet
    }

    /**
     * It allows to assign the current token of the commonGoal to a specific player
     *
     * @param commonGoalKey is the Common Goal Card
     * @param player        is the Player who achieved that Common Goal
     * @return token that is the Token that the player achieved for that specific Common Goal
     * @throws IllegalArgumentException if the Common Goal does not exist
     */
    public Token setPlayer(CommonGoal commonGoalKey, Player player) throws IllegalArgumentException {
        if (!firstMap.containsKey(commonGoalKey))
            throw new IllegalArgumentException("This common goal doesn't exist");
        Token token = firstMap.get(commonGoalKey);
        secondMap.put(token, player);
        int val = token.getId();
        boolean added = false;
        if(playersNumber == 2)
            val -= 4;
        else
            val -= 2;
        if (secondMap.size() < playersNumber * 2) {
            Token updateToken;
            if((playersNumber == 2 || playersNumber == 3) && val >= 3) {
                added = true;
                updateToken = new Token(val);
                firstMap.replace(commonGoalKey, updateToken);
            }
            if(playersNumber == 4 && val >= 1) {
                added = true;
                updateToken = new Token(val);
                firstMap.replace(commonGoalKey, updateToken);
            }
        }
        if(!added)
            firstMap.replace(commonGoalKey, null);
        return token;
    }

    /**
     * It checks if the player already has a token for a specific Common Goal
     *
     * @param commonGoalKey is the Common Goal Card
     * @param player        is the player
     * @return true if the player already has a token the that Common Goal, false otherwise
     */
    public boolean hasToken(CommonGoal commonGoalKey, Player player) {
        if (!firstMap.containsKey(commonGoalKey))
            return true;
        else {
            int tokenId = firstMap.get(commonGoalKey).getId();
            for (var entry : secondMap.entrySet())
                if (entry.getValue() != null && entry.getValue().equals(player))
                    if ((entry.getKey().getId() % 2 == 0 && tokenId % 2 == 0) ||
                            (entry.getKey().getId() % 2 != 0 && tokenId % 2 != 0))
                        return true;
        }
        return false;
    }

    /**
     * It returns the CommonGoals stored in the structure
     *
     * @return the ArrayList of the CommonGoals
     */
    public ArrayList<CommonGoal> getCommonGoals() {
        ArrayList<CommonGoal> commonGoalArrayList = new ArrayList<>();
        ArrayList<CommonGoal> reverted = new ArrayList<>(firstMap.keySet());
        if(firstMap.get(reverted.get(0))!= null && firstMap.get(reverted.get(0)).getId() %2 != 0){
            commonGoalArrayList.add(reverted.get(1));
            commonGoalArrayList.add(reverted.get(0));
        }
        else
            commonGoalArrayList.addAll(firstMap.keySet());
        return commonGoalArrayList;
    }


    /**
     * Getter method, returns all the tokens list in the game
     * @param commonGoals is a list of the two CommonGoals
     * @param numOfPlayers is the number of the player in the Game
     * @return a sorted list with inside the remaining Game Tokens
     * @throws IllegalArgumentException if the number of the player is incorrect
     */
    public ArrayList<Token> getRemainingTokenList(ArrayList<CommonGoal> commonGoals, int numOfPlayers)
            throws IllegalArgumentException {

        ArrayList <Token> remainingTokenList = new ArrayList<>();
        if (numOfPlayers == 1) {
            throw new IllegalArgumentException("The number of player is incorrect");
        } else {
            remainingTokenList.addAll(getRemainingTokenListForACommonGoal(commonGoals.get(0), numOfPlayers));
            remainingTokenList.addAll(getRemainingTokenListForACommonGoal(commonGoals.get(1),numOfPlayers));
        }
        //remainingTokenList.add(0, new Token(0));
        remainingTokenList.sort(Comparator.comparingInt(Token::getId));
        return remainingTokenList;
    }


    /**
     * Private getter method, returns the remaining tokens associated with that common goal
     * @param commonGoal is the specific CommonGoal
     * @param numOfPlayers is the number of the player in the Game
     * @return a list with the remaining game Token for a specific CommonGoal
     */
    private ArrayList<Token> getRemainingTokenListForACommonGoal(CommonGoal commonGoal, int numOfPlayers) {
        int idLastToken;
        if (firstMap.get(commonGoal) != null) {
            idLastToken = firstMap.get(commonGoal).getId();
            ArrayList<Token> partialRemainingTokenList = new ArrayList<>();
            partialRemainingTokenList.add(firstMap.get(commonGoal));
            if (numOfPlayers == 2 && (idLastToken == 7 || idLastToken == 8))
                partialRemainingTokenList.add(new Token(idLastToken - 4));
            else {
                if (numOfPlayers == 3) {
                    while (idLastToken != 3 && idLastToken != 4) {
                        idLastToken -= 2;
                        partialRemainingTokenList.add(new Token(idLastToken));
                    }
                } else {
                    if(numOfPlayers == 4) {
                        while (idLastToken != 1 && idLastToken != 2) {
                            idLastToken -= 2;
                            partialRemainingTokenList.add(new Token(idLastToken));
                        }
                    }
                }
            }
            return partialRemainingTokenList;
        }
        else
            return new ArrayList<>();
    }
}


