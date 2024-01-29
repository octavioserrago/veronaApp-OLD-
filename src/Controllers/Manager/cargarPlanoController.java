package Controllers.Manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import Data.Colores;
import Data.DatabaseConnection;
import Data.Material;
import Controllers.SceneController;
import Data.Plano;
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
    private ComboBox<String> colorCombobox;

    @FXML
    private ComboBox<Material> materialComboBox;

    @FXML
    private TextField idTextField;

    @FXML
    private Label labelIndicator;

    @FXML
    private File selectedFile;

    Material material = new Material("");

    Plano plano = new Plano("", 0, 0, null, 0, "");
    private final FileChooser fileChooser = new FileChooser();

    DatabaseConnection con = new DatabaseConnection();
    Connection conexion;

    @FXML
    public void initialize() throws SQLException {
        try {
            conexion = con.conectar();
            List<Material> listaMateriales = Material.obtenerListaMateriales(conexion);
            ObservableList<Material> materialesItems = FXCollections.observableArrayList(listaMateriales);
            materialComboBox.setItems(materialesItems);
        } finally {
            cerrarConexion();
        }
    }

    @FXML
    void materialComboBoxChanged(ActionEvent event) {
        try {
            conexion = con.conectar();
            Material materialSeleccionado = materialComboBox.getValue();

            if (materialSeleccionado != null) {
                int materialID = Material.obtenerMaterialID(conexion, materialSeleccionado.getTipoMaterial());
                plano.setMaterialID(materialID);
                List<String> listaColores = Colores.obtenerListaColoresPorMaterial(conexion, materialID);
                ObservableList<String> coloresItems = FXCollections.observableArrayList(listaColores);
                colorCombobox.setItems(coloresItems);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

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
        sceneController.switchToManagerDashboard();
    }

    @FXML
    void btnCargarClicked(ActionEvent event) {
        String ventas = idTextField.getText();
        int ventasID = Integer.parseInt(ventas);
        String codigoPlano = plano.generadorCodigoPlano(ventasID);
        plano.setCodigoPlano(codigoPlano);
        Material materialSeleccionado = materialComboBox.getValue();

        if (materialSeleccionado != null) {
            String tipoMaterialSeleccionado = materialSeleccionado.getTipoMaterial();
            int materialID = plano.obtenerMaterial(tipoMaterialSeleccionado);
            String colorSeleccionado = colorCombobox.getValue();

            try {
                conexion = con.conectar();

                int materialColorPrecioID = Colores.obtenerColoresID(conexion, colorSeleccionado);

                byte[] imgBytes = obtenerBytesDeImagen(selectedFile);

                plano.cargarPlano(codigoPlano, materialID, materialColorPrecioID, imgBytes, ventasID, "En Producción");

                mostrarAlerta("Plano cargado con éxito.", AlertType.INFORMATION);
            } catch (SQLException | IOException e) {
                e.printStackTrace();

                mostrarAlerta("Error al cargar el plano. Consulte el registro para más detalles.", AlertType.ERROR);
            } finally {
                cerrarConexion();
            }
        } else {
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
