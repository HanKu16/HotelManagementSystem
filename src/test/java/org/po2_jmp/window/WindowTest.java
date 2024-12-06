package org.po2_jmp.AppWindow;

import org.junit.jupiter.api.Test;
import org.po2_jmp.Panels.Panel;
import org.po2_jmp.Panels.PanelId;
import static org.junit.jupiter.api.Assertions.*;
import org.po2_jmp.Panels.PanelsContainer;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

class WindowTest {

    private final WindowSettings validSettings = createValidWindowSettings();
    private final PanelsContainer validContainer = createValidContainer();

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenSettingsAreNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Window(null, validContainer);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenContainerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Window(validSettings, null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenArgumentsAreValid() {
        Window window = new Window(validSettings, validContainer);
        assertNotNull(window);
    }

    private WindowSettings createValidWindowSettings() {
        return new WindowSettings(new WindowDimensions(800, 600),
                new WindowTitle("ValidTitle"), new PanelId("3"));
    }

    private PanelsContainer createValidContainer() {
        List<Panel> panels = createValidPanels();
        return new PanelsContainer(new JPanel(),
                new CardLayout(), panels);
    }

    private List<Panel> createValidPanels() {
        List<Panel> panels = new ArrayList<>();
        panels.add(new Panel() {
            @Override
            public PanelId getId() {
                return new PanelId("1");
            }

            @Override
            public JPanel get() {
                return new JPanel();
            }
        });
        panels.add(new Panel() {
            @Override
            public PanelId getId() {
                return new PanelId("2");
            }

            @Override
            public JPanel get() {
                return new JPanel();
            }
        });
        panels.add(new Panel() {
            @Override
            public PanelId getId() {
                return new PanelId("3");
            }

            @Override
            public JPanel get() {
                return new JPanel();
            }
        });
        return panels;
    }

}