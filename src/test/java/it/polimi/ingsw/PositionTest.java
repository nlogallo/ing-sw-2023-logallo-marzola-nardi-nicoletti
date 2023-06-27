package it.polimi.ingsw;

import it.polimi.ingsw.model.Position;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PositionTest {

    Position position1 = new Position(2, 6);
    Position position2 = new Position(4,5);
    Position position3 = new Position(1, 4);

    @Test
    public void setRow_correctBehaviour() {

        position1.setRow(4);
        position2.setRow(1);
        position3.setRow(2);

        assertEquals(position1.getRow(), 4);
        assertEquals(position2.getRow(), 1);
        assertEquals(position3.getRow(), 2);

    }

    @Test
    public void setColumn_correctBehaviour() {

        position1.setColumn(2);
        position2.setColumn(4);
        position3.setColumn(1);

        assertEquals(position1.getColumn(), 2);
        assertEquals(position2.getColumn(), 4);
        assertEquals(position3.getColumn(), 1);

    }

}
