package org.po2_jmp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.po2_jmp.controller.Controller;
import java.time.Duration;

public class BackendApp {

    private static final Logger LOGGER = LogManager.getLogger(BackendApp.class);

    public void run()  {
        Server server = new Server(8889);
        var handler = new ServletContextHandler(server, "/ws");
        server.setHandler(handler);

        JettyWebSocketServletContainerInitializer.configure(handler,
                (servletContext, container) -> {
                container.setIdleTimeout(Duration.ofMinutes(15L));
                container.addMapping("/", Controller.class);
        });

        try {
            server.start();
            System.out.println("Server started");
        } catch (Exception e) {
            LOGGER.error("An error occurred:", e);
        }
    }

}