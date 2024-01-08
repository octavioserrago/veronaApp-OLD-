package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private final Stage stage;

    public SceneController(Stage stage) {
        this.stage = stage;
    }

    public void switchToLogin() {
        switchScene("/Resources/Views/loginScreen.fxml");
    }

    public void switchToDashboardSeller() {
        switchScene("/Resources/Views/dashboard.fxml");
    }

    public void switchToCotizaciones() {
        switchScene("/Resources/Views/cotizaciones.fxml");
    }

    public void switchToBachas() {
        switchScene("/Resources/Views/bachas.fxml");
    }
    public void switchToCajaSeller() {
        switchScene("/Resources/Views/cajaSeller.fxml");
    }

    public void switchToVentas(AnchorPane rootPane) {
        try {
            Scene scene = new Scene(rootPane);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void switchScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
