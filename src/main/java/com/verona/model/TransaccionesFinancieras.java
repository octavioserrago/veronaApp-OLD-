package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransaccionesFinancieras {

    private String tipoMovimiento;
    private double importeEnPesos;
    private int sucursalID;

    public TransaccionesFinancieras(String tipoMovimiento, double importeEnPesos, int sucursalID) {
        this.tipoMovimiento = tipoMovimiento;
        this.importeEnPesos = importeEnPesos;
        this.sucursalID = sucursalID;
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

}
