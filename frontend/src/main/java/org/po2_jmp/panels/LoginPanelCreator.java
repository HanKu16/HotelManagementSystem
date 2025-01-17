package org.po2_jmp.panels;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.po2_jmp.request.UserAuthenticationRequest;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.response.UserAuthenticationResponse;
import org.po2_jmp.websocket.JsonUtils;
import org.po2_jmp.websocket.MyWebSocketHandler;

import javax.swing.*;
import java.awt.*;

public class LoginPanelCreator {

    private JsonUtils jsonUtils = new JsonUtils();
    private MyWebSocketHandler myWebSocketHandler;

    public LoginPanelCreator(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    public JPanel create(CardLayout layout, JPanel container) {
        JPanel panel = initializePanel();
        panel.add(createTitlePanel());
        panel.add(Box.createVerticalStrut(20));
        panel.add(createLoginSection(layout, container));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createRegisterSection(layout, container));
        return panel;
    }

    private JPanel initializePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        return panel;
    }

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

    private JPanel createLoginSection(CardLayout layout, JPanel container) {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(Color.WHITE);

        JLabel loginLabel = new JLabel("SIGN IN", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(loginLabel);
        loginPanel.add(Box.createVerticalStrut(20));

        JTextField loginField = createTextField("login");
        loginPanel.add(loginField);
        loginPanel.add(Box.createVerticalStrut(20));

        JPasswordField passwordField = createPasswordField("password");
        loginPanel.add(passwordField);
        loginPanel.add(Box.createVerticalStrut(20));

        JButton loginButton = createButton("Sign in");
        loginButton.addActionListener(e -> handleLogin(layout, container, loginField, passwordField));
        loginPanel.add(loginButton);

        return loginPanel;
    }

    private JPanel createRegisterSection(CardLayout layout, JPanel container) {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
        registerPanel.setBackground(Color.WHITE);

        JLabel noAccountLabel = new JLabel("Don't have an account?", SwingConstants.CENTER);
        noAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerPanel.add(noAccountLabel);

        JButton registerButton = createButton("Sign up");
        registerButton.addActionListener(e -> layout.show(container, "Register"));
        registerPanel.add(registerButton);

        return registerPanel;
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setPreferredSize(new Dimension(200, 40));
        textField.setMaximumSize(new Dimension(200, 40));
        return textField;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(placeholder);
        passwordField.setPreferredSize(new Dimension(200, 40));
        passwordField.setMaximumSize(new Dimension(200, 40));
        return passwordField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
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

    private void handleAuthenticationResponse(String response, CardLayout layout, JPanel container) {
        try {
            UserAuthenticationResponse authResponse = jsonUtils.deserialize(response, UserAuthenticationResponse.class);
            if (authResponse.getStatus() == ResponseStatus.OK) {
                layout.show(container, "menuPanel");
            } else {
                System.out.println("Authentication failed: " + authResponse.getMessage());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
