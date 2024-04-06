package com.verona.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection con;

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MarmoleriaVerona", "root", "");
        } catch (Exception e) {
            System.err.println("Error al conectarse: " + e.getMessage());
        }
        return con;
    }

    public boolean isConnected() {
        return (con != null);
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
