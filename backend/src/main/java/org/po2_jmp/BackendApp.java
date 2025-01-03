package org.po2_jmp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.po2_jmp.controller.Controller;
import java.time.Duration;

public class BackendApp {

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
            e.printStackTrace();
        }
    }

}