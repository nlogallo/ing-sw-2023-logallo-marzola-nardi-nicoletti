package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyShelfieFX extends Application {

    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        GUIView gui = new GUIView();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlFiles/LoadingScreen.fxml"));
        primaryStage.setTitle("My Shelfie");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
