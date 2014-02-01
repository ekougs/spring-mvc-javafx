package com.acme.view;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * User: sennen
 * Date: 30/01/2014
 * Time: 21:40
 */
@Component
public class CustomViewResolver implements ViewResolver {
    @Autowired
    private BeanFactory beanFactory;

    @Autowired @Qualifier("content")
    private Pane applicationContentPane;

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return new JavaFXPanelRenderingView(viewName);
    }

    private class JavaFXPanelRenderingView implements View {
        private final String viewName;

        public JavaFXPanelRenderingView(String viewName) {
            this.viewName = viewName;
        }

        @Override
        public String getContentType() {
            return "application/octet-stream";
        }

        @Override
        public void render(Map<String, ?> stringMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ObservableList<Node> contentPaneNodes = applicationContentPane.getChildren();
                    contentPaneNodes.clear();
                    PaneProvider paneProvider = (PaneProvider) beanFactory.getBean(viewName);
                    contentPaneNodes.add(paneProvider.get());
                }
            });
        }
    }
}
