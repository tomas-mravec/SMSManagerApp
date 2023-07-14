package com.example.smsmanagerapp.gui.controller.message;

import com.example.smsmanagerapp.gui.controller.interfaces.BlockController;
import com.example.smsmanagerapp.gui.controller.interfaces.DeletableMessagesController;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.gui.controller.interfaces.MarkableAsSeenMessagesController;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageBlockController implements Initializable, BlockController {

    @FXML
    private Button seenButton;

    @FXML
    private CheckBox seenCheckBox;

    @FXML
    private Label contactLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private CheckBox deleteCheckBox;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button deleteMessagesButton;

    private DeletableMessagesController controller;
    private boolean markableAsSeen;
    private Separator separator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpCheckBoxes();
        seenButton.setVisible(false);
        seenCheckBox.setVisible(false);
        deleteMessagesButton.setVisible(false);
        deleteCheckBox.setVisible(false);

    }

    private void setUpCheckBoxes() {

        deleteMessagesButton.setOnAction(event -> {
            controller.markToDelete(this, false);
        });

        deleteCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Marking to delete");
                controller.markToDelete(this, true);
            } else {
                System.out.println("Unmarking to delete");
                controller.unMarkToDelete(this);
            }
        });
    }

    public void setDeletableMessageController(DeletableMessagesController controller) {
        this.controller = controller;
    }
    public Parent getRoot() {
        return rootPane;
    }

    @Override
    public void select() {
        deleteCheckBox.setSelected(true);
        deleteCheckBox.setVisible(true);
        deleteMessagesButton.setVisible(true);
    }

    @Override
    public void unSelect() {
        deleteCheckBox.setSelected(false);
        deleteCheckBox.setVisible(false);
        deleteMessagesButton.setVisible(false);
    }

    @Override
    public void selectAsSeen() {
        if (markableAsSeen)
            seenCheckBox.setSelected(true);
            seenButton.setVisible(true);
            seenCheckBox.setVisible(true);
    }

    @Override
    public void unSelectAsSeen() {
        if (markableAsSeen)
            seenCheckBox.setSelected(false);
            seenButton.setVisible(false);
            seenCheckBox.setVisible(false);
    }

    public void setContactLabelText(String text) {
        contactLabel.setText(text);
    }

    public void setTimeLabelText(String text) {
        timeLabel.setText(text);
    }

    public void setMessageLabelText(String text) {
        this.messageLabel.setText(text);
    }

    public void openIcons() {
        deleteMessagesButton.setVisible(true);
        deleteCheckBox.setVisible(true);
        if (markableAsSeen) {
            seenButton.setVisible(true);
            seenCheckBox.setVisible(true);
        }
    }

    public void closeIcons() {
        if (!deleteCheckBox.isSelected()) {
            deleteCheckBox.setVisible(false);
            deleteMessagesButton.setVisible(false);
        }
        if (markableAsSeen && !seenCheckBox.isSelected()) {
            seenCheckBox.setVisible(false);
            seenButton.setVisible(false);
        }
    }

    public void addSeparator(Separator separator) {
        this.separator = separator;
    }

    public Separator getSeparator() {
        return separator;
    }

    public void setMarkableAsSeen(boolean markableAsSeen) {
        this.markableAsSeen = markableAsSeen;
        if (markableAsSeen) {
            seenButton.setOnAction(event -> {
                ((MarkableAsSeenMessagesController)controller).markAsSeen(this, false);
            });

            seenCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue && markableAsSeen) {
                    ((MarkableAsSeenMessagesController) controller).markAsSeen(this, true);
                } else if(!newValue && markableAsSeen) {
                    ((MarkableAsSeenMessagesController) controller).unMarkAsSeen(this);
                }
            });

        }
    }
}
