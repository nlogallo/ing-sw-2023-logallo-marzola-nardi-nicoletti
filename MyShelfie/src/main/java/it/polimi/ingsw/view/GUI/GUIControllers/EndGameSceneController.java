package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.GUIView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


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

    public EndGameSceneController () {}

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            showFinalResult();
            replayButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::startANewGame);
            exitButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::endGame);
        }
    }


    @Override
    public void initData(ArrayList<Object> parameters) {
        synchronized (lock) {
            finalMessage = (String) parameters.get(0);
            lock.notifyAll();
        }
    }


    private void startANewGame(Event event) {
        //to be completed
    }

    private void endGame(Event event) {
        //to be completed
    }



    private void showFinalResult () {

        /* Final Message:
        gameResults = "Congratulations " + winner.getNickname() + " you won with " + playerPoints.get(maxPointIndex) + " points";
        gameResults += "\n" + (i+2) + ") " + player.getNickname() + ": " + playerPoints.get(maxPointIndex) + " points";
         */

        String [] differentLine = finalMessage.split(";");

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
        position0.setText("1");
        switch(gui.getPlayersNickname().size()) {
            case 1 -> {
                label1.setText(winner);
                points1.setText(winnerPoints);
                position2.setText("2");
                label2.setText(otherPlayerAndPoints.get(0).split(" : ")[0]);
                points2.setText(otherPlayerAndPoints.get(0).split(" : ")[1]);
            }
            case 2,3 -> {
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
                }
            }
        }
    }


    private String splitMethod (String line) {

        String [] divisionLine = line.split(": ");
        String player = divisionLine[0].split(" ")[1];
        String playerPoints = divisionLine[1].split(" ")[0];
        return player + " : " + playerPoints;

    }
}