package src.View.FlightPage;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

import src.Model.FlightModel;
import src.main;

public class FlightDetail {
    public static JPanel flightDetail = new JPanel();

    // Store outbound and return flights
    private static FlightModel outboundFlight;
    private static FlightModel returnFlight;

    // Declare labels for outbound flight
    private static JLabel outboundFlightIdLabel;
    private static JLabel outboundFromLabel;
    private static JLabel outboundToLabel;
    private static JLabel outboundDepartureLabel;
    private static JLabel outboundArrivalLabel;

    // Declare labels for return flight
    private static JLabel returnFlightIdLabel;
    private static JLabel returnFromLabel;
    private static JLabel returnToLabel;
    private static JLabel returnDepartureLabel;
    private static JLabel returnArrivalLabel;

    static {
        flightDetail.setLayout(new BorderLayout());
        flightDetail.setBackground(Color.WHITE);
        flightDetail.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Top bar with back button and title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel backPanel = main.createBackPanel(() -> main.setCardLayout("booking"));

        JLabel title = new JLabel("Flight Details", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(30, 30, 60));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        topPanel.add(backPanel, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);
        flightDetail.add(topPanel, BorderLayout.NORTH);

        // Info panel with BoxLayout (vertical)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);
        Color textColor = new Color(50, 50, 80);

        // Outbound flight labels
        outboundFlightIdLabel = createInfoLabel("Outbound Flight ID: ", labelFont, textColor);
        outboundFromLabel = createInfoLabel("From: ", labelFont, textColor);
        outboundToLabel = createInfoLabel("To: ", labelFont, textColor);
        outboundDepartureLabel = createInfoLabel("Departure: ", labelFont, textColor);
        outboundArrivalLabel = createInfoLabel("Arrival: ", labelFont, textColor);

        infoPanel.add(outboundFlightIdLabel);
        infoPanel.add(outboundFromLabel);
        infoPanel.add(outboundToLabel);
        infoPanel.add(outboundDepartureLabel);
        infoPanel.add(outboundArrivalLabel);

        infoPanel.add(Box.createVerticalStrut(30)); // space between outbound and return

        // Return flight labels
        returnFlightIdLabel = createInfoLabel("Return Flight ID: ", labelFont, textColor);
        returnFromLabel = createInfoLabel("From: ", labelFont, textColor);
        returnToLabel = createInfoLabel("To: ", labelFont, textColor);
        returnDepartureLabel = createInfoLabel("Departure: ", labelFont, textColor);
        returnArrivalLabel = createInfoLabel("Arrival: ", labelFont, textColor);

        infoPanel.add(returnFlightIdLabel);
        infoPanel.add(returnFromLabel);
        infoPanel.add(returnToLabel);
        infoPanel.add(returnDepartureLabel);
        infoPanel.add(returnArrivalLabel);

        flightDetail.add(infoPanel, BorderLayout.CENTER);

        // Add Continue to Booking button panel at the bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton continueButton = new JButton("Continue to Booking");
        continueButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        continueButton.setBackground(new Color(70, 130, 180));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        continueButton.setPreferredSize(new Dimension(250, 40));

        // Add action listener for the button
        continueButton.addActionListener(e -> {
            // Here you can implement the next step of your booking process
            // For example, open booking form or confirmation page
            // For now, just print to console and switch to booking confirmation panel (example)

            System.out.println("Continue booking clicked for flights:");
            System.out.println("Outbound: " + (outboundFlight != null ? outboundFlight.getId() : "N/A"));
            System.out.println("Return: " + (returnFlight != null ? returnFlight.getId() : "N/A"));

            // TODO: Replace with your booking confirmation or form panel navigation
            main.setCardLayout("bookingConfirmation"); // example panel name
        });

        bottomPanel.add(continueButton);
        flightDetail.add(bottomPanel, BorderLayout.SOUTH);
    }

    private static JLabel createInfoLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    // Update all labels when flights are set
    private static void updateLabels() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");

        if (outboundFlight != null) {
            outboundFlightIdLabel.setText("Outbound Flight ID: " + outboundFlight.getId());
            outboundFromLabel.setText("From: " + outboundFlight.getFrom());
            outboundToLabel.setText("To: " + outboundFlight.getTo());
            outboundDepartureLabel.setText("Departure: " + sdf.format(outboundFlight.getDeparture()));
            outboundArrivalLabel.setText("Arrival: " + sdf.format(outboundFlight.getArrival()));
        } else {
            outboundFlightIdLabel.setText("Outbound Flight ID: N/A");
            outboundFromLabel.setText("From: N/A");
            outboundToLabel.setText("To: N/A");
            outboundDepartureLabel.setText("Departure: N/A");
            outboundArrivalLabel.setText("Arrival: N/A");
        }

        if (returnFlight != null) {
            returnFlightIdLabel.setText("Return Flight ID: " + returnFlight.getId());
            returnFromLabel.setText("From: " + returnFlight.getFrom());
            returnToLabel.setText("To: " + returnFlight.getTo());
            returnDepartureLabel.setText("Departure: " + sdf.format(returnFlight.getDeparture()));
            returnArrivalLabel.setText("Arrival: " + sdf.format(returnFlight.getArrival()));
        } else {
            returnFlightIdLabel.setText("Return Flight ID: N/A");
            returnFromLabel.setText("From: N/A");
            returnToLabel.setText("To: N/A");
            returnDepartureLabel.setText("Departure: N/A");
            returnArrivalLabel.setText("Arrival: N/A");
        }
    }

    public static void setOutboundFlight(FlightModel flight) {
        outboundFlight = flight;
        updateLabels();
    }

    public static void setReturnFlight(FlightModel flight) {
        returnFlight = flight;
        updateLabels();
    }
}
