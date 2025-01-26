package org.po2_jmp.panels;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.po2_jmp.FrontendApp;
import org.po2_jmp.request.ReservationCancellationRequest;
import org.po2_jmp.request.UserReservationsRequest;
import org.po2_jmp.response.*;
import org.po2_jmp.websocket.JsonUtils;
import org.po2_jmp.websocket.MyWebSocketHandler;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javax.swing.*;
import java.awt.*;

public class ReservationsPanelCreator {

    private UserReservationsResponse resResponse;
    private MyWebSocketHandler myWebSocketHandler;
    private JsonUtils jsonUtils = new JsonUtils();
    private JPanel reservationsMainPanel;
    private DefaultListModel<String> reservationListModel;
    private JList<String> reservationList;

    public ReservationsPanelCreator(MyWebSocketHandler webSocketHandler) {
        this.myWebSocketHandler = webSocketHandler;
        this.reservationListModel = new DefaultListModel<>();
    }

    public JPanel create(CardLayout cardLayout, JPanel containerPanel) {
        reservationsMainPanel = new JPanel();
        reservationsMainPanel.setLayout(new BorderLayout());
        reservationsMainPanel.setBackground(Color.WHITE);

        reservationsMainPanel.add(createHeader(), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(createReservationPanel());
        JScrollPane reservationListScrollPane = new JScrollPane(createReservationList());
        centerPanel.add(reservationListScrollPane);

        reservationsMainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = createFooterPanel(cardLayout, containerPanel);
        reservationsMainPanel.add(footerPanel, BorderLayout.SOUTH);

        handleReservationsList(cardLayout, containerPanel);

        return reservationsMainPanel;
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(230, 230, 230));

        JLabel titleLabel = new JLabel("Sieć Hoteli Akropol");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(128, 0, 128));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(Box.createVerticalStrut(20));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));

        return headerPanel;
    }

    private JScrollPane createReservationList() {
        reservationList = new JList<>(reservationListModel);
        reservationList.setFont(new Font("Arial", Font.PLAIN, 16));
        reservationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reservationList.setBackground(new Color(245, 245, 245));
        reservationList.setVisibleRowCount(10);

        JScrollPane scrollPane = new JScrollPane(reservationList);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        return scrollPane;
    }

    private JPanel createReservationPanel() {
        JPanel reservationPanel = new JPanel();
        reservationPanel.setLayout(new BoxLayout(reservationPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Twoje rezerwacje");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        reservationPanel.add(Box.createVerticalStrut(20));
        reservationPanel.add(titleLabel);
        reservationPanel.add(Box.createVerticalStrut(10));

        return reservationPanel;
    }

    private JPanel createFooterPanel(CardLayout cardLayout, JPanel containerPanel) {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.WHITE);
        JButton backButton = new JButton("Cofnij");
        backButton.addActionListener(e -> cardLayout.show(containerPanel, "menuPanel"));
        leftPanel.add(backButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        JButton cancelReservationButton = new JButton("Anuluj Rezerwację");
        cancelReservationButton.addActionListener(e -> cancelSelectedReservation());
        rightPanel.add(cancelReservationButton);

        footerPanel.add(leftPanel, BorderLayout.WEST);
        footerPanel.add(rightPanel, BorderLayout.EAST);

        return footerPanel;
    }

    private void handleReservationsList(CardLayout layout, JPanel container) {
        UserReservationsRequest reservationsRequest = new UserReservationsRequest("getUserReservations", FrontendApp.userId);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                boolean success = false;
                while (!success) {
                    try {
                        String jsonRequest = jsonUtils.serialize(reservationsRequest);
                        myWebSocketHandler.sendMessage(jsonRequest);

                        String response = myWebSocketHandler.getResponse();
                        resResponse = jsonUtils.deserialize(response, UserReservationsResponse.class);

                        if (resResponse != null && resResponse.getReservations() != null) {
                            success = true;
                        } else {
                            Thread.sleep(1000);
                        }
                    } catch (JsonProcessingException | InterruptedException e) {
                        e.printStackTrace();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException interruptedException) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    if (resResponse != null && resResponse.getReservations() != null) {
                        for (ReservationDto reservation : resResponse.getReservations()) {
                            String reservationInfo = String.format(
                                    "Numer rezerwacji: %s, Data: %s, Utworzona: %s, Hotel: %s, Liczba gości: %d",
                                    reservation.getReservationId(),
                                    reservation.getReservationDate(),
                                    formatDateTime(reservation.getCreationDateTime()),
                                    reservation.getHotel(),
                                    reservation.getRoomGuestCapacity()
                            );
                            reservationListModel.addElement(reservationInfo);
                        }
                        container.add(reservationsMainPanel, "reservationsPanel");
                        layout.show(container, "reservationsPanel");
                    } else {
                        JOptionPane.showMessageDialog(null, "Brak dostępnych rezerwacji.");
                    }
                });
            }
        };

        worker.execute();
    }

    private void cancelSelectedReservation() {
        int selectedIndex = reservationList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Proszę zaznaczyć rezerwację do anulowania.");
            return;
        }

        String selectedReservation = reservationListModel.getElementAt(selectedIndex);
        String idString = selectedReservation.split(",")[0].replace("Numer rezerwacji: ", "").trim();
        int reservationId = Integer.parseInt(idString);
        ReservationCancellationRequest cancelRequest = new ReservationCancellationRequest("cancelReservation", reservationId);

        SwingWorker<Void, Void> cancelWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String jsonRequest = jsonUtils.serialize(cancelRequest);
                    myWebSocketHandler.sendMessage(jsonRequest);

                    ReservationCancellationResponse cancellationResponse = jsonUtils.deserialize(myWebSocketHandler.getResponse(), ReservationCancellationResponse.class);
                    if (cancellationResponse.getStatus() == ResponseStatus.OK) {
                        JOptionPane.showMessageDialog(null, "Anulowanie rezerwacji powiodło się");
                        reservationListModel.remove(selectedIndex);
                    }
                    else if (cancellationResponse.getStatus() == ResponseStatus.BAD_REQUEST){
                        JOptionPane.showMessageDialog(null, "Nie można anulować rezerwacji z przeszłości.");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Anulowanie rezerwacji nie powiodło się.");
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                });
            }
        };
        cancelWorker.execute();
    }
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); // Przykładowy format
        return dateTime.format(formatter);
    }
}
