package src.View;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import src.main;
import src.Controller.FlightController;
import src.Model.FlightModel;
import src.Model.FlightStatus;
import src.View.FlightPage.FlightCurrentPage;

public class NewFlight {
    public static JPanel newFlightPage = new JPanel();

    private static Map<String, JComponent> inputFields = new HashMap<>();

    static {
        newFlightPage.setLayout(new BorderLayout());
        newFlightPage.setBackground(Color.WHITE);
        newFlightPage.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // padding around

        JPanel backPanel = createBackPanel(() -> main.setCardLayout("home"));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(backPanel, BorderLayout.WEST);

        JLabel title = new JLabel("Add New Flight", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(30, 30, 60));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));
        topPanel.add(title, BorderLayout.CENTER);

        newFlightPage.add(topPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 20, 20)); // changed rows to 6, removed status row
        formPanel.setBackground(Color.WHITE);

        String[] labels = {
                "Flight ID:",
                "From:",
                "To:",
                "Departure:",
                "Arrival:",
                "Seats Available:"
        };

        for (String label : labels) {
            JLabel jLabel = new JLabel(label);
            jLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

            if (label.equals("Departure:") || label.equals("Arrival:")) {
                JPanel labelPanel = new JPanel(new BorderLayout());
                labelPanel.setBackground(Color.WHITE);
                labelPanel.add(jLabel, BorderLayout.CENTER);

                JPanel dateTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
                dateTimePanel.setBackground(Color.WHITE);

                JTextField dateField = new JTextField();
                dateField.setPreferredSize(new Dimension(120, 30));
                dateField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                addPlaceholder(dateField, "yyyy-mm-dd");

                JTextField timeField = new JTextField();
                timeField.setPreferredSize(new Dimension(80, 30));
                timeField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                addPlaceholder(timeField, "HH:mm");

                dateTimePanel.add(dateField);
                dateTimePanel.add(timeField);

                inputFields.put(label + " Date", dateField);
                inputFields.put(label + " Time", timeField);

                formPanel.add(labelPanel);
                formPanel.add(dateTimePanel);
            } else {
                JTextField textField = new JTextField();
                textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textField.setPreferredSize(new Dimension(200, 30));
                addPlaceholder(textField, "Enter " + label.replace(":", ""));
                inputFields.put(label, textField);

                formPanel.add(jLabel);
                formPanel.add(textField);
            }
        }

        newFlightPage.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        submitButton.setPreferredSize(new Dimension(140, 50));

        submitButton.addActionListener(e -> {
            if (validateInputs()) {
                // Collect data
                String flightId = ((JTextField) inputFields.get("Flight ID:")).getText();
                String from = ((JTextField) inputFields.get("From:")).getText();
                String to = ((JTextField) inputFields.get("To:")).getText();

                String depDate = ((JTextField) inputFields.get("Departure: Date")).getText();
                String depTime = ((JTextField) inputFields.get("Departure: Time")).getText();

                String arrDate = ((JTextField) inputFields.get("Arrival: Date")).getText();
                String arrTime = ((JTextField) inputFields.get("Arrival: Time")).getText();

                String seatsStr = ((JTextField) inputFields.get("Seats Available:")).getText();
                int seatInt = Integer.parseInt(seatsStr);

                System.out.println("Flight ID: " + flightId);
                System.out.println("From: " + from);
                System.out.println("To: " + to);
                System.out.println("Departure: " + depDate + " " + depTime);
                System.out.println("Arrival: " + arrDate + " " + arrTime);
                System.out.println("Status: " + FlightStatus.Scheduled);
                System.out.println("Seats Available: " + seatsStr);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                Date departure = null;
                Date arrival = null;

                try {
                    departure = formatter.parse(depDate + " " + depTime);
                    arrival = formatter.parse(arrDate + " " + arrTime);
                } catch (ParseException err) {
                    err.printStackTrace();
                }

                FlightModel newFlight = new FlightModel(flightId, from, to, departure, arrival, FlightStatus.Scheduled, seatInt);
                FlightController.createNewFlights(newFlight);
                FlightCurrentPage.refreshFlightList();
                JOptionPane.showMessageDialog(newFlightPage, "Flight added successfully with status: Scheduled!");
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        newFlightPage.add(buttonPanel, BorderLayout.SOUTH);
    }

    private static boolean validateInputs() {
        // Check required text fields (non-placeholder, non-empty)
        String[] requiredTextFields = { "Flight ID:", "From:", "To:", "Seats Available:" };
        for (String key : requiredTextFields) {
            JTextField tf = (JTextField) inputFields.get(key);
            if (tf == null)
                continue;
            String val = tf.getText().trim();
            if (val.isEmpty() || isPlaceholder(tf)) {
                showError(key + " is required.");
                return false;
            }
        }

        // Validate seats available is integer and > 0
        JTextField seatsField = (JTextField) inputFields.get("Seats Available:");
        try {
            int seats = Integer.parseInt(seatsField.getText().trim());
            if (seats < 0) {
                showError("Seats Available must be a positive number.");
                return false;
            }
        } catch (NumberFormatException ex) {
            showError("Seats Available must be a valid integer.");
            return false;
        }

        // Validate departure and arrival date/time
        String depDate = ((JTextField) inputFields.get("Departure: Date")).getText().trim();
        String depTime = ((JTextField) inputFields.get("Departure: Time")).getText().trim();
        String arrDate = ((JTextField) inputFields.get("Arrival: Date")).getText().trim();
        String arrTime = ((JTextField) inputFields.get("Arrival: Time")).getText().trim();

        if (depDate.isEmpty() || isPlaceholder((JTextField) inputFields.get("Departure: Date"))) {
            showError("Departure date is required.");
            return false;
        }
        if (depTime.isEmpty() || isPlaceholder((JTextField) inputFields.get("Departure: Time"))) {
            showError("Departure time is required.");
            return false;
        }
        if (arrDate.isEmpty() || isPlaceholder((JTextField) inputFields.get("Arrival: Date"))) {
            showError("Arrival date is required.");
            return false;
        }
        if (arrTime.isEmpty() || isPlaceholder((JTextField) inputFields.get("Arrival: Time"))) {
            showError("Arrival time is required.");
            return false;
        }

        if (!isValidDate(depDate)) {
            showError("Departure date format must be yyyy-mm-dd.");
            return false;
        }
        if (!isValidTime(depTime)) {
            showError("Departure time format must be HH:mm.");
            return false;
        }
        if (!isValidDate(arrDate)) {
            showError("Arrival date format must be yyyy-mm-dd.");
            return false;
        }
        if (!isValidTime(arrTime)) {
            showError("Arrival time format must be HH:mm.");
            return false;
        }

        return true;
    }

    private static boolean isPlaceholder(JTextField tf) {
        return tf.getForeground().equals(Color.GRAY);
    }

    private static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean isValidTime(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(false);
        try {
            sdf.parse(timeStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private static void showError(String message) {
        JOptionPane.showMessageDialog(newFlightPage, message, "Input Error", JOptionPane.ERROR_MESSAGE);
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

    // Simple placeholder implementation for JTextField
    private static void addPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }
}
