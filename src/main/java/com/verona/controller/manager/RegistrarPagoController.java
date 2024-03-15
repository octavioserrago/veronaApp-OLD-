package com.verona.controller.manager;

import com.verona.controller.SceneController;
import com.verona.model.Pago;
import com.verona.model.Proveedor;
import com.verona.model.TipoPago;
import com.verona.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private ComboBox<?> fondosCombobox;

    @FXML
    private TextField importeTextFieldToComplete;

    @FXML
    private Label proveedorLabel;

    private User user = User.getCurrentUser();
    private TipoPago tiposDePago = new TipoPago(0, null);
    private Proveedor proveedor = new Proveedor(0, null);

    @FXML
    void initialize() {
        noVisibles();
        llenarComboBoxTiposDePago();
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

    @FXML
    void btnCargarClicked(ActionEvent event) {
        TipoPago tipoPagoSeleccionado = comboboxDescribirGasto.getValue();
        Proveedor proveedorSeleccionado = comboboxProveedor.getValue(); // Obtener el proveedor seleccionado

        if (tipoPagoSeleccionado == null || proveedorSeleccionado == null) {
            // Manejar el caso en que no se haya seleccionado algún tipo de pago o proveedor
            // Por ejemplo, mostrar un mensaje de error al usuario y salir del método
            return;
        }

        int tipoPagoID = tipoPagoSeleccionado.getTiposPagosID();
        int monedasID = 1;
        double importe = Double.parseDouble(importeTextFieldToComplete.getText());
        double importeEnPesos = Double.parseDouble(importeTextFieldToComplete.getText());
        int proveedorID = proveedorSeleccionado.getProveedorID();
        int sucursalID = user.getSucursalID();

        Pago pago = new Pago(tipoPagoID, monedasID, importe, importeEnPesos, proveedorID, sucursalID);
        try {
            pago.insertarPago(sucursalID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }
}
