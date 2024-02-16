package com.verona.model;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class Venta {
    private int ventasID;
    private String nombreCliente;
    private String descripcion;
    private String material;
    private String color;
    private String fecha;
    private String fechaEstimadaTerminacion;
    private int colocadoresID;
    private double precioColocacion;
    private int monedasID;
    private Double importe;
    private String estado;
    private int token;
    private String telefono1;
    private String telefono2;
    private String email;
    private String nombreApellidoColocador;
    private int sucursalID;
    private int usersID;

    public Venta(int ventasID, String nombreCliente, String descripcion, String material, String color,
            String fechaEstimadaTerminacion, int colocadoresID, double precioColocacion, int monedasID, Double importe,
            String estado, int token, String telefono1, String telefono2, String email, int sucursalID, int usersID) {
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
        this.estado = estado;
        this.token = token;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.email = email;
        this.sucursalID = sucursalID;
        this.usersID = usersID;
    }

    public int getUsersID() {
        return usersID;
    }

    public void setUsersID(int usersID) {
        this.usersID = usersID;
    }

    public void setNombreApellidoColocador(String nombreApellidoColocador) {
        this.nombreApellidoColocador = nombreApellidoColocador;
    }

    public String getNombreApellidoColocador() {
        return nombreApellidoColocador;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public int getSucursalID() {
        return sucursalID;
    }

    public void setSucursalID(int sucursalID) {
        this.sucursalID = sucursalID;
    }

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public void insertarVenta() throws SQLException {
        String sql = "INSERT INTO Ventas (nombreCliente, descripcion, material, color," +
                "fechaEstimadaTerminacion, colocadoresID, precioColocacion, monedasID, importe, " +
                "estado, token, telefono1, telefono2, email, sucursalID, usersID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

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
            preparedStatement.setInt(15, sucursalID);
            preparedStatement.setInt(16, usersID);

            preparedStatement.executeUpdate();
        }
    }

    public List<Venta> allVentas() throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM Ventas";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int colocadorID = resultSet.getInt("colocadoresID");

                Venta venta = new Venta(
                        resultSet.getInt("ventasID"),
                        resultSet.getString("nombreCliente"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("material"),
                        resultSet.getString("color"),
                        resultSet.getString("fechaEstimadaTerminacion"),
                        colocadorID,
                        resultSet.getDouble("precioColocacion"),
                        resultSet.getInt("monedasID"),
                        resultSet.getDouble("importe"),
                        resultSet.getString("estado"),
                        resultSet.getInt("token"),
                        resultSet.getString("telefono1"),
                        resultSet.getString("telefono2"),
                        resultSet.getString("email"),
                        resultSet.getInt("sucursalID"),
                        resultSet.getInt("usersID"));

                venta.setFecha(formatFecha(resultSet.getString("fecha"), "fecha"));
                venta.setFechaEstimadaTerminacion(formatFechaTerminacion(
                        resultSet.getString("fechaEstimadaTerminacion"), "fechaEstimadaTerminacion"));
                String nombreApellidoColocador = getNombreApellidoPorColocadorID(colocadorID);
                venta.setNombreApellidoColocador(nombreApellidoColocador);

                ventas.add(venta);
            }
        }

        return ventas;
    }

    private String formatFecha(String fecha, String tipoFecha) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date = originalFormat.parse(fecha);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return fecha;
        }
    }

    private String formatFechaTerminacion(String fecha, String tipoFecha) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date = originalFormat.parse(fecha);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return fecha;
        }
    }

    public String getNombreApellidoPorColocadorID(int colocadorID) {
        String nombreApellido = null;
        String sql = "SELECT nombreApellido FROM colocadores WHERE colocadoresID = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, colocadorID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    nombreApellido = resultSet.getString("nombreApellido");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombreApellido;
    }

    public int tokenGenerator() {
        int tokenGenerated = (int) (Math.random() * 9000) + 1000;
        return tokenGenerated;
    }

    public Venta findVentaById(int ventaID) {
        String sql = "SELECT * FROM Ventas WHERE ventasID = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, ventaID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    java.sql.Date fechaEstimadaTerminacionSQL = resultSet.getDate("fechaEstimadaTerminacion");
                    java.util.Date fechaEstimadaTerminacionUtil = new java.util.Date(
                            fechaEstimadaTerminacionSQL.getTime());
                    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                    String fechaEstimadaTerminacionFormateada = formato.format(fechaEstimadaTerminacionUtil);
                    String nombreCliente = resultSet.getString("nombreCliente");
                    setNombreCliente(nombreCliente);

                    return new Venta(
                            resultSet.getInt("ventasID"),
                            nombreCliente,
                            resultSet.getString("descripcion"),
                            resultSet.getString("material"),
                            resultSet.getString("color"),
                            fechaEstimadaTerminacionFormateada,
                            resultSet.getInt("colocadoresID"),
                            resultSet.getDouble("precioColocacion"),
                            resultSet.getInt("monedasID"),
                            resultSet.getDouble("importe"),
                            resultSet.getString("estado"),
                            resultSet.getInt("token"),
                            resultSet.getString("telefono1"),
                            resultSet.getString("telefono2"),
                            resultSet.getString("email"),
                            resultSet.getInt("sucursalID"),
                            resultSet.getInt("usersID"));

                } else {
                    System.err.println("No se encontró ninguna venta con el ID: " + ventaID);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la venta con ID: " + ventaID);
            e.printStackTrace();
        }

        return null;
    }

    public Venta findVentaByName(String nombreCliente) {
        String sql = "SELECT * FROM Ventas WHERE nombreCliente LIKE ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + nombreCliente + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    java.sql.Date fechaEstimadaTerminacionSQL = resultSet.getDate("fechaEstimadaTerminacion");
                    java.util.Date fechaEstimadaTerminacionUtil = new java.util.Date(
                            fechaEstimadaTerminacionSQL.getTime());
                    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                    String fechaEstimadaTerminacionFormateada = formato.format(fechaEstimadaTerminacionUtil);
                    String nombreCliente1 = resultSet.getString("nombreCliente");
                    setNombreCliente(nombreCliente1);
                    int ventasID = resultSet.getInt("ventasID");
                    setVentasID(ventasID);

                    return new Venta(
                            ventasID,
                            nombreCliente1,
                            resultSet.getString("descripcion"),
                            resultSet.getString("material"),
                            resultSet.getString("color"),
                            fechaEstimadaTerminacionFormateada,
                            resultSet.getInt("colocadoresID"),
                            resultSet.getDouble("precioColocacion"),
                            resultSet.getInt("monedasID"),
                            resultSet.getDouble("importe"),
                            resultSet.getString("estado"),
                            resultSet.getInt("token"),
                            resultSet.getString("telefono1"),
                            resultSet.getString("telefono2"),
                            resultSet.getString("email"),
                            resultSet.getInt("sucursalID"),
                            resultSet.getInt("usersID"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar venta por nombre: " + nombreCliente);
            e.printStackTrace();
        }

        return null;
    }

    public String obtenerFechaCreacion(int ventasID) throws SQLException {
        String sql = "SELECT fecha FROM Ventas WHERE ventasID = ?";
        String fechaFormateada = null;

        try {
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, ventasID);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                java.sql.Date fechaSQL = resultSet.getDate("fecha");

                java.util.Date fechaUtil = new java.util.Date(fechaSQL.getTime());

                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                fechaFormateada = formato.format(fechaUtil);
            }

        } catch (Exception e) {
            throw new SQLException("Error al buscar fecha de creación: " + e.getMessage(), e);
        }

        return fechaFormateada;
    }

    public ObservableList<XYChart.Data<String, Integer>> obtenerVentasPorMes(int sucursalID) throws SQLException {
        ObservableList<XYChart.Data<String, Integer>> datosVentasPorMes = FXCollections.observableArrayList();

        String sql = "SELECT MONTH(fecha) AS mes, COUNT(*) AS cantidad FROM Ventas WHERE sucursalID = ? GROUP BY mes";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, sucursalID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int mes = resultSet.getInt("mes");
                    String nombreMes = obtenerNombreMes(mes);
                    int cantidad = resultSet.getInt("cantidad");

                    datosVentasPorMes.add(new XYChart.Data<>(nombreMes, cantidad));
                }
            }
        }

        return datosVentasPorMes;
    }

    public String obtenerNombreMes(int numeroMes) {
        String[] meses = { "", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
                "Octubre", "Noviembre", "Diciembre" };
        return meses[numeroMes];
    }

}