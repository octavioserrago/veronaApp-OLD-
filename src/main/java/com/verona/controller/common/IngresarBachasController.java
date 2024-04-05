package com.verona.controller.common;

import com.verona.controller.SceneController;
import com.verona.model.Bacha;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class IngresarBachasController {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField cantidadAAgregarTextField;

    @FXML
    private ComboBox<String> comboboxModelos;

    private Bacha bacha = new Bacha(0, null, null, 0);

    @FXML
    void initialize() {
        try {
            llenarComboboxModelos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void llenarComboboxModelos() throws SQLException {
        List<String> modelos = bacha.obtenerModelos();
        ObservableList<String> modelosObservable = FXCollections.observableArrayList(modelos);
        comboboxModelos.setItems(modelosObservable);
    }

    @FXML
    void btnCargarClicked(ActionEvent event) {
        String nombreModeloSeleccionado = comboboxModelos.getValue();
        if (nombreModeloSeleccionado == null || nombreModeloSeleccionado.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un modelo de bacha.");
            alert.showAndWait();
            return;
        }
        String[] partes = nombreModeloSeleccionado.split(" - ");
        String modelo = partes[0];
        System.out.println(modelo);

        try {
            int bachaID = bacha.obtenerIDBachaPorNombre(modelo);
            int cantidadAAgregar = Integer.parseInt(cantidadAAgregarTextField.getText());
            bacha.agregarBachas(bachaID, cantidadAAgregar);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Se ha cargado la cantidad de bachas correctamente.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("La cantidad ingresada no es válida.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al obtener el ID de la bacha.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToBachas();
    }

}
