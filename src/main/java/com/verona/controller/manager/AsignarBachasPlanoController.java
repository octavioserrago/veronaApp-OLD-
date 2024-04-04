package com.verona.controller.manager;

import java.sql.SQLException;
import java.util.List;

import com.verona.controller.SceneController;
import com.verona.model.Bacha;
import com.verona.model.Plano;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private ComboBox<Bacha> comboboxBachas;

    @FXML
    private Label labelIndicativo;

    @FXML
    private TextField textFieldCodigoPlano;

    private int idPlano;

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    Plano plano = new Plano(null, null, null, null, 0, null, null);
    Bacha bacha = new Bacha(0, null, null, 0);

    @FXML
    void initialize() throws SQLException {
        noVisibles();
        cargarBachasEnComboBox();
    }

    public void noVisibles() {
        labelIndicativo.setVisible(false);
        comboboxBachas.setVisible(false);
        btnCargar.setVisible(false);
    }

    @FXML
    void btnBuscarClicked(ActionEvent event) {
        String codigoPlano = textFieldCodigoPlano.getText();
        if (!codigoPlano.isEmpty()) {
            try {
                int idPlano = plano.buscarPlano(codigoPlano);

                if (idPlano > 0) {
                    labelIndicativo.setVisible(true);
                    comboboxBachas.setVisible(true);
                    btnCargar.setVisible(true);
                    setIdPlano(idPlano);
                } else {

                    labelIndicativo.setVisible(false);
                    comboboxBachas.setVisible(false);
                    btnCargar.setVisible(false);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Plano no encontrado");
                    alert.setHeaderText(null);
                    alert.setContentText("El plano con el código especificado no se encontró.");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo Vacío");
            alert.setHeaderText(null);
            alert.setContentText("Por favor complete el campo de código de plano.");
            alert.showAndWait();
        }
    }

    private void cargarBachasEnComboBox() throws SQLException {
        List<Bacha> bachas = bacha.obtenerBachas();
        ObservableList<Bacha> observableBachas = FXCollections.observableArrayList(bachas);
        comboboxBachas.setItems(observableBachas);
    }

    @FXML
    void btnCargarClicked(ActionEvent event) {
        Bacha bachaSeleccionada = comboboxBachas.getValue();
        if (bachaSeleccionada != null) {
            int cantidadADescontar = bachaSeleccionada.getCantidad() - 1;
            try {
                int idBacha = bachaSeleccionada.getBachasID();
                bachaSeleccionada.descontarCantidad(idBacha, cantidadADescontar, getIdPlano());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Operación Exitosa");
                alert.setHeaderText(null);
                alert.setContentText("Bacha asignada correctamente al plano.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al asignar la bacha al plano.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Bacha no seleccionada");
            alert.setHeaderText(null);
            alert.setContentText("Por favor seleccione una bacha.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToVerProduccion();
    }

}
