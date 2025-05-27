package src.View.FlightPage;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.*;
import src.Model.FlightModel;
import src.View.BookingPage.BookingDetail;
import src.main;
import src.Controller.FlightController;
public class FlightCurrentPage {
    public static JPanel flightCurrentPage = new JPanel();

    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy - HH:mm");

    // Create a panel for a single flight with fixed font size
    static JPanel flightItem(FlightModel flight) {
        JPanel card = new JPanel(new GridLayout(3, 2));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setBackground(new Color(240, 248, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 200, 220)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(800, 100));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);

        JLabel flightIdLabel = new JLabel("Flight ID: " + flight.getId());
        flightIdLabel.setFont(labelFont);
        card.add(flightIdLabel);

        JLabel fromLabel = new JLabel("From: " + flight.getFrom());
        fromLabel.setFont(labelFont);
        card.add(fromLabel);

        JLabel toLabel = new JLabel("To: " + flight.getTo());
        toLabel.setFont(labelFont);
        card.add(toLabel);

        JLabel departureLabel = new JLabel("Departure: " + dateFormat.format(flight.getDeparture()));
        departureLabel.setFont(labelFont);
        card.add(departureLabel);

        JLabel arrivalLabel = new JLabel("Arrival: " + dateFormat.format(flight.getArrival()));
        arrivalLabel.setFont(labelFont);
        card.add(arrivalLabel);
        String currentFlightId = flight.getId();
        card.addMouseListener(new MouseAdapter() {
           
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                BookingDetail.setFlightId(currentFlightId);
                main.setCardLayout("bookingDetail");
            }
        });
        return card;
    }

    // Create a panel that contains the list of all flights
    static JPanel flightList() throws SQLException {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);
        // Sample flight data (you can add more to test scroll)
        List<FlightModel> flights = FlightController.getAllFlights();
        for (FlightModel flight : flights) {
            listPanel.add(flightItem(flight));
            listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return listPanel;
    }

    // Helper to create a Date object
    static Date getDate(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, minute);
        return cal.getTime();
    }

    // Setup main page
    static {
    flightCurrentPage.setLayout(new BorderLayout());
    flightCurrentPage.setBackground(Color.WHITE);

    // Wrapper panel for top: back button left + title center
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(Color.WHITE);

    // Add back panel (left)
    JPanel backPanel = main.createBackPanel(() -> {
        // Replace with your card switching logic
        main.setCardLayout("home");
    });
    topPanel.add(backPanel, BorderLayout.WEST);

    // Title (center)
    JLabel title = new JLabel("Current Flights", SwingConstants.CENTER);
    title.setFont(new Font("Segoe UI", Font.BOLD, 24));
    title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    topPanel.add(title, BorderLayout.CENTER);

    flightCurrentPage.add(topPanel, BorderLayout.NORTH);

    JScrollPane scrollPane = null;
    try {
        scrollPane = new JScrollPane(flightList());
    } catch (SQLException e) {
        e.printStackTrace();
    }
    scrollPane.setBorder(null);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    flightCurrentPage.add(scrollPane, BorderLayout.CENTER);
}
}
