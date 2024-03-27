package com.verona.controller.manager;

import com.verona.controller.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificarPlanoController {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField textFieldCodigoPlano;

    @FXML
    void btnBuscarClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToVerProduccion();
    }

}
