package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;

public class PanelTwo implements Panel {

    private final PanelId id;
    private final JPanel panel;

    public PanelTwo(PanelId id, CardLayout layout, JPanel container) {
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
        JPanel panelSecond = new JPanel();
        JLabel label = new JLabel("Panel Second");
        panelSecond.add(label);
        JButton buttonOne = new JButton("Switch to first panel");
        buttonOne.addActionListener(
                e -> layout.show(container, "1"));
        panelSecond.add(buttonOne);
        JButton buttonThree = new JButton("Switch to third panel");
        buttonThree.addActionListener(
                e -> layout.show(container, "3"));
        panelSecond.add(buttonThree);
        panelSecond.setBackground(Color.GREEN);
        return panelSecond;
    }

}
