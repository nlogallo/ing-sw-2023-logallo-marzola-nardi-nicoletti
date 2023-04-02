package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GameTest {
    @Test
    public void assignPersonalGoal() {
        ArrayList<Player> players= new ArrayList<>();
        Game game1 = new Game(players);
        players.add(new Player(true, new Shelf(), "player1", null, null, game1));
        players.add(new Player(false, new Shelf(), "player2", null, null, game1));
        game1 = new Game(players); //delete when the method Game.addPlayers is done
        game1.assignPersonalGoal();
        TileType[][] matrix1 = players.get(0).getPersonalGoal().getMatrix();
        TileType[][] matrix2 = players.get(1).getPersonalGoal().getMatrix();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++) {
                System.out.print(matrix1[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.println("----------------------------");
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++) {
                System.out.print(matrix2[i][j]+" ");
            }
            System.out.print("\n");
        }
    }

    @Test
    public void addCommonGoals_correctBehavior(){
        ArrayList<Player> players= new ArrayList<>();
        Game game1 = new Game(players);
        players.add(new Player(true, new Shelf(), "player1", null, null, game1));
        players.add(new Player(false, new Shelf(), "player2", null, null, game1));
        game1 = new Game(players);//delete when the method Game.addPlayers is done
        game1.startGame();
        for(CommonGoal key : game1.getCommonGoals())
            System.out.println("-------" + "\n" + key.getDescription());
    }

    @Test
    public void startGame_correctInput_correctBehavior(){
        ArrayList<Player> players= new ArrayList<>();
        Game game1 = new Game(players);
        players.add(new Player(true, new Shelf(), "player1", null, null, game1));
        players.add(new Player(false, new Shelf(), "player2", null, null, game1));
        game1 = new Game(players); //delete when the method Game.addPlayers is done
        game1.startGame();
    }

    @Test
    public void startGame_only1player_correctBehavior(){
        ArrayList<Player> players= new ArrayList<>();
        Game game1 = new Game(players);
        players.add(new Player(true, new Shelf(), "player1", null, null, game1));
        game1 = new Game(players); //delete when the method Game.addPlayers is done
        game1.startGame();
    }

    @Test
    public void updateCommonGoal_correctBehavior(){
        ArrayList<Player> players= new ArrayList<>();
        Game game1 = new Game(players);
        Player player1 = new Player(true, new Shelf(), "player1", null, null, game1);
        players.add(player1);
        players.add(new Player(true, new Shelf(), "player2", null, null, game1));
        game1 = new Game(players); //delete when the method Game.addPlayers is done
        game1.startGame();
        CommonGoal commonGoal = game1.getCommonGoals().get(0);
        TileType[][] playerShelf = {{TileType.CAT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        try{
            Token token = game1.updateCommonGoal(commonGoal, playerShelf, player1);
        }catch (Exception e){
            System.out.println("Not achieved");
        };
    }



}