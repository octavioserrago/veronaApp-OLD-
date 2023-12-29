package Controllers.Common;


import java.sql.SQLException;
import java.util.List;

import Controllers.SceneController;
import Data.Bachas;
import Data.Colocador;
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
    private ComboBox<Bachas> bachasOption;

    @FXML
    private Button btnCargarVenta;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox colocadorASK;

    @FXML
    private ComboBox<String> colocadorOptions; // Cambiado a String

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
    private Label precioColocacionLabel;

    @FXML
    private TextField precioColocacionTextField;

    @FXML
    void initialize() {
        noVisibles();

        colocadorASK.setOnAction(event -> {
            colocadorOptions.setVisible(colocadorASK.isSelected());
            precioColocacionLabel.setVisible(colocadorASK.isSelected());
            precioColocacionTextField.setVisible(colocadorASK.isSelected());
        });
        llenarComboBoxColocadores();
        llenarComboBoxBachas();

        bachasOption.setVisible(false);
        bachaASK.setOnAction(event -> bachasOption.setVisible(bachaASK.isSelected()));
    }

    @FXML
    void btnCargarVentaClicked(ActionEvent event) {
        String nombreCliente = nombreClienteTextField.getText();
        String descripcion = descripcionTextFIeld.getText();
        String material = materialTextField.getText();
        String color = colorTextField.getText();
        //Bachas bacha = bachasOption.getSelectionModel().getSelectedItem();
        //String nombreModelo = bacha.getNombreModelo();
        String fechaEstimadaTerminacion = fechaTerminacionSelect.getValue().toString();
        //int colocadoresID = colocadorOptions.getSelectionModel().getSelectedItem();
        try {
            String importeStr = importeTextField.getText();
            double importe = Double.parseDouble(importeStr);
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir la cadena a double: " + e.getMessage());
        }
        String estado = "En Espera";
        String telefono1 = telefonoTextField.getText();
        String telefono2 = telefonoSecundarioTextField.getText();
        String email = emailTextField.getText();
        


    
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardSeller();
    }

    public void noVisibles() {
        colocadorOptions.setVisible(false);
        bachasOption.setVisible(false);
        precioColocacionLabel.setVisible(false);
        precioColocacionTextField.setVisible(false);
    }

    private void llenarComboBoxBachas() {
        Bachas bacha = new Bachas(0, null, null, 0);
        try {
            List<Bachas> bachasList = bacha.obtenerBachas();

            bachasOption.getItems().clear();
            bachasOption.getItems().addAll(bachasList);

            if (!bachasList.isEmpty()) {
                bachasOption.setValue(bachasList.get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void llenarComboBoxColocadores() {
        Colocador colocador = new Colocador(null, null, null, null);
        try {
            List<String> nombresColocadores = colocador.obtenerNombresColocadores();

            colocadorOptions.getItems().clear(); // Cambiado a colocadorOptions
            colocadorOptions.getItems().addAll(nombresColocadores);

            if (!nombresColocadores.isEmpty()) {
                colocadorOptions.setValue(nombresColocadores.get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
