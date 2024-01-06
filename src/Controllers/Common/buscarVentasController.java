package Controllers.Common;

import java.sql.SQLException;

import Controllers.SceneController;
import Data.Colocador;
import Data.Venta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TableView<?> tablaCobros;

    @FXML
    private Label telefono2Label;

    @FXML
    private Label telefono2LabelToComplete;

    @FXML
    private Label telefonoLabel;

    @FXML
    private Label telefonoLabelToComplete;

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
        ventaModel = new Venta(id, nombreCliente, "", "", "", "", 0, 0, 0, null, "", "", 0, "", "", "");
        
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


    public void noVisibles(){
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
        materialLabelToComplete.setText(venta.getMaterial());
        nombreLabelToComplete.setText(venta.getNombreCliente());
        precioColocacionLabelToComplete.setText(String.valueOf(venta.getPrecioColocacion()));
        telefono2LabelToComplete.setText(venta.getTelefono2());
        telefonoLabelToComplete.setText(venta.getTelefono1());
    
        colocadorLabel.setVisible(true);
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
        precioColocacionLabel.setVisible(true);
        resultadoLabel.setVisible(true);
        saldoLabel.setVisible(true);
        tablaCobros.setVisible(true);
        telefono2Label.setVisible(true);
        telefonoLabel.setVisible(true);
        descripcionLabelToComplete.setVisible(true);
    }
    


}

