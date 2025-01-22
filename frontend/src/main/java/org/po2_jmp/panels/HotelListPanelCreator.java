package org.po2_jmp.panels;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.jetty.client.Origin;
import org.po2_jmp.request.HotelsOverviewsRequest;
import org.po2_jmp.response.AddressDto;
import org.po2_jmp.response.HotelOverviewDto;
import org.po2_jmp.response.HotelProfileResponse;
import org.po2_jmp.response.HotelsOverviewsResponse;
import org.po2_jmp.websocket.JsonUtils;
import org.po2_jmp.websocket.MyWebSocketHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HotelListPanelCreator {
    private HotelsOverviewsResponse result;
    private MyWebSocketHandler myWebSocketHandler;
    private JsonUtils jsonUtils = new JsonUtils();

    public HotelListPanelCreator(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    public void create(CardLayout layout, JPanel container) {
        handleHotelList(layout, container); // Uruchomienie asynchronicznego zadania
    }

    private void handleHotelList(CardLayout layout, JPanel container) {
        HotelsOverviewsRequest overviewsRequest = new HotelsOverviewsRequest("getHotelsOverviews");
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String request = jsonUtils.serialize(overviewsRequest);
                    myWebSocketHandler.sendMessage(request);

                    String response = null;
                    long timeout = System.currentTimeMillis() + 10000;
                    while ((response == null || response.isEmpty()) && System.currentTimeMillis() < timeout) {
                        response = myWebSocketHandler.getResponse();
                    }
                    if (response == null || response.isEmpty()) {
                        throw new RuntimeException("Timeout while waiting for response or received empty response");
                    }

                    try {
                        result = handleResponse(response);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error processing response: " + ex.getMessage());
                        ex.printStackTrace();
                    }

                    result = handleResponse(response); // Przypisanie wyniku odpowiedzi

                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }

            @Override
            protected void done() {
                // Wykonaj operacje na GUI po zakończeniu zadania
                SwingUtilities.invokeLater(() -> {
                    if (result != null) {
                        JPanel panel = initializePanel();
                        JLabel titleLabel = new JLabel("Nasze hotele:", SwingConstants.LEFT);
                        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
                        titleLabel.setForeground(Color.BLACK);
                        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panel.add(titleLabel);
                        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        backButtonPanel.setBackground(Color.WHITE);

                        JButton backButton = new JButton("Cofnij");
                        backButton.setBackground(new Color(192, 192, 192));
                        backButton.setForeground(Color.BLACK);
                        backButton.addActionListener(e -> layout.show(container, "menuPanel"));
                        backButtonPanel.add(backButton);

                        panel.add(backButtonPanel);


                        for (HotelOverviewDto hotel : result.getHotels()) {
                            panel.add(createHotelPanel(hotel.getHotelId(),hotel.getName(), FormatAddress(hotel.getAddress()), FormatAmenities(hotel.getAmenities()), layout, container));
                        }

                        container.add(panel, "hotelList");
                        layout.show(container, "hotelList");
                        System.out.println("Panel HotelList został aktywowany.");
                    } else {
                        // Obsługa przypadku, gdy wynik jest null
                        JOptionPane.showMessageDialog(null, "Nie udało się pobrać listy hoteli.");
                    }
                });
            }
        };
        worker.execute();
    }

    private JPanel initializePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);


        return panel;
    }

    private JPanel createHotelPanel(int hotelId, String hotelName, String address, String amenities, CardLayout layout, JPanel container) {
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
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HotelProfilePanelCreator hotelProfilePanelCreator = new HotelProfilePanelCreator(myWebSocketHandler);
                hotelProfilePanelCreator.create(layout, container, hotelId);
            }
        });
        hotelPanel.add(selectButton, gbc);

        return hotelPanel;
    }

    private HotelsOverviewsResponse handleResponse(String response) {
        HotelsOverviewsResponse ovsResponse = null;
        try {
            System.out.println(response);
            ovsResponse = jsonUtils.deserialize(response, HotelsOverviewsResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ovsResponse;
    }

    private String FormatAddress(AddressDto address) {
        return address.getCity() + " " + address.getStreet() + " " + address.getPostalCode() + " " + address.getBuildingNumber();
    }

    private String FormatAmenities(List<String> amenities) {
        return String.join(", ", amenities);
    }
}
