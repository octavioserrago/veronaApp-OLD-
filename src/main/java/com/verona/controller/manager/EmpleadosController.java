package com.verona.controller.manager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

import com.verona.controller.SceneController;
import com.verona.model.Empleado;
import com.verona.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EmpleadosController {

    @FXML
    private Button btnAumentarSueldo;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Empleado> tablaEmpleados;

    private User user = User.getCurrentUser();
    int sucursalID = user.getSucursalID();

    @SuppressWarnings("unchecked")
    @FXML
    public void initialize() {
        TableColumn<Empleado, Integer> idColumna = new TableColumn<>("ID");
        idColumna.setCellValueFactory(new PropertyValueFactory<>("empleadosID"));

        TableColumn<Empleado, String> nombreColumna = new TableColumn<>("Nombre y Apellido");
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombreApellido"));

        TableColumn<Empleado, String> puestoColumna = new TableColumn<>("Puesto");
        puestoColumna.setCellValueFactory(new PropertyValueFactory<>("puesto"));

        TableColumn<Empleado, String> telefonoColumna = new TableColumn<>("Telefono");
        telefonoColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Empleado, String> emailColumna = new TableColumn<>("Email");
        emailColumna.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Empleado, String> cbuAliasColumna = new TableColumn<>("CBU | ALIAS");
        cbuAliasColumna.setCellValueFactory(new PropertyValueFactory<>("cbuAlias"));

        TableColumn<Empleado, Date> fechaNacimientoColumna = new TableColumn<>("Fecha Nacimiento");
        fechaNacimientoColumna.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        TableColumn<Empleado, Integer> monedasIDColumna = new TableColumn<>("Moneda");
        monedasIDColumna.setCellValueFactory(new PropertyValueFactory<>("monedasID"));

        TableColumn<Empleado, Double> sueldoSemanalColumna = new TableColumn<>("Sueldo Semanal");
        sueldoSemanalColumna.setCellValueFactory(new PropertyValueFactory<>("sueldoSemanal"));

        tablaEmpleados.getColumns().addAll(idColumna, nombreColumna, puestoColumna, telefonoColumna, emailColumna,
                cbuAliasColumna, fechaNacimientoColumna, monedasIDColumna, sueldoSemanalColumna);

        ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();
        listaEmpleados.addAll(Empleado.obtenerListaEmpleados(sucursalID));
        tablaEmpleados.setItems(listaEmpleados);
    }

    @FXML
    void btnAumentarSueldoClicked(ActionEvent event) {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Aumentar Sueldo");
        idDialog.setHeaderText("Ingrese el ID del empleado:");
        idDialog.setContentText("ID del empleado:");

        Optional<String> idResult = idDialog.showAndWait();
        idResult.ifPresent(idEmpleado -> {
            TextInputDialog sueldoDialog = new TextInputDialog();
            sueldoDialog.setTitle("Aumentar Sueldo");
            sueldoDialog.setHeaderText("Ingrese el nuevo sueldo:");
            sueldoDialog.setContentText("Nuevo sueldo:");

            Optional<String> sueldoResult = sueldoDialog.showAndWait();
            sueldoResult.ifPresent(nuevoSueldo -> {
                try {
                    double nuevoSueldoDouble = Double.parseDouble(nuevoSueldo);
                    int empleadoID = Integer.parseInt(idEmpleado);

                    Empleado empleado = new Empleado(idEmpleado, idEmpleado, idEmpleado, idEmpleado, idEmpleado, null,
                            empleadoID, nuevoSueldoDouble, empleadoID);
                    boolean aumentoExitoso = empleado.aumentarSueldo(nuevoSueldoDouble, empleadoID);

                    if (aumentoExitoso) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Aumento de Sueldo");
                        alert.setHeaderText(null);
                        alert.setContentText(
                                "Se aumentó el sueldo del empleado con ID " + empleadoID + " a " + nuevoSueldo);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Hubo un error al aumentar el sueldo del empleado.");
                        alert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor, ingrese un número válido para el sueldo.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Hubo un error al acceder a la base de datos.");
                    alert.showAndWait();
                    e.printStackTrace();
                }
            });
        });
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }

}
