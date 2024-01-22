package Controllers.Manager;

import Controllers.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class managerDashboardController {

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private ComboBox<String> comboboxBachas;

    @FXML
    private ComboBox<String> comboboxCaja;

    @FXML
    private Button btnNegocio;

    @FXML
    private ComboBox<String> comboboxVentas;

    @FXML
    private Button btnEmpleados;
    @FXML
    void btnCerrarSesionClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToLogin();
    }

    @FXML
    void initialize() {
    
        ObservableList<String> bachasItems = FXCollections.observableArrayList("Ver Stock de Bachas", "Agregar cantidad de Bachas", "Modificar Cantidad de bachas", "Agregar modelo nuevo", "Modificar modelo");
        comboboxBachas.setItems(bachasItems);

        
        ObservableList<String> cajaItems = FXCollections.observableArrayList("Ver Balances", "Entradas", "Salidas");
        comboboxCaja.setItems(cajaItems);

        
        ObservableList<String> ventasItems = FXCollections.observableArrayList("Crear nueva Venta", "Buscar Venta", "Modificar datos de una venta", "Cargar Foto de Plano","Eliminar una venta");
        comboboxVentas.setItems(ventasItems);

        comboboxVentas.setOnAction(event -> {
            String selectedItem = comboboxVentas.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                handleVentasItemSelected(selectedItem);
            }
        });

        
    }

    @FXML
    void btnNegocioClicked(ActionEvent event) {

    }

    @FXML
    void btnEmpleadosClicked(ActionEvent event) {

    }

    

    private void handleVentasItemSelected(String selectedItem) {
        if ("Cargar Foto de Plano".equals(selectedItem)) {
            cargarFotoDePlano();
        }
    }

    private void cargarFotoDePlano() {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToCargarFotoPlano();
    }
}
