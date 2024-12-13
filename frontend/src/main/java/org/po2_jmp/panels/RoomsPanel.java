package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;

public class RoomsPanel implements Panel{
    private final PanelId id;
    private final JPanel panel;

    public RoomsPanel(PanelId id, CardLayout layout, JPanel container) {
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
        JPanel roomsPanel = new JPanel();
        roomsPanel.add(new JLabel("Zarządzanie rezerwacjami"));
        roomsPanel.add(new JTextField("Wyszukaj rezerwację", 20));
        return roomsPanel;
    }

}
