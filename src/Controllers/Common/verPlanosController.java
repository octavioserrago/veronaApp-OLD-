package Controllers.Common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Controllers.SceneController;
import Data.Plano;
import Data.PlanosData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class verPlanosController {

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

    Plano plano = new Plano(null, getVentaActual(), getVentaActual(), null, getVentaActual(), null);

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
        System.out.println(ventasID);
        List<Plano> listaPlanos = new ArrayList<>();

        try {
            listaPlanos = plano.obtenerPlanosParaVenta(ventasID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<PlanosData> data = FXCollections.observableArrayList();
        for (Plano p : listaPlanos) {
            // Debes implementar métodos para obtener material y color según sus IDs
            String material = obtenerMaterialSegunID(p.getMaterialID());
            String color = obtenerColorSegunID(p.getColorID());

            data.add(new PlanosData(p.getCodigoPlano(), material, color));
        }

        // Asignar datos a la tabla y a las columnas
        tablaDatos.setItems(data);
        colCodigoPlano.setCellValueFactory(cellData -> cellData.getValue().codigoPlanoProperty());
        colMaterial.setCellValueFactory(cellData -> cellData.getValue().materialProperty());
        colColor.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
    }

    private String obtenerMaterialSegunID(int materialID) {
        // Implementa la lógica para obtener el nombre del material según el ID
        return "";
    }

    private String obtenerColorSegunID(int colorID) {
        // Implementa la lógica para obtener el nombre del color según el ID
        return "";
    }
}
