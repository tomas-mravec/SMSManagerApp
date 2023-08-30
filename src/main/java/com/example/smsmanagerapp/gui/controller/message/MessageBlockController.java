package com.example.smsmanagerapp.gui.controller.message;

import com.example.smsmanagerapp.gui.controller.interfaces.*;
import com.example.smsmanagerapp.gui.updater.manager.DeleteMessagesManager;
import com.example.smsmanagerapp.gui.updater.manager.SetMessagesAsSeenManager;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
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

    //private DeletableMessagesController controller;
    private SetMessagesAsSeenManager setMessagesAsSeenManager;
    private DeleteMessagesManager deleteMessagesManager;

    private boolean markableAsSeen;
    private Separator separator;
    private boolean unboxed;
    private boolean firstTimeUnboxed;

    private TextArea fullMessageArea;

    public MessageBlockController() {
        unboxed = false;
        firstTimeUnboxed = true;
    }

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
            deleteMessagesManager.markToDelete(this, false);
        });

        deleteCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Marking to delete");
                deleteMessagesManager.markToDelete(this, true);
            } else {
                System.out.println("Unmarking to delete");
                deleteMessagesManager.unMarkToDelete(this);
            }
        });
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
        if (markableAsSeen) {
            seenCheckBox.setSelected(true);
            seenButton.setVisible(true);
            seenCheckBox.setVisible(true);
        }
    }

    public void unbox() {
      if (firstTimeUnboxed) {
         fullMessageArea = new TextArea();
         fullMessageArea.setWrapText(true);
         fullMessageArea.setLayoutX(90);
         fullMessageArea.setLayoutY(56);
         fullMessageArea.setPrefHeight(110);
         fullMessageArea.setPrefWidth(1150);
//         String messageText = ((UnboxableMessagesController) controller).getMessageText(this);
//         fullMessageArea.setText(messageText);
          fullMessageArea.setText(messageLabel.getText());
         rootPane.getChildren().add(fullMessageArea);
         firstTimeUnboxed = false;
         unboxed = true;
      }
      else if (!unboxed) {
         rootPane.getChildren().add(fullMessageArea);
         unboxed = true;
     } else {
          rootPane.getChildren().remove(fullMessageArea);
          unboxed = false;
     }
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
                setMessagesAsSeenManager.markAsSeen(this, false);
            });
            seenCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue && markableAsSeen) {
                    setMessagesAsSeenManager.markAsSeen(this, true);
                } else if(!newValue && markableAsSeen) {
                    setMessagesAsSeenManager.unMarkAsSeen(this);
                }
            });
        }
    }

    public void setSetMessagesAsSeenManager(SetMessagesAsSeenManager setMessagesAsSeenManager) {
        this.setMessagesAsSeenManager = setMessagesAsSeenManager;
        setMarkableAsSeen(true);
    }
    public void setDeleteMessagesManager(DeleteMessagesManager deleteMessagesManager) {
        this.deleteMessagesManager = deleteMessagesManager;
    }


}
