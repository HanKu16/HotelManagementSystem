package org.po2_jmp.websocket;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.nio.ByteBuffer;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@WebSocket
public class MyWebSocketHandler {

    private Session session;
    @Getter
    @Setter
    private String respondFromBackend;
    private BlockingQueue<String> responseQueue = new LinkedBlockingQueue<>();
    private Timer pingTimer;

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        System.out.println("Connected to WebSocket server");

        pingTimer = new Timer(true);
        pingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (session.isOpen()) {
                        session.getRemote().sendPing(ByteBuffer.wrap("ping".getBytes()));
                    }
                } catch (Exception e) {
                    System.err.println("Failed to send ping: " + e.getMessage());
                }
            }
        }, 0, 15000); // Ping every 15 seconds
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        try {
            responseQueue.put(message);  // Put the message in the queue
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to put message in queue: " + e.getMessage());
        }
    }

    @OnWebSocketClose
    public void onClose(String reason) {
        System.out.println("WebSocket closed: " + reason);
        if (pingTimer != null) {
            pingTimer.cancel();
        }
    }

    @OnWebSocketError
    public void onError(Throwable error) {
        System.err.println("WebSocket error: " + error.getMessage());
    }

    public void sendMessage(String message) {
        if (session != null && session.isOpen()) {
            try {
                session.getRemote().sendString(message);
            } catch (IOException e) {
                System.err.println("Failed to send message: " + e.getMessage());
            }
        }
    }
    public String getResponse() throws InterruptedException {
        return responseQueue.take();  // This will block until a message is available
    }

}


