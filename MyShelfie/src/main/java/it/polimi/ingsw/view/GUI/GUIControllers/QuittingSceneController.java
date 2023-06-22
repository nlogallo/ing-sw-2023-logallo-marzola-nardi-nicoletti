package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.MyShelfieClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents the Controller of the Error Scene
 * ErrorScene is thrown when the Server crashed
 */
public class QuittingSceneController implements GenericSceneController, Initializable {

    @FXML
    Button quit;
    @FXML
    Button play;
    private int protocol;
    private String nickname;


    /**
     * Override method of setGui in GenericSceneController
     *
     * @param gui is the gui to set, in this scene we don't have it
     */
    @Override
    public void setGui(GUIView gui) {
    }

    /**
     * Override method of initData from GenericSceneController
     * @param parameters is the list of parameters
     */
    @Override
    public void initData(ArrayList<Object> parameters) {
        this.protocol = (int) parameters.get(0);
        this.nickname = (String) parameters.get(1);
    }

    /**
     * Override method of initialize from Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       quit.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
           SceneController.closeStage();
       });
       play.addEventHandler(MouseEvent.MOUSE_CLICKED, this::playAgain);
    }

    private void playAgain(Event event) {
        try {
            ArrayList<Object> parameters = new ArrayList<>();
            if (protocol == 1) {
                MyShelfieClient.TCPDoWantToPlayAgain(true);
                ArrayList<Object> recoverableGames = null;
                recoverableGames = MyShelfieClient.TCPCheckForRecoverableGames();
                if (!recoverableGames.isEmpty()) {
                    parameters.add(nickname);
                    parameters.add(recoverableGames);
                    parameters.add(protocol);
                    parameters.add("Already connected");
                    SceneController.changeScene("SavingsScene.fxml", parameters);
                } else {
                    if (MyShelfieClient.TCPCheckForAvailableGames().equals("newGame")) {
                        parameters.add("Already connected");
                        parameters.add(protocol);
                        parameters.add(nickname);
                        SceneController.changeScene("NoGamesAvailable.fxml", parameters);
                    } else {
                        parameters.add(protocol);
                        parameters.add(-1);
                        parameters.add(nickname);
                        SceneController.changeScene("LobbyStage.fxml", parameters);
                    }
                }
            } else {
                ArrayList<Object> recoverableGames = MyShelfieClient.RMICheckForAvailableGame();
                Game game = null;
                if (!recoverableGames.isEmpty())
                    game = (Game) recoverableGames.get(0);
                if (game == null) {
                    parameters.add("Already connected");
                    parameters.add(protocol);
                    parameters.add(nickname);
                    MyShelfieClient.RMISetIsRecovered(false);
                    SceneController.changeScene("NoGamesAvailable.fxml", parameters);
                } else if (game.getPlayers().size() == game.getPlayersNumber()) {
                    parameters.add(nickname);
                    parameters.add(recoverableGames);
                    parameters.add(protocol);
                    parameters.add("Already connected");
                    MyShelfieClient.RMISetIsRecovered(true);
                    SceneController.changeScene("SavingsScene.fxml", parameters);
                } else {
                    parameters.add(protocol);
                    game = MyShelfieClient.RMISetPlayer(game.getId());
                    parameters.add(game.getId());
                    parameters.add(nickname);
                    MyShelfieClient.RMISetIsRecovered(false);
                    SceneController.changeScene("LobbyStage.fxml", parameters);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Stage primaryStage = SceneController.getStage();
            primaryStage.close();
            Stage stage = new Stage();
            stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
            stage.setTitle("My Shelfie Connection Error");
            SceneController.setStage(stage);
            SceneController.changeScene("ErrorStage.fxml");
        }
    }

}
