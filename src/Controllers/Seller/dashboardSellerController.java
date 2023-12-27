package Controllers.Seller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controllers.SceneController;
import Controllers.Common.cotizacionesController;
import Data.Cotizacion;
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
    private Label cotizacionDolarOficial;

    @FXML
    private Label cotizacionDolarBlue;

    @FXML
    private Label fechaLabel;

    @FXML
    private Label cotizacionDolarCLL;

    @FXML
    private Label nombreEmpleado;

    @FXML
    private Label fechaUltimaOficial;

    @FXML
    private Label fechaUltimaBlue;

    @FXML
    private Label fechaUltimaCCL;

    @FXML
    private TableView<?> tablaVentasResumen;

    @FXML
    void BtnCotizacionesClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) BtnCotizaciones.getScene().getWindow());
        sceneController.switchToCotizaciones();
    }

    @FXML
    public void initialize() {
       
        mostrarFechaActual();
        cargarUltimasCotizaciones();
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

    cotizacionesController cotizacionesController = new cotizacionesController();

    private void cargarUltimasCotizaciones() {
        Cotizacion ultimaCotizacionOficial = cotizacionesController.obtenerUltimaCotizacionOficial();
        Cotizacion ultimaCotizacionBlue = cotizacionesController.obtenerUltimaCotizacionBlue();
        Cotizacion ultimaCotizacionCCL = cotizacionesController.obtenerUltimaCotizacionCCL();

        if (ultimaCotizacionOficial != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaUltimaFormateada = ultimaCotizacionOficial.getFecha().formatted(formatter);

            fechaUltimaOficial.setText(fechaUltimaFormateada);
            cotizacionDolarOficial.setText(String.valueOf(ultimaCotizacionOficial.getTasaCambio()));
        }

        if (ultimaCotizacionBlue != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaUltimaFormateada = ultimaCotizacionBlue.getFecha().formatted(formatter);

            fechaUltimaBlue.setText(fechaUltimaFormateada);
            cotizacionDolarBlue.setText(String.valueOf(ultimaCotizacionBlue.getTasaCambio()));
        }

        if (ultimaCotizacionCCL != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaUltimaFormateada = ultimaCotizacionCCL.getFecha().formatted(formatter);

            fechaUltimaCCL.setText(fechaUltimaFormateada);
            cotizacionDolarCLL.setText(String.valueOf(ultimaCotizacionCCL.getTasaCambio()));
        }
    }


}
