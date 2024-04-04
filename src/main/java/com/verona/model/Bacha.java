package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bacha {

    private int bachasID;
    private String nombreMarca;
    private int marcasBachasID;
    private String nombreModelo;
    private String medidas;
    private int cantidad;
    public String tipoBacha;

    public Bacha(int marcasBachasID, String nombreModelo, String medidas, int cantidad) {
        this.marcasBachasID = marcasBachasID;
        this.nombreModelo = nombreModelo;
        this.medidas = medidas;
        this.cantidad = cantidad;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public int getBachasID() {
        return bachasID;
    }

    public void setBachasID(int bachasID) {
        this.bachasID = bachasID;
    }

    public int getMarcasBachasID() {
        return marcasBachasID;
    }

    public void setMarcasBachasID(int marcasBachasID) {
        this.marcasBachasID = marcasBachasID;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoBacha() {
        return tipoBacha;
    }

    public void setTipoBacha(String tipoBacha) {
        this.tipoBacha = tipoBacha;
    }

    @Override
    public String toString() {
        return nombreMarca + " - " + nombreModelo + " - Tipo: " + tipoBacha + "- Medidas:" + medidas + "- Cantidad: "
                + cantidad;
    }

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public void descontarCantidad(int bachasID, int cantidadADescontar, int idPlano) throws SQLException {
        String sqlUpdate = "UPDATE Bachas SET cantidad = ? WHERE bachasID = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sqlUpdate)) {
            pstmt.setInt(1, cantidadADescontar);
            pstmt.setInt(2, bachasID);
            pstmt.executeUpdate();
        }

        String sqlInsert = "INSERT INTO BachaPlanos (bachasID, planosID) VALUES (?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sqlInsert)) {
            pstmt.setInt(1, bachasID);
            pstmt.setInt(2, idPlano);
            pstmt.executeUpdate();
        }
    }

    public List<Bacha> obtenerBachasStock() throws SQLException {
        String sql = "SELECT marcasBachasID, nombreModelo, medidas, cantidad FROM Bachas WHERE marcasBachasID = 2";
        List<Bacha> bachasList = new ArrayList<>();

        try {
            stmt = conexion.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int marcasBachasID = resultSet.getInt("marcasBachasID");
                String nombreModelo = resultSet.getString("nombreModelo");
                String medidas = resultSet.getString("medidas");
                int cantidad = resultSet.getInt("cantidad");

                Bacha bacha = new Bacha(marcasBachasID, nombreModelo, medidas, cantidad);
                bachasList.add(bacha);
            }

        } catch (Exception e) {
            throw new SQLException("Error al buscar bachas: " + e.getMessage(), e);
        }

        return bachasList;
    }

    public List<Bacha> obtenerBachas() throws SQLException {
        String sql = "SELECT b.bachasID, b.marcasBachasID, b.nombreModelo, b.tipoBacha, b.medidas, b.cantidad, m.marca "
                +
                "FROM Bachas b " +
                "JOIN MarcasBachas m ON b.marcasBachasID = m.marcaBachaID";

        List<Bacha> bachasList = new ArrayList<>();

        try {
            stmt = conexion.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int bachasID = resultSet.getInt("bachasID");
                int marcasBachasID = resultSet.getInt("marcasBachasID");
                String nombreModelo = resultSet.getString("nombreModelo");
                String tipoBacha = resultSet.getString("tipoBacha");
                String medidas = resultSet.getString("medidas");
                int cantidad = resultSet.getInt("cantidad");
                String nombreMarca = resultSet.getString("marca");

                Bacha bacha = new Bacha(marcasBachasID, nombreModelo, medidas, cantidad);
                bacha.setBachasID(bachasID);
                bacha.setTipoBacha(tipoBacha);
                bacha.setNombreMarca(nombreMarca);

                bachasList.add(bacha);
            }

        } catch (SQLException e) {
            throw new SQLException("Error al buscar bachas: " + e.getMessage(), e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        return bachasList;
    }

    public int obtenerIDBachaPorNombre(String nombreBacha) throws SQLException {
        int bachaID = -1;
        String sql = "SELECT marcasBachasID FROM Bachas WHERE nombreModelo = ?";

        try {
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nombreBacha);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                bachaID = resultSet.getInt("marcasBachasID");
            }
        } catch (Exception e) {
            throw new SQLException("Error al buscar la Bacha: " + e.getMessage(), e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        return bachaID;
    }

}