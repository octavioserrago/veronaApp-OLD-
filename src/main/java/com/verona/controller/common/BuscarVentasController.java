package com.verona.controller.common;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.verona.controller.SceneController;
import com.verona.model.Colocador;
import com.verona.model.Cotizacion;
import com.verona.model.DatabaseConnection;
import com.verona.model.Entrada;
import com.verona.model.Plano;
import com.verona.model.User;
import com.verona.model.Venta;

public class BuscarVentasController {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnFotoPlano;

    @FXML
    private Label labelPdfAlert;

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
    Plano plano = new Plano("", 0, 0, null, 0, "");

    @FXML
    void initialize() {
        noVisibles();
        idString = "";
        id = 0;
        nombreCliente = "";
        entradaModel = new Entrada("", "", 0, null, 0, 0, 0, 0, "");
        ventaModel = new Venta(id, nombreCliente, "", "", "", "", 0, 0, 0, null, "", 0, "", "", "", 0);
    }

    @FXML
    void btnFotoPlanoClicked(ActionEvent event) {
        String id = idLabelToComplete.getText();
        int id1 = Integer.parseInt(id);

        Plano.setCurrentPlano(0, "codigoDeseado", 0, 0, null,
                id1, "estadoDeseado");

        SceneController sceneController = new SceneController((Stage) btnFotoPlano.getScene().getWindow());
        sceneController.switchToVerPlanos();
    }

    @FXML
    void btnGenerarPDFClicked(ActionEvent event) {
        Document document = new Document();

        try {

            String route = System.getProperty("user.home");

            String nombrePDF = "/Desktop/Detalle-Venta-" + nombreLabelToComplete.getText() + ".pdf";

            PdfWriter.getInstance(document, new FileOutputStream(route + nombrePDF));

            document.open();

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = localDate.format(formatter);
            String logoPath = "src/main/java/com/verona/images/verona.png";

            PdfPTable tableHeader = new PdfPTable(3);
            tableHeader.setWidthPercentage(100);
            float[] columnWidths2 = { 20f, 60f, 20f };
            tableHeader.setWidths(columnWidths2);

            Image logo = Image.getInstance(logoPath);
            logo.scaleAbsolute(115, 100);
            PdfPCell cellLogo = new PdfPCell(logo);
            cellLogo.setBorder(Rectangle.NO_BORDER);
            tableHeader.addCell(cellLogo);

            Font fontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.ITALIC);

            PdfPCell cellTitle = new PdfPCell(new Phrase("Detalle de Venta", fontItalic));
            cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTitle.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellTitle.setBorder(Rectangle.NO_BORDER);
            tableHeader.addCell(cellTitle);

            PdfPCell cellFechaText = new PdfPCell(new Phrase(formattedDate));
            cellFechaText.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellFechaText.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellFechaText.setBorder(Rectangle.NO_BORDER);
            tableHeader.addCell(cellFechaText);

            document.add(tableHeader);

            Font fontNegrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font fontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
            Font fontMini = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
            Font fontMiniSub = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.UNDERLINE);

            Paragraph paragraph1 = new Paragraph();
            Paragraph paragraph2 = new Paragraph();
            Paragraph paragraph4 = new Paragraph();
            Paragraph paragraph5 = new Paragraph();
            Paragraph paragraph7 = new Paragraph();
            Paragraph paragraph8 = new Paragraph();
            Paragraph paragraph9 = new Paragraph();
            Paragraph paragraph10 = new Paragraph();
            Paragraph paragraph11 = new Paragraph();
            Paragraph paragraph12 = new Paragraph();
            Paragraph paragraph13 = new Paragraph();
            Paragraph paragraph14 = new Paragraph();
            Paragraph paragraph15 = new Paragraph();

            Chunk chunkNegrita = new Chunk("ID Del Pedido: ", fontNegrita);
            paragraph1.add(chunkNegrita);
            Chunk chunkNormal = new Chunk(idLabelToComplete.getText(), fontNormal);
            paragraph1.add(chunkNormal);
            document.add(paragraph1);
            document.add(new Paragraph("\n"));

