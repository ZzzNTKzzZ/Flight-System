package src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.main;

public class Navbar {
    public static JPanel navbar = new JPanel();

    static {
        navbar.setLayout(new BoxLayout(navbar, BoxLayout.Y_AXIS));
        navbar.setBackground(Color.LIGHT_GRAY);
        navbar.setPreferredSize(new Dimension(200, 0)); // fixed width
    }

    static void createNavbarItem(String name, String cardName) {
        JButton navButton = createButton(name);

        // Add action listener to switch cards
        navButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setCardLayout(cardName); // switch card on click
            }
        });

        navbar.add(navButton);
        navbar.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    static JButton createButton(String name) {
        JButton button = new JButton(name);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(210, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setBackground(null); // Set background
        button.setFont(button.getFont().deriveFont(16f)); // Set font size
        
        Color originalColor = button.getBackground();
        Color hoverColor = Color.decode("#5a5a5a");

        button.addMouseListener(new MouseAdapter() { // Hover Effect
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
                button.setForeground(Color.decode("#ffffff"));

            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(originalColor);
                button.setForeground(Color.decode("#000000"));

            }
        });

        return button;
    }

    static {
        // Create navbar items with associated card names
        createNavbarItem("Home", "home");
        createNavbarItem("Settings", "settings");
        createNavbarItem("Profile", "profile");
        createNavbarItem("Booking", "booking");
    }
}
