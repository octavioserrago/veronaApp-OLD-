package Controllers.Common;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import Controllers.SceneController;
import Data.Colocador;
import Data.Cotizacion;
import Data.Entrada;
import Data.Venta;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class buscarVentasController {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVolver;

    
    @FXML
    private Label colocadorLabel;

    @FXML
    private Label colocadorLabelToComplete;

    @FXML
    private Label colorLabel;

    @FXML
    private Label colorLabelToComplete;

    @FXML
    private Label descripcionLabel;

    @FXML
    private Label descripcionLabelToComplete;

    @FXML
    private Label emailLabel;

    @FXML
    private Label emailLabelToComplete;

    @FXML
    private Label estadoLabel;

    @FXML
    private Label estadoLabelToComplete;

    @FXML
    private Label fechaCreacionLabel;

    @FXML
    private Label fechaCreacionLabelToComplete;

    @FXML
    private Label fechaTerminacionLabel;

    @FXML
    private Label fechaTerminacionLabelToComplete;

    @FXML
    private TextField idClienteTextField;

    @FXML
    private Label idLabel;

    @FXML
    private Label idLabelToComplete;

    @FXML
    private Label importeTotalLabel;

    @FXML
    private Label importeTotalLabelToComplete;

    @FXML
    private Label materialLabel;

    @FXML
    private Label materialLabelToComplete;

    @FXML
    private TextField nombreClienteTextField;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label nombreLabelToComplete;

    @FXML
    private Label precioColocacionLabel;

    @FXML
    private Label precioColocacionLabelToComplete;

    
    @FXML
    private Label resultadoLabel;

    @FXML
    private Label saldoLabel;

    @FXML
    private Label saldoLabelToComplete;

   
    @FXML
    private TableView<Entrada> tablaCobros;

    @FXML
    private Label telefono2Label;

    @FXML
    private Label telefono2LabelToComplete;

    @FXML
    private Label telefonoLabel;

    @FXML
    private Label telefonoLabelToComplete;

    @FXML
    private Button btnGenerarPDF;

    @FXML
    private Separator linea1;

    @FXML
    private Separator linea2;

    @FXML
    private Separator linea3;

    @FXML
    private Separator linea4;

    @FXML
    private Separator linea5;

    @FXML
    private Separator linea6;

    @FXML
    private Separator linea7;

    @FXML
    private Separator linea8;

    @FXML
    private Separator linea9;

    @FXML
    private Separator linea10;



  
    private Entrada entradaModel;
    String idString;
    int id;
    String nombreCliente;


    private Venta ventaModel;

    
    @FXML
    void initialize() {
        noVisibles();
        idString = "";
        id = 0;
        nombreCliente = "";
        entradaModel = new Entrada("", "", 0, 0.0, 0, 0, 0);
        ventaModel = new Venta(id, nombreCliente, "", "", "", "", 0, 0, 0, null, "", "", 0, "", "", "");
    }

   
    @FXML
    void btnGenerarPDFClicked(ActionEvent event) {
        // Lógica para generar un PDF (por implementar)
    }

   
    @FXML
    void btnBuscarClicked(ActionEvent event) {
        idString = idClienteTextField.getText();
        nombreCliente = nombreClienteTextField.getText();

        if (!idString.isEmpty() || !nombreCliente.isEmpty()) {
            try {
                if (!idString.isEmpty()) {
                    id = Integer.parseInt(idString);
                   
                    Venta venta = ventaModel.findVentaById(id);

                    if (venta != null) {
                        
                        mostrarDetallesVenta(venta);
                        resultadoLabel.setText("Venta encontrada");
                    } else {
                        resultadoLabel.setText("No se encontró ninguna venta con el ID proporcionado");
                    }
                } else if (!nombreCliente.isEmpty()) {
              
                    Venta venta = ventaModel.findVentaByName(nombreCliente);

                    if (venta != null) {
                        
                        mostrarDetallesVenta(venta);
                        resultadoLabel.setText("Venta encontrada");
                    } else {
                        resultadoLabel.setText("No se encontró ninguna venta con el nombre proporcionado");
                    }
                }
            } finally {
               
            }
        } else {
            resultadoLabel.setText("Ingrese al menos un criterio de búsqueda (ID o Nombre Cliente)");
        }
    }

    public void noVisibles() {
        colocadorLabel.setVisible(false);
        colorLabel.setVisible(false);
        descripcionLabel.setVisible(false);
        emailLabel.setVisible(false);
        estadoLabel.setVisible(false);
        fechaCreacionLabel.setVisible(false);
        fechaTerminacionLabel.setVisible(false);
        idLabel.setVisible(false);
        importeTotalLabel.setVisible(false);
        materialLabel.setVisible(false);
        nombreLabel.setVisible(false);
        precioColocacionLabel.setVisible(false);
        resultadoLabel.setVisible(false);
        saldoLabel.setVisible(false);
        tablaCobros.setVisible(false);
        telefono2Label.setVisible(false);
        telefonoLabel.setVisible(false);
        descripcionLabelToComplete.setVisible(false);
        linea1.setVisible(false);
        linea2.setVisible(false);
        linea3.setVisible(false);
        linea4.setVisible(false);
        linea5.setVisible(false);
        linea6.setVisible(false);
        linea7.setVisible(false);
        linea8.setVisible(false);
        linea9.setVisible(false);
        linea10.setVisible(false);
        btnGenerarPDF.setVisible(false);
    }

    



    
    @FXML
    void btnVolverClicked(ActionEvent event) {
      
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardSeller();
    }

    
    public void mostrarDetallesVenta(Venta venta) {
        int colocadorid = venta.getColocadoresID();
        Colocador colocador = new Colocador("", "", "", "");
        String nombreApellido = "";

        try {
            nombreApellido = colocador.obtenerNombresPorID(colocadorid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        colocadorLabelToComplete.setText(nombreApellido);
        colorLabelToComplete.setText(venta.getColor());
        descripcionLabelToComplete.setText(venta.getDescripcion());
        emailLabelToComplete.setText(venta.getEmail());
        estadoLabelToComplete.setText(venta.getEstado());

        int ventasID = venta.getVentasID();
        String fecha = null;
        try {
            fecha = venta.obtenerFechaCreacion(ventasID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fechaCreacionLabelToComplete.setText(fecha);

        fechaTerminacionLabelToComplete.setText(venta.getFechaEstimadaTerminacion());
        idLabelToComplete.setText(String.valueOf(venta.getVentasID()));
        importeTotalLabelToComplete.setText(String.valueOf(venta.getImporte()));

        Double importeTotal = venta.getImporte();
        Double pagosTotales = 0.0;
        try {
            pagosTotales = entradaModel.calcularTotalEntradasEnPesos(venta.getVentasID());
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Double saldo = importeTotal - pagosTotales;
        saldoLabelToComplete.setText(String.valueOf(saldo));



        materialLabelToComplete.setText(venta.getMaterial());
        nombreLabelToComplete.setText(venta.getNombreCliente());
        precioColocacionLabelToComplete.setText(String.valueOf(venta.getPrecioColocacion()));

        if (venta.getPrecioColocacion() == 0) {
            precioColocacionLabel.setVisible(false);
            precioColocacionLabelToComplete.setVisible(false);

        } else {
            precioColocacionLabel.setVisible(true);
            precioColocacionLabelToComplete.setVisible(true);
            colocadorLabel.setVisible(true);
        }
        if (colocadorLabelToComplete.getText().equals("Ninguno")) {
            colocadorLabel.setVisible(false);
            colocadorLabelToComplete.setVisible(false);
        } else {
            colocadorLabel.setVisible(true);
            colocadorLabelToComplete.setVisible(true);
        }
        colorLabel.setVisible(true);
        descripcionLabel.setVisible(true);
        emailLabel.setVisible(true);
        estadoLabel.setVisible(true);
        fechaCreacionLabel.setVisible(true);
        fechaTerminacionLabel.setVisible(true);
        idLabel.setVisible(true);
        importeTotalLabel.setVisible(true);
        materialLabel.setVisible(true);
        nombreLabel.setVisible(true);
        resultadoLabel.setVisible(true);
        saldoLabel.setVisible(true);
        tablaCobros.setVisible(true);
        telefono2Label.setVisible(true);
        telefonoLabel.setVisible(true);
        descripcionLabelToComplete.setVisible(true);

        if (venta.getTelefono2() == null) {
            telefono2Label.setVisible(false);
        } else {
            telefono2LabelToComplete.setText(venta.getTelefono2());
        }

        if (venta.getTelefono2() == null) {
            telefono2Label.setVisible(false);
        }
        telefonoLabelToComplete.setText(venta.getTelefono1());

        if (nombreApellido.equals("")) {
            colocadorLabel.setVisible(false);
        }
        if (venta.getEmail().equals("")) {
            emailLabel.setVisible(false);
        }

        linea1.setVisible(true);
        linea2.setVisible(true);
        linea3.setVisible(true);
        linea4.setVisible(true);
        linea5.setVisible(true);
        linea6.setVisible(true);
        linea7.setVisible(true);
        linea8.setVisible(true);
        linea9.setVisible(true);
        linea10.setVisible(true);
        btnGenerarPDF.setVisible(true);
        cargarEntradas();
    }


    private void cargarEntradas() {
        try {
            boolean[] operacion = {false};
            int ventasID;
            ventasID =  Integer.parseInt(idLabelToComplete.getText());
            List<Entrada> listaEntrada = entradaModel.obtenerEntradaPorCliente(ventasID);
            tablaCobros.getItems().clear();
            tablaCobros.getColumns().clear();
            if (listaEntrada != null && !listaEntrada.isEmpty()) {
                Cotizacion cotizacion1 = new Cotizacion("", 0);
                TableColumn<Entrada, String> fechaColumn = new TableColumn<>("Fecha");
                TableColumn<Entrada, String> nombreClienteColumn = new TableColumn<>("Nombre");
                TableColumn<Entrada, String> descripcionColumn = new TableColumn<>("Descripción");
                TableColumn<Entrada, String> monedaColumn = new TableColumn<>("Moneda");
                TableColumn<Entrada, Double> importeColumn = new TableColumn<>("Importe");
                TableColumn<Entrada, Double> cotizacionColumn = new TableColumn<>("Cotizacion USD");
                TableColumn<Entrada, Double> importeEnPesosColumn = new TableColumn<>("Importe Total ARS");
                fechaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha()));
                nombreClienteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreCliente()));
                descripcionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDetalle()));
                monedaColumn.setCellValueFactory(cellData -> {
                    String monedaSimbolo;
                    int monedaID = cellData.getValue().getMonedasID();
                    if (monedaID == 1) {
                        monedaSimbolo = "ARS";
                        operacion[0] = false;
                    } else if (monedaID == 3) {
                        monedaSimbolo = "USD";
                        operacion[0] = true;
                    } else {
                        monedaSimbolo = "";
                    }
                    return new SimpleStringProperty(monedaSimbolo);
                });
                cotizacionColumn.setCellValueFactory(cellData -> {
                    int cotizacionesID = cellData.getValue().getCotizacionesID();
                    return new SimpleDoubleProperty(cotizacion1.getCotizacion(cotizacionesID)).asObject();
                });
                importeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getImporte()).asObject());
                importeEnPesosColumn.setCellValueFactory(cellData -> {
                    double importe = cellData.getValue().getImporte();
                    double cotizacion = cotizacionColumn.getCellObservableValue(cellData.getValue()).getValue();
                    double importeEnPesos = operacion[0] ? importe * cotizacion : importe;
                    return new SimpleDoubleProperty(importeEnPesos).asObject();
                });
                fechaColumn.setMaxWidth(1f * Integer.MAX_VALUE * 10);
                nombreClienteColumn.setMaxWidth(1f * Integer.MAX_VALUE * 25);
                descripcionColumn.setMaxWidth(1f * Integer.MAX_VALUE * 50);
                monedaColumn.setMaxWidth(1f * Integer.MAX_VALUE * 25);
                importeColumn.setMaxWidth(1f * Integer.MAX_VALUE * 25);
                cotizacionColumn.setMaxWidth(1f * Integer.MAX_VALUE * 25);
                importeEnPesosColumn.setMaxWidth(1f * Integer.MAX_VALUE * 25);
                tablaCobros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                List<TableColumn<Entrada, ?>> columnList = Arrays.asList(fechaColumn, nombreClienteColumn, descripcionColumn, monedaColumn, importeColumn, cotizacionColumn, importeEnPesosColumn);
                tablaCobros.getColumns().addAll(columnList);
                tablaCobros.getItems().addAll(listaEntrada);
                tablaCobros.setItems(FXCollections.observableList(listaEntrada));
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}