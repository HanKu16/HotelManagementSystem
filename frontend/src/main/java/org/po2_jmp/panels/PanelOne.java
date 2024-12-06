package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;

public class PanelOne implements Panel {

    private final PanelId id;
    private final JPanel panel;

    public PanelOne(PanelId id, CardLayout layout, JPanel container) {
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
        JPanel panelFirst = new JPanel();
        JLabel label = new JLabel("Panel One");
        panelFirst.add(label);
        JButton buttonTwo = new JButton("Switch to second panel");
        buttonTwo.addActionListener(
                e -> layout.show(container, "2"));
        panelFirst.add(buttonTwo);
        JButton buttonThree = new JButton("Switch to third panel");
        buttonThree.addActionListener(
                e -> layout.show(container, "3"));
        panelFirst.add(buttonThree);
        panelFirst.setBackground(Color.RED);
        return panelFirst;
    }

}
