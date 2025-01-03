package com.verona.controller.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.verona.controller.SceneController;
import com.verona.model.Colocador;
import com.verona.model.DatabaseConnection;
import com.verona.model.User;
import com.verona.model.Validador;
import com.verona.model.Venta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CargarVentasController {

    @FXML
    private Button btnCargarVenta;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox colocadorASK;

    @FXML
    private ComboBox<String> colocadorOptions;

    @FXML
    private TextField colorTextField;

    @FXML
    private Label cantidadBachasLabel;

    @FXML
    private TextArea descripcionTextFIeld;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label labelAclaracion;

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

    private int colocadoresID;

    User user = User.getCurrentUser();
    private boolean ventaCargada = false;

    @FXML
    void initialize() {
        noVisibles();

        colocadorASK.setOnAction(event -> {
            colocadorOptions.setVisible(colocadorASK.isSelected());
            precioColocacionLabel.setVisible(colocadorASK.isSelected());
            precioColocacionTextField.setVisible(colocadorASK.isSelected());
        });

        llenarComboBoxColocadores();

    }

    @FXML
    void btnCargarVentaClicked(ActionEvent event) throws SQLException {
        if (ventaCargada) {
            return;
        }

        String estado = "En Produccion";
        String nombreCliente = nombreClienteTextField.getText();
        String descripcion = descripcionTextFIeld.getText();
        String material = materialTextField.getText();
        String color = colorTextField.getText();

        String fechaEstimadaTerminacion = (fechaTerminacionSelect.getValue() != null)
                ? fechaTerminacionSelect.getValue().toString()
                : null;

        String colocadorSelected = colocadorOptions.getSelectionModel().getSelectedItem();

        colocadoresID = obtenerIDPorNombre(colocadorSelected);

        String telefono1 = telefonoTextField.getText();
        String telefono2 = telefonoSecundarioTextField.getText();
        String email = emailTextField.getText();

        Double precioColocacion = colocadorASK.isSelected() ? 0.0 : 0.0;

        if (colocadorASK.isSelected()) {
            String precioColocacionText = precioColocacionTextField.getText();

            if (!precioColocacionText.isEmpty() && precioColocacionText.matches("\\d+(\\.\\d+)?")) {
                precioColocacion = Double.parseDouble(precioColocacionText);
            } else {

                mostrarMensaje("Error: El precio de colocación no es un número válido", false);
                return;
            }
        }

        Venta venta = new Venta(0, nombreCliente, descripcion, material, color,
                fechaEstimadaTerminacion, colocadoresID, precioColocacion,
                1, Double.parseDouble(importeTextField.getText()), estado, 0,
                telefono1, telefono2, email, user.getSucursalID(), user.getUserID());

        Validador validador = new Validador(venta);
        String errores = validador.validarVenta();

        if (errores.isEmpty()) {

            String mensaje = "¿Está a punto de cargar una venta a nombre de: " + nombreCliente + " con un importe de: $"
                    + importeTextField.getText() + "?";

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, mensaje, ButtonType.YES, ButtonType.CANCEL);
            alert.setTitle("Confirmación de carga de venta");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {

                    try {
                        venta.insertarVenta();
                        mostrarMensaje("Venta cargada con éxito", true);
                        btnCargarVenta.setDisable(true);
                        ventaCargada = true;
                    } catch (SQLException e) {
                        mostrarMensaje("Error al cargar la venta", false);
                        e.printStackTrace();
                    }
                } else {

                    System.out.println("La carga de la venta fue cancelada por el usuario.");
                }
            });
        } else {
            mostrarMensaje("Errores en la validación: " + errores, false);
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

    public void noVisibles() {
        colocadorOptions.setVisible(false);
        precioColocacionLabel.setVisible(false);
        precioColocacionTextField.setVisible(false);

    }

    private void llenarComboBoxColocadores() {
        Colocador colocador = new Colocador(null, null, null, null);
        List<String> nombresColocadores = colocador.obtenerNombresColocadores();

        colocadorOptions.getItems().clear();
        colocadorOptions.getItems().addAll(nombresColocadores);

        if (!nombresColocadores.isEmpty()) {
            colocadorOptions.setValue(nombresColocadores.get(0));
        }
    }

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    private int obtenerIDPorNombre(String nombreColocador) throws SQLException {
        int colocadorID = -1;
        String sql = "SELECT colocadoresID FROM Colocadores WHERE nombreApellido = ?";

        try {
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nombreColocador);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                colocadorID = resultSet.getInt("colocadoresID");
            }
        } catch (Exception e) {
            throw new SQLException("Error al buscar colocadores: " + e.getMessage(), e);
        } finally {

            if (stmt != null) {
                stmt.close();
            }
        }

        return colocadorID;
    }

    private void mostrarMensaje(String mensaje, boolean exitoso) {
        Alert alert = new Alert(exitoso ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(exitoso ? "Éxito" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
