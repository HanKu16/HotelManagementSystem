package org.po2_jmp.AppWindow;

import lombok.Getter;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

@Getter
public class Window extends JFrame {

    private final WindowSettings settings;
    private final HashMap<String, JPanel> panels;
    private final JPanel container;
    private final CardLayout cardLayout;

    public Window(WindowSettings settings, HashMap<String, JPanel> panels,
                  JPanel container, CardLayout cardLayout) {
        if (!areParamsValid(settings, panels, container, cardLayout)) {
            throw new IllegalArgumentException("Illegal values passed " +
                    "to constructor");
        }
        this.settings = settings;
        this.panels = panels;
        this.container = container;
        this.cardLayout = cardLayout;
        configure();
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
        container.setLayout(cardLayout);
    }

    private void assignPanelsToContainer() {
        for (String panelId : panels.keySet()) {
            container.add(panels.get(panelId), panelId);
        }
    }

    private void assignContainer() {
        this.add(container);
    }

    private void setStartingPanel(String panelId) {
        cardLayout.show(container, panelId);
    }

    private boolean areParamsValid(WindowSettings settings, HashMap<String,
            JPanel> panels, JPanel container, CardLayout cardLayout) {
        return areSettingsValid(settings) && arePanelsValid(panels) &&
                isContainerValid(container) && isCardLayoutValid(cardLayout);
    }

    private boolean areSettingsValid(WindowSettings settings) {
        return settings != null;
    }

    private boolean arePanelsValid(HashMap<String, JPanel> panels) {
        return (panels != null) && !panels.containsValue(null);
    }

    private boolean isContainerValid(JPanel container) {
        return container != null;
    }

    private boolean isCardLayoutValid(CardLayout cardLayout) {
        return cardLayout != null;
    }

}



