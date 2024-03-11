package com.verona.controller.manager;

import java.sql.SQLException;

import com.verona.controller.SceneController;
import com.verona.model.Senias;
import com.verona.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VerSeniasController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Senias> tableSenias;

    private final ObservableList<Senias> seniasData = FXCollections.observableArrayList();
    User user = User.getCurrentUser();

    @SuppressWarnings("unchecked")
    @FXML
    void initialize() {

        TableColumn<Senias, Integer> ventasIDColumn = new TableColumn<>("Ventas ID");
        ventasIDColumn.setCellValueFactory(new PropertyValueFactory<>("ventasID"));

        TableColumn<Senias, Double> importeEfectivoColumn = new TableColumn<>("Importe Efectivo");
        importeEfectivoColumn.setCellValueFactory(new PropertyValueFactory<>("importeEfectivo"));

        TableColumn<Senias, Double> importeBancoColumn = new TableColumn<>("Importe Banco");
        importeBancoColumn.setCellValueFactory(new PropertyValueFactory<>("importeBanco"));

        TableColumn<Senias, Double> saldoColumn = new TableColumn<>("Saldo");
        saldoColumn.setCellValueFactory(new PropertyValueFactory<>("saldo"));

        TableColumn<Senias, Button> colBtn = new TableColumn<>("Acción");
        colBtn.setCellValueFactory(new PropertyValueFactory<>("accionButton"));
        colBtn.setCellFactory(tc -> new TableCell<Senias, Button>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Senias senias = getTableView().getItems().get(getIndex());
                    try {
                        if (senias.isAccionDisponible(senias.getVentasID(), senias.getImporteEfectivo(),
                                senias.getImporteBanco())) {
                            setGraphic(item);
                            setDisable(false);
                            item.setOnAction(event -> {
                                System.out.println("Botón presionado para la Seña ID: " + senias.getVentasID());
                            });
                        } else {
                            setGraphic(item);
                            setDisable(true);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        tableSenias.getColumns().addAll(ventasIDColumn, importeEfectivoColumn, importeBancoColumn, saldoColumn, colBtn);

        try {
            Senias senias = new Senias(0, 0, 0, 0, 0, 0);
            seniasData.addAll(senias.obtenerSeniasPorSucursal(user.getSucursalID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableSenias.setItems(seniasData);
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }
}
