package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel implements Panel{
    private final PanelId id;
    private final JPanel panel;
    public NavigationPanel(PanelId id, CardLayout layout, JPanel container) {
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
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new GridLayout(1, 3));
        JButton buttonRooms = createRoomsButton();
        JButton buttonReservations = createReservationsButton();
        JButton buttonGuests = createGuestsButton();

        navigationPanel.add(buttonRooms);
        navigationPanel.add(buttonReservations);
        navigationPanel.add(buttonGuests);
        return navigationPanel;
    }

    private JButton createRoomsButton() {
        JButton buttonRooms = new JButton("Pokoje");
        return buttonRooms;
    }

    private JButton createReservationsButton() {
        JButton buttonReservations = new JButton("Rezerwacje");
        return buttonReservations;
    }
    private JButton createGuestsButton() {
        JButton buttonGuests = new JButton("Goście");
        return buttonGuests;
    }


}
