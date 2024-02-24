package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Senias {
    private int monedasID;
    private double importeEfectivo;
    private double importeBanco;
    private double saldo;
    private int ventasID;
    private int sucursalID;

    public Senias(int monedasID, double importeEfectivo, double importeBanco, double saldo, int ventasID,
            int sucursalID) {
        this.monedasID = monedasID;
        this.importeEfectivo = importeEfectivo;
        this.importeBanco = importeBanco;
        this.saldo = saldo;
        this.ventasID = ventasID;
        this.sucursalID = sucursalID;
    }

    public int getMonedasID() {
        return monedasID;
    }

    public void setMonedasID(int monedasID) {
        this.monedasID = monedasID;
    }

    public double getImporteEfectivo() {
        return importeEfectivo;
    }

    public void setImporteEfectivo(double importeEfectivo) {
        this.importeEfectivo = importeEfectivo;
    }

    public double getImporteBanco() {
        return importeBanco;
    }

    public void setImporteBanco(double importeBanco) {
        this.importeBanco = importeBanco;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getVentasID() {
        return ventasID;
    }

    public void setVentasID(int ventasID) {
        this.ventasID = ventasID;
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

    public boolean insertarSeniaEfectivo(int monedasID, double importeEfectivo, double saldo,
            int ventasID,
            int sucursalID) throws SQLException {
        String sql = "INSERT INTO Senias (monedasID, importeEfectivo, saldo, ventasID, sucursalID) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, monedasID);
            preparedStatement.setDouble(2, importeEfectivo);
            preparedStatement.setDouble(3, saldo);
            preparedStatement.setInt(4, ventasID);
            preparedStatement.setInt(5, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    public boolean insertarSeniaBanco(int monedasID, double importeBanco, double saldo,
            int ventasID,
            int sucursalID) throws SQLException {
        String sql = "INSERT INTO Senias (monedasID, importeBanco, saldo, ventasID, sucursalID) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, monedasID);
            preparedStatement.setDouble(2, importeBanco);
            preparedStatement.setDouble(3, saldo);
            preparedStatement.setInt(4, ventasID);
            preparedStatement.setInt(5, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();

            return filasAfectadas > 0;
        }
    }
}
