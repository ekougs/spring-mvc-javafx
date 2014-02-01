package com.acme.view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

/**
 * User: sennen
 * Date: 30/01/2014
 * Time: 22:11
 */
@Component
public class HelloPaneProvider implements PaneProvider {
    public Pane get() {
        VBox pane = new VBox();
        pane.getChildren().add(new Label("Hello"));
        return pane;
    }
}
