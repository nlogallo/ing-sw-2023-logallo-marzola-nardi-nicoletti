package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents the Controller of the Error Scene
 * ErrorScene is thrown when the Server crashed
 */
public class ErrorSceneController implements GenericSceneController, Initializable {

    @FXML
    Button button;

    /**
     * Override method of setGui in GenericSceneController
     *
     * @param gui is the gui to set, in this scene we don't have it
     */
    @Override
    public void setGui(GUIView gui) {
    }

    /**
     * Override method of initData from GenericSceneController
     * @param parameters is the list of parameters
     */
    @Override
    public void initData(ArrayList<Object> parameters) {
    }

    /**
     * Override method of initialize from Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            SceneController.closeStage();
        });
    }
}
