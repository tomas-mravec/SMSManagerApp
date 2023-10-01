package com.example.smsmanagerapp.gui.controller.message;

import com.example.smsmanagerapp.gui.controller.interfaces.BlockController;
import com.example.smsmanagerapp.gui.controller.interfaces.DeletableMessagesController;
import com.example.smsmanagerapp.gui.controller.interfaces.UnboxableMessagesController;
import com.example.smsmanagerapp.gui.controller.menu.MenuControllerImpl;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.page.manager.MessagePagesManager;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.table.manager.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.manager.MenuManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class HistorySMSMessagesController implements DeletableMessagesController, Initializable, GUIControllerUpdateable, UnboxableMessagesController {

    private Node menu;

//    @FXML
//    private VBox messageBox;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField searchField;
    private List<MessageManager> messageManagers;

    @FXML
    private AnchorPane pagePane;

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

    @FXML
    private HBox buttonBox;

    private LocalDate dateFilterFrom;
    private LocalDate dateFilterTo;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private HashMap<BlockController, SMSMessage> messages;
    private HashMap<BlockController, SMSMessage> messagesToDelete;
    private final int MAX_MESSAGES_ON_PAGE = 5;

    private MessagePagesManager messagePagesManager;

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
        //messageBox.setStyle("-fx-border-color: transparent; -fx-border-width: 0; -fx-background-color: white;");
        //messageBox.getStylesheets().add(getClass().getResource(ResourceHelper.getMessageLabelsStyle()).toExternalForm());
    }

    private void unSelectAllMessageBlocks() {
        messagePagesManager.unSelectAllMessagesToDelete();
    }

    private void selectAllMessageBlocks() {
        messagePagesManager.selectAllMessagesToDelete();
    }

    public void setMenu() {
        rootPane.getChildren().add(0, MenuManager.getMenuInstance());
    }

    @Override
    public void updateGUI(Data data) {
    }

    @Override
    public void addMessageBlock(MessageBlockController controller, SMSMessage smsMessage) {
        this.messages.put(controller, smsMessage);
    }


    public List<Integer> getIdentifiersFromMessages(Collection<SMSMessage> smsMessages) {
       return smsMessages.stream()
                .map(message -> message.getId())
                .collect(Collectors.toList());
    }

    public void deleteMarkedMessages() {
//        messageManagers.get(0).deleteMessagesById(getIdentifiersFromMessages(messagesToDelete.values()));
//        for (BlockController blockController : messagesToDelete.keySet()) {
//            messageBox.getChildren().remove(blockController.getRoot());
//        }
//        messages.remove(messagesToDelete.keySet());
//        messagesToDelete.clear();
        messagePagesManager.deleteMarkedMessages();
    }

    @Override
    public void markToDelete(BlockController blockController, boolean checked) {
//        if (checked) {
//            messagesToDelete.put(blockController, messages.get(blockController));
//        } else {
//            messageManagers.get(0).deleteMessagesById(getIdentifiersFromMessages(List.of(messages.get(blockController))));
//            messageBox.getChildren().remove(blockController.getRoot());
//            messages.remove(blockController);
//            messagesToDelete.remove(blockController);
//        }

    }

    @Override
    public void unMarkToDelete(BlockController blockController) {
//        messagesToDelete.remove(blockController);
    }

    public void dateSelected(ActionEvent event) {
//        dateFilterFrom = datePickerFrom.getValue();
//        dateFilterTo = datePickerTo.getValue();
//        //if (dateFilterFrom != null && dateFilterTo != null) {
//        if ((senderFilter == null || senderFilter.isEmpty()) && (dateFilterFrom == null && dateFilterTo == null))  {
//            messagePagesManager.mainPagesMessages();
//        } else if ((dateFilterTo != null && dateFilterFrom != null) || (senderFilter != null && !senderFilter.isEmpty())){
//            messagePagesManager.filterMessages(senderFilter, dateFilterFrom, dateFilterTo);
//        }
       // }
        if ((dateFilterTo != datePickerTo.getValue() || dateFilterFrom != datePickerFrom.getValue()) &&
                (datePickerFrom.getValue() != null && datePickerTo.getValue() != null)) {
            messagePagesManager.filterMessages(senderFilter, datePickerFrom.getValue(), datePickerTo.getValue());
        }
        else if ((datePickerFrom.getValue() == null && datePickerTo.getValue() == null) && (dateFilterFrom != null || dateFilterTo != null) &&
                senderFilter != null && !senderFilter.isEmpty()) {
            messagePagesManager.filterMessages(senderFilter, datePickerFrom.getValue(), datePickerTo.getValue());
        } else if ((datePickerFrom.getValue() == null && datePickerTo.getValue() == null) && (dateFilterFrom != null || dateFilterTo != null) &&
                senderFilter != null && senderFilter.isEmpty()) {
            messagePagesManager.mainPagesMessages();
        }

        dateFilterFrom = datePickerFrom.getValue();
        dateFilterTo = datePickerTo.getValue();
    }

    private void filterMesssages() {
        for (MessageManager messageManager : messageManagers) {
            for (Data data : messageManager.getFilteredMessages(senderFilter, dateFilterFrom, dateFilterTo, true)) {
                updateGUI(data);
            }
        }
    }

    private void searchHistoryBySender(String newValue) {
//        System.out.println("in search history by sender New value is: " + newValue);
//        senderFilter = newValue;
//        if ((senderFilter == null || senderFilter.isEmpty()) && (dateFilterFrom == null || dateFilterTo == null))  {
//            messagePagesManager.mainPagesMessages();
//        } else {
//            messagePagesManager.filterMessages(senderFilter, dateFilterFrom, dateFilterTo);
//        }

        System.out.println("in search history by sender New value is: " + newValue);

        if (!newValue.equals(senderFilter) && newValue != null && !newValue.isEmpty()) {
            messagePagesManager.filterMessages(newValue, dateFilterFrom, dateFilterTo);
        } else if (newValue.isEmpty() && (dateFilterTo == null || dateFilterFrom == null)) {
            messagePagesManager.mainPagesMessages();
        } else if (newValue.isEmpty() && dateFilterFrom != null && dateFilterTo != null) {
            messagePagesManager.filterMessages(newValue, dateFilterFrom, dateFilterTo);
        }
        senderFilter = newValue;

    }

    private void eraseMessagesOnGUI() {
       // messageBox.getChildren().clear();
    }

    @Override
    public void addMessageManager(MessageManager messageManager) {
        this.messageManagers.add(messageManager);
        messagePagesManager = new MessagePagesManager(pagePane, messageManager, false, MAX_MESSAGES_ON_PAGE, buttonBox);


       // messageManager.addPageManagerToNotifyWhenMessageChangesToSeen(messagePageManager);


        //setUpPages(messageManager.getNumberOfMessages(true));
        //setUpListButtons(messageManager.getNumberOfMessages(true));
    }

