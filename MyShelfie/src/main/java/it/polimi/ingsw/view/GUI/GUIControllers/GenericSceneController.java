package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;

import java.util.ArrayList;

/**
 * Interface used to implements all scenes' controllers
 */

public interface GenericSceneController {
    public void setGui(GUIView gui);

    public void initData(ArrayList<Object> parameters);
}
