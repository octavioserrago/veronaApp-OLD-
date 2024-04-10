package com.verona.controller.manager;

import com.verona.controller.SceneController;
import com.verona.model.Caja;
import com.verona.model.Pago;
import com.verona.model.Proveedor;
import com.verona.model.TipoPago;
import com.verona.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class RegistrarPagoController {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<TipoPago> comboboxDescribirGasto;

    @FXML
    private ComboBox<Proveedor> comboboxProveedor;

    @FXML
    private ComboBox<String> fondosCombobox;

    @FXML
    private TextField importeTextFieldToComplete;

    @FXML
    private Label proveedorLabel;

    private User user = User.getCurrentUser();
    private TipoPago tiposDePago = new TipoPago(0, null);
    private Proveedor proveedor = new Proveedor(0, null);
    private Caja caja = new Caja(0, 0);

    @FXML
    void initialize() {
        noVisibles();
        llenarComboBoxTiposDePago();
        llenarComboBoxFondos();
        comboboxDescribirGasto.setOnAction(event -> {
            TipoPago tipoPagoSeleccionado = comboboxDescribirGasto.getValue();
            if (tipoPagoSeleccionado != null && tipoPagoSeleccionado.getDetalle().equals("Pago a Proveedor")) {
                comboboxProveedor.setVisible(true);
                proveedorLabel.setVisible(true);
                llenarComboBoxProveedores();
            } else {
                comboboxProveedor.setVisible(false);
                proveedorLabel.setVisible(false);
            }
        });
    }

    private void noVisibles() {
        proveedorLabel.setVisible(false);
        comboboxProveedor.setVisible(false);
    }

    private void llenarComboBoxTiposDePago() {
        List<TipoPago> listaTiposDePago = tiposDePago.obtenerTiposDePago();
        ObservableList<TipoPago> observableListaTiposDePago = FXCollections.observableArrayList(listaTiposDePago);
        comboboxDescribirGasto.setItems(observableListaTiposDePago);
    }

    private void llenarComboBoxProveedores() {
        List<Proveedor> listaProveedores = proveedor.obtenerProveedores();
        ObservableList<Proveedor> observableListaProveedores = FXCollections.observableArrayList(listaProveedores);
        comboboxProveedor.setItems(observableListaProveedores);
    }

    private void llenarComboBoxFondos() {
        ObservableList<String> fondosList = FXCollections.observableArrayList(
                "Caja Efectivo",
                "Caja Banco",
                "Caja Efectivo y Caja Banco");
        fondosCombobox.setItems(fondosList);
    }

    @FXML
    void btnCargarClicked(ActionEvent event) {
        TipoPago tipoPagoSeleccionado = comboboxDescribirGasto.getValue();
        Proveedor proveedorSeleccionado = comboboxProveedor.getValue();
        String fondoSeleccionado = fondosCombobox.getValue();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Pago");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro/a que deseas realizar este pago?");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            double importe = Double.parseDouble(importeTextFieldToComplete.getText());
            double importeEnPesos = Double.parseDouble(importeTextFieldToComplete.getText());
            int sucursalID = user.getSucursalID();

            try {
                double saldoDisponible = 0.0;
                switch (fondoSeleccionado) {
                    case "Caja Efectivo":
                        saldoDisponible = caja.obtenerUltimoSaldo(sucursalID, "CajaEfectivo");
                        break;
                    case "Caja Banco":
                        saldoDisponible = caja.obtenerUltimoSaldo(sucursalID, "CajaBanco");
                        break;
                }

                if (saldoDisponible >= importeEnPesos) {
                    int tipoPagoID = tipoPagoSeleccionado.getTiposPagosID();
                    int monedasID = 1;
                    int proveedorID = (proveedorSeleccionado != null) ? proveedorSeleccionado.getProveedorID() : 0;

                    Pago pago;
                    if (proveedorSeleccionado == null) {
                        pago = new Pago(tipoPagoID, monedasID, importe, importeEnPesos, 0, sucursalID);
                        pago.insertarPagoNoProveedor(sucursalID);
                    } else {
                        pago = new Pago(tipoPagoID, monedasID, importe, importeEnPesos, proveedorID, sucursalID);
                        pago.insertarPagoProveedor(sucursalID);
                    }

                    switch (fondoSeleccionado) {
                        case "Caja Efectivo":
                            caja.pagoCajaEfectivo(importeEnPesos, sucursalID);
                            break;
                        case "Caja Banco":
                            caja.pagoCajaBanco(importeEnPesos, sucursalID);
                            break;
                    }
                    btnCargar.setDisable(true);
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error de saldo");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("El saldo disponible no es suficiente para realizar este pago.");
                    errorAlert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }
}
