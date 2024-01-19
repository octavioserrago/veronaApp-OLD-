import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controllers.SceneController;
import Controllers.loginController;
import Data.DatabaseConnection;

import java.io.IOException;



public class App extends Application {
    private SceneController sceneController;

    @Override
    public void start(Stage primaryStage) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/Views/loginScreen.fxml"));
            Parent root = loader.load();

            
            sceneController = new SceneController(primaryStage);
            sceneController.switchToLogin();

            
            loginController loginController = loader.getController();
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
