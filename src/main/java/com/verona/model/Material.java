package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Material {
    private int materialID;
    private String tipoMaterial;

    public Material(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    @Override
    public String toString() {
        return tipoMaterial;
    }

    static DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public static List<Material> obtenerListaMateriales(Connection conexion) {
        List<Material> listaMateriales = new ArrayList<>();

        String sql = "SELECT * FROM Materiales";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String tipoMaterial = resultSet.getString("tipoMaterial");
                Material material = new Material(tipoMaterial);
                listaMateriales.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }

        return listaMateriales;
    }

    public static int obtenerMaterialID(Connection conexion, String tipoMaterial) {
        int materialID = 0;

        String sql = "SELECT materialID FROM Materiales WHERE tipoMaterial LIKE ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + tipoMaterial + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    materialID = resultSet.getInt("materialID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }

        return materialID;
    }

    public String obtenerMaterial(int materialID) {
        String material = "";
        String sql = "SELECT tipoMaterial FROM Materiales WHERE materialID = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, materialID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    material = resultSet.getString("tipoMaterial");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }

        return material;
    }
}