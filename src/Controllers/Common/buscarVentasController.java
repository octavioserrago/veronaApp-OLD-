package Controllers.Common;

import Controllers.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class buscarVentasController {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVolver;

    @FXML
    private Label colocadorLabel;

    @FXML
    private Label colocadorLabelToComplete;

    @FXML
    private Label colorLabel;

    @FXML
    private Label colorLabelToComplete;

    @FXML
    private Label descripcionLabel;

    @FXML
    private TextField descripcionTextFieldToComplete;

    @FXML
    private Label emailLabel;

    @FXML
    private Label emailLabelToComplete;

    @FXML
    private Label estadoLabel;

    @FXML
    private Label estadoLabelToComplete;

    @FXML
    private Label fechaCreacionLabel;

    @FXML
    private Label fechaCreacionLabelToComplete;

    @FXML
    private Label fechaTerminacionLabel;

    @FXML
    private Label fechaTerminacionLabelToComplete;

    @FXML
    private TextField idClienteTextField;

    @FXML
    private Label idLabel;

    @FXML
    private Label idLabelToComplete;

    @FXML
    private Label importeTotalLabel;

    @FXML
    private Label importeTotalLabelToComplete;

    @FXML
    private Label materialLabel;

    @FXML
    private Label materialLabelToComplete;

    @FXML
    private TextField nombreClienteTextField;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label nombreLabelToComplete;

    @FXML
    private Label precioColocacionLabel;

    @FXML
    private Label precioColocacionLabelToComplete;

    @FXML
    private Label resultadoLabel;

    @FXML
    private Label saldoLabel;

    @FXML
    private Label saldoLabelToComplete;

    @FXML
    private TableView<?> tablaCobros;

    @FXML
    private Label telefono2Label;

    @FXML
    private Label telefono2LabelToComplete;

    @FXML
    private Label telefonoLabel;

    @FXML
    private Label telefonoLabelToComplete;

    @FXML
    void btnBuscarClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardSeller();
    }

}

