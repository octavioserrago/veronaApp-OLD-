package com.verona.model;

public class Validador {

    private Venta venta;

    public Validador(Venta venta) {
        this.venta = venta;
    }

    public String validarVenta() {
        StringBuilder errores = new StringBuilder();

        if (venta.getNombreCliente() == null || venta.getNombreCliente().isEmpty()) {
            errores.append("El nombre del cliente no puede estar vacío.\n");
        }
        if (venta.getDescripcion() == null || venta.getDescripcion().isEmpty()) {
            errores.append("La descripcion no puede estar vacia.\n");
        }
        if (venta.getMaterial() == null || venta.getMaterial().isEmpty()) {
            errores.append("El material no puede estar vacio.\n");
        }
        if (venta.getColor() == null || venta.getColor().isEmpty()) {
            errores.append("El Color no puede estar vacio.\n");
        }
        if (venta.getFechaEstimadaTerminacion() == null || venta.getFechaEstimadaTerminacion().isEmpty()) {
            errores.append("La fecha de Terminacion no puede estar Vacia.\n");
        }
        if (venta.getImporte() == null) {
            errores.append("El importe no puede estar Vacia.\n");
        }
        if (venta.getTelefono1() == null || venta.getTelefono1().isEmpty()) {
            errores.append("El telefono no puede estar Vacio.\n");
        }

        return errores.toString();
    }
}