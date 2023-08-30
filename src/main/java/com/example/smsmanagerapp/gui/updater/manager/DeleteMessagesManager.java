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

public class DeleteMessagesManager {

    private HashMap<MessageBlockController, SMSMessage> messagesToDelete;
    private HashMap<MessageBlockController, SMSMessage> messages;
    private VBox page;
    private MessageManager messageManager;
    private MessagePageManager pageManager;

    public DeleteMessagesManager(MessageManager messageManager) {
        this.messageManager = messageManager;
        this.messagesToDelete = new HashMap<>();
    }

    private void unSelectAllMessageBlocks() {
        for (BlockController blockController : messages.keySet()) {
            blockController.select();
        }
    }

    private void selectAllMessageBlocks() {
        for (BlockController blockController : messages.keySet()) {
            blockController.select();
        }
    }
    public void markToDelete(MessageBlockController blockController, boolean checked) {
        if (checked) {
            messagesToDelete.put(blockController, messages.get(blockController));
        } else {
            messageManager.deleteMessagesById(getIdentifiersFromMessages(List.of(messages.get(blockController))));
            page.getChildren().remove(blockController.getRoot());
            messages.remove(blockController);
            messagesToDelete.remove(blockController);
            pageManager.update();
        }
    }

    public void unMarkToDelete(BlockController blockController) {
        messagesToDelete.remove(blockController);
    }

    public void deleteMarkedMessages() {
        messageManager.deleteMessagesById(getIdentifiersFromMessages(messagesToDelete.values()));
        for (BlockController blockController : messagesToDelete.keySet()) {
            page.getChildren().remove(blockController.getRoot());
        }
        messages.remove(messagesToDelete.keySet());
        messagesToDelete.clear();
        pageManager.update();
    }
    public void pageSwitched(HashMap<MessageBlockController, SMSMessage> messages, VBox page) {
        this.messages = messages;
        this.page = page;
        messagesToDelete.clear();
    }
    public List<Integer> getIdentifiersFromMessages(Collection<SMSMessage> smsMessages) {
        return smsMessages.stream()
                .map(message -> message.getId())
                .collect(Collectors.toList());
    }
    public void setPageManager(MessagePageManager pageManager) {
        this.pageManager = pageManager;
    }
}
