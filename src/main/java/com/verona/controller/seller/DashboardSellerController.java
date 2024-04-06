package com.verona.controller.seller;

import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.verona.controller.SceneController;
import com.verona.model.ApiDolarBlue;
import com.verona.model.ApiDolarOficial;

import com.verona.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashboardSellerController {

    @FXML
    private Button btnBachas;

    @FXML
    private Button btnCaja;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private MenuItem btnVentas;

    @FXML
    private MenuItem btnVentasBuscar;

    @FXML
    private MenuButton btnVentasMenu;

    @FXML
    private MenuItem btnVentasModificarEstado;

    @FXML
    private Button btnVerIngresosConCredito;

    @FXML
    private MenuItem btnVerVentasDeEstaSucursal;

    @FXML
    private Label dolarBlueLabelToComplete;

    @FXML
    private Label dolarOficialLabelToComplete;

    @FXML
    private Label fechaLabel;

    @FXML
    private Button btnVerPrecioManoObra;

    @FXML
    private Button btnVerPrecioMateriales;

    @FXML
    private Button btnVerPrecioBachas;

    @FXML
    void btnVerIngresosConCreditoClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToVerificarTarjetaCredito();
    }

    @FXML
    void btnVentasDeEstaSucursalClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToVerVentasDeSucursal();
    }

    @FXML
    public void initialize() {
        mostrarFechaActual();
        dolarOficialLabelToComplete.setText(cargarCotizacionOficial());
        dolarBlueLabelToComplete.setText(cargarCotizacionBlue());
    }

    @FXML
    void btnBachasClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnBachas.getScene().getWindow());
        sceneController.switchToBachas();
    }

    @FXML
    void btnCajaClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnCaja.getScene().getWindow());
        sceneController.switchToRegistrarIngreso();
    }

    @FXML
    void btnCerrarSesionClicked(ActionEvent event) {
        User user = new User("", "", "", "", 0, 0);
        User.setCurrentUser(user);
        System.gc();
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToLogin();
    }

    @FXML
    void btnVentasClicked(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            MenuButton menuButton = (MenuButton) menuItem.getParentPopup().getOwnerNode();
            Stage stage = (Stage) menuButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/verona/view/cargarVentas.fxml"));
            AnchorPane rootPane = loader.load();

            SceneController sceneController = new SceneController(stage);
            sceneController.switchToVentas(rootPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnVentasBuscarClicked(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            MenuButton menuButton = (MenuButton) menuItem.getParentPopup().getOwnerNode();
            Stage stage = (Stage) menuButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/verona/view/buscarVentas.fxml"));
            AnchorPane rootPane = loader.load();

            SceneController sceneController = new SceneController(stage);
            sceneController.switchToVentas(rootPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnVentasModificarEstadoClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnCaja.getScene().getWindow());
        sceneController.switchToModificarEstadoVenta();
    }

    private void mostrarFechaActual() {

        LocalDate fechaActual = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        fechaLabel.setText(fechaFormateada);
    }

    ApiDolarOficial apiOficial = new ApiDolarOficial();
    ApiDolarBlue apiBlue = new ApiDolarBlue();

    private String cargarCotizacionOficial() {
        double dolarOficial = ApiDolarOficial.obtenerDolarOficial();
        String dolarOficialReal = Double.toString(dolarOficial);

        return dolarOficialReal;
    }

    private String cargarCotizacionBlue() {
        double dolarBlue = ApiDolarBlue.obtenerDolarBlue();
        String dolarBlueReal = Double.toString(dolarBlue);

        return dolarBlueReal;
    }

    @FXML
    void btnVerPrecioBachasClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVerPrecioBachas.getScene().getWindow());
        sceneController.switchToVerPrecioBachas();
    }

    @FXML
    void btnVerPrecioManoObraClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVerPrecioManoObra.getScene().getWindow());
        sceneController.switchToVerPrecioManoObra();
    }

    @FXML
    void btnVerPrecioMaterialesClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVerPrecioMateriales.getScene().getWindow());
        sceneController.switchToVerPrecioMateriales();
        ;
    }

}