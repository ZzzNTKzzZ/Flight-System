package src.View.BookingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import src.main;
import src.Controller.FlightController;
import src.Controller.FlightSeatController;
import src.Controller.SeatController;
import src.Controller.TicketController;
import src.Controller.UserController;  // Make sure this exists
import src.Model.FlightModel;
import src.Model.FlightSeatModel;
import src.Model.SeatModel;
import src.Model.TicketModel;
import src.Model.UserModel;

public class BookingConfirmation {
    public static JPanel bookingConfirmation = new JPanel();

    private static String flightIdFrom;

    public static void setFlightFrom(String id) {
        flightIdFrom = id;
    }

    private static JTextField userIdField;
    private static JTextField fullNameField;
    private static JComboBox<String> genderBox;
    private static JTextField ageField;
    private static JTextField phoneField;
    private static JSpinner dateSpinner;
    private static JComboBox<String> seatClassBox;
    private static JLabel priceLabel;

    private static final int ECONOMY_PRICE = 100;
    private static final int BUSINESS_PRICE = 250;
    private static final int FIRST_PRICE = 500;

    static {
        bookingConfirmation.setLayout(new BorderLayout(40, 20));
        bookingConfirmation.setBackground(Color.WHITE);
        bookingConfirmation.setBorder(BorderFactory.createEmptyBorder(40, 70, 40, 70));

        JLabel title = new JLabel("Passenger Information", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(30, 30, 60));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        bookingConfirmation.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 20);
        int row = 0;

        addLabel(formPanel, "Date:", labelFont, gbc, row, 0);
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        addField(formPanel, dateSpinner, gbc, row, 1);
        row++;

        addLabel(formPanel, "User ID:", labelFont, gbc, row, 0);
        userIdField = new JTextField(20);
        addField(formPanel, userIdField, gbc, row, 1);

