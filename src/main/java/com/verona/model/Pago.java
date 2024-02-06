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
        }
        return sumaImporteEnPesos;
    }

}
