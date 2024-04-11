package com.verona.controller.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import com.verona.controller.SceneController;
import com.verona.model.Plano;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ModificarPlanoController {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField textFieldCodigoPlano;

    private Plano plano = new Plano(null, null, null, null, 0, null, null);
    int planoID = -1;

    @FXML
    void btnBuscarClicked(ActionEvent event) throws SQLException {
        String codigoPlano = textFieldCodigoPlano.getText();
        if (!codigoPlano.isEmpty()) {
            planoID = plano.buscarPlano(codigoPlano);
            if (planoID != -1) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Plano encontrado");
                alert.setHeaderText(null);
                alert.setContentText("Plano encontrado. ¿Qué desea modificar?");

                ButtonType materialColorButton = new ButtonType("Material y Color");
                ButtonType fechaButton = new ButtonType("Fecha de Terminación");
                ButtonType imagenButton = new ButtonType("Imagen");

                alert.getButtonTypes().setAll(materialColorButton, fechaButton, imagenButton);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent()) {
                    if (result.get() == materialColorButton) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Material y Color");
                        dialog.setHeaderText(null);
                        dialog.setContentText("Ingrese el material y el color separados por coma (,):");
                        Optional<String> materialColorResult = dialog.showAndWait();

                        materialColorResult.ifPresent(materialColor -> {
                            String[] valores = materialColor.split(",");
                            if (valores.length == 2) {
                                String material = valores[0].trim();
                                String color = valores[1].trim();

                                try {
                                    plano.actualizarMaterialColor(material, color, planoID);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Alert errorAlert = new Alert(AlertType.ERROR);
                                errorAlert.setTitle("Error");
                                errorAlert.setHeaderText(null);
                                errorAlert.setContentText("Ingrese el material y el color separados por coma (,).");
                                errorAlert.showAndWait();
                            }
                        });
                    } else if (result.get() == fechaButton) {
                        DatePicker datePicker = new DatePicker();
                        datePicker.setPromptText("Selecciona la nueva fecha");

                        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert1.setTitle("Nueva fecha de finalización");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Ingresa la nueva fecha de finalización:");

                        GridPane grid = new GridPane();
                        grid.add(new Label("Fecha:"), 0, 0);
                        grid.add(datePicker, 1, 0);
                        alert1.getDialogPane().setContent(grid);

                        Optional<ButtonType> resultDate = alert1.showAndWait();

                        resultDate.ifPresent(buttonType -> {
                            if (buttonType == ButtonType.OK) {
                                LocalDate selectedDate = datePicker.getValue();
                                if (selectedDate != null) {
                                    java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
                                    try {
                                        plano.cambiarFechaTerminacion(sqlDate, planoID);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setTitle("Error");
                                errorAlert.setHeaderText(null);
                                errorAlert.setContentText("Por favor, selecciona una fecha.");
                                errorAlert.showAndWait();
                            }
                        });
                    } else if (result.get() == imagenButton) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Selecciona una imagen");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.jpeg"),
                                new FileChooser.ExtensionFilter("Todos los archivos", "*.*"));

                        File selectedFile = fileChooser.showOpenDialog(null);
                        if (selectedFile != null) {
                            try {
                                byte[] imgBytes = Files.readAllBytes(selectedFile.toPath());
                                plano.cambiarImagenPlano(imgBytes, planoID);

                            } catch (IOException | SQLException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Error");
                            errorAlert.setHeaderText(null);
                            errorAlert.setContentText("Por favor, selecciona una imagen.");
                            errorAlert.showAndWait();
                        }
                    }

                }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Plano no encontrado");
                alert.setHeaderText(null);
                alert.setContentText("No se encontró el plano. Revise si el código está ingresado correctamente.");
                alert.showAndWait();
            }
        } else

        {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campo vacío");
            alert.setHeaderText(null);
            alert.setContentText("No has ingresado ningún código de plano.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToVerProduccion();
    }

}
