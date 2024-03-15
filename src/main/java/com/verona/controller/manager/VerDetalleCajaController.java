package com.verona.controller.manager;

import com.verona.controller.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class VerDetalleCajaController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<?> tableDetails;

    @FXML
    void btnActualizarClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToVerBalances();
    }

}
