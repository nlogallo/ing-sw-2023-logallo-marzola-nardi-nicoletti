package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commonGoal.CommonGoal;

import java.io.Serializable;
import java.util.*;

/**
 * Custom Map created for saving the tokens (for each CommonGoal) taken by players
 * @author Emanuele Nicoletti
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
        firstMap = new HashMap<CommonGoal, Token>(2);
        secondMap = new HashMap<Token, Player>(playersNumber * 2);
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
        Token token = firstMap.remove(commonGoalKey);
        secondMap.put(token, player);
        if (token.getId() > 2 && secondMap.size() < playersNumber * 2) {
            Token updateToken = new Token(token.getId() - 2);
            firstMap.put(commonGoalKey, updateToken);
        }
        return token;
    }

    /**
     * checks if the player already has a token for a specific Common Goal
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
     * This method returns the CommonGoals stored in the structure
     *
     * @return the ArrayList of the CommonGoals
     */
    public ArrayList<CommonGoal> getCommonGoals() {
        ArrayList<CommonGoal> commonGoalArrayList = new ArrayList<>();
        commonGoalArrayList.addAll(firstMap.keySet());
        return commonGoalArrayList;
    }


    /**
     * Getter method
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
        remainingTokenList.add(0, new Token(0));
        remainingTokenList.sort(Comparator.comparingInt(Token::getId));
        return remainingTokenList;
    }


    /**
     * Private getter method
     * @param commonGoal is the specific CommonGoal
     * @param numOfPlayers is the number of the player in the Game
     * @return a list with the remaining game Token for a specific CommonGoal
     */
    private ArrayList<Token> getRemainingTokenListForACommonGoal(CommonGoal commonGoal, int numOfPlayers) {

        int idLastToken = firstMap.get(commonGoal).getId();
        ArrayList<Token> partialRemainingTokenList = new ArrayList<>();
        partialRemainingTokenList.add(firstMap.get(commonGoal));

        for (int i = 1; i < numOfPlayers; i++) {
            idLastToken -= 2;
            partialRemainingTokenList.add(new Token(idLastToken));
        }
        return partialRemainingTokenList;
    }
}


