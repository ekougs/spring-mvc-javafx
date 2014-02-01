package com.acme.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.WebTarget;

/**
 * Date: 01/02/2014
 * Time: 21:57
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@Component
public class PrimarySceneFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrimarySceneFactory.class);
    @Autowired
    private WebTarget webTarget;
    @Autowired
    @Qualifier("content")
    private Pane contentPane;
    @Autowired
    @Qualifier("root")
    private Pane rootPane;

    public Scene createScene() {
        Scene scene = new Scene(rootPane, 300, 200);
        rootPane.getChildren().addAll(getMenuBar(), contentPane);
        return scene;
    }

    private MenuBar getMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(getEditMenu());
        return menuBar;
    }

    private Menu getEditMenu() {
        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(getMenuItem("Say hello...", "/hello"));
        return editMenu;
    }

    private MenuItem getMenuItem(String label, String resource) {
        MenuItem newProductMenuItem = new MenuItem(label);
        newProductMenuItem.setOnAction(new MenuItemMainActionHandler(resource));
        return newProductMenuItem;
    }

    private class MenuItemMainActionHandler implements EventHandler<ActionEvent> {
        private final String resource;

        private MenuItemMainActionHandler(String resource) {
            this.resource = resource;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                webTarget.path(resource).request().get();
            } catch (Exception e) {
                LOGGER.error("Exception ", e);
            }
        }
    }
}
