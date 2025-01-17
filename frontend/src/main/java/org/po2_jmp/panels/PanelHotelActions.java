package org.po2_jmp.panels;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Setter;
import org.po2_jmp.request.HotelsOverviewsRequest;
import org.po2_jmp.request.UserAuthenticationRequest;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.response.UserAuthenticationResponse;
import org.po2_jmp.websocket.JsonUtils;
import org.po2_jmp.websocket.MyWebSocketHandler;

import javax.swing.*;
import java.awt.*;

public class PanelHotelActions implements Panel {

    private final PanelId id;
    private final JPanel panel;
    private final JsonUtils jsonUtils = new JsonUtils();
    private final MyWebSocketHandler myWebSocketHandler;

    @Setter
    private PanelsContainer panelsContainer;


    public PanelHotelActions(PanelId id, CardLayout layout, JPanel container, MyWebSocketHandler myWebSocketHandler) {
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
        JPanel hotelPanel = new JPanel();
        hotelPanel.setLayout(new BoxLayout(hotelPanel, BoxLayout.Y_AXIS)); // Ustawienie pionowego układu elementów

        JLabel titleLabel = new JLabel("Hotel Actions");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Wyśrodkowanie tekstu
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        hotelPanel.add(titleLabel);

        JButton hotelListButton = new JButton("Go to Hotel List");
        hotelListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hotelListButton.addActionListener(
                e -> {
                    JPanel panel = HotelListPanel.generatePanel();
                    panelsContainer.replacePanel(id, panel);
                    layout.show(container, "HotelList");
                });
        hotelPanel.add(hotelListButton);

        JButton userReservationsButton = new JButton("Go to User Reservations");
        userReservationsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        userReservationsButton.addActionListener(
                e -> layout.show(container, "UserReservations")); // Zmień nazwę "UserReservations" na odpowiedni identyfikator
        hotelPanel.add(userReservationsButton);

        JButton backButton = new JButton("Back to Main Panel");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(
                e -> layout.show(container, "Main")); // Zmień nazwę "Main" na odpowiedni identyfikator
        hotelPanel.add(backButton);

        hotelPanel.setBackground(Color.LIGHT_GRAY); // Ustawienie tła
        hotelPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Dodanie marginesów

        return hotelPanel;
    }
//    private void handleHotelList(CardLayout layout, JPanel container) {
//        HotelsOverviewsRequest overviewsRequest = new HotelsOverviewsRequest("getHotelsOverviews");
//        SwingWorker<Void, Void> worker = new SwingWorker<>() {
//            @Override
//            protected Void doInBackground() {
//                try {
//                    String request = jsonUtils.serialize(overviewsRequest);
//                    myWebSocketHandler.sendMessage(request);
//
//                    String response;
//                    do {
//                        response = myWebSocketHandler.getRespondFromBackend();
//                    } while (response == null);
//
//                    myWebSocketHandler.setRespondFromBackend(null);
//                    handleResponse(response, layout, container);
//                } catch (JsonProcessingException ex) {
//                    ex.printStackTrace();
//                }
//                return null;
//            }
//        };
//        worker.execute();
//    }
//    private void handleResponse(String response, CardLayout layout, JPanel container) {
//        try {
//            UserAuthenticationResponse authResponse = jsonUtils.deserialize(response, UserAuthenticationResponse.class);
//            if (authResponse.getStatus() == ResponseStatus.OK) {
//                layout.show(container, "HotelList");
//
//            } else {
//                System.out.println("Authentication failed: " + authResponse.getMessage());
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }

}
