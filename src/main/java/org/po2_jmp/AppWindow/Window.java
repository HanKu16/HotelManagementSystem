package org.po2_jmp.AppWindow;

import lombok.Getter;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

@Getter
public class Window extends JFrame {

    private final WindowSettings settings;
    private final HashMap<String, JPanel> panels;
    private static final JPanel panelContainer = new JPanel();
    private static final CardLayout cardLayout = new CardLayout();

    public Window(WindowSettings settings, HashMap<String, JPanel> panels) {
        if (!areSettingsValid(settings) || ! arePanelsValid(panels)) {
            throw new IllegalArgumentException("Illegal values passed " +
                    "to constructor\nsettings: " + settings + "\n" +
                    "panels: " + panels);
        }
        this.settings = settings;
        this.panels = panels;
        configure();
    }

    public static void switchToPanel(String panelId) {
        if (panelId != null) {
            cardLayout.show(panelContainer, panelId);
        }
    }

    private void configure() {
        setImmutableProperties();
        applySettings(settings);
        setLayout();
        assignPanelsToContainer();
        assignContainer();
        setStartingPanel(settings.getStartPanelId());
    }

    private void setImmutableProperties() {
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void applySettings(WindowSettings settings) {
        this.setTitle(settings.getTitle().getValue());
        this.setSize(settings.getDimensions().getWidth(),
                settings.getDimensions().getHeight());
    }

    private void setLayout() {
        panelContainer.setLayout(cardLayout);
    }

    private void assignPanelsToContainer() {
        for (String panelId : panels.keySet()) {
            panelContainer.add(panels.get(panelId), panelId);
        }
    }

    private void assignContainer() {
        this.add(panelContainer);
    }

    private void setStartingPanel(String panelId) {
        cardLayout.show(panelContainer, panelId);
    }

    private boolean areSettingsValid(WindowSettings settings) {
        return settings != null;
    }

    private boolean arePanelsValid(HashMap<String, JPanel> panels) {
        return (panels != null) && !panels.containsValue(null);
    }

}



