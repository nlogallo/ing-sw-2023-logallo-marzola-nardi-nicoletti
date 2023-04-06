package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

class GameTest {

    @Test
    public void assignPersonalGoal() {

        Game game1 = new Game(0,2);
        game1.addPlayer(new Player(true, new Shelf(), "player1", game1));
        game1.addPlayer(new Player(false, new Shelf(), "player2", game1));
        game1.assignPersonalGoal();
        TileType[][] matrix1 = game1.getPlayers().get(0).getPersonalGoal().getMatrix();
        TileType[][] matrix2 = game1.getPlayers().get(1).getPersonalGoal().getMatrix();
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

        Game game1 = new Game(0,2);
        game1.addPlayer(new Player(true, new Shelf(), "player1", game1));
        game1.addPlayer(new Player(false, new Shelf(), "player2", game1));
        game1.startGame();
        for(CommonGoal key : game1.getCommonGoals())
            System.out.println("-------" + "\n" + key.getDescription());
    }

    @Test
    public void startGame_correctInput_correctBehavior(){

        Game game1 = new Game(0,2);
        game1.addPlayer(new Player(true, new Shelf(), "player1", game1));
        game1.addPlayer(new Player(false, new Shelf(), "player2", game1));
        game1.startGame();
    }

    @Test
    public void startGame_only1player_correctBehavior_throwIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            Game game1 = new Game(0,2);
            game1.addPlayer(new Player(true, new Shelf(), "player1", game1));
            game1.startGame();
        });

    }
    @Test
    public void updateCommonGoal_correctBehavior(){

        Game game1 = new Game(0,2);
        Player player1 = new Player(true, new Shelf(), "player1", game1);
        game1.addPlayer(player1);

        game1.addPlayer(new Player(true, new Shelf(), "player2", game1));
        game1.startGame();
        CommonGoal commonGoal = game1.getCommonGoals().get(0);
        TileType[][] playerShelf = {{TileType.CAT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        try{
            Token token = game1.updateCommonGoal(commonGoal, playerShelf, player1);
        }catch (Exception e){
            System.out.println("Not achieved");
        };
    }

    @Test
    public void fileTest_AssertEquals(){

        Game game1 = new Game(456,2);
        Player player1 = new Player(true, new Shelf(), "player1", game1);
        game1.addPlayer(player1);
        game1.addPlayer(new Player(true, new Shelf(), "player2", game1));
        game1.startGame();
        game1.saveGame();
        Game game2 = game1.restoreGame();
        assertEquals(game1.getId(), game2.getId());
        game2.endGame();
    }


    @Test
    public void winnerTest() {
        //To be implemented after complete PlayerClass
    }

    @Test
    public void nextPhaseText() {

        Game game = new Game(400,4);
        Shelf shelf = new Shelf();
        for (int i = 0; i<4; i++) {
            String nickname = "Player" + i;
            Player player = new Player (false, shelf, nickname, game);
            game.addPlayer(player);
        }
        Player initialFirstPlayer = game.getPlayers().get(0);
        Player initialSecondPlayer = game.getPlayers().get(1);
        Player initialThirdPlayer = game.getPlayers().get(2);
        Player initialFourthPlayer = game.getPlayers().get(3);
        game.nextPhase();
        assertEquals(initialSecondPlayer, game.getPlayers().get(0));
        assertEquals(initialThirdPlayer, game.getPlayers().get(1));
        assertEquals(initialFourthPlayer, game.getPlayers().get(2));
        assertEquals(initialFirstPlayer, game.getPlayers().get(3));

    }
    @Test
    public void testAllGame(){
        Game game = new Game(1, 2);
        Player player1 = new Player(true, new Shelf(), "pippo", game);
        Player player2 = new Player(false, new Shelf(), "pluto", game);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.startGame();
        for(CommonGoal cg : game.getCommonGoals())
            System.out.println(cg.getDescription());
        System.out.println("------------");
        for(Player p : game.getPlayers()) {
            TileType[][] matrix1 = p.getPersonalGoal().getMatrix();
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++) {
                    System.out.print(matrix1[i][j]+" ");
                }
                System.out.print("\n");
            }
            System.out.println("-----------");
            p.calculatePoints();
            System.out.println(p.getPoints());
        }
        System.out.println("Current player: " + game.getCurrentPlayer().getNickname());
        System.out.println("-----------");
        TileType[][] tt = game.getBoard().getTilesType();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++) {
                System.out.print(tt[i][j]+" ");
            }
            System.out.print("\n");
        }
        ArrayList<Position> positions1 = new ArrayList<>();
        positions1.add(new Position(1, 3));
        positions1.add(new Position(1,4));
        game.getCurrentPlayer().makeMove(0, positions1);
        tt = game.getBoard().getTilesType();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++) {
                System.out.print(tt[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.println("\n\n");
        game.getBoard().getTilesType();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++) {
                System.out.print(tt[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
        TileType[][] shelf1 = game.getCurrentPlayer().getShelf().getShelfTypes();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++) {
                System.out.print(shelf1[i][j]+" ");
            }
            System.out.print("\n");
        }
        game.nextPhase();
        System.out.println("Current player: " + game.getCurrentPlayer().getNickname());
        game.nextPhase();
        System.out.println("Current player: " + game.getCurrentPlayer().getNickname());
        System.out.println(game.winner().getNickname());
    }
}