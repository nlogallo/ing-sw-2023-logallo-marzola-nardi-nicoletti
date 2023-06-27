package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.MyShelfieClient;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents the Controller of the ConnectToServer Scene
 * ConnectToServer allows the user to connect to the Server. The user has to insert the Server Socket and choose the type of protocol to use.
 */
public class ConnectToServerController implements GenericSceneController, Initializable {

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

    /**
     * Override method of setGui in GenericSceneController
     *
     * @param gui is the gui to set, in this scene we don't have it
     */
    @Override
    public void setGui(GUIView gui) {
    }

    /**
     * Override method of initialize from Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::connectOnClick);
        quitButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::quitOnClick);
        toggleSocket.setOnAction(actionEvent -> {
            toggleRMI.setSelected(false);
            toggleSocket.setStyle(null);
            toggleRMI.setStyle(null);
        });
        toggleRMI.setOnAction(actionEvent -> {
            toggleSocket.setSelected(false);
            toggleSocket.setStyle(null);
            toggleRMI.setStyle(null);
        });
        IPText.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            IPText.setStyle(null);
            labelIP.setText("Server IP:");
        });
        portText.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            portText.setStyle(null);
            labelPort.setText("Port:");
        });
    }

    /**
     * Override method of initData from GenericSceneController
     *
     * @param parameters is the list of parameters
     */
    @Override
    public void initData(ArrayList<Object> parameters) {
    }

    /**
     * This method allows to close the scene (so the application)
     *
     * @param event is the event triggered by clicking the quit button
     */
    private void quitOnClick(Event event) {
        SceneController.closeStage();
    }

    /**
     * This method makes a call to the Server (via Socket or RMI) to set up the connection
     *
     * @param event is the event triggered by clicking on the connect button
     */
    private void connectOnClick(Event event) {
        labelIP.setText("Server IP:");
        labelPort.setText("Port:");
        IPText.setStyle(null);
        portText.setStyle(null);
        if (!toggleSocket.isSelected() && !toggleRMI.isSelected()) {
            toggleSocket.setStyle("-fx-border-color: RED;");
            toggleRMI.setStyle("-fx-border-color: RED;");
        } else {
            final Pattern pattern = Pattern.compile("^((\\d{1,3}\\.){3}\\d{1,3}|[a-zA-Z0-9\\-\\.]+):([0-9]{1,5})$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(IPText.getText() + ":" + portText.getText());
            if (!matcher.matches()) {
                labelIP.setText("Invalid Socket");
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
                        SceneController.changeScene("EnterNicknameStage.fxml", parameters);
                    } else {
                        Stage primaryStage = SceneController.getStage();
                        primaryStage.close();
                        Stage stage = new Stage();
                        stage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
                        stage.setTitle("My Shelfie Connection Error");
                        SceneController.setStage(stage);
                        SceneController.changeScene("ErrorStage.fxml");
                    }
                } catch (NumberFormatException ex) {
                    labelPort.setText("Invalid Port");
                    portText.setStyle("-fx-text-fill: #ff3131; -fx-border-color: #ff3131;");
                }
            }
        }
    }


}
