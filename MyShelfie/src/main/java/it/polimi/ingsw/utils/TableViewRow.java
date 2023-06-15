package it.polimi.ingsw.utils;

import it.polimi.ingsw.MyShelfieClient;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * TableViewRow is a class used to fill the TableView in the Savings Scene (GUI). This class allows to show all the infos about the game row by row.
 */

public class TableViewRow {

    private int id;
    private String participants;
    private Button join;
    private Button delete;
    private String nickname;
    private boolean isCreator;
    private int protocol;
    private TableView tableView;

    /**
     * Constructor method
     * @param id is the game id as
     * @param list is the list of players' nickname
     * @param nickname is the nickname of the local Client
     * @param isCreator is a boolean that said if the local Client has created this specific Game. If it is true it can delete the game, otherwise no.
     * @param protocol is the protocol chosen by the user (1 for Socket or 2 for RMI)
     * @param tableView is the TableView taken from the fxmlFiles
     */
    public TableViewRow(int id, ArrayList<String> list, String nickname, boolean isCreator, int protocol, TableView tableView){
        this.id = id;
        this.participants = String.join(", ", list);
        this.nickname = nickname;
        delete = new Button();
        delete.setText("Delete");
        join = new Button();
        join.setText("Join");
        this.isCreator = isCreator;
        if(!isCreator) {
            delete.setDisable(true);
        }
        else
            delete.addEventHandler(MouseEvent.MOUSE_CLICKED, this::deleteRestorableGame);
        this.protocol = protocol;
        join.addEventHandler(MouseEvent.MOUSE_CLICKED, this::joinRestorableGame);
        this.tableView = tableView;
    }

    /**
     * Id getter method
     * @return the game id as an int
     */
    public int getId() {
        return id;
    }

    /**
     * Id setter method
     * @param id is the game id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Participants getter method (not used, but it is necessary to build the TableView without exceptions)
     * @return a String of all the participants divided by commas
     */
    public String getParticipants() {
        return participants;
    }

    /**
     * Participants setter method (not used, but it is necessary to build the TableView without exceptions)
     * @param participants is a String of all the participants divided by commas
     */
    public void setParticipants(String participants) {
        this.participants = participants;
    }

    /**
     * Join Button getter method (not used, but it is necessary to build the TableView without exceptions)
     * @return the Join Button
     */
    public Button getJoin() {
        return join;
    }

    /**
     * Join Button setter method (not used, but it is necessary to build the TableView without exceptions)
     * @param join is the Join Button
     */
    public void setJoin(Button join) {
        this.join = join;
    }

    /**
     * Delete Button getter method (not used, but it is necessary to build the TableView without exceptions)
     * @return the Delete Button
     */
    public Button getDelete() {
        return delete;
    }

    /**
     * Delete Button setter method (not used, but it is necessary to build the TableView without exceptions)
     * @param delete is the Delete Button
     */
    public void setDelete(Button delete) {
        this.delete = delete;
    }

    /**
     * This private method makes the call via Socket or RMI to the Server, to get the list of recoverable games
     * @param event is the event triggered by clicking the previous scene button
     */
    private void joinRestorableGame(Event event){
        if(protocol == 1){
            try {
                MyShelfieClient.TCPDoWantToRecover("RECGAME", id);
                MyShelfieClient.TCPCheckForAvailableGames();
            } catch (IOException | ClassNotFoundException e) {
                Stage primaryStage = SceneController.getStage();
                Stage stage = new Stage();
                stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
                stage.setTitle("My Shelfie Connection Error");
                SceneController.setStage(stage);
                SceneController.changeScene("ErrorStage.fxml");
                SceneController.setStage(primaryStage);
            }
        }
        else {
            try {
                MyShelfieClient.RMIDoWantToRecover("RECGAME", id);
            } catch (RemoteException e) {
                Stage primaryStage = SceneController.getStage();
                Stage stage = new Stage();
                stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
                stage.setTitle("My Shelfie Connection Error");
                SceneController.setStage(stage);
                SceneController.changeScene("ErrorStage.fxml");
                SceneController.setStage(primaryStage);
            }
        }
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(protocol);
        parameters.add(id);
        parameters.add(nickname);
        SceneController.changeScene("LobbyStage.fxml", parameters);
    }

    /**
     * This method makes a call to the Server (via Socket or RMI) to delete a game. It deletes the row in the local Client. Be aware that this method can be called only if the local user created that game.
     * @param event is the event triggered by clicking the Delete Button
     */
    private void deleteRestorableGame(Event event){
        if(protocol == 1){
            try {
                MyShelfieClient.TCPDoWantToRecover("DELGAME", id);
            } catch (IOException | ClassNotFoundException e) {
                Stage primaryStage = SceneController.getStage();
                Stage stage = new Stage();
                stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
                stage.setTitle("My Shelfie Connection Error");
                SceneController.setStage(stage);
                SceneController.changeScene("ErrorStage.fxml");
                SceneController.setStage(primaryStage);
            }
        }
        else {
            try {
                MyShelfieClient.RMIDoWantToRecover("DELGAME", id);
            } catch (RemoteException e) {
                Stage primaryStage = SceneController.getStage();
                Stage stage = new Stage();
                stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
                stage.setTitle("My Shelfie Connection Error");
                SceneController.setStage(stage);
                SceneController.changeScene("ErrorStage.fxml");
                SceneController.setStage(primaryStage);
            }
        }
        tableView.getItems().remove(this);
    }
}
