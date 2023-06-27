
package it.polimi.ingsw;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.model.TileType;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalTest {
    PersonalGoal personalGoal;

    @Before
    public void setUp(){
        TileType[][] PersonalGoal1 = {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        personalGoal = new PersonalGoal(PersonalGoal1);
        personalGoal.setId(1);
    }

    @Test
    void checkPersonalGoal_CorrectBehavior_AssertSame() {
        setUp();
        TileType[][] playerShelf1 = {{TileType.PLANT, TileType.FRAME, TileType.FRAME, TileType.CAT, TileType.EMPTY},{TileType.EMPTY, TileType.CAT, TileType.PLANT, TileType.EMPTY, TileType.CAT},{TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.PLANT},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] playerShelf2 = {{TileType.CAT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] playerShelf3 = {{TileType.CAT, TileType.FRAME, TileType.CAT, TileType.CAT, TileType.EMPTY},{TileType.EMPTY, TileType.CAT, TileType.PLANT, TileType.EMPTY, TileType.EMPTY},{TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.PLANT},{TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] playerShelf4 = {{TileType.PLANT, TileType.FRAME, TileType.CAT, TileType.CAT, TileType.EMPTY},{TileType.EMPTY, TileType.CAT, TileType.PLANT, TileType.EMPTY, TileType.EMPTY},{TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.PLANT},{TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] playerShelf5 = {{TileType.PLANT, TileType.FRAME, TileType.FRAME, TileType.CAT, TileType.EMPTY},{TileType.EMPTY, TileType.CAT, TileType.PLANT, TileType.EMPTY, TileType.EMPTY},{TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.PLANT},{TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY}};;
        TileType[][] playerShelf6 = {{TileType.PLANT, TileType.FRAME, TileType.FRAME, TileType.CAT, TileType.EMPTY},{TileType.EMPTY, TileType.CAT, TileType.PLANT, TileType.EMPTY, TileType.CAT},{TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.PLANT},{TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY}};
        TileType[][] playerShelf7 = {{TileType.PLANT, TileType.FRAME, TileType.FRAME, TileType.CAT, TileType.EMPTY},{TileType.EMPTY, TileType.CAT, TileType.PLANT, TileType.EMPTY, TileType.CAT},{TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.PLANT},{TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.CAT, TileType.EMPTY, TileType.EMPTY}};

        assertSame(12, personalGoal.checkPersonalGoal(playerShelf1));
        assertSame(9, personalGoal.checkPersonalGoal(playerShelf2));
        assertSame(0, personalGoal.checkPersonalGoal(playerShelf3));
        assertSame(1, personalGoal.checkPersonalGoal(playerShelf4));
        assertSame(2, personalGoal.checkPersonalGoal(playerShelf5));
        assertSame(4, personalGoal.checkPersonalGoal(playerShelf6));
        assertSame(6, personalGoal.checkPersonalGoal(playerShelf7));
    }
    @Test
    public void checkPersonalGoal_GetId_AssertEquals(){
        setUp();
        assertEquals(personalGoal.getId(), 1);
    }
    @Test
    public void checkPersonalGoal_GetMatrix_AssertEquals(){
        setUp();
        assertEquals(personalGoal.getMatrix(), personalGoal.getMatrix());
    }

}