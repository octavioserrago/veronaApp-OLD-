package com.verona.controller.manager;

import java.net.URL;
import java.util.ResourceBundle;

import com.verona.controller.SceneController;
import com.verona.model.User;
import com.verona.model.Venta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NegocioController implements Initializable {

    @FXML
    private Button btnCargarEstadisticas;

    @FXML
    private Button btnEstadisticasIngresos;

    @FXML
    private Button btnEstadisticasSalidas;

    @FXML
    private Button btnVolver;

    @FXML
    private DatePicker fechaCierrePicker;

    @FXML
    private DatePicker fechaInicioPicker;

    @FXML
    private LineChart<?, ?> graphDineroPorVenta;

    @FXML
    private HBox legendContainer;

    private Venta ventaModel = new Venta(0, null, null, null, null, null, 0, 0, 0, null, null, 0, null, null, null, 0);
    private User user = User.getCurrentUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void btnCargarEstadisticasClicked(ActionEvent event) {

    }

    @FXML
    void btnCargarEstadisticasIngresosClicked(ActionEvent event) {
        // Método para cargar estadísticas de ingresos
    }

    @FXML
    void btnCargarEstadisticasSalidasClicked(ActionEvent event) {
        // Método para cargar estadísticas de salidas
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }

}
