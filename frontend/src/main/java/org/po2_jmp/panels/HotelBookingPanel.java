package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HotelBookingPanel implements Panel {

    private final PanelId id;
    private final JPanel panel;

    public HotelBookingPanel(PanelId id, CardLayout layout, JPanel container) {
        this.id = id;
        this.panel = create(layout, container);
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

        JLabel hotelBookingLabel = new JLabel("Wybrałeś hotel: ", SwingConstants.CENTER);
        hotelBookingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        hotelBookingLabel.setForeground(new Color(128, 0, 128));
        hotelBookingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);
        titlePanel.add(hotelBookingLabel);

        panel.add(titlePanel);
        panel.add(Box.createVerticalStrut(20)); // Space between title and content

        // Room selection section
        JLabel roomLabel = new JLabel("Wybierz pokój dla siebie:", SwingConstants.CENTER);
        roomLabel.setFont(new Font("Arial", Font.BOLD, 16));
        roomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(roomLabel);

        ButtonGroup roomGroup = new ButtonGroup();
        JPanel roomOptionsPanel = new JPanel();
        roomOptionsPanel.setLayout(new BoxLayout(roomOptionsPanel, BoxLayout.Y_AXIS));
        roomOptionsPanel.setBackground(Color.WHITE);

        panel.add(roomOptionsPanel);
        panel.add(Box.createVerticalStrut(20));

        // Date selection placeholder
        JLabel dateLabel = new JLabel("Wybierz datę rezerwacji:", SwingConstants.CENTER);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(dateLabel);

        JTextField calendarPlaceholder = new JTextField("kalendarz");
        calendarPlaceholder.setHorizontalAlignment(JTextField.CENTER);
        calendarPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
        calendarPlaceholder.setEditable(false);
        calendarPlaceholder.setMaximumSize(new Dimension(200, 30));
        panel.add(calendarPlaceholder);

        panel.add(Box.createVerticalStrut(20));

        // Footer section
        JButton reserveButton = new JButton("Rezerwuj");
        reserveButton.setBackground(new Color(128, 0, 128));
        reserveButton.setForeground(Color.WHITE);
        reserveButton.setPreferredSize(new Dimension(200, 40));
        reserveButton.setMaximumSize(new Dimension(200, 40));
        reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(reserveButton);

        JLabel footerLabel = new JLabel("Do zobaczenia w naszym hotelu!", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(128, 0, 128));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(footerLabel);

        return panel;
    }
}
