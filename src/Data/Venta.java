package Data;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Venta {
    private int ventasID;
    private String nombreCliente;
    private String descripcion;
    private String material;
    private String color;
    private int bachasID;
    private String fechaEstimadaTerminacion;
    private int colocadoresID;
    private double precioColocacion;
    private int monedasID;
    private Double importe;
    private File fotoPlano;
    private String estado;
    private int token;
    private String telefono1;
    private String telefono2;
    private String email;

    
    public Venta(String nombreCliente, String descripcion, String material, String color, int bachasID,
            String fechaEstimadaTerminacion, int colocadoresID, double precioColocacion, int monedasID, Double importe,
            File fotoPlano, String estado, int token, String telefono1, String telefono2, String email) {
        this.nombreCliente = nombreCliente;
        this.descripcion = descripcion;
        this.material = material;
        this.color = color;
        this.bachasID = bachasID;
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
    public int getBachasID() {
        return bachasID;
    }
    public void setBachasID(int bachasID) {
        this.bachasID = bachasID;
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
    public File getFotoPlano() {
        return fotoPlano;
    }
    public void setFotoPlano(File fotoPlano) {
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
        String sql = "INSERT INTO Ventas (nombreCliente, descripcion, material, color, bachasID, " +
                "fechaEstimadaTerminacion, colocadoresID, precioColocacion, monedasID, importe, " +
                "estado, token, telefono1, telefono2, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, nombreCliente);
            preparedStatement.setString(2, descripcion);
            preparedStatement.setString(3, material);
            preparedStatement.setString(4, color);
            preparedStatement.setInt(5, bachasID);
            preparedStatement.setString(6, fechaEstimadaTerminacion);
            preparedStatement.setInt(7, colocadoresID);
            preparedStatement.setDouble(8, precioColocacion);
            preparedStatement.setInt(9, 1);
            preparedStatement.setDouble(10, importe);
            preparedStatement.setString(11, estado);
            preparedStatement.setInt(12, tokenGenerator());
            preparedStatement.setString(13, telefono1);
            preparedStatement.setString(14, telefono2);
            preparedStatement.setString(15, email);

            preparedStatement.executeUpdate();
        }
    }

    public int tokenGenerator(){
        int tokenGenerated = (int)(Math.random()*4);
        return tokenGenerated;
    }
    

}
