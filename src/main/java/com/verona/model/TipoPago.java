package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoPago {

    private int tiposPagosID;
    private String detalle;

    public TipoPago(int tiposPagosID, String detalle) {
        this.tiposPagosID = tiposPagosID;
        this.detalle = detalle;
    }

    public int getTiposPagosID() {
        return tiposPagosID;
    }

    public void setTiposPagosID(int tiposPagosID) {
        this.tiposPagosID = tiposPagosID;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return detalle;
    }

    DatabaseConnection con = new DatabaseConnection();
    Connection conexion = con.conectar();
    PreparedStatement stmt;

    public List<TipoPago> obtenerTiposDePago() {
        List<TipoPago> tiposDePago = new ArrayList<>();

        String consulta = "SELECT * FROM TiposPagos";

        try (PreparedStatement stmt = conexion.prepareStatement(consulta);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int tiposPagosID = rs.getInt("tiposPagosID");
                String detalle = rs.getString("detalle");
                TipoPago tipoPago = new TipoPago(tiposPagosID, detalle);
                tiposDePago.add(tipoPago);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tiposDePago;
    }
}
