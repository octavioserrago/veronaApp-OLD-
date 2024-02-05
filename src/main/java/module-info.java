module com.verona {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.base;
    requires transitive java.sql;
    requires itextpdf;

    opens com.verona to javafx.fxml;
    opens com.verona.controller to javafx.fxml;
    opens com.verona.controller.seller to javafx.fxml;
    opens com.verona.controller.manager to javafx.fxml;
    opens com.verona.controller.common to javafx.fxml;

    exports com.verona;
    exports com.verona.model;
    exports com.verona.controller;
    exports com.verona.controller.seller to javafx.fxml;
    exports com.verona.controller.manager to javafx.fxml;
}
