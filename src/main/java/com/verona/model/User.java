package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    private String userName;
    private String password;
    private String nombre;
    private String apellido;
    private int roleID;
    private int sucursalID;

    public User(String userName, String password, String nombre, String apellido, int sucursalID) {
        this.userName = userName;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sucursalID = sucursalID;
    }

    private static User currentUser;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public int getSucursalID() {
        return sucursalID;
    }

    public void setSucursalID(int sucursalID) {
        this.sucursalID = sucursalID;
    }

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }

    public boolean login(String enteredUserName, String enteredPassword) {
        String sql = "SELECT * FROM users WHERE userName = ? AND password = ?";

        try {
            stmt = conexion.prepareStatement(sql);

            stmt.setString(1, enteredUserName);
            stmt.setString(2, enteredPassword);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                int roleID = resultSet.getInt("roleID");
                int sucursalID = resultSet.getInt("sucursalID");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");

                this.setUserName(enteredUserName);
                this.setRoleID(roleID);
                this.setSucursalID(sucursalID);
                this.setNombre(nombre);
                this.setApellido(apellido);
                setCurrentUser(this);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
            return false;
        }
    }
}