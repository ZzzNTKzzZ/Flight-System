package src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import src.main;

public class Navbar {
    public static JPanel navbar = new JPanel();
    private static List<JButton> buttons = new ArrayList<>();
    private static JButton activeButton = null;

    static {
        navbar.setLayout(new BoxLayout(navbar, BoxLayout.Y_AXIS));
        navbar.setBackground(Color.LIGHT_GRAY);
        navbar.setPreferredSize(new Dimension(200, 0)); // fixed width

        // Create navbar items with associated card names
        createNavbarItem("Home", "home");
        createNavbarItem("Upcoming", "listProfile");
        createNavbarItem("Booking", "booking");
    }

    static void createNavbarItem(String name, String cardName) {
        JButton navButton = createButton(name);

        // Add to list for tracking
        buttons.add(navButton);

        navButton.addActionListener(e -> {
            main.setCardLayout(cardName); // switch card on click
            setActiveButton(navButton);   // update active style
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
        button.setBackground(null);
        button.setFont(button.getFont().deriveFont(16f));

        Color hoverColor = Color.decode("#5a5a5a");
        Color normalColor = null;

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (button != activeButton) {
                    button.setBackground(hoverColor);
                    button.setForeground(Color.WHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (button != activeButton) {
                    button.setBackground(normalColor);
                    button.setForeground(Color.BLACK);
                }
            }
        });

        return button;
    }

    static void setActiveButton(JButton selectedButton) {
        for (JButton button : buttons) {
            button.setBackground(null);
            button.setForeground(Color.BLACK);
        }

        selectedButton.setBackground(Color.DARK_GRAY);
        selectedButton.setForeground(Color.WHITE);
        activeButton = selectedButton;
    }
}
