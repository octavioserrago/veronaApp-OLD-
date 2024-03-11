package com.verona.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class SeniasItem {
    private final IntegerProperty ventasID;
    private final DoubleProperty importeEfectivo;
    private final DoubleProperty importeBanco;
    private final DoubleProperty saldo;
    private final StringProperty accion;
    private final Button accionButton;

    public SeniasItem(int ventasID, double importeEfectivo, double importeBanco, double saldo, String accion) {
        this.ventasID = new SimpleIntegerProperty(ventasID);
        this.importeEfectivo = new SimpleDoubleProperty(importeEfectivo);
        this.importeBanco = new SimpleDoubleProperty(importeBanco);
        this.saldo = new SimpleDoubleProperty(saldo);
        this.accion = new SimpleStringProperty(accion);
        this.accionButton = new Button(accion);
    }

    public int getVentasID() {
        return ventasID.get();
    }

    public double getImporteEfectivo() {
        return importeEfectivo.get();
    }

    public double getImporteBanco() {
        return importeBanco.get();
    }

    public double getSaldo() {
        return saldo.get();
    }

    public String getAccion() {
        return accion.get();
    }

    public Button getAccionButton() {
        return accionButton;
    }
}
