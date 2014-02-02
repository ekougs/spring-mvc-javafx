package com.acme.main;

import com.acme.configuration.ConfigurationProvider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * User: sennen
 * Date: 16/01/2014
 * Time: 13:11
 */
public class ServletServer {
    private static final int PORT = 8080;
    private static String SERVER_ADDRESS;
    private static Server SERVER;
    private static AnnotationConfigWebApplicationContext applicationContext;

    public static void launch() throws Exception {
        InetAddress serverAddress = InetAddress.getLocalHost();
        SERVER_ADDRESS = buildServerAddress(serverAddress, PORT);
        SERVER = new Server(new InetSocketAddress(serverAddress, PORT));
        SERVER.setStopAtShutdown(true);
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ConfigurationProvider.class);
        ServletHolder mvcServletHolder = new ServletHolder("mvc-dispatcher", new DispatcherServlet(applicationContext));
        webAppContext.addServlet(mvcServletHolder, "/");
        webAppContext.setExtraClasspath(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        webAppContext.setResourceBase("classpath:/");

        SERVER.setHandler(webAppContext);
        SERVER.start();
    }

    public static AnnotationConfigWebApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void stop() throws Exception {
        SERVER.stop();
    }

    private static String buildServerAddress(InetAddress address, int port) {
        StringBuilder serverAddress = new StringBuilder("http://");
        serverAddress.append(address.getHostAddress()).append(":").append(port);
        return serverAddress.toString();
    }

    public static String getServerAddress() {
        return SERVER_ADDRESS;
    }
}
