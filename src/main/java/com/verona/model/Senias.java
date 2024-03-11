package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;

public class Senias {
    private int monedasID;
    private double importeEfectivo;
    private double importeBanco;
    private double saldo;
    private int ventasID;
    private int sucursalID;
    private Button accionButton;

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

    public Button getAccionButton() {
        return accionButton;
    }

    public void setAccionButton(Button accionButton) {
        this.accionButton = accionButton;
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

    public List<Senias> obtenerSeniasPorSucursal(int sucursalID) throws SQLException {
        List<Senias> senias = new ArrayList<>();

        String sql = "SELECT senias.ventasID, " +
                "COALESCE(SUM(senias.importeEfectivo), 0) AS sumaEfectivo, " +
                "COALESCE(SUM(senias.importeBanco), 0) AS sumaBanco, " +
                "(SELECT importe FROM Ventas WHERE ventasID = senias.ventasID) " +
                "- COALESCE(SUM(senias.importeEfectivo), 0) - COALESCE(SUM(senias.importeBanco), 0) AS saldoMaximo " +
                "FROM senias " +
                "WHERE senias.sucursalID = ? " +
                "GROUP BY senias.ventasID";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, sucursalID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ventasID = resultSet.getInt("ventasID");
                    double sumaImporteEfectivo = resultSet.getDouble("sumaEfectivo");
                    double sumaImporteBanco = resultSet.getDouble("sumaBanco");
                    double saldoMaximo = resultSet.getDouble("saldoMaximo");

                    Senias senia = new Senias(-1, sumaImporteEfectivo, sumaImporteBanco, saldoMaximo, ventasID,
                            sucursalID);
                    senias.add(senia);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return senias;
    }

    public boolean isAccionDisponible(int ventasID, Double importeEfectivo, Double importeBanco) throws SQLException {

        double totalVenta = 0.0;
        String sql = "SELECT totalVenta FROM ventas WHERE ventasID = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, ventasID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalVenta = resultSet.getDouble("totalVenta");
                }
            }
        }
        double totalIngresos = importeEfectivo + importeBanco;

        return totalIngresos >= totalVenta;
    }

}