package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cotizacion {
    private String fecha;
    private int cotizacionID;
    private int monedasID;
    private Double importe;
    private int monedasIDtasaCambio;
    private double tasaCambio;

    public Cotizacion(String fecha, double tasaCambio) {
        this.fecha = fecha;
        this.tasaCambio = tasaCambio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCotizacionID() {
        return cotizacionID;
    }

    public void setCotizacionID(int cotizacionID) {
        this.cotizacionID = cotizacionID;
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

    public int getMonedasIDtasaCambio() {
        return monedasIDtasaCambio;
    }

    public void setMonedasIDtasaCambio(int monedasIDtasaCambio) {
        this.monedasIDtasaCambio = monedasIDtasaCambio;
    }

    public double getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    public String toString() {
        return "Cotizacion ID: " + cotizacionID + ", Moneda ID: " + monedasID + ", Importe: " + importe +
                ", Moneda ID Tasa de Cambio: " + monedasIDtasaCambio + ", Tasa de Cambio: " + tasaCambio;
    }

    DatabaseConnection con = new DatabaseConnection();
    Connection conexion = con.conectar();
    PreparedStatement stmt;

    public Object[] getUltimaCotizacion() {
        String sql = "SELECT cotizacionesID, tasaCambio FROM Cotizaciones WHERE monedasID = 3 ORDER BY cotizacionesID DESC LIMIT 1";
        int cotizacionesID = 0;
        double tasaCambio = 0;

        try {
            stmt = conexion.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                cotizacionesID = resultSet.getInt("cotizacionesID");
                tasaCambio = resultSet.getDouble("tasaCambio");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Object[] { cotizacionesID, tasaCambio };
    }

    public boolean insertarCotizacionBlue(int monedasID, double importe, int monedasIDtasaCambio, double tasaCambio)
            throws SQLException {
        String sql = "INSERT INTO Cotizaciones (monedasID, importe, monedasIDtasaCambio, tasaCambio) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, monedasID);
            preparedStatement.setDouble(2, importe);
            preparedStatement.setInt(3, monedasIDtasaCambio);
            preparedStatement.setDouble(4, tasaCambio);

            int filasAfectadas = preparedStatement.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    public double getCotizacion(int cotizacionesID) {
        String sql = "SELECT tasaCambio FROM Cotizaciones WHERE cotizacionesID = ?";
        double tasaCambio = 0;

        try {
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, cotizacionesID); // Establecer el valor del parámetro
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                tasaCambio = resultSet.getDouble("tasaCambio");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasaCambio;
    }

}