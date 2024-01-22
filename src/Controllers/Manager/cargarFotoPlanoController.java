package Controllers.Manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import Controllers.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.nio.file.Path;


public class cargarFotoPlanoController {
    

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnSeleccionarArchivo;

    @FXML
    private Label firstAlert;

    @FXML
    private TextField idTextField;

    @FXML
    private Label labelIndicator;

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardManager();
    }

    @FXML
    void btnCargarClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardManager();
    }

    @FXML
    void btnSeleccionarArchivoClicked(ActionEvent event) {
        cargarFotoDePlano();
    }

    @FXML
    void cargarFotoDePlano() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Foto de Plano");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            String idPlano = idTextField.getText();
            String rutaGuardarFoto = "src/Resources/img/blueprints/" + idPlano + ".png";
            Path destino = new File(rutaGuardarFoto).toPath();

            // Verificar si el archivo ya existe
            if (Files.exists(destino)) {
                mostrarAlerta("Ya existe una foto de ese plano. Borre la foto e intente nuevamente.", AlertType.ERROR);
            } else {
                try {
                    Files.copy(selectedFile.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                    mostrarAlerta("Foto cargada con éxito.", AlertType.INFORMATION);
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error al cargar la foto.", AlertType.ERROR);
                }
            }
        }
    }

    private void mostrarAlerta(String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);


        if (tipo == AlertType.ERROR) {
            labelIndicator.setTextFill(Color.RED);
        } else if (tipo == AlertType.INFORMATION) {
            labelIndicator.setTextFill(Color.GREEN);
        }
        alert.showAndWait();
    }
}




