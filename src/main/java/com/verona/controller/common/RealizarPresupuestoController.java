package com.verona.controller.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.verona.controller.SceneController;
import com.verona.model.User;

public class RealizarPresupuestoController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<?> coloresTable;

    @FXML
    private TextField m2TextFieldToComplete;

    @FXML
    private TextField nombreClienteTextFieldToComplete;

    @FXML
    void initialize() {

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