            Chunk chunkNegrita2 = new Chunk("Cliente: ", fontNegrita);
            paragraph2.add(chunkNegrita2);
            Chunk chunkNormal2 = new Chunk(nombreLabelToComplete.getText(), fontNormal);
            paragraph2.add(chunkNormal2);
            document.add(paragraph2);
            document.add(new Paragraph("\n"));

            Chunk chunkNegrita12 = new Chunk("Descripción: ", fontNegrita);
            paragraph12.add(chunkNegrita12);
            Chunk chunkNormal12 = new Chunk(descripcionLabelToComplete.getText(), fontNormal);
            paragraph12.add(chunkNormal12);
            document.add(paragraph12);
            document.add(new Paragraph("\n"));

            Chunk chunkNegrita4 = new Chunk("Fecha Estimada de Terminación: ", fontNegrita);
            paragraph4.add(chunkNegrita4);
            Chunk chunkNormal4 = new Chunk(fechaTerminacionLabelToComplete.getText(), fontNormal);
            paragraph4.add(chunkNormal4);
            document.add(paragraph4);
            document.add(new Paragraph("\n"));

            Chunk chunkNegrita5 = new Chunk("Material: ", fontNegrita);
            paragraph5.add(chunkNegrita5);
            Chunk chunkNormal5 = new Chunk(materialLabelToComplete.getText(), fontNormal);
            paragraph5.add(chunkNormal5);
            document.add(paragraph5);
            document.add(new Paragraph("\n"));

            Chunk chunkNegrita7 = new Chunk("Color: ", fontNegrita);
            paragraph7.add(chunkNegrita7);
            Chunk chunkNormal7 = new Chunk(colorLabelToComplete.getText(), fontNormal);
            paragraph7.add(chunkNormal7);
            document.add(paragraph7);
            document.add(new Paragraph("\n"));

            Chunk chunkNegrita8 = new Chunk("Importe Total de la Venta: ", fontNegrita);
            paragraph8.add(chunkNegrita8);

            try {
                String importeTotal = importeTotalLabelToComplete.getText();
                double importeV = Double.parseDouble(importeTotal);
                Locale localeArgentina = Locale.forLanguageTag("es-AR");
                NumberFormat formatoMonedaD = NumberFormat.getCurrencyInstance(localeArgentina);

                String importeFormateado = formatoMonedaD.format(importeV);

                Chunk chunkNormal8 = new Chunk(importeFormateado, fontNormal);
                paragraph8.add(chunkNormal8);
                document.add(paragraph8);
                document.add(new Paragraph("\n"));

            } catch (Exception e) {
                e.printStackTrace();
            }

            Chunk chunkNegrita9 = new Chunk("Saldo Restante: ", fontNegrita);
            paragraph9.add(chunkNegrita9);
            if (!saldoLabelToComplete.getText().equals("0")) {
                String importeSaldo = saldoLabelToComplete.getText();
                double saldo = Double.parseDouble(importeSaldo);
                Locale localeArgentina = Locale.forLanguageTag("es-AR");
                NumberFormat formatoSaldo = NumberFormat.getCurrencyInstance(localeArgentina);
                String importeFormateado1 = formatoSaldo.format(saldo);

                Chunk chunkNormal9 = new Chunk(importeFormateado1, fontNormal);
                paragraph9.add(chunkNormal9);
                document.add(paragraph9);
                document.add(new Paragraph("\n"));
            } else {

                Chunk chunkNormal9 = new Chunk(
                        "No hay saldo pendiente. El cliente ha abonado la totalidad de la venta.", fontNormal);
                paragraph9.add(chunkNormal9);
                document.add(paragraph9);
                document.add(new Paragraph("\n"));
            }
            String nombreColocador = colocadorLabelToComplete.getText();

