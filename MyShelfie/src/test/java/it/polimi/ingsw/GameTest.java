package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

class GameTest {
    @Test
    void assignPersonalGoal() {
        Player[] players = new Player[2];
        Game game1 = new Game(players);
        players[0] = new Player(true, new Shelf(), "player1", null, null, game1);
        players[1] = new Player(false, new Shelf(), "player2", null, null, game1);
        game1.assignPersonalGoal();
        TileType[][] matrix1 = players[0].getPersonalGoal().getMatrix();
        TileType[][] matrix2 = players[1].getPersonalGoal().getMatrix();
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
}