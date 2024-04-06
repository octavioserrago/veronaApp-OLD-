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

public class BachasController {

    @FXML
    private TableView<Bacha> bachasSinStockTable;

    @FXML
    private Button btnIngresarBachas;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Bacha> tablaBachasStock;

    Bacha bacha = new Bacha(0, null, null, 0);

    @SuppressWarnings("unchecked")
    @FXML
    void initialize() {
        TableColumn<Bacha, String> tipoBachaColumn1 = new TableColumn<>("Tipo");
        tipoBachaColumn1.setCellValueFactory(new PropertyValueFactory<>("tipoBacha"));

        TableColumn<Bacha, String> nombreModeloColumn1 = new TableColumn<>("Modelo");
        nombreModeloColumn1.setCellValueFactory(new PropertyValueFactory<>("nombreModelo"));

        TableColumn<Bacha, String> medidasColumn1 = new TableColumn<>("Medidas");
        medidasColumn1.setCellValueFactory(new PropertyValueFactory<>("medidas"));

        TableColumn<Bacha, Integer> cantidadColumn1 = new TableColumn<>("Cantidad");
        cantidadColumn1.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        tablaBachasStock.getColumns().addAll(tipoBachaColumn1, nombreModeloColumn1, medidasColumn1, cantidadColumn1);

        TableColumn<Bacha, String> tipoBachaColumn2 = new TableColumn<>("Tipo");
        tipoBachaColumn2.setCellValueFactory(new PropertyValueFactory<>("tipoBacha"));

        TableColumn<Bacha, String> nombreModeloColumn2 = new TableColumn<>("Modelo");
        nombreModeloColumn2.setCellValueFactory(new PropertyValueFactory<>("nombreModelo"));

        TableColumn<Bacha, String> medidasColumn2 = new TableColumn<>("Medidas");
        medidasColumn2.setCellValueFactory(new PropertyValueFactory<>("medidas"));

        TableColumn<Bacha, Integer> cantidadColumn2 = new TableColumn<>("Cantidad");
        cantidadColumn2.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        bachasSinStockTable.getColumns().addAll(tipoBachaColumn2, nombreModeloColumn2, medidasColumn2, cantidadColumn2);

        llenarComoboboxBachas();
        llenarComboboxBachasSinStock();
    }

    private void llenarComoboboxBachas() {
        try {
            List<Bacha> bachasConStock = bacha.obtenerBachasStock();
            ObservableList<Bacha> bachasObservable = FXCollections.observableArrayList(bachasConStock);
            tablaBachasStock.setItems(bachasObservable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void llenarComboboxBachasSinStock() {
        try {
            List<Bacha> bachasSinStock = bacha.obtenerBachasSinStock();
            ObservableList<Bacha> bachasObservable = FXCollections.observableArrayList(bachasSinStock);
            bachasSinStockTable.setItems(bachasObservable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnIngresarBachasClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnIngresarBachas.getScene().getWindow());
        sceneController.switchToIngresarBachas();
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