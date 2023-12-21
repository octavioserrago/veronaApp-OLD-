package Controllers.Seller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controllers.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class dashboardSellerController {

    @FXML
    private Button btnBachas;

    @FXML
    private Button btnCaja;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnPedidos;

    @FXML
    private Button btnVentas;

    @FXML
    private Label fechaLabel;

    @FXML
    public void initialize() {
        
        mostrarFechaActual();
    }

    @FXML
    void btnBachasClicked(ActionEvent event) {
        
    }

    @FXML
    void btnCajaClicked(ActionEvent event) {
        
    }

    @FXML
    void btnCerrarSesionClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToLogin();
    }

    @FXML
    void btnPedidosClicked(ActionEvent event) {
        
    }

    @FXML
    void btnVentasClicked(ActionEvent event) {
        
    }

    private void mostrarFechaActual() {
        
        LocalDate fechaActual = LocalDate.now();

        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        
        fechaLabel.setText(fechaFormateada);
    }
}
