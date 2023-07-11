package com.example.smsmanagerapp.gui.controller.send;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ReceiverBlockController {

    @FXML
    private Label receiverLabel;

    @FXML
    private AnchorPane receiverBlock;

    @FXML
    private Button removeButton;


    public void setReceiverLabelText(String text) {
        receiverLabel.setText(text);
    }

    public Label getReceiverLabel() {
        return receiverLabel;
    }

    public Parent getRootPane() {
        return receiverBlock;
    }

    public Button getRemoveButton() {
        return removeButton;
    }
//    public void remove() {
//        VBox parent = (VBox) receiverBlock.getParent();
//        parent.getChildren().remove(receiverBlock);
//    }


}
