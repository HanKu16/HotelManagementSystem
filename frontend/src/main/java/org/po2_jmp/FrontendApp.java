package org.po2_jmp;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.po2_jmp.request.HotelsOverviewsRequest;
import org.po2_jmp.request.UserAuthenticationRequest;
import org.po2_jmp.response.AddressDto;
import org.po2_jmp.response.HotelsOverviewsResponse;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.response.UserAuthenticationResponse;
import org.po2_jmp.websocket.JsonUtils;
import org.po2_jmp.window.*;
import org.po2_jmp.panels.*;
import org.po2_jmp.panels.Panel;
import org.po2_jmp.panels.PanelsContainer;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.po2_jmp.websocket.MyWebSocketHandler;

public class FrontendApp implements Runnable {

    public static String userId = null;
    private WebSocketClient webSocketClient;
    private MyWebSocketHandler myWebSocketHandler = new MyWebSocketHandler();
    @Override
    public void run() {
        connectToWebSocketServer("ws://localhost:8889/ws");
        WindowSettings settings = createWindowSettings();
        JPanel container = new JPanel();
        CardLayout cardLayout = new CardLayout();
        container.setLayout(cardLayout);
        LoginPanelCreator loginPanelCreator = new LoginPanelCreator(myWebSocketHandler);
        JPanel loginPanel = loginPanelCreator.create(cardLayout, container);
        container.add(loginPanel, "loginPanel");
        MenuPanelCreator menuPanelCreator = new MenuPanelCreator(myWebSocketHandler);
        JPanel menuPanel = menuPanelCreator.create(cardLayout, container);
        container.add(menuPanel, "menuPanel");

        JFrame window = new JFrame("HotelManagementSystem");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 700);
        JPanel testPanel = new JPanel();
        container.add(testPanel, "testPanel");
        cardLayout.show(container, "loginPanel");

        window.add(container);
        window.setVisible(true);
    }

    private void connectToWebSocketServer(String serverUri) {
        try {
            webSocketClient = new WebSocketClient();
            webSocketClient.start();
            // Connect to the WebSocket server
            webSocketClient.connect(myWebSocketHandler, new URI(serverUri)).get(15L, TimeUnit.MINUTES);
        } catch (Exception e) {
            System.err.println("Failed to connect to WebSocket server: " + e.getMessage());
        }
    }

    private WindowSettings createWindowSettings() {
        return new WindowSettings(
                new WindowDimensions(800, 600),
                new WindowTitle("Hotel Management System"),
                new PanelId("loginPanel"));
    }
}