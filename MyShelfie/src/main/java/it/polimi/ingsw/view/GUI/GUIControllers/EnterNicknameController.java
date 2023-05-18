package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.model.MyShelfieClient;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
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

    int protocolValue;

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initData(ArrayList<Object> parameters) {
        serverSocket.setText((String)parameters.get(0) + " : " + (String) parameters.get(1));
        protocolValue = (int) parameters.get(2);
        if((int) parameters.get(2) == 1)
            protocol.setText("TCP Socket");
        else
            protocol.setText("RMI");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendNickname.addEventHandler(MouseEvent.MOUSE_RELEASED, this::nicknameEntered);
        nicknameText.setOnMouseClicked(actionEvent -> {
            nicknameText.setStyle(null);
            infoMessage.setFill(Color.BLACK);
        });
    }

    private void nicknameEntered(Event event){
        if(nicknameText.getText().length() < 3 || nicknameText.getText().length() > 14) {
            nicknameText.setStyle("-fx-text-fill: #ff3131; -fx-border-color: #ff3131;");
        }
        try {
            String response = MyShelfieClient.checkNickname(nicknameText.getText(), protocolValue);
            switch (response){
                case "nicknameOk":
                    ArrayList<Object> parameters = new ArrayList<>();
                    parameters.add(serverSocket.getText());
                    parameters.add(protocolValue);
                    parameters.add(nicknameText.getText());
                    //gui.setNickname("nickname");
                    if(protocolValue == 1) {
                        if(MyShelfieClient.TCPCheckForAvailableGames().equals("newGame"))
                            SceneController.changeScene(gui, "NoGamesAvailable.fxml", parameters);
                        else
                            SceneController.changeScene(gui, "LobbyStage.fxml");
                    }
                    else{
                        if(MyShelfieClient.RMICheckForAvailableGame() == null)
                            SceneController.changeScene(gui, "NoGamesAvailable.fxml", parameters);
                        else
                            SceneController.changeScene(gui, "LobbyStage.fxml");
                    }
                    break;
                case "nicknameWrong":
                    infoMessage.setFill(Color.RED);
                    nicknameText.setStyle("-fx-text-fill: #ff3131; -fx-border-color: #ff3131;");
                    break;
                case "nicknameExists":
                    infoMessage.setText("This nickname already exists");
                    nicknameText.setStyle("-fx-text-fill: #ff3131; -fx-border-color: #ff3131;");
                    infoMessage.setFill(Color.RED);
                    break;
            }
        } catch (IOException | ClassNotFoundException ex) {
            Stage primaryStage = SceneController.getStage();
            Stage stage = new Stage();
            stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
            stage.setTitle("My Shelfie Connection Error");
            SceneController.setStage(stage);
            SceneController.changeScene(gui, "ErrorStage.fxml");
            SceneController.setStage(primaryStage);
        }
    }
}
