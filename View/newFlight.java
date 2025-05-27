package src.View;

import javax.swing.*;
import java.awt.*;

import src.main;

public class newFlight {
    public static JPanel newFlightPage = new JPanel();

    static {
        newFlightPage.setLayout(new BorderLayout());
        newFlightPage.setBackground(Color.WHITE);
        newFlightPage.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // padding around

        // Create the back panel with a back button
        JPanel backPanel = createBackPanel(() -> {
            // Call your navigation method here to go back to home or previous card
            main.setCardLayout("home");
        });

        // Create a top panel to hold back button on left and title centered
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(backPanel, BorderLayout.WEST);

        JLabel title = new JLabel("Add New Flight", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36)); // large font for title
        title.setForeground(new Color(30, 30, 60));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0)); // top and bottom padding
        topPanel.add(title, BorderLayout.CENTER);

        newFlightPage.add(topPanel, BorderLayout.NORTH);

        // Labels for form fields
        String[] labels = { "Flight ID:", "From:", "To:", "Departure:", "Arrival:" };

        // Create form panel with label+textfield rows
        JPanel form = main.createForm(labels);
        newFlightPage.add(form, BorderLayout.CENTER);

        // Button panel at bottom with submit button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Segoe UI", Font.PLAIN, 20)); // larger font
        submitButton.setPreferredSize(new Dimension(140, 50)); // bigger button size

        buttonPanel.add(submitButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0)); // padding around button panel

        newFlightPage.add(buttonPanel, BorderLayout.SOUTH);
    }
    

    // Back panel factory method with callback
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
}
