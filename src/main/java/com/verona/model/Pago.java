package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Pago {
    private int tiposPagosID;
    private int monedasID;
    private double importe;
    private double importeEnPesos;
    private int proveedorID;
    private int sucursalID;

    public Pago(int tiposPagosID, int monedasID, double importe, double importeEnPesos, int proveedorID,
            int sucursalID) {
        this.tiposPagosID = tiposPagosID;
        this.monedasID = monedasID;
        this.importe = importe;
        this.importeEnPesos = importeEnPesos;
        this.proveedorID = proveedorID;
        this.sucursalID = sucursalID;
    }

    public int getTiposPagosID() {
        return tiposPagosID;
    }

    public void setTiposPagosID(int tiposPagosID) {
        this.tiposPagosID = tiposPagosID;
    }

    public int getMonedasID() {
        return monedasID;
    }

    public void setMonedasID(int monedasID) {
        this.monedasID = monedasID;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getImporteEnPesos() {
        return importeEnPesos;
    }

    public void setImporteEnPesos(double importeEnPesos) {
        this.importeEnPesos = importeEnPesos;
    }

    public int getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(int proveedorID) {
        this.proveedorID = proveedorID;
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

    public double obtenerSumaImporteEnPesosPorSucursalYMesVigente(int sucursalID) {
        double sumaImporteEnPesos = 0.0;

        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();
        int anioActual = fechaActual.getYear();

        String consulta = "SELECT SUM(importeEnPesos) AS total FROM Pagos " +
                "WHERE sucursalID = ? AND MONTH(fecha) = ? AND YEAR(fecha) = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setInt(1, sucursalID);
            stmt.setInt(2, mesActual);
            stmt.setInt(3, anioActual);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    sumaImporteEnPesos = rs.getDouble("total");
                    System.out.println("Suma de importeEnPesos para la sucursal " + sucursalID +
                            " en el mes " + mesActual + " y año " + anioActual + ": " + sumaImporteEnPesos);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return sumaImporteEnPesos;
    }

    public double calcularTotalSalidasEnPesosPorSucursalYDia(int sucursalID) throws SQLException {
        double totalSalidasEnPesos = 0;
        String sql = "SELECT SUM(importeEnPesos) AS total FROM Pagos WHERE sucursalID = ? AND DATE(fecha) = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, sucursalID);
            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalSalidasEnPesos = resultSet.getDouble("total");
                }
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return totalSalidasEnPesos;
    }

    public void insertarPagoProveedor(int sucursalID) throws SQLException {
        String sql = "INSERT INTO Pagos (tiposPagosID, monedasID, importe, importeEnPesos, proveedorID, sucursalID) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, tiposPagosID);
            preparedStatement.setInt(2, monedasID);
            preparedStatement.setDouble(3, importe);
            preparedStatement.setDouble(4, importeEnPesos);
            preparedStatement.setInt(5, proveedorID);
            preparedStatement.setInt(6, sucursalID);

            preparedStatement.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void insertarPagoNoProveedor(int sucursalID) throws SQLException {
        String sql = "INSERT INTO Pagos (tiposPagosID, monedasID, importe, importeEnPesos, sucursalID) VALUES (?,?,?,?,?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, tiposPagosID);
            preparedStatement.setInt(2, monedasID);
            preparedStatement.setDouble(3, importe);
            preparedStatement.setDouble(4, importeEnPesos);
            preparedStatement.setInt(5, sucursalID);

            preparedStatement.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public double calcularTotalPagosEnPesosPorSucursalYMes(int sucursalID, int month) throws SQLException {
        double totalPagosEnPesos = 0;
        String sql = "SELECT SUM(importeEnPesos) AS total FROM Pagos WHERE sucursalID = ? AND MONTH(fecha) = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, sucursalID);
            preparedStatement.setInt(2, month);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalPagosEnPesos = resultSet.getDouble("total");
                }
            }
        }
        return totalPagosEnPesos;
    }

}
