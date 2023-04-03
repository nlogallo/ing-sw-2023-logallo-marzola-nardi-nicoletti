package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

class GameTest {

    @Test
    public void assignPersonalGoal() {

        Game game1 = new Game(0);
        game1.addPlayer(new Player(true, new Shelf(), "player1", null, null, game1));
        game1.addPlayer(new Player(false, new Shelf(), "player2", null, null, game1));
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

        Game game1 = new Game(0);
        game1.addPlayer(new Player(true, new Shelf(), "player1", null, null, game1));
        game1.addPlayer(new Player(false, new Shelf(), "player2", null, null, game1));
        game1.startGame();
        for(CommonGoal key : game1.getCommonGoals())
            System.out.println("-------" + "\n" + key.getDescription());
    }

    @Test
    public void startGame_correctInput_correctBehavior(){

        Game game1 = new Game(0);
        game1.addPlayer(new Player(true, new Shelf(), "player1", null, null, game1));
        game1.addPlayer(new Player(false, new Shelf(), "player2", null, null, game1));
        game1.startGame();
    }

    @Test
    public void startGame_only1player_correctBehavior_throwIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            Game game1 = new Game(0);
            game1.addPlayer(new Player(true, new Shelf(), "player1", null, null, game1));
            game1.startGame();
        });

    }
    @Test
    public void updateCommonGoal_correctBehavior(){

        Game game1 = new Game(0);
        Player player1 = new Player(true, new Shelf(), "player1", null, null, game1);
        game1.addPlayer(player1);

        game1.addPlayer(new Player(true, new Shelf(), "player2", null, null, game1));
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

        Game game1 = new Game(456);
        Player player1 = new Player(true, new Shelf(), "player1", null, null, game1);
        game1.addPlayer(player1);
        game1.addPlayer(new Player(true, new Shelf(), "player2", null, null, game1));
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

        Game game = new Game(400);
        Shelf shelf = new Shelf();
        for (int i = 0; i<4; i++) {
            String nickname = "Player" + i;
            Player player = new Player (false, shelf, nickname,null,null, game);
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
}