package org.po2_jmp.panels;

import org.po2_jmp.websocket.MyWebSocketHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanelCreator {
    private MyWebSocketHandler myWebSocketHandler;

    public MenuPanelCreator(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    public JPanel create(CardLayout layout, JPanel container) {
        JPanel panel = initializePanel();
        panel.add(createTitlePanel());
        panel.add(Box.createVerticalStrut(20));
        panel.add(createButtonPanel(layout, container));
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

    private JPanel createButtonPanel(CardLayout layout, JPanel container) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton reservationsButton = new JButton("Rezerwacje");
        reservationsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        reservationsButton.setForeground(Color.WHITE);
        reservationsButton.setBackground(new Color(128, 0, 128));
        reservationsButton.setFocusPainted(false);
        reservationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationsPanelCreator reservationsPanelCreator = new ReservationsPanelCreator(myWebSocketHandler);
                reservationsPanelCreator.create(layout, container);
            }
        });

        JButton hotelListButton = new JButton("Lista hoteli");
        hotelListButton.setFont(new Font("Arial", Font.PLAIN, 16));
        hotelListButton.setForeground(Color.WHITE);
        hotelListButton.setBackground(new Color(128, 0, 128));
        hotelListButton.setFocusPainted(false);
        hotelListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HotelListPanelCreator hotelListPanelCreator = new HotelListPanelCreator(myWebSocketHandler);
                hotelListPanelCreator.create(layout, container);
            }
        });

        buttonPanel.add(reservationsButton);
        buttonPanel.add(hotelListButton);

        return buttonPanel;
    }
}
