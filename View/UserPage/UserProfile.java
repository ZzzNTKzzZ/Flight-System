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

    // Load user based on flightId and userId
    private static void reloadUser() {
        user = UserController.getUser(flightId, userId);
        updateViewModeFields();
    }

    // Labels for view mode
    private static JLabel idLabel = new JLabel();
    private static JLabel nameLabel = new JLabel();
    private static JLabel genderLabel = new JLabel();
    private static JLabel ageLabel = new JLabel();

    // Input fields for edit mode
    private static JTextField idField = new JTextField(20);
    private static JTextField nameField = new JTextField(20);
    private static JComboBox<String> genderField = new JComboBox<>(new String[]{"Male", "Female", "Other"});
    private static JTextField ageField = new JTextField(5);

    private static boolean editing = false;

    private static JPanel profilePanel;

    static {
        userProfilePage.setLayout(new BorderLayout());
        userProfilePage.setBackground(Color.WHITE);
        userProfilePage.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Top panel with back button and title
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

        // Profile info panel
        profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setBackground(Color.WHITE);
        userProfilePage.add(profilePanel, BorderLayout.CENTER);

        // Add profile info labels (view mode by default)
        buildProfileLayout();

        // Button panel with edit/save toggle button
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

        editButton.addActionListener(e -> {
            if (!editing) {
                // Switch to edit mode
                editing = true;
                editButton.setText("Save");
                switchToEditMode();
            } else {
                // Try to save changes and switch to view mode
                try {
                    if (user == null) {
                        JOptionPane.showMessageDialog(userProfilePage, "No user loaded!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String newId = idField.getText().trim();
                    String newName = nameField.getText().trim();
                    String newGender = (String) genderField.getSelectedItem();
                    String ageText = ageField.getText().trim();

                    if (newId.isEmpty()) {
                        JOptionPane.showMessageDialog(userProfilePage, "User ID cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (newName.isEmpty()) {
                        JOptionPane.showMessageDialog(userProfilePage, "Full name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int newAge;
                    try {
                        newAge = Integer.parseInt(ageText);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(userProfilePage, "Please enter a valid age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update user model
                    user.setId(newId);
                    user.setFullName(newName);
                    user.setGender(newGender);
                    user.setAge(newAge);
                    System.out.println(user);
                    // Save to DB via controller
                    UserController.setUser(user);

                    // Refresh userId variable as well
                    userId = newId;

                    // Switch back to view mode
                    editing = false;
                    editButton.setText("Edit Profile");
                    switchToViewMode();

                    JOptionPane.showMessageDialog(userProfilePage, "User profile updated successfully!");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(userProfilePage, "Error updating user profile: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Build profile panel with labels & input fields but only show labels by default
    private static void buildProfileLayout() {
        profilePanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);

        // User ID
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel idText = new JLabel("User ID:");
        idText.setFont(labelFont);
        profilePanel.add(idText, gbc);

        gbc.gridx = 1;
        idLabel.setFont(labelFont);
        profilePanel.add(idLabel, gbc);

        gbc.gridx = 1;
        idField.setFont(labelFont);

        // Full Name
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel nameText = new JLabel("Full Name:");
        nameText.setFont(labelFont);
        profilePanel.add(nameText, gbc);

        gbc.gridx = 1;
        nameLabel.setFont(labelFont);
        profilePanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField.setFont(labelFont);

        // Gender
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel genderText = new JLabel("Gender:");
        genderText.setFont(labelFont);
        profilePanel.add(genderText, gbc);

        gbc.gridx = 1;
        genderLabel.setFont(labelFont);
        profilePanel.add(genderLabel, gbc);

        gbc.gridx = 1;
        genderField.setFont(labelFont);

        // Age
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel ageText = new JLabel("Age:");
        ageText.setFont(labelFont);
        profilePanel.add(ageText, gbc);

        gbc.gridx = 1;
        ageLabel.setFont(labelFont);
        profilePanel.add(ageLabel, gbc);

        gbc.gridx = 1;
        ageField.setFont(labelFont);

        updateViewModeFields();

        profilePanel.revalidate();
        profilePanel.repaint();
    }

    // Show all labels, hide input fields
    private static void switchToViewMode() {
        profilePanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);

        // User ID label
        gbc.gridx = 0; gbc.gridy = 0;
        profilePanel.add(new JLabel("User ID:"), gbc);
        ((JLabel) profilePanel.getComponent(profilePanel.getComponentCount()-1)).setFont(labelFont);
        gbc.gridx = 1;
        profilePanel.add(idLabel, gbc);

        // Full Name label
        gbc.gridx = 0; gbc.gridy = 1;
        profilePanel.add(new JLabel("Full Name:"), gbc);
        ((JLabel) profilePanel.getComponent(profilePanel.getComponentCount()-1)).setFont(labelFont);
        gbc.gridx = 1;
        profilePanel.add(nameLabel, gbc);

        // Gender label
        gbc.gridx = 0; gbc.gridy = 2;
        profilePanel.add(new JLabel("Gender:"), gbc);
        ((JLabel) profilePanel.getComponent(profilePanel.getComponentCount()-1)).setFont(labelFont);
        gbc.gridx = 1;
        profilePanel.add(genderLabel, gbc);

        // Age label
        gbc.gridx = 0; gbc.gridy = 3;
        profilePanel.add(new JLabel("Age:"), gbc);
        ((JLabel) profilePanel.getComponent(profilePanel.getComponentCount()-1)).setFont(labelFont);
        gbc.gridx = 1;
        profilePanel.add(ageLabel, gbc);

        updateViewModeFields();

        profilePanel.revalidate();
        profilePanel.repaint();
    }

    // Show input fields, hide labels
    private static void switchToEditMode() {
        profilePanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);

        // User ID input
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel idText = new JLabel("User ID:");
        idText.setFont(labelFont);
        profilePanel.add(idText, gbc);

        gbc.gridx = 1;
        idField.setText(user != null ? user.getId() : "");
        profilePanel.add(idField, gbc);

        // Full Name input
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel nameText = new JLabel("Full Name:");
        nameText.setFont(labelFont);
        profilePanel.add(nameText, gbc);

        gbc.gridx = 1;
        nameField.setText(user != null && user.getFullName() != null ? user.getFullName() : "");
        profilePanel.add(nameField, gbc);

        // Gender input
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel genderText = new JLabel("Gender:");
        genderText.setFont(labelFont);
        profilePanel.add(genderText, gbc);

        gbc.gridx = 1;
        genderField.setSelectedItem(user != null ? user.getGender() : "Male");
        profilePanel.add(genderField, gbc);

        // Age input
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel ageText = new JLabel("Age:");
        ageText.setFont(labelFont);
        profilePanel.add(ageText, gbc);

        gbc.gridx = 1;
        ageField.setText(user != null ? String.valueOf(user.getAge()) : "");
        profilePanel.add(ageField, gbc);

        profilePanel.revalidate();
        profilePanel.repaint();
    }

    // Update label texts from user data
    private static void updateViewModeFields() {
        if (user != null) {
            idLabel.setText(user.getId());
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
