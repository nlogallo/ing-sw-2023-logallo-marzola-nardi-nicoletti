package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.MyShelfieClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents the controller of the EndGameScene
 * This class allows the user, when the previous Game is ended, to play again or exit
 */
public class EndGameSceneController implements GenericSceneController, Initializable {

    private GUIView gui;

    @FXML
    private Label label0;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label points0;

    @FXML
    private Label points1;

    @FXML
    private Label points2;

    @FXML
    private Label points3;

    @FXML
    private Label position0;

    @FXML
    private Label position1;

    @FXML
    private Label position2;

    @FXML
    private Label position3;

    @FXML
    private Button replayButton;

    @FXML
    private Button exitButton;

    private String finalMessage;
    private final Object lock = new Object();
    private int protocol;
    private String clientNickname;

    /**
     * Class Constructor
     */
    public EndGameSceneController () {}


    /**
     * Setter method
     * @param gui is the specific GUI
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
    }


    /**
     * Override method of initData from GenericSceneController
     * @param parameters is the list of parameters
     */
    @Override
    public void initData(ArrayList<Object> parameters) {

        finalMessage = (String) parameters.get(0);
        this.gui = (GUIView) parameters.get(1);
        clientNickname = (String) parameters.get(2);
        protocol = (int) parameters.get(3);
        showFinalResult();
        replayButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::startANewGame);
        exitButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::endGame);

    }


    /**
     * This method close the window of the Game
     * @param event is the press of the exit button
     */
    private void endGame(Event event) {
        SceneController.closeStage();
    }


    /**
     * This method shows the final results of the Game
     */
    private void showFinalResult () {

        /* Final Message:
        gameResults = "Congratulations " + winner.getNickname() + " you won with " + playerPoints.get(maxPointIndex) + " points"
        gameResults += "\n" + (i+2) + ") " + player.getNickname() + ": " + playerPoints.get(maxPointIndex) + " points"
         */

        String [] differentLine = finalMessage.split("\n");

        //winner and winner Points
        String firstLine = differentLine[0];
        String [] divisionFirstLine = firstLine.split(" you won with ");
        String winner = divisionFirstLine[0].split(" ")[1];
        String winnerPoints = divisionFirstLine[1].split(" ")[0];


        ArrayList<String> otherPlayerAndPoints = new ArrayList<>();
        for (int i = 1; i < differentLine.length; i++) {
            otherPlayerAndPoints.add(splitMethod(differentLine[i]));
        }


        // associate labels to player and points
        switch(gui.getPlayersNickname().size()) {
            case 1 -> {
                position0.setOpacity(0);
                label0.setOpacity(0);
                points0.setOpacity(0);
                position1.setText("1");
                label1.setText(winner);
                points1.setText(winnerPoints);
                position2.setText("2");
                label2.setText(otherPlayerAndPoints.get(0).split(" : ")[0]);
                points2.setText(otherPlayerAndPoints.get(0).split(" : ")[1]);
                position3.setOpacity(0);
                label3.setOpacity(0);
                points3.setOpacity(0);
            }
            case 2,3 -> {
                position0.setText("1");
                label0.setText(winner);
                points0.setText(winnerPoints);
                position1.setText("2");
                label1.setText(otherPlayerAndPoints.get(0).split(" : ")[0]);
                points1.setText(otherPlayerAndPoints.get(0).split(" : ")[1]);
                position2.setText("3");
                label2.setText(otherPlayerAndPoints.get(1).split(" : ")[0]);
                points2.setText(otherPlayerAndPoints.get(1).split(" : ")[1]);
                if (gui.getPlayersNickname().size() == 3) {
                    position3.setText("4");
                    label3.setText(otherPlayerAndPoints.get(2).split(" : ")[0]);
                    points3.setText(otherPlayerAndPoints.get(2).split(" : ")[1]);
                } else {
                    position3.setOpacity(0);
                    label3.setOpacity(0);
                    points3.setOpacity(0);
                }
            }
        }
    }


    /**
     * This method is a splitter method
     * @param line is the specific line
     * @return the divided line
     */
    private String splitMethod (String line) {

        String [] divisionLine = line.split(": ");
        String player = divisionLine[0].split(" ")[1];
        String playerPoints = divisionLine[1].split(" ")[0];
        return player + " : " + playerPoints;

    }


    /**
     * This method allows to start a new Game when the previous Game is ended
     * @param event is the press of the button replayButton
     */
    private void startANewGame(Event event) {
        try {
            ArrayList<Object> parameters = new ArrayList<>();
            if (protocol == 1) {
                MyShelfieClient.TCPDoWantToPlayAgain(true);
                ArrayList<Object> recoverableGames = null;
                recoverableGames = MyShelfieClient.TCPCheckForRecoverableGames();
                if (!recoverableGames.isEmpty()) {
                    parameters.add(clientNickname);
                    parameters.add(recoverableGames);
                    parameters.add(protocol);
                    parameters.add("Already connected");
                    SceneController.changeScene("SavingsScene.fxml", parameters);
                } else {
                    if (MyShelfieClient.TCPCheckForAvailableGames().equals("newGame")) {
                        parameters.add("Already connected");
                        parameters.add(protocol);
                        parameters.add(clientNickname);
                        SceneController.changeScene("NoGamesAvailable.fxml", parameters);
                    } else {
                        parameters.add(protocol);
                        parameters.add(-1);
                        parameters.add(clientNickname);
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
                    parameters.add(clientNickname);
                    MyShelfieClient.RMISetIsRecovered(false);
                    SceneController.changeScene("NoGamesAvailable.fxml", parameters);
                } else if (game.getPlayers().size() == game.getPlayersNumber()) {
                    parameters.add(clientNickname);
                    parameters.add(recoverableGames);
                    parameters.add(protocol);
                    parameters.add("Already connected");
                    MyShelfieClient.RMISetIsRecovered(true);
                    SceneController.changeScene("SavingsScene.fxml", parameters);
                } else {
                    parameters.add(protocol);
                    game = MyShelfieClient.RMISetPlayer(game.getId());
                    parameters.add(game.getId());
                    parameters.add(clientNickname);
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
