package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoPago {

    private String detalle;

    public TipoPago(String detalle) {
        this.detalle = detalle;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public List<TipoPago> obtenerTiposDePago() {
        List<TipoPago> tiposDePago = new ArrayList<>();

        String consulta = "SELECT detalle FROM TiposPagos";

        try (PreparedStatement stmt = conexion.prepareStatement(consulta);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String detalle = rs.getString("detalle");
                TipoPago tipoPago = new TipoPago(detalle);
                tiposDePago.add(tipoPago);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tiposDePago;
    }

}