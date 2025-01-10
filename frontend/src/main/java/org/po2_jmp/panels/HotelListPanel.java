package org.po2_jmp.panels;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.po2_jmp.request.HotelsOverviewsRequest;
import org.po2_jmp.request.UserAuthenticationRequest;
import org.po2_jmp.websocket.MyWebSocketHandler;
import org.po2_jmp.websocket.JsonUtils;

import javax.swing.*;
import java.awt.*;

public class HotelListPanel implements Panel {

    private final PanelId id;
    private final JPanel panel;
    MyWebSocketHandler myWebSocketHandler;
    JsonUtils jsonUtils;

    public HotelListPanel(PanelId id, CardLayout layout, JPanel container, MyWebSocketHandler myWebSocketHandler) {
        this.id = id;
        this.panel = create(layout, container);
        this.myWebSocketHandler = myWebSocketHandler;
    }

    @Override
    public PanelId getId() {
        return id;
    }

    @Override
    public JPanel get() {
        return panel;
    }

    private JPanel create(CardLayout layout, JPanel container) {
        // Main panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sieć Hoteli Akropol", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(128, 0, 128));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        HotelsOverviewsRequest hotelsOverviewsRequest = new HotelsOverviewsRequest("getHotelsOverviews");
        try {
            assert jsonUtils != null;
            String request = jsonUtils.serialize(hotelsOverviewsRequest);
            assert myWebSocketHandler != null;
            myWebSocketHandler.sendMessage(request);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        for(int i = 0; i < 4; i++){
            int hotelId = i + 1;


            String hotelName;
            String address;
            String amenities;
            panel.add(createHotelPanel(hotelName, address, amenities, layout, container));
        }
        panel.add(createHotelPanel("Hotel A", "Ulica Przykładowa 1, Miasto, 00-001", "- WiFi\n- Basen\n- Siłownia", layout, container));
        panel.add(createHotelPanel("Hotel B", "Ulica Druga 2, Miasto, 00-002", "- Darmowe Śniadanie\n- Parking\n- Spa", layout, container));
        panel.add(createHotelPanel("Hotel C", "Ulica Trzecia 3, Miasto, 00-003", "- WiFi\n- Restauracja\n- Sala konferencyjna", layout, container));
        panel.add(createHotelPanel("Hotel D", "Ulica Czwarta 4, Miasto, 00-004", "- Basen\n- Siłownia\n- Sauna", layout, container));
        panel.add(createHotelPanel("Hotel E", "Ulica Piąta 5, Miasto, 00-005", "- Darmowy transport\n- WiFi\n- Fitness", layout, container));

        return panel;
    }

    private JPanel createHotelPanel(String hotelName, String address, String amenities, CardLayout layout, JPanel container) {
        JPanel hotelPanel = new JPanel();
        hotelPanel.setLayout(new GridBagLayout());
        hotelPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        hotelPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel(hotelName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        hotelPanel.add(nameLabel, gbc);

        gbc.gridy = 1;
        JLabel addressLabel = new JLabel("Lokalizacja: " + address);
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        hotelPanel.add(addressLabel, gbc);

        gbc.gridy = 2;
        JTextArea amenitiesArea = new JTextArea(amenities);
        amenitiesArea.setFont(new Font("Arial", Font.PLAIN, 12));
        amenitiesArea.setEditable(false);
        amenitiesArea.setBackground(Color.WHITE);
        amenitiesArea.setBorder(BorderFactory.createEmptyBorder());
        hotelPanel.add(amenitiesArea, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton selectButton = new JButton("Wybierz");
        selectButton.setBackground(new Color(128, 0, 128));
        selectButton.setForeground(Color.WHITE);
        selectButton.addActionListener(e -> layout.show(container, "HotelDetails"));
        hotelPanel.add(selectButton, gbc);

        return hotelPanel;
    }
}
