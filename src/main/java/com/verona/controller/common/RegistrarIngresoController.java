package com.verona.controller.common;

import java.sql.SQLException;
import com.verona.controller.SceneController;
import com.verona.model.Caja;
import com.verona.model.Cotizacion;
import com.verona.model.Entrada;
import com.verona.model.Senias;
import com.verona.model.User;
import com.verona.model.Venta;
import com.verona.model.VerificadorIngresosCredito;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrarIngresoController {
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
    private Label nombreClienteLabel;

    @FXML
    private Label nombreClienteLabelToComplete;

    @FXML
    private Button btnBuscar;

    private VerificadorIngresosCredito verificadorCredito = new VerificadorIngresosCredito(0, 0, 0);
    private Venta venta = new Venta(0, null, null, null, null, null, 0, 0, 0, null, null, 0, null, null, null, 0, 0);
    private User user = User.getCurrentUser();
    private Senias senia = new Senias(0, 0, 0, 0, 0, 0);
    private Caja caja = new Caja(0, 0);
    private Entrada entrada = new Entrada(null, null, 0, null, 0, 0, 0, 0, null);

    private void buscar() throws SQLException {
        try {
            int numero = Integer.parseInt(idNombreClienteTextField.getText());
            venta.findVentaById(numero);
            String nombreCliente = venta.getNombreCliente();
            venta.setVentasID(numero);

            if (numero > 0 && nombreCliente != null && !nombreCliente.isEmpty()) {
                hacerVisibles();
                mostrarAlerta("Venta Encontrada", AlertType.INFORMATION);
                nombreClienteLabelToComplete.setText(nombreCliente);

            } else {
                mostrarAlerta("Venta No Encontrada", AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            String texto = idNombreClienteTextField.getText();
            venta.findVentaByName(texto);
            String nombreCliente = venta.getNombreCliente();

            if (!texto.isEmpty() && nombreCliente != null && !nombreCliente.isEmpty()) {
                hacerVisibles();
                mostrarAlerta("Venta Encontrada", AlertType.INFORMATION);
                nombreClienteLabelToComplete.setText(nombreCliente);
            } else {
                mostrarAlerta("Venta No Encontrada", AlertType.ERROR);
            }
        }
    }

    @FXML
    void btnRegistrarEntradaClicked(ActionEvent event) throws SQLException {

        String detalle = detalleTextArea.getText();
        String importeTexto = importeTextField.getText();

        if (importeTexto.isEmpty()) {
            mostrarAlerta("Debe ingresar un importe válido", AlertType.ERROR);
            return;
        }

        double importe = Double.parseDouble(importeTexto);

        String metodoPagoSeleccionado = metodoPagosComboBox.getValue();
        String monedaSeleccionada = monedasComboBox.getValue();

        if (metodoPagoSeleccionado == null || monedaSeleccionada == null) {
            mostrarAlerta("Debe seleccionar un método de pago y una moneda", AlertType.ERROR);
            return;
        }

        Integer moneda = null;
        Integer ultimoIDCotizacion = null;
        double importeEnPesos = 0;
        double tasaCambio = 0;
        String nombreVendedor = user.getNombre() + "\n" + user.getApellido();

        double precioTotal = 0.0;
        precioTotal = venta.findVentaImporte(venta.getVentasID());
        double entradasDeVenta = entrada.calcularTotalEntradasEnPesos(venta.getVentasID());
        double saldo = precioTotal - entradasDeVenta;

        if ("ARS".equals(monedaSeleccionada)) {
            moneda = 1;
            importeEnPesos = importe;

            try {
                Entrada entrada = new Entrada(detalle, metodoPagoSeleccionado, moneda, importe, 0,
                        importeEnPesos,
                        venta.getVentasID(), user.getSucursalID(), nombreVendedor);
                boolean insercionExitosa = entrada.insertarEntradaPesos();

                if (insercionExitosa) {
                    mostrarAlerta("Entrada registrada correctamente", AlertType.INFORMATION);

                    if (importeEnPesos >= precioTotal) {
                        switch (metodoPagoSeleccionado) {
                            case "Efectivo":
                                caja.insertarCajaEfectivo(importeEnPesos, user.getSucursalID());
                                break;
                            case "Transferencia":
                                caja.insertarCajaBanco(importeEnPesos, user.getSucursalID());
                                break;
                            case "Tarjeta de débito":
                                caja.insertarCajaBanco(importeEnPesos, user.getSucursalID());
                                break;
                            case "Tarjeta de crédito":
                                verificadorCredito.insertVerificadorIngresosCredito(importeEnPesos,
                                        user.getSucursalID(), venta.getVentasID());
                                break;
                            case "Cheque":
                                caja.insertarCajaEfectivo(importeEnPesos, user.getSucursalID());
                                break;
                        }

                    } else {
                        switch (metodoPagoSeleccionado) {
                            case "Efectivo":
                                senia.insertarSeniaEfectivo(moneda, importeEnPesos, saldo - importeEnPesos,
                                        venta.getVentasID(), user.getSucursalID());
                                break;
                            case "Transferencia":
                                senia.insertarSeniaBanco(moneda, importeEnPesos, saldo - importeEnPesos,
                                        venta.getVentasID(), user.getSucursalID());
                                break;
                            case "Tarjeta de débito":
                                senia.insertarSeniaBanco(moneda, importeEnPesos, saldo - importeEnPesos,
                                        venta.getVentasID(), user.getSucursalID());
                                break;
                            case "Tarjeta de crédito":
                                verificadorCredito.insertVerificadorIngresosCredito(importeEnPesos,
                                        user.getSucursalID(), venta.getVentasID());
                                break;
                            case "Cheque":
                                senia.insertarSeniaEfectivo(moneda, importeEnPesos, saldo - importeEnPesos,
                                        venta.getVentasID(), user.getSucursalID());
                                break;
                        }
                    }

                } else {
                    mostrarAlerta("Error al registrar entrada", AlertType.ERROR);
                }

            } catch (SQLException e) {
                mostrarAlerta("Error al registrar entrada: " + e.getMessage(), AlertType.ERROR);
                e.printStackTrace();
            }
        }

        else if ("USD".equals(monedaSeleccionada))

        {
            moneda = 3;
            Cotizacion cotizacion = new Cotizacion("", 0);
            Object[] ultimaCotizacion = cotizacion.getUltimaCotizacion();

            ultimoIDCotizacion = (int) ultimaCotizacion[0];
            tasaCambio = (double) ultimaCotizacion[1];
            importeEnPesos = importe * tasaCambio;

            try {
                Entrada entrada = new Entrada(detalle, metodoPagoSeleccionado, moneda, importe, ultimoIDCotizacion,
                        importeEnPesos, venta.getVentasID(), user.getSucursalID(), nombreVendedor);
                boolean insercionExitosa = entrada.insertarEntrada();

                if (insercionExitosa) {

                    mostrarAlerta("Entrada registrada correctamente", AlertType.INFORMATION);
                    senia.insertarSeniaEfectivo(moneda, importeEnPesos, saldo - importeEnPesos,
                            venta.getVentasID(), user.getSucursalID());
                } else {
                    mostrarAlerta("Error al registrar entrada", AlertType.ERROR);
                }

            } catch (SQLException e) {
                mostrarAlerta("Error al registrar entrada: " + e.getMessage(), AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    private void mostrarAlerta(String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void btnBuscarClicked(ActionEvent event) throws SQLException {
        buscar();
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

    @FXML
    public void initialize() {
        llenarMetodoPagosComboBox();
        llenarMonedasComboBox();
        noVisibles();
    }

    private void llenarMetodoPagosComboBox() {
        ObservableList<String> metodoPagos = FXCollections.observableArrayList("Efectivo", "Tarjeta de débito",
                "Tarjeta de crédito", "Transferencia", "Cheque");
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
}
