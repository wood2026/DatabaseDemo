@SuppressWarnings("module")
module com.wood2026.databasedemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires com.google.gson;

    opens com.wood2026.databasedemo to javafx.fxml;
    exports com.wood2026.databasedemo;
}