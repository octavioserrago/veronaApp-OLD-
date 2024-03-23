package com.verona.controller.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.verona.controller.SceneController;
import com.verona.model.ApiDolarOficial;
import com.verona.model.ApiDolarBlue;
import com.verona.model.User;

public class RealizarPresupuestoController {

    @FXML
    private Button btnVolver;

    @FXML
    private Label dolarOficialLabel;

    @FXML
    void initialize() {
        double valorDolar = ApiDolarOficial.obtenerDolarOficial();
        double valorDolarBlue = ApiDolarBlue.obtenerDolarBlue();
        dolarOficialLabel.setText(String.valueOf("oficial: " + valorDolar + "Dolar Blue" + valorDolarBlue));
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        User user = User.getCurrentUser();

        if (user != null) {
            SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());

            switch (user.getRoleID()) {
                case 1:
                    sceneController.switchToManagerDashboard();
                    break;
                case 2:
                    sceneController.switchToDashboardSeller();
                    break;
                case 3:
                    // Lógica para el administrador
                    break;
                default:
                    System.out.println("Error relacionado al ROL");
                    break;
            }
        }
    }
}
