package com.example.smsmanagerapp.gui.controller.interfaces;

public interface MarkableAsSeenMessagesController {
    public void markAsSeen(BlockController blockController, boolean checked);
    public void unMarkAsSeen(BlockController blockController);
}