            if (!"Ninguno".equals(nombreColocador)) {

                document.add(new LineSeparator());
                Chunk chunkNegrita10 = new Chunk("Colocador: ", fontNegrita);
                paragraph10.add(chunkNegrita10);
                Chunk chunkNormal10 = new Chunk(colocadorLabelToComplete.getText(), fontNormal);
                paragraph10.add(chunkNormal10);
                document.add(paragraph10);
                document.add(new Paragraph("\n"));

                Chunk chunkNegrita11 = new Chunk("Precio de Flete, Envio y Colocacion: ", fontNegrita);
                paragraph11.add(chunkNegrita11);
                Chunk chunkNormal11 = new Chunk(precioColocacionLabelToComplete.getText(), fontNormal);
                paragraph11.add(chunkNormal11);
                document.add(paragraph11);
                document.add(new Paragraph("\n"));
                document.add(new LineSeparator());

            }

            PdfPTable tableEntradas = new PdfPTable(8);
            tableEntradas.setWidthPercentage(100);
            float[] columnWidths = { 40f, 50f, 30f, 28f, 30f, 35f, 40f, 37f };
            tableEntradas.setWidths(columnWidths);
            Font fontTitulosTabla = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

            tableEntradas.addCell(new PdfPCell(new Paragraph("Fecha", fontTitulosTabla)));
            tableEntradas.addCell(new PdfPCell(new Paragraph("Descripción", fontTitulosTabla)));
            tableEntradas.addCell(new PdfPCell(new Paragraph("Método de Pago", fontTitulosTabla)));
            tableEntradas.addCell(new PdfPCell(new Paragraph("Moneda", fontTitulosTabla)));
            tableEntradas.addCell(new PdfPCell(new Paragraph("Importe", fontTitulosTabla)));
            tableEntradas.addCell(new PdfPCell(new Paragraph("Cotización USD", fontTitulosTabla)));
            tableEntradas.addCell(new PdfPCell(new Paragraph("Importe Total en ARS", fontTitulosTabla)));
            tableEntradas.addCell(new PdfPCell(new Paragraph("Vendedor", fontTitulosTabla)));

            try {

                DatabaseConnection con = new DatabaseConnection();
                Connection conexion = con.conectar();

                if (conexion != null) {

                    int idVenta = Integer.parseInt(idLabelToComplete.getText());

                    PreparedStatement preparedStatement = conexion.prepareStatement(
                            "SELECT fecha, detalle, metodoPago, monedasID, importe, cotizacionesID, nombreVendedor FROM Entradas WHERE ventasID = ?");

                    preparedStatement.setInt(1, idVenta);

                    ResultSet rs = preparedStatement.executeQuery();

                    if (rs.next()) {
                        do {
                            Cotizacion cotizacion1 = new Cotizacion("", 0);
                            int monedaID = rs.getInt(4);
                            String monedaSimbolo = (monedaID == 1) ? "ARS" : (monedaID == 3) ? "USD" : "";

                            int cotizacionesID = rs.getInt(6);
                            Double tasaCambio = cotizacion1.getCotizacion(cotizacionesID);

                            double importe = rs.getDouble(5);
                            double importeEnPesos = (monedaID == 3) ? importe * tasaCambio : importe;

                            String fechaString = rs.getString(1);
                            LocalDate fecha = LocalDate.parse(fechaString);
                            String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                            if (tasaCambio != null) {
                                tableEntradas.addCell(fechaFormateada);
                                tableEntradas.addCell(rs.getString(2));
                                tableEntradas.addCell(rs.getString(3));
                                tableEntradas.addCell(monedaSimbolo);
                                tableEntradas.addCell(rs.getString(5));
                                tableEntradas.addCell(String.valueOf(tasaCambio));
                                tableEntradas.addCell(String.valueOf(importeEnPesos));
                                tableEntradas.addCell(rs.getString(7));
                            }

                        } while (rs.next());

                    } else {
                        System.out.println("No hay datos para la venta con ID: " + idVenta);
                    }
                } else {
                    System.out.println("No se pudo establecer la conexión a la base de datos.");
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
            document.add(tableEntradas);
            document.add(new Paragraph("\n"));

            Chunk chunkNormal1 = new Chunk(
                    "El horario de retiro es de lunes a viernes de 9:00 a 12:00 y de 13:30 a 16:30, sábados de 9:00 a 11:30.",
                    fontMini);
            paragraph13.add(chunkNormal1);
            document.add(paragraph13);

            Chunk chunkNorma = new Chunk(
                    "Flete y colocación (en caso de ser solicitado) corren por cuenta del cliente.", fontMini);
            paragraph14.add(chunkNorma);
            document.add(paragraph14);

            Chunk chunkNorma1 = new Chunk(
                    "Tratándose de un producto natural, deberán admitirse pequeñas variaciones en la tonalidad y el vetado de las mercaderías entregadas con respecto a las muestras exhibidas.",
                    fontMini);
            paragraph15.add(chunkNorma1);
            document.add(paragraph15);

            Chunk chunkAclaracion1 = new Chunk(
                    "La fecha estimada de terminación no es completamente precisa; para retirar, debe esperar a ser contactado/a por nosotros.",
                    fontMiniSub);
            document.add(new Paragraph(chunkAclaracion1));

            Chunk chunkAclaracion = new Chunk("Este documento no es válido como factura.", fontMini);
            document.add(new Paragraph(chunkAclaracion));

            Chunk saludo = new Chunk(
                    "¡Gracias por elegirnos! Valoramos tu preferencia y estamos comprometidos a ofrecerte el mejor servicio posible.",
                    fontNegrita);
            document.add(new Paragraph(saludo));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            float[] columnWidths1 = { 10f, 90f, 10f, 50f, 10f, 50f };
            table.setWidths(columnWidths1);

            Image locationImage = Image.getInstance("src/main/java/com/verona/images/location.png");
            locationImage.scaleAbsolute(15, 15);
            PdfPCell cell1a = new PdfPCell(locationImage);
            cell1a.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell1a);

            PdfPCell cell2TextA = new PdfPCell(new Phrase("Juan Bautista Alberdi 3333, CABA"));
            cell2TextA.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell2TextA);

