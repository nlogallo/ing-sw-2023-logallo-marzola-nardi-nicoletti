package it.polimi.ingsw.model.wrapperCustom;

import it.polimi.ingsw.model.TileType;

/**
 * This class represents a Custom Wrapper class for the PersonalGoal Cards. It is necessary for reading correctly from personalGoals.json file.
 */
public class PersonalGoalWrapper {
    private TileType[][] matrix0;
    private TileType[][] matrix1;
    private TileType[][] matrix2;
    private TileType[][] matrix3;
    private TileType[][] matrix4;
    private TileType[][] matrix5;
    private TileType[][] matrix6;
    private TileType[][] matrix7;
    private TileType[][] matrix8;
    private TileType[][] matrix9;
    private TileType[][] matrix10;
    private TileType[][] matrix11;

    /**
     * Constructor method, it's not called anywhere, it's just for creating a correct Java class
     */
    private PersonalGoalWrapper(){
        matrix0 = null;
        matrix1 = null;
        matrix2 = null;
        matrix3 = null;
        matrix4 = null;
        matrix5 = null;
        matrix6 = null;
        matrix7 = null;
        matrix8 = null;
        matrix9 = null;
        matrix10 = null;
        matrix11 = null;
    }

    private TileType[][] getMatrix0() {
        return matrix0;
    }

    private TileType[][] getMatrix1() {
        return matrix1;
    }

    private TileType[][] getMatrix2() {
        return matrix2;
    }

    private TileType[][] getMatrix3() {
        return matrix3;
    }

    private TileType[][] getMatrix4() {
        return matrix4;
    }

    private TileType[][] getMatrix5() {
        return matrix5;
    }

    private TileType[][] getMatrix6() {
        return matrix6;
    }

    private TileType[][] getMatrix7() {
        return matrix7;
    }

    private TileType[][] getMatrix8() {
        return matrix8;
    }

    private TileType[][] getMatrix9() {
        return matrix9;
    }

    private TileType[][] getMatrix10() {
        return matrix10;
    }

    private TileType[][] getMatrix11() {
        return matrix11;
    }

    /**
     * It's the only important method of the class, it's important to get the correct TileType matrix associated with the card.
     * @param id is the CommonGoal id
     * @return a matrix of TileType
     * @throws IllegalStateException if the id is not in the correct range
     */
    public TileType[][] getMatrix(int id) throws IllegalStateException{
        switch (id) {
            case 0 -> { return getMatrix0(); }
            case 1 -> { return getMatrix1(); }
            case 2 -> { return getMatrix2(); }
            case 3 -> { return getMatrix3(); }
            case 4 -> { return getMatrix4(); }
            case 5 -> { return getMatrix5(); }
            case 6 -> { return getMatrix6(); }
            case 7 -> { return getMatrix7(); }
            case 8 -> { return getMatrix8(); }
            case 9 -> { return getMatrix9(); }
            case 10 -> { return getMatrix10(); }
            case 11 -> {return getMatrix11();}
            default -> throw new IllegalStateException("Unexpected id value: " + id);
        }
    }




}
