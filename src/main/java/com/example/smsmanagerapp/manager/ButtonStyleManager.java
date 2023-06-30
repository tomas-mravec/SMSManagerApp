package com.example.smsmanagerapp.manager;

import javafx.scene.control.Button;

import java.util.Hashtable;

public class ButtonStyleManager {

    private Hashtable<Button, String> buttonStyle;

    public ButtonStyleManager() {
        buttonStyle = new Hashtable<>();
    }

    public void addButton(Button button) {
        buttonStyle.put(button, button.getStyle());
    }
    public String getOriginalStyle(Button button) {
        return buttonStyle.get(button);
    }
}
