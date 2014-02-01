package com.acme.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Date: 01/02/2014
 * Time: 23:01
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@Service
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext ctx = null;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }
}
