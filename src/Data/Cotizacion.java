package Data;

public class Cotizacion {
    private int cotizacionID;
    private int monedasID;
    private Double importe;
    private int monedasIDtasaCambio;
    private double tasaCambio;


    
    public Cotizacion(int monedasID, Double importe, int monedasIDtasaCambio, double tasaCambio) {
        this.monedasID = monedasID;
        this.importe = importe;
        this.monedasIDtasaCambio = monedasIDtasaCambio;
        this.tasaCambio = tasaCambio;
    }

    public int getCotizacionID() {
        return cotizacionID;
    }
    public void setCotizacionID(int cotizacionID) {
        this.cotizacionID = cotizacionID;
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
    public int getMonedasIDtasaCambio() {
        return monedasIDtasaCambio;
    }
    public void setMonedasIDtasaCambio(int monedasIDtasaCambio) {
        this.monedasIDtasaCambio = monedasIDtasaCambio;
    }
    public double getTasaCambio() {
        return tasaCambio;
    }
    public void setTasaCambio(double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    public String toString(){
        return "Cotizacion ID: " + cotizacionID + ", Moneda ID: " + monedasID + ", Importe: " + importe +
                ", Moneda ID Tasa de Cambio: " + monedasIDtasaCambio + ", Tasa de Cambio: " + tasaCambio;
    }
    
}
