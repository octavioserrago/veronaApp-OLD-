package com.verona.controller.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.verona.controller.SceneController;
import com.verona.model.Plano;
import com.verona.model.PlanosData;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class VerProduccionController {

    @FXML
    private Button btnBorrarPlano;

    @FXML
    private Button btnCargarPlanos;

    @FXML
    private Button btnCambiarEstadoPlano;

    @FXML
    private Button btnModificarPlano;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnAsignarBachas;

    @FXML
    private TableView<PlanosData> tablaPlanosProduccion;

    @FXML
    private TableColumn<PlanosData, String> colCodigoPlano;

    @FXML
    private TableColumn<PlanosData, String> colMaterial;

    @FXML
    private TableColumn<PlanosData, String> colColor;

    @FXML
    private TableColumn<PlanosData, String> colEstado;

    @FXML
    private TableColumn<PlanosData, Button> colImagen;

    @FXML
    private TableColumn<PlanosData, String> colVentasID;

    @FXML
    private TableColumn<PlanosData, String> colFechaTerminacion;

    Plano plano = new Plano(null, "", "", null, 0, null, "");

    @FXML
    public void initialize() {
        cargarLosPlanosTabla();
    }

    @FXML
    void btnBorrarPlanoClicked(ActionEvent event) {

    }

    @FXML
    void btnCargarPlanosClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToCargarPlano();
    }

    @FXML
    void btnAsignarBachasClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnAsignarBachas.getScene().getWindow());
        sceneController.switchToAsignarBachasPlano();
    }

    @FXML
    void btnCambiarEstadoPlanoClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToCambiarEstadoPlano();
    }

    @FXML
    void btnModificarPlanoClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnModificarPlano.getScene().getWindow());
        sceneController.switchToModificarPlano();
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToManagerDashboard();
    }

    private void cargarLosPlanosTabla() {
        List<Plano> listaPlanos = new ArrayList<>();

        try {
            listaPlanos = plano.obtenerPlanosEnProduccion();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<PlanosData> data = FXCollections.observableArrayList();
        for (Plano p : listaPlanos) {
            String material = p.getMaterial();
            String color = p.getColor();
            String estado = p.getEstado();
            byte[] imgBytes = p.getImgBlueprint();

            PlanosData planosData = new PlanosData(p.getCodigoPlano(), material, color, estado, imgBytes);
            data.add(planosData);

            planosData.setVentasID(String.valueOf(p.getVentasID()));
            planosData.setFechaTerminacion(p.getFechaTermiancion());
        }

        tablaPlanosProduccion.setItems(data);
        colCodigoPlano.setCellValueFactory(cellData -> cellData.getValue().codigoPlanoProperty());
        colMaterial.setCellValueFactory(cellData -> cellData.getValue().materialProperty());
        colColor.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        colEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        colImagen.setCellValueFactory(cellData -> cellData.getValue().verImagenButtonProperty());

        colVentasID.setCellValueFactory(cellData -> {
            PlanosData planosData = cellData.getValue();
            String ventasID = planosData.getVentasID();
            return new SimpleStringProperty(ventasID);
        });

        colFechaTerminacion.setCellValueFactory(cellData -> {
            PlanosData planosData = cellData.getValue();
            String fechaTerminacion = planosData.getFechaTerminacion();
            return new SimpleStringProperty(fechaTerminacion);
        });
    }

}