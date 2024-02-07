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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    private LineChart<String, Integer> graphCantidadVentas;

    @FXML
    private LineChart<?, ?> graphDineroPorVenta;

    @FXML
    private HBox legendContainer;

    private Venta ventaModel = new Venta(0, null, null, null, null, null, 0, 0, 0, null, null, 0, null, null, null, 0);
    private User user = User.getCurrentUser();

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Mes");
        yAxis.setLabel("Cantidad de Ventas");

        graphCantidadVentas.setTitle("Cantidad de Ventas por Mes");
        graphCantidadVentas.setCreateSymbols(true);

        XYChart.Series<String, Integer> persona1 = new XYChart.Series<>();
        persona1.setName("Roberto");
        persona1.getData().add(new XYChart.Data<>("Enero", 10));
        persona1.getData().add(new XYChart.Data<>("Febrero", 20));
        persona1.getData().add(new XYChart.Data<>("Marzo", 15));

        XYChart.Series<String, Integer> persona2 = new XYChart.Series<>();
        persona2.setName("Juan");
        persona2.getData().add(new XYChart.Data<>("Enero", 15));
        persona2.getData().add(new XYChart.Data<>("Febrero", 25));
        persona2.getData().add(new XYChart.Data<>("Marzo", 30));

        XYChart.Series<String, Integer> persona3 = new XYChart.Series<>();
        persona3.setName("Pedro");
        persona3.getData().add(new XYChart.Data<>("Enero", 20));
        persona3.getData().add(new XYChart.Data<>("Febrero", 10));
        persona3.getData().add(new XYChart.Data<>("Marzo", 25));

        graphCantidadVentas.getData().addAll(persona1, persona2, persona3);

        for (XYChart.Series<String, Integer> series : graphCantidadVentas.getData()) {
            Rectangle legendRect = new Rectangle(10, 10);
            legendRect.setFill(Color.RED); // Color de la serie
            Text legendText = new Text(series.getName()); // Nombre de la serie
            legendContainer.getChildren().addAll(legendRect, legendText);
        }
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
