package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.po2_jmp.FrontendApp;
import org.po2_jmp.request.UserRegistrationRequest;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.response.UserAuthenticationResponse;
import org.po2_jmp.response.UserRegistrationResponse;
import org.po2_jmp.websocket.JsonUtils;
import org.po2_jmp.websocket.MyWebSocketHandler;

public class RegisterPanelCreator {

    MyWebSocketHandler myWebSocketHandler;
    JsonUtils jsonUtils = new JsonUtils();

    public RegisterPanelCreator(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    public JPanel create(CardLayout layout, JPanel container) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.add(createRegisterPanel(layout, container));
        return panel;
    }

    private JPanel createRegisterPanel(CardLayout layout, JPanel container) {
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

        JPasswordField confirmPasswordField = new JPasswordField("password");
        confirmPasswordField.setPreferredSize(new Dimension(200, 40));
        confirmPasswordField.setMaximumSize(new Dimension(200, 40));
        panel.add(confirmPasswordField);
        panel.add(Box.createVerticalStrut(20));

        // Buttons
        JButton registerButton = new JButton("Sign up");
        registerButton.setPreferredSize(new Dimension(200, 40));
        registerButton.setMaximumSize(new Dimension(200, 40));
        registerButton.setBackground(Color.GRAY);
        registerButton.setForeground(Color.WHITE);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginField.getText().trim();
                char[] passwordArray = passwordField.getPassword();
                String password = new String(passwordArray);
                char[] confirmPasswordArray = confirmPasswordField.getPassword();
                String confirmPassword = new String(confirmPasswordArray);
                UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("register", username, password, confirmPassword);
                try {
                    String request = jsonUtils.serialize(userRegistrationRequest);
                    myWebSocketHandler.sendMessage(request);
                    String response = myWebSocketHandler.getResponse();
                    myWebSocketHandler.setRespondFromBackend(null);
                    handleRegistrationResponse(response, layout, container);
                } catch (JsonProcessingException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    private void handleRegistrationResponse(String response, CardLayout layout, JPanel container) {
        try {
            UserRegistrationResponse regResponse = jsonUtils.deserialize(response, UserRegistrationResponse.class);
            if (regResponse.getStatus() == ResponseStatus.CREATED) {
                showSuccessDialog(layout, container);
            } else {
                System.out.println("Registration failed: " + regResponse.getMessage());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void showSuccessDialog(CardLayout layout, JPanel container) {
        JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        layout.show(container, "loginPanel");
    }
}
