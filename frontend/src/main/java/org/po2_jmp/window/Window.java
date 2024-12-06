package org.po2_jmp.window;

import lombok.Getter;
import org.po2_jmp.panels.PanelsContainer;
import javax.swing.*;

@Getter
public class Window extends JFrame {

    private final WindowSettings settings;
    private final PanelsContainer container;

    public Window(WindowSettings settings, PanelsContainer container) {
        if (!areParamsValid(settings, container)) {
            throw new IllegalArgumentException("Illegal values passed " +
                    "to Window constructor");
        }
        this.settings = settings;
        this.container = container;
        configure(container);
    }

    private void configure(PanelsContainer container) {
        setImmutableProperties();
        applySettings(settings);
        assignContainer(container);
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
        this.container.setStartPanel(settings.getStartPanelId());
    }

    private void assignContainer(PanelsContainer container) {
        this.add(container.get());
    }

    private boolean areParamsValid(WindowSettings settings, PanelsContainer container) {
        return areSettingsValid(settings) && isContainerValid(container);
    }

    private boolean areSettingsValid(WindowSettings settings) {
        return settings != null;
    }

    private boolean isContainerValid(PanelsContainer container) {
        return container != null;
    }

}



