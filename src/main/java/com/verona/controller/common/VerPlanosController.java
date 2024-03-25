package com.verona.controller.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.verona.controller.SceneController;

import com.verona.model.Plano;
import com.verona.model.PlanosData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class VerPlanosController {

    @FXML
    private Button btnVolver;

    @FXML
    private Label label;

    @FXML
    private TableView<PlanosData> tablaDatos;

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

    Plano plano = new Plano(null, "", "", null, getVentaActual(), null, "");

    private int ventaActual;

    public int getVentaActual() {
        return ventaActual;
    }

    public void setVentaActual(int ventaActual) {
        this.ventaActual = ventaActual;
    }

    @FXML
    public void initialize() {
        cargarLosPlanosTabla();
    }

    @FXML
    void btnVolverClicked() {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToBuscarVenta();
    }

    private void cargarLosPlanosTabla() {
        int ventasID = Plano.getCurrentVentasID();
        List<Plano> listaPlanos = new ArrayList<>();

        try {
            listaPlanos = plano.obtenerPlanosParaVenta(ventasID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<PlanosData> data = FXCollections.observableArrayList();
        for (Plano p : listaPlanos) {
            String material = p.getMaterial();
            String color = p.getColor();
            String estado = p.getEstado();
            byte[] imgBytes = p.getImgBlueprint();

            data.add(new PlanosData(p.getCodigoPlano(), material, color, estado, imgBytes));
        }

        tablaDatos.setItems(data);
        colCodigoPlano.setCellValueFactory(cellData -> cellData.getValue().codigoPlanoProperty());
        colMaterial.setCellValueFactory(cellData -> cellData.getValue().materialProperty());
        colColor.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        colEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        colImagen.setCellValueFactory(cellData -> cellData.getValue().verImagenButtonProperty());
    }

}