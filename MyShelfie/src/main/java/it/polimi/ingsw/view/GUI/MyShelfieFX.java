package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
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
        primaryStage.getIcons().add(new Image("assets/Publisher material/Icon 50x50px.png"));
        primaryStage.setTitle("My Shelfie");
        primaryStage.centerOnScreen();
        SceneController.setStage(primaryStage);
        SceneController.changeScene("LoadingScreen.fxml");
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
