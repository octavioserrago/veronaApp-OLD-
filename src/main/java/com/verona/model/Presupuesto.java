package com.verona.model;

import java.util.Date;

public class Presupuesto {
    private Date fecha;
    private String nombreCliente;
    private String nombreVendedor;
    private String direccionSucursal;
    private double m2;
    private double ml;
    private double valorDolarOficial;
    private double valorMaterial;
    private double valorManoObra;
    private double descuento;
    private double total;

    public Presupuesto(Date fecha, String nombreCliente, String nombreVendedor, String direccionSucursal, double m2,
            double ml, double valorDolarOficial, double valorMaterial, double valorManoObra, double descuento,
            double total) {
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.nombreVendedor = nombreVendedor;
        this.direccionSucursal = direccionSucursal;
        this.m2 = m2;
        this.ml = ml;
        this.valorDolarOficial = valorDolarOficial;
        this.valorMaterial = valorMaterial;
        this.valorManoObra = valorManoObra;
        this.descuento = descuento;
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getDireccionSucursal() {
        return direccionSucursal;
    }

    public void setDireccionSucursal(String direccionSucursal) {
        this.direccionSucursal = direccionSucursal;
    }

    public double getM2() {
        return m2;
    }

    public void setM2(double m2) {
        this.m2 = m2;
    }

    public double getMl() {
        return ml;
    }

    public void setMl(double ml) {
        this.ml = ml;
    }

    public double getValorDolarOficial() {
        return valorDolarOficial;
    }

    public void setValorDolarOficial(double valorDolarOficial) {
        this.valorDolarOficial = valorDolarOficial;
    }

    public double getValorMaterial() {
        return valorMaterial;
    }

    public void setValorMaterial(double valorMaterial) {
        this.valorMaterial = valorMaterial;
    }

    public double getValorManoObra() {
        return valorManoObra;
    }

    public void setValorManoObra(double valorManoObra) {
        this.valorManoObra = valorManoObra;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
