package it.polimi.ingsw.utils.wrapperCustom;

/**
 * This class represents a Custom Wrapper class for the CommonGoal descriptions. It is necessary for reading correctly from commonGoalDescription.json file.
 */
public class DescriptionWrapper {
    private String description0;
    private String description1;
    private String description2;
    private String description3;
    private String description4;
    private String description5;
    private String description6;
    private String description7;
    private String description8;
    private String description9;
    private String description10;
    private String description11;

    /**
     * Constructor method, it's not called anywhere, it's just for creating a correct Java class
     */
    private DescriptionWrapper(){
        description0 = null;
        description1 = null;
        description2 = null;
        description3 = null;
        description4 = null;
        description5 = null;
        description6 = null;
        description7 = null;
        description8 = null;
        description9 = null;
        description10 = null;
        description11 = null;
    }

    /**
     * It returns the description of the first common goal
     * @return a String, that is the description
     */
    private String getDescription0() {
        return description0;
    }

    /**
     * It returns the description of the second common goal
     * @return a String, that is the description
     */
    private String getDescription1() {
        return description1;
    }

    /**
     * It returns the description of the third common goal
     * @return a String, that is the description
     */
    private String getDescription2() {
        return description2;
    }

    /**
     * It returns the description of the fourth common goal
     * @return a String, that is the description
     */
    private String getDescription3() {
        return description3;
    }

    /**
     * It returns the description of the fifth common goal
     * @return a String, that is the description
     */
    private String getDescription4() {
        return description4;
    }

    /**
     * It returns the description of the sixth common goal
     * @return a String, that is the description
     */
    private String getDescription5() {
        return description5;
    }

    /**
     * It returns the description of the seventh common goal
     * @return a String, that is the description
     */
    private String getDescription6() {
        return description6;
    }

    /**
     * It returns the description of the eighth common goal
     * @return a String, that is the description
     */
    private String getDescription7() {
        return description7;
    }

    /**
     * It returns the description of the ninth common goal
     * @return a String, that is the description
     */
    private String getDescription8() {
        return description8;
    }

    /**
     * It returns the description of the tenth common goal
     * @return a String, that is the description
     */
    private String getDescription9() {
        return description9;
    }

    /**
     * It returns the description of the eleventh common goal
     * @return a String, that is the description
     */
    private String getDescription10() {
        return description10;
    }

    /**
     * It returns the description of the twelfth common goal
     * @return a String, that is the description
     */
    private String getDescription11() {
        return description11;
    }

    /**
     * It gets the correct description associated to the CommonGoal id as a String.
     * @param id is the CommonGoal id
     * @return a String, that is a description of the card read from the json file
     * @throws IllegalStateException if the id is not in the correct range
     */
    public String getDescription(int id) throws IllegalStateException{
        switch (id){
            case 0 -> { return getDescription0(); }
            case 1 -> { return getDescription1(); }
            case 2 -> { return getDescription2(); }
            case 3 -> { return getDescription3(); }
            case 4 -> { return getDescription4(); }
            case 5 -> { return getDescription5(); }
            case 6 -> { return getDescription6(); }
            case 7 -> { return getDescription7(); }
            case 8 -> { return getDescription8(); }
            case 9 -> { return getDescription9(); }
            case 10 -> { return getDescription10(); }
            case 11 -> { return getDescription11(); }
            default -> throw new IllegalStateException("Unexpected id value: " + id);
        }
    }


}
