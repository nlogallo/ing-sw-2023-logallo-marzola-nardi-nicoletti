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
    public void Test1_correctInput_correctOutput () {

        Token token = new Token(8);
        assertEquals(token.getPoints(), 8, 0);
        assertEquals(token.getType(), TokenType.SCORING);
    }

    @Test
    public void Test2_correctInput_correctOutput () {
        Token token = new Token(0);
        assertEquals(token.getPoints(), 1, 0);
        assertEquals(token.getType(), TokenType.END_GAME);
    }

    @Test (expected = IllegalArgumentException.class)
    public void Test3_overMaxID_throwIllegalArgumentException () {
        Token token = new Token(10);
    }

    @Test
    public void Test4_setPoints_overMaxID_throwIllegalArgumentException () {
        assertThrows(IllegalArgumentException.class, () -> new Token(5).setPoints(100));
    }

    @Test
    public void Test5_correctInput_correctOutput() {
        Token token = new Token(3);
        assertEquals(token.getPoints(), 4, 0);
        assertEquals(token.getType(), TokenType.SCORING);
    }

    @Test
    public void Test6_correctInput_correctOutput() {
        Token token = new Token(6);
        assertEquals(token.getPoints(), 6, 0);
        assertEquals(token.getType(), TokenType.SCORING);
    }

}
