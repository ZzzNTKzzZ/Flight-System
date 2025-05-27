package src.View.FlightPage;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

import src.Model.FlightModel;
import src.main;

public class FlightDetail {
    public static JPanel flightDetail = new JPanel();
    public static FlightModel flight;

    // Declare labels
    private static JLabel flightIdLabel;
    private static JLabel fromLabel;
    private static JLabel toLabel;
    private static JLabel departureLabel;
    private static JLabel arrivalLabel;

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

        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);
        Color textColor = new Color(50, 50, 80);

        flightIdLabel = createInfoLabel("Flight ID: ", labelFont, textColor);
        fromLabel = createInfoLabel("From: ", labelFont, textColor);
        toLabel = createInfoLabel("To: ", labelFont, textColor);
        departureLabel = createInfoLabel("Departure: ", labelFont, textColor);
        arrivalLabel = createInfoLabel("Arrival: ", labelFont, textColor);

        infoPanel.add(flightIdLabel);
        infoPanel.add(fromLabel);
        infoPanel.add(toLabel);
        infoPanel.add(departureLabel);
        infoPanel.add(arrivalLabel);

        flightDetail.add(infoPanel, BorderLayout.CENTER);
    }

    private static JLabel createInfoLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    // Call this method before showing the detail view
    public static void setFlight(FlightModel flight) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");

        flightIdLabel.setText("Flight ID: " + flight.getId());
        fromLabel.setText("From: " + flight.getFrom());
        toLabel.setText("To: " + flight.getTo());
        departureLabel.setText("Departure: " + sdf.format(flight.getDeparture()));
        arrivalLabel.setText("Arrival: " + sdf.format(flight.getArrival()));
    }
}
