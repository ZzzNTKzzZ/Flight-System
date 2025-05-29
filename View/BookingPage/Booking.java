package src.View.BookingPage;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;

import src.main;
import src.Controller.FlightController;
import src.Model.FlightModel;
import src.Model.TripModel;
import src.View.FlightPage.FlightDetail;

public class Booking {
    public static JPanel booking = new JPanel();
    private static JPanel listPanel = new JPanel();
    private static JComboBox<String> fromBox;
    private static JComboBox<String> toBox;

    static {
        booking.setLayout(new BorderLayout());
        booking.setBackground(Color.WHITE);
        booking.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel backPanel = main.createBackPanel(() -> main.setCardLayout("home"));

        JLabel title = new JLabel("Booking", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        title.setForeground(new Color(30, 30, 60));

        topPanel.add(backPanel, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);
        booking.add(topPanel, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        form.setBackground(Color.WHITE);

        String[] labels = {"From", "To", "Departure", "Return"};
        JComponent[] fields = new JComponent[labels.length];

        // Fetch initial suggestions
        List<String> fromSuggestions = FlightController.getFlightFrom();
        List<String> toSuggestions = FlightController.getFligthTo();

        for (int i = 0; i < labels.length; i++) {
            JPanel pairPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            pairPanel.setBackground(Color.WHITE);

            JLabel label = new JLabel(labels[i] + ":");
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            if (i == 0) {
                fromBox = new JComboBox<>(fromSuggestions.toArray(new String[0]));
                fromBox.setPreferredSize(new Dimension(100, 25));
                fromBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                pairPanel.add(label);
                pairPanel.add(fromBox);
                fields[i] = fromBox;
            } else if (i == 1) {
                toBox = new JComboBox<>(toSuggestions.toArray(new String[0]));
                toBox.setPreferredSize(new Dimension(100, 25));
                toBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                pairPanel.add(label);
                pairPanel.add(toBox);
                fields[i] = toBox;
            } else {
                JTextField dateField = new JTextField(8);
                dateField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                pairPanel.add(label);
                pairPanel.add(dateField);
                fields[i] = dateField;
            }

            form.add(pairPanel);
        }

        JButton searchButton = createSearchButton(fields);
        form.add(searchButton);
        booking.add(form, BorderLayout.CENTER);

        // List area
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        booking.add(scrollPane, BorderLayout.SOUTH);
    }

    private static JButton createSearchButton(JComponent[] fields) {
        JButton button = new JButton("Search");
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        button.addActionListener(e -> {
            String from = (String) ((JComboBox<?>) fields[0]).getSelectedItem();
            String to = (String) ((JComboBox<?>) fields[1]).getSelectedItem();
            String departure = ((JTextField) fields[2]).getText();
            String returnDate = ((JTextField) fields[3]).getText();

            boolean isRoundTrip = returnDate != null && !returnDate.trim().isEmpty();

            List<TripModel> trips = src.Controller.FlightController.searchFlight(from, to, departure, isRoundTrip ? returnDate : null);

            listPanel.removeAll();
            updateListPanel(trips, isRoundTrip);
            listPanel.revalidate();
            listPanel.repaint();
        });

        return button;
    }

    private static void updateListPanel(List<TripModel> trips, boolean isRoundTrip) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");

        for (TripModel trip : trips) {
            FlightModel outbound = trip.getOutbound();
            FlightModel returnFlight = trip.getReturnFlight();

            JPanel tripCard = new JPanel(new BorderLayout());
            tripCard.setBackground(new Color(240, 248, 255));
            tripCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            tripCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

            String infoHtml = "<html>" +
                    "<b>Outbound:</b> " + outbound.getFrom() + " → " + outbound.getTo() + "<br>" +
                    "<b>Departure:</b> " + sdf.format(outbound.getDeparture()) + " | " +
                    "<b>Arrival:</b> " + sdf.format(outbound.getArrival()) + "<br>";

            if (isRoundTrip && returnFlight != null) {
                infoHtml += "<br><b>Return:</b> " + returnFlight.getFrom() + " → " + returnFlight.getTo() + "<br>" +
                        "<b>Departure:</b> " + sdf.format(returnFlight.getDeparture()) + " | " +
                        "<b>Arrival:</b> " + sdf.format(returnFlight.getArrival());
            }

            infoHtml += "</html>";

            JLabel infoLabel = new JLabel(infoHtml);
            infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JButton bookButton = new JButton("Booking");
            bookButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
            bookButton.setBackground(new Color(70, 130, 180));
            bookButton.setForeground(Color.WHITE);
            bookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            bookButton.setPreferredSize(new Dimension(120, 40));

            bookButton.addActionListener(ev -> {
                FlightDetail.setOutboundFlight(outbound);
                if (isRoundTrip) {
                    FlightDetail.setReturnFlight(returnFlight);
                } else {
                    FlightDetail.setReturnFlight(null);
                }
                main.setCardLayout("flightDetail");
            });

            tripCard.add(infoLabel, BorderLayout.CENTER);
            tripCard.add(bookButton, BorderLayout.EAST);
            listPanel.add(Box.createVerticalStrut(10));
            listPanel.add(tripCard);
        }
    }

    // 🔄 Public method to refresh combo box contents
    public static void refreshComboBoxes() {
        List<String> newFrom = FlightController.getFlightFrom();
        List<String> newTo = FlightController.getFligthTo();

        fromBox.removeAllItems();
        for (String from : newFrom) {
            fromBox.addItem(from);
        }

        toBox.removeAllItems();
        for (String to : newTo) {
            toBox.addItem(to);
        }
    }
}
