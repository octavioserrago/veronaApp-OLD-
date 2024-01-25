package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Plano {
    private int planoID;
    private String codigoPlano;
    private String material;
    private String color;
    private int ventasID;

  
    public Plano(int planoID, String codigoPlano, String material, String color, int ventasID) {
        this.planoID = planoID;
        this.codigoPlano = codigoPlano;
        this.material = material;
        this.color = color;
        this.ventasID = ventasID;
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
                    String obtenerUltimoNumeroQuery = "SELECT MAX(CAST(SUBSTRING_INDEX(codigoPlano, '-', -1) AS UNSIGNED)) AS ultimoNumero " +
                            "FROM Planos WHERE codigoPlano LIKE ?";
                    try (PreparedStatement ultimoNumeroStatement = conexion.prepareStatement(obtenerUltimoNumeroQuery)) {
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

    public void cargarPlanoMaterialCliente(String codigoPlano, String material,int ventasID) throws SQLException {
        String sql = "INSERT INTO Planos (codigoPlano, material, ventasID) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, codigoPlano);
            preparedStatement.setString(2, material);
            preparedStatement.setInt(3, ventasID);
            preparedStatement.executeUpdate();
        }
    }

    public void cargarPlanoMaterialEmpresa(String codigoPlano, String material, String color,int ventasID) throws SQLException {
        String sql = "INSERT INTO Planos (codigoPlano, material, color, ventasID) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, codigoPlano);
            preparedStatement.setString(2, material);
            preparedStatement.setString(3, color);
            preparedStatement.setInt(4, ventasID);
            preparedStatement.executeUpdate();
        }
    }
}
