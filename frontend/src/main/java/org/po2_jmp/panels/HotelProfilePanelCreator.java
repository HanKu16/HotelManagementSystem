package org.po2_jmp.panels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toedter.calendar.JDateChooser;
import org.po2_jmp.FrontendApp;
import org.po2_jmp.request.HotelProfileRequest;
import org.po2_jmp.request.ReservationCreationRequest;
import org.po2_jmp.response.AddressDto;
import org.po2_jmp.response.HotelProfileResponse;
import org.po2_jmp.response.ReservationCreationResponse;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.websocket.JsonUtils;
import org.po2_jmp.websocket.MyWebSocketHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class HotelProfilePanelCreator {
    private HotelProfileResponse profileResult;
    private ReservationCreationResponse reservationResult;
    private final MyWebSocketHandler myWebSocketHandler;
    private final JsonUtils jsonUtils = new JsonUtils();

    public HotelProfilePanelCreator(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    public void create(CardLayout layout, JPanel container, int hotelId) {
        fetchHotelProfile(layout, container, hotelId);
    }

    private JPanel initializeMainPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(186, 85, 211));
        return panel;
    }

    private JPanel createTitlePanel(String title) {
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(186, 85, 211));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        return titlePanel;
    }

    private JPanel createHotelInfoPanel(String hotelName, String address, String amenities, String description) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        addLabelToPanel(panel, hotelName, new Font("Arial", Font.BOLD, 18), gbc, 0, 0);

        addLabelToPanel(panel, address, new Font("Arial", Font.PLAIN, 14), gbc, 0, 1, new Color(255, 20, 147));

        addTextAreaToPanel(panel, amenities, gbc, 0, 2, 2);

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setForeground(new Color(186, 85, 211));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(scrollPane, gbc);

        return panel;
    }


    private void addLabelToPanel(JPanel panel, String text, Font font, GridBagConstraints gbc, int x, int y) {
        addLabelToPanel(panel, text, font, gbc, x, y, null);
    }

    private void addLabelToPanel(JPanel panel, String text, Font font, GridBagConstraints gbc, int x, int y, Color foreground) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        if (foreground != null) label.setForeground(foreground);
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(label, gbc);
    }

    private void addTextAreaToPanel(JPanel panel, String text, GridBagConstraints gbc, int x, int y, int width) {
        JTextArea textArea = new JTextArea(text);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(new Color(186, 85, 211));
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        panel.add(textArea, gbc);
    }

    private JPanel createOptionsPanel(List<Integer> guestCapacities, JDateChooser dateChooser) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        addLabelToPanel(panel, "Liczba osób:", new Font("Arial", Font.PLAIN, 14), gbc, 0, 0);
        JComboBox<Integer> peopleComboBox = new JComboBox<>(guestCapacities.toArray(new Integer[0]));
        gbc.gridx = 1;
        panel.add(peopleComboBox, gbc);

        addLabelToPanel(panel, "Wybierz datę:", new Font("Arial", Font.PLAIN, 14), gbc, 0, 1);
        gbc.gridx = 1;
        panel.add(dateChooser, gbc);

        return panel;
    }

    private JPanel createButtonPanel(CardLayout layout, JPanel container, int hotelId, JComboBox<Integer> peopleComboBox, JDateChooser dateChooser) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(255, 182, 193));

        JButton backButton = createButton("Cofnij", new Color(255, 182, 193), e -> layout.show(container, "hotelList"));
        JButton reserveButton = createButton("Rezerwuj", new Color(255, 182, 193), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dateChooser.getDate() != null)
                    handleReservation(layout, container, createReservationRequest(hotelId, peopleComboBox, dateChooser));
                else
                    JOptionPane.showMessageDialog(null, "Proszę zaznaczyć datę");
            }
        });
        panel.add(backButton);
        panel.add(reserveButton);

        return panel;
    }

    private JButton createButton(String text, Color background, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        return button;
    }

    private ReservationCreationRequest createReservationRequest(int hotelId, JComboBox<Integer> peopleComboBox, JDateChooser dateChooser) {
        return new ReservationCreationRequest(
                "createReservation",
                FrontendApp.userId,
                convertToLocalDate(dateChooser.getDate()),
                hotelId,
                (Integer) peopleComboBox.getSelectedItem()
        );

    }

    private void fetchHotelProfile(CardLayout layout, JPanel container, int hotelId) {
        HotelProfileRequest request = new HotelProfileRequest("getHotelProfile", hotelId);
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String jsonRequest = jsonUtils.serialize(request);
                    myWebSocketHandler.sendMessage(jsonRequest);
                    profileResult = jsonUtils.deserialize(myWebSocketHandler.getResponse(), HotelProfileResponse.class);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Nie udało się pobrać danych hotelu:  " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                if (profileResult != null) {
                    JPanel panel = initializeMainPanel();

                    panel.add(createTitlePanel("Sieć Hoteli Akropol"), BorderLayout.NORTH);

                    JPanel hotelInfoPanel = createHotelInfoPanel(
                            profileResult.getName(),
                            formatAddress(profileResult.getAddress()),
                            formatAmenities(profileResult.getAmenities()),
                            profileResult.getDescription()
                    );
                    panel.add(hotelInfoPanel, BorderLayout.CENTER);


                    JDateChooser dateChooser = new JDateChooser();
                    JPanel optionsPanel = createOptionsPanel(profileResult.getGuestCapacities(), dateChooser);
                    panel.add(optionsPanel, BorderLayout.EAST);

                    JComboBox<Integer> peopleComboBox = new JComboBox<>(profileResult.getGuestCapacities().toArray(new Integer[0]));
                    JPanel buttonPanel = createButtonPanel(layout, container, hotelId, peopleComboBox, dateChooser);
                    panel.add(buttonPanel, BorderLayout.SOUTH);

                    container.add(panel, "hotelProfile");
                    layout.show(container, "hotelProfile");
                } else {
                    JOptionPane.showMessageDialog(null, "Nie udało się pobrać danych");
                }
            }
        };
        worker.execute();
    }

    private void handleReservation(CardLayout layout, JPanel container, ReservationCreationRequest request) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String jsonRequest = jsonUtils.serialize(request);
                    myWebSocketHandler.sendMessage(jsonRequest);
                    reservationResult = jsonUtils.deserialize(myWebSocketHandler.getResponse(), ReservationCreationResponse.class);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Nie udało się utworzyć rejestracji" + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                if (reservationResult.getStatus() == ResponseStatus.CREATED) {
                    JOptionPane.showMessageDialog(null, "Utworzono rejestrację", "", JOptionPane.INFORMATION_MESSAGE);
                    layout.show(container, "menuPanel");
                } else if(reservationResult.getStatus() == ResponseStatus.NOT_FOUND) {
                    JOptionPane.showMessageDialog(null, "Brak dostępnych pokojów w tym dniu");
                }
            }
        };
        worker.execute();
    }

    private String formatAddress(AddressDto address) {
        return address.getCity() + " " + address.getStreet() + " " + address.getPostalCode() + " " + address.getBuildingNumber();
    }

    private String formatAmenities(List<String> amenities) {
        return String.join(", ", amenities);
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
