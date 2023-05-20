package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.model.MyShelfieClient;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LobbyController implements GenericSceneController, Initializable {

    private GUIView gui;
    private int protocol;
    private int gameId;
    private String nickname;
    private boolean mutex = false;
    @FXML
    private ProgressIndicator progressIndicator;

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        new Thread(this::gameStarting).start();
    }

    @Override
    public void initData(ArrayList<Object> parameters) {
        protocol = (int) parameters.get(0);
        gameId = (int) parameters.get(1);
        nickname = (String) parameters.get(2);
        mutex = true;
    }

    private void gameStarting(){
        while(!mutex){}
        if(protocol == 1){
            String command = "";
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
                    if (MyShelfieClient.RMICheckForGameStart(gameId, true)) {
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
