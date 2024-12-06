package org.po2_jmp.panels;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class PanelsContainerTest {

    private final JPanel validContainer = new JPanel();
    private final CardLayout validLayout = new CardLayout();
    private final List<Panel> validPanels = createValidPanels();

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPanelsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PanelsContainer(validContainer, validLayout, null);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenAnyPanelInPanelsIsNull() {
        List<Panel> panelsWithNull = createPanelsWithNull();
        assertThrows(IllegalArgumentException.class, () -> {
           new PanelsContainer(validContainer, validLayout, panelsWithNull);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPanelIdsAreNotUnique() {
        List<Panel> panelsWithNotUniquePanelIds = createPanelsWithNotUniquePanelIds();
        assertThrows(IllegalArgumentException.class, () -> {
            new PanelsContainer(validContainer, validLayout,
                    panelsWithNotUniquePanelIds);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenContainerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PanelsContainer(null, validLayout, validPanels);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenLayoutIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PanelsContainer(validContainer, null, validPanels);
        });
    }

    @Test
    void Constructor_ShouldCreateObjects_WhenArgumentsAreValid() {
        PanelsContainer panelsContainer = new PanelsContainer(
                validContainer, validLayout, validPanels);
    }

    @Test
    void Get_ShouldReturnContainerPassedByConstructor() {
        PanelsContainer panelsContainer = new PanelsContainer(
                validContainer, validLayout, validPanels);
        assertSame(this.validContainer, panelsContainer.get());
    }

    @Test
    void GetById_ShouldReturnPanel_WhenPassedIdOfExistingPanel() {
        PanelsContainer panelsContainer = new PanelsContainer(
                validContainer, validLayout, validPanels);
        PanelId panelId = new PanelId("2");
        assertNotNull(panelsContainer.getById(panelId));
    }

    @Test
    void GetById_ShouldThrowNoSuchElementException_WhenPassedIdOfNonExistingPanel() {
        PanelsContainer panelsContainer = new PanelsContainer(
                validContainer, validLayout, validPanels);
        PanelId panelId = new PanelId("10");
        assertThrows(NoSuchElementException.class, () -> {
            panelsContainer.getById(panelId);
        });
    }

    @Test
    void SetStartPanel_ShouldThrowNoSuchElementException_WhenPassedIdOfNonExistingPanel() {
        PanelsContainer panelsContainer = new PanelsContainer(
                validContainer, validLayout, validPanels);
        PanelId panelId = new PanelId("5");
        assertThrows(NoSuchElementException.class, () -> {
            panelsContainer.setStartPanel(panelId);
        });
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

    private List<Panel> createPanelsWithNull() {
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
        panels.add(null);
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

    private List<Panel> createPanelsWithNotUniquePanelIds() {
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
                return new PanelId("2");
            }

            @Override
            public JPanel get() {
                return new JPanel();
            }
        });
        return panels;
    }

}
