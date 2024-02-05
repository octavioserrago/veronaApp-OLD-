package com.verona.controller.manager;

import java.io.IOException;

import com.verona.controller.SceneController;
import com.verona.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> comboboxVentas;

    @FXML
    private Button btnEmpleados;

    User user = User.getCurrentUser();

    @FXML
    void btnCerrarSesionClicked(ActionEvent event) {
        User user = new User("", "", "", "", 0);
        User.setCurrentUser(user);
        System.gc();
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToLogin();
    }

    @FXML
    void initialize() {

        ObservableList<String> bachasItems = FXCollections.observableArrayList("Ver Stock de Bachas",
                "Agregar cantidad de Bachas", "Modificar Cantidad de bachas", "Agregar modelo nuevo",
                "Modificar modelo");
        comboboxBachas.setItems(bachasItems);

        ObservableList<String> cajaItems = FXCollections.observableArrayList("Ver Balances", "Entradas", "Salidas");
        comboboxCaja.setItems(cajaItems);

        ObservableList<String> ventasItems = FXCollections.observableArrayList();

        ventasItems.addAll("Crear nueva Venta", "Buscar Venta", "Modificar datos de una venta", "Eliminar una venta");

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

    }

    @FXML
    void btnNegocioClicked(ActionEvent event) {

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
        }
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
}