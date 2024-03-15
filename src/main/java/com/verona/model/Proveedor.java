package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Proveedor {

    private int proveedorID;
    private String empresa;

    public Proveedor(int proveedorID, String empresa) {
        this.proveedorID = proveedorID;
        this.empresa = empresa;
    }

    public int getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(int proveedorID) {
        this.proveedorID = proveedorID;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return empresa;
    }

    public List<Proveedor> obtenerProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        String consulta = "SELECT proveedorID, empresa FROM Proveedores";

        DatabaseConnection con = new DatabaseConnection();
        Connection conexion = con.conectar();

        try (PreparedStatement stmt = conexion.prepareStatement(consulta);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int proveedorID = rs.getInt("proveedorID");
                String empresa = rs.getString("empresa");
                Proveedor proveedor = new Proveedor(proveedorID, empresa);
                proveedores.add(proveedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return proveedores;
    }
}
