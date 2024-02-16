package com.verona.controller.common;

import java.sql.SQLException;
import java.util.List;

import com.verona.controller.SceneController;
import com.verona.model.CajaSeñas;
import com.verona.model.MovimientosCajaSeñas;
import com.verona.model.User;
import com.verona.model.VerificadorIngresosCredito;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VerificadorIngresosTarjetaCreditoController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<VerificadorIngresosCredito> tableVerificadorIngresos;

    private VerificadorIngresosCredito verificadorIngresosCredito;

    private CajaSeñas cajaSeñas = new CajaSeñas(0, 0);
    private MovimientosCajaSeñas movimientoBanco = new MovimientosCajaSeñas(0, 0);
    private User user = User.getCurrentUser();

    @FXML
    void initialize() {
        User user = User.getCurrentUser();
        if (user != null) {
            verificadorIngresosCredito = new VerificadorIngresosCredito(0.0, user.getSucursalID());
            createColumns();
            cargarTabla();
        }
    }

    @SuppressWarnings("unchecked")
    private void createColumns() {
        TableColumn<VerificadorIngresosCredito, Integer> idColumn = new TableColumn<>("ID de Verificador");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("verificadorIngresosCreditoID"));

        TableColumn<VerificadorIngresosCredito, String> fechaCobroColumn = new TableColumn<>("Fecha de Cobro");
        fechaCobroColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<VerificadorIngresosCredito, Double> importeColumn = new TableColumn<>("Importe");
        importeColumn.setCellValueFactory(new PropertyValueFactory<>("importe"));

        TableColumn<VerificadorIngresosCredito, Void> buttonColumn = new TableColumn<>("Acción");
        buttonColumn.setCellFactory(column -> {
            return new TableCell<VerificadorIngresosCredito, Void>() {
                private final Button button = new Button("Confirmar Registro en Banco");

                {
                    button.setOnAction(event -> {
                        confirmarIngreso(event);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            };
        });

        tableVerificadorIngresos.getColumns().addAll(idColumn, fechaCobroColumn, importeColumn, buttonColumn);
    }

    private void cargarTabla() {
        List<VerificadorIngresosCredito> ingresos = verificadorIngresosCredito.obtenerTodosLosRegistros();
        tableVerificadorIngresos.getItems().addAll(ingresos);
    }

    @FXML
    void confirmarIngreso(ActionEvent event) {
        VerificadorIngresosCredito ingresoSeleccionado = tableVerificadorIngresos.getSelectionModel().getSelectedItem();
        if (ingresoSeleccionado != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar ingreso");
            alert.setHeaderText("¿Está seguro/a de que este importe ya está vigente en la caja del banco?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        double importe = ingresoSeleccionado.getImporte();
                        int verificadorID = ingresoSeleccionado.getVerificadorIngresosCreditoID();
                        if (verificadorIngresosCredito.eliminarVerificadorPorID(verificadorID)) {
                            tableVerificadorIngresos.getItems().remove(ingresoSeleccionado);

                            cajaSeñas.insertarCajaSeñaBanco(importe, user.getSucursalID());
                            movimientoBanco.cargarMovimientoCajaSeñasBanco(importe, user.getSucursalID());

                            mostrarMensaje("Ingreso confirmado y eliminado correctamente", "green");
                        } else {
                            mostrarMensaje("Error al confirmar el ingreso", "red");
                        }
                    } catch (SQLException e) {
                        mostrarMensaje("Error al confirmar el ingreso: " + e.getMessage(), "red");
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ningún ingreso");
            alert.setContentText("Por favor, seleccione un ingreso para confirmar");
            alert.showAndWait();
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

    private void mostrarMensaje(String mensaje, String color) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