//    private void setUpPages(int numberOfMessages) {
//        double result = (double) numberOfMessages / MAX_MESSAGES_ON_PAGE;
//        int numberOfPages =(int) Math.ceil(result);
//        System.out.println("Number of seen messages je: " + numberOfMessages + " Result je: " + result + " Number of pages je: " + numberOfPages);
//        for(int i = 0; i < numberOfPages;i++) {
//            System.out.println("Vytvaram button num: " + i);
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getPageButtonResource()));
//                Button pageButton = fxmlLoader.load();
//                pageButton.setText(i + 1 + "");
//                buttonBox.setHgrow(pageButton,javafx.scene.layout.Priority.ALWAYS);
//                buttonBox.getChildren().add(pageButton);
//                int id = i;
//                pageButton.setOnAction(event -> {
//                    messagePageManager.switchPage(id);
//                });
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        messagePageManager.switchPage(0);
//    }

//    private void setUpListButtons(int numberOfMessages) {
//        double result = (double) numberOfMessages / MAX_MESSAGES_ON_PAGE;
//        int numberOfPages =(int) Math.ceil(result);
//        System.out.println("Number of messages je: " + numberOfMessages + " Result je: " + result + " Number of pages je: " + numberOfPages);
//        for(int i = 0; i < numberOfPages;i++) {
//            System.out.println("Vytvaram button num: " + i);
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getPageButtonResource()));
//                Button pageButton = fxmlLoader.load();
//                pageButton.setText(i + 1 + "");
//                buttonBox.setHgrow(pageButton,javafx.scene.layout.Priority.ALWAYS);
//                buttonBox.getChildren().add(pageButton);
//                int id = i;
//                pageButton.setOnAction(event -> {
//                    messagePageManager.switchPage(id);
//                });
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        messagePageManager.switchPage(0);
//    }

    private void switchPage(int i) {

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
        if (senderFilter == null || senderFilter.isEmpty())  {
            messagePagesManager.mainPagesMessages();
        } else {
            messagePagesManager.filterMessages(senderFilter, dateFilterFrom, dateFilterTo);
        }
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


    @Override
    public String getMessageText(BlockController controller) {
        return messages.get(controller).getContent();
    }
}
