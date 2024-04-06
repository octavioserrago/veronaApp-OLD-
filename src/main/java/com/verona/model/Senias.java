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

    TransaccionesFinancieras transaccion = new TransaccionesFinancieras(null, importeBanco, sucursalID);

    public boolean insertarSeniaEfectivo(int monedasID, double importeEfectivo, double saldo, int ventasID,
            int sucursalID) {
        String descripcionMovimiento = "Se agrega seña en efectivo de " + importeEfectivo
                + ". Pertenece al ID de venta: "
                + ventasID;
        String sql = "INSERT INTO senias (monedasID, importeEfectivo, saldo, ventasID, sucursalID) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, monedasID);
            preparedStatement.setDouble(2, importeEfectivo);
            preparedStatement.setDouble(3, saldo);
            preparedStatement.setInt(4, ventasID);
            preparedStatement.setInt(5, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();

            return filasAfectadas > 0
                    && transaccion.agregarTransaccionFinanciera(descripcionMovimiento, importeEfectivo, sucursalID);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean insertarSeniaBanco(int monedasID, double importeBanco, double saldo, int ventasID, int sucursalID) {
        String descripcionMovimiento = "Se agrego seña por Banco de" + importeBanco + ". Pertenece al ID de venta: "
                + ventasID;
        String sql = "INSERT INTO senias (monedasID, importeBanco, saldo, ventasID, sucursalID) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, monedasID);
            preparedStatement.setDouble(2, importeBanco);
            preparedStatement.setDouble(3, saldo);
            preparedStatement.setInt(4, ventasID);
            preparedStatement.setInt(5, sucursalID);

            int filasAfectadas = preparedStatement.executeUpdate();

            return filasAfectadas > 0
                    && transaccion.agregarTransaccionFinanciera(descripcionMovimiento, importeBanco, sucursalID);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return senias;
    }

    public boolean isAccionDisponible(int ventasID, Double importeEfectivo, Double importeBanco) throws SQLException {

        double totalVenta = 0.0;
        String sql = "SELECT importe FROM Ventas WHERE ventasID = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, ventasID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalVenta = resultSet.getDouble("importe");
                }
            }
        }
        double totalIngresos = importeEfectivo + importeBanco;

        return totalIngresos >= totalVenta;
    }

    public boolean pasarACajaEfectivoYBanco(int ventasID, int sucursalID, double importeEfectivo, double importeBanco)
            throws SQLException {
        String deleteSql = "DELETE FROM senias WHERE ventasID = ? AND sucursalID = ?";
        String insertCajaEfectivoSql = "INSERT INTO CajaEfectivo (importeTransaccion, saldoActual, sucursalID) VALUES (?, ?, ?)";
        String insertCajaBancoSql = "INSERT INTO CajaBanco (importeTransaccion, saldoActual, sucursalID) VALUES (?, ?, ?)";
        String selectSaldoEfectivoSql = "SELECT saldoActual FROM CajaEfectivo ORDER BY cajaEfectivoID DESC LIMIT 1";
        String selectSaldoBancoSql = "SELECT saldoActual FROM CajaBanco ORDER BY cajaBancoID DESC LIMIT 1";

        try (PreparedStatement deleteStatement = conexion.prepareStatement(deleteSql);
                PreparedStatement insertCajaEfectivoStatement = conexion.prepareStatement(insertCajaEfectivoSql);
                PreparedStatement insertCajaBancoStatement = conexion.prepareStatement(insertCajaBancoSql);
                PreparedStatement selectSaldoEfectivoStatement = conexion.prepareStatement(selectSaldoEfectivoSql);
                PreparedStatement selectSaldoBancoStatement = conexion.prepareStatement(selectSaldoBancoSql)) {
            deleteStatement.setInt(1, ventasID);
            deleteStatement.setInt(2, sucursalID);
            int filasEliminadas = deleteStatement.executeUpdate();

            if (filasEliminadas > 0) {

                double saldoActualEfectivo = 0;
                ResultSet saldoEfectivoResult = selectSaldoEfectivoStatement.executeQuery();
                if (saldoEfectivoResult.next()) {
                    saldoActualEfectivo = saldoEfectivoResult.getDouble("saldoActual");
                }

                double nuevoSaldoEfectivo = saldoActualEfectivo + importeEfectivo;
                insertCajaEfectivoStatement.setDouble(1, importeEfectivo);
                insertCajaEfectivoStatement.setDouble(2, nuevoSaldoEfectivo);
                insertCajaEfectivoStatement.setInt(3, sucursalID);
                int filasInsertadasEfectivo = insertCajaEfectivoStatement.executeUpdate();

                double saldoActualBanco = 0;
                ResultSet saldoBancoResult = selectSaldoBancoStatement.executeQuery();
                if (saldoBancoResult.next()) {
                    saldoActualBanco = saldoBancoResult.getDouble("saldoActual");
                }

                double nuevoSaldoBanco = saldoActualBanco + importeBanco;
                insertCajaBancoStatement.setDouble(1, importeBanco);
                insertCajaBancoStatement.setDouble(2, nuevoSaldoBanco);
                insertCajaBancoStatement.setInt(3, sucursalID);
                int filasInsertadasBanco = insertCajaBancoStatement.executeUpdate();

                return filasInsertadasEfectivo > 0 && filasInsertadasBanco > 0;
            } else {
                return false;
            }
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public double obtenerCajaSenia(String tipoImporte, int sucursalID) throws SQLException {
        String selectSql = "SELECT SUM(" + tipoImporte + ") AS total FROM Senias WHERE sucursalID = ?";

        try (PreparedStatement selectStatement = conexion.prepareStatement(selectSql)) {
            selectStatement.setInt(1, sucursalID);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total");
            }
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0.0;
    }

}