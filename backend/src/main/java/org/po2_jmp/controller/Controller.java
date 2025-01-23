package org.po2_jmp.controller;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebSocket
public class Controller {

    private final Set<Session> sessions =
            Collections.synchronizedSet(new HashSet<>());
    private final MessageResponder messageResponder = new MessageResponder();

    @OnWebSocketConnect
    public void clientConnected(Session session){
        sessions.add(session);
        System.out.println("New client connected: " +
                session.getRemoteAddress().toString());
    }

    @OnWebSocketClose
    public void clientClose(Session session){
        sessions.remove(session);
        System.out.println("Client disconnected: " +
                session.getRemoteAddress().toString());
    }

    @OnWebSocketError
    public void clientError(Throwable err){
        System.out.println("Client error: " + err.toString());
    }

    @OnWebSocketMessage
    public void clientMessage(Session session, String message) throws IOException {
        System.out.println("Request from client\n" + message);
        String response = messageResponder.respond(message);
        session.getRemote().sendString(response);
        System.out.println("Response from server\n" + response);
    }

}
