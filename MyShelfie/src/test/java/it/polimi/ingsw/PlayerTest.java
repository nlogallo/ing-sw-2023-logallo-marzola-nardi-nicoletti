package it.polimi.ingsw;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class PlayerTest {

    Player player;

    @Before
    public void setUp(){
        this.player = new Player(false, new Shelf(), "username", null, null, null);
    }

    @Test
    void calculatePoints() {
        this.player.calculatePoints();
    }
}