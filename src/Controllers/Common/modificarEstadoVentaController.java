package Controllers.Common;

import Controllers.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class modificarEstadoVentaController {

    @FXML
    private Button btnBuscar;

    @FXML
    private Label thirdAlert;

    @FXML
    private ComboBox<?> btnComboBox;

    @FXML
    private Button btnVolver;

    @FXML
    private Label firstAlert;

    @FXML
    private TextField idTextFieldToComplete;

    @FXML
    private Label labelIndicacion;

    @FXML
    private Label secondAlert;

    @FXML
    void initialize() {
        noVisibles();
    }

    private void noVisibles(){
        btnComboBox.setVisible(false);
        labelIndicacion.setVisible(false);
    }
    
    @FXML
    void btnBuscarClicked(ActionEvent event) {
      
        
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
      
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardSeller();
    }

    

}
