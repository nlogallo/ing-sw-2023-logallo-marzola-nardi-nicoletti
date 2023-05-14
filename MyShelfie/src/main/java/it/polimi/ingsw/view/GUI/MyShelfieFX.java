package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MyShelfieFX extends Application {

    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        GUIView gui = new GUIView();
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
