package com.verona.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VerificadorIngresosCredito {

    private int verificadorIngresosCreditoID;
    private double importe;
    private int sucursalID;
    private Date fecha;

    public VerificadorIngresosCredito(double importe, int sucursalID) {
        this.importe = importe;
        this.sucursalID = sucursalID;
    }

    public int getVerificadorIngresosCreditoID() {
        return verificadorIngresosCreditoID;
    }

    public void setVerificadorIngresosCreditoID(int verificadorIngresosCreditoID) {
        this.verificadorIngresosCreditoID = verificadorIngresosCreditoID;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public boolean insertVerificadorIngresosCredito(double importe, int sucursalID) {
        String sql = "INSERT INTO VerificadorIngresosCredito (importe, sucursalID) VALUES (?, ?)";
        try {
            stmt = conexion.prepareStatement(sql);

            stmt.setDouble(1, importe);
            stmt.setInt(2, sucursalID);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public List<VerificadorIngresosCredito> obtenerTodosLosRegistros() {
        List<VerificadorIngresosCredito> ingresos = new ArrayList<>();
        String sql = "SELECT verificadorIngresosCreditoID, fecha, importe FROM VerificadorIngresosCredito WHERE sucursalID = ?";
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, sucursalID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VerificadorIngresosCredito verificador = new VerificadorIngresosCredito(importe, sucursalID);
                verificador.setVerificadorIngresosCreditoID(rs.getInt("verificadorIngresosCreditoID"));
                verificador.setFecha(rs.getDate("fecha"));
                verificador.setImporte(rs.getDouble("importe"));
                ingresos.add(verificador);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingresos;
    }

    public boolean eliminarVerificadorPorID(int id) {
        String sql = "DELETE FROM VerificadorIngresosCredito WHERE verificadorIngresosCreditoID = ?";
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
