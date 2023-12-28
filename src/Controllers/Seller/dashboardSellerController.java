package Controllers.Seller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import Controllers.SceneController;
import Controllers.Common.cotizacionesController;
import Data.Bachas;
import Data.Cotizacion;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class dashboardSellerController {

    @FXML
    private Button BtnCotizaciones;

    @FXML
    private Button btnBachas;

    @FXML
    private Button btnCaja;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnPedidos;

    @FXML
    private Button btnVentas;

    @FXML
    private Label cotizacionDolarBlue;

    @FXML
    private Label fechaLabel;

    @FXML
    private Label nombreEmpleado;

    @FXML
    private Label fechaUltimaBlue;

    @FXML
    private TableView<Bachas> bachasTablaPreview; // Cambiado el tipo de TableView

    @FXML
    void BtnCotizacionesClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) BtnCotizaciones.getScene().getWindow());
        sceneController.switchToCotizaciones();
    }

    @FXML
    public void initialize() {
        mostrarFechaActual();
        cargarUltimasCotizaciones();
        try {
            cargarBachas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnBachasClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnBachas.getScene().getWindow());
        sceneController.switchToBachas();
    }

    @FXML
    void btnCajaClicked(ActionEvent event) {

    }

    @FXML
    void btnCerrarSesionClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnCerrarSesion.getScene().getWindow());
        sceneController.switchToLogin();
    }

    @FXML
    void btnPedidosClicked(ActionEvent event) {

    }

    @FXML
    void btnVentasClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVentas.getScene().getWindow());
        sceneController.switchToVentas();
    }

    private void mostrarFechaActual() {

        LocalDate fechaActual = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        fechaLabel.setText(fechaFormateada);
    }

    cotizacionesController cotizacionesController = new cotizacionesController();

    private void cargarUltimasCotizaciones() {
        Cotizacion ultimaCotizacionBlue = cotizacionesController.obtenerUltimaCotizacionBlue();

        if (ultimaCotizacionBlue != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaUltimaFormateada = ultimaCotizacionBlue.getFecha().formatted(formatter);

            fechaUltimaBlue.setText(fechaUltimaFormateada);
            cotizacionDolarBlue.setText(String.valueOf(ultimaCotizacionBlue.getTasaCambio()));
        }
    }

    private List<Bachas> obtenerBachasStock() throws SQLException {
        Bachas bachas = new Bachas(0, null, null, 0);
        bachas.setMarcasBachasID(2); // Establece el valor de marcasBachasID según tus necesidades
        return bachas.obtenerBachasStock();
    }

    private void cargarBachas() throws SQLException {
        List<Bachas> listaBachas = obtenerBachasStock();
    
        bachasTablaPreview.getItems().clear();
        bachasTablaPreview.getColumns().clear();
    
        if (listaBachas != null && !listaBachas.isEmpty()) {
    
            TableColumn<Bachas, String> marcasBachasColumn = new TableColumn<>("Marca Bachas");
            TableColumn<Bachas, String> nombreModeloColumn = new TableColumn<>("Nombre Modelo");
            TableColumn<Bachas, String> medidasColumn = new TableColumn<>("Medidas");
            TableColumn<Bachas, Integer> cantidadColumn = new TableColumn<>("Cantidad");
    
            marcasBachasColumn.setCellValueFactory(cellData -> new SimpleStringProperty(obtenerNombreMarcaBachas(cellData.getValue().getMarcasBachasID())));
            nombreModeloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreModelo()));
            medidasColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedidas()));
            cantidadColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCantidad()).asObject());
    
            marcasBachasColumn.setMaxWidth(1f * Integer.MAX_VALUE * 25);
            nombreModeloColumn.setMaxWidth(1f * Integer.MAX_VALUE * 25);
            medidasColumn.setMaxWidth(1f * Integer.MAX_VALUE * 25);
            cantidadColumn.setMaxWidth(1f * Integer.MAX_VALUE * 25);
    
            bachasTablaPreview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
            List<TableColumn<Bachas, ?>> columnList = Arrays.asList(marcasBachasColumn, nombreModeloColumn, medidasColumn, cantidadColumn);
    
            bachasTablaPreview.getColumns().addAll(columnList);
            bachasTablaPreview.getItems().addAll(listaBachas);
            bachasTablaPreview.setItems(FXCollections.observableList(listaBachas));
        }
    }
    
    private String obtenerNombreMarcaBachas(int marcasBachasID) {
        
        if (marcasBachasID == 2) {
            return "Mi Pileta";
        }
        return "Desconocido";
    }
    
}
