package src.View.FlightPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import src.Model.FlightModel;
import src.View.BookingPage.BookingDetail;
import src.main;
import src.Controller.FlightController;

public class FlightCurrentPage {
    public static JPanel flightCurrentPage = new JPanel();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy - HH:mm");

    static JPanel flightItem(FlightModel flight) {
        JPanel card = new JPanel(new GridLayout(3, 2));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setBackground(new Color(240, 248, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        card.setMaximumSize(new Dimension(800, 100));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);

        JLabel flightIdLabel = new JLabel("Flight ID: " + flight.getId());
        flightIdLabel.setFont(labelFont);
        card.add(flightIdLabel);

        JLabel fromLabel = new JLabel("From: " + flight.getFrom());
        fromLabel.setFont(labelFont);
        card.add(fromLabel);

        JLabel toLabel = new JLabel("To: " + flight.getTo());
        toLabel.setFont(labelFont);
        card.add(toLabel);

        JLabel departureLabel = new JLabel("Departure: " + dateFormat.format(flight.getDeparture()));
        departureLabel.setFont(labelFont);
        card.add(departureLabel);

        JLabel arrivalLabel = new JLabel("Arrival: " + dateFormat.format(flight.getArrival()));
        arrivalLabel.setFont(labelFont);
        card.add(arrivalLabel);

        String currentFlightId = flight.getId();

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    BookingDetail.setFlightId(currentFlightId);
                    main.setCardLayout("bookingDetail");
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu menu = new JPopupMenu();

                    JMenuItem editItem = new JMenuItem("Edit");
                    editItem.addActionListener(ev -> {
                        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(card);
                        EditFlightDialog dialog = new EditFlightDialog(parentFrame, flight);
                        dialog.setVisible(true);
                        if (dialog.isUpdated()) {
                            FlightModel updatedFlight = dialog.getUpdatedFlight();
                            FlightController.updateFlight(updatedFlight);
                            refreshFlightList();
                        }
                    });

                    JMenuItem deleteItem = new JMenuItem("Delete");
                    deleteItem.addActionListener(ev -> {
                        int confirm = JOptionPane.showConfirmDialog(card,
                                "Are you sure you want to delete this flight?",
                                "Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            FlightController.deleteFlight(flight.getId());
                            refreshFlightList();
                        }
                    });

                    menu.add(editItem);
                    menu.add(deleteItem);
                    menu.show(card, e.getX(), e.getY());
                }
            }
        });

        return card;
    }

    static JPanel flightList() throws SQLException {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        List<FlightModel> flights = FlightController.getAllFlights();
        for (FlightModel flight : flights) {
            listPanel.add(flightItem(flight));
            listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return listPanel;
    }

    public static void refreshFlightList() {
        flightCurrentPage.removeAll();
        flightCurrentPage.setLayout(new BorderLayout());
        flightCurrentPage.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel backPanel = main.createBackPanel(() -> {
            main.setCardLayout("home");
        });
        topPanel.add(backPanel, BorderLayout.WEST);

        JLabel title = new JLabel("Current Flights", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        topPanel.add(title, BorderLayout.CENTER);

        flightCurrentPage.add(topPanel, BorderLayout.NORTH);

        try {
            JScrollPane scrollPane = new JScrollPane(flightList());
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            flightCurrentPage.add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        flightCurrentPage.revalidate();
        flightCurrentPage.repaint();
    }

    static {
        refreshFlightList();
    }
}

class EditFlightDialog extends JDialog {
    private JTextField fromField;
    private JTextField toField;
    private JTextField departureField;
    private JTextField arrivalField;

    private boolean updated = false;
    private FlightModel updatedFlight;

    public EditFlightDialog(JFrame parent, FlightModel flight) {
        super(parent, "Edit Flight: " + flight.getId(), true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        fromField = new JTextField(flight.getFrom());
        toField = new JTextField(flight.getTo());
        departureField = new JTextField(sdf.format(flight.getDeparture()));
        arrivalField = new JTextField(sdf.format(flight.getArrival()));

        panel.add(new JLabel("From:"));
        panel.add(fromField);
        panel.add(new JLabel("To:"));
        panel.add(toField);
        panel.add(new JLabel("Departure (yyyy-MM-dd HH:mm):"));
        panel.add(departureField);
        panel.add(new JLabel("Arrival (yyyy-MM-dd HH:mm):"));
        panel.add(arrivalField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                String from = fromField.getText();
                String to = toField.getText();
                Date departure = sdf.parse(departureField.getText());
                Date arrival = sdf.parse(arrivalField.getText());
                updatedFlight = new FlightModel(flight.getId(), from, to, departure, arrival, flight.getStatus(),
                        flight.getSeatAvailable());
                updated = true;
                dispose();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid date format. Please use yyyy-MM-dd HH:mm",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel());
        panel.add(saveButton);

        add(panel);
    }

    public boolean isUpdated() {
        return updated;
    }

    public FlightModel getUpdatedFlight() {
        return updatedFlight;
    }
}