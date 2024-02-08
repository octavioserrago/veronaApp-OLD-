package com.verona.controller.common;

import com.verona.controller.SceneController;
import com.verona.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class VerVentasDeSucursalController {

    @FXML
    private Button btnCargarFiltradas;

    @FXML
    private Button btnVolver;

    @FXML
    private DatePicker dateFromPicker;

    @FXML
    private DatePicker dateToPicker;

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
