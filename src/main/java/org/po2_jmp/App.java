package org.po2_jmp;

import org.po2_jmp.AppWindow.*;
import org.po2_jmp.AppWindow.Window;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class App implements Runnable {

    @Override
    public void run() {
        try {
            WindowSettings settings = createWindowSettings();
            JPanel container = new JPanel();
            CardLayout cardLayout = new CardLayout();
            HashMap<String, JPanel> panels = createPanels(cardLayout, container);
            Window window = new Window(settings, panels,
                    container, cardLayout);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public WindowSettings createWindowSettings() {
        return new WindowSettings(
                new WindowDimensions(800, 600),
                new WindowTitle("Hotel Management System"),
                "1");
    }

    private HashMap<String, JPanel> createPanels(CardLayout cardLayout, JPanel container) {
        HashMap<String, JPanel> panels = new HashMap<>();
        panels.put("1", createPanelOne(cardLayout, container));
        panels.put("2", createPanelTwo(cardLayout, container));
        panels.put("3", createPanelThree(cardLayout, container));
        return panels;
    }

    private JPanel createPanelOne(CardLayout cardLayout, JPanel container) {
        JPanel panelFirst = new JPanel();
        JLabel label = new JLabel("Panel One");
        panelFirst.add(label);
        JButton buttonTwo = new JButton("Switch to second panel");
        buttonTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "2");

            }
        });
        panelFirst.add(buttonTwo);
        JButton buttonThree = new JButton("Switch to third panel");
        buttonThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "3");
            }
        });
        panelFirst.add(buttonThree);
        panelFirst.setBackground(Color.RED);
        return panelFirst;
    }

    private JPanel createPanelTwo(CardLayout cardLayout, JPanel container) {
        JPanel panelSecond = new JPanel();
        JLabel label = new JLabel("Panel Second");
        panelSecond.add(label);
        JButton buttonOne = new JButton("Switch to first panel");
        buttonOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "1");
            }
        });
        panelSecond.add(buttonOne);
        JButton buttonThree = new JButton("Switch to third panel");
        buttonThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "3");
            }
        });
        panelSecond.add(buttonThree);
        panelSecond.setBackground(Color.GREEN);
        return panelSecond;
    }

    private JPanel createPanelThree(CardLayout cardLayout, JPanel container) {
        JPanel panelThree = new JPanel();
        JLabel label = new JLabel("Panel Three");
        panelThree.add(label);
        JButton buttonOne = new JButton("Switch to first panel");
        buttonOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "1");
            }
        });
        panelThree.add(buttonOne);
        JButton buttonTwo = new JButton("Switch to second panel");
        buttonTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "2");
            }
        });
        panelThree.add(buttonTwo);
        panelThree.setBackground(Color.BLUE);
        return panelThree;
    }

}