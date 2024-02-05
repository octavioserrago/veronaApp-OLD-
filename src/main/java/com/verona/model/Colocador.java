package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Colocador {

    private int colocadoresID;
    private String nombreApellido;
    private String telefono;
    private String cbuAlias;
    private String fechaNacimiento;

    public Colocador(String nombreApellido, String telefono, String cbuAlias, String fechaNacimiento) {
        this.nombreApellido = nombreApellido;
        this.telefono = telefono;
        this.cbuAlias = cbuAlias;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getColocadoresID() {
        return colocadoresID;
    }

    public void setColocadoresID(int colocadoresID) {
        this.colocadoresID = colocadoresID;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCbuAlias() {
        return cbuAlias;
    }

    public void setCbuAlias(String cbuAlias) {
        this.cbuAlias = cbuAlias;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return nombreApellido;
    }

    DatabaseConnection con = new DatabaseConnection();
    Connection conexion = con.conectar();
    PreparedStatement stmt;

    public String obtenerNombresPorID(int colocadorid) throws SQLException {
        String sql = "SELECT nombreApellido FROM Colocadores WHERE colocadoresID = ?";
        String nombreApellido = null;

        try {
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, colocadorid);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                nombreApellido = resultSet.getString("nombreApellido");
            }

        } catch (Exception e) {
            throw new SQLException("Error al buscar colocadores: " + e.getMessage(), e);
        }

        return nombreApellido;
    }

    public List<String> obtenerNombresColocadores() throws SQLException {
        String sql = "SELECT nombreApellido FROM Colocadores";
        List<String> nombresColocadores = new ArrayList<>();

        try {
            stmt = conexion.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String nombreApellido = resultSet.getString("nombreApellido");
                nombresColocadores.add(nombreApellido);
            }

        } catch (Exception e) {
            throw new SQLException("Error al buscar colocadores: " + e.getMessage(), e);
        }

        return nombresColocadores;
    }

    public int obtenerIDPorNombre(String nombreColocador) throws SQLException {
        int colocadorID = -1;
        String sql = "SELECT colocadoresID FROM Colocadores WHERE nombreApellido = ?";

        try {
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nombreColocador);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                colocadorID = resultSet.getInt("colocadoresID");
            }
        } catch (Exception e) {
            throw new SQLException("Error al buscar colocadores: " + e.getMessage(), e);
        }

        return colocadorID;
    }

}