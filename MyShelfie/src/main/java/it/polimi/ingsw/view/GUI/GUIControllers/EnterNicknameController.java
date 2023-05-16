package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EnterNicknameController implements GenericSceneController, Initializable {
    private GUIView gui;
    @FXML
    private Button sendNickname;
    @FXML
    private Text infoMessage;
    @FXML
    private TextField nicknameText;
    @FXML
    private Text serverSocket;
    @FXML
    private Text protocol;

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initData(ArrayList<Object> parameters) {
        serverSocket.setText((String)parameters.get(0) + " : " + (String) parameters.get(1));
        if((int) parameters.get(2) == 1)
            protocol.setText("TCP Socket");
        else
            protocol.setText("RMI");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendNickname.addEventHandler(MouseEvent.MOUSE_RELEASED, this::nicknameEntered);
    }

    private void nicknameEntered(Event event){
        nicknameText.setStyle(null);
        if(nicknameText.getText().length() < 3 || nicknameText.getText().length() > 14) {
            nicknameText.setStyle("-fx-text-fill: #ff3131; -fx-border-color: #ff3131;");
        }
        //infoMessage.setText("This nickname is already taken");
        //SceneController.changeScene(gui, "NoGamesAvailable.fxml");
    }
}
