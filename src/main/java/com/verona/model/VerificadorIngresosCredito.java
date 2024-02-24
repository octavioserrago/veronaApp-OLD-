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
    private int ventasID;
    private Date fecha;

    public VerificadorIngresosCredito(double importe, int sucursalID, int ventasID) {
        this.importe = importe;
        this.sucursalID = sucursalID;
        this.ventasID = ventasID;
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

    public int getVentasID() {
        return ventasID;
    }

    public void setVentasID(int ventasID) {
        this.ventasID = ventasID;
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

    public void insertVerificadorIngresosCredito(double importe, int sucursalID, int ventasID) throws SQLException {
        String sql = "INSERT INTO VerificadorIngresosCredito (importe, sucursalID, ventasID) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);

            stmt.setDouble(1, importe);
            stmt.setInt(2, sucursalID);
            stmt.setInt(3, ventasID);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected <= 0) {
                throw new SQLException("Error al insertar en VerificadorIngresosCredito");
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<VerificadorIngresosCredito> obtenerTodosLosRegistros() {
        List<VerificadorIngresosCredito> ingresos = new ArrayList<>();
        String sql = "SELECT verificadorIngresosCreditoID, fecha, importe, ventasID FROM VerificadorIngresosCredito WHERE sucursalID = ?";
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, sucursalID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VerificadorIngresosCredito verificador = new VerificadorIngresosCredito(importe, sucursalID, ventasID);
                verificador.setVerificadorIngresosCreditoID(rs.getInt("verificadorIngresosCreditoID"));
                verificador.setFecha(rs.getDate("fecha"));
                verificador.setImporte(rs.getDouble("importe"));
                verificador.setVentasID(rs.getInt("ventasID"));
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
