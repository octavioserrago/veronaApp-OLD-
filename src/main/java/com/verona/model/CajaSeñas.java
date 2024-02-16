package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CajaSeñas {
    private double importe;
    private int sucursalID;

    public CajaSeñas(double importe, int sucursalID) {
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

    public boolean insertarCajaSeñaEfectivo(double importeTransaccion, int sucursalID) throws SQLException {
        String sqlInsert = "INSERT INTO CajaSeñasEfectivo (importeTransaccion, saldoActual, sucursalID) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlInsert)) {
            double saldoAnterior = obtenerUltimoSaldo(sucursalID, "CajaSeñasEfectivo", "cajaSeñasEfectivoID");

            preparedStatement.setDouble(1, importeTransaccion);
            preparedStatement.setDouble(2, saldoAnterior + importeTransaccion);
            preparedStatement.setInt(3, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public boolean insertarCajaSeñaBanco(double importeTransaccion, int sucursalID) throws SQLException {
        String sqlInsert = "INSERT INTO CajaSeñasBanco (importeTransaccion, saldoActual ,sucursalID) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlInsert)) {
            double saldoAnterior = obtenerUltimoSaldo(sucursalID, "CajaSeñasBanco", "cajaSeñasBancoID");

            preparedStatement.setDouble(1, importeTransaccion);
            preparedStatement.setDouble(2, saldoAnterior + importeTransaccion);
            preparedStatement.setInt(3, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public double obtenerUltimoSaldo(int sucursalID, String tableName, String idColumnName) throws SQLException {
        String sql = "SELECT saldoActual FROM " + tableName + " WHERE sucursalID = ? ORDER BY " + idColumnName
                + " DESC LIMIT 1";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, sucursalID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("saldoActual");
                }
                return 0;
            }
        }
    }

}
