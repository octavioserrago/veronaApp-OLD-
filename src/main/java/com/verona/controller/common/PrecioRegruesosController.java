package com.verona.controller.common;

import java.sql.SQLException;
import java.util.List;

import com.verona.controller.SceneController;
import com.verona.model.Regruesos;
import com.verona.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PrecioRegruesosController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Regruesos> tablePrecioRegruesos;

    @SuppressWarnings("unchecked")
    @FXML
    public void initialize() {

        TableColumn<Regruesos, String> detalleColumn = new TableColumn<>("Detalle");
        detalleColumn.setCellValueFactory(new PropertyValueFactory<>("detalle"));

        TableColumn<Regruesos, String> mlColumn = new TableColumn<>("Precio ML");
        mlColumn.setCellValueFactory(new PropertyValueFactory<>("precioML"));

        tablePrecioRegruesos.getColumns().addAll(detalleColumn, mlColumn);

        llenarPreciosRegruesos();
    }

    private void llenarPreciosRegruesos() {
        try {
            List<Regruesos> listaRegruesos = Regruesos.obtenerListaRegruesos();
            ObservableList<Regruesos> RegruesosObservableList = FXCollections.observableArrayList(listaRegruesos);
            tablePrecioRegruesos.setItems(RegruesosObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
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
