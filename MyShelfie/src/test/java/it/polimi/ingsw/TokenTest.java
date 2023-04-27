package it.polimi.ingsw;

import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class TokenTest {

    @Before
    public void setUp () {}

    @After
    public void tearDown () {}

    @Test
    public void newToken_CorrectBehavior () {

        Token token1 = new Token(8);
        assertEquals(token1.getPoints(), 8, 0);
        assertEquals(token1.getType(), TokenType.SCORING);

        Token token2 = new Token(0);
        assertEquals(token2.getPoints(), 1, 0);
        assertEquals(token2.getType(), TokenType.END_GAME);

        Token token3 = new Token(3);
        assertEquals(token3.getPoints(), 4, 0);
        assertEquals(token3.getType(), TokenType.SCORING);

        Token token4 = new Token(6);
        assertEquals(token4.getPoints(), 6, 0);
        assertEquals(token4.getType(), TokenType.SCORING);
    }


    @Test
    public void newToken_ThrowsIllegalArgumentException () {
        assertThrows(IllegalArgumentException.class, () -> {
            Token token = new Token(10);
        });
    }

    @Test
    public void setPoints_ThrowsIllegalArgumentException () {
        assertThrows(IllegalArgumentException.class, () -> new Token(5).setPoints(100));
    }

}
