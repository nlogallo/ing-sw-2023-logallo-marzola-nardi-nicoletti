package it.polimi.ingsw;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Shelf;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class PlayerTest {

    Player player;

    @Before
    public void setUp(){
        this.player = new Player(false, new Shelf(), "username", null, null);
    }

    /*@Test
    void calculatePoints() {
        this.player.calculatePoints();
    }*/
}