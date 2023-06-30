package com.example.smsmanagerapp.manager;

import com.example.smsmanagerapp.gui.controller.MenuControllerImpl;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuManager {

    private static VBox menu;
    private static MenuControllerImpl menuController;

    private MenuManager() {
        // Private constructor to prevent instantiation
    }

    public static VBox getMenuInstance() {
        VBox local = menu;
        if (local == null) {
            synchronized (MenuManager.class) {
                local = menu;
                if (local == null) {
                    menu = local = loadMenu();
                }
            }
        }
        return menu;
    }

    private static VBox loadMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuManager.class.getResource(ResourceHelper.getMenuResource()));
        try {
            VBox vBoxMenu = fxmlLoader.load(); // Assign the loaded value to menu
            menuController = fxmlLoader.getController();
            return vBoxMenu;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MenuControllerImpl getMenuController() {
        if (menu == null) {
            getMenuInstance();
        }
        return menuController;
    }
}
