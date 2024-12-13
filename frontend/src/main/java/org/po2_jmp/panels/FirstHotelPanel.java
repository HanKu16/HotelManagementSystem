package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;

public class FirstHotelPanel implements Panel{

    private final PanelId id;
    private final JPanel panel;
    public FirstHotelPanel(PanelId id,CardLayout layout, JPanel container) {
        this.id = id;
        this.panel = createFirstHotelPanel(layout, container);
    }
    @Override
    public PanelId getId() {
        return id;
    }

    @Override
    public JPanel get() {
        return panel;
    }
    private static JPanel createFirstHotelPanel(CardLayout layout, JPanel container) {

        JPanel firstHotelPanel = new JPanel();
        firstHotelPanel.setLayout(new BorderLayout());
        firstHotelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left: Image
        JLabel imageLabel = new JLabel("Grafika", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Center: Hotel information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel locationLabel = new JLabel("Lokalizacja: przykładowa ulica 1, miasto, kod-pocztowy");
        JLabel amenitiesLabel = new JLabel("<html>Dostępne udogodnienia:<br>- wifi<br>- basen<br>- siłownia</html>");
        infoPanel.add(locationLabel);
        infoPanel.add(amenitiesLabel);

        // Right: Rating
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        JLabel ratingLabel = new JLabel("5.0", SwingConstants.CENTER);
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ratingLabel.setForeground(Color.ORANGE);
        JButton chooseButton = new JButton("Wybierz");
        chooseButton.setBackground(new Color(128, 0, 128));
        chooseButton.setForeground(Color.WHITE);

        actionPanel.add(Box.createVerticalGlue());
        actionPanel.add(ratingLabel);
        actionPanel.add(Box.createVerticalStrut(10));
        actionPanel.add(chooseButton);
        actionPanel.add(Box.createVerticalGlue());

        firstHotelPanel.add(imageLabel, BorderLayout.WEST);
        firstHotelPanel.add(infoPanel, BorderLayout.CENTER);
        firstHotelPanel.add(actionPanel, BorderLayout.EAST);

        return firstHotelPanel;
    }
}