            Image phoneImage = Image.getInstance("src/main/java/com/verona/images/wpp.png");
            phoneImage.scaleAbsolute(15, 15);
            PdfPCell cell3 = new PdfPCell(phoneImage);
            cell3.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell3);

            PdfPCell cell4Text = new PdfPCell(new Phrase("11-5990-6984"));
            cell4Text.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell4Text);

            Image instagramImage = Image.getInstance("src/main/java/com/verona/images/ig.png");
            instagramImage.scaleAbsolute(15, 15);
            PdfPCell cell5 = new PdfPCell(instagramImage);
            cell5.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell5);

            PdfPCell cell6Text = new PdfPCell(new Phrase("@marmoleria.verona"));
            cell6Text.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell6Text);

            document.add(table);

            document.add(new Paragraph("\n"));

            document.close();
            labelPdfAlert.setTextFill(Color.GREEN.darker());
            labelPdfAlert.setText("PDF Creado correctamente en Escritorio!");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
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
        btnFotoPlano.setVisible(false);
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
        btnFotoPlano.setVisible(true);
        cargarEntradas();
    }

    private void cargarEntradas() {
        try {
            boolean[] operacion = { false };
            int ventasID;
            ventasID = Integer.parseInt(idLabelToComplete.getText());
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
                nombreClienteColumn.setCellValueFactory(
                        cellData -> new SimpleStringProperty(cellData.getValue().getNombreCliente()));
                descripcionColumn
                        .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDetalle()));
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
                importeColumn.setCellValueFactory(
                        cellData -> new SimpleDoubleProperty(cellData.getValue().getImporte()).asObject());
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
                List<TableColumn<Entrada, ?>> columnList = Arrays.asList(fechaColumn, nombreClienteColumn,
                        descripcionColumn, monedaColumn, importeColumn, cotizacionColumn, importeEnPesosColumn);
                tablaCobros.getColumns().addAll(columnList);
                tablaCobros.getItems().addAll(listaEntrada);
                tablaCobros.setItems(FXCollections.observableList(listaEntrada));
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}