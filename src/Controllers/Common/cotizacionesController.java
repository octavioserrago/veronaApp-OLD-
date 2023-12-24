package Controllers.Common;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Controllers.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class cotizacionesController implements Initializable {

    @FXML
    private Button btnNCB;

    @FXML
    private Button btnNCCCL;

    @FXML
    private Button btnNCOficial;

    @FXML
    private Button btnVolver;

    @FXML
    private Label fecha;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mostrarFechaActual();
    }

    @FXML
    void btnNCBClicked(ActionEvent event) {
        // Aquí puedes agregar código para manejar el clic en el botón NCB si es necesario
    }

    @FXML
    void btnNCCCLClicked(ActionEvent event) {
        // Aquí puedes agregar código para manejar el clic en el botón NCCCL si es necesario
    }

    @FXML
    void btnNCOficialClicked(ActionEvent event) {
        // Aquí puedes agregar código para manejar el clic en el botón NCOficial si es necesario
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardSeller();
    }

    private void mostrarFechaActual() {
        try {
            LocalDate fechaActual = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = fechaActual.format(formatter);

            fecha.setText(fechaFormateada);
        } catch (DateTimeException e) {
            e.printStackTrace();
        }
    }
}
