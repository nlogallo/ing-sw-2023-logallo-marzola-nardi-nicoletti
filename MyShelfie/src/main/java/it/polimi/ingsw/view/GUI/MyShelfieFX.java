package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * This class is the runnable application of the GUI
 */
public class MyShelfieFX extends Application {

    public static void main(String[] args){
        Application.launch(args);
    }

    /**
     * It starts the application
     * @param primaryStage is the first stage
     * @throws IOException when it cannot create the window
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
        primaryStage.setTitle("My Shelfie");
        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        SceneController.setStage(primaryStage);
        SceneController.changeScene("LoadingScreen.fxml");
    }

    /**
     * It stops the application
     */
    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
