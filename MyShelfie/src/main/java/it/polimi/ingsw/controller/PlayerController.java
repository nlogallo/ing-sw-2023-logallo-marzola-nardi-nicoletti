package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Token;

import java.util.ArrayList;

public class PlayerController {
    public static class UtilPlayerController{
        public static Shelf getPlayerShelf(Player player){
            return player.getShelf();
        }

        public static ArrayList<Token> getPlayerTokens(Player player){
            return player.getTokenCards();
        }
    }
}
