package com.example.smsmanagerapp.page;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.gui.controller.message.MessageBlockController;
import com.example.smsmanagerapp.gui.updater.GUIMessageUpdater;
import com.example.smsmanagerapp.page.manager.DeleteMessagesManager;
import com.example.smsmanagerapp.page.manager.SetMessagesAsSeenManager;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessagePages {

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
    int x = 0;
    private String textFilter;
    private LocalDate dateFilterFrom;
    private LocalDate dateFilterTo;
    private boolean connectToPane;
    private boolean firstUpdate;

    public MessagePages(AnchorPane pagePane, MessageManager messageManager,
                        boolean sendToHistory, int pageLimit, HBox buttonBox) {
        this.pagePane = pagePane;
        this.pages = new HashMap<>();
        this.sendToHistory = sendToHistory;
        this.pageMessageBlocks = new HashMap<>();
        this.pagesUpdated = new HashMap<>();
        this.messageManager = messageManager;
        this.MAX_MESSAGES_ON_PAGE = pageLimit;
        this.buttons = new ArrayList<>();
        this.firstUpdate = true;
        this.deleteMessagesManager = new DeleteMessagesManager(messageManager);
        this.deleteMessagesManager.setPageManager(this);
        if (sendToHistory) {
            this.setMessagesAsSeenManager = new SetMessagesAsSeenManager(messageManager);
            this.setMessagesAsSeenManager.setPageManager(this);
        }
        this.buttonBox = buttonBox;
    }

    public void setUpPages() {
        int numberOfMessages = messageManager.getNumberOfMessages(!this.sendToHistory, null, null, null);
        double result = (double) numberOfMessages / MAX_MESSAGES_ON_PAGE;
        numberOfPages =(int) Math.ceil(result);
        VBox page = new VBox();
        page.setPrefHeight(596);
        page.setPrefWidth(1336);
        pages.put(0, page);
        pagesUpdated.put(page, false);
        System.out.println("Number of messages je: " + numberOfMessages + " Result je: " + result + " Number of pages je: " + numberOfPages);
        createButtons(numberOfPages, 0);
        //switchPage(0);
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
        // update();
    }

    //pri kazdom update by sa mal zmenit pocet sprav preto skontrolujeme ci rozdiel v pocte stranok
    //novej a starej hodnoty je iny a ak hej tak bud pridame alebo odobereme button(y)
    public void markMessagesAsSeen() {
        setMessagesAsSeenManager.markAllAsSeen();
        //update();
    }

    public void switchPage(int id) {
        System.out.println("Pocet page vboxov v hasmape je: " + pages.size());
        VBox page = pages.get(id);
        currentId = id;
        if (page == null) {
            page = new VBox();
            page.setPrefHeight(596);
            page.setPrefWidth(1336);
            pages.put(id, page);
            pagesUpdated.put(page, false);
            System.out.printf("vytvaran novy page id " + id);
            updatePage(page, textFilter, dateFilterFrom, dateFilterTo);
        } else if (!pagesUpdated.get(page)) {
            updatePage(page, textFilter, dateFilterFrom, dateFilterTo);
            System.out.println("idem updatnut existujucu page s id: " + currentId);
        }

        //tu asi treba dat else lebo zbytocne to davame do managerov inak

        System.out.println("Stav updtovania aktualnej page id: " + currentPage + " " + currentId + " je: " + pagesUpdated.get(page));

        deleteMessagesManager.pageSwitched(pageMessageBlocks.get(page), page);
        if (sendToHistory) {
            setMessagesAsSeenManager.pageSwitched(pageMessageBlocks.get(page), page);
        }
        ///potial

        if (currentPage != null) {
            pagePane.getChildren().remove(currentPage);
        }
        currentPage = page;
        firstUpdate = false;
        if (connectToPane)
            pagePane.getChildren().add(page);
        System.out.println("Switchol som do page: " + id + 1);
    }



    private void createButtons(int numberOfPages, int startPoint) {
        for(int i = startPoint; i < numberOfPages;i++) {
            System.out.println("Vytvaram button num: " + i);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getPageButtonResource()));
                Button pageButton = fxmlLoader.load();
                pageButton.setText(i + 1 + "");
                buttonBox.setHgrow(pageButton,javafx.scene.layout.Priority.ALWAYS);
                if (connectToPane)
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


    private boolean updateNumberOfPageButtons(String textFilter, LocalDate dateFilterFrom, LocalDate dateFilterTo) {
        boolean updatePage = true;
        double result = (double)  messageManager.getNumberOfMessages(!sendToHistory, textFilter, dateFilterFrom, dateFilterTo) / MAX_MESSAGES_ON_PAGE;
        int newNumberOfPages =(int) Math.ceil(result);
        int difference = newNumberOfPages - this.numberOfPages;
        if (difference > 0) {
            createButtons(newNumberOfPages, this.numberOfPages);
            this.numberOfPages = newNumberOfPages;
        } else if (difference < 0) {
            int buttonsSize = buttons.size();
            for (int i = buttonsSize - 1; i > buttonsSize -1 + difference; i--) {   //difference je vzdy zaporne
                System.out.println("Mazem button " + i);
                if (connectToPane)
                    buttonBox.getChildren().remove(buttons.get(i));
                buttons.remove(i);
                VBox vBoxToRemove = pages.get(i);
                System.out.println("Mazem vbox page " + i + " zostavajuci pocet vboxov je: " + pages.size());
                pages.remove(i);
                pagesUpdated.remove(vBoxToRemove);
                pageMessageBlocks.remove(vBoxToRemove);
            }
            this.numberOfPages = newNumberOfPages;
            if (!pages.containsValue(currentPage) && pages.size() > 0 && !firstUpdate) {
//                currentPage = pages.get(buttonsSize -1 + difference);
//                currentId = buttonsSize -1 + difference;
                System.out.println("Pages does not contian currentPage, currentid: " + currentId);
                updatePage = false;                                             //nechceme updatovat page ked switchPage ju updatne
                switchPage(buttonsSize -1 + difference);
            }
            else if (pages.size() <= 0) {
                noMessages();
                updatePage = false;
            }
        }
        return updatePage;
    }

    private void noMessages() {
        pagePane.getChildren().remove(currentPage);
        currentPage = null;
    }

    public void update() {
       if ((textFilter != null && !textFilter.isEmpty()) || (dateFilterFrom != null && dateFilterTo != null)) {
            update(textFilter, dateFilterFrom, dateFilterTo, false);
         } else {
           pagesUpdated.forEach((key, value) -> pagesUpdated.replace(key, false));
           boolean updateCurrentPage = updateNumberOfPageButtons(null, null, null);
             if (updateCurrentPage){
               if (currentPage == null)
                   switchPage(0);
               else
                   updatePage(currentPage, null, null, null);
            }
       }

    }

    public void update(String newValue, LocalDate dateFilterFrom, LocalDate dateFilterTo, boolean reload) {
        this.textFilter = newValue;
        this.dateFilterFrom = dateFilterFrom;
        this.dateFilterTo = dateFilterTo;
        pagesUpdated.forEach((key, value) -> pagesUpdated.replace(key, false));
        boolean updateCurrentPage = updateNumberOfPageButtons(textFilter, dateFilterFrom, dateFilterTo);
        if (updateCurrentPage){
//            if (currentPage == null)
//                switchPage(0);
//            else
//                updatePage(currentPage, null, null, null);
            switchPage(0);
        }
        }



    public void updatePage(VBox page, String newValue, LocalDate dateFilterFrom, LocalDate dateFilterTo) {
        Platform.runLater(() -> {
            page.getChildren().clear();
            //System.out.println("Mazem celu page");
            HashMap<MessageBlockController, SMSMessage> messageBlockControllers = new HashMap<>();
            for (Data data : messageManager.getPageMessages(currentId * MAX_MESSAGES_ON_PAGE, MAX_MESSAGES_ON_PAGE,
                    !sendToHistory, newValue, dateFilterFrom, dateFilterTo)) {
                System.out.println("Davam spravu na page");
                updateGUI(data, page, messageBlockControllers);
            }
            System.out.println("Updatujem page: " + currentId + " status na true");
            pagesUpdated.put(page, true);
            pageMessageBlocks.put(page, messageBlockControllers);
            deleteMessagesManager.pageSwitched(messageBlockControllers, page);
            if (sendToHistory) {
                setMessagesAsSeenManager.pageSwitched(messageBlockControllers, page);
            }
            //}
        });
    }

    public void updateGUI(Data data, VBox messageBox,  HashMap<MessageBlockController, SMSMessage> messageBlockControllers) {
        if (data != null) {
            //Platform.runLater(() -> {
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
            if (setMessagesAsSeenManager != null) {
                messageBlockController.setSetMessagesAsSeenManager(setMessagesAsSeenManager);
                messageBlockController.setMarkableAsSeen(true);
            }
            messageBlockController.setContactLabelText(smsMessage.getSender());
            messageBlockController.setTimeLabelText(smsMessage.getRecvTime().format(formatter));
            messageBlockController.setMessageLabelText(smsMessage.getContent());
            Separator separator = new Separator();
            messageBlockController.addSeparator(separator);

            messageBlockControllers.put(messageBlockController, (SMSMessage) data);
            messageBox.getChildren().addAll(messageBlockController.getRoot(), separator);
            String position;
            if (sendToHistory) {
                position = "new messages";
            } else {
                position = "history";
            }
            //System.out.println("Pred pridanim v " + position + " pocej nodes je: " +  (long) messageBox.getChildren().size());
           // messageBox.getChildren().add(messageBlockController.getRoot());
            System.out.println("pridavam novy node do vboxu, cize spravu, x je: " + x);
            //System.out.println("Pridal som node do vboxu v " + position + " , celkovy pocet children nodes je: " + (long) messageBox.getChildren().size());
            // });
        } else {
            System.out.println("Data je null");
        }
    }

    public void disconnectFromPanel() {
        connectToPane = false;
        pagePane.getChildren().remove(currentPage);
        buttonBox.getChildren().clear();
    }

    public void connectToPanel() {
        connectToPane = true;
        if (currentPage!= null) {
            pagePane.getChildren().add(currentPage);
            System.out.println("current page nie je null");
        }
        buttonBox.getChildren().addAll(buttons);
    }


//    public void setMessageManager(SMSMessageManager messageManager) {
//        this.messageManager = messageManager;
//    }
}
