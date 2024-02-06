package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Entrada {
    private String fecha;
    private String detalle;
    private String metodoPago;
    private int monedasID;
    private Double importe;
    private Integer cotizacionesID;
    private double importeEnPesos;
    private int ventasID;
    private int sucursalID;
    private String simboloMoneda;
    private double tasaCambio;
    private String nombreCliente;
    private String nombreVendedor;

    public Entrada(String detalle, String metodoPago, int monedasID, Double importe, int cotizacionesID,
            double importeEnPesos, int ventasID, int sucursalID, String nombreVendedor) {
        this.detalle = detalle;
        this.metodoPago = metodoPago;
        this.monedasID = monedasID;
        this.importe = importe;
        this.cotizacionesID = cotizacionesID;
        this.importeEnPesos = importeEnPesos;
        this.ventasID = ventasID;
        this.sucursalID = sucursalID;
        this.nombreVendedor = nombreVendedor;
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

    public int getSucursalID() {
        return sucursalID;
    }

    public void setSucursalID(int sucursalID) {
        this.sucursalID = sucursalID;
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

    public String getSimboloMoneda() {
        return simboloMoneda;
    }

    public void setSimboloMoneda(String simboloMoneda) {
        this.simboloMoneda = simboloMoneda;
    }

    public double getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public boolean insertarEntrada() throws SQLException {
        String sql = "INSERT INTO Entradas (detalle, metodoPago, monedasID, importe, cotizacionesID, importeEnPesos, ventasID, sucursalID, nombreVendedor) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, detalle);
            preparedStatement.setString(2, metodoPago);
            preparedStatement.setInt(3, monedasID);
            preparedStatement.setDouble(4, importe);
            preparedStatement.setInt(5, cotizacionesID);
            preparedStatement.setDouble(6, importeEnPesos);
            preparedStatement.setInt(7, ventasID);
            preparedStatement.setInt(8, sucursalID);
            preparedStatement.setString(9, nombreVendedor);

            int filasAfectadas = preparedStatement.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    public boolean insertarEntradaPesos() throws SQLException {
        String sql = "INSERT INTO Entradas (detalle, metodoPago, monedasID, importe, importeEnPesos, ventasID, sucursalID,nombreVendedor) VALUES (?, ?, ?, ?, ?,?,?,?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, detalle);
            preparedStatement.setString(2, metodoPago);
            preparedStatement.setInt(3, monedasID);
            preparedStatement.setDouble(4, importe);
            preparedStatement.setDouble(5, importeEnPesos);
            preparedStatement.setInt(6, ventasID);
            preparedStatement.setInt(7, sucursalID);
            preparedStatement.setString(8, nombreVendedor);

            int filasAfectadas = preparedStatement.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    public List<Entrada> obtenerEntradaPorCliente(int ventasID) throws SQLException {
        List<Entrada> entradasPorCliente = new ArrayList<>();
        String sql = "SELECT e.*, m.simbolo AS monedaSimbolo, c.tasaCambio, v.nombreCliente " +
                "FROM Entradas e " +
                "INNER JOIN Monedas m ON e.monedasID = m.monedasID " +
                "LEFT JOIN Cotizaciones c ON e.cotizacionesID = c.cotizacionesID " +
                "INNER JOIN Ventas v ON e.ventasID = v.ventasID " +
                "WHERE v.ventasID = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, ventasID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Entrada entrada = new Entrada(
                            resultSet.getString("detalle"),
                            resultSet.getString("metodoPago"),
                            resultSet.getInt("monedasID"),
                            resultSet.getDouble("importe"),
                            resultSet.getInt("cotizacionesID"),
                            resultSet.getDouble("importeEnPesos"),
                            resultSet.getInt("ventasID"),
                            resultSet.getInt("sucursalID"),
                            resultSet.getString("nombreVendedor"));

                    entrada.setFecha(resultSet.getString("fecha"));
                    entrada.setSimboloMoneda(resultSet.getString("monedaSimbolo"));
                    entrada.setTasaCambio(resultSet.getDouble("tasaCambio"));
                    entrada.setNombreCliente(resultSet.getString("nombreCliente"));
                    entrada.setNombreVendedor(resultSet.getString("nombreVendedor"));

                    entradasPorCliente.add(entrada);
                }
            }
        }

        return entradasPorCliente;
    }

    public double calcularTotalEntradasEnPesos(int ventasID) throws SQLException {
        double totalEntradasEnPesos = 0;
        String sql = "SELECT importeEnPesos FROM Entradas WHERE ventasID = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, ventasID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    double importeEnPesos = resultSet.getDouble("importeEnPesos");
                    totalEntradasEnPesos += importeEnPesos;
                }
            }
        }
        return totalEntradasEnPesos;
    }

    public double calcularTotalEntradasEnPesosPorSucursalYMes(int sucursalID) throws SQLException {
        double totalEntradasEnPesos = 0;
        String sql = "SELECT SUM(importeEnPesos) AS total FROM Entradas WHERE sucursalID = ? AND MONTH(fecha) = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, sucursalID);
            preparedStatement.setInt(2, LocalDate.now().getMonthValue());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalEntradasEnPesos = resultSet.getDouble("total");
                }
            }
        }

        return totalEntradasEnPesos;
    }

}