package org.po2_jmp;

import org.po2_jmp.window.*;
import org.po2_jmp.window.Window;
import org.po2_jmp.panels.*;
import org.po2_jmp.panels.Panel;
import org.po2_jmp.panels.PanelsContainer;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FrontendApp implements Runnable {

    @Override
    public void run() {
        try {
            WindowSettings settings = createWindowSettings();
            JPanel container = new JPanel();
            CardLayout cardLayout = new CardLayout();
            List<Panel> panels = createPanels(cardLayout, container);
            PanelsContainer myContainer = new PanelsContainer(
                    container, cardLayout, panels);
            Window window = new Window(settings, myContainer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public WindowSettings createWindowSettings() {
        return new WindowSettings(
                new WindowDimensions(800, 600),
                new WindowTitle("Hotel Management System"),
                new PanelId("1"));
    }

    public List<Panel> createPanels(CardLayout cardLayout, JPanel container) {
        List<Panel> panels = new ArrayList<>();
        Panel panelOne = new PanelOne(
                new PanelId("1"), cardLayout, container);
        Panel panelTwo = new PanelTwo(
                new PanelId("2"), cardLayout, container);
        Panel panelThree = new PanelThree(
                new PanelId("3"), cardLayout, container);
        Panel loginPanel = new LoginPanel(
                new PanelId("Login"), cardLayout, container);
        panels.add(panelOne);
        panels.add(panelTwo);
        panels.add(panelThree);
        panels.add(loginPanel);
        return panels;
    }

}