package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManoObra {
    private int manoDeObraID;
    private String detalle;
    private double ml;
    private double precioUnidad;

    public ManoObra(String detalle, double ml, double precioUnidad) {
        this.detalle = detalle;
        this.ml = ml;
        this.precioUnidad = precioUnidad;
    }

    public int getManoDeObraID() {
        return manoDeObraID;
    }

    public void setManoDeObraID(int manoDeObraID) {
        this.manoDeObraID = manoDeObraID;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getMl() {
        return ml;
    }

    public void setMl(double ml) {
        this.ml = ml;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    static DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public static List<ManoObra> obtenerListaManoObra() throws SQLException {
        List<ManoObra> listaManoObra = new ArrayList<>();
        String sql = "SELECT detalle, precioML, precioUnidad FROM ManoDeObra";

        try (PreparedStatement stmt = con.conectar().prepareStatement(sql)) {

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    String detalle = resultSet.getString("detalle");
                    double ml = resultSet.getDouble("precioML");
                    double precioUnidad = resultSet.getDouble("precioUnidad");

                    ManoObra manoObra = new ManoObra(detalle, ml, precioUnidad);
                    listaManoObra.add(manoObra);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return listaManoObra;
    }
}