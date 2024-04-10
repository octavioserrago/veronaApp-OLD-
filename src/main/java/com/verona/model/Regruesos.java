package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Regruesos {
    private int engrosesID;
    private String detalle;
    private double precioML;

    public Regruesos(String detalle, double precioML) {
        this.detalle = detalle;
        this.precioML = precioML;
    }

    public int getEngrosesID() {
        return engrosesID;
    }

    public void setEngrosesID(int engrosesID) {
        this.engrosesID = engrosesID;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getPrecioML() {
        return precioML;
    }

    public void setPrecioML(double precioML) {
        this.precioML = precioML;
    }

    static DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public static List<Regruesos> obtenerListaRegruesos() throws SQLException {
        List<Regruesos> listaRegruesos = new ArrayList<>();
        String sql = "SELECT detalle, precioML FROM Engroses";

        try (PreparedStatement stmt = con.conectar().prepareStatement(sql)) {

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    String detalle = resultSet.getString("detalle");
                    double precioML = resultSet.getDouble("precioML");

                    Regruesos regrueso = new Regruesos(detalle, precioML);
                    listaRegruesos.add(regrueso);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return listaRegruesos;
    }

}
