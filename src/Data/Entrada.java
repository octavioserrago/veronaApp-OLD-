package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Entrada {
    private String fecha;
    private String detalle;
    private String metodoPago;
    private int monedasID;
    private Double importe;
    private Integer cotizacionesID;
    private double importeEnPesos;
    private int ventasID;

    

    public Entrada(String detalle, String metodoPago, int monedasID, Double importe, int cotizacionesID,
            double importeEnPesos, int ventasID) {
        this.detalle = detalle;
        this.metodoPago = metodoPago;
        this.monedasID = monedasID;
        this.importe = importe;
        this.cotizacionesID = cotizacionesID;
        this.importeEnPesos = importeEnPesos;
        this.ventasID = ventasID;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getMonedasID() {
        return monedasID;
    }

    public void setMonedasID(int monedasID) {
        this.monedasID = monedasID;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public int getCotizacionesID() {
        return cotizacionesID;
    }

    public void setCotizacionesID(int cotizacionesID) {
        this.cotizacionesID = cotizacionesID;
    }

    public double getImporteEnPesos() {
        return importeEnPesos;
    }

    public void setImporteEnPesos(double importeEnPesos) {
        this.importeEnPesos = importeEnPesos;
    }

    public int getVentasID() {
        return ventasID;
    }

    public void setVentasID(int ventasID) {
        this.ventasID = ventasID;
    }

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();
	
	PreparedStatement stmt;

    public boolean insertarEntrada() throws SQLException {
        String sql = "INSERT INTO Entradas (detalle, metodoPago, monedasID, importe, cotizacionesID, importeEnPesos, ventasID) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, detalle);
            preparedStatement.setString(2, metodoPago);
            preparedStatement.setInt(3, monedasID);
            preparedStatement.setDouble(4, importe);
            preparedStatement.setInt(5, cotizacionesID);
            preparedStatement.setDouble(6, importeEnPesos);
            preparedStatement.setInt(7, ventasID);
    
            int filasAfectadas = preparedStatement.executeUpdate();
    
            return filasAfectadas > 0;
        }
    }

    public boolean insertarEntradaPesos() throws SQLException {
        String sql = "INSERT INTO Entradas (detalle, metodoPago, monedasID, importe, importeEnPesos, ventasID) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, detalle);
            preparedStatement.setString(2, metodoPago);
            preparedStatement.setInt(3, monedasID);
            preparedStatement.setDouble(4, importe);
            preparedStatement.setDouble(5, importeEnPesos);
            preparedStatement.setInt(6, ventasID);
    
            int filasAfectadas = preparedStatement.executeUpdate();
    
            return filasAfectadas > 0;
        }
    }
}
