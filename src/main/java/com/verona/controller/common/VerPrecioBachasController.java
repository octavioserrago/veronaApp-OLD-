package com.verona.controller.common;

import java.sql.SQLException;
import java.util.List;

import com.verona.controller.SceneController;
import com.verona.model.Bacha;
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

public class VerPrecioBachasController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Bacha> preciosBachasTable;

    @SuppressWarnings("unchecked")
    @FXML
    public void initialize() {
        TableColumn<Bacha, String> tipoBachaColumn = new TableColumn<>("Tipo de Bacha");
        tipoBachaColumn.setCellValueFactory(new PropertyValueFactory<>("tipoBacha"));

        TableColumn<Bacha, String> nombreModeloColumn = new TableColumn<>("Nombre del Modelo");
        nombreModeloColumn.setCellValueFactory(new PropertyValueFactory<>("nombreModelo"));

        TableColumn<Bacha, String> medidasColumn = new TableColumn<>("Medidas");
        medidasColumn.setCellValueFactory(new PropertyValueFactory<>("medidas"));

        TableColumn<Bacha, Integer> cantidadColumn = new TableColumn<>("Cantidad");
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<Bacha, Double> precioColumn = new TableColumn<>("Precio");
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

        preciosBachasTable.getColumns().addAll(tipoBachaColumn, nombreModeloColumn, medidasColumn, cantidadColumn,
                precioColumn);

        llenarPreciosBachasTable();
    }

    private void llenarPreciosBachasTable() {
        try {
            Bacha bacha = new Bacha(0, null, null, 0);
            List<Bacha> bachas = bacha.obtenerModelosConPrecio();
            ObservableList<Bacha> bachasObservable = FXCollections.observableArrayList(bachas);
            preciosBachasTable.setItems(bachasObservable);
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
