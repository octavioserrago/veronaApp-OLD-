package com.verona.model;

public class Moneda {
    private int monedasID;
    private String simbolo;

    public Moneda(int monedasID, String simbolo) {
        this.monedasID = monedasID;
        this.simbolo = simbolo;
    }

    public int getMonedasID() {
        return monedasID;
    }

    public void setMonedasID(int monedasID) {
        this.monedasID = monedasID;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String toString() {
        return "Moneda ID: " + monedasID + ", Símbolo: " + simbolo;
    }

}