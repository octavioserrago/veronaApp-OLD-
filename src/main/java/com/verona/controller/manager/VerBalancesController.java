package com.verona.controller.manager;

import com.verona.controller.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VerBalancesController {

    @FXML
    private Button btnArqueo;

    @FXML
    private Button btnSalidasDia;

    @FXML
    private Button btnVerIngresosDia;

    @FXML
    private Button btnVolver;

    @FXML
    private Label dineroCajaAhorroLabel;

    @FXML
    private Label dineroEfectivoLabel;

    @FXML
    private Label dineroTotalLabel;

    @FXML
    private Label seniasLabel;

    @FXML
    void btnArqueoClicked(ActionEvent event) {

    }

    @FXML
    void btnSalidasDiaClicked(ActionEvent event) {

    }

    @FXML
    void btnVerIngresosDiaClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }
}