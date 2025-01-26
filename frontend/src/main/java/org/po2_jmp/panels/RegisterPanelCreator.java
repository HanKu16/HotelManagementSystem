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
        panel.add(createLoginSection(layout, container));
        return panel;
    }

    private JPanel createRegisterPanel(CardLayout layout, JPanel container) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

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
        panel.add(Box.createVerticalStrut(20));

        JLabel usernameLabel = new JLabel("Login", SwingConstants.LEFT);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(5));

        JTextField loginField = new JTextField("");
        loginField.setPreferredSize(new Dimension(200, 40));
        loginField.setMaximumSize(new Dimension(200, 40));
        panel.add(loginField);
        panel.add(Box.createVerticalStrut(20));

        JLabel passwordLabel = new JLabel("Hasło", SwingConstants.LEFT);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(5));

        JPasswordField passwordField = new JPasswordField("");
        passwordField.setPreferredSize(new Dimension(200, 40));
        passwordField.setMaximumSize(new Dimension(200, 40));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));

        JLabel confirmPasswordLabel = new JLabel("Powtórz hasło", SwingConstants.LEFT);
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(confirmPasswordLabel);
        panel.add(Box.createVerticalStrut(5));

        JPasswordField confirmPasswordField = new JPasswordField("");
        confirmPasswordField.setPreferredSize(new Dimension(200, 40));
        confirmPasswordField.setMaximumSize(new Dimension(200, 40));
        panel.add(confirmPasswordField);
        panel.add(Box.createVerticalStrut(20));

        JButton registerButton = new JButton("Zarejestruj się");
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
            } else if (regResponse.getStatus() == ResponseStatus.BAD_REQUEST){
                JOptionPane.showMessageDialog(null, "Nie udało się zarejestrować: " + regResponse.getMessage());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void showSuccessDialog(CardLayout layout, JPanel container) {
        JOptionPane.showMessageDialog(null, "Udało się zarejestrować");
        layout.show(container, "loginPanel");
    }
    private JPanel createLoginSection(CardLayout layout, JPanel container) {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(Color.WHITE);

        JLabel noAccountLabel = new JLabel("Masz już konto?", SwingConstants.CENTER);
        noAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(noAccountLabel);

        JButton registerButton = createButton("Zaloguj się");
        registerButton.addActionListener(e -> layout.show(container, "loginPanel"));
        loginPanel.add(registerButton);

        return loginPanel;
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
}
