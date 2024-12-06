package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;

public class PanelThree implements Panel {

    private final PanelId id;
    private final JPanel panel;

    public PanelThree(PanelId id, CardLayout layout, JPanel container) {
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
        JPanel panelThree = new JPanel();
        JLabel label = new JLabel("Panel Three");
        panelThree.add(label);
        JButton buttonOne = new JButton("Switch to first panel");
        buttonOne.addActionListener(
                e -> layout.show(container, "1"));
        panelThree.add(buttonOne);
        JButton buttonTwo = new JButton("Switch to second panel");
        buttonTwo.addActionListener(e -> layout.show(container, "2"));
        panelThree.add(buttonTwo);
        panelThree.setBackground(Color.BLUE);
        return panelThree;
    }
}
