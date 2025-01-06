package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HotelDetailsPanel implements Panel {

    private final PanelId id;
    private final JPanel panel;

    public HotelDetailsPanel(PanelId id, CardLayout layout, JPanel container) {
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
        panel.setBackground(new Color(221, 160, 221));

        // Title section
        JLabel titleLabel = new JLabel("Sieć Hoteli Akropol", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        // Hotel information section
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setBackground(new Color(221, 160, 221));

        // Left: Hotel image placeholder
        JLabel imageLabel = new JLabel("foto");
        imageLabel.setPreferredSize(new Dimension(150, 150));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(imageLabel);

        // Center: Hotel details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(new Color(221, 160, 221));

        JLabel hotelNameLabel = new JLabel("nazwa hotelu", SwingConstants.LEFT);
        hotelNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        hotelNameLabel.setForeground(Color.WHITE);

        JLabel ratingLabel = new JLabel(" ★", SwingConstants.LEFT); // Unicode star character
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ratingLabel.setForeground(Color.ORANGE);

        JLabel locationLabel = new JLabel("Lokalizacja: ", SwingConstants.LEFT);
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        locationLabel.setForeground(Color.WHITE);

        detailsPanel.add(hotelNameLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(ratingLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(locationLabel);

        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(detailsPanel);

        // Right: Strengths
        JPanel strengthsPanel = new JPanel();
        strengthsPanel.setLayout(new BoxLayout(strengthsPanel, BoxLayout.Y_AXIS));
        strengthsPanel.setBackground(new Color(221, 160, 221));

        JLabel strengthsTitle = new JLabel("Mocne strony:");
        strengthsTitle.setFont(new Font("Arial", Font.BOLD, 14));
        strengthsTitle.setForeground(Color.WHITE);
        strengthsPanel.add(strengthsTitle);

        for (int i = 0; i<3; i++) {
            JLabel strengthLabel = new JLabel("- " + "zaleta");
            strengthLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            strengthLabel.setForeground(Color.WHITE);
            strengthsPanel.add(strengthLabel);
        }

        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(strengthsPanel);

        panel.add(infoPanel);
        panel.add(Box.createVerticalStrut(20));

        // Hotel description
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setBackground(new Color(238, 130, 238));
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel descriptionTitle = new JLabel("Opis hotelu:");
        descriptionTitle.setFont(new Font("Arial", Font.BOLD, 16));
        descriptionTitle.setForeground(Color.WHITE);

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(238, 130, 238));
        descriptionArea.setForeground(Color.WHITE);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder());

        descriptionPanel.add(descriptionTitle);
        descriptionPanel.add(Box.createVerticalStrut(10));
        descriptionPanel.add(descriptionArea);

        panel.add(descriptionPanel);
        panel.add(Box.createVerticalStrut(20));

        // Reserve button
        JButton reserveButton = new JButton("Rezerwuj");
        reserveButton.setBackground(new Color(186, 85, 211));
        reserveButton.setForeground(Color.WHITE);
        reserveButton.setPreferredSize(new Dimension(200, 40));
        reserveButton.setMaximumSize(new Dimension(200, 40));
        reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        reserveButton.setEnabled(false); // Initially disabled

        panel.add(reserveButton);

        return panel;
    }
}
