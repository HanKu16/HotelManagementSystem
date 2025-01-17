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

    private WebSocketClient webSocketClient;
    private MyWebSocketHandler myWebSocketHandler = new MyWebSocketHandler();
    private PanelsContainer panelsContainer;
    private JsonUtils jsonUtils = new JsonUtils();

//    @Override
//    public void run() {
//        try {
//            // Connect to WebSocket server
//            connectToWebSocketServer("ws://localhost:8889/ws");
//
//            // GUI setup
//            WindowSettings settings = createWindowSettings();
//            JPanel container = new JPanel();
//            CardLayout cardLayout = new CardLayout();
//            List<Panel> panels = createPanels(cardLayout, container);
//            PanelHotelActions hotelActionsPanel = new PanelHotelActions(new PanelId("HotelActions"), cardLayout, container, webSocketHandler);
//            panels.add (hotelActionsPanel);
//            panelsContainer = new PanelsContainer(container, cardLayout, panels);
//            hotelActionsPanel.setPanelsContainer(panelsContainer);
//            Window window = new Window(settings, panelsContainer);
//
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }

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
        window.setSize(800,700);
        JPanel testPanel = new JPanel();
        container.add(testPanel, "testPanel");
        cardLayout.show(container, "loginPanel");

        window.add(container);
        window.setVisible(true);
    }
    private void connectToWebSocketServer(String serverUri) {
        try {

            webSocketClient = new WebSocketClient();
            //webSocketHandler = new MyWebSocketHandler();

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

//    private List<Panel> createPanels(CardLayout cardLayout, JPanel container) {
//        List<Panel> panels = new ArrayList<>();
////        panels.add(new PanelHotelActions(new PanelId("HotelActions"), cardLayout, container, webSocketHandler));
//        panels.add(new LoginPanel(new PanelId("Login"), cardLayout, container, myWebSocketHandler));
//        panels.add(new NavigationPanel(new PanelId("Navigation"), cardLayout, container));
//        panels.add(new HotelListPanel(new PanelId("HotelList"), cardLayout, container, myWebSocketHandler));
//        panels.add(new HotelBookingPanel(new PanelId("HotelBooking"), cardLayout, container));
//        panels.add(new HotelDetailsPanel(new PanelId("HotelDetails"), cardLayout, container));
//        panels.add(new RegisterPanel(new PanelId("Register"), cardLayout, container, myWebSocketHandler));
//        return panels;
//    }
//    private JPanel createLoginPanel(CardLayout layout, JPanel container) {
//        JPanel panel = initializePanel();
//        panel.add(createTitlePanel());
//        panel.add(Box.createVerticalStrut(20));
//        panel.add(createLoginSection(layout, container));
//        panel.add(Box.createVerticalStrut(10));
//        panel.add(createRegisterSection(layout, container));
//        return panel;
//    }
//
//    private JPanel initializePanel() {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBackground(Color.WHITE);
//        return panel;
//    }
//
//    private JPanel createTitlePanel() {
//        JLabel titleLabel = new JLabel("Sieć Hoteli Akropol", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        titleLabel.setForeground(new Color(128, 0, 128));
//        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        JLabel iconLabel = new JLabel("\u2302", SwingConstants.CENTER);
//        iconLabel.setFont(new Font("Arial", Font.PLAIN, 30));
//        iconLabel.setForeground(new Color(128, 0, 128));
//        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        JPanel titlePanel = new JPanel();
//        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
//        titlePanel.setBackground(Color.WHITE);
//        titlePanel.add(titleLabel);
//        titlePanel.add(iconLabel);
//
//        return titlePanel;
//    }
//
//    private JPanel createLoginSection(CardLayout layout, JPanel container) {
//        JPanel loginPanel = new JPanel();
//        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
//        loginPanel.setBackground(Color.WHITE);
//
//        JLabel loginLabel = new JLabel("SIGN IN", SwingConstants.CENTER);
//        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
//        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        loginPanel.add(loginLabel);
//        loginPanel.add(Box.createVerticalStrut(20));
//
//        JTextField loginField = createTextField("login");
//        loginPanel.add(loginField);
//        loginPanel.add(Box.createVerticalStrut(20));
//
//        JPasswordField passwordField = createPasswordField("password");
//        loginPanel.add(passwordField);
//        loginPanel.add(Box.createVerticalStrut(20));
//
//        JButton loginButton = createButton("Sign in");
//        loginButton.addActionListener(e -> handleLogin(layout, container, loginField, passwordField));
//        loginPanel.add(loginButton);
//
//        return loginPanel;
//    }
//
//    private JPanel createRegisterSection(CardLayout layout, JPanel container) {
//        JPanel registerPanel = new JPanel();
//        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
//        registerPanel.setBackground(Color.WHITE);
//
//        JLabel noAccountLabel = new JLabel("Don't have an account?", SwingConstants.CENTER);
//        noAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        registerPanel.add(noAccountLabel);
//
//        JButton registerButton = createButton("Sign up");
//        registerButton.addActionListener(e -> layout.show(container, "Register"));
//        registerPanel.add(registerButton);
//
//        return registerPanel;
//    }
//
//    private JTextField createTextField(String placeholder) {
//        JTextField textField = new JTextField(placeholder);
//        textField.setPreferredSize(new Dimension(200, 40));
//        textField.setMaximumSize(new Dimension(200, 40));
//        return textField;
//    }
//
//    private JPasswordField createPasswordField(String placeholder) {
//        JPasswordField passwordField = new JPasswordField(placeholder);
//        passwordField.setPreferredSize(new Dimension(200, 40));
//        passwordField.setMaximumSize(new Dimension(200, 40));
//        return passwordField;
//    }
//
//    private JButton createButton(String text) {
//        JButton button = new JButton(text);
//        button.setPreferredSize(new Dimension(200, 40));
//        button.setMaximumSize(new Dimension(200, 40));
//        button.setBackground(Color.GRAY);
//        button.setForeground(Color.WHITE);
//        button.setAlignmentX(Component.CENTER_ALIGNMENT);
//        return button;
//    }
//
//    private void handleLogin(CardLayout layout, JPanel container, JTextField loginField, JPasswordField passwordField) {
//        String username = loginField.getText().trim();
//        String password = new String(passwordField.getPassword());
//        UserAuthenticationRequest authRequest = new UserAuthenticationRequest("authenticate", username, password);
//
//        SwingWorker<Void, Void> worker = new SwingWorker<>() {
//            @Override
//            protected Void doInBackground() {
//                try {
//                    String request = jsonUtils.serialize(authRequest);
//                    myWebSocketHandler.sendMessage(request);
//
//                    String response = myWebSocketHandler.getResponse();
//
//                    myWebSocketHandler.setRespondFromBackend(null);
//                    handleAuthenticationResponse(response, layout, container);
//                } catch (JsonProcessingException ex) {
//                    ex.printStackTrace();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                return null;
//            }
//        };
//        worker.execute();
//    }
//
//    private void handleAuthenticationResponse(String response, CardLayout layout, JPanel container) {
//        try {
//            UserAuthenticationResponse authResponse = jsonUtils.deserialize(response, UserAuthenticationResponse.class);
//            if (authResponse.getStatus() == ResponseStatus.OK) {
//                handleHotelList(layout, container);
//            } else {
//                System.out.println("Authentication failed: " + authResponse.getMessage());
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }

//    private JPanel createHotelOverviews(CardLayout layout, JPanel container) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBackground(Color.WHITE);
//
//        JLabel titleLabel = new JLabel("Sieć Hoteli Akropol", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        titleLabel.setForeground(new Color(128, 0, 128));
//        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        panel.add(titleLabel);
//        panel.add(Box.createVerticalStrut(20));
//
//        handleHotelList(layout, container);
//
//
//
//        return panel;
//    }

    private JPanel createHotelPanel(String hotelName, String address, String amenities, CardLayout layout, JPanel container) {
        JPanel hotelPanel = new JPanel();
        hotelPanel.setLayout(new GridBagLayout());
        hotelPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        hotelPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel(hotelName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        hotelPanel.add(nameLabel, gbc);

        gbc.gridy = 1;
        JLabel addressLabel = new JLabel("Lokalizacja: " + address);
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        hotelPanel.add(addressLabel, gbc);

        gbc.gridy = 2;
        JTextArea amenitiesArea = new JTextArea(amenities);
        amenitiesArea.setFont(new Font("Arial", Font.PLAIN, 12));
        amenitiesArea.setEditable(false);
        amenitiesArea.setBackground(Color.WHITE);
        amenitiesArea.setBorder(BorderFactory.createEmptyBorder());
        hotelPanel.add(amenitiesArea, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton selectButton = new JButton("Wybierz");
        selectButton.setBackground(new Color(128, 0, 128));
        selectButton.setForeground(Color.WHITE);
        selectButton.addActionListener(e -> layout.show(container, "HotelDetails"));
        hotelPanel.add(selectButton, gbc);

        return hotelPanel;
    }
    private void handleHotelList(CardLayout layout, JPanel container) {
        HotelsOverviewsRequest overviewsRequest = new HotelsOverviewsRequest("getHotelsOverviews");
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String request = jsonUtils.serialize(overviewsRequest);
                    myWebSocketHandler.sendMessage(request);

                    String response;
                    do {
                        response = myWebSocketHandler.getRespondFromBackend();
                    } while (response == null);

                    myWebSocketHandler.setRespondFromBackend(null);
                    handleResponse(response, layout, container);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }

    private void handleResponse(String response, CardLayout layout, JPanel container) {
        try {
            JPanel hotelOverviewsPanel = new JPanel();
            HotelsOverviewsResponse ovsResponse = jsonUtils.deserialize(response, HotelsOverviewsResponse.class);
            if (ovsResponse.getStatus() == ResponseStatus.OK) {

                SwingUtilities.invokeLater(() -> {
                    try {
                        // Aktualizacja GUI
                        JLabel titleLabel = new JLabel("Sieć Hoteli Akropol", SwingConstants.CENTER);
                        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
                        titleLabel.setForeground(new Color(128, 0, 128));
                        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        hotelOverviewsPanel.add(titleLabel);
                        hotelOverviewsPanel.add(Box.createVerticalStrut(20));

                        ovsResponse.getHotels().forEach(hotel -> {
                            String hotelName = hotel.getName();
                            AddressDto address = hotel.getAddress();
                            String addressAsText = address.toString();
                            String amenities = String.join("\n", hotel.getAmenities());
                            hotelOverviewsPanel.add(createHotelPanel(hotelName, addressAsText, amenities, layout, container));
                        });

                        hotelOverviewsPanel.revalidate();
                        hotelOverviewsPanel.repaint();
                        container.add(hotelOverviewsPanel, "hotelOverviewsPanel");
                        layout.show(container, "hotelOverviewsPanel");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } else {
                System.out.println("Authentication failed: " + ovsResponse.getMessage());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static JPanel generatePanel(){

        return new JPanel();
    }



}
