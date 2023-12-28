package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bachas {
    
    private int bachasID;
    private int marcasBachasID;
    private String nombreModelo;
    private String medidas;
    private int cantidad;

    public Bachas(int marcasBachasID, String nombreModelo, String medidas, int cantidad) {
        this.marcasBachasID = marcasBachasID;
        this.nombreModelo = nombreModelo;
        this.medidas = medidas;
        this.cantidad = cantidad;
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

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();
	
	PreparedStatement stmt;

    public List<Bachas> obtenerBachasStock() throws SQLException {
        String sql = "SELECT marcasBachasID, nombreModelo, medidas, cantidad FROM Bachas WHERE marcasBachasID = 2";
        List<Bachas> bachasList = new ArrayList<>();
    
        try {
            stmt = conexion.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
    
            while (resultSet.next()) {
                int marcasBachasID = resultSet.getInt("marcasBachasID");
                String nombreModelo = resultSet.getString("nombreModelo");
                String medidas = resultSet.getString("medidas");
                int cantidad = resultSet.getInt("cantidad");
    
                Bachas bacha = new Bachas(marcasBachasID, nombreModelo, medidas, cantidad);
                bachasList.add(bacha);
            }
    
        } catch (Exception e) {
            throw new SQLException("Error al buscar bachas: " + e.getMessage(), e);
        }
    
        return bachasList;
    }
    
    
}

    



