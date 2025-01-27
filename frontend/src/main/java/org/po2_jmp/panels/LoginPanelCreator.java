package org.po2_jmp.panels;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.po2_jmp.FrontendApp;
import org.po2_jmp.request.UserAuthenticationRequest;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.response.UserAuthenticationResponse;
import org.po2_jmp.websocket.JsonUtils;
import org.po2_jmp.websocket.MyWebSocketHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class that creates a login panel for user authentication.
 * It contains methods to initialize the panel, handle user login, and process the authentication response.
 */
public class LoginPanelCreator {

    private JsonUtils jsonUtils = new JsonUtils();
    private MyWebSocketHandler myWebSocketHandler;

    /**
     * Constructs a LoginPanelCreator with the given WebSocket handler.
     */
    public LoginPanelCreator(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    /**
     * Creates and returns the login panel, including title, login section, and registration section.
     */
    public JPanel create(CardLayout layout, JPanel container) {
        JPanel panel = initializePanel();
        panel.add(createTitlePanel());
        panel.add(Box.createVerticalStrut(20));
        panel.add(createLoginSection(layout, container));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createRegisterButton(layout, container));
        return panel;
    }

    /**
     * Initializes the main panel with a vertical BoxLayout and a white background.
     */
    private JPanel initializePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    /**
     * Creates the title panel, containing the application title and an icon.
     */
    private JPanel createTitlePanel() {
        JLabel titleLabel = new JLabel("Sieć Hoteli Akropol", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(128, 0, 128));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel iconLabel = new JLabel("\u2302", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        iconLabel.setForeground(new Color(128, 0, 128));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);
        titlePanel.add(iconLabel);

        return titlePanel;
    }

    /**
     * Creates the login section of the panel, including fields for username, password, and a login button.
     */
    private JPanel createLoginSection(CardLayout layout, JPanel container) {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(Color.WHITE);

        JLabel loginLabel = new JLabel("Zaloguj się", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(loginLabel);
        loginPanel.add(Box.createVerticalStrut(20));

        JLabel usernameLabel = new JLabel("Login", SwingConstants.LEFT);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(usernameLabel);
        loginPanel.add(Box.createVerticalStrut(5));

        JTextField loginField = createTextField("");
        loginPanel.add(loginField);
        loginPanel.add(Box.createVerticalStrut(20));

        JLabel passwordLabel = new JLabel("Hasło", SwingConstants.LEFT);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(passwordLabel);
        loginPanel.add(Box.createVerticalStrut(5));

        JPasswordField passwordField = createPasswordField("");
        loginPanel.add(passwordField);
        loginPanel.add(Box.createVerticalStrut(20));

        JButton loginButton = createButton("Zaloguj się");
        loginButton.addActionListener(e -> handleLogin(layout, container, loginField, passwordField));
        loginPanel.add(loginButton);

        return loginPanel;
    }

    /**
     * Creates the registration section of the panel with a prompt and a register button.
     */
    private JPanel createRegisterButton(CardLayout layout, JPanel container) {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
        registerPanel.setBackground(Color.WHITE);

        JLabel noAccountLabel = new JLabel("Nie masz konta?", SwingConstants.CENTER);
        noAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerPanel.add(noAccountLabel);

        JButton registerButton = createButton("Zarejestruj się");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPanelCreator registerPanelCreator = new RegisterPanelCreator(myWebSocketHandler);
                JPanel registerPanel = registerPanelCreator.create(layout, container);
                container.add(registerPanel, "registerPanel");
                layout.show(container, "registerPanel");
            }
        });
        registerPanel.add(registerButton);

        return registerPanel;
    }


    /**
     * Creates a JTextField with the specified placeholder text.
     */
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setPreferredSize(new Dimension(200, 40));
        textField.setMaximumSize(new Dimension(200, 40));
        return textField;
    }

    /**
     * Creates a JPasswordField with the specified placeholder text.
     */
    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(placeholder);
        passwordField.setPreferredSize(new Dimension(200, 40));
        passwordField.setMaximumSize(new Dimension(200, 40));
        return passwordField;
    }

    /**
     * Creates a JButton with the specified text.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    /**
     * Handles the login process, sending the authentication request and processing the response.
     */
    private void handleLogin(CardLayout layout, JPanel container, JTextField loginField, JPasswordField passwordField) {
        String username = loginField.getText().trim();
        String password = new String(passwordField.getPassword());
        UserAuthenticationRequest authRequest = new UserAuthenticationRequest("authenticate", username, password);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String request = jsonUtils.serialize(authRequest);
                    myWebSocketHandler.sendMessage(request);

                    String response = myWebSocketHandler.getResponse();

                    myWebSocketHandler.setRespondFromBackend(null);
                    handleAuthenticationResponse(response, layout, container);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        };
        worker.execute();
    }

    /**
     * Processes the authentication response and updates the UI accordingly.
     */
    private void handleAuthenticationResponse(String response, CardLayout layout, JPanel container) {
        try {
            UserAuthenticationResponse authResponse = jsonUtils.deserialize(response, UserAuthenticationResponse.class);
            if (authResponse.getStatus() == ResponseStatus.OK) {
                FrontendApp.userId = authResponse.getUserId();
                layout.show(container, "menuPanel");
            } else if (authResponse.getStatus() == ResponseStatus.UNAUTHORIZED) {
                JOptionPane.showMessageDialog(null, "Nie udało się zalogować");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
