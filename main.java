package src;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.View.NewFlight;
import src.View.BookingPage.Booking;
import src.View.BookingPage.BookingConfirmation;
import src.View.BookingPage.BookingDetail;
import src.View.FlightPage.FlightCurrentPage;
import src.View.FlightPage.FlightDetail;
import src.View.UserPage.UserProfile;
import src.Model.FlightDAO;
import src.View.Home;
import src.View.Navbar;

public class main {

    public static JFrame frame = new JFrame("Flight manager system");
    public static CardLayout cardLayout = new CardLayout();
    public static JPanel cardPanel = new JPanel(cardLayout);

    public static void setCardLayout(String nameFrame) {
        cardLayout.show(cardPanel, nameFrame);
    }

    static {
        frame.setLayout(new BorderLayout());

        // Add navbar panel on left
        frame.add(Navbar.navbar, BorderLayout.WEST);

        // Add card panel center
        frame.add(cardPanel, BorderLayout.CENTER);

        JPanel settingsPanel = new JPanel();
        settingsPanel.add(new JLabel("Settings Page"));

        JPanel profilePanel = new JPanel();
        profilePanel.add(new JLabel("User Profile Page"));

        // Add cards to cardPanel with a name
        cardPanel.add(Home.home, "home");
        cardPanel.add(FlightCurrentPage.flightCurrentPage, "flightCurrent");
        cardPanel.add(NewFlight.newFlightPage, "newFlight");
        cardPanel.add(BookingDetail.bookingDetail, "bookingDetail");
        cardPanel.add(UserProfile.userProfilePage, "userProfile");
        cardPanel.add(Booking.booking, "booking");
        cardPanel.add(FlightDetail.flightDetail, "flightDetail");
        cardPanel.add(BookingConfirmation.bookingConfirmation, "bookingConfirmation");
        frame.setSize(1300, 675);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public static JPanel createBackPanel(Runnable onBack) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);

        backButton.addActionListener(e -> {
            if (onBack != null) {
                onBack.run();
            }
        });

        panel.add(backButton);
        return panel;
    }

    public static JPanel createForm(String[] labels) {
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 20);

        JPanel form = new JPanel();
        form.setLayout(new GridLayout(labels.length, 2, 10, 10));
        form.setBackground(Color.WHITE);

        for(int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(labelFont);
            
            JTextField textField = new JTextField(10);
            textField.setFont(textFieldFont);
            form.add(label);
            form.add(textField);
        }

        return form;
    }

    public static void main(String[] args) {
        frame.setVisible(true);
        setCardLayout("home"); // Show home card by default
    }
}
