module com.example.smsmanagerapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.smsmanagerapp to javafx.fxml;
    exports com.example.smsmanagerapp;
    exports com.example.smsmanagerapp.gui;
    opens com.example.smsmanagerapp.gui to javafx.fxml;
    exports com.example.smsmanagerapp.interfaces;
    opens com.example.smsmanagerapp.interfaces to javafx.fxml;
    exports com.example.smsmanagerapp.gui.controller;
    opens com.example.smsmanagerapp.gui.controller to javafx.fxml;
    exports com.example.smsmanagerapp.data;
    opens com.example.smsmanagerapp.data to javafx.fxml;
}