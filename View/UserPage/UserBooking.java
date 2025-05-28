package src.View.UserPage;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class UserBooking {
    public static JPanel UserBooking = new JPanel();

    static {
        UserBooking.setLayout(new BorderLayout());
        UserBooking.setBackground(Color.WHITE);
        UserBooking.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        
    }
}
