package com.verona.controller.manager;

import java.time.LocalDate;
import java.util.List;

import com.verona.controller.SceneController;
import com.verona.model.Pago;
import com.verona.model.Proveedor;
import com.verona.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PagosProveedoresController {

    @FXML
    private Button btnFiltrar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<Proveedor> comboboxProveedores;

    @FXML
    private DatePicker datePickerDesde;

    @FXML
    private DatePicker datePickerHasta;

    @FXML
    private TableView<Pago> tableViewPagos;

    private Proveedor proveedores = new Proveedor(0, null);
    private Pago pago = new Pago(0, 0, 0, 0, 0, 0);
    private User user = User.getCurrentUser();

    @FXML
    void initialize() {
        cargarProveedores();
        inicializarTablaPagos();
    }

    @FXML
    void btnFiltrarClicked(ActionEvent event) {
        int sucursalID = user.getSucursalID();
        LocalDate fechaInicio = datePickerDesde.getValue();
        LocalDate fechaFin = datePickerHasta.getValue();
        List<Pago> pagosAProveedores = pago.obtenerPagosAProveedoresPorFechaYSucursal(fechaInicio, fechaFin,
                sucursalID);
        tableViewPagos.getItems().clear();
        ObservableList<Pago> observablePagos = FXCollections.observableArrayList(pagosAProveedores);
        tableViewPagos.setItems(observablePagos);
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }

    private void cargarProveedores() {
        List<Proveedor> listaProveedores = proveedores.obtenerProveedores();
        ObservableList<Proveedor> observableProveedores = FXCollections.observableArrayList(listaProveedores);
        comboboxProveedores.setItems(observableProveedores);
    }

    @SuppressWarnings("unchecked")
    @FXML
    void inicializarTablaPagos() {
        TableColumn<Pago, LocalDate> fechaColumn = new TableColumn<>("Fecha");
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<Pago, String> proveedorNombreColumn = new TableColumn<>("Proveedor");
        proveedorNombreColumn.setCellValueFactory(cellData -> {
            int proveedorID = cellData.getValue().getProveedorID();
            String nombreProveedor = obtenerNombreProveedor(proveedorID);
            return new SimpleStringProperty(nombreProveedor);
        });

        TableColumn<Pago, Double> importeEnPesosColumn = new TableColumn<>("Importe en Pesos");
        importeEnPesosColumn.setCellValueFactory(new PropertyValueFactory<>("importeEnPesos"));

        int sucursalID = user.getSucursalID();
        List<Pago> pagosAProveedores = pago.obtenerPagosAProveedoresPorSucursal(sucursalID);
        ObservableList<Pago> observablePagos = FXCollections.observableArrayList(pagosAProveedores);
        tableViewPagos.setItems(observablePagos);

        tableViewPagos.getColumns().addAll(fechaColumn, proveedorNombreColumn, importeEnPesosColumn);
    }

    private String obtenerNombreProveedor(int proveedorID) {
        Proveedor proveedor = new Proveedor(proveedorID, null);
        return proveedor.obtenerNombreProveedor(proveedorID);
    }
}
