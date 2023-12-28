package Controllers.Common;

import Controllers.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class cargarVentasController {

    @FXML
    private CheckBox bachaASK;

    @FXML
    private ComboBox<?> bachasOption;

    @FXML
    private Button btnCargarVenta;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox colocadorASK;

    @FXML
    private ComboBox<?> colocadorOptions;

    @FXML
    private TextField colorTextField;

    @FXML
    private TextArea descripcionTextFIeld;

    @FXML
    private TextField emailTextField;

    @FXML
    private DatePicker fechaTerminacionSelect;

    @FXML
    private TextField importeTextField;

    @FXML
    private TextField materialTextField;

    @FXML
    private Label msjErrorLogro;

    @FXML
    private TextField nombreClienteTextField;

    @FXML
    private TextField telefonoSecundarioTextField;

    @FXML
    private TextField telefonoTextField;

    
    @FXML
    void initialize() {
       colocadorOptions.setVisible(false);
        colocadorASK.setOnAction(event -> {
            if (colocadorASK.isSelected()) {
                colocadorOptions.setVisible(true);
            } else {
                if (!colocadorASK.isSelected()) {
                    colocadorOptions.setVisible(false);
                }
            }
        });
        bachasOption.setVisible(false);
        bachaASK.setOnAction(event -> {
            if (bachaASK.isSelected()) {
                bachasOption.setVisible(true);
            } else {
                if (!bachaASK.isSelected()) {
                    bachasOption.setVisible(false);
                }
            }
        });
    }

    @FXML
    void btnCargarVentaClicked(ActionEvent event) {

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
    SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardSeller();
    }

}

