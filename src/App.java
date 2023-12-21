import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        Parent root = null; 
        try {
            root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);

        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Marmoleria Verona");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
