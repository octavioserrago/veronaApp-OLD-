package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int ventasID;
    private String nombreCliente;
    private String descripcion;
    private String material;
    private String color;
    private String fechaEstimadaTerminacion;
    private int colocadoresID;
    private double precioColocacion;
    private int monedasID;
    private Double importe;
    private String fotoPlano;
    private String estado;
    private int token;
    private String telefono1;
    private String telefono2;
    private String email;

    
    public Venta(int ventasID,String nombreCliente, String descripcion, String material, String color,
                 String fechaEstimadaTerminacion, int colocadoresID, double precioColocacion, int monedasID, Double importe,
                 String fotoPlano, String estado, int token, String telefono1, String telefono2, String email) {
        this.ventasID = ventasID;
        this.nombreCliente = nombreCliente;
        this.descripcion = descripcion;
        this.material = material;
        this.color = color;
        this.fechaEstimadaTerminacion = fechaEstimadaTerminacion;
        this.colocadoresID = colocadoresID;
        this.precioColocacion = precioColocacion;
        this.monedasID = monedasID;
        this.importe = importe;
        this.fotoPlano = fotoPlano;
        this.estado = estado;
        this.token = token;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.email = email;
    }

    public int getVentasID() {
        return ventasID;
    }
    public void setVentasID(int ventasID) {
        this.ventasID = ventasID;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getMaterial() {
        return material;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getFechaEstimadaTerminacion() {
        return fechaEstimadaTerminacion;
    }
    public void setFechaEstimadaTerminacion(String fechaEstimadaTerminacion) {
        this.fechaEstimadaTerminacion = fechaEstimadaTerminacion;
    }
    public int getColocadoresID() {
        return colocadoresID;
    }
    public void setColocadoresID(int colocadoresID) {
        this.colocadoresID = colocadoresID;
    }
    public double getPrecioColocacion() {
        return precioColocacion;
    }
    public void setPrecioColocacion(double precioColocacion) {
        this.precioColocacion = precioColocacion;
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
    
    public String getFotoPlano() {
        return fotoPlano;
    }

    public void setFotoPlano(String fotoPlano) {
        this.fotoPlano = fotoPlano;
    }
    
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public int getToken() {
        return token;
    }
    public void setToken(int token) {
        this.token = token;
    }
    public String getTelefono1() {
        return telefono1;
    }
    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }
    public String getTelefono2() {
        return telefono2;
    }
    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();
	
	PreparedStatement stmt;

    public void insertarVenta() throws SQLException {
        String sql = "INSERT INTO Ventas (nombreCliente, descripcion, material, color," +
                "fechaEstimadaTerminacion, colocadoresID, precioColocacion, monedasID, importe, " +
                "estado, token, telefono1, telefono2, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, nombreCliente);
            preparedStatement.setString(2, descripcion);
            preparedStatement.setString(3, material);
            preparedStatement.setString(4, color);
            preparedStatement.setString(5, fechaEstimadaTerminacion);
            preparedStatement.setInt(6, colocadoresID);
            preparedStatement.setDouble(7, precioColocacion);
            preparedStatement.setInt(8, 1);
            preparedStatement.setDouble(9, importe);
            preparedStatement.setString(10, estado);
            preparedStatement.setInt(11, tokenGenerator());
            preparedStatement.setString(12, telefono1);
            preparedStatement.setString(13, telefono2);
            preparedStatement.setString(14, email);

            preparedStatement.executeUpdate();
        }
    }

    public List<Venta> allVentas() throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM Ventas";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Venta venta = new Venta(
                        resultSet.getInt("ventasID"),
                        resultSet.getString("nombreCliente"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("material"),
                        resultSet.getString("color"),
                        resultSet.getString("fechaEstimadaTerminacion"),
                        resultSet.getInt("colocadoresID"),
                        resultSet.getDouble("precioColocacion"),
                        resultSet.getInt("monedasID"),
                        resultSet.getDouble("importe"),
                        resultSet.getString("fotoPlano"),
                        resultSet.getString("estado"),
                        resultSet.getInt("token"),
                        resultSet.getString("telefono1"),
                        resultSet.getString("telefono2"),
                        resultSet.getString("email")
                );

                ventas.add(venta);
            }
        }

        return ventas;
    }


    public int tokenGenerator(){
        int tokenGenerated = (int)(Math.random()* 9000)+1000;
        return tokenGenerated;
    }
    

}
