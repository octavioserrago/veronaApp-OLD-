package com.verona.controller.manager;

import com.verona.controller.SceneController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AsignarBachasPlanoController {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<?> comboboxBachas;

    @FXML
    private Label labelIndicativo;

    @FXML
    private TextField textFieldCodigoPlano;

    @FXML
    void initialize() {
        noVisibles();
    }

    public void noVisibles() {
        labelIndicativo.setVisible(false);
        comboboxBachas.setVisible(false);
        btnCargar.setVisible(false);
    }

    @FXML
    void btnBuscarClicked(ActionEvent event) {

    }

    @FXML
    void btnCargarClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToVerProduccion();
    }

}
