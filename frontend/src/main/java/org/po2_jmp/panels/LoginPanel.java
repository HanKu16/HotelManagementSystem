package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.po2_jmp.request.UserAuthenticationRequest;
import org.po2_jmp.websocket.JsonUtils;
import org.po2_jmp.websocket.MyWebSocketHandler;

public class LoginPanel implements Panel {

    private final PanelId id;
    private final JPanel panel;
    MyWebSocketHandler myWebSocketHandler;
    JsonUtils jsonUtils = new JsonUtils();


    public LoginPanel(PanelId id, CardLayout layout, JPanel container, MyWebSocketHandler myWebSocketHandler) {
        this.id = id;
        this.panel = create(layout, container);
        this.myWebSocketHandler = myWebSocketHandler;
    }

    @Override
    public PanelId getId() {
        return id;
    }

    @Override
    public JPanel get() {
        return panel;
    }

    private JPanel create(CardLayout layout, JPanel container) {
        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Title section
        JLabel titleLabel = new JLabel("Sieć Hoteli Akropol", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(128, 0, 128));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel iconLabel = new JLabel("\u2302", SwingConstants.CENTER); // Unicode for house
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        iconLabel.setForeground(new Color(128, 0, 128));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);
        titlePanel.add(iconLabel);

        panel.add(titlePanel);
        panel.add(Box.createVerticalStrut(20)); // Space between title and login

        // Login section
        JLabel loginLabel = new JLabel("SIGN IN", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(loginLabel);
        panel.add(Box.createVerticalStrut(20));

        JTextField loginField = new JTextField("login");
        loginField.setPreferredSize(new Dimension(200, 40));
        loginField.setMaximumSize(new Dimension(200, 40));
        panel.add(loginField);
        panel.add(Box.createVerticalStrut(20));

        JPasswordField passwordField = new JPasswordField("password");
        passwordField.setPreferredSize(new Dimension(200, 40));
        passwordField.setMaximumSize(new Dimension(200, 40));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));

        // Buttons
        JButton loginButton = new JButton("Sign in");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setMaximumSize(new Dimension(200, 40));
        loginButton.setBackground(Color.GRAY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(
                e ->layout.show(container, "HotelList"));
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String username = loginField.getText().trim();
                char[] passwordArray = passwordField.getPassword();
                String password = new String(passwordArray);
                UserAuthenticationRequest userAuthenticationRequest = new UserAuthenticationRequest("authenticate", username, password );
                try {
                    String request = jsonUtils.serialize(userAuthenticationRequest);
                    myWebSocketHandler.sendMessage(request);
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(Box.createVerticalStrut(10));

        JLabel noAccountLabel = new JLabel("Don't have an account?", SwingConstants.CENTER);
        noAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(noAccountLabel);

        JButton registerButton = new JButton("Sign up");
        registerButton.setPreferredSize(new Dimension(200, 40));
        registerButton.setMaximumSize(new Dimension(200, 40));
        registerButton.setBackground(Color.GRAY);
        registerButton.setForeground(Color.WHITE);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(
                e -> layout.show(container, "Register"));
        panel.add(registerButton);

        return panel;
    }



}