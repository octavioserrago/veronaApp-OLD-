package com.verona.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Empleado {
    private int empleadosID;
    private String nombreApellido;
    private String puesto;
    private String telefono;
    private String email;
    private String cbuAlias;
    private Date fechaNacimiento;
    private int monedasID;
    private double sueldoSemanal;
    private int sucursalID;

    public Empleado(String nombreApellido, String puesto, String telefono, String email, String cbuAlias,
            Date fechaNacimiento, int monedasID, double sueldoSemanal, int sucursalID) {
        this.nombreApellido = nombreApellido;
        this.puesto = puesto;
        this.telefono = telefono;
        this.email = email;
        this.cbuAlias = cbuAlias;
        this.fechaNacimiento = fechaNacimiento;
        this.monedasID = monedasID;
        this.sueldoSemanal = sueldoSemanal;
        this.sucursalID = sucursalID;
    }

    public int getEmpleadosID() {
        return empleadosID;
    }

    public void setEmpleadosID(int empleadosID) {
        this.empleadosID = empleadosID;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCbuAlias() {
        return cbuAlias;
    }

    public void setCbuAlias(String cbuAlias) {
        this.cbuAlias = cbuAlias;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getMonedasID() {
        return monedasID;
    }

    public void setMonedasID(int monedasID) {
        this.monedasID = monedasID;
    }

    public double getSueldoSemanal() {
        return sueldoSemanal;
    }

    public void setSueldoSemanal(double sueldoSemanal) {
        this.sueldoSemanal = sueldoSemanal;
    }

    public int getSucursalID() {
        return sucursalID;
    }

    public void setSucursalID(int sucursalID) {
        this.sucursalID = sucursalID;
    }

    static DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

    public static List<Empleado> obtenerListaEmpleados(int sucursalID) {
        List<Empleado> listaEmpleado = new ArrayList<>();
        String sql = "SELECT * FROM Empleados WHERE sucursalID = ?";

        try (Connection conexion = con.conectar();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, sucursalID);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    int empleadoID = resultSet.getInt("empleadosID");
                    String nombreApellido = resultSet.getString("nombreApellido");
                    String puesto = resultSet.getString("puesto");
                    String telefono = resultSet.getString("telefono");
                    String email = resultSet.getString("email");
                    String cbuAlias = resultSet.getString("cbuAlias");
                    Date fechaNacimiento = resultSet.getDate("fechaNacimiento");
                    int monedasID = resultSet.getInt("monedasID");
                    double sueldoSemanal = resultSet.getDouble("sueldoSemanal");

                    Empleado empleado = new Empleado(nombreApellido, puesto, telefono, email, cbuAlias,
                            fechaNacimiento, monedasID, sueldoSemanal, sucursalID);

                    empleado.setEmpleadosID(empleadoID);

                    listaEmpleado.add(empleado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaEmpleado;
    }

    public boolean aumentarSueldo(double nuevoSueldo, int empleadosID) throws SQLException {
        String sqlUpdate = "UPDATE Empleados SET sueldoSemanal = ? WHERE empleadosID = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlUpdate)) {
            preparedStatement.setDouble(1, nuevoSueldo);
            preparedStatement.setInt(2, empleadosID);

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el sueldo: " + e.getMessage(), e);
        }
        return false;
    }

}
