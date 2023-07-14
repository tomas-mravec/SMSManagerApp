package com.example.smsmanagerapp.gui.controller.message;

import com.example.smsmanagerapp.gui.controller.interfaces.BlockController;
import com.example.smsmanagerapp.gui.controller.interfaces.DeletableMessagesController;
import com.example.smsmanagerapp.gui.controller.menu.MenuControllerImpl;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.table.manager.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.manager.MenuManager;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class HistorySMSMessagesController implements DeletableMessagesController, Initializable, GUIControllerUpdateable {

    private Node menu;

    @FXML
    private VBox messageBox;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField searchField;
    private List<MessageManager> messageManagers;

    @FXML
    private Button resetDateButton;

    private MenuControllerImpl menuController;
    private MessageRecencyType recencyType;
    private String senderFilter;
    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private CheckBox selectAllToDeleteCheckBox;

    private LocalDate dateFilterFrom;
    private LocalDate dateFilterTo;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private HashMap<BlockController, Integer> messages;
    private HashMap<BlockController, Integer> messagesToDelete;

    public HistorySMSMessagesController() {
        messages = new HashMap<>();
        messagesToDelete = new HashMap<>();
        messageManagers = new ArrayList<>();
        recencyType = MessageRecencyType.HISTORY_MESSAGE;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //menu = MenuManager.getMenuInstance();
       // menuController = MenuManager.getMenuController();
        System.out.println("Som v history controlleru pred pridadim menu do root pane");
        //rootPane.getChildren().add(0, menu); // Add menuBox as the first child
        System.out.println("Som v history controlleru za pridadim menu do root pane");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("In button listener new search word is " + newValue);
            searchHistoryBySender(newValue);
        });
//        scrollPane.setStyle("-fx-background-color: white;");
//        messageBox.setStyle("-fx-background-color: white;");

        selectAllToDeleteCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectAllMessageBlocks();
            } else {
                unSelectAllMessageBlocks();
            }
        });

        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-color: white;");
        messageBox.setStyle("-fx-border-color: transparent; -fx-border-width: 0; -fx-background-color: white;");
        messageBox.getStylesheets().add(getClass().getResource(ResourceHelper.getMessageLabelsStyle()).toExternalForm());
    }

    private void unSelectAllMessageBlocks() {
        for (BlockController blockController : messages.keySet()) {
            blockController.unSelect();
        }
    }

    private void selectAllMessageBlocks() {
        for (BlockController blockController : messages.keySet()) {
            blockController.select();
        }
    }

    public void setMenu() {
        rootPane.getChildren().add(0, MenuManager.getMenuInstance());
    }

    @Override
    public void updateGUI(Data data) {
        if (data != null) {
            Platform.runLater(() -> {
                Parent parent;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getMessageBlockResource()));
                MessageBlockController messageBlockController;
                try {
                    parent = fxmlLoader.load();
                    messageBlockController = fxmlLoader.getController();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                 }

                SMSMessage smsMessage = (SMSMessage) data;

                messageBlockController.setDeletableMessageController(this);
                messages.put(messageBlockController, smsMessage.getId());

                messageBlockController.setContactLabelText(smsMessage.getSender());
                messageBlockController.setTimeLabelText(smsMessage.getRecvTime().format(formatter));
                messageBlockController.setMessageLabelText(smsMessage.getContent());
                Separator separator = new Separator();
                messageBlockController.addSeparator(separator);
                messageBox.getChildren().addAll(messageBlockController.getRoot(), separator);

            });
        }
    }

    public void deleteMarkedMessages() {
        messageManagers.get(0).deleteMessagesById(new ArrayList<>(messagesToDelete.values()));
        for (BlockController blockController : messagesToDelete.keySet()) {
            messageBox.getChildren().remove(blockController.getRoot());
        }
        messages.remove(messagesToDelete.keySet());
        messagesToDelete.clear();
    }

    @Override
    public void markToDelete(BlockController blockController, boolean checked) {
        if (checked) {
            messagesToDelete.put(blockController, messages.get(blockController));
        } else {
            messageManagers.get(0).deleteMessagesById(List.of(messages.get(blockController)));
            messageBox.getChildren().remove(blockController.getRoot());
            messages.remove(blockController);
            messagesToDelete.remove(blockController);
        }
    }

    @Override
    public void unMarkToDelete(BlockController blockController) {
        messagesToDelete.remove(blockController);
    }

    public void dateSelected(ActionEvent event) {
        dateFilterFrom = datePickerFrom.getValue();
        dateFilterTo = datePickerTo.getValue();
        if (dateFilterFrom != null && dateFilterTo != null) {
            eraseMessagesOnGUI();
            filterMesssages();
        }
    }

    private void filterMesssages() {
        for (MessageManager messageManager : messageManagers) {
            for (Data data : messageManager.getFilteredMessages(senderFilter, dateFilterFrom, dateFilterTo, true)) {
                updateGUI(data);
            }
        }
    }

    private void searchHistoryBySender(String newValue) {
        System.out.println("in search history by sender New value is: " + newValue);
        senderFilter = newValue;
        eraseMessagesOnGUI();
        System.out.println("I erased messages");
        filterMesssages();
    }

    private void eraseMessagesOnGUI() {
        messageBox.getChildren().clear();
    }

    @Override
    public void addMessageManager(MessageManager messageManager) {
        this.messageManagers.add(messageManager);
    }

    @Override
    public void loadMessages() {
        System.out.println("Loadujem data z history controllera budem vnutry?");
        messages.clear();
        messagesToDelete.clear();
        eraseMessagesOnGUI();
        for (MessageManager messageManager : messageManagers) {
            for (Data data : messageManager.getAllSeenMessages()) {
                System.out.println("YEP");
                updateGUI(data);
            }
        }
    }

    public void resetDatePicker(ActionEvent event) {
        datePickerFrom.setValue(null);
        datePickerTo.setValue(null);
        dateFilterFrom = null;
        dateFilterTo = null;
        eraseMessagesOnGUI();
        filterMesssages();
    }

    @Override
    public void testReturn() {

    }
    public Parent getRoot() {
        return rootPane;
    }
    public MessageRecencyType getRecencyType() {
        return recencyType;
    }


}
