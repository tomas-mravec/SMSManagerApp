package com.example.smsmanagerapp.manager;

import com.example.smsmanagerapp.gui.controller.menu.MenuControllerImpl;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class MenuManager {

    private static Node menu = null;
    private static MenuControllerImpl menuController;

    private MenuManager() {
        // Private constructor to prevent instantiation
    }

    public synchronized static Node getMenuInstance() {
//        VBox local = menu;
//        System.out.println("Som v get instance");
//        if (local == null) {
//            synchronized (MenuManager.class) {
//                local = menu;
//                if (local == null) {
//                    System.out.println("vytvaram menu");
//                    menu = local = loadMenu();
//                }
//            }
//        }
        if (menu == null) {
            menu = loadMenu();
        }
        return menu;
    }

    private synchronized static Node loadMenu() {
        if (menu == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuManager.class.getResource(ResourceHelper.getMenuResource()));
            try {
                menu = fxmlLoader.load(); // Assign the loaded value to menu
                menuController = fxmlLoader.getController();
                return menu;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return menu;
    }

    public synchronized static MenuControllerImpl getMenuController() {
        if (menuController == null) {
            getMenuInstance();
        }
        return menuController;
    }
}
