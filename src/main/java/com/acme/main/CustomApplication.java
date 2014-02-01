package com.acme.main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * User: sennen
 * Date: 20/01/2014
 * Time: 13:06
 */
@Component
public class CustomApplication extends Application {
    public static final VBox CONTENT_PANE = new VBox();
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomApplication.class);
    private WebTarget target;

    public static void main(String[] args) {
        CustomApplication.launch();
    }

    @Override
    public void init() throws Exception {
        super.init();
        launchServer();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Java FX Feat Spring MVC");
        primaryStage.setScene(getPrimaryScene());
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        ServletServer.stop();
        super.stop();
    }

    private void launchServer() throws Exception {
        ServletServer.launch();
        Client client = ClientBuilder.newClient();
        target = client.target(ServletServer.getServerAddress());
    }

    private Scene getPrimaryScene() {
        VBox rootPane = new VBox();
        rootPane.getChildren().addAll(getMenuBar(), CONTENT_PANE);
        return new Scene(rootPane, 300, 250);
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
                target.path(resource).request().get();
            } catch (Exception e) {
                LOGGER.error("Exception ", e);
            }
        }
    }
}
