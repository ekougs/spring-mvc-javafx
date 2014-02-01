package com.acme.configuration;

import com.acme.main.ServletServer;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * User: sennen
 * Date: 17/01/2014
 * Time: 21:20
 */
@Configuration
@EnableWebMvc
public class ConfigurationProvider extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    @Qualifier("content")
    public Pane contentPane() {
        return new VBox();
    }

    @Bean
    @Qualifier("root")
    public Pane rootPane() {
        return new VBox();
    }

    @Bean
    public WebTarget webTarget() {
        Client client = ClientBuilder.newClient();
        return client.target(ServletServer.getServerAddress());
    }
}
