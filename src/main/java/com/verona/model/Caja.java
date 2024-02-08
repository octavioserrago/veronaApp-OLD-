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
}
