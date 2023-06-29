module com.example.smsmanagerapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

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
    exports com.example.smsmanagerapp.garbage;
    opens com.example.smsmanagerapp.garbage to javafx.fxml;
    exports com.example.smsmanagerapp.utility;
    opens com.example.smsmanagerapp.utility to javafx.fxml;
    exports com.example.smsmanagerapp.sender;
    opens com.example.smsmanagerapp.sender to javafx.fxml;
    exports com.example.smsmanagerapp.data.contact;
    opens com.example.smsmanagerapp.data.contact to javafx.fxml;
    //opens com.example.smsmanagerapp.custom.control to javafx.fxml;

}