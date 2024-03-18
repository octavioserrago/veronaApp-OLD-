package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransaccionesFinancieras {

    private String fecha;
    private String tipoMovimiento;
    private double importeEnPesos;
    private int sucursalID;

    public TransaccionesFinancieras(String tipoMovimiento, double importeEnPesos, int sucursalID) {
        this.tipoMovimiento = tipoMovimiento;
        this.importeEnPesos = importeEnPesos;
        this.sucursalID = sucursalID;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public double getImporteEnPesos() {
        return importeEnPesos;
    }

    public void setImporteEnPesos(double importeEnPesos) {
        this.importeEnPesos = importeEnPesos;
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

    public boolean agregarTransaccionFinanciera(String tipoMovimiento, double importe, int sucursalID)
            throws SQLException {
        String insertSql = "INSERT INTO TransaccionesFinancieras (tipoMovimiento, importeEnPesos, sucursalID) VALUES (?, ?, ?)";

        try (PreparedStatement insertStatement = conexion.prepareStatement(insertSql)) {
            insertStatement.setString(1, tipoMovimiento);
            insertStatement.setDouble(2, importe);
            insertStatement.setInt(3, sucursalID);

            int filasInsertadas = insertStatement.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    public List<TransaccionesFinancieras> obtenerTodasTransacciones(int sucursalID) throws SQLException {
        List<TransaccionesFinancieras> transacciones = new ArrayList<>();
        String selectSql = "SELECT fecha, tipoMovimiento, importeEnPesos FROM TransaccionesFinancieras WHERE sucursalID = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, sucursalID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String fecha = resultSet.getString("fecha");
                    String tipoMovimiento = resultSet.getString("tipoMovimiento");
                    double importe = resultSet.getDouble("importeEnPesos");

                    TransaccionesFinancieras transaccion = new TransaccionesFinancieras(tipoMovimiento, importe,
                            sucursalID);
                    transaccion.setFecha(fecha);
                    transacciones.add(transaccion);
                }
            }
        }

        return transacciones;
    }

    public List<TransaccionesFinancieras> obtenerTransaccionesPorFecha(int sucursalID, LocalDate desdeFecha,
            LocalDate hastaFecha) throws SQLException {
        List<TransaccionesFinancieras> transacciones = new ArrayList<>();
        String selectSql = "SELECT fecha, tipoMovimiento, importeEnPesos FROM TransaccionesFinancieras WHERE sucursalID = ? AND fecha BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, sucursalID);
            preparedStatement.setString(2, desdeFecha.toString());
            preparedStatement.setString(3, hastaFecha.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String fecha = resultSet.getString("fecha");
                    String tipoMovimiento = resultSet.getString("tipoMovimiento");
                    double importe = resultSet.getDouble("importeEnPesos");

                    TransaccionesFinancieras transaccion = new TransaccionesFinancieras(tipoMovimiento, importe,
                            sucursalID);
                    transaccion.setFecha(fecha);
                    transacciones.add(transaccion);
                }
            }
        }

        return transacciones;
    }

}
