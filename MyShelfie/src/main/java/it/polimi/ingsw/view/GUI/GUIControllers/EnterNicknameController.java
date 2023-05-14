package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class EnterNicknameController implements GenericSceneController, Initializable {
    private GUIView gui;
    @FXML
    private Button sendNickname;
    @FXML
    private Text textUnderInput;
    @FXML
    private TextField nicknameText;

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendNickname.addEventHandler(MouseEvent.MOUSE_RELEASED, this::nicknameEntered);
    }

    private void nicknameEntered(Event event){
        textUnderInput.setText("This nickname is already taken");
        SceneController.changeScene(gui, "NoGamesAvailable.fxml");
    }
}
