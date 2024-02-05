package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Colores {
    private int materialID;
    private String color;
    private int monedaID;
    private double m2Precio;

    public Colores(int materialID, String color, int monedaID, double m2Precio) {
        this.materialID = materialID;
        this.color = color;
        this.monedaID = monedaID;
        this.m2Precio = m2Precio;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMonedaID() {
        return monedaID;
    }

    public void setMonedaID(int monedaID) {
        this.monedaID = monedaID;
    }

    public double getM2Precio() {
        return m2Precio;
    }

    public void setM2Precio(double m2Precio) {
        this.m2Precio = m2Precio;
    }

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public static List<String> obtenerListaColoresPorMaterial(Connection conexion, int materialID) throws SQLException {
        List<String> listaColores = new ArrayList<>();

        String sql = "SELECT color FROM Materiales_Colores_Precios WHERE materialID = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, materialID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String color = resultSet.getString("color");
                    listaColores.add(color);
                }
            }
        }

        return listaColores;
    }

    public static int obtenerColoresID(Connection conexion, int materialID, String color) {
        int materialColorPrecioID = 0;

        String sql = "SELECT materialColorPrecioID FROM Materiales_Colores_Precios WHERE materialID = ? AND color = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, materialID);
            preparedStatement.setString(2, color);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    materialColorPrecioID = resultSet.getInt("materialColorPrecioID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materialColorPrecioID;
    }

    public String obtenerColor(int colorID) {
        String color = "";
        String sql = "SELECT color FROM Materiales_Colores_Precios WHERE materialColorPrecioID = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, colorID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    color = resultSet.getString("color");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return color;
    }
}