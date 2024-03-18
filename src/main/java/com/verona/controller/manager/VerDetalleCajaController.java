package com.verona.controller.manager;

import com.verona.controller.SceneController;
import com.verona.model.TransaccionesFinancieras;
import com.verona.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VerDetalleCajaController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnVolver;

    @FXML
    private DatePicker desdeDateSelect;

    @FXML
    private DatePicker hastaDateSelect;

    @FXML
    private TableView<TransaccionesFinancieras> tableDetails;

    private TransaccionesFinancieras transaccionesFinancieras;
    private User user = User.getCurrentUser();

    @SuppressWarnings("unchecked")
    @FXML
    private void initialize() {
        TableColumn<TransaccionesFinancieras, String> fechaColumn = new TableColumn<>("Fecha");
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<TransaccionesFinancieras, String> tipoMovimientoColumn = new TableColumn<>("Descripción");
        tipoMovimientoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoMovimiento"));

        TableColumn<TransaccionesFinancieras, String> importeColumn = new TableColumn<>("Importe");
        importeColumn.setCellValueFactory(new PropertyValueFactory<>("importeEnPesos"));

        tableDetails.getColumns().addAll(fechaColumn, tipoMovimientoColumn, importeColumn);

        transaccionesFinancieras = new TransaccionesFinancieras(null, 0, 0);

        // Cargar las transacciones al inicializar
        cargarTransacciones();
    }

    private void cargarTransacciones() {
        try {
            List<TransaccionesFinancieras> transacciones = transaccionesFinancieras
                    .obtenerTodasTransacciones(user.getSucursalID());
            ObservableList<TransaccionesFinancieras> transaccionesData = FXCollections
                    .observableArrayList(transacciones);
            tableDetails.setItems(transaccionesData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnActualizarClicked(ActionEvent event) {
        LocalDate desdeFecha = desdeDateSelect.getValue();
        LocalDate hastaFecha = hastaDateSelect.getValue();

        if (desdeFecha != null && hastaFecha != null) {
            try {
                List<TransaccionesFinancieras> transacciones = transaccionesFinancieras
                        .obtenerTransaccionesPorFecha(user.getSucursalID(), desdeFecha, hastaFecha);
                ObservableList<TransaccionesFinancieras> transaccionesData = FXCollections
                        .observableArrayList(transacciones);
                tableDetails.setItems(transaccionesData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToVerBalances();
    }
}
