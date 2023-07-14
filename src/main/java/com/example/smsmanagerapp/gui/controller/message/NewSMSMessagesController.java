package com.example.smsmanagerapp.gui.controller.message;

import com.example.smsmanagerapp.gui.controller.interfaces.BlockController;
import com.example.smsmanagerapp.gui.controller.interfaces.DeletableMessagesController;
import com.example.smsmanagerapp.gui.controller.interfaces.MarkableAsSeenMessagesController;
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

public class NewSMSMessagesController implements GUIControllerUpdateable, DeletableMessagesController, MarkableAsSeenMessagesController {

    @FXML
    private VBox messageBox;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField searchField;
    private LocalDate dateFilterFrom;
    private LocalDate dateFilterTo;

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private CheckBox selectAllToDeleteCheckBox;

    @FXML
    private CheckBox markAllAsSeenCheckBox;

    private List<MessageManager> messageManagers;
    private MessageRecencyType recencyType;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private HashMap<BlockController, Integer> messages;
    private HashMap<BlockController, Integer> messagesToDelete;
    private HashMap<BlockController, Integer> messagesToHistory;
    private String senderFilter;

    public NewSMSMessagesController() {
        messages = new HashMap<>();
        messagesToDelete = new HashMap<>();
        messagesToHistory = new HashMap<>();
        messageManagers = new ArrayList<>();
        recencyType = MessageRecencyType.NEW_MESSAGE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //menu = MenuManager.getMenuInstance();
        //menuController = MenuManager.getMenuController();
        //rootPane.getChildren().add(0, menu);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-color: white;");
        messageBox.setStyle("-fx-border-color: transparent; -fx-border-width: 0; -fx-background-color: white;");
        messageBox.getStylesheets().add(getClass().getResource(ResourceHelper.getMessageLabelsStyle()).toExternalForm());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("In button listener new search word is " + newValue);
            searchHistoryBySender(newValue);
        });
        setUpCheckBoxes();
    }

    private void setUpCheckBoxes() {
        selectAllToDeleteCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectAllMessageBlocks();
            } else {
                unSelectAllMessageBlocks();
            }
        });

        markAllAsSeenCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectAllAsSeenMessageBlocks();
            } else {
                unSelectAllAsSeenMessageBlocks();
            }
        });
    }

    public void resetDatePicker() {
        datePickerFrom.setValue(null);
        datePickerTo.setValue(null);
        dateFilterFrom = null;
        dateFilterTo = null;
        eraseMessagesOnGUI();
        filterMesssages();
    }

    private void searchHistoryBySender(String newValue) {
        System.out.println("in search history by sender New value is: " + newValue);
        senderFilter = newValue;
        eraseMessagesOnGUI();
        System.out.println("I erased messages");
        filterMesssages();
    }

    private void filterMesssages() {
        for (MessageManager messageManager : messageManagers) {
            for (Data data : messageManager.getFilteredMessages(senderFilter, dateFilterFrom, dateFilterTo, false)) {
                updateGUI(data);
            }
        }
    }

    private void eraseMessagesOnGUI() {
        messageBox.getChildren().clear();
    }

    private void unSelectAllAsSeenMessageBlocks() {
        for (BlockController blockController : messages.keySet()) {
            blockController.unSelectAsSeen();
        }
    }

    private void selectAllAsSeenMessageBlocks() {
        for (BlockController blockController : messages.keySet()) {
            blockController.selectAsSeen();
        }
    }

    public void markMessagesAsSeen() {
        messageManagers.get(0).setMessagesAsSeen(new ArrayList<>(messagesToHistory.values()));
        for (BlockController blockController : messagesToHistory.keySet()) {
            messageBox.getChildren().remove(blockController.getRoot());
        }
        messages.remove(messagesToHistory.keySet());
        messagesToHistory.clear();
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

    public void loadMessages() {
        System.out.println("Loadujem data z gui controllera");
        messageBox.getChildren().clear();
        for (MessageManager messageManager : messageManagers) {
           for (Data data : messageManager.getAllNewMessages()) {
                   updateGUI(data);
           }
        }
    }

    public void dateSelected(ActionEvent event) {
        dateFilterFrom = datePickerFrom.getValue();
        dateFilterTo = datePickerTo.getValue();
        if (dateFilterFrom != null && dateFilterTo != null) {
            eraseMessagesOnGUI();
            filterMesssages();
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
    public void updateGUI(Data data) {
        if (data != null) {
            Platform.runLater(() -> {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getMessageBlockResource()));
                MessageBlockController messageBlockController;
                try {
                    fxmlLoader.load();
                    messageBlockController = fxmlLoader.getController();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                SMSMessage smsMessage = (SMSMessage) data;
                messageBlockController.setDeletableMessageController(this);
                messageBlockController.setMarkableAsSeen(true);
                messages.put(messageBlockController, smsMessage.getId());

                if (smsMessage.getContact().getName() != null && !smsMessage.getContact().getName().isEmpty()) {
                    messageBlockController.setContactLabelText(smsMessage.getContact().getName());
                } else {
                    messageBlockController.setContactLabelText(smsMessage.getContact().getNumber());
                }
                messageBlockController.setTimeLabelText(smsMessage.getRecvTime().format(formatter));
                messageBlockController.setMessageLabelText(smsMessage.getContent());
                Separator separator = new Separator();
                messageBox.getChildren().addAll(messageBlockController.getRoot(), separator);

            });
        }
    }

    private void sendMessageToHistory(SMSMessage smsMessage) {
        for (MessageManager messageManager : messageManagers) {
           messageManager.setMessageAsSeen(smsMessage);
        }
    }

    @Override
    public void addMessageManager(MessageManager messageManager) {
        if (!messageManagers.contains(messageManager)) {
            messageManagers.add(messageManager);
        }
    }

    public Parent getRoot() {
        return rootPane;
    }

    public void testReturn() {
        //System.out.println("GUIController sa ozyva " + messageManagers.get(0).toString());
    }

    public MessageRecencyType getRecencyType() {
        return recencyType;
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

    @Override
    public void markAsSeen(BlockController blockController, boolean checked) {
        if (checked) {
            messagesToHistory.put(blockController, messages.get(blockController));
        } else {
            messageManagers.get(0).setMessagesAsSeen(List.of(messages.get(blockController)));
            messageBox.getChildren().remove(blockController.getRoot());
            messages.remove(blockController);
            messagesToHistory.remove(blockController);
        }
    }

    @Override
    public void unMarkAsSeen(BlockController blockController) {
        messagesToHistory.remove(blockController);
    }
}