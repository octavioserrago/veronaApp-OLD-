package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Plano {
    private int planoID;
    private String codigoPlano;
    private int materialID;
    private int colorID;
    private byte[] imgBlueprint;
    private int ventasID;
    private String estado;

    private static Plano currentPlano;

    public static void setCurrentPlano(int planoID, String codigoPlano, int materialID, int colorID,
            byte[] imgBlueprint, int ventasID, String estado) {
        Plano.currentPlano = new Plano(codigoPlano, materialID, colorID, imgBlueprint, ventasID, estado);
        currentPlano.setPlanoID(planoID);
    }

    public static int getCurrentVentasID() {
        if (currentPlano != null) {
            return currentPlano.getVentasID();
        } else {
            return 0;
        }
    }

    public Plano(String codigoPlano, int materialID, int colorID, byte[] imgBlueprint, int ventasID, String estado) {
        this.codigoPlano = codigoPlano;
        this.materialID = materialID;
        this.colorID = colorID;
        this.imgBlueprint = imgBlueprint;
        this.ventasID = ventasID;
        this.estado = estado;
    }

    public int getPlanoID() {
        return planoID;
    }

    public void setPlanoID(int planoID) {
        this.planoID = planoID;
    }

    public String getCodigoPlano() {
        return codigoPlano;
    }

    public void setCodigoPlano(String codigoPlano) {
        this.codigoPlano = codigoPlano;
    }

    public int getVentasID() {
        return ventasID;
    }

    public void setVentasID(int ventasID) {
        this.ventasID = ventasID;
    }

    public byte[] getImgBlueprint() {
        return imgBlueprint;
    }

    public void setImgBlueprint(byte[] imgBlueprint) {
        this.imgBlueprint = imgBlueprint;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public String generadorCodigoPlano(int ventasID) {
        String codigo = "";
        try {
            String obtenerSucursalIDQuery = "SELECT sucursalID FROM Ventas WHERE ventasID = ?";
            try (PreparedStatement sucursalIDStatement = conexion.prepareStatement(obtenerSucursalIDQuery)) {
                sucursalIDStatement.setInt(1, ventasID);
                ResultSet resultSet = sucursalIDStatement.executeQuery();

                if (resultSet.next()) {
                    int sucursalID = resultSet.getInt("sucursalID");
                    String obtenerUltimoNumeroQuery = "SELECT MAX(CAST(SUBSTRING_INDEX(codigoPlano, '-', -1) AS UNSIGNED)) AS ultimoNumero "
                            +
                            "FROM Planos WHERE codigoPlano LIKE ?";
                    try (PreparedStatement ultimoNumeroStatement = conexion
                            .prepareStatement(obtenerUltimoNumeroQuery)) {
                        ultimoNumeroStatement.setString(1, sucursalID + "-%");
                        ResultSet ultimoNumeroResult = ultimoNumeroStatement.executeQuery();

                        int ultimoNumero = 0;
                        if (ultimoNumeroResult.next()) {
                            ultimoNumero = ultimoNumeroResult.getInt("ultimoNumero");
                        }
                        int nuevoNumero = ultimoNumero + 1;
                        codigo = sucursalID + "-" + nuevoNumero;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigo;
    }

    public int obtenerMaterial(String material) {
        int materialID = 0;

        String sql = "SELECT materialID FROM Materiales WHERE tipoMaterial LIKE ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + material + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    materialID = resultSet.getInt("materialID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materialID;
    }

    public int obtenerColor(int materialID, String color) {
        int materialPrecioColorID = 0;

        String sql = "SELECT colorID FROM Materiales_Colores_Precios WHERE materialID = ? AND color LIKE ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, materialID);
            preparedStatement.setString(2, "%" + color + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    materialPrecioColorID = resultSet.getInt("colorID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materialPrecioColorID;
    }

    public void cargarPlano(String codigoPlano, int materialID, int materialColorPrecioID, byte[] imgBlueprint,
            int ventasID, String estado) throws SQLException {
        String sql = "INSERT INTO Planos (codigoPlano, materialID, materialColorPrecioID, img, ventasID,estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, codigoPlano);
            preparedStatement.setInt(2, materialID);
            preparedStatement.setInt(3, materialColorPrecioID);
            preparedStatement.setBytes(4, imgBlueprint);
            preparedStatement.setInt(5, ventasID);
            preparedStatement.setString(6, estado);
            preparedStatement.executeUpdate();
        }
    }

    public List<Plano> obtenerPlanosParaVenta(int ventasID) throws SQLException {
        List<Plano> listaPlanos = new ArrayList<>();
        String sql = "SELECT * FROM Planos WHERE ventasID = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, ventasID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Plano plano = new Plano(
                            resultSet.getString("codigoPlano"),
                            resultSet.getInt("materialID"),
                            resultSet.getInt("materialColorPrecioID"),
                            resultSet.getBytes("img"),
                            ventasID,
                            resultSet.getString("estado"));

                    listaPlanos.add(plano);
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        System.out.println("Tamaño de la lista de planos: " + listaPlanos.size());
        return listaPlanos;
    }

}