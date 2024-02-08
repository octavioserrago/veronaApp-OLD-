package com.verona.controller.manager;

import com.verona.controller.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VerBalancesController {

    @FXML
    private Label SeñasEfectivoLabelToComplete;

    @FXML
    private Button btnArqueo;

    @FXML
    private Button btnArqueosPasados;

    @FXML
    private Button btnVolver;

    @FXML
    private Label cajaBancoLabelToComplete;

    @FXML
    private Label cajaEfectivoLabelToComplete;

    @FXML
    private Label señasBancoLabelToComplete;

    @FXML
    private Label totalCajaToComplete;

    @FXML
    private Label totalSeñasToComplete;

    @FXML
    void btnArqueoClicked(ActionEvent event) {

    }

    @FXML
    void btnArqueosPasadosClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }
}