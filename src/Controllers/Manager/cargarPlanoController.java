package Controllers.Manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import Controllers.SceneController;
import Data.Plano;
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

public class cargarPlanoController {
    
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
    private File selectedFile;

    @FXML
    private TextField colorTextField;

    @FXML
    private TextField materialTextField;

    Plano plano = new Plano(0, null, "","",0);
    private final FileChooser fileChooser = new FileChooser();

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }

    @FXML
    void btnCargarClicked(ActionEvent event) {
        String ventas = idTextField.getText();
        int ventasID = Integer.parseInt(ventas);
        String codigoPlano = plano.generadorCodigoPlano(ventasID);
        plano.setCodigoPlano(codigoPlano);

        if (colorTextField.getText().equals("")) {
            try {
                plano.cargarPlanoMaterialCliente(codigoPlano, materialTextField.getText(),ventasID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                plano.cargarPlanoMaterialEmpresa(codigoPlano, materialTextField.getText(), colorTextField.getText(),ventasID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        cargarFotoDePlano();
    }

    @FXML
    void btnSeleccionarArchivoClicked(ActionEvent event) {
        seleccionar();
    }
    
    public void seleccionar(){
        fileChooser.setTitle("Seleccionar Foto de Plano");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        selectedFile = fileChooser.showOpenDialog(new Stage());
    }

    void cargarFotoDePlano() {
        if (selectedFile != null) {
            String rutaGuardarFoto = "src/Resources/img/blueprints/" + plano.getCodigoPlano() + ".png";
            Path destino = new File(rutaGuardarFoto).toPath();

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
            firstAlert.setTextFill(Color.RED);
            firstAlert.setVisible(true);
        } else if (tipo == AlertType.INFORMATION) {
            firstAlert.setTextFill(Color.GREEN);
            firstAlert.setVisible(true);
        }
        alert.showAndWait();
    }
}
