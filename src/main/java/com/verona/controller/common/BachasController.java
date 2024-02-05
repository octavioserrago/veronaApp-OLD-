package com.verona.controller.common;

import com.verona.controller.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class BachasController {

    @FXML
    private Button BtnActualizarCantidad;

    @FXML
    private Button btnIngresarBachas;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<?> tablaBachasStock;

    @FXML
    void btnActualizarCantidadClicked(ActionEvent event) {

    }

    @FXML
    void btnIngresarBachasClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardSeller();
    }

}