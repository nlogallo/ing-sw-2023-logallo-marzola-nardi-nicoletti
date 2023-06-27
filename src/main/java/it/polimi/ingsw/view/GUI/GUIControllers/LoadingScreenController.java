package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents the Controller of the LoadingScreen Scene. It is the first scene in the GUI.
 * LoadingScreen is a static scene, made to start the game.
 */
public class LoadingScreenController implements GenericSceneController, Initializable {
    private GUIView gui;
    @FXML
    private Button button;

    /**
     * Override method of setGui in GenericSceneController
     *
     * @param gui is the gui to set
     */
    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    /**
     * Override method of initialize from Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       button.addEventHandler(MouseEvent.MOUSE_RELEASED, this::buttonClick);
    }

    /**
     * Override method of initData from GenericSceneController
     * @param parameters is the list of parameters
     */
    @Override
    public void initData(ArrayList<Object> parameters) {}

    /**
     * This method allows to load the next scene in the game
     * @param event is the event triggered by clicking the start button
     */
    private void buttonClick(Event event) {
        SceneController.getStage().close();
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
        primaryStage.setTitle("My Shelfie");
        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.DECORATED);
        SceneController.setStage(primaryStage);
        SceneController.changeScene(gui, "ConnectToServerStage.fxml");
    }



}
