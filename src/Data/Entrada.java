package Data;

public class Entrada {
    private String fecha;
    private String detalle;
    private String metodoPago;
    private String monedasID;
    private Double importe;
    private int ventasID;

    public Entrada(String detalle, String metodoPago, String monedasID, Double importe, int ventasID) {
        this.detalle = detalle;
        this.metodoPago = metodoPago;
        this.monedasID = monedasID;
        this.importe = importe;
        this.ventasID = ventasID;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getMonedasID() {
        return monedasID;
    }

    public void setMonedasID(String monedasID) {
        this.monedasID = monedasID;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public int getVentasID() {
        return ventasID;
    }

    public void setVentasID(int ventasID) {
        this.ventasID = ventasID;
    }

    

}
