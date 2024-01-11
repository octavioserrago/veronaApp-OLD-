package Controllers.Seller;

import java.sql.SQLException;

import Controllers.SceneController;
import Data.Cotizacion;
import Data.Entrada;
import Data.Venta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class cajaSellerController {

    @FXML
    private Button btnRegistrarEntrada;

    @FXML
    private Button btnVolver;

    @FXML
    private Label detalleLabel;

    @FXML
    private TextArea detalleTextArea;

    @FXML
    private TextField idNombreClienteTextField;

    @FXML
    private Label importeLabel;

    @FXML
    private TextField importeTextField;

    @FXML
    private Label metodoPagoLabel;

    @FXML
    private ComboBox<String> metodoPagosComboBox;

    @FXML
    private ComboBox<String> monedasComboBox;

    @FXML
    private Label monedasLabel;

    @FXML
    private Label resultadoBusquedaLabel;

    @FXML
    private Label resultadoRegistroLabel;

    @FXML
    private Label nombreClienteLabel;

    @FXML
    private Label nombreClienteLabelToComplete;

    @FXML
    private Button btnBuscar;

    Venta venta = new Venta(0, null, null, null, null, null, 0, 0, 0, null, null, null, 0, null, null, null);

    private void buscar() {
        try {
            int numero = Integer.parseInt(idNombreClienteTextField.getText());
            venta.findVentaById(numero);
            String nombreCliente = venta.getNombreCliente();
            System.out.println("Nombre del cliente: " + nombreCliente);

            if (numero > 0 && nombreCliente != null && !nombreCliente.isEmpty()) {
                hacerVisibles();
                mostrarResultado("Venta Encontrada", true);
                nombreClienteLabelToComplete.setText(nombreCliente);
                venta.setVentasID(numero);
            } else {
                mostrarResultado("Venta No Encontrada", false);
            }

        } catch (NumberFormatException e) {
            String texto = idNombreClienteTextField.getText();
            venta.findVentaByName(texto);
            String nombreCliente = venta.getNombreCliente();

            if (!texto.isEmpty() && nombreCliente != null && !nombreCliente.isEmpty()) {
                hacerVisibles();
                mostrarResultado("Venta Encontrada", true);
                nombreClienteLabelToComplete.setText(nombreCliente);

            } else {
                mostrarResultado("Venta No Encontrada", false);
            }
        }
    }

    @FXML
    void btnRegistrarEntradaClicked(ActionEvent event) {
        String detalle = detalleTextArea.getText();
        String importeTexto = importeTextField.getText();

        if (importeTexto.isEmpty()) {
            mostrarMensaje("Debe ingresar un importe válido", "red");
            return;
        }

        double importe = Double.parseDouble(importeTexto);

        String metodoPagoSeleccionado = metodoPagosComboBox.getValue();
        String monedaSeleccionada = monedasComboBox.getValue();

        if (metodoPagoSeleccionado == null || monedaSeleccionada == null) {
            mostrarMensaje("Debe seleccionar un método de pago y una moneda", "red");
            return;
        }

        Integer moneda = null;
        Integer ultimoIDCotizacion = null;
        double importeEnPesos = 0;
        double tasaCambio = 0;

        if ("ARS".equals(monedaSeleccionada)) {
            moneda = 1;
            importeEnPesos = importe;

            try {
                Entrada entrada = new Entrada(detalle, metodoPagoSeleccionado, moneda, importe, 0, importeEnPesos, venta.getVentasID());
                boolean insercionExitosa = entrada.insertarEntradaPesos();

                if (insercionExitosa) {
                    mostrarMensaje("Entrada registrada correctamente", "green");
                } else {
                    mostrarMensaje("Error al registrar entrada", "red");
                }

            } catch (SQLException e) {
                mostrarMensaje("Error al registrar entrada: " + e.getMessage(), "red");
                e.printStackTrace();
            }
            
        } else if ("USD".equals(monedaSeleccionada)) {
            moneda = 3;
            Cotizacion cotizacion = new Cotizacion("", 0);
            Object[] ultimaCotizacion = cotizacion.getUltimaCotizacion();

            ultimoIDCotizacion = (int) ultimaCotizacion[0];
            tasaCambio = (double) ultimaCotizacion[1];
            importeEnPesos = importe * tasaCambio;

            try {
            Entrada entrada = new Entrada(detalle, metodoPagoSeleccionado, moneda, importe, ultimoIDCotizacion, importeEnPesos, venta.getVentasID());
            boolean insercionExitosa = entrada.insertarEntrada();

            if (insercionExitosa) {
                mostrarMensaje("Entrada registrada correctamente", "green");
            } else {
                mostrarMensaje("Error al registrar entrada", "red");
            }

            } catch (SQLException e) {
                mostrarMensaje("Error al registrar entrada: " + e.getMessage(), "red");
                e.printStackTrace();
            }
        }
    }


    private void mostrarMensaje(String mensaje, String color) {
        resultadoRegistroLabel.setText(mensaje);
        resultadoRegistroLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    @FXML
    void btnBuscarClicked(ActionEvent event) {
        buscar();
    }

    @FXML
    void btnVolverClicked(ActionEvent event) {
        SceneController sceneController = new SceneController((Stage) btnVolver.getScene().getWindow());
        sceneController.switchToDashboardSeller();
    }

    @FXML
    public void initialize() {
        llenarMetodoPagosComboBox();
        llenarMonedasComboBox();
        noVisibles();
    }

    private void llenarMetodoPagosComboBox() {
        ObservableList<String> metodoPagos = FXCollections.observableArrayList("Efectivo", "Tarjeta de débito", "Tarjeta de crédito", "Transferencia", "Cheque");
        metodoPagosComboBox.setItems(metodoPagos);
    }

    private void llenarMonedasComboBox() {
        ObservableList<String> monedas = FXCollections.observableArrayList("ARS", "USD");
        monedasComboBox.setItems(monedas);
    }

    private void noVisibles() {
        btnRegistrarEntrada.setVisible(false);
        detalleLabel.setVisible(false);
        detalleTextArea.setVisible(false);
        importeLabel.setVisible(false);
        importeTextField.setVisible(false);
        metodoPagoLabel.setVisible(false);
        metodoPagosComboBox.setVisible(false);
        monedasComboBox.setVisible(false);
        monedasLabel.setVisible(false);
        nombreClienteLabel.setVisible(false);
    }

    private void hacerVisibles() {
        btnRegistrarEntrada.setVisible(true);
        detalleLabel.setVisible(true);
        detalleTextArea.setVisible(true);
        importeLabel.setVisible(true);
        importeTextField.setVisible(true);
        metodoPagoLabel.setVisible(true);
        metodoPagosComboBox.setVisible(true);
        monedasComboBox.setVisible(true);
        monedasLabel.setVisible(true);
        nombreClienteLabel.setVisible(true);
    }

    private void mostrarResultado(String mensaje, boolean exitoso) {
        resultadoBusquedaLabel.setText(mensaje);

        if (exitoso) {
            resultadoBusquedaLabel.setStyle("-fx-text-fill: green; -fx-alignment: center;");
        } else {
            resultadoBusquedaLabel.setStyle("-fx-text-fill: red; -fx-alignment: center;");
        }
        resultadoBusquedaLabel.setVisible(true);
    }

}
