package src.View.UserPage;

import javax.swing.*;

import src.main;
import src.Controller.UserController;
import src.Model.UserModel;

import java.awt.*;

public class UserProfile {
    public static JPanel userProfilePage = new JPanel();
    private static String flightId, userId;

    private static UserModel user;

    public static void setIdFlight(String id) {
        flightId = id;
        reloadUser();
    }

    public static String getIdFlight() {
        return flightId;
    }
    
    public static void setUserId(String id) {
        userId = id;
        reloadUser();
    }

    public static String getUserId() {
        return userId;
    }

    // Load user based on flightId and seatId
    private static void reloadUser() {
        user = UserController.getUser(flightId, userId);
        refreshUserInfo();
    }

    // Components that show user info, keep references to update
    private static JLabel idLabel = new JLabel();
    private static JLabel nameLabel = new JLabel();
    private static JLabel genderLabel = new JLabel();
    private static JLabel ageLabel = new JLabel();

    // Input fields for edit mode
    private static JTextField nameField = new JTextField(20);
    private static JComboBox<String> genderField = new JComboBox<>(new String[]{"Male", "Female", "Other"});
    private static JTextField ageField = new JTextField(5);

    private static boolean editing = false;

    // The panel holding user info labels or inputs
    private static JPanel profilePanel;

    static {
        userProfilePage.setLayout(new BorderLayout());
        userProfilePage.setBackground(Color.WHITE);
        userProfilePage.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // --- Add Back Panel + Title Panel ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel backPanel = main.createBackPanel(() -> {
            main.setCardLayout("booking");
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

        // Create initial labels layout
        createProfileLabels();

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

        // --- Edit Button Action ---
        editButton.addActionListener(e -> {
            if (!editing) {
                // Switch to edit mode
                editing = true;
                editButton.setText("Save");

                // Remove labels, add input fields with current data
                profilePanel.removeAll();

                GridBagConstraints gbcEdit = new GridBagConstraints();
                gbcEdit.insets = new Insets(15, 20, 15, 20);
                gbcEdit.anchor = GridBagConstraints.WEST;

                Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);

                // Add labels on left column (User ID, Full Name, Gender, Age)
                gbcEdit.gridx = 0; gbcEdit.gridy = 0;
                JLabel idText = new JLabel("User ID:");
                idText.setFont(labelFont);
                profilePanel.add(idText, gbcEdit);

                gbcEdit.gridy = 1;
                JLabel nameText = new JLabel("Full Name:");
                nameText.setFont(labelFont);
                profilePanel.add(nameText, gbcEdit);

                gbcEdit.gridy = 2;
                JLabel genderText = new JLabel("Gender:");
                genderText.setFont(labelFont);
                profilePanel.add(genderText, gbcEdit);

                gbcEdit.gridy = 3;
                JLabel ageText = new JLabel("Age:");
                ageText.setFont(labelFont);
                profilePanel.add(ageText, gbcEdit);

                // Add input fields on right column
                gbcEdit.gridx = 1; gbcEdit.gridy = 0;
                idLabel.setFont(labelFont);
                idLabel.setText(user != null ? String.valueOf(user.getId()) : "");
                profilePanel.add(idLabel, gbcEdit);

                gbcEdit.gridy = 1;
                nameField.setText(user != null && user.getFullName() != null ? user.getFullName() : "");
                profilePanel.add(nameField, gbcEdit);

                gbcEdit.gridy = 2;
                genderField.setSelectedItem(user != null ? user.getGender() : "Male");
                profilePanel.add(genderField, gbcEdit);

                gbcEdit.gridy = 3;
                ageField.setText(user != null ? String.valueOf(user.getAge()) : "");
                profilePanel.add(ageField, gbcEdit);

                profilePanel.revalidate();
                profilePanel.repaint();

            } else {
                // Save changes
                try {
                    if (user == null) {
                        JOptionPane.showMessageDialog(userProfilePage, "No user loaded!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validate and update user model
                    String newName = nameField.getText().trim();
                    String newGender = (String) genderField.getSelectedItem();
                    int newAge = Integer.parseInt(ageField.getText().trim());

                    if (newName.isEmpty()) {
                        JOptionPane.showMessageDialog(userProfilePage, "Full name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    user.setFullName(newName);
                    user.setGender(newGender);
                    user.setAge(newAge);

                    // Update DB via controller
                    UserController.setUser(user);

                    // Switch back to view mode
                    editing = false;
                    editButton.setText("Edit Profile");

                    // Remove input fields and recreate labels
                    createProfileLabels();

                    // Refresh labels to show new data
                    refreshUserInfo();

                    profilePanel.revalidate();
                    profilePanel.repaint();

                    JOptionPane.showMessageDialog(userProfilePage, "User profile updated successfully!");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(userProfilePage, "Please enter a valid age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(userProfilePage, "Error updating user profile: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Create and add profile info labels to profilePanel
    private static void createProfileLabels() {
        profilePanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);

        // User ID label and value
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel idText = new JLabel("User ID:");
        idText.setFont(labelFont);
        profilePanel.add(idText, gbc);

        gbc.gridx = 1;
        idLabel.setFont(labelFont);
        profilePanel.add(idLabel, gbc);

        // Full Name label and value
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel nameText = new JLabel("Full Name:");
        nameText.setFont(labelFont);
        profilePanel.add(nameText, gbc);

        gbc.gridx = 1;
        nameLabel.setFont(labelFont);
        profilePanel.add(nameLabel, gbc);

        // Gender label and value
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel genderText = new JLabel("Gender:");
        genderText.setFont(labelFont);
        profilePanel.add(genderText, gbc);

        gbc.gridx = 1;
        genderLabel.setFont(labelFont);
        profilePanel.add(genderLabel, gbc);

        // Age label and value
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel ageText = new JLabel("Age:");
        ageText.setFont(labelFont);
        profilePanel.add(ageText, gbc);

        gbc.gridx = 1;
        ageLabel.setFont(labelFont);
        profilePanel.add(ageLabel, gbc);

        profilePanel.revalidate();
        profilePanel.repaint();
    }

    // Update displayed user info labels
    private static void refreshUserInfo() {
        if (user != null) {
            idLabel.setText(String.valueOf(user.getId()));
            nameLabel.setText(user.getFullName() != null ? user.getFullName() : "");
            genderLabel.setText(user.getGender() != null ? user.getGender() : "");
            ageLabel.setText(String.valueOf(user.getAge()));
        } else {
            idLabel.setText("");
            nameLabel.setText("");
            genderLabel.setText("");
            ageLabel.setText("");
        }
    }
}
