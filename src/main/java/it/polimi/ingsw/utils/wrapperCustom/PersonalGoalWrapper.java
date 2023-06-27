package it.polimi.ingsw.utils.wrapperCustom;

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

    /**
     * It returns the matrix of the first personal goal
     * @return a TileType[][], that is the matrix
     */
    private TileType[][] getMatrix0() {
        return matrix0;
    }

    /**
     * It returns the matrix of the second personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix1() {
        return matrix1;
    }

    /**
     * It returns the matrix of the third personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix2() {
        return matrix2;
    }

    /**
     * It returns the matrix of the fourth personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix3() {
        return matrix3;
    }

    /**
     * It returns the matrix of the fifth personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix4() {
        return matrix4;
    }

    /**
     * It returns the matrix of the sixth personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix5() {
        return matrix5;
    }

    /**
     * It returns the matrix of the seventh personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix6() {
        return matrix6;
    }

    /**
     * It returns the matrix of the eighth personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix7() {
        return matrix7;
    }

    /**
     * It returns the matrix of the ninth personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix8() {
        return matrix8;
    }

    /**
     * It returns the matrix of the tenth personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix9() {
        return matrix9;
    }

    /**
     * It returns the matrix of the eleventh personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix10() {
        return matrix10;
    }

    /**
     * It returns the matrix of the twelfth personal goal
     * @return a TileType[][] that is the matrix
     */
    private TileType[][] getMatrix11() {
        return matrix11;
    }

    /**
     * It gets the correct TileType matrix associated with the card.
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
