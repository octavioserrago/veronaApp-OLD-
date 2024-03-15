package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Caja {
    private double importe;
    private int sucursalID;

    public Caja(double importe, int sucursalID) {
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

    TransaccionesFinancieras transaccion = new TransaccionesFinancieras(null, 0, sucursalID);

    public boolean insertarCajaEfectivo(double importeTransaccion, int sucursalID) throws SQLException {
        String sqlInsert = "INSERT INTO CajaEfectivo (importeTransaccion, saldoActual, sucursalID) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlInsert)) {
            double saldoAnterior = obtenerUltimoSaldo(sucursalID, "CajaEfectivo", "cajaEfectivoID");

            preparedStatement.setDouble(1, importeTransaccion);
            preparedStatement.setDouble(2, saldoAnterior + importeTransaccion);
            preparedStatement.setInt(3, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                return transaccion.agregarTransaccionFinanciera("Ingreso a caja efectivo", importeTransaccion,
                        sucursalID);
            }
        }
        return false;
    }

    public boolean insertarCajaBanco(double importeTransaccion, int sucursalID) throws SQLException {
        String sqlInsert = "INSERT INTO CajaBanco (importeTransaccion, saldoActual ,sucursalID) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlInsert)) {
            double saldoAnterior = obtenerUltimoSaldo(sucursalID, "CajaBanco", "cajaBancoID");

            preparedStatement.setDouble(1, importeTransaccion);
            preparedStatement.setDouble(2, saldoAnterior + importeTransaccion);
            preparedStatement.setInt(3, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                return transaccion.agregarTransaccionFinanciera("Ingreso a caja banco", importeTransaccion, sucursalID);
            }
        }
        return false;
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
