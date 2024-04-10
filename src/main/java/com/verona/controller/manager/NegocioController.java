package com.verona.controller.manager;

import java.sql.SQLException;

import com.verona.controller.SceneController;
import com.verona.model.Entrada;
import com.verona.model.Pago;
import com.verona.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NegocioController {

    @FXML
    private Button btnMasAnalisisDeGastos;

    @FXML
    private Button btnVolver;

    @FXML
    private BarChart<String, Double> graphGananciasNetas;

    @FXML
    private BarChart<String, Double> graphGastosXMes;

    private User user = User.getCurrentUser();
    int sucursalID = user.getSucursalID();

    @FXML
    public void initialize() {
        try {
            XYChart.Series<String, Double> seriesGanancias = obtenerDatosGananciasNetasPorSucursal();
            inicializarGraficoGananciasNetas(seriesGanancias);

            XYChart.Series<String, Double> seriesGastos = obtenerDatosGastosPorMes();
            inicializarGraficoGastosXMes(seriesGastos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        graphGananciasNetas.setLegendVisible(false);
        graphGastosXMes.setLegendVisible(false);

    }

    private void inicializarGraficoGastosXMes(XYChart.Series<String, Double> series) {
        graphGastosXMes.getData().clear();
        graphGastosXMes.getData().add(series);
    }

    private XYChart.Series<String, Double> obtenerDatosGananciasNetasPorSucursal() throws SQLException {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (int month = 1; month <= 12; month++) {
            double gananciasNetasMes = calcularGananciasNetasPorMesYSucursal(sucursalID, month);
            String nombreMes = obtenerNombreMesEnEspanol(month);
            series.getData().add(new XYChart.Data<>(nombreMes, gananciasNetasMes));
        }
        return series;
    }

    private String obtenerNombreMesEnEspanol(int numeroMes) {
        switch (numeroMes) {
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
            default:
                return "Mes inválido";
        }
    }

    private void inicializarGraficoGananciasNetas(XYChart.Series<String, Double> series) {
        graphGananciasNetas.getData().clear();
        graphGananciasNetas.getData().add(series);

        for (XYChart.Data<String, Double> data : series.getData()) {
            Node node = data.getNode();
            node.setStyle("-fx-bar-fill: green;");
        }
    }

    @FXML
    void btnMasAnalisisDeGastosClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }

    public double calcularGananciasNetasPorMesYSucursal(int sucursalID, int month) throws SQLException {
        Entrada entradaMes = new Entrada(null, null, 0, null, 0, 0, 0, 0, null);
        Pago pagoMes = new Pago(0, 0, 0, 0, 0, 0);
        double entradas = entradaMes.calcularTotalEntradasEnPesosPorSucursalYMesDiscriminado(sucursalID, month);
        double pagos = pagoMes.calcularTotalPagosEnPesosPorSucursalYMes(sucursalID, month);

        if (Double.isNaN(entradas) || Double.isNaN(pagos)) {
            return 0;
        }
        return entradas - pagos;
    }

    private XYChart.Series<String, Double> obtenerDatosGastosPorMes() throws SQLException {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (int month = 1; month <= 12; month++) {
            double gastosMes = calcularGastosPorMes(month);
            String nombreMes = obtenerNombreMesEnEspanol(month);
            series.getData().add(new XYChart.Data<>(nombreMes, gastosMes));
        }
        return series;
    }

    public double calcularGastosPorMes(int month) throws SQLException {
        Pago pagoMes = new Pago(0, 0, 0, 0, 0, 0);
        return pagoMes.calcularTotalPagosEnPesosPorSucursalYMes(sucursalID, month);
    }

}
