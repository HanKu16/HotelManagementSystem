package org.po2_jmp.panels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toedter.calendar.JDateChooser;
import org.po2_jmp.FrontendApp;
import org.po2_jmp.request.HotelProfileRequest;
import org.po2_jmp.request.ReservationCreationRequest;
import org.po2_jmp.response.AddressDto;
import org.po2_jmp.response.HotelProfileResponse;
import org.po2_jmp.response.ReservationCreationResponse;
import org.po2_jmp.websocket.JsonUtils;
import org.po2_jmp.websocket.MyWebSocketHandler;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class HotelProfilePanelCreator {
    private HotelProfileResponse profileResult;
    private ReservationCreationResponse reservationResult;
    private MyWebSocketHandler myWebSocketHandler;
    private JsonUtils jsonUtils = new JsonUtils();
    public HotelProfilePanelCreator(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    public void create(CardLayout layout, JPanel container, int hotelId) {
        handleHotelProfile(layout, container, hotelId);
    }

    private JPanel initializePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    public JPanel createHotelProfilePanel(int hotelId, String hotelName, String description, String address, String amenities, List<Integer> guestCapacities , CardLayout layout, JPanel container) {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(186, 85, 211)); // Fioletowy kolor
        mainPanel.setLayout(new BorderLayout(10, 10)); // Odstępy między elementami

        // Górny panel z tytułem
        JLabel titleLabel = new JLabel("Sieć Hoteli Akropol", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Zwiększona czcionka
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel środkowy na dane hotelu
        JPanel hotelInfoPanel = new JPanel();
        hotelInfoPanel.setBackground(Color.WHITE);
        hotelInfoPanel.setLayout(new GridBagLayout());
        hotelInfoPanel.setPreferredSize(new Dimension(550, 250));
        mainPanel.add(hotelInfoPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // Zwiększone odstępy

        // Nazwa hotelu i ocena
        JLabel hotelNameLabel = new JLabel(hotelName, SwingConstants.LEFT);
        hotelNameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Zwiększona czcionka
        gbc.gridx = 0;
        gbc.gridy = 0;
        hotelInfoPanel.add(hotelNameLabel, gbc);

        // Lokalizacja
        JLabel locationLabel = new JLabel(address, SwingConstants.LEFT);
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        locationLabel.setForeground(new Color(255, 20, 147));
        gbc.gridx = 0;
        gbc.gridy = 1;
        hotelInfoPanel.add(locationLabel, gbc);

        // Mocne strony
        JTextArea strengthsText = new JTextArea(amenities);
        strengthsText.setFont(new Font("Arial", Font.PLAIN, 14));
        strengthsText.setEditable(false);
        strengthsText.setBackground(Color.WHITE);
        strengthsText.setForeground(new Color(186, 85, 211));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Zajmuje całą szerokość
        hotelInfoPanel.add(strengthsText, gbc);

        // Opis hotelu
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBackground(new Color(219, 112, 247)); // Jaśniejszy fiolet
        descriptionPanel.setLayout(new BorderLayout(10, 10));
        descriptionPanel.setPreferredSize(new Dimension(550, 250));

        JLabel descriptionLabel = new JLabel("Opis hotelu:");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Zwiększona czcionka
        descriptionLabel.setForeground(Color.WHITE);
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);

        JTextArea descriptionText = new JTextArea(description);
        descriptionText.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setEditable(false);
        descriptionText.setBackground(new Color(219, 112, 247));
        descriptionText.setForeground(Color.WHITE);
        descriptionPanel.add(new JScrollPane(descriptionText), BorderLayout.CENTER);

        mainPanel.add(descriptionPanel, BorderLayout.SOUTH);

        // Panel z wyborem liczby osób i daty
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        optionsPanel.setBackground(Color.WHITE);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        // Liczba osób
        JLabel numPeopleLabel = new JLabel("Liczba osób:");
        numPeopleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        optionsPanel.add(numPeopleLabel, gbc);

        JComboBox<Integer> peopleComboBox = new JComboBox<>(guestCapacities.stream().toArray(Integer[]::new));
        peopleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        optionsPanel.add(peopleComboBox, gbc);

        // Data rezerwacji
        JLabel dateLabel = new JLabel("Wybierz datę:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        optionsPanel.add(dateLabel, gbc);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        optionsPanel.add(dateChooser, gbc);

        mainPanel.add(optionsPanel, BorderLayout.NORTH);

        JButton backButton = new JButton("Cofnij");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(new Color(255,182,193));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            layout.show(container, "hotelList");
        });
        // Przycisk Rezerwuj
        JButton reserveButton = new JButton("Rezerwuj");
        reserveButton.setFont(new Font("Arial", Font.BOLD, 16)); // Zwiększona czcionka
        reserveButton.setBackground(new Color(255, 182, 193)); // Jasnoróżowy
        reserveButton.setForeground(Color.WHITE);
        reserveButton.setFocusPainted(false);
        reserveButton.setPreferredSize(new Dimension(120, 50));
        reserveButton.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180), 2)); // Obramowanie przycisku
        reserveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reserveButton.addActionListener(e -> {
            // Zbieranie wybranych danych
            int selectedCapacity = Integer.parseInt((String) peopleComboBox.getSelectedItem());
            Date selectedDate = dateChooser.getDate();
            ReservationCreationRequest reservationCreationRequest = new ReservationCreationRequest("createReservation", FrontendApp.userId, convertToLocalDate(selectedDate), hotelId, selectedCapacity);
            handleReservation(layout, container, reservationCreationRequest);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 182, 193));
        buttonPanel.add(backButton);
        buttonPanel.add(reserveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }



    private void handleHotelProfile(CardLayout layout, JPanel container, int hotelId) {

        HotelProfileRequest hotelProfileRequest = new HotelProfileRequest("getHotelProfile", hotelId);
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String request = jsonUtils.serialize(hotelProfileRequest);
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
                        profileResult = handleProfileResponse(response);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error processing response: " + ex.getMessage());
                        ex.printStackTrace();
                    }

                    profileResult = handleProfileResponse(response); // Przypisanie wyniku odpowiedzi

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
                    if (profileResult != null) {
                        JPanel panel = initializePanel();

                        profileResult.getHotelId();
                        panel.add(createHotelProfilePanel(profileResult.getHotelId(), profileResult.getName(), profileResult.getDescription() ,FormatAddress(profileResult.getAddress()), FormatAmenities(profileResult.getAmenities()), profileResult.getGuestCapacities(), layout, container));
                        container.add(panel, "hotelProfile");
                        layout.show(container, "hotelProfile");
                        System.out.println("Panel HotelProfile został aktywowany.");
                    } else {
                        // Obsługa przypadku, gdy wynik jest null
                        JOptionPane.showMessageDialog(null, "Nie udało się pobrać profilu hotelu");
                    }
                });
            }
        };
        worker.execute();
    }

    private HotelProfileResponse handleProfileResponse(String response) {
        HotelProfileResponse profResponse = null;
        try {
            System.out.println(response);
            profResponse = jsonUtils.deserialize(response, HotelProfileResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return profResponse;
    }
    private String FormatAddress(AddressDto address) {
        return address.getCity() + " " + address.getStreet() + " " + address.getPostalCode() + " " + address.getBuildingNumber();
    }

    private String FormatAmenities(List<String> amenities) {
        return String.join(", ", amenities);
    }


    private void handleReservation(CardLayout layout, JPanel container, ReservationCreationRequest reservationCreationRequest) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String request = jsonUtils.serialize(reservationCreationRequest);
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
                        reservationResult = handleReservation(response);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error processing response: " + ex.getMessage());
                        ex.printStackTrace();
                    }

                    reservationResult = handleReservation(response); // Przypisanie wyniku odpowiedzi

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
                    if (reservationResult != null) {
                        System.out.println("Rezerwacja została pomyślnie utworzona.");
                        showSuccessDialog(layout, container);
                    } else {
                        // Obsługa przypadku, gdy wynik jest null
                        JOptionPane.showMessageDialog(null, "Nie udało się utworzyć rezerwacji.");
                    }
                });
            }
        };
        worker.execute();
    }

    private ReservationCreationResponse handleReservation(String response) {
        ReservationCreationResponse resResponse = null;
        try {
            System.out.println(response);
            resResponse = jsonUtils.deserialize(response, ReservationCreationResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resResponse;
    }
    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    private void showSuccessDialog(CardLayout layout, JPanel container) {
        JOptionPane.showMessageDialog(null, "Reservation successfully created!", "Success", JOptionPane.INFORMATION_MESSAGE);
        layout.show(container, "menuPanel");
    }

}
