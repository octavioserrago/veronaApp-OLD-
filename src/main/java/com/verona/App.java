package com.verona;

import java.io.IOException;

import com.verona.controller.LoginController;
import com.verona.controller.SceneController;
import com.verona.model.DatabaseConnection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private SceneController sceneController;

    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/verona/view/loginScreen.fxml"));
            Parent root = loader.load();

            sceneController = new SceneController(primaryStage);
            sceneController.switchToLogin();

            LoginController loginController = loader.getController();
            loginController.setSceneController(sceneController);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Marmoleria Verona");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseConnection db = new DatabaseConnection();
        db.conectar();
    }

    public static void main(String[] args) {
        launch(args);
    }

}