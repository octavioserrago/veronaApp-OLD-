package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
            double saldoAnterior = obtenerUltimoSaldo(sucursalID, "CajaEfectivo");

            preparedStatement.setDouble(1, importeTransaccion);
            preparedStatement.setDouble(2, saldoAnterior + importeTransaccion);
            preparedStatement.setInt(3, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                return transaccion.agregarTransaccionFinanciera("Ingreso a caja efectivo", importeTransaccion,
                        sucursalID);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error al insertar en CajaEfectivo", e.getMessage());
            throw new SQLException("Error al insertar en CajaEfectivo: " + e.getMessage(), e);
        }
        return false;
    }

    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public boolean insertarCajaBanco(double importeTransaccion, int sucursalID) {
        String sqlInsert = "INSERT INTO CajaBanco (importeTransaccion, saldoActual, sucursalID) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlInsert)) {
            double saldoAnterior = obtenerUltimoSaldo(sucursalID, "CajaBanco");

            preparedStatement.setDouble(1, importeTransaccion);
            preparedStatement.setDouble(2, saldoAnterior + importeTransaccion);
            preparedStatement.setInt(3, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                return transaccion.agregarTransaccionFinanciera("Ingreso a caja banco", importeTransaccion, sucursalID);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error de SQL", "Error al intentar insertar en la tabla CajaBanco: " + e.getMessage());
        }
        return false;
    }

    public boolean pagoCajaEfectivo(double importeTransaccion, int sucursalID) {
        String sqlInsert = "INSERT INTO CajaEfectivo (importeTransaccion, saldoActual, sucursalID) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlInsert)) {
            double saldoAnterior = obtenerUltimoSaldo(sucursalID, "CajaEfectivo");

            preparedStatement.setDouble(1, importeTransaccion);
            preparedStatement.setDouble(2, saldoAnterior - importeTransaccion);
            preparedStatement.setInt(3, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                return transaccion.agregarTransaccionFinanciera(
                        "Se realizó un pago de $" + importeTransaccion + " desde los fondos en efectivo",
                        importeTransaccion,
                        sucursalID);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error de SQL", "Error al intentar insertar en la tabla CajaEfectivo: " + e.getMessage());
        }
        return false;
    }

    public boolean pagoCajaBanco(double importeTransaccion, int sucursalID) {
        String sqlInsert = "INSERT INTO CajaBanco (importeTransaccion, saldoActual, sucursalID) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlInsert)) {
            double saldoAnterior = obtenerUltimoSaldo(sucursalID, "CajaBanco");

            preparedStatement.setDouble(1, importeTransaccion);
            preparedStatement.setDouble(2, saldoAnterior - importeTransaccion);
            preparedStatement.setInt(3, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                return transaccion.agregarTransaccionFinanciera(
                        "Se realizó un pago de $" + importeTransaccion + " desde los fondos en Banco",
                        importeTransaccion,
                        sucursalID);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error de SQL", "Error al intentar insertar en la tabla CajaBanco: " + e.getMessage());
        }
        return false;
    }

    public double obtenerUltimoSaldo(int sucursalID, String tableName) {
        String sql = "SELECT saldoActual FROM " + tableName + " WHERE sucursalID = ? ORDER BY fecha DESC LIMIT 1";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, sucursalID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("saldoActual");
                }
                return 0;
            }
        } catch (SQLException e) {
            mostrarAlerta("Error de SQL", "Error al intentar obtener el último saldo: " + e.getMessage());
        }
        return 0;
    }

}
