package com.verona.controller.manager;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import com.verona.controller.SceneController;
import com.verona.model.Senias;
import com.verona.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VerBalancesController {

    @FXML
    private Button btnVerDetalleCajas;

    @FXML
    private Button btnVolver;

    @FXML
    private Label cajaBancoLabelToComplete;

    @FXML
    private Label cajaEfectivoLabelToComplete;

    @FXML
    private Label cajaSeñasBancoLabelToComplete;

    @FXML
    private Label cajaSeñasEfectivoLabelToComplete;

    @FXML
    private Label dineroTotalLabelToComplete;

    Senias cajaSeñas = new Senias(0, 0, 0, 0, 0, 0);
    private User user = User.getCurrentUser();
    @SuppressWarnings("deprecation")
    Locale localeArgentina = new Locale("es", "AR");
    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(localeArgentina);

    @FXML
    void initialize() throws SQLException {

    }

    @FXML
    void btnVerDetalleCajasClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }

}