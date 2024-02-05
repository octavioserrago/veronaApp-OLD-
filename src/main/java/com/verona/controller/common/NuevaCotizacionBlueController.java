package com.verona.controller.common;

import java.sql.SQLException;

import com.verona.controller.SceneController;
import com.verona.model.Cotizacion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NuevaCotizacionBlueController {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField cotizacionTextField;

    @FXML
    private Label resultadoLabel;

    @FXML
    void btnCargarClicked(ActionEvent event) {
        Cotizacion cotizacion = new Cotizacion(null, 0);

        String cotizacionText = cotizacionTextField.getText();

        if (cotizacionText.isEmpty() || !cotizacionText.matches("^\\d*\\.?\\d*$")) {
            String mensajeError1 = "La cotización está vacía o contiene caracteres no permitidos (no números o puntos)";
            mostrarAlerta(mensajeError1, AlertType.ERROR);
            resultadoLabel.setTextFill(Color.RED);
        } else {
            try {
                double nuevaCotizacion = Double.parseDouble(cotizacionText);

                cotizacion.insertarCotizacionBlue(3, 1, 1, nuevaCotizacion);
                mostrarAlerta("Éxito al insertar la nueva cotización:\n1 USD = $" + nuevaCotizacion,
                        AlertType.INFORMATION);
                resultadoLabel.setTextFill(Color.GREEN);
            } catch (NumberFormatException e) {
                String mensajeError = "Error al insertar la nueva cotización. Por favor, verifica que la cotización sea un número válido.";
                mostrarAlerta(mensajeError, AlertType.ERROR);
                resultadoLabel.setTextFill(Color.RED);
            } catch (SQLException e) {
                e.printStackTrace();
                String mensajeError = "Error al insertar la nueva cotización. Por favor, inténtalo de nuevo.";

                if (e.getMessage() != null && e.getMessage().toLowerCase().contains("unique constraint")) {
                    mensajeError = "Ya existe una cotización para esta moneda y este tipo de cambio.";
                }
                mostrarAlerta(mensajeError, AlertType.ERROR);
                resultadoLabel.setTextFill(Color.rgb(255, 0, 0));
            }
        }
    }

    private void mostrarAlerta(String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToCotizaciones();
    }

}