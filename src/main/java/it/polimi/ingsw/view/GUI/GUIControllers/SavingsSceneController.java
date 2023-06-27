package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.MyShelfieClient;
import it.polimi.ingsw.utils.TableViewRow;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents the Controller of the Savings Scene
 * SavingScene allows the user to select a recoverable game (recoverable means that is not ended due to Server crashes).The user can choose to join it or if it was the creator to delete it.
 * If it doesn't want to join a recoverable game, it can select to join a new game according to the specifics.
 */
public class SavingsSceneController implements GenericSceneController, Initializable {

    @FXML
    private TableView table;
    @FXML
    private Button newGame;
    private ArrayList<Object> recoverableGames;
    private String nickname;
    private int protocolValue;
    private String serverSocket;
    private GUIView gui;

    /**
     * Override method of setGui in GenericSceneController
     * @param gui is the gui to set
     */
    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    /**
     * Override method of initData from GenericSceneController
     * @param parameters is the list of parameters
     */
    @Override
    public void initData(ArrayList<Object> parameters) {
        nickname = (String) parameters.get(0);
        protocolValue = (Integer) parameters.get(2);
        serverSocket = (String) parameters.get(3);
        MyShelfieClient.setRemoteNickname(nickname);
        ObservableList<TableColumn> columns = table.getColumns();
        columns.get(0).setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        columns.get(1).setCellValueFactory(
                new PropertyValueFactory<>("participants")
        );
        columns.get(2).setCellValueFactory(
                new PropertyValueFactory<>("join")
        );
        columns.get(3).setCellValueFactory(
                new PropertyValueFactory<>("delete")
        );
        recoverableGames = (ArrayList<Object>) parameters.get(1);
        ArrayList<String> rows = new ArrayList<>();
        Game game;
        for(int j = 0; j < recoverableGames.size(); j++) {
            game = (Game) recoverableGames.get(j);
            ArrayList<String> nicknames = new ArrayList<>();
            for (int i = 0; i < game.getPlayers().size(); i++)
                nicknames.add(game.getPlayers().get(i).getNickname());
            ArrayList<String> listOfNicknames = new ArrayList<>();
            for (int i = 0; i < game.getPlayers().size(); i++)
                listOfNicknames.add(game.getPlayers().get(i).getNickname());
            boolean creator;
            if(game.getPlayers().get(0).getNickname().equals(parameters.get(0)))
                creator = true;
            else
                creator = false;
            TableViewRow tableViewRow = new TableViewRow(game.getId(), listOfNicknames, nickname, creator, protocolValue, table);
            table.getItems().add(tableViewRow);
        }
    }

    /**
     * Override method of initialize from Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, this::joinGame);
    }

    /**
     * This method allows the user to join a new game even if there are recoverable games. It makes a call to the Server (via Socket or RMI) asking to join a new game.
     * @param event is the event triggered by clicking the Join a new Game button
     */
    private void joinGame(Event event){
        ArrayList<Object> parameters = new ArrayList<>();
        try {
            if (protocolValue == 1) {
                MyShelfieClient.TCPDoWantToRecover("NEWGAME", -1);
                if (MyShelfieClient.TCPCheckForAvailableGames().equals("newGame")) {
                    parameters.add(serverSocket);
                    parameters.add(protocolValue);
                    parameters.add(nickname);
                    SceneController.changeScene("NoGamesAvailable.fxml", parameters);
                } else {
                    parameters.add(protocolValue);
                    parameters.add(-1);
                    parameters.add(nickname);
                    SceneController.changeScene("LobbyStage.fxml", parameters);
                }
            } else {
                Game game = MyShelfieClient.RMIDoWantToRecover("NEWGAME", -1);
                if (game == null) {
                    parameters.add(serverSocket);
                    parameters.add(protocolValue);
                    parameters.add(nickname);
                    MyShelfieClient.RMISetIsRecovered(false);
                    SceneController.changeScene("NoGamesAvailable.fxml", parameters);
                } else {
                    parameters.add(protocolValue);
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
