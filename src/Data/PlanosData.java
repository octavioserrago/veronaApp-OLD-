package Data;

import javafx.beans.property.SimpleStringProperty;

public class PlanosData {
    private final SimpleStringProperty codigoPlano;
    private final SimpleStringProperty material;
    private final SimpleStringProperty color;

    public PlanosData(String codigoPlano, String material, String color) {
        this.codigoPlano = new SimpleStringProperty(codigoPlano);
        this.material = new SimpleStringProperty(material);
        this.color = new SimpleStringProperty(color);
    }

    public String getCodigoPlano() {
        return codigoPlano.get();
    }

    public SimpleStringProperty codigoPlanoProperty() {
        return codigoPlano;
    }

    public String getMaterial() {
        return material.get();
    }

    public SimpleStringProperty materialProperty() {
        return material;
    }

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }
}
