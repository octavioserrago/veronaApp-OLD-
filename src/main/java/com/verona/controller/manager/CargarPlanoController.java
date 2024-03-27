package com.verona.controller.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import com.verona.controller.SceneController;
import com.verona.model.DatabaseConnection;
import com.verona.model.Material;
import com.verona.model.Plano;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class CargarPlanoController {

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnSeleccionarArchivo;

    @FXML
    private Label firstAlert;

    @FXML
    private ComboBox<String> colorCombobox;

    @FXML
    private ComboBox<Material> materialComboBox;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField materialTextFieldToComplete;

    @FXML
    private TextField colorTextFieldToComplete;

    @FXML
    private Label labelIndicator;

    @FXML
    private DatePicker dateFechaTerminacion;

    @FXML
    private File selectedFile;

    Material material = new Material("");

    Plano plano = new Plano("", "", "", null, 0, "", "");
    private final FileChooser fileChooser = new FileChooser();

    DatabaseConnection con = new DatabaseConnection();
    Connection conexion;

    private void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToVerProduccion();
    }

    @FXML
    void btnCargarClicked(ActionEvent event) {
        String ventas = idTextField.getText();
        int ventasID = Integer.parseInt(ventas);
        String codigoPlano = plano.generadorCodigoPlano(ventasID);
        plano.setCodigoPlano(codigoPlano);
        String material = materialTextFieldToComplete.getText();
        String color = colorTextFieldToComplete.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaTerminacionStr = dateFechaTerminacion.getValue().format(formatter);

        if (material != "" && color != "") {

            try {
                conexion = con.conectar();

                byte[] imgBytes = obtenerBytesDeImagen(selectedFile);

                plano.cargarPlano(codigoPlano, material, color, imgBytes, ventasID, "En Producción",
                        fechaTerminacionStr);

                mostrarAlerta("Plano cargado con éxito.", AlertType.INFORMATION);
            } catch (SQLException | IOException e) {
                e.printStackTrace();

                mostrarAlerta("Error al cargar el plano. Consulte el registro para más detalles.", AlertType.ERROR);
            } finally {
                cerrarConexion();
            }
        } else

        {
            mostrarAlerta("No se ha seleccionado ningún material", AlertType.ERROR);
        }
    }

    @FXML
    void btnSeleccionarArchivoClicked(ActionEvent event) {
        seleccionar();
    }

    public void seleccionar() {
        fileChooser.setTitle("Seleccionar Foto de Plano");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        selectedFile = fileChooser.showOpenDialog(new Stage());
    }

    private byte[] obtenerBytesDeImagen(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    private void mostrarAlerta(String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        if (tipo == AlertType.ERROR) {
            firstAlert.setTextFill(Color.RED);
            firstAlert.setVisible(true);
        } else if (tipo == AlertType.INFORMATION) {
            firstAlert.setTextFill(Color.GREEN);
            firstAlert.setVisible(true);
        }
        alert.showAndWait();
    }
}