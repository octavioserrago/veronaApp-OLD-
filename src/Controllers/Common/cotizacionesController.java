package Controllers.Common;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Controllers.SceneController;
import Data.Cotizacion;
import Data.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class cotizacionesController implements Initializable {
    @FXML
    private TableView<Cotizacion> tablaDolarBlue;

    @FXML
    private TableView<Cotizacion> tablaDolarCCL;

    @FXML
    private TableView<Cotizacion> tablaDolarOficial;

    @FXML
    private Button btnNCB;

    @FXML
    private Button btnNCCCL;

    @FXML
    private Button btnNCOficial;

    @FXML
    private Button btnVolver;

    @FXML
    private Label fecha;

    @FXML
    private TableColumn<Cotizacion, String> columnaFechaCCL;

    @FXML
    private TableColumn<Cotizacion, String> columnaFechaDB;

    @FXML
    private TableColumn<Cotizacion, Double> columnaImporteCCLARS;

    @FXML
    private TableColumn<Cotizacion, Double> columnaImporteDBARS;

    @FXML
    private TableColumn<Cotizacion, Double> columnaImporteDOARS;

    @FXML
    private TableColumn<Cotizacion, String> fechaColumnaDO;
    
    @FXML
    private Label fechaUltimaOficial;

    @FXML
    private Label fechaUltimaBlue;

    @FXML
    private Label fechaUltimaCCL;

    @FXML
    private Label cotizacionDolarOficial;

    @FXML
    private Label cotizacionDolarBlue;

    @FXML
    private Label cotizacionDolarCCL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mostrarFechaActual();
        configurarTablas();
        cargarUltimasCotizaciones();
    }

    @FXML
    void btnNCBClicked(ActionEvent event) {

    }

    @FXML
    void btnNCCCLClicked(ActionEvent event) {

    }

    @FXML
    void btnNCOficialClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardSeller();
    }

    private void mostrarFechaActual() {
        try {
            LocalDate fechaActual = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = fechaActual.format(formatter);

            fecha.setText(fechaFormateada);
        } catch (DateTimeException e) {
            e.printStackTrace();
        }
    }

    DatabaseConnection con = new DatabaseConnection();
    Connection conexion = con.conectar();

    public Cotizacion obtenerUltimaCotizacionOficial() {
        String sql = "SELECT fecha, tasaCambio FROM Cotizaciones WHERE monedasID = 2 ORDER BY fecha DESC LIMIT 1";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String fechaCotizacion = resultSet.getString("fecha");
                double tasaCambio = resultSet.getDouble("tasaCambio");

                return new Cotizacion(fechaCotizacion, tasaCambio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Cotizacion obtenerUltimaCotizacionBlue() {
        String sql = "SELECT fecha, tasaCambio FROM Cotizaciones WHERE monedasID = 3 ORDER BY fecha DESC LIMIT 1";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String fechaCotizacion = resultSet.getString("fecha");
                double tasaCambio = resultSet.getDouble("tasaCambio");

                return new Cotizacion(fechaCotizacion, tasaCambio);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cotizacion obtenerUltimaCotizacionCCL() {
        String sql = "SELECT fecha, tasaCambio FROM Cotizaciones WHERE monedasID = 4 ORDER BY fecha DESC LIMIT 1";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String fechaCotizacion = resultSet.getString("fecha");
                double tasaCambio = resultSet.getDouble("tasaCambio");

                return new Cotizacion(fechaCotizacion, tasaCambio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void configurarTablas() {
        configurarTablaDolar(tablaDolarOficial);
        configurarTablaDolar(tablaDolarBlue);
        configurarTablaDolar(tablaDolarCCL);
    }

    private void configurarTablaDolar(TableView<Cotizacion> tabla) {
        TableColumn<Cotizacion, String> fechaColumna = new TableColumn<>("Fecha");
        TableColumn<Cotizacion, Double> importeColumna = new TableColumn<>("Tasa Cambio");

        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        importeColumna.setCellValueFactory(new PropertyValueFactory<>("tasaCambio"));

        fechaColumna.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        importeColumna.setMaxWidth(1f * Integer.MAX_VALUE * 25);

        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabla.getColumns().add(fechaColumna);
        tabla.getColumns().add(importeColumna);
    }

    private void cargarUltimasCotizaciones() {
        Cotizacion ultimaCotizacionOficial = obtenerUltimaCotizacionOficial();
        Cotizacion ultimaCotizacionBlue = obtenerUltimaCotizacionBlue();
        Cotizacion ultimaCotizacionCCL = obtenerUltimaCotizacionCCL();

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
            cotizacionDolarCCL.setText(String.valueOf(ultimaCotizacionCCL.getTasaCambio()));
        }
    }

}
