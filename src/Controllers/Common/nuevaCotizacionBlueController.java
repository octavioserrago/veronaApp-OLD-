package Controllers.Common;

import Controllers.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class nuevaCotizacionBlueController {

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

    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToCotizaciones();
    }

}