        // Add focus lost listener to check if user exists and load info
        userIdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String userId = userIdField.getText().trim();
                if (!userId.isEmpty()) {
                    if (isExistingUser(userId)) {
                        loadUserInfo(userId);
                        JOptionPane.showMessageDialog(null, "Existing user detected. Info loaded.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Clear other fields for new user
                        fullNameField.setText("");
                        genderBox.setSelectedIndex(0);
                        ageField.setText("");
                        phoneField.setText("");
                    }
                }
            }
        });

        row++;

        addLabel(formPanel, "Full Name:", labelFont, gbc, row, 0);
        fullNameField = new JTextField(20);
        addField(formPanel, fullNameField, gbc, row, 1);
        row++;

        addLabel(formPanel, "Gender:", labelFont, gbc, row, 0);
        genderBox = new JComboBox<>(new String[]{"M", "F"});
        genderBox.setPreferredSize(new Dimension(100, 28));
        addField(formPanel, genderBox, gbc, row, 1);
        row++;

        addLabel(formPanel, "Age:", labelFont, gbc, row, 0);
        ageField = new JTextField(20);
        addField(formPanel, ageField, gbc, row, 1);
        row++;

        addLabel(formPanel, "Phone:", labelFont, gbc, row, 0);
        phoneField = new JTextField(20);
        addField(formPanel, phoneField, gbc, row, 1);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel classLabel = new JLabel("Class:");
        classLabel.setFont(labelFont);
        formPanel.add(classLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        seatClassBox = new JComboBox<>(new String[]{"Economy", "Business", "First"});
        seatClassBox.setPreferredSize(new Dimension(150, 28));
        formPanel.add(seatClassBox, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.5;
        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        priceLabel.setForeground(new Color(70, 130, 180));
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(priceLabel, gbc);

        row++;

        bookingConfirmation.add(formPanel, BorderLayout.CENTER);

        JButton submitBtn = new JButton("Confirm Booking");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 22));
        submitBtn.setBackground(new Color(70, 130, 180));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.setPreferredSize(new Dimension(0, 50));
        submitBtn.addActionListener(e -> handleSubmit());

        bookingConfirmation.add(submitBtn, BorderLayout.SOUTH);

        updatePrice();
        seatClassBox.addActionListener(e -> updatePrice());
    }

    public static void setUserId(String userId) {
        userIdField.setText(userId);
    }

    private static void addLabel(JPanel panel, String text, Font font, GridBagConstraints gbc, int row, int col) {
        gbc.gridx = col;
        gbc.gridy = row;
        JLabel label = new JLabel(text);
        label.setFont(font);
        panel.add(label, gbc);
    }

    private static void addField(JPanel panel, JComponent field, GridBagConstraints gbc, int row, int col) {
        gbc.gridx = col;
        gbc.gridy = row;
        panel.add(field, gbc);
    }

    private static void updatePrice() {
        String seatClass = (String) seatClassBox.getSelectedItem();
        int price = switch (seatClass) {
            case "Economy" -> ECONOMY_PRICE;
            case "Business" -> BUSINESS_PRICE;
            case "First" -> FIRST_PRICE;
            default -> 0;
        };
        priceLabel.setText("Price: $" + price);
    }

    private static void handleSubmit() {
    Date date = (Date) dateSpinner.getValue();
    String fullName = fullNameField.getText().trim();
    String gender = (String) genderBox.getSelectedItem();
    String ageText = ageField.getText().trim();
    String phone = phoneField.getText().trim();
    String seatClass = (String) seatClassBox.getSelectedItem();
    String userId = userIdField.getText().trim();

    // Validate inputs
    if (userId.isEmpty()) {
        JOptionPane.showMessageDialog(null, "User ID is missing.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (fullName.isEmpty() || ageText.isEmpty() || phone.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int age;
    try {
        age = Integer.parseInt(ageText);
        if (age <= 0 || age > 120) throw new NumberFormatException("Invalid age");
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Please enter a valid age between 1 and 120.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!phone.matches("\\d{7,15}")) {
        JOptionPane.showMessageDialog(null, "Phone number must be 7–15 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dateStr = sdf.format(date);

    try {
        java.sql.Date sqlDate = new java.sql.Date(sdf.parse(dateStr).getTime());

        // Check user existence
        UserModel user = UserController.getUser(userId);
        if (user == null) {
            user = new UserModel(userId, sqlDate, fullName, gender, age, phone);
            UserController.createUser(user);
        }

        // Get flight info
        FlightModel flight = FlightController.getFlight(flightIdFrom);
        if (flight == null) {
            JOptionPane.showMessageDialog(null, "Flight not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get available seats for this flight with matching class and available status

        // This method should find a seat that matches flightId, seatClass, and status "Available"
        FlightSeatModel availableFlightSeat = FlightSeatController.getAvailableSeatByClass(flight.getId(), seatClass);

        if (availableFlightSeat == null) {
            JOptionPane.showMessageDialog(null, "No available seats in selected class.", "Booking Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get seat info by seatId from SeatController
        SeatModel seat = SeatController.getSeat(availableFlightSeat.getFlightId());
        if (seat == null) {
            JOptionPane.showMessageDialog(null, "Seat info not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate price (you can also just get price from seat.getPrice())
        double price = seat.getPrice();

        // Generate ticket id
        String ticketId = java.util.UUID.randomUUID().toString();

        // Create ticket object
        TicketModel ticket = new TicketModel(ticketId, user, flight, seat, price);

        // Save ticket to DB
        TicketController.createTicket(ticket);

        // Update seat status to booked
        FlightSeatController.updateSeatStatus(flight.getId(), seat.getSeatId(), "Booked");

        // Show confirmation
        StringBuilder message = new StringBuilder();
        message.append("🎫 Booking Successful!\n\n")
               .append("📅 Date: ").append(dateStr).append("\n")
               .append("👤 User ID: ").append(userId).append("\n")
               .append("🧍 Name: ").append(fullName).append("\n")
               .append("🔹 Gender: ").append(gender).append("\n")
               .append("🔢 Age: ").append(age).append("\n")
               .append("📞 Phone: ").append(phone).append("\n")
               .append("💺 Seat Class: ").append(seatClass).append("\n")
               .append("💰 Price: $").append(price).append("\n\n");

        JOptionPane.showMessageDialog(null, message.toString(), "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);

        main.setCardLayout("home");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Date parsing or booking failed.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    public static boolean isExistingUser(String userId) {
        if (userId == null || userId.isEmpty()) return false;
        UserModel user = UserController.getUser(userId);
        return user != null;
    }

    public static void loadUserInfo(String userId) {
        UserModel user = UserController.getUser(userId);
        if (user != null) {
            fullNameField.setText(user.getFullName());
            genderBox.setSelectedItem(user.getGender());
            ageField.setText(String.valueOf(user.getAge()));
            phoneField.setText(user.getPhone());
        }
    }
}
