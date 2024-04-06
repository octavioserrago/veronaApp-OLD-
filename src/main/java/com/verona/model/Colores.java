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
    private String tipoMaterial;
    private String monedaSimbolo;

    public Colores(int materialID, String color, int monedaID, double m2Precio) {
        this.materialID = materialID;
        this.color = color;
        this.monedaID = monedaID;
        this.m2Precio = m2Precio;
    }

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public String getMonedaSimbolo() {
        return monedaSimbolo;
    }

    public void setMonedaSimbolo(String monedaSimbolo) {
        this.monedaSimbolo = monedaSimbolo;
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

    private static DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public static List<String> obtenerListaColoresPorMaterial(Connection conexion, int materialID) {
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
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar lista de colores por material: " + e.getMessage(), e);
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
            throw new RuntimeException("Error al buscar ID de colores: " + e.getMessage(), e);
        }

        return materialColorPrecioID;
    }

    public String obtenerColor(int colorID) {
        String color = "";
        String sql = "SELECT color FROM Materiales_Colores_Precios WHERE materialColorPrecioID = ?";
        try (PreparedStatement preparedStatement = con.conectar().prepareStatement(sql)) {
            preparedStatement.setInt(1, colorID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    color = resultSet.getString("color");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener color: " + e.getMessage(), e);
        }

        return color;
    }

    public static List<Colores> obtenerListaMaterialColorM2Precio() {
        List<Colores> listaMaterialColorM2Precio = new ArrayList<>();
        Connection connection = null;

        String sql = "SELECT MC.color, M.tipoMaterial, MC.monedaID, MO.simbolo, MC.m2Precio " +
                "FROM Materiales_Colores_Precios MC " +
                "JOIN Materiales M ON MC.materialID = M.materialID " +
                "JOIN Monedas MO ON MC.monedaID = MO.monedasID";

        try {
            connection = con.conectar();
            PreparedStatement stmt = connection.prepareStatement(sql);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    String color = resultSet.getString("color");
                    String tipoMaterial = resultSet.getString("tipoMaterial");
                    int monedaID = resultSet.getInt("monedaID");
                    String monedaSimbolo = resultSet.getString("simbolo");
                    double m2Precio = resultSet.getDouble("m2Precio");

                    Colores material = new Colores(0, color, monedaID, m2Precio);
                    material.setTipoMaterial(tipoMaterial);
                    material.setMonedaSimbolo(monedaSimbolo);
                    listaMaterialColorM2Precio.add(material);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al obtener lista de materiales, colores y precios por m2: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return listaMaterialColorM2Precio;
    }

}