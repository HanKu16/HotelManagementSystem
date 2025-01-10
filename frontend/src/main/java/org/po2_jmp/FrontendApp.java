package org.po2_jmp;

import org.po2_jmp.window.*;
import org.po2_jmp.window.Window;
import org.po2_jmp.panels.*;
import org.po2_jmp.panels.Panel;
import org.po2_jmp.panels.PanelsContainer;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.po2_jmp.websocket.MyWebSocketHandler;

public class FrontendApp implements Runnable {

    private WebSocketClient webSocketClient;
    private MyWebSocketHandler webSocketHandler;

    @Override
    public void run() {
        try {
            // Connect to WebSocket server
            connectToWebSocketServer("ws://localhost:8889/ws");

            // GUI setup
            WindowSettings settings = createWindowSettings();
            JPanel container = new JPanel();
            CardLayout cardLayout = new CardLayout();
            List<Panel> panels = createPanels(cardLayout, container);
            PanelsContainer myContainer = new PanelsContainer(container, cardLayout, panels);
            Window window = new Window(settings, myContainer);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void connectToWebSocketServer(String serverUri) {
        try {

            webSocketClient = new WebSocketClient();
            BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
            webSocketHandler = new MyWebSocketHandler();

            webSocketClient.start();

            // Connect to the WebSocket server
            webSocketClient.connect(webSocketHandler, new URI(serverUri)).get(15, TimeUnit.MINUTES);
        } catch (Exception e) {
            System.err.println("Failed to connect to WebSocket server: " + e.getMessage());
        }
    }

    private WindowSettings createWindowSettings() {
        return new WindowSettings(
                new WindowDimensions(800, 600),
                new WindowTitle("Hotel Management System"),
                new PanelId("Login"));
    }

    private List<Panel> createPanels(CardLayout cardLayout, JPanel container) {
        List<Panel> panels = new ArrayList<>();
        panels.add(new PanelOne(new PanelId("1"), cardLayout, container));
        panels.add(new PanelTwo(new PanelId("2"), cardLayout, container));
        panels.add(new PanelThree(new PanelId("3"), cardLayout, container));
        panels.add(new LoginPanel(new PanelId("Login"), cardLayout, container, webSocketHandler));
        panels.add(new NavigationPanel(new PanelId("Navigation"), cardLayout, container));
        panels.add(new RoomsPanel(new PanelId("Rooms"), cardLayout, container));
        panels.add(new HotelListPanel(new PanelId("HotelList"), cardLayout, container, webSocketHandler));
        panels.add(new HotelBookingPanel(new PanelId("HotelBooking"), cardLayout, container));
        panels.add(new HotelDetailsPanel(new PanelId("HotelDetails"), cardLayout, container));
        panels.add(new RegisterPanel(new PanelId("Register"), cardLayout, container, webSocketHandler));
        return panels;
    }
}
