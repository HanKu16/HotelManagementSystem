package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;

public class LoginPanel implements Panel {

    private final PanelId id;
    private final JPanel panel;

    public LoginPanel(PanelId id, CardLayout layout, JPanel container) {
        this.id = id;
        this.panel = create();
    }

    @Override
    public PanelId getId() {
        return id;
    }

    @Override
    public JPanel get() {
        return panel;
    }

    private JPanel create() {
        JPanel panel = setUpPanel();

        JLabel labelTitle = createTitleLabel();
        JLabel labelLogin = createLoginLabel();
        JTextField loginField = createLoginField();
        JTextField passwordField = createPasswordField();
        JButton loginButton = createLoginButton();
        JLabel labelNoAccount = createRegistrationLabel();
        JButton registrationButton = createRegistrationButton();

        panel.add(labelTitle);
        panel.add(Box.createVerticalStrut(10));
        panel.add(labelLogin);
        panel.add(Box.createVerticalStrut(10));
        panel.add(loginField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(labelNoAccount);
        return panel;
    }

    private JPanel setUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }

    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("Sieć Hoteli Akropol");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return titleLabel;
    }

    private JLabel createLoginLabel() {
        JLabel loginLabel = new JLabel("Logowanie");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return loginLabel;
    }

    private JTextField createLoginField() {
        JTextField loginField = new JTextField(20);
        loginField.setMaximumSize(new Dimension(Integer.MAX_VALUE, loginField.getPreferredSize().height));
        return loginField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, passwordField.getPreferredSize().height));
        return passwordField;
    }

    private JButton createLoginButton() {
        JButton loginButton = new JButton("Zaloguj się");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return loginButton;
    }

    private JLabel createRegistrationLabel() {
        JLabel registrationLabel = new JLabel("Nie masz konta?");
        registrationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return registrationLabel;
    }

    private JButton createRegistrationButton() {
        JButton registrationButton = new JButton("Zarejestruj sie");
        registrationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return registrationButton;
    }

}
