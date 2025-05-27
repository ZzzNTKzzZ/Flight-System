package src.View;

import javax.swing.*;

import src.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Home {
    public static JPanel home = new JPanel();

    static {
        home.setLayout(new BorderLayout());
        home.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Flight Manager System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(30, 30, 60));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        home.add(titleLabel, BorderLayout.NORTH);

        // Center content panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JButton flightsButton = createHomeItem("View Flights","flightCurrent");
        JButton addNewFlightButton = createHomeItem("Add New Flight", "newFlight");

        JLabel welcomeLabel = new JLabel("Welcome! Manage your flights easily.");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        welcomeLabel.setForeground(new Color(60, 60, 90));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        centerPanel.add(welcomeLabel, BorderLayout.NORTH);
        centerPanel.add(flightsButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(addNewFlightButton);

        home.add(centerPanel, BorderLayout.CENTER);
        
    }

    static JButton createHomeItem(String name, String cardName) {
        JButton homeButton = createButton(name);

        // Add action listener to switch cards
        homeButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setCardLayout(cardName); 
            }
        });

        return homeButton;
    }

    static JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(50, 110, 160));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }
}
