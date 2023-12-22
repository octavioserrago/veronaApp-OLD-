package Data;

public class Empleado {
    
    private int empleadosID;
    private String nombreApellido;
    private String puesto;
    private String telefono;
    private String email;
    private String cbuAlias;
    private String fechaNacimiento;
    private Double sueldo;

    

    public Empleado(String nombreApellido,String puesto, String telefono, String email, String cbuAlias, String fechaNacimiento, Double sueldo) {
        this.nombreApellido = nombreApellido;
        this.puesto = puesto;
        this.telefono = telefono;
        this.email = email;  
        this.cbuAlias = cbuAlias;
        this.fechaNacimiento = fechaNacimiento;
        this.sueldo = sueldo;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return "Empleado: "+ nombreApellido + "Puesto: '" + puesto + '\'' +
            ", Teléfono: '" + telefono + '\'' +
            ", Email: '" + email + '\'' +
            ", CBU: '" + cbuAlias + '\'' +
            ", Fecha de Nacimiento: '" + fechaNacimiento + '\'' +
            ", Sueldo: " + sueldo +
            '}';
    }


}
