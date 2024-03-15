package com.verona.controller.common;

import com.verona.controller.SceneController;
import com.verona.model.User;
import com.verona.model.Venta;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class VerVentasDeSucursalController {

    @FXML
    private Button btnCargarFiltradas;

    @FXML
    private Button btnVolver;

    @FXML
    private DatePicker dateFromPicker;

    @FXML
    private DatePicker dateToPicker;

    @FXML
    private TableView<Venta> tableVentas;

    User user = User.getCurrentUser();

    @SuppressWarnings("unchecked")
    @FXML
    void initialize() {
        // Inicializar las columnas
        TableColumn<Venta, Integer> ventasIDColumn = new TableColumn<>("ID Venta");
        ventasIDColumn.setCellValueFactory(new PropertyValueFactory<>("ventasID"));

        TableColumn<Venta, String> nombreClienteColumn = new TableColumn<>("Nombre Cliente");
        nombreClienteColumn.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));

        TableColumn<Venta, String> descripcionColumn = new TableColumn<>("Descripción");
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<Venta, String> materialColumn = new TableColumn<>("Material");
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));

        TableColumn<Venta, String> colorColumn = new TableColumn<>("Color");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn<Venta, String> fechaEstimadaTerminacionColumn = new TableColumn<>("Fecha Estimada de Terminación");
        fechaEstimadaTerminacionColumn.setCellValueFactory(new PropertyValueFactory<>("fechaEstimadaTerminacion"));

        TableColumn<Venta, String> telefono1Column = new TableColumn<>("Teléfono 1");
        telefono1Column.setCellValueFactory(new PropertyValueFactory<>("telefono1"));

        TableColumn<Venta, String> telefono2Column = new TableColumn<>("Teléfono 2");
        telefono2Column.setCellValueFactory(new PropertyValueFactory<>("telefono2"));

        TableColumn<Venta, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Agregar las columnas a la tabla
        tableVentas.getColumns().addAll(
                ventasIDColumn,
                nombreClienteColumn,
                descripcionColumn,
                materialColumn,
                colorColumn,
                fechaEstimadaTerminacionColumn,
                telefono1Column,
                telefono2Column,
                emailColumn);

        // Llamar a la función para obtener las ventas y rellenar la tabla
        try {
            cargarVentas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarVentas() throws SQLException {
        int sucursalID = user.getSucursalID();

        Venta venta = new Venta(0, null, null, null, null, null, 0, 0, 0, null,
                null, 0, null, null, null, 0, 0);
        List<Venta> ventas = venta.ventasPorSucursal(sucursalID); // Llamar al método en la instancia
        ObservableList<Venta> ventasObservable = FXCollections.observableArrayList(ventas);
        tableVentas.setItems(ventasObservable);
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
