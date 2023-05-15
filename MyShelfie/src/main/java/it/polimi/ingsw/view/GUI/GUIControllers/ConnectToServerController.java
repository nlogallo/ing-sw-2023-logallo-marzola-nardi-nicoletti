package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectToServerController implements GenericSceneController, Initializable {

    private GUIView gui;

    @FXML
    private Button connectButton;

    @FXML
    private Button quitButton;

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::connectOnClick);
        quitButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::quitOnClick);
    }

    private void quitOnClick(Event event){
        SceneController.closeStage();
    }

    private void connectOnClick(Event event){
        SceneController.changeScene(gui, "ConnectionTypeStage.fxml");
    }


}
