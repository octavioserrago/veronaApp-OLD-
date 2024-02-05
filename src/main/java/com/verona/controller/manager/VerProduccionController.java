package com.verona.controller.manager;

import com.verona.controller.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class VerProduccionController {

    @FXML
    private Button btnBorrarPlano;

    @FXML
    private Button btnCargarPlanos;

    @FXML
    private Button btnModificarPlano;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<?> tablaPlanosProduccion;

    @FXML
    void btnBorrarPlanoClicked(ActionEvent event) {

    }

    @FXML
    void btnCargarPlanosClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToCargarPlano();
    }

    @FXML
    void btnModificarPlanoClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }

}