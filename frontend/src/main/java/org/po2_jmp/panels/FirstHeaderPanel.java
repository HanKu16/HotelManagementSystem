package org.po2_jmp.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FirstHeaderPanel implements Panel {

    private final PanelId id;
    private final JPanel panel;
    private final List<Panel> hotelPanels; // Przechowywanie referencji do FirstHotelPanel

    public FirstHeaderPanel(PanelId id, CardLayout layout, JPanel container) {
        this.id = id;
        this.hotelPanels = new ArrayList<>();
        this.panel = createHeaderPanel(layout, container);
    }

    @Override
    public PanelId getId() {
        return id;
    }

    @Override
    public JPanel get() {
        return panel;
    }

    private JPanel createHeaderPanel(CardLayout layout, JPanel container) {

        JPanel headerPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel headerLabel = new JLabel("Sieć Hoteli Akropol");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(128, 0, 128));

        headerTopPanel.add(headerLabel);

        headerPanel.add(headerTopPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Adding for hotel panels
        for (int i = 1; i <= 4; i++) {
            PanelId hotelId = new PanelId("Hotel_" + i);
            FirstHotelPanel hotelPanel = new FirstHotelPanel(hotelId, layout, container);
            hotelPanels.add(hotelPanel);
            contentPanel.add(hotelPanel.get());
        }

        //TODO: czemu to nie dziala
        // Scrolling for the hotel list
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        headerPanel.add(scrollPane, BorderLayout.CENTER);

        return headerPanel;
    }
}
