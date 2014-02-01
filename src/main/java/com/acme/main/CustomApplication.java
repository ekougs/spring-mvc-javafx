package com.acme.main;

import com.acme.components.PrimarySceneFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import static com.acme.main.ServletServer.getApplicationContext;

/**
 * User: sennen
 * Date: 20/01/2014
 * Time: 13:06
 */
@Component
public class CustomApplication extends Application {

    public static void main(String[] args) throws Exception {
        CustomApplication.launch();
    }

    @Override
    public void init() throws Exception {
        super.init();
        ServletServer.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Java FX Feat Spring MVC");
        PrimarySceneFactory primarySceneFactory = getApplicationContext().getBean(PrimarySceneFactory.class);
        primaryStage.setScene(primarySceneFactory.createScene());
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        ServletServer.stop();
        super.stop();
    }
}
