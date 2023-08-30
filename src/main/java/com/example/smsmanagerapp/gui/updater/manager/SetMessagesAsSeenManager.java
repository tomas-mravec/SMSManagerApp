package com.example.smsmanagerapp.gui.updater.manager;

import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.gui.controller.interfaces.BlockController;
import com.example.smsmanagerapp.gui.controller.message.MessageBlockController;
import com.example.smsmanagerapp.gui.updater.MessagePageManager;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SetMessagesAsSeenManager {
    private HashMap<MessageBlockController, SMSMessage> messagesToHistory;
    private HashMap<MessageBlockController, SMSMessage> messages;
    private VBox page;
    private MessageManager messageManager;
    private MessagePageManager pageManager;

    public SetMessagesAsSeenManager(MessageManager messageManager) {
        this.messageManager = messageManager;
        this.messagesToHistory = new HashMap<>();
    }

    public void pageSwitched(HashMap<MessageBlockController, SMSMessage> messages, VBox page) {
        this.messages = messages;
        this.page = page;
        messagesToHistory.clear();
    }

    public void markAsSeen(MessageBlockController blockController, boolean checked) {
        if (checked) {
            messagesToHistory.put(blockController, messages.get(blockController));
        } else {
            messageManager.setMessagesAsSeen(getIdentifiersFromMessages(List.of(messages.get(blockController))));
            page.getChildren().remove(blockController.getRoot());
            messages.remove(blockController);
            messagesToHistory.remove(blockController);
            pageManager.update();
        }
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
    public List<Integer> getIdentifiersFromMessages(Collection<SMSMessage> smsMessages) {
        return smsMessages.stream()
                .map(message -> message.getId())
                .collect(Collectors.toList());
    }

    public void unMarkAsSeen(BlockController blockController) {
        messagesToHistory.remove(blockController);
    }

    public void markAllAsSeen() {
        messageManager.setMessagesAsSeen(getIdentifiersFromMessages(messagesToHistory.values()));
        for (BlockController blockController : messagesToHistory.keySet()) {
            page.getChildren().remove(blockController.getRoot());
        }
        messages.remove(messagesToHistory.keySet());
        messagesToHistory.clear();
        pageManager.update();
    }

    public void setPageManager(MessagePageManager pageManager) {
        this.pageManager = pageManager;
    }
}
