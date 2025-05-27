package src.View;

import javax.swing.*;

import src.main;
import src.Controller.UserController;
import src.Model.UserModel;

import java.awt.*;

public class UserProfile {
    public static JPanel userProfilePage = new JPanel();
    private static String idFlight, seatId;

    private static UserModel user;  // keep user info here

    public static void setIdFlight(String id) {
        idFlight = id;
        reloadUser();
    }

    public static String getIdFlight() {
        return idFlight;
    }

    public static void setSeatId(String id) {
        seatId = id;
        reloadUser();
    }

    public static String getSeatId() {
        return seatId;
    }

    // Load user based on idFlight and seatId
    private static void reloadUser() {
        // user = userController.getUser(idFlight, seatId);
        refreshUserInfo();
    }

    // Components that show user info, keep references to update
    private static JLabel idLabel = new JLabel();
    private static JLabel nameLabel = new JLabel();
    private static JLabel genderLabel = new JLabel();
    private static JLabel ageLabel = new JLabel();

    // The panel holding user info labels
    private static JPanel profilePanel;

    static {
        userProfilePage.setLayout(new BorderLayout());
        userProfilePage.setBackground(Color.WHITE);
        userProfilePage.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // --- Add Back Panel + Title Panel (like Booking page) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // You need to create this method (or replace with your back button panel)
        JPanel backPanel = main.createBackPanel(() -> {
            main.setCardLayout("booking");  // or any card you want to go back to
        });

        JLabel title = new JLabel("User Profile", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        title.setForeground(new Color(30, 30, 60));

        topPanel.add(backPanel, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);

        userProfilePage.add(topPanel, BorderLayout.NORTH);

        // Profile Info Panel
        profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setBackground(Color.WHITE);
        userProfilePage.add(profilePanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);

        // Labels setup: label name and corresponding value label (empty now)
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel idText = new JLabel("User ID:");
        idText.setFont(labelFont);
        profilePanel.add(idText, gbc);

        gbc.gridx = 1;
        idLabel.setFont(labelFont);
        profilePanel.add(idLabel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        JLabel nameText = new JLabel("Full Name:");
        nameText.setFont(labelFont);
        profilePanel.add(nameText, gbc);

        gbc.gridx = 1;
        nameLabel.setFont(labelFont);
        profilePanel.add(nameLabel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        JLabel genderText = new JLabel("Gender:");
        genderText.setFont(labelFont);
        profilePanel.add(genderText, gbc);

        gbc.gridx = 1;
        genderLabel.setFont(labelFont);
        profilePanel.add(genderLabel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        JLabel ageText = new JLabel("Age:");
        ageText.setFont(labelFont);
        profilePanel.add(ageText, gbc);

        gbc.gridx = 1;
        ageLabel.setFont(labelFont);
        profilePanel.add(ageLabel, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton editButton = new JButton("Edit Profile");
        editButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        editButton.setBackground(new Color(70, 130, 180));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.setPreferredSize(new Dimension(160, 45));
        buttonPanel.add(editButton);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        userProfilePage.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Update displayed user info
    private static void refreshUserInfo() {
        if (user != null) {
            idLabel.setText(String.valueOf(user.getId()));
            nameLabel.setText(user.getFullName() != null ? user.getFullName() : "");
            genderLabel.setText(user.getGender() != null ? user.getGender() : "");
            ageLabel.setText(user.getAge() > 0 ? String.valueOf(user.getAge()) : "");
        } else {
            // Clear info if user is null
            idLabel.setText("");
            nameLabel.setText("");
            genderLabel.setText("");
            ageLabel.setText("");
        }
    }
}
