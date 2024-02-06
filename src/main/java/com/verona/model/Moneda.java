package com.verona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Moneda {
    private int monedasID;
    private String simbolo;

    public Moneda(String simbolo) {
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

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();

    PreparedStatement stmt;

}