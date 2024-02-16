package com.verona.controller.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

import java.util.Locale;
import com.verona.controller.SceneController;
import com.verona.controller.common.CotizacionesController;
import com.verona.model.Cotizacion;
import com.verona.model.Entrada;
import com.verona.model.Pago;
import com.verona.model.User;
import com.verona.model.Venta;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManagerDashboardController {

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private ComboBox<String> comboboxBachas;

    @FXML
    private ComboBox<String> comboboxCaja;

    @FXML
    private Button btnNegocio;

    @FXML
    private Button btnActualizarPrecios;

    @FXML
    private ComboBox<String> comboboxVentas;

    @FXML
    private PieChart graficoEntradasYSalidas;

    @FXML
    private LineChart<String, Integer> graphVentasPorMes;

    @FXML
    private ComboBox<?> proveedoresCombobox;

    @FXML
    private Label valorDolarBlueLabel;

    @FXML
    private Label salidasDiaLabel;

    @FXML
    private Label entradasDiaLabel;

    @FXML
    private Label fechaUltimaDolarLabel;

    @FXML
    private Button btnRealizarPrespuesto;

    @FXML
    private Button btnActualizarCotizacionDolarBlue;

    @FXML
    void btnActualizarCotizacionDolarBlueClicked(ActionEvent event) {
        SceneController sceneController = new SceneController(
                (Stage) btnActualizarCotizacionDolarBlue.getScene().getWindow());
        sceneController.switchToCotizaciones();
    }

    @FXML
    void btnRealizarPresupuestoClicked(ActionEvent event) {

    }

    @FXML
    void btnActualizarPreciosClicked(ActionEvent event) {

    }

    private Venta ventaModel = new Venta(0, null, null, null, null, null, 0, 0, 0, null, null, 0, null, null, null, 0,
            0);
    User user = User.getCurrentUser();
    private Entrada entradaModel = new Entrada(null, null, 0, null, 0, 0, 0, 0, null);
    private Pago pagoModel = new Pago(0, 0, 0, 0, 0, 0);

    @FXML
    void btnCerrarSesionClicked(ActionEvent event) {
        User user = new User("", "", "", "", 0, 0);
        User.setCurrentUser(user);
        System.gc();
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToLogin();
    }

    @FXML
    void initialize() throws SQLException {

        ObservableList<String> bachasItems = FXCollections.observableArrayList("Ver Stock de Bachas",
                "Agregar cantidad de Bachas", "Modificar Cantidad de bachas", "Agregar modelo nuevo",
                "Modificar modelo");
        comboboxBachas.setItems(bachasItems);

        ObservableList<String> cajaItems = FXCollections.observableArrayList("Ver Balances", "Registrar Nuevo Ingreso",
                "Registrar Nueva Salida", "Verificar ingresos con tarjetas de crédito");
        comboboxCaja.setItems(cajaItems);

        comboboxCaja.setOnAction(event -> {
            String selectedItem = comboboxCaja.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                handleCajaItemSelected(selectedItem);
            }
        });

        ObservableList<String> ventasItems = FXCollections.observableArrayList();

        ventasItems.addAll("Crear nueva Venta", "Buscar Venta", "Ver Ventas de Esta Sucursal",
                "Modificar datos de una venta", "Eliminar una venta");

        int sucursalID = user.getSucursalID();

        if (sucursalID == 1) {
            ventasItems.add("Ver Produccion");
        }

        comboboxVentas.setItems(ventasItems);

        comboboxVentas.setOnAction(event -> {
            String selectedItem = comboboxVentas.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                handleVentasItemSelected(selectedItem);
            }
        });

        // PieChart
        double sumaTotal = entradaModel.calcularTotalEntradasEnPesosPorSucursalYMes(user.getSucursalID());
        double sumaSalidas = pagoModel.obtenerSumaImporteEnPesosPorSucursalYMesVigente(user.getSucursalID());

        PieChart.Data entradaData = new PieChart.Data("Entrada", sumaTotal);
        PieChart.Data salidaData = new PieChart.Data("Salida", sumaSalidas);

        graficoEntradasYSalidas.getData().clear();
        graficoEntradasYSalidas.getData().addAll(entradaData, salidaData);

        graficoEntradasYSalidas.setOnMouseClicked(event -> {
            try {
                handlePieChartClick();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Graph cantidad de ventas
        XYChart.Series<String, Integer> seriesVentas = new XYChart.Series<>();
        seriesVentas.setName("Ventas por Mes");
        ObservableList<XYChart.Data<String, Integer>> datosVentasPorMes = ventaModel
                .obtenerVentasPorMes(user.getSucursalID());
        seriesVentas.getData().addAll(datosVentasPorMes);
        graphVentasPorMes.getData().add(seriesVentas);
        graphVentasPorMes.setTitle("Cantidad de Ventas por Mes");
        graphVentasPorMes.getYAxis().setLabel("Cantidad de Ventas");

        // Cotizaciones dolar
        cargarUltimasCotizaciones();

        cargarTotalEntradasDelDia();
        cargarSalidasDelDia();
    }

    private void handlePieChartClick() throws SQLException {
        PieChart.Data clickedData = graficoEntradasYSalidas.getData().stream()
                .filter(data -> data.getNode().isHover())
                .findFirst()
                .orElse(null);

        if (clickedData != null) {
            String categoria = clickedData.getName();
            double total = (categoria.equals("Entrada"))
                    ? entradaModel.calcularTotalEntradasEnPesosPorSucursalYMes(user.getSucursalID())
                    : pagoModel.obtenerSumaImporteEnPesosPorSucursalYMesVigente(user.getSucursalID());

            System.out.println("Total de salidas: " + total);

            String totalFormateado = formatearComoDinero(total);
            mostrarAlert(categoria, totalFormateado);
        }

    }

    @SuppressWarnings("deprecation")
    private String formatearComoDinero(double cantidad) {
        NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.getDefault());
        formatoDinero.setCurrency(Currency.getInstance(new Locale("es", "AR")));
        return formatoDinero.format(cantidad);
    }

    // Cargar Entradas del dia
    private void cargarTotalEntradasDelDia() throws SQLException {
        double totalEntradas = entradaModel.calcularTotalEntradasEnPesosPorSucursalYDia(user.getSucursalID());
        @SuppressWarnings("deprecation")
        NumberFormat formatoPesos = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        String totalEntradasTexto = formatoPesos.format(totalEntradas);
        entradasDiaLabel.setText(totalEntradasTexto);
    }

    // Cargar Salidas del dia
    private void cargarSalidasDelDia() throws SQLException {
        double totalSalidas = pagoModel.calcularTotalSalidasEnPesosPorSucursalYDia(user.getSucursalID());
        @SuppressWarnings("deprecation")
        NumberFormat formatoPesos = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        String totalSalidasTexto = formatoPesos.format(totalSalidas);
        salidasDiaLabel.setText(totalSalidasTexto);
    }

    private void mostrarAlert(String categoria, String total) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Total del mes");
        alert.setHeaderText("Total de " + categoria);
        alert.setContentText("El total de " + categoria.toLowerCase() + " para el mes vigente es:  " + total);
        alert.showAndWait();
    }

    @FXML
    void btnNegocioClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnNegocio.getScene().getWindow());
        sceneController.switchToNegocio();
    }

    @FXML
    void btnEmpleadosClicked(ActionEvent event) {

    }

    private void handleVentasItemSelected(String selectedItem) {
        if ("Crear nueva Venta".equals(selectedItem)) {
            crearNuevaVenta();
        } else if ("Buscar Venta".equals(selectedItem)) {
            buscarVenta();
        } else if ("Ver Produccion".equals(selectedItem)) {
            verProduccion();
        } else if ("Ver Ventas de Esta Sucursal".equals(selectedItem)) {
            verVentasDeSucursal();
        }
    }

    private void handleCajaItemSelected(String selectedItem) {
        if ("Ver Balances".equals(selectedItem)) {
            verBalances();
        } else if ("Registrar Nuevo Ingreso".equals(selectedItem)) {
            nuevoIngreso();
        } else if ("Registrar Nueva Salida".equals(selectedItem)) {
            nuevaSalida();
        } else if ("Verificar ingresos con tarjetas de crédito".equals(selectedItem)) {
            verificarIngresosCredito();
        }
    }

    private void nuevoIngreso() {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToRegistrarIngreso();
    }

    private void verBalances() {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToVerBalances();
    }

    private void verificarIngresosCredito() {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToVerificarTarjetaCredito();
    }

    private void verVentasDeSucursal() {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToVerVentasDeSucursal();
    }

    private void nuevaSalida() {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToRegistrarPago();
    }

    private void verProduccion() {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToVerProduccion();
    }

    private void buscarVenta() {
        AnchorPane nuevoRootPane = obtenerRootPaneParaBuscarVentas();
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToVentas(nuevoRootPane);
    }

    private AnchorPane obtenerRootPaneParaBuscarVentas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/verona/view/buscarVentas.fxml"));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void crearNuevaVenta() {
        AnchorPane nuevoRootPane = obtenerRootPaneParaVentas();
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToVentas(nuevoRootPane);
    }

    private AnchorPane obtenerRootPaneParaVentas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/verona/view/cargarVentas.fxml"));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    CotizacionesController cotizacionesController = new CotizacionesController();

    private void cargarUltimasCotizaciones() {
        Cotizacion ultimaCotizacionBlue = cotizacionesController.obtenerUltimaCotizacionBlue();

        if (ultimaCotizacionBlue != null) {
            DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fechaUltimaCotizacion = LocalDateTime.parse(ultimaCotizacionBlue.getFecha(),
                    formatterDateTime);

            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaUltimaFormateada = fechaUltimaCotizacion.format(formatterDate);

            fechaUltimaDolarLabel.setText(fechaUltimaFormateada);
            valorDolarBlueLabel.setText(String.valueOf(ultimaCotizacionBlue.getTasaCambio()));
        }
    }
}