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

public class ConnectionTypeController implements GenericSceneController, Initializable {

    private GUIView gui;
    @FXML
    private Button socketButton;
    @FXML
    private Button rmiButton;

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socketButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::socketClicked);
        rmiButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::rmiClicked);
    }

    private void socketClicked(Event event){
        SceneController.changeScene(gui, "EnterNicknameStage.fxml");
    }

    private void rmiClicked(Event event){
        SceneController.changeScene(gui, "EnterNicknameStage.fxml");
    }
}
