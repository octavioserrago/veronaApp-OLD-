package com.verona.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Plano {
    private int planoID;
    private String codigoPlano;
    private String material;
    private String color;
    private byte[] imgBlueprint;
    private int ventasID;
    private String estado;
    private String fechaTermiancion;

    private static Plano currentPlano;

    public static void setCurrentPlano(int planoID, String codigoPlano, String material, String color,
            byte[] imgBlueprint, int ventasID, String estado, String fechaTerminacion) {
        Plano.currentPlano = new Plano(codigoPlano, material, color, imgBlueprint, ventasID, estado, fechaTerminacion);
        currentPlano.setPlanoID(planoID);
    }

    public static int getCurrentVentasID() {
        if (currentPlano != null) {
            return currentPlano.getVentasID();
        } else {
            return 0;
        }
    }

    public Plano(String codigoPlano, String material, String color, byte[] imgBlueprint, int ventasID, String estado,
            String fechaTermiancion) {
        this.codigoPlano = codigoPlano;
        this.material = material;
        this.color = color;
        this.imgBlueprint = imgBlueprint;
        this.ventasID = ventasID;
        this.estado = estado;
        this.fechaTermiancion = fechaTermiancion;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaTermiancion() {
        return fechaTermiancion;
    }

    public void setFechaTermiancion(String fechaTermiancion) {
        this.fechaTermiancion = fechaTermiancion;
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
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return codigo;
    }

    public int buscarPlano(String codigoPlano) throws SQLException {
        int planoID = -1;
        String sql = "SELECT planosID FROM Planos WHERE codigoPlano = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, codigoPlano);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    planoID = resultSet.getInt("planosID");
                }
            }
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return planoID;
    }

    public boolean modificarEstadoPlano(String nuevoEstado, int planosID) throws SQLException {
        String sql = "UPDATE Planos SET estado = ? WHERE planosID = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, nuevoEstado);
            preparedStatement.setInt(2, planosID);
            preparedStatement.executeUpdate();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public boolean actualizarMaterialColor(String material, String color, int planosID) throws SQLException {
        String sql = "UPDATE Planos SET material = ?, color = ? WHERE planosID = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, material);
            preparedStatement.setString(2, color);
            preparedStatement.setInt(3, planosID);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Plano actualizado");
            alert.setHeaderText(null);
            alert.setContentText("Plano actualizado correctamente.");
            alert.showAndWait();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public boolean cambiarFechaTerminacion(Date fechaTerminacion, int planosID) throws SQLException {
        String sql = "UPDATE Planos SET fechaTerminacion = ? WHERE planosID = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setDate(1, fechaTerminacion);
            preparedStatement.setInt(2, planosID);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Plano actualizado");
            alert.setHeaderText(null);
            alert.setContentText("Plano actualizado correctamente.");
            alert.showAndWait();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public boolean cambiarImagenPlano(byte[] img, int planosID) throws SQLException {
        String sql = "UPDATE Planos SET img = ? WHERE planosID = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setBytes(1, img);
            preparedStatement.setInt(2, planosID);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Plano actualizado");
            alert.setHeaderText(null);
            alert.setContentText("Plano actualizado correctamente.");
            alert.showAndWait();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public void cargarPlano(String codigoPlano, String material, String color, byte[] imgBlueprint,
            int ventasID, String estado, String fechaTerminacion) throws SQLException {
        String sql = "INSERT INTO Planos (codigoPlano, material, color, img, ventasID,estado, fechaTerminacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, codigoPlano);
            preparedStatement.setString(2, material);
            preparedStatement.setString(3, color);
            preparedStatement.setBytes(4, imgBlueprint);
            preparedStatement.setInt(5, ventasID);
            preparedStatement.setString(6, estado);
            preparedStatement.setString(7, fechaTerminacion);
            preparedStatement.executeUpdate();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
                            resultSet.getString("material"),
                            resultSet.getString("color"),
                            resultSet.getBytes("img"),
                            ventasID,
                            resultSet.getString("estado"),
                            resultSet.getString("fechaTerminacion"));

                    listaPlanos.add(plano);
                }
            }
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return listaPlanos;
    }

    public List<Plano> obtenerPlanosEnProduccion() throws SQLException {
        List<Plano> listaPlanos = new ArrayList<>();
        String sql = "SELECT * FROM Planos WHERE estado = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, "En Produccion");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Plano plano = new Plano(
                            resultSet.getString("codigoPlano"),
                            resultSet.getString("material"),
                            resultSet.getString("color"),
                            resultSet.getBytes("img"),
                            resultSet.getInt("ventasID"),
                            resultSet.getString("estado"),
                            resultSet.getString("fechaTerminacion"));

                    listaPlanos.add(plano);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener planos en producción: " + e.getMessage());
            throw e;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaPlanos;
    }

}
