package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.MyShelfieClient;
import it.polimi.ingsw.view.GUI.GUIControllers.GenericSceneController;
import it.polimi.ingsw.view.GUI.GUIControllers.MainSceneController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This static class allows the GUI to handle the scene
 */
public class SceneController {

    private static GenericSceneController activeSceneController;
    private static Stage stage;
    private static Scene activeScene;

    /**
     * This method allows the GUI to change the scene
     * @param gui is the view stored in the Client
     * @param fxmlName is the name of the fxml file to load
     */
    public static void changeScene(GUIView gui, String fxmlName){
        FXMLLoader fmxlLoader = new FXMLLoader(SceneController.class.getResource("/fxmlFiles/" + fxmlName));
        Parent root = null;
        try {
            root = fmxlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        activeSceneController = fmxlLoader.getController();
        activeSceneController.setGui(gui);
        activeScene = new Scene(root);
        stage.setScene(activeScene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * This method allows the GUI to change the scene
     * @param fxmlName is the name of the fxml file to load
     */
    public static void changeScene(String fxmlName){
        FXMLLoader fmxlLoader = new FXMLLoader(SceneController.class.getResource("/fxmlFiles/" + fxmlName));
        Parent root = null;
        try {
            root = fmxlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        activeSceneController = fmxlLoader.getController();
        activeScene = new Scene(root);
        stage.setScene(activeScene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * This method allows the GUI to change the scene
     * @param fxmlName is the name of the fxml file to load
     * @param parameters are a list of parameters to pass from the previous controller to the new one
     */
    public static void changeScene(String fxmlName, ArrayList<Object> parameters) {
        FXMLLoader fmxlLoader = new FXMLLoader(SceneController.class.getResource("/fxmlFiles/" + fxmlName));
        Parent root = null;
        try {
            root = fmxlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        activeSceneController = fmxlLoader.getController();
        activeScene = new Scene(root);
        activeSceneController.initData(parameters);
        stage.setScene(activeScene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * It creates the first main stage
     * @param fxmlName is the name of the fxml file to load
     */
    public static void createMainStage(String fxmlName, ArrayList<Object> parameters){
        FXMLLoader fmxlLoader = new FXMLLoader(SceneController.class.getResource("/fxmlFiles/" + fxmlName));
        Parent root = null;
        try {
            fmxlLoader.setControllerFactory(controllerClass -> new MainSceneController(MyShelfieClient.getGuiView(), parameters));
            root = fmxlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        activeSceneController = fmxlLoader.getController();
        MyShelfieClient.getGuiView().setStageController((MainSceneController) activeSceneController);
        activeScene = new Scene(root, 1400, 815);
        Platform.runLater(() -> {
            stage.setScene(activeScene);
            stage.setResizable(false);
            stage.show();
        });
    }

    /**
     * This method sets the stage
     * @param stage
     */
    public static void setStage(Stage stage){
        SceneController.stage = stage;
    }

    /**
     * This method gets the stage
     * @return Stage
     */
    public static Stage getStage(){
        return SceneController.stage;
    }

    /**
     * This method closes the Stage
     */
    public static void closeStage(){
        stage.close();
    }


}
