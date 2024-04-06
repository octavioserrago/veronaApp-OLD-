package com.verona.controller.common;

import java.util.List;

import com.verona.controller.SceneController;
import com.verona.model.Colores;
import com.verona.model.User;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VerPrecioMaterialesController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Colores> materialesTable;

    @SuppressWarnings("unchecked")
    @FXML
    public void initialize() {

        TableColumn<Colores, String> materialColumn = new TableColumn<>("Material");
        materialColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoMaterial()));

        TableColumn<Colores, String> colorColumn = new TableColumn<>("Color");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn<Colores, String> monedaColumn = new TableColumn<>("Moneda");
        monedaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMonedaSimbolo()));

        TableColumn<Colores, String> m2PrecioColumn = new TableColumn<>("M2 Precio");
        m2PrecioColumn.setCellValueFactory(new PropertyValueFactory<>("m2Precio"));

        materialesTable.getColumns().addAll(materialColumn, colorColumn, monedaColumn, m2PrecioColumn);

        llenarMaterialesTable();
    }

    private void llenarMaterialesTable() {
        List<Colores> materiales = Colores.obtenerListaMaterialColorM2Precio();
        ObservableList<Colores> coloresObservable = FXCollections.observableArrayList(materiales);
        materialesTable.setItems(coloresObservable);
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
