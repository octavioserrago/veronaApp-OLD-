package com.verona.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneController {
    private final Stage stage;

    public SceneController(Stage stage) {
        this.stage = stage;
    }

    public void switchToLogin() {
        System.out.println("Switching to Login");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/verona/view/loginScreen.fxml"));
            Parent root = loader.load();

            loader.setControllerFactory(c -> new LoginController());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio de Sesión");
            stage.show();
            System.out.println("Switched to Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToCargarPlano() {
        switchScene("/com/verona/view/cargarPlano.fxml", "Cargar Plano");
    }

    public void switchToVerProduccion() {
        switchScene("/com/verona/view/verProduccion.fxml", "Produccion");
    }

    public void switchToVerPlanos() {
        switchScene("/com/verona/view/verPlanos.fxml", "Ver detalle de los Planos");
    }

    public void switchToDashboardSeller() {
        switchScene("/com/verona/view/dashboardSeller.fxml", "Dashboard - Vendedor");
    }

    public void switchToManagerDashboard() {
        switchScene("/com/verona/view/managerDashboard.fxml", "Dashboard - Gerente");
    }

    public void switchToCotizaciones() {
        switchScene("/com/verona/view/cotizaciones.fxml", "Cotizaciones");
    }

    public void switchToBachas() {
        switchScene("/com/verona/view/bachas.fxml", "Bachas");
    }

    public void switchToRegistrarIngreso() {
        switchScene("/com/verona/view/registrarIngreso.fxml", "Registrar Ingreso");
    }

    public void switchToNuevaCotizacionBlue() {
        switchScene("/com/verona/view/nuevaCotizacionBlue.fxml", "Nueva Cotización Blue");
    }

    public void switchToBuscarVenta() {
        switchScene("/com/verona/view/buscarVentas.fxml", "Buscador de Ventas");
    }

    public void switchToIngresarBachas() {
        switchScene("/com/verona/view/ingresarBachas.fxml", "Ingresar Bachas a Stock");
    }

    public void switchToModificarEstadoVenta() {
        switchScene("/com/verona/view/modificarEstadoVenta.fxml", "Modificar Estado de Venta");
    }

    public void switchToAsignarBachasPlano() {
        switchScene("/com/verona/view/asignarBachasPlano.fxml", "Asignar Bacha a Plano");
    }

    public void switchToVerBalances() {
        switchScene("/com/verona/view/verBalances.fxml", "Balance - Gerente");
    }

    public void switchToNegocio() {
        switchScene("/com/verona/view/negocio.fxml", "Negocio | Estadisitcas - Gerente");
    }

    public void switchToVerVentasDeSucursal() {
        switchScene("/com/verona/view/verVentasDeSucursal.fxml", "Ventas de nuestra Sucursal");
    }

    public void switchToRegistrarPago() {
        switchScene("/com/verona/view/registrarPago.fxml", "Registrar un pago (Salida)");
    }

    public void switchToPresupuesto() {
        switchScene("/com/verona/view/realizarPresupuesto.fxml", "Presupuesto");
    }

    public void switchToModificarPlano() {
        switchScene("/com/verona/view/modificarPlano.fxml", "Modificar Plano");
    }

    public void switchToCambiarEstadoPlano() {
        switchScene("/com/verona/view/modificarEstadoDePlano.fxml", "Modificar Estado de Plano");
    }

    public void switchToVerDetalleCaja() {
        switchScene("/com/verona/view/verDetalleCajas.fxml", "Detalle de Caja)");
    }

    public void switchToVerSenias() {
        switchScene("/com/verona/view/verSenias.fxml",
                "Ver Señas");
    }

    public void switchToVerificarTarjetaCredito() {
        switchScene("/com/verona/view/verificarIngresosTarjetaCredito.fxml",
                "Verificar Los ingresos con tarjeta de credito");
    }

    public void switchToVentas(AnchorPane rootPane) {
        try {
            Scene scene = new Scene(rootPane);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchScene(String fxmlPath, String windowTitle) {
        System.out.println("Switching Scene: " + fxmlPath);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(windowTitle);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Switched Scene: " + fxmlPath);
    }

}