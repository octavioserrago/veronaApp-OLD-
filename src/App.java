import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import Data.DatabaseConnection;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        Parent root = null; 
        try {
            root = FXMLLoader.load(getClass().getResource("/Resources/Views/loginScreen.fxml"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        

        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Marmoleria Verona");
        primaryStage.show();

        DatabaseConnection db = new DatabaseConnection();
        db.conectar();

        if (db.isConnected()) {
            System.out.println("Conexión establecida correctamente.");
        } else {
            System.out.println("Error al conectar a la base de datos.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
