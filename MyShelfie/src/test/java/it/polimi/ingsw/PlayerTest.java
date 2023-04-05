package it.polimi.ingsw;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.model.Token;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class PlayerTest {

    Player player = null;

    @Before
    public void setUp(){
        player = new Player(false, new Shelf(), "username", null, null);
    }

    @Test
    void adiacentTilesPoints() {
        setUp();
        TileType[][] playerShelf1 = {{TileType.PLANT, TileType.PLANT, TileType.EMPTY, TileType.GAME, TileType.EMPTY},
                                    {TileType.PLANT, TileType.PLANT, TileType.PLANT, TileType.PLANT, TileType.CAT},
                                    {TileType.TROPHY, TileType.TROPHY, TileType.PLANT, TileType.PLANT, TileType.PLANT},
                                    {TileType.TROPHY, TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.TROPHY, TileType.GAME, TileType.CAT, TileType.CAT, TileType.CAT},
                                    {TileType.GAME, TileType.GAME, TileType.GAME, TileType.CAT, TileType.CAT}};

        assertSame(19, player.adjacentTilesPoints(playerShelf1));

        TileType[][] playerShelf2 = {{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.PLANT, TileType.EMPTY, TileType.PLANT, TileType.PLANT, TileType.EMPTY},
                                    {TileType.PLANT, TileType.EMPTY, TileType.EMPTY, TileType.PLANT, TileType.EMPTY},
                                    {TileType.PLANT, TileType.EMPTY, TileType.PLANT, TileType.EMPTY, TileType.EMPTY},
                                    {TileType.PLANT, TileType.PLANT, TileType.PLANT, TileType.EMPTY, TileType.EMPTY}};

        assertSame(10, player.adjacentTilesPoints(playerShelf2));

    }

    @Test
    void calculatePoints() {
        setUp();
        player.calculatePoints();
        assertSame(0, player.getPoints());

        player.giveToken(new Token(8));
        player.giveToken(new Token(3));
        player.calculatePoints();

        assertSame(12, player.getPoints());

    }


}