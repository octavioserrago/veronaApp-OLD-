package com.verona.controller.common;

import com.verona.controller.SceneController;
import com.verona.model.User;
import com.verona.model.Venta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificarEstadoVentaController {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCargar;

    @FXML
    private ComboBox<String> btnComboBox;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField idTextFieldToComplete;

    @FXML
    private Label labelIndicacion;

    Venta venta = new Venta(0, null, null, null, null, null, 0, 0, 0, null, null, 0, null, null, null, 0, 0);
    private User user = User.getCurrentUser();

    @FXML
    void initialize() {
        noVisibles();
        llenarComboBox();
    }

    private void llenarComboBox() {
        btnComboBox.getItems().addAll("Suspendida", "En Espera", "En produccion", "Terminada con pago incompleto",
                "Terminada con pago completo");
    }

    private void noVisibles() {
        btnComboBox.setVisible(false);
        labelIndicacion.setVisible(false);
        btnCargar.setVisible(false);
    }

    @FXML
    void btnBuscarClicked(ActionEvent event) {
        String ventasID = idTextFieldToComplete.getText();
        int sucursalUsuario = user.getSucursalID();

        if (ventasID.isBlank()) {
            mostrarAlertaError("Por favor, ingresa un número en el campo.");
        } else {
            if (ventasID.matches("\\d+")) {
                try {
                    int ventasIDNumero = Integer.parseInt(ventasID);
                    if (usuarioTienePermisoActualizarVenta(sucursalUsuario, ventasIDNumero)) {
                        btnComboBox.setVisible(true);
                        labelIndicacion.setVisible(true);
                        btnCargar.setVisible(true);

                        mostrarAlertaInformativa("Venta encontrada exitosamente.");
                    } else {
                        mostrarAlertaError("La venta no se encuentra en la sucursal del usuario.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlertaError("El valor ingresado no es un número válido.");
                }
            } else {
                mostrarAlertaError("El valor ingresado no es un número válido.");
            }
        }
    }

    private boolean usuarioTienePermisoActualizarVenta(int sucursalUsuario, int ventasID) {
        String estadoVenta = venta.obtenerEstadoVenta(sucursalUsuario, ventasID);
        if (estadoVenta != null) {
            return true;
        } else {
            return false;
        }
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaInformativa(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void btnCargarClicked(ActionEvent event) {
        String ventasID = idTextFieldToComplete.getText();
        int sucursalUsuario = user.getSucursalID();

        if (ventasID.isBlank()) {
            mostrarAlertaError("Por favor, ingresa un número en el campo.");
        } else {
            if (ventasID.matches("\\d+")) {
                try {
                    int ventasIDNumero = Integer.parseInt(ventasID);
                    if (usuarioTienePermisoActualizarVenta(sucursalUsuario, ventasIDNumero)) {
                        String nuevoEstado = btnComboBox.getValue();
                        if (venta.actualizarEstadoVenta(ventasIDNumero, nuevoEstado)) {
                            mostrarAlertaInformativa("Venta actualizada exitosamente.");
                        } else {
                            mostrarAlertaError("Error al actualizar el estado de la venta.");
                        }
                    } else {
                        mostrarAlertaError("La venta no se encuentra en la sucursal del usuario.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlertaError("El valor ingresado no es un número válido.");
                }
            } else {
                mostrarAlertaError("El valor ingresado no es un número válido.");
            }
        }
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        User user = User.getCurrentUser();

        if (user != null) {
            SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());

            switch (user.getRoleID()) {
                case 1:
                    sceneController.switchToManagerDashboard();
                    break;
                case 2:
                    sceneController.switchToDashboardSeller();
                    break;
                case 3:
                    // Lógica para el administrador
                    break;
                default:
                    System.out.println("Error relacionado al ROL");
                    break;
            }
        }
    }

}
