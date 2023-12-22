package Data;

public class Proveedor {
    private String proveedoresID;
    private String empresa;
    private String email;
    private String telefono;
    private String cbuAlias;
    private String direccion;
    private String cuit;

    public Proveedor(String empresa, String email, String telefono, String cbuAlias,
        String direccion, String cuit) {
        this.empresa = empresa;
        this.email = email;
        this.telefono = telefono;
        this.cbuAlias = cbuAlias;
        this.direccion = direccion;
        this.cuit = cuit;
    }

    public String getProveedoresID() {
        return proveedoresID;
    }

    public void setProveedoresID(String proveedoresID) {
        this.proveedoresID = proveedoresID;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
            "ID='" + proveedoresID + '\'' +
            ", Empresa='" + empresa + '\'' +
            ", Email='" + email + '\'' +
            ", Teléfono='" + telefono + '\'' +
            ", CBU o Alias='" + cbuAlias + '\'' +
            ", Dirección='" + direccion + '\'' +
            ", CUIT='" + cuit + '\'' +
            '}';
    }

    
}
