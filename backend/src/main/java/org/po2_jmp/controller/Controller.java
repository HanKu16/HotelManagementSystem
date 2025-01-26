package org.po2_jmp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.po2_jmp.controller.helper.implementation.MessageResponder;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * WebSocket controller that manages client connections and
 * handles incoming messages.
 * <p>
 * This class listens for WebSocket events such as connection, disconnection,
 * messages, and errors. It processes messages from clients and sends responses
 * using the {@link MessageResponder}.
 * </p>
 */
@WebSocket
public class Controller {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private final Set<Session> sessions =
            Collections.synchronizedSet(new HashSet<>());
    private final MessageResponder messageResponder = new MessageResponder();

    /**
     * This method is called when a new WebSocket connection is established.
     * <p>
     * It adds the session to the list of active sessions and logs the connection.
     * </p>
     *
     * @param session the WebSocket session of the connected client
     */
    @OnWebSocketConnect
    public void clientConnected(Session session) {
        sessions.add(session);
        LOGGER.info("New client connected: {}", session.getRemoteAddress().toString());
    }

    /**
     * This method is called when a WebSocket connection is closed.
     * <p>
     * It removes the session from the list of active sessions and logs the disconnection.
     * </p>
     *
     * @param session the WebSocket session of the disconnected client
     */
    @OnWebSocketClose
    public void clientClose(Session session) {
        sessions.remove(session);
        LOGGER.info("Client disconnected: {}", session.getRemoteAddress().toString());
    }

    /**
     * This method is called when an error occurs with the WebSocket connection.
     * <p>
     * It logs the error message.
     * </p>
     *
     * @param err the exception thrown during the WebSocket communication
     */
    @OnWebSocketError
    public void clientError(Throwable err) {
        LOGGER.info("Client error: {}", err.toString());
    }

    /**
     * This method is called when a WebSocket message is received from a client.
     * <p>
     * It processes the message and sends back a response using the {@link MessageResponder}.
     * </p>
     *
     * @param session the WebSocket session of the client
     * @param message the message received from the client
     */
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
