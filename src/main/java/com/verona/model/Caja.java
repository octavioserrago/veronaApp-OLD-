package com.verona.model;

public class Caja {
    private double importe;
    private int sucursalID;

    public Caja(double importe, int sucursalID) {
        this.importe = importe;
        this.sucursalID = sucursalID;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getSucursalID() {
        return sucursalID;
    }

    public void setSucursalID(int sucursalID) {
        this.sucursalID = sucursalID;
    }

    /*
     * Recordar crear una vista para poder dejar en claro cuando hay que mover
     * dinero en efectivo de las señas a la caja en efectivo
     */
}
