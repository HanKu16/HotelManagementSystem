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

    private WindowSettings createWindowSettings() {
        return new WindowSettings(
                new WindowDimensions(800, 600),
                new WindowTitle("Hotel Management System"),
                new PanelId("First"));
    }

    private List<Panel> createPanels(CardLayout cardLayout, JPanel container) {
        List<Panel> panels = new ArrayList<>();
        Panel panelOne = new PanelOne(
                new PanelId("1"), cardLayout, container);
        Panel panelTwo = new PanelTwo(
                new PanelId("2"), cardLayout, container);
        Panel panelThree = new PanelThree(
                new PanelId("3"), cardLayout, container);
        Panel loginPanel = new LoginPanel(
                new PanelId("Login"), cardLayout, container);
        Panel navigationPanel = new NavigationPanel(
                new PanelId("Navigation"), cardLayout, container);
        Panel roomsPanel = new RoomsPanel(
                new PanelId("Rooms"), cardLayout, container);
        Panel firstHeaderPanel = new FirstHeaderPanel(
                new PanelId("FirstHeader"), cardLayout, container);
        Panel firstHotelPanel = new FirstHotelPanel(
                new PanelId("FirstHotel"), cardLayout, container);
        Panel firstPanel = new FirstPanel(
                new PanelId("First"), cardLayout, container);
        panels.add(panelOne);
        panels.add(panelTwo);
        panels.add(panelThree);
        panels.add(loginPanel);
        panels.add(navigationPanel);
        panels.add(roomsPanel);
        panels.add(firstHeaderPanel);
        panels.add(firstHotelPanel);
        panels.add(firstPanel);
        return panels;
    }

}