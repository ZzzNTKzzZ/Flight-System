package src.View.BookingPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import src.main;
import src.Controller.FlightController;
import src.Model.FlightModel;
import src.Model.TripModel;  // Make sure this import exists
import src.View.FlightPage.FlightDetail;

public class Booking {
    public static JPanel booking = new JPanel();
    private static JPanel listPanel = new JPanel(); // container for cards

    static {
        booking.setLayout(new BorderLayout());
        booking.setBackground(Color.WHITE);
        booking.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Top: Back button and title
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

        // Search Form
        JPanel form = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        form.setBackground(Color.WHITE);

        String[] labels = { "From", "To", "Departure", "Return" };
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JPanel pairPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            pairPanel.setBackground(Color.WHITE);

            JLabel label = new JLabel(labels[i] + ":");
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JTextField field = new JTextField(8);
            field.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            pairPanel.add(label);
            pairPanel.add(field);
            pairPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

            form.add(pairPanel);
            fields[i] = field;
        }

        JButton searchButton = createSearchButton(fields);
        form.add(searchButton);
        booking.add(form, BorderLayout.CENTER);

        // Result list panel
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scroll

        booking.add(scrollPane, BorderLayout.SOUTH);
    }

    private static JButton createSearchButton(JTextField[] fields) {
        JButton button = new JButton("Search");
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        button.addActionListener(e -> {
            String from = fields[0].getText();
            String to = fields[1].getText();
            String departure = fields[2].getText();
            String returnDate = fields[3].getText();

            List<TripModel> trips = FlightController.searchFlight(from, to, departure, returnDate);
            listPanel.removeAll();
            updateListPanel(trips);
            listPanel.revalidate();
            listPanel.repaint();
        });

        return button;
    }

    private static void updateListPanel(List<TripModel> trips) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");

        for (TripModel trip : trips) {
            FlightModel outbound = trip.getOutbound();
            FlightModel returnFlight = trip.getReturnFlight();

            JPanel tripCard = new JPanel(new BorderLayout());
            tripCard.setBackground(new Color(240, 248, 255));
            tripCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            tripCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));  // Taller for two flights

            String infoHtml = "<html>" +
                    "<b>Outbound Flight:</b> " + outbound.getFrom() + " → " + outbound.getTo() + "<br>" +
                    "<b>Departure:</b> " + sdf.format(outbound.getDeparture()) + " | " +
                    "<b>Arrival:</b> " + sdf.format(outbound.getArrival()) + "<br><br>" +
                    "<b>Return Flight:</b> " + returnFlight.getFrom() + " → " + returnFlight.getTo() + "<br>" +
                    "<b>Departure:</b> " + sdf.format(returnFlight.getDeparture()) + " | " +
                    "<b>Arrival:</b> " + sdf.format(returnFlight.getArrival()) +
                    "</html>";

            JLabel infoLabel = new JLabel(infoHtml);
            infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JButton bookingBtn = new JButton("Booking");
            bookingBtn.setFocusPainted(false);
            bookingBtn.setPreferredSize(new Dimension(120, 5));
            bookingBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            bookingBtn.setBackground(new Color(70, 130, 180));
            bookingBtn.setForeground(Color.WHITE);
            bookingBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            bookingBtn.addActionListener(ev -> {
                FlightDetail.setOutboundFlight(outbound);
                FlightDetail.setReturnFlight(returnFlight);
                main.setCardLayout("flightDetail");
            });

            tripCard.add(infoLabel, BorderLayout.CENTER);
            tripCard.add(bookingBtn, BorderLayout.EAST);

            listPanel.add(Box.createVerticalStrut(10)); // spacing
            listPanel.add(tripCard);
        }
    }
}
