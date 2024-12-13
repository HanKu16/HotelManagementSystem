package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;

public class FirstPanel implements Panel {

    private final PanelId id;
    private final JPanel panel;

    public FirstPanel(PanelId id, CardLayout layout, JPanel container) {
        this.id = id;
        this.panel = createFirstPanel(layout, container);
    }

    @Override
    public PanelId getId() {
        return id;
    }

    @Override
    public JPanel get() {
        return panel;
    }

    private JPanel createFirstPanel(CardLayout layout, JPanel container) {
        JPanel firstPanel = new JPanel(new BorderLayout());

        Panel headerPanel = new FirstHeaderPanel(new PanelId("Header"), layout, container);
        JPanel header = headerPanel.get();

        firstPanel.add(header, BorderLayout.NORTH);

        return firstPanel;
    }
}
