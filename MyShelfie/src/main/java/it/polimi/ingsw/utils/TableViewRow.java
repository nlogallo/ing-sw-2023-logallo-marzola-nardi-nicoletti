package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.MyShelfieClient;
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

public class TableViewRow {

    private int id;
    private String participants;
    private Button join;
    private Button delete;
    private String nickname;
    private boolean isCreator;
    private int protocol;
    private TableView tableView;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public Button getJoin() {
        return join;
    }

    public void setJoin(Button join) {
        this.join = join;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

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
