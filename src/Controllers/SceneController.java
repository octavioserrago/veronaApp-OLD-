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
        System.out.println("Switching to Login");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/Views/loginScreen.fxml"));
            Parent root = loader.load();
            // Invalidar la caché del cargador FXML
            loader.setControllerFactory(c -> new loginController());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio de Sesión");
            stage.show();
            System.out.println("Switched to Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToCargarPlano() {
        switchScene("/Resources/Views/cargarPlano.fxml", "Cargar Plano");
    }

    public void switchToVerProduccion() {
        switchScene("/Resources/Views/verProduccion.fxml", "Produccion");
    }

    public void switchToVerPlanos() {
        switchScene("/Resources/Views/verPlanos.fxml", "Ver detalle de los Planos");
    }

    public void switchToDashboardSeller() {
        switchScene("/Resources/Views/dashboard.fxml", "Dashboard - Vendedor");
    }

    public void switchToManagerDashboard() {
        switchScene("/Resources/Views/managerDashboard.fxml", "Dashboard - Gerente");
    }

    public void switchToCotizaciones() {
        switchScene("/Resources/Views/cotizaciones.fxml", "Cotizaciones");
    }

    public void switchToBachas() {
        switchScene("/Resources/Views/bachas.fxml", "Bachas");
    }

    public void switchToCajaSeller() {
        switchScene("/Resources/Views/cajaSeller.fxml", "Caja - Vendedor");
    }

    public void switchToNuevaCotizacionBlue() {
        switchScene("/Resources/Views/nuevaCotizacionBlue.fxml", "Nueva Cotización Blue");
    }

    public void switchToBuscarVenta() {
        switchScene("/Resources/Views/buscarVentas.fxml", "Buscador de Ventas");
    }

    public void switchToModificarEstadoVenta() {
        switchScene("/Resources/Views/modificarEstadoVenta.fxml", "Modificar Estado de Venta");
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

    private void switchScene(String fxmlPath, String windowTitle) {
        System.out.println("Switching Scene: " + fxmlPath);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(windowTitle);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Switched Scene: " + fxmlPath);
    }

}
