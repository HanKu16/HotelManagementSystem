package org.po2_jmp.websocket;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.po2_jmp.response.UserAuthenticationResponse;
import org.po2_jmp.response.UserRegistrationResponse;
import org.po2_jmp.websocket.JsonUtils;


import java.io.IOException;
import java.io.*;

@WebSocket
public class MyWebSocketHandler {

    private Session session;
    private JsonUtils jsonUtils = new JsonUtils();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        System.out.println("Connected to WebSocket server");
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        try {
            String type = jsonUtils.getTypeFromMessage(message);
            switch (type) {
                case "user was successfully authenticated":
                    UserAuthenticationResponse loginResponse =  jsonUtils.deserialize(message, UserAuthenticationResponse.class);
                    break;
                case "user was successfully registered":
                    UserRegistrationResponse registerResponse =  jsonUtils.deserialize(message, UserRegistrationResponse.class);
                    break;
                default:
                    System.err.println("Unknown message type: " + type);
                    break;
            }
        }
        catch (Exception e) {
            System.err.println("Response failed: " + e.getMessage());
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("WebSocket closed: " + reason);
    }

    @OnWebSocketError
    public void onError(Throwable error) {
        System.err.println("WebSocket error: " + error.getMessage());
    }

    public void sendMessage(String message) {
        if (session != null && session.isOpen()) {
            try {
                session.getRemote().sendString(message);
                System.out.println(message);
            } catch (IOException e) {
                System.err.println("Failed to send message: " + e.getMessage());
            }
        }
    }
}


