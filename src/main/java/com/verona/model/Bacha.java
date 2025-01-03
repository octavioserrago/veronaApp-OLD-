package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;

public class Bacha {

    private int bachasID;
    private String nombreMarca;
    private int marcasBachasID;
    private String nombreModelo;
    private String medidas;
    private int cantidad;
    public String tipoBacha;
    public double precio;

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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
        PreparedStatement pstmtUpdate = null;
        PreparedStatement pstmtInsert = null;
        try {
            pstmtUpdate = conexion.prepareStatement(sqlUpdate);
            pstmtUpdate.setInt(1, cantidadADescontar);
            pstmtUpdate.setInt(2, bachasID);
            pstmtUpdate.executeUpdate();

            String sqlInsert = "INSERT INTO BachaPlanos (bachasID, planosID) VALUES (?, ?)";
            pstmtInsert = conexion.prepareStatement(sqlInsert);
            pstmtInsert.setInt(1, bachasID);
            pstmtInsert.setInt(2, idPlano);
            pstmtInsert.executeUpdate();
        } catch (SQLException e) {
            mostrarAlerta("Error al descontar cantidad", e.getMessage());
            throw new SQLException("Error al descontar cantidad: " + e.getMessage(), e);
        } finally {
            if (pstmtUpdate != null) {
                try {
                    pstmtUpdate.close();
                } catch (SQLException e) {
                    mostrarAlerta("Error al cerrar la conexión", e.getMessage());
                }
            }
            if (pstmtInsert != null) {
                try {
                    pstmtInsert.close();
                } catch (SQLException e) {
                    mostrarAlerta("Error al cerrar la conexión", e.getMessage());
                }
            }
        }
    }

    public void agregarBachas(int bachasID, int cantidadAAgregar) throws SQLException {
        int cantidadActual = obtenerCantidadBachas(bachasID);
        int nuevaCantidad = cantidadActual + cantidadAAgregar;
        String sqlUpdate = "UPDATE Bachas SET cantidad = ? WHERE bachasID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(sqlUpdate);
            pstmt.setInt(1, nuevaCantidad);
            pstmt.setInt(2, bachasID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            mostrarAlerta("Error al agregar bachas", e.getMessage());
            throw new SQLException("Error al agregar bachas: " + e.getMessage(), e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    mostrarAlerta("Error al cerrar la conexión", e.getMessage());
                }
            }
        }
    }

    private int obtenerCantidadBachas(int bachasID) throws SQLException {
        String sql = "SELECT cantidad FROM Bachas WHERE bachasID = ?";
        int cantidad = 0;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, bachasID);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                cantidad = resultSet.getInt("cantidad");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener la cantidad de bachas: " + e.getMessage(), e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    mostrarAlerta("Error al cerrar el ResultSet", e.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    mostrarAlerta("Error al cerrar el PreparedStatement", e.getMessage());
                }
            }
        }
        return cantidad;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public List<Bacha> obtenerBachasStock() throws SQLException {
        String sql = "SELECT marcasBachasID, tipoBacha, nombreModelo, medidas, cantidad FROM Bachas WHERE marcasBachasID = 1 AND cantidad > 0";
        List<Bacha> bachasList = new ArrayList<>();

        try {
            stmt = conexion.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int marcasBachasID = resultSet.getInt("marcasBachasID");
                String tipoBacha = resultSet.getString("tipoBacha");
                String nombreModelo = resultSet.getString("nombreModelo");
                String medidas = resultSet.getString("medidas");
                int cantidad = resultSet.getInt("cantidad");

                Bacha bacha = new Bacha(marcasBachasID, nombreModelo, medidas, cantidad);
                bacha.setTipoBacha(tipoBacha);
                bachasList.add(bacha);
            }
        } catch (Exception e) {
            throw new SQLException("Error al buscar bachas: " + e.getMessage(), e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return bachasList;
    }

    public List<Bacha> obtenerBachasSinStock() throws SQLException {
        String sql = "SELECT marcasBachasID, tipoBacha, nombreModelo, medidas, cantidad FROM Bachas WHERE marcasBachasID = 1 AND cantidad < 1";
        List<Bacha> bachasList = new ArrayList<>();

        try {
            stmt = conexion.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int marcasBachasID = resultSet.getInt("marcasBachasID");
                String tipoBacha = resultSet.getString("tipoBacha");
                String nombreModelo = resultSet.getString("nombreModelo");
                String medidas = resultSet.getString("medidas");
                int cantidad = resultSet.getInt("cantidad");

                Bacha bacha = new Bacha(marcasBachasID, nombreModelo, medidas, cantidad);
                bacha.setTipoBacha(tipoBacha);
                bachasList.add(bacha);
            }
        } catch (Exception e) {
            throw new SQLException("Error al buscar bachas: " + e.getMessage(), e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
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

    public List<String> obtenerModelos() throws SQLException {
        List<String> modelos = new ArrayList<>();
        String sql = "SELECT nombreModelo, medidas FROM Bachas";

        try {
            stmt = conexion.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String nombreModelo = resultSet.getString("nombreModelo");
                String medidas = resultSet.getString("medidas");

                String modeloConMedidas = nombreModelo + " - " + medidas;
                modelos.add(modeloConMedidas);
            }
        } catch (Exception e) {
            throw new SQLException("Error al buscar los modelos de bachas: " + e.getMessage(), e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        return modelos;
    }

    public List<Bacha> obtenerModelosConPrecio() throws SQLException {
        List<Bacha> bachas = new ArrayList<>();
        String sql = "SELECT tipoBacha, nombreModelo, medidas, cantidad, precio FROM Bachas";

        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            stmt = conexion.prepareStatement(sql);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String tipoBacha = resultSet.getString("tipoBacha");
                String nombreModelo = resultSet.getString("nombreModelo");
                String medidas = resultSet.getString("medidas");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");

                Bacha bacha = new Bacha(0, nombreModelo, medidas, cantidad);
                bacha.setTipoBacha(tipoBacha);
                bacha.setPrecio(precio);
                bachas.add(bacha);
            }
        } catch (Exception e) {
            throw new SQLException("Error al buscar los modelos de bachas: " + e.getMessage(), e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return bachas;
    }
}