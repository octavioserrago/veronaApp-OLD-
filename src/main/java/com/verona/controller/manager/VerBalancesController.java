package com.verona.controller.manager;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import com.verona.controller.SceneController;
import com.verona.model.Caja;
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
    Caja caja = new Caja(0, 0);
    private User user = User.getCurrentUser();
    @SuppressWarnings("deprecation")
    Locale localeArgentina = new Locale("es", "AR");
    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(localeArgentina);

    @FXML
    void initialize() throws SQLException {

        double cajaBanco = obtenerCajaBanco();
        double cajaEfectivo = obtenerCajaEfectivo();
        double cajaSeniasBanco = obtenerCajaSeniasBanco();
        double cajaSeniasEfectivo = obtenerCajaSeniasEfectivo();

        String cajaBancoFormateada = formatoMoneda.format(cajaBanco);
        String cajaEfectivoFormateada = formatoMoneda.format(cajaEfectivo);
        String cajaSeniasBancoFormateada = formatoMoneda.format(cajaSeniasBanco);
        String cajaSeniasEfectivoFormateada = formatoMoneda.format(cajaSeniasEfectivo);

        cajaBancoLabelToComplete.setText(cajaBancoFormateada);
        cajaEfectivoLabelToComplete.setText(cajaEfectivoFormateada);
        cajaSeñasBancoLabelToComplete.setText(cajaSeniasBancoFormateada);
        cajaSeñasEfectivoLabelToComplete.setText(cajaSeniasEfectivoFormateada);

        double dineroTotal = cajaBanco + cajaEfectivo + cajaSeniasBanco + cajaSeniasEfectivo;
        String dineroTotalFormateado = formatoMoneda.format(dineroTotal);
        dineroTotalLabelToComplete.setText(dineroTotalFormateado);
    }

    private double obtenerCajaBanco() throws SQLException {
        double cajaBanco = caja.obtenerUltimoSaldo(user.getSucursalID(), "CajaBanco");
        return cajaBanco;
    }

    private double obtenerCajaEfectivo() throws SQLException {
        double cajaEfectivo = caja.obtenerUltimoSaldo(user.getSucursalID(), "CajaEfectivo");
        return cajaEfectivo;
    }

    private double obtenerCajaSeniasBanco() throws SQLException {
        double cajaSeñaBanco = cajaSeñas.obtenerCajaSenia("importeBanco", user.getSucursalID());
        return cajaSeñaBanco;
    }

    private double obtenerCajaSeniasEfectivo() throws SQLException {
        double cajaSeñaEfectivo = cajaSeñas.obtenerCajaSenia("importeEfectivo", user.getSucursalID());
        return cajaSeñaEfectivo;
    }

    @FXML
    void btnVerDetalleCajasClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVerDetalleCajas.getScene().getWindow());
        sceneController.switchToVerDetalleCaja();
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }

}