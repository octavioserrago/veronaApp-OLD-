package com.verona.model;

import java.io.ByteArrayInputStream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PlanosData {
    private final StringProperty codigoPlano;
    private final StringProperty material;
    private final StringProperty color;
    private final StringProperty estado;
    private final StringProperty ventasIDProperty; // Ahora ventasID será un String
    private final StringProperty fechaTerminacionProperty;

    private final ObjectProperty<Button> verImagenButton;

    public PlanosData(String codigoPlano, String material, String color, String estado, byte[] imgBytes) {
        this.codigoPlano = new SimpleStringProperty(codigoPlano);
        this.material = new SimpleStringProperty(material);
        this.color = new SimpleStringProperty(color);
        this.estado = new SimpleStringProperty(estado);

        Button btnVerImagen = new Button("Ver imagen");
        btnVerImagen.setOnAction(event -> mostrarImagen(imgBytes));
        this.verImagenButton = new SimpleObjectProperty<>(btnVerImagen);

        this.ventasIDProperty = new SimpleStringProperty();
        this.fechaTerminacionProperty = new SimpleStringProperty();
    }

    public String getCodigoPlano() {
        return codigoPlano.get();
    }

    public StringProperty codigoPlanoProperty() {
        return codigoPlano;
    }

    public String getMaterial() {
        return material.get();
    }

    public StringProperty materialProperty() {
        return material;
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public String getEstado() {
        return estado.get();
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public Button getVerImagenButton() {
        return verImagenButton.get();
    }

    public ObjectProperty<Button> verImagenButtonProperty() {
        return verImagenButton;
    }

    public String getVentasID() {
        return ventasIDProperty.get();
    }

    public void setVentasID(String ventasID) {
        this.ventasIDProperty.set(ventasID);
    }

    public StringProperty ventasIDProperty() {
        return ventasIDProperty;
    }

    public String getFechaTerminacion() {
        return fechaTerminacionProperty.get();
    }

    public void setFechaTerminacion(String fechaTerminacion) {
        this.fechaTerminacionProperty.set(fechaTerminacion);
    }

    public StringProperty fechaTerminacionProperty() {
        return fechaTerminacionProperty;
    }

    private void mostrarImagen(byte[] imgBytes) {
        if (imgBytes == null) {
            mostrarAlerta("Imagen no disponible", "Aún no hay una foto cargada de este plano en la base de datos.");
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Ver Imagen");

        Image image = new Image(new ByteArrayInputStream(imgBytes));

        ImageView imageView = new ImageView(image);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(imageView);
        scrollPane.setPannable(true);

        double originalAspectRatio = image.getWidth() / image.getHeight();

        scrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            double deltaY = event.getDeltaY();
            double zoomFactor = 1.1;

            if (deltaY < 0) {
                zoomFactor = 1 / zoomFactor;
            }

            double maxZoomOut = Math.max(1, originalAspectRatio * 0.5);

            scrollPane.setHvalue(
                    scrollPane.getHvalue() + event.getDeltaX() / scrollPane.getContent().getBoundsInLocal().getWidth());
            scrollPane.setVvalue(scrollPane.getVvalue()
                    + event.getDeltaY() / scrollPane.getContent().getBoundsInLocal().getHeight());

            imageView.setScaleX(imageView.getScaleX() * zoomFactor);
            imageView.setScaleY(imageView.getScaleY() * zoomFactor);

            if (imageView.getScaleX() > maxZoomOut) {
                imageView.setScaleX(maxZoomOut);
                imageView.setScaleY(maxZoomOut);
            }

            event.consume();
        });

        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);
        stage.setScene(scene);

        stage.show();
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}