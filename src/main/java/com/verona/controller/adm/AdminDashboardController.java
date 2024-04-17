package com.verona.controller.adm;

import java.util.Optional;

import com.verona.controller.SceneController;
import com.verona.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML
    private Button btnBachas;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnCuentas;

    @FXML
    private Button btnEmpleados;

    @FXML
    private Button btnEstadisticas;

    @FXML
    private Button btnPlanos;

    @FXML
    private Button btnProveedores;

    @FXML
    private Button btnSucursales;

    @FXML
    private Button btnVentas;

    @FXML
    void btnBachasClicked(ActionEvent event) {
        mostrarAlerta("Acciones de Bachas", "¿Qué acción desea realizar?",
                "Crear Nueva Bacha", "Modificar una Bacha", "Eliminar una Bacha", "Nueva Marca de Bachas",
                "Modificar Marca Bachas", "Eliminar una Marca de Bacha");
    }

    @FXML
    void btnCerrarSesionClicked(ActionEvent event) {
        User user = new User("", "", "", "", 0, 0);
        User.setCurrentUser(user);
        System.gc();
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToLogin();
    }

    @FXML
    void btnCuentasClicked(ActionEvent event) {
        mostrarAlerta("Acciones de Cuentas", "¿Qué acción desea realizar?",
                "Crear Cuenta Nueva", "Modificar una Cuenta", "Eliminar una Cuenta");
    }

    @FXML
    void btnEmpleadosClicked(ActionEvent event) {
        mostrarAlerta("Acciones de empleado", "¿Qué acción desea realizar?",
                "Cargar Nuevo Empleado", "Modificar Empleado",
                "Borrar Empleado");
    }

    @FXML
    void btnEstadisticasClicked(ActionEvent event) {
        mostrarAlerta("Acciones de Estadisticas", "¿Qué acción desea realizar?",
                "Ver todas las Estadisticas", "Ver estadisticas por Sucursal");
    }

    @FXML
    void btnPlanosClicked(ActionEvent event) {
        mostrarAlerta("Acciones de Planos", "¿Qué acción desea realizar?",
                "Borrar un Plano");
    }

    @FXML
    void btnProveedoresClicked(ActionEvent event) {
        mostrarAlerta("Acciones de Proveedores", "¿Qué acción desea realizar?",
                "Crear nuevo Proveedor", "Modificar Proveedor",
                "Borrar Proveedor");
    }

    @FXML
    void btnSucursalesClicked(ActionEvent event) {
        mostrarAlerta("Acciones de Sucursales", "¿Qué acción desea realizar?",
                "Crear nueva Sucursal", "Modificar Sucursal",
                "Eliminar Sucursal");
    }

    @FXML
    void btnVentasClicked(ActionEvent event) {
        mostrarAlerta("Acciones de Ventas", "¿Qué acción desea realizar?",
                "Modificar Venta", "Eliminar Venta");
    }

    private void mostrarAlerta(String titulo, String mensaje, String... opciones) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.setContentText("Seleccione una acción:");

        ButtonType[] botones = new ButtonType[opciones.length + 1];
        for (int i = 0; i < opciones.length; i++) {
            botones[i] = new ButtonType(opciones[i]);
        }
        botones[opciones.length] = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(botones);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            String accionSeleccionada = result.get().getText();
            if (!accionSeleccionada.equals("Cancelar")) {
                // Aquí puedes realizar acciones basadas en la acción seleccionada
                switch (accionSeleccionada) {
                    case "Realizar adelanto":
                        // Lógica para realizar un adelanto
                        break;
                    case "Cargar Nuevo Empleado":
                        // Lógica para cargar un nuevo empleado
                        break;
                    case "Modificar Empleado":
                        // Lógica para modificar un empleado
                        break;
                    case "Borrar Empleado":
                        // Lógica para borrar un empleado
                        break;
                    case "Cambiar Sueldo de Empleado":
                        // Lógica para cambiar el sueldo de un empleado
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
