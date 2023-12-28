package Data;

import java.io.File;

public class Venta {
    private int ventasID;
    private String nombreCliente;
    private String descripcion;
    private String material;
    private String color;
    private int bachasID;
    private String fechaEstimadaTerminacion;
    private int colocadoresID;
    private int monedasID;
    private Double importe;
    private File fotoPlano;
    private String estado;
    private int token;
    private String telefono1;
    private String telefono2;
    private String email;

    public Venta(String nombreCliente, String descripcion, String material, String color, int bachasID,
            String fechaEstimadaTerminacion, int colocadoresID, int monedasID, Double importe, File fotoPlano,
            String estado, int token, String telefono1, String telefono2, String email) {
        this.nombreCliente = nombreCliente;
        this.descripcion = descripcion;
        this.material = material;
        this.color = color;
        this.bachasID = bachasID;
        this.fechaEstimadaTerminacion = fechaEstimadaTerminacion;
        this.colocadoresID = colocadoresID;
        this.monedasID = monedasID;
        this.importe = importe;
        this.fotoPlano = fotoPlano;
        this.estado = estado;
        this.token = token;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.email = email;
    }


    public int getVentasID() {
        return ventasID;
    }
    public void setVentasID(int ventasID) {
        this.ventasID = ventasID;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public int getBachasID() {
        return bachasID;
    }
    public void setBachasID(int bachasID) {
        this.bachasID = bachasID;
    }
    public String getFechaEstimadaTerminacion() {
        return fechaEstimadaTerminacion;
    }
    public void setFechaEstimadaTerminacion(String fechaEstimadaTerminacion) {
        this.fechaEstimadaTerminacion = fechaEstimadaTerminacion;
    }
    public int getColocadoresID() {
        return colocadoresID;
    }
    public void setColocadoresID(int colocadoresID) {
        this.colocadoresID = colocadoresID;
    }
    public int getMonedasID() {
        return monedasID;
    }
    public void setMonedasID(int monedasID) {
        this.monedasID = monedasID;
    }
    public Double getImporte() {
        return importe;
    }
    public void setImporte(Double importe) {
        this.importe = importe;
    }
    public File getFotoPlano() {
        return fotoPlano;
    }
    public void setFotoPlano(File fotoPlano) {
        this.fotoPlano = fotoPlano;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public int getToken() {
        return token;
    }
    public void setToken(int token) {
        this.token = token;
    }
    public String getTelefono1() {
        return telefono1;
    }
    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }
    public String getTelefono2() {
        return telefono2;
    }
    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    

}
