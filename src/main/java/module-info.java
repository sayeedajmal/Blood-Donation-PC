module com.strong.blooddashboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.jfoenix;

    requires com.google.gson;

    opens com.strong.blooddashboard to javafx.fxml, javafx.graphics, javafx;

    exports com.strong.blooddashboard.model;

    opens com.strong.blooddashboard.Controller to javafx.fxml;

}