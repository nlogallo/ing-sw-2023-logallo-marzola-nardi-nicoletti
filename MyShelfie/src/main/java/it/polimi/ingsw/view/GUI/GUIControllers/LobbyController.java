package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LobbyController implements GenericSceneController, Initializable {

    private GUIView gui;
    @FXML
    private ProgressIndicator progressIndicator;

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeScene);
    }

    @Override
    public void initData(ArrayList<Object> parameters) {}

    private void closeScene(Event event){

    }
}
