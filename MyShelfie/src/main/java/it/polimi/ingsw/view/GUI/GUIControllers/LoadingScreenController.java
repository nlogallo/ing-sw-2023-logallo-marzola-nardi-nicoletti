package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoadingScreenController implements GenericSceneController, Initializable {
    private GUIView gui;
    @FXML
    private Button button;

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       button.addEventHandler(MouseEvent.MOUSE_RELEASED, this::buttonClick);
    }

    @Override
    public void initData(ArrayList<Object> parameters) {}

    private void buttonClick(Event event) {
        SceneController.changeScene(gui, "ConnectToServerStage.fxml");
    }



}
