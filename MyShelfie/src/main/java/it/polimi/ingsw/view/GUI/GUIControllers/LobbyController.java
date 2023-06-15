package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.MyShelfieClient;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents the Controller of the Lobby Scene
 * LobbyScene is "the waiting room" scene of the GUI. The user needs to wait there until the game starts.
 */
public class LobbyController implements GenericSceneController, Initializable {

    private GUIView gui;
    private int protocol;
    private int gameId;
    private String nickname;
    private boolean mutex = false;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Text text;

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
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    }

    /**
     * Override method of initData from GenericSceneController
     * @param parameters is the list of parameters
     */
    @Override
    public void initData(ArrayList<Object> parameters) {
        protocol = (int) parameters.get(0);
        gameId = (int) parameters.get(1);
        nickname = (String) parameters.get(2);
        mutex = true;
        text.setText("Hi " + nickname + " wait here until the game is fullfilled");
        new Thread(this::gameStarting).start();
    }

    /**
     * This method waits a message from the Server to start the game (if everything is ok) or to show an error. When it receives it load the main scene of the game.
     */
    private void gameStarting(){
        if(protocol == 1){
            String command = "";
            try {
                gameId = MyShelfieClient.TCPGetGameId();
            } catch (IOException | ClassNotFoundException e) {
                Stage primaryStage = SceneController.getStage();
                Stage stage = new Stage();
                stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
                stage.setTitle("My Shelfie Connection Error");
                SceneController.setStage(stage);
                SceneController.changeScene(gui, "ErrorStage.fxml");
                SceneController.setStage(primaryStage);
            }
            while (true) {
                try {
                    command = MyShelfieClient.TCPCheckForGameStart();
                } catch (IOException | ClassNotFoundException e) {
                    Stage primaryStage = SceneController.getStage();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
                    stage.setTitle("My Shelfie Connection Error");
                    SceneController.setStage(stage);
                    SceneController.changeScene(gui, "ErrorStage.fxml");
                    SceneController.setStage(primaryStage);
                }
                if (command.equals("startGame")) {
                    new MyShelfieClient().handleGameTCP(nickname);
                    break;
                }
            }
        }else{
            while (true) {
                try {
                    if (MyShelfieClient.RMICheckForGameStart(gameId).equals("startGame")) {
                        new MyShelfieClient().handleGameRMI(MyShelfieClient.RMIGetGame(gameId), nickname);
                        break;
                    }
                } catch (RemoteException e) {
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
    }




}
