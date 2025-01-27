package org.po2_jmp;
import org.po2_jmp.panels.*;
import javax.swing.*;
import java.awt.*;
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
        JFrame window = createWindow();
        window.setVisible(true);
    }

    private void connectToWebSocketServer(String serverUri) {
        try {
            webSocketClient = new WebSocketClient();
            webSocketClient.start();

            webSocketClient.connect(myWebSocketHandler, new URI(serverUri)).get(15L, TimeUnit.MINUTES);
        } catch (Exception e) {
            System.err.println("Failed to connect to WebSocket server: " + e.getMessage());
        }
    }

    private JFrame createWindow() {
        JFrame window = new JFrame("HotelManagementSystem");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 700);

        JPanel container = createContainer();
        window.add(container);

        return window;
    }

    private JPanel createContainer() {
        JPanel container = new JPanel();
        CardLayout cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        JPanel loginPanel = createLoginPanel(cardLayout, container);
        container.add(loginPanel, "loginPanel");

        JPanel menuPanel = createMenuPanel(cardLayout, container);
        container.add(menuPanel, "menuPanel");

        cardLayout.show(container, "loginPanel");

        return container;
    }

    private JPanel createLoginPanel(CardLayout cardLayout, JPanel container) {
        LoginPanelCreator loginPanelCreator = new LoginPanelCreator(myWebSocketHandler);
        return loginPanelCreator.create(cardLayout, container);
    }

    private JPanel createMenuPanel(CardLayout cardLayout, JPanel container) {
        MenuPanelCreator menuPanelCreator = new MenuPanelCreator(myWebSocketHandler);
        return menuPanelCreator.create(cardLayout, container);
    }
}
