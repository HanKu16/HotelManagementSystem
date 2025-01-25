package org.po2_jmp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebSocket
public class Controller {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private final Set<Session> sessions =
            Collections.synchronizedSet(new HashSet<>());
    private final MessageResponder messageResponder = new MessageResponder();

    @OnWebSocketConnect
    public void clientConnected(Session session) {
        sessions.add(session);
        LOGGER.info("New client connected: {}", session.getRemoteAddress().toString());
    }

    @OnWebSocketClose
    public void clientClose(Session session) {
        sessions.remove(session);
        LOGGER.info("Client disconnected: {}", session.getRemoteAddress().toString());
    }

    @OnWebSocketError
    public void clientError(Throwable err) {
        LOGGER.info("Client error: {}", err.toString());
    }

    @OnWebSocketMessage
    public void clientMessage(Session session, String message) {
        try {
            handleMessage(session, message);
        } catch (Exception e) {
            LOGGER.error("An error occurred: {}", message);
        }
    }

    private void handleMessage(Session session, String message) throws IOException {
        LOGGER.info("Message from client: {}", message);
        String response = messageResponder.respond(message);
        session.getRemote().sendString(response);
        LOGGER.info("Response from server: {}", response);
    }

}
