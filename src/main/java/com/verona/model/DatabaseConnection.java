package com.verona.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    Connection con;

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MarmoleriaVerona", "root", "1");
        } catch (Exception e) {
            System.err.println("Error al conectarse: " + e.getMessage());
        }
        return con;
    }

    public boolean isConnected() {
        return (con != null);
    }

}