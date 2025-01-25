package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class PanelsContainer {

    private final JPanel container;
    private final CardLayout layout;
    private final HashMap<PanelId, JPanel> panels = new HashMap<>();

    public PanelsContainer(JPanel container, CardLayout layout,
            List<Panel> panels) {
        if (!areParamsValid(panels, container, layout)) {
            throw new IllegalArgumentException("Illegal values passed " +
                    "to PanelContainer constructor");
        }
        this.container = container;
        this.layout = layout;
        setLayout();
        assignPanels(panels);
    }

    public JPanel get() {
        return container;
    }

    public JPanel getById(PanelId panelId) {
        if (!panels.containsKey(panelId)) {
            throw new NoSuchElementException("No panel found " +
                    "in PanelContainer of id: " + panelId);
        }
        return panels.get(panelId);
    }

    public void setStartPanel(PanelId panelId) {
        if (!panels.containsKey(panelId)) {
            throw new NoSuchElementException("No panel found " +
                    "in PanelContainer of id: " + panelId);
        }
        layout.show(container, panelId.getValue());
    }

    private void setLayout() {
        container.setLayout(layout);
    }

    private void assignPanels(List<Panel> panels) {
        for (Panel panel : panels) {
            JPanel currentPanel = panel.get();
            PanelId panelId = panel.getId();
            this.container.add(currentPanel, panelId.getValue());
            this.panels.put(panelId, currentPanel);
        }
    }

    private boolean areParamsValid(List<Panel> panels,
            JPanel container, CardLayout layout) {
        return  arePanelsValid(panels) && isContainerValid(container) &&
                isCardLayoutValid(layout);
    }

    private boolean arePanelsValid(List<Panel> panels) {
        if (panels == null || areAnyNullPanels(panels)) {
            return false;
        }
        List<PanelId> allIds = getAllPanelsIds(panels);
        return areAllPanelsIdsUnique(allIds);
    }

    private List<PanelId> getAllPanelsIds(List<Panel> panels) {
        List<PanelId> allIds = new ArrayList<>();

        for (Panel panel : panels) {
            PanelId panelId = panel.getId();
            allIds.add(panelId);
        }
        return allIds;
    }

    private boolean areAnyNullPanels(List<Panel> panels) {
        return panels.contains(null);
    }

    private boolean areAllPanelsIdsUnique(List<PanelId> allIds) {
        return allIds.size() == allIds.stream().distinct().count();
    }

    private boolean isContainerValid(JPanel container) {
        return container != null;
    }

    private boolean isCardLayoutValid(CardLayout layout) {
        return layout != null;
    }


}
