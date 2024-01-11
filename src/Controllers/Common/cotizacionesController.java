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
    private Button btnNCB;

    @FXML
    private Button btnVolver;

    @FXML
    private Label fecha;

    @FXML
    private TableColumn<Cotizacion, String> columnaFechaDB;

    @FXML
    private TableColumn<Cotizacion, Double> columnaImporteDBARS;

    @FXML
    private Label fechaUltimaBlue;

    @FXML
    private Label cotizacionDolarBlue;

    private DatabaseConnection con = new DatabaseConnection();
    private Connection conexion = con.conectar();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mostrarFechaActual();
        configurarTablas();
    }

    @FXML
    void btnNCBClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnNCB.getScene().getWindow());
        sceneController.switchToNuevaCotizacionBlue();
    }

    @FXML
    void btnNCCCLClicked(ActionEvent event) {
        // Implementa tu lógica aquí si es necesario
    }

    @FXML
    void btnNCOficialClicked(ActionEvent event) {
        // Implementa tu lógica aquí si es necesario
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

    private void configurarTablas() {
        configurarTablaDolar(tablaDolarBlue, "SELECT fecha, tasaCambio FROM Cotizaciones WHERE monedasID = 3");
    }

    private void configurarTablaDolar(TableView<Cotizacion> tabla, String sqlQuery) {
        TableColumn<Cotizacion, String> fechaColumna = new TableColumn<>("Fecha");
        TableColumn<Cotizacion, Double> importeColumna = new TableColumn<>("Tasa Cambio");

        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        importeColumna.setCellValueFactory(new PropertyValueFactory<>("tasaCambio"));

        fechaColumna.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        importeColumna.setMaxWidth(1f * Integer.MAX_VALUE * 25);

        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabla.getColumns().add(fechaColumna);
        tabla.getColumns().add(importeColumna);

        cargarTodasCotizaciones(tabla, sqlQuery);
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

    private void cargarTodasCotizaciones(TableView<Cotizacion> tabla, String sqlQuery) {
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String fechaCotizacion = resultSet.getString("fecha");
                double tasaCambio = resultSet.getDouble("tasaCambio");

                Cotizacion cotizacion = new Cotizacion(fechaCotizacion, tasaCambio);
                tabla.getItems().add(cotizacion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
