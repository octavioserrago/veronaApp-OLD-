package Controllers.Seller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controllers.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class dashboardSellerController {

    @FXML
    private Button BtnCotizaciones;

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
    private TableColumn<?, ?> colColor;

    @FXML
    private TableColumn<?, ?> colDetalle;

    @FXML
    private TableColumn<?, ?> colEstado;

    @FXML
    private TableColumn<?, ?> colFechaIngreso;

    @FXML
    private TableColumn<?, ?> colFechaTerminacion;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private TableColumn<?, ?> colTelefono;

    @FXML
    private Label cotizacionDolarBNA;

    @FXML
    private Label cotizacionDolarBlue;

    @FXML
    private Label fechaLabel;

    @FXML
    private Label nombreEmpleado;

    @FXML
    private TableView<?> tablaVentasResumen;

    @FXML
    void BtnCotizacionesClicked(ActionEvent event) {

    }

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
