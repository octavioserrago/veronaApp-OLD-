package com.verona.controller.manager;

import com.verona.controller.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrarPagoController {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<?> comboboxDescribirGasto;

    @FXML
    private ComboBox<?> comboboxProveedor;

    @FXML
    private ComboBox<?> fondosCombobox;

    @FXML
    private TextField importeTextFieldToComplete;

    @FXML
    private Label proveedorLabel;

    @FXML
    void initialize() {
        noVisibles();
    }

    private void noVisibles() {
        proveedorLabel.setVisible(false);
        comboboxProveedor.setVisible(false);
    }

    @FXML
    void btnCargarClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }

}
