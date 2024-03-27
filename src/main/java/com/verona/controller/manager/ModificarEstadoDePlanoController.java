package com.verona.controller.manager;

import java.sql.SQLException;

import com.verona.controller.SceneController;
import com.verona.model.Plano;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificarEstadoDePlanoController {
    @FXML
    private Label label;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> comboboxEstados;

    @FXML
    private TextField textFieldCodigoPlano;

    Plano plano = new Plano(null, null, null, null, 0, null, null);

    @FXML
    void initialize() {
        noVisibles();
        ObservableList<String> estados = FXCollections.observableArrayList(
                "En Suspension",
                "En Producción",
                "Terminado",
                "Terminado y Entregado");

        comboboxEstados.setItems(estados);
    }

    @FXML
    void btnBuscarClicked(ActionEvent event) {
        try {
            if (!textFieldCodigoPlano.getText().isEmpty()) {
                String codigoPlano = textFieldCodigoPlano.getText();
                int planosID = plano.buscarPlano(codigoPlano);
                if (planosID != -1) {
                    comboboxEstados.setVisible(true);
                    btnCargar.setVisible(true);
                    label.setVisible(true);
                } else {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error al modificar el estado del plano");
                    alert.setHeaderText(null);
                    alert.setContentText("El plano con el código " + codigoPlano + " no existe.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error al modificar el estado del plano");
                alert.setHeaderText(null);
                alert.setContentText(
                        "El campo de Codigo de Plano no puede estar vacío. Intente nuevamente");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void noVisibles() {
        comboboxEstados.setVisible(false);
        btnCargar.setVisible(false);
        label.setVisible(false);
    };

    @FXML
    void btnCargarClicked(ActionEvent event) throws SQLException {
        String codigoPlano = textFieldCodigoPlano.getText();
        int planosID = plano.buscarPlano(codigoPlano);
        String nuevoEstado = comboboxEstados.getSelectionModel().getSelectedItem();
        boolean modificarEstadoPlano = plano.modificarEstadoPlano(nuevoEstado, planosID);

        if (modificarEstadoPlano) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Estado del plano modificado exitosamente");
            alert.setHeaderText(null);
            alert.setContentText("El estado del plano se ha modificado correctamente.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error al modificar el estado del plano");
            alert.setHeaderText(null);
            alert.setContentText(
                    "No se pudo modificar el estado del plano. Verifica los datos e intenta nuevamente.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToVerProduccion();
    }
}
