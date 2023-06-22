package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.MyShelfieClient;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents the Controller of the NoGamesAvailable Scene
 * NoGamesAvailable allows the user to create a new game. The user must select the number of players to play with. It is loaded only if there weren't available games to join.
 */
public class NoGamesAvailableController implements GenericSceneController, Initializable {

    private GUIView gui;
    @FXML
    private Button createGame;
    @FXML
    private Spinner spinner;
    @FXML
    private Text serverSocket;
    @FXML
    private Text protocol;
    @FXML
    private Text nickname;

    /**
     * Override method of setGui in GenericSceneController
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
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4));
        spinner.getValueFactory().setValue(2);
        createGame.addEventHandler(MouseEvent.MOUSE_RELEASED, this::createGame);
    }

    /**
     * Override method of initData from GenericSceneController
     * @param parameters is the list of parameters
     */
    @Override
    public void initData(ArrayList<Object> parameters) {
        serverSocket.setText((String) parameters.get(0));
        int protocolValue = (int) parameters.get(1);
        if(protocolValue == 1)
            protocol.setText("TCP Socket");
        else
            protocol.setText("RMI");
        nickname.setText((String) parameters.get(2));
    }

    /**
     * This method creates makes a call to the Server (via RMI or Socket) to create a new Game. This controller is called only if there aren't games not fulfilled in the Server
     * @param event is the event triggered by clicking the Create Button
     */
    private void createGame(Event event){
        ArrayList<Object> parameters = new ArrayList<>();
        if(protocol.getText().equals("TCP Socket")){
            parameters.add(1);
            try {
                MyShelfieClient.TCPSetPlayersNumber((int)spinner.getValue());
                parameters.add(-1);
            } catch (IOException e) {
                Stage primaryStage = SceneController.getStage();
                primaryStage.close();
                Stage stage = new Stage();
                stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
                stage.setTitle("My Shelfie Connection Error");
                SceneController.setStage(stage);
                SceneController.changeScene("ErrorStage.fxml");
                return;
            }
        }
        else {
            try {
                parameters.add(2);
                Game game = MyShelfieClient.RMIHandleGameCreation((int) spinner.getValue());
                parameters.add(game.getId());
            } catch (RemoteException e) {
                Stage primaryStage = SceneController.getStage();
                primaryStage.close();
                Stage stage = new Stage();
                stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
                stage.setTitle("My Shelfie Connection Error");
                SceneController.setStage(stage);
                SceneController.changeScene("ErrorStage.fxml");
                return;
            }
        }
        parameters.add(nickname.getText());
        SceneController.changeScene("LobbyStage.fxml", parameters);
    }
}
