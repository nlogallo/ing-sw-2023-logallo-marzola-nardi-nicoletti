package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;

import java.util.ArrayList;

/**
 * Interface used to implements all scenes' controllers
 */

public interface GenericSceneController {

    /**
     * This method allows to set the GUI object in a Generic Scene Controller.
     *
     * @param gui
     */
    public void setGui(GUIView gui);

    /**
     * This method allows to init all the data used in a Generic Scene Controllers passed by the parameters from another Controller
     *
     * @param parameters is the list of parameters
     */
    public void initData(ArrayList<Object> parameters);
}
