package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This method is the runnable application
 */
public class MyShelfieFX extends Application {

    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("My Shelfie");
        GUIView gui = new GUIView();
        SceneController.setStage(primaryStage);
        SceneController.changeScene(gui, "LoadingScreen.fxml");
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
