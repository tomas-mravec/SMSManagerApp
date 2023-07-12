package com.example.smsmanagerapp.gui.controller.interfaces;

import javafx.scene.Parent;

public interface DeletableMessagesController {
    public void markToDelete(BlockController blockController, boolean checked);
    public void unMarkToDelete(BlockController blockController);
}
