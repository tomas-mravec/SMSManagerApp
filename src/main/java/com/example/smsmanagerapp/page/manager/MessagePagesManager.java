package com.example.smsmanagerapp.page.manager;

import com.example.smsmanagerapp.page.MessagePages;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class MessagePagesManager {

    private AnchorPane pagePane;
    private MessageManager messageManager;
    private boolean sendToHistory;
    private final int MAX_NUMBER_OF_MESSAGES_ON_PAGE;
    private HBox buttonBox;
    private MessagePages mainPages;
    private MessagePages filterPages;
    private MessagePages currentPages;

    public MessagePagesManager(AnchorPane pagePane, MessageManager messageManager, boolean sendToHistory, int MAX_NUMBER_OF_MESSAGES_ON_PAGE, HBox buttonBox) {
        this.pagePane = pagePane;
        this.messageManager = messageManager;
        this.sendToHistory = sendToHistory;
        this.MAX_NUMBER_OF_MESSAGES_ON_PAGE = MAX_NUMBER_OF_MESSAGES_ON_PAGE;
        this.buttonBox = buttonBox;
        this.mainPages = new MessagePages(pagePane, messageManager, sendToHistory, MAX_NUMBER_OF_MESSAGES_ON_PAGE, buttonBox);
        this.filterPages = new MessagePages(pagePane, messageManager, sendToHistory, MAX_NUMBER_OF_MESSAGES_ON_PAGE, buttonBox);
        this.currentPages = mainPages;
        if (!sendToHistory) {
            messageManager.addPageManagerToNotifyWhenMessageChangesToSeen(mainPages);
        }
        this.mainPages.setUpPages();
        this.filterPages.setUpPages();

        this.mainPages.switchPage(0);
        this.mainPages.connectToPanel();
    }

    public void update() {
        mainPages.update();
    }
    public void selectAllMessagesAsSeen() {
        currentPages.selectAllMessagesAsSeen();
    }

    public void unSelectAllMessagesAsSeen() {
        currentPages.unSelectAllMessagesAsSeen();
    }

    public void selectAllMessagesToDelete() {
        currentPages.selectAllMessagesToDelete();
    }

    public void unSelectAllMessagesToDelete() {
        currentPages.unSelectAllMessagesToDelete();
    }

    public void deleteMarkedMessages() {
        currentPages.deleteMarkedMessages();
    }

    //pri kazdom update by sa mal zmenit pocet sprav preto skontrolujeme ci rozdiel v pocte stranok
    //novej a starej hodnoty je iny a ak hej tak bud pridame alebo odobereme button(y)
    public void markMessagesAsSeen() {
        currentPages.markMessagesAsSeen();
    }

    private void switchPaiges(MessagePages currentPages, MessagePages targetPages) {
        //odpojit current pages, cize aj buttony
        //pripojit target pages

        System.out.println("disconnecting and connecting new pannel");
        currentPages.disconnectFromPanel();
        targetPages.connectToPanel();
        this.currentPages = targetPages;
    }

    public void filterMessages(String newValue, LocalDate dateFilterFrom, LocalDate dateFilterTo) {
        System.out.println("Filter mode");
        filterPages.update(newValue, dateFilterFrom, dateFilterTo, true);
        if (currentPages != filterPages)
            switchPaiges(currentPages, filterPages);
    }


    public void mainPagesMessages() {
        System.out.println("main pages mode");
        mainPages.update();
        if (currentPages != mainPages)
            switchPaiges(currentPages, mainPages);
    }
}