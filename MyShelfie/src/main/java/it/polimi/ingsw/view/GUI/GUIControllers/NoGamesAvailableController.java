package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class NoGamesAvailableController implements GenericSceneController, Initializable {

    private GUIView gui;
    @FXML
    private Button createGame;
    @FXML
    private Spinner spinner;

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4));
        spinner.getValueFactory().setValue(2);
        createGame.addEventHandler(MouseEvent.MOUSE_RELEASED, this::createGame);
    }

    private void createGame(Event event){
        SceneController.changeScene(gui, "LobbyStage.fxml");
    }
}
