package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.model.MyShelfieClient;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectToServerController implements GenericSceneController, Initializable {

    private GUIView gui;

    @FXML
    private ToggleButton toggleSocket;
    @FXML
    private ToggleButton toggleRMI;
    @FXML
    private Button connectButton;
    @FXML
    private Button quitButton;
    @FXML
    private TextField IPText;
    @FXML
    private TextField portText;
    @FXML
    private Text labelIP;
    @FXML
    private Text labelPort;

    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::connectOnClick);
        quitButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::quitOnClick);
        toggleSocket.setOnAction(actionEvent -> {
            toggleRMI.setSelected(false);
        });
        toggleRMI.setOnAction(actionEvent -> {
            toggleSocket.setSelected(false);
        });
    }

    @Override
    public void initData(ArrayList<Object> parameters) {}

    private void quitOnClick(Event event){
        SceneController.closeStage();
    }

    private void connectOnClick(Event event){
        labelIP.setText("Server IP:");
        labelPort.setText("Port:");
        IPText.setStyle(null);
        portText.setStyle(null);
        if(!toggleSocket.isSelected() && !toggleRMI.isSelected()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You have to select the protocol");
            alert.setTitle("My Shelfie Alert");;
            alert.showAndWait();
        }
        else {
            final String ipPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
            Pattern pattern = Pattern.compile(ipPattern);
            Matcher matcher = pattern.matcher(IPText.getText());
            if (!matcher.matches()) {
                labelIP.setText("Invalid IP");
                IPText.setStyle("-fx-text-fill: #ff3131; -fx-border-color: #ff3131;");
            } else {
                int portNumber;
                try {
                    portNumber = Integer.parseInt(portText.getText());
                    int protocol;
                    if (toggleSocket.isSelected())
                        protocol = 1;
                    else
                        protocol = 2;
                    boolean connectionStatus = MyShelfieClient.connect(IPText.getText(), portNumber, protocol);
                    if (connectionStatus) {
                        ArrayList<Object> parameters = new ArrayList<>();
                        parameters.add(IPText.getText());
                        parameters.add(portText.getText());
                        parameters.add(protocol);
                        SceneController.changeScene(gui, "EnterNicknameStage.fxml", parameters);
                    }
                } catch (NumberFormatException ex) {
                    labelPort.setText("Invalid Port");
                    portText.setStyle("-fx-text-fill: #ff3131; -fx-border-color: #ff3131;");
                }
            }
        }
    }


}
