package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MovimientosCajaSeñas {

    private double importe;
    private int sucursalID;

    public MovimientosCajaSeñas(double importe, int sucursalID) {
        this.importe = importe;
        this.sucursalID = sucursalID;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
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

    public boolean cargarMovimientoCajaSeñasEfectivo(double importe, int sucursalID) throws SQLException {
        String sqlInsert = "INSERT INTO MovimientosCajaSeñasEfectivo (importe, sucursalID) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlInsert)) {

            preparedStatement.setDouble(1, importe);
            preparedStatement.setInt(2, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public boolean cargarMovimientoCajaSeñasBanco(double importe, int sucursalID) throws SQLException {
        String sqlInsert = "INSERT INTO MovimientosCajaSeñasBanco (importe, sucursalID) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlInsert)) {

            preparedStatement.setDouble(1, importe);
            preparedStatement.setInt(2, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        }
    }
}
