package com.example.smsmanagerapp.gui.updater;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.gui.controller.message.MessageBlockController;
import com.example.smsmanagerapp.gui.updater.manager.DeleteMessagesManager;
import com.example.smsmanagerapp.gui.updater.manager.SetMessagesAsSeenManager;
import com.example.smsmanagerapp.table.manager.message.SMSMessageManager;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessagePageManager {

    private AnchorPane pagePane;
    private GUIMessageUpdater guiMessageUpdater;
    private MessageManager messageManager;
    private final int MAX_MESSAGES_ON_PAGE;
    private HashMap<Integer, VBox> pages;
    private DeleteMessagesManager deleteMessagesManager;
    private SetMessagesAsSeenManager setMessagesAsSeenManager;
    private HashMap<VBox, HashMap<MessageBlockController, SMSMessage>> pageMessageBlocks;
    private HashMap<VBox, Boolean> pagesUpdated;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private VBox currentPage;
    private int currentId;
    private VBox helper;
    private boolean sendToHistory;
    private HBox buttonBox;
    private int numberOfPages;
    private List<Button> buttons;
    public MessagePageManager(AnchorPane pagePane, MessageManager messageManager,
                              boolean sendToHistory, int pageLimit, HBox buttonBox) {
        this.pagePane = pagePane;
        this.pages = new HashMap<>();
        this.sendToHistory = sendToHistory;
        this.pageMessageBlocks = new HashMap<>();
        this.pagesUpdated = new HashMap<>();
        this.messageManager = messageManager;
        this.MAX_MESSAGES_ON_PAGE = pageLimit;
        this.buttons = new ArrayList<>();
        this.deleteMessagesManager = new DeleteMessagesManager(messageManager);
        if (sendToHistory) {
            this.setMessagesAsSeenManager = new SetMessagesAsSeenManager(messageManager);
        }
        this.helper = new VBox();
        this.buttonBox = buttonBox;
        setUpPages(messageManager.getNumberOfMessages(!this.sendToHistory));
    }

    private void setUpPages(int numberOfMessages) {
        double result = (double) numberOfMessages / MAX_MESSAGES_ON_PAGE;
        numberOfPages =(int) Math.ceil(result);
        System.out.println("Number of messages je: " + numberOfMessages + " Result je: " + result + " Number of pages je: " + numberOfPages);
        createButtons(numberOfPages, 0);
        switchPage(0);
    }

    public void selectAllMessagesAsSeen() {
        pageMessageBlocks.get(currentPage).forEach((blockController, smsMessage) -> blockController.selectAsSeen());
    }

    public void unSelectAllMessagesAsSeen() {
        pageMessageBlocks.get(currentPage).forEach(((blockController, smsMessage) -> blockController.unSelectAsSeen()));
    }

    public void selectAllMessagesToDelete() {
        pageMessageBlocks.get(currentPage).forEach((blockController, smsMessage) -> blockController.select());
    }

    public void unSelectAllMessagesToDelete() {
        pageMessageBlocks.get(currentPage).forEach((blockController, smsMessage) -> blockController.unSelect());
    }

    public void deleteMarkedMessages() {
        deleteMessagesManager.deleteMarkedMessages();
        update();
    }

    //pri kazdom update by sa mal zmenit pocet sprav preto skontrolujeme ci rozdiel v pocte stranok
    //novej a starej hodnoty je iny a ak hej tak bud pridame alebo odobereme button(y)
    public void markMessagesAsSeen() {
        setMessagesAsSeenManager.markAllAsSeen();
        update();
    }

    public void switchPage(int id) {
        VBox page = pages.get(id);
            currentId = id;
            if (page == null) {
                page = new VBox();
                page.setPrefHeight(596);
                page.setPrefWidth(1336);
                pages.put(id, page);
                pagesUpdated.put(page, false);
                updatePage(page);
            } else if (!pagesUpdated.get(page)) {
                updatePage(page);
            }
            deleteMessagesManager.pageSwitched(pageMessageBlocks.get(page), page);
            if (sendToHistory) {
                setMessagesAsSeenManager.pageSwitched(pageMessageBlocks.get(page), page);
            }
            if (currentPage != null) {
//            helper.getChildren().add(currentPage);
//            helper.getChildren().clear();
                pagePane.getChildren().remove(currentPage);
            }
            currentPage = page;
            pagePane.getChildren().add(page);
        }

    public void update() {
        updateNumberOfPageButtons();
        pagesUpdated.forEach((key, value) -> pagesUpdated.replace(key, false));
        updatePage(currentPage);
    }

    private void createButtons(int numberOfPages, int startPoint) {
        for(int i = startPoint; i < numberOfPages;i++) {
            System.out.println("Vytvaram button num: " + i);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getPageButtonResource()));
                Button pageButton = fxmlLoader.load();
                pageButton.setText(i + 1 + "");
                buttonBox.setHgrow(pageButton,javafx.scene.layout.Priority.ALWAYS);
                buttonBox.getChildren().add(pageButton);
                buttons.add(pageButton);
                int id = i;
                pageButton.setOnAction(event -> {
                    switchPage(id);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateNumberOfPageButtons() {
        double result = (double)  messageManager.getNumberOfMessages(!sendToHistory) / MAX_MESSAGES_ON_PAGE;
        int newNumberOfPages =(int) Math.ceil(result);
        int difference = newNumberOfPages - this.numberOfPages;
        if (difference > 0) {
            createButtons(newNumberOfPages, this.numberOfPages);
            this.numberOfPages = newNumberOfPages;
        } else if (difference < 0) {
            int buttonsSize = buttons.size();
            for (int i = buttonsSize - 1; i > buttonsSize -1 + difference; i--) {   //difference je vzdy zaporne
                System.out.println("Mazem button " + i);
                buttonBox.getChildren().remove(buttons.get(i));
                buttons.remove(i);
                //removnut Vboxy z hashmapov
                //ak je current page page ktora sa ide removnut switchnut do novej poslednej page
            }
            this.numberOfPages = newNumberOfPages;
        }
    }

    public void updatePage(VBox page) {
       // if (page != null) {
            page.getChildren().clear();
            HashMap<MessageBlockController, SMSMessage> messageBlockControllers = new HashMap<>();
            for (Data data : messageManager.getPageMessages(currentId * MAX_MESSAGES_ON_PAGE, MAX_MESSAGES_ON_PAGE, !sendToHistory)) {
                updateGUI(data, page, messageBlockControllers);
            }
            pagesUpdated.put(page, true);
            pageMessageBlocks.put(page, messageBlockControllers);
            deleteMessagesManager.pageSwitched(messageBlockControllers, page);
            if (sendToHistory) {
                setMessagesAsSeenManager.pageSwitched(messageBlockControllers, page);
            }
        //}
    }

    public void updateGUI(Data data, VBox messageBox,  HashMap<MessageBlockController, SMSMessage> messageBlockControllers) {
        if (data != null) {
            Platform.runLater(() -> {
                Parent parent;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getMessageBlockResource()));
                MessageBlockController messageBlockController;
                try {
                    parent = fxmlLoader.load();
                    messageBlockController = fxmlLoader.getController();
                    //setBlockController(messageBlockController);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                SMSMessage smsMessage = (SMSMessage) data;

                messageBlockController.setDeleteMessagesManager(deleteMessagesManager);
                deleteMessagesManager.setPageManager(this);
                if (setMessagesAsSeenManager != null) {
                    messageBlockController.setSetMessagesAsSeenManager(setMessagesAsSeenManager);
                    setMessagesAsSeenManager.setPageManager(this);
                    messageBlockController.setMarkableAsSeen(true);
                }
                messageBlockController.setContactLabelText(smsMessage.getSender());
                messageBlockController.setTimeLabelText(smsMessage.getRecvTime().format(formatter));
                messageBlockController.setMessageLabelText(smsMessage.getContent());
                Separator separator = new Separator();
                messageBlockController.addSeparator(separator);

                messageBlockControllers.put(messageBlockController, (SMSMessage) data);
                messageBox.getChildren().addAll(messageBlockController.getRoot(), separator);
            });
        }
    }

//    public void setMessageManager(SMSMessageManager messageManager) {
//        this.messageManager = messageManager;
//    }
}
