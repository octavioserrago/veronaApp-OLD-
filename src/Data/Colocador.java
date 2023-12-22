package Data;

public class Colocador {

    private int colocadoresID;
    private String nombreApellido;
    private String telefono;
    private String cbuAlias;
    private String fechaNacimiento;
    
    

    public Colocador(String nombreApellido, String telefono, String cbuAlias, String fechaNacimiento) {
        this.nombreApellido = nombreApellido;
        this.telefono = telefono;
        this.cbuAlias = cbuAlias;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getColocadoresID() {
        return colocadoresID;
    }

    public void setColocadoresID(int colocadoresID) {
        this.colocadoresID = colocadoresID;
    }
    
    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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


    @Override
    public String toString() {
            return "Colocador{" +
            "Nombre y Apellido='" + nombreApellido + '\'' +
            ", Teléfono='" + telefono + '\'' +
            ", CBU o Alias='" + cbuAlias + '\'' +
            ", Fecha de Nacimiento='" + fechaNacimiento + '\'' +
            '}';
    }


}
